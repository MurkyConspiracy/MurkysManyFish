package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.TunaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
@SuppressWarnings("unused")
public class TunaRenderer extends MobEntityRenderer<TunaEntity, LivingEntityRenderState, TunaModel> {
    public TunaRenderer(EntityRendererFactory.Context context) {
        super(context, new TunaModel(context.getPart(TunaModel.TUNA)), 0.1f);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return Identifier.of(MurkysManyFish.MOD_ID, "textures/entity/tuna.png");
    }


    @Override
    public void render(LivingEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

        if(livingEntityRenderState.baby)
        {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(2f, 2f, 2f);
        }

        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
