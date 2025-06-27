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
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AngelfishEntity extends SchoolingFishEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimer = 0;

    public AngelfishEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
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
                .add(EntityAttributes.MAX_HEALTH, 4)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.7)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    private void setupAnimationStates()
    {
        if (this.idleAnimationTimer <=0)
        {
            this.idleAnimationTimer = 40;
            this.idleAnimationState.start(this.age);
        }else
        {
            --this.idleAnimationTimer;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getWorld().isClient)
        {
            this.setupAnimationStates();
        }
    }


    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItemHandler.ANGELFISH_BUCKET);
    }

    @Override
    public void travel(Vec3d movementInput) {
        super.travel(movementInput);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new MoveIntoWaterGoal(this));
        this.goalSelector.add(2, new SwimAroundGoal(this, 1.0D, 10));
    }
}