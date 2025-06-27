package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.BettaEntity;
import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class BettaRenderer extends MobEntityRenderer<BettaEntity, BettaRenderState, BettaModel> {

    private static final Identifier DEFAULT_TEXTURE = Identifier.of(MurkysManyFish.MOD_ID, "textures/entity/betta.png");

    private static final Map<BettaVariant, Identifier> TEXTURE_BY_PRIDE = Util.make(Maps.newEnumMap(BettaVariant.class), (map) -> {
        map.put(BettaVariant.DEFAULT, DEFAULT_TEXTURE);
        map.put(BettaVariant.PRIDE, Identifier.of(MurkysManyFish.MOD_ID, "textures/entity/betta_pride.png"));
    });

    public BettaRenderer(EntityRendererFactory.Context context) {
        super(context, new BettaModel(context.getPart(BettaModel.BETTA)), 0.2f);
    }

    @Override
    public Identifier getTexture(BettaRenderState state) {
        return TEXTURE_BY_PRIDE.get(state.variant);
    }

    @Override
    public void render(BettaRenderState bettaRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(bettaRenderState.baby) {
            matrixStack.scale(0.4f, 0.4f, 0.4f); // Smaller baby anchovy
        } else {
            matrixStack.scale(0.8f, 0.8f, 0.8f); // Slightly smaller than angelfish
        }

        super.render(bettaRenderState, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public BettaRenderState createRenderState() {
        return new BettaRenderState();
    }

    @Override
    public void updateRenderState(BettaEntity livingEntity, BettaRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
        livingEntityRenderState.variant = livingEntity.getVariant();
    }
}