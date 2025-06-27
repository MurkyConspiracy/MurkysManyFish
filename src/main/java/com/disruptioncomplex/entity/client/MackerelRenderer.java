package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.MackerelEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MackerelRenderer extends MobEntityRenderer<MackerelEntity, LivingEntityRenderState, MackerelModel> {
    public MackerelRenderer(EntityRendererFactory.Context context) {
        super(context, new MackerelModel(context.getPart(MackerelModel.MACKEREL)), 0.1f);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return Identifier.of(MurkysManyFish.MOD_ID, "textures/entity/mackerel.png");
    }


    @Override
    public void render(LivingEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if(livingEntityRenderState.baby)
        {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
