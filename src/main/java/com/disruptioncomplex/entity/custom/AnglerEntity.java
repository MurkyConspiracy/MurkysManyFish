package com.disruptioncomplex.entity.custom;

import com.disruptioncomplex.item.ModItemHandler;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AnglerEntity extends FishEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimer = 0;
    // Angler fish have light glow animation
    public final AnimationState glowAnimationState = new AnimationState();
    private int glowAnimationTimer = 0;

    public AnglerEntity(EntityType<? extends FishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PUFFER_FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_PUFFER_FISH_FLOP;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 6)  // Higher health than anchovy
                .add(EntityAttributes.MOVEMENT_SPEED, 0.7)  // Slower movement
                .add(EntityAttributes.FOLLOW_RANGE, 20);  // Larger follow range
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimer <= 0) {
            this.idleAnimationTimer = 40;  // Longer animation cycle
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimer;
        }
        
        // Manage glow animation
        if (this.glowAnimationTimer <= 0) {
            this.glowAnimationTimer = 60;  // Glow animation cycle
            this.glowAnimationState.start(this.age);
        } else {
            --this.glowAnimationTimer;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getWorld().isClient) {
            this.setupAnimationStates();
        }
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItemHandler.ANGLER_BUCKET);
    }

    @Override
    public void travel(Vec3d movementInput) {
        super.travel(movementInput);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new MoveIntoWaterGoal(this));
        this.goalSelector.add(2, new SwimAroundGoal(this, 0.8D, 8));  // Slower swimming with smaller range
    }
}