package com.disruptioncomplex.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class AnchovyRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public AnchovyVariant variant;


}
