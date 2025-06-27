package com.disruptioncomplex.entity.custom;

import com.disruptioncomplex.entity.client.AnchovyVariant;
import com.disruptioncomplex.item.ModItemHandler;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AnchovyEntity extends SchoolingFishEntity {
    // Track variant status using DataTracker for proper synchronization
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimer = 0;

    /*
    VARIANT
     */
    private static final TrackedData<Integer> ANCHOVY_VARIANT =
            DataTracker.registerData(AnchovyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public AnchovyEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
        // Set variant with 1% chance when entity is created
        if (world.isClient()) {
            return; // Only set on server side
        }

        this.setVariant(this.random.nextInt(100) == 0 ? AnchovyVariant.PRIDE : AnchovyVariant.DEFAULT);
    }



    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_COD_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_COD_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 2)  // Lower health than angelfish
                .add(EntityAttributes.MOVEMENT_SPEED, 0.9)  // Faster movement
                .add(EntityAttributes.FOLLOW_RANGE, 16);  // Slightly smaller follow range
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimer <= 0) {
            this.idleAnimationTimer = 30;  // Shorter animation cycle
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimer;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient) {
            this.setupAnimationStates();
        }
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItemHandler.ANCHOVY_BUCKET);
    }

    @Override
    public void travel(Vec3d movementInput) {
        super.travel(movementInput);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new MoveIntoWaterGoal(this));
        this.goalSelector.add(2, new SwimAroundGoal(this, 1.2D, 10));  // Slightly faster swimming
    }

    /*
    VARIANT
     */

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ANCHOVY_VARIANT, 0);

    }

    public AnchovyVariant getVariant() {
        return AnchovyVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(ANCHOVY_VARIANT);
    }



    public void setVariant(AnchovyVariant variant) {
        this.dataTracker.set(ANCHOVY_VARIANT, variant.getId());
    }



    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Variant")) {
            this.dataTracker.set(ANCHOVY_VARIANT, nbt.getInt("Variant").orElse(0));
        }
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {

        AnchovyVariant variant = world.getRandom().nextInt(100) == 1 ? AnchovyVariant.PRIDE : AnchovyVariant.DEFAULT;
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }






}