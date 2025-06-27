package com.disruptioncomplex.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class BettaRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public BettaVariant variant;

}