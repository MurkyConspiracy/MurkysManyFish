package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.AnchovyEntity;
import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class AnchovyRenderer extends MobEntityRenderer<AnchovyEntity, AnchovyRenderState, AnchovyModel> {

    private static final Identifier DEFAULT_TEXTURE = Identifier.of(MurkysManyFish.MOD_ID, "textures/entity/anchovy.png");
    
    private static final Map<AnchovyVariant, Identifier> TEXTURE_BY_PRIDE = Util.make(Maps.newEnumMap(AnchovyVariant.class), (map) -> {
        map.put(AnchovyVariant.DEFAULT, DEFAULT_TEXTURE);
        map.put(AnchovyVariant.PRIDE, Identifier.of(MurkysManyFish.MOD_ID, "textures/entity/anchovy_pride.png"));
    });

    public AnchovyRenderer(EntityRendererFactory.Context context) {
        super(context, new AnchovyModel(context.getPart(AnchovyModel.ANCHOVY)), 0.2f);
    }

    @Override
    public Identifier getTexture(AnchovyRenderState state) {
       return TEXTURE_BY_PRIDE.get(state.variant);
    }

    @Override
    public void render(AnchovyRenderState anchovyRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(anchovyRenderState.baby) {
            matrixStack.scale(0.4f, 0.4f, 0.4f); // Smaller baby anchovy
        } else {
            matrixStack.scale(0.8f, 0.8f, 0.8f); // Slightly smaller than angelfish
        }

        super.render(anchovyRenderState, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public AnchovyRenderState createRenderState() {
        return new AnchovyRenderState();
    }

    @Override
    public void updateRenderState(AnchovyEntity livingEntity, AnchovyRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
        livingEntityRenderState.variant = livingEntity.getVariant();
    }
}