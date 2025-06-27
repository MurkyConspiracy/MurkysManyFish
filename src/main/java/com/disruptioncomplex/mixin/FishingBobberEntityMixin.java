package com.disruptioncomplex.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("all")
@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {

    @Shadow private int waitCountdown;
    @Shadow private int hookCountdown;
    @Shadow private int fishTravelCountdown;
    @Shadow private float fishAngle;
    @Shadow private int waitTimeReductionTicks;
    @Shadow private static TrackedData<Boolean> CAUGHT_FISH;
    @Shadow private boolean inOpenWater;
    //private World world;


    @Inject(method = "tickFishingLogic", at = @At("HEAD"), cancellable = true)
    private void tickFishingLogic(BlockPos pos, CallbackInfo ci) {


        Entity entity = (Entity)(Object)this;
        World world = entity.getEntityWorld();
        DataTracker dataTracker = entity.getDataTracker();
        Random random = entity.getRandom();


        ServerWorld serverWorld = (ServerWorld) world;
        int i = 1;
        BlockPos blockPos = pos.up();
        if (random.nextFloat() < 0.25F && world.hasRain(blockPos)) {
            ++i;
        }

        if (random.nextFloat() < 0.5F && !world.isSkyVisible(blockPos)) {
            --i;
        }




        if (this.hookCountdown > 0) {
            --this.hookCountdown;
            if (this.hookCountdown <= 0) {
                this.waitCountdown = 0;
                this.fishTravelCountdown = 0;
                dataTracker.set(CAUGHT_FISH, false);
            }
        } else if (this.fishTravelCountdown > 0) {
            this.fishTravelCountdown -= i;
            if (this.fishTravelCountdown > 0) {
                this.fishAngle += (float)random.nextTriangular((double)0.0F, 9.188);
                float f = this.fishAngle * ((float)Math.PI / 180F);
                float g = MathHelper.sin(f);
                float h = MathHelper.cos(f);
                double d = ((FishingBobberEntity)(Object)this).getX() + (double)(g * (float)this.fishTravelCountdown * 0.1F);
                double e = (double)((float)MathHelper.floor(((FishingBobberEntity)(Object)this).getY()) + 1.0F);
                double j = ((FishingBobberEntity)(Object)this).getZ() + (double)(h * (float)this.fishTravelCountdown * 0.1F);
                BlockState blockState = serverWorld.getBlockState(BlockPos.ofFloored(d, e - (double)1.0F, j));
                if (blockState.isOf(Blocks.WATER)) {
                    if (random.nextFloat() < 0.15F) {
                        serverWorld.spawnParticles(ParticleTypes.BUBBLE, d, e - (double)0.1F, j, 1, (double)g, 0.1, (double)h, (double)0.0F);
                    }

                    float k = g * 0.04F;
                    float l = h * 0.04F;

                    serverWorld.spawnParticles(ParticleTypes.FISHING, d, e, j, 0, (double)l, 0.01, (double)(-k), (double)1.0F);
                    serverWorld.spawnParticles(ParticleTypes.FISHING, d, e, j, 0, (double)(-l), 0.01, (double)k, (double)1.0F);
                }
            } else {
                ((FishingBobberEntity)(Object) this).playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (random.nextFloat() - random.nextFloat()) * 0.4F);
                double m = ((FishingBobberEntity)(Object)this).getY() + (double)0.5F;

                serverWorld.spawnParticles(ParticleTypes.BUBBLE, ((FishingBobberEntity)(Object)this).getX(), m, ((FishingBobberEntity)(Object)this).getZ(), (int)(1.0F + ((FishingBobberEntity)(Object) this).getWidth() * 20.0F), (double)((FishingBobberEntity)(Object) this).getWidth(), (double)0.0F, ((FishingBobberEntity)(Object) this).getWidth(), (double)0.2F);
                serverWorld.spawnParticles(ParticleTypes.FISHING, ((FishingBobberEntity)(Object)this).getX(), m, ((FishingBobberEntity)(Object)this).getZ(), (int)(1.0F + ((FishingBobberEntity)(Object) this).getWidth() * 20.0F), (double)((FishingBobberEntity)(Object) this).getWidth(), (double)0.0F, ((FishingBobberEntity)(Object) this).getWidth(), (double)0.2F);
                this.hookCountdown = MathHelper.nextInt(random, 20, 40);
                dataTracker.set(CAUGHT_FISH, true);
            }
        } else if (this.waitCountdown > 0) {
            this.waitCountdown -= i;
            float f = 0.15F;
            if (this.waitCountdown < 20) {
                f += (float)(20 - this.waitCountdown) * 0.05F;
            } else if (this.waitCountdown < 40) {
                f += (float)(40 - this.waitCountdown) * 0.02F;
            } else if (this.waitCountdown < 60) {
                f += (float)(60 - this.waitCountdown) * 0.01F;
            }

            if (random.nextFloat() < f) {
                float g = MathHelper.nextFloat(random, 0.0F, 360.0F) * ((float)Math.PI / 180F);
                float h = MathHelper.nextFloat(random, 25.0F, 60.0F);
                double d = ((FishingBobberEntity)(Object)this).getX() + (double)(MathHelper.sin(g) * h) * 0.1;
                double e = (double)((float)MathHelper.floor(((FishingBobberEntity)(Object)this).getY()) + 1.0F);
                double j = ((FishingBobberEntity)(Object)this).getZ() + (double)(MathHelper.cos(g) * h) * 0.1;

                BlockState blockState = serverWorld.getBlockState(BlockPos.ofFloored(d, e - (double)1.0F, j));
                if (blockState.isOf(Blocks.WATER)) {
                    serverWorld.spawnParticles(ParticleTypes.SPLASH, d, e, j, 10 + random.nextInt(2), (double)0.1F, (double)0.1F, (double)0.1F, (double)1.1F);
                }
            }

            if (this.waitCountdown <= 0) {
                this.fishAngle = MathHelper.nextFloat(random, 0.0F, 360.0F);
                this.fishTravelCountdown = MathHelper.nextInt(random, 20, 80);
            }
        } else {
            this.waitCountdown = MathHelper.nextInt(random, 100, 600);
            this.waitCountdown -= this.waitTimeReductionTicks;
        }

        ci.cancel();






    }
    
}
