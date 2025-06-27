package com.disruptioncomplex.entity.custom;

import com.disruptioncomplex.entity.ModEntityHandler;
import com.disruptioncomplex.item.ModItemHandler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public class BambooSpearEntity extends PersistentProjectileEntity {

    public static final Codec<EntityReference> ENTITY_REFERENCE_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("uuid").forGetter(ref -> ref.uuid().toString()),
                    Codec.DOUBLE.fieldOf("x").forGetter(EntityReference::x),
                    Codec.DOUBLE.fieldOf("y").forGetter(EntityReference::y),
                    Codec.DOUBLE.fieldOf("z").forGetter(EntityReference::z)
            ).apply(instance, (uuid, x, y, z) -> new EntityReference(UUID.fromString(uuid), x, y, z))
    );

    // Add this record for entity reference
    public record EntityReference(UUID uuid, double x, double y, double z) {}

    private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(BambooSpearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private boolean dealtDamage = false;

    public BambooSpearEntity(EntityType<? extends BambooSpearEntity> entityType, World world) {
        super(ModEntityHandler.BAMBOO_SPEAR, world);
    }

    public BambooSpearEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntityHandler.BAMBOO_SPEAR, owner, world, stack, null);
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
    }

    public BambooSpearEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntityHandler.BAMBOO_SPEAR, x, y, z, world, stack, stack);
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
    }

    public BambooSpearEntity(World world) {
        super(ModEntityHandler.BAMBOO_SPEAR, world);
    }


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ENCHANTED, false);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoClip()) && entity != null) {
            if (!this.isOwnerAlive()) {
                if (this.getWorld() instanceof ServerWorld serverWorld && this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(serverWorld, this.asItemStack(), 0.1F);
                }

                this.discard();
            } else {
                if (!(entity instanceof PlayerEntity) && this.getPos().distanceTo(entity.getEyePos()) < entity.getWidth() + 1.0) {
                    this.discard();
                    return;
                }

                //this.setNoClip(true);
                Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015, this.getZ());
                this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(0.05)));
            }
        }

        super.tick();
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        return entity != null && entity.isAlive() && (!(entity instanceof ServerPlayerEntity) || !entity.isSpectator());
    }

    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }

    @Nullable
    @Override
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = 8.0F;
        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.getDamageSources().trident(this, (entity2 == null ? this : entity2));
        if (this.getWorld() instanceof ServerWorld serverWorld && this.getWeaponStack() != null) {
            f = EnchantmentHelper.getDamage(serverWorld, this.getWeaponStack(), entity, damageSource, f);
        }

        this.dealtDamage = true;
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (entity.damage(serverWorld, damageSource, f)) {
                if (entity.getType() == EntityType.ENDERMAN) {
                    return;
                }

                EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource, this.getWeaponStack(), item -> this.kill(serverWorld));


                if (entity instanceof LivingEntity livingEntity) {
                    this.knockback(livingEntity, damageSource);
                    this.onHit(livingEntity);
                }
            }
        }

        this.deflect(ProjectileDeflection.SIMPLE, entity, this.getOwner(), false);
        this.setVelocity(this.getVelocity().multiply(0.02, 0.2, 0.02));
        this.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1.0F, 1.0F);
    }

    @Override
    protected void onBlockHitEnchantmentEffects(ServerWorld world, BlockHitResult blockHitResult, ItemStack weaponStack) {
        Vec3d vec3d = blockHitResult.getBlockPos().clampToWithin(blockHitResult.getPos());
        EnchantmentHelper.onHitBlock(
                world,
                weaponStack,
                this.getOwner() instanceof LivingEntity livingEntity ? livingEntity : null,
                this,
                null,
                vec3d,
                world.getBlockState(blockHitResult.getBlockPos()),
                item -> this.kill(world)
        );
    }

    @Override
    public ItemStack getWeaponStack() {
        return this.getItemStack();
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return super.tryPickup(player) || this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItemHandler.BAMBOO_SPEAR);
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.isOwner(player) || this.getOwner() == null) {
            super.onPlayerCollision(player);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dealtDamage = nbt.getBoolean("DealtDamage", false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void age() {
        if (this.pickupType != PersistentProjectileEntity.PickupPermission.ALLOWED) {
            super.age();
        }
    }

    @Override
    protected float getDragInWater() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(1F, 1F);
    }
}
