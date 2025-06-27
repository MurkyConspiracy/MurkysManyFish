package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.BambooSpearEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BambooSpearEntityRenderer extends EntityRenderer<BambooSpearEntity, BambooSpearEntityRenderState> {
    public static final Identifier TEXTURE = Identifier.of(MurkysManyFish.MOD_ID,"textures/entity/bamboo_spear.png");
    private final BambooSpearEntityModel model;

    public BambooSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new BambooSpearEntityModel(context.getPart(BambooSpearEntityModel.BAMBOO_SPEAR));
    }

    public void render(BambooSpearEntityRenderState bambooSpearEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(bambooSpearEntityRenderState.yaw - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(bambooSpearEntityRenderState.pitch + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(
                vertexConsumerProvider, this.model.getLayer(TEXTURE), false, bambooSpearEntityRenderState.enchanted
        );
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(bambooSpearEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    public BambooSpearEntityRenderState createRenderState() {
        return new BambooSpearEntityRenderState();
    }

    public void updateRenderState(BambooSpearEntity bambooSpearEntity, BambooSpearEntityRenderState bambooSpearEntityRenderState, float f) {
        super.updateRenderState(bambooSpearEntity, bambooSpearEntityRenderState, f);
        bambooSpearEntityRenderState.yaw = bambooSpearEntity.getLerpedYaw(f);
        bambooSpearEntityRenderState.pitch = bambooSpearEntity.getLerpedPitch(f);
        bambooSpearEntityRenderState.enchanted = bambooSpearEntity.isEnchanted();
    }
}

