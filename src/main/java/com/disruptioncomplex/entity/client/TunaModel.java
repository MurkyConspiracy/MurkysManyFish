package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("unused")
public class TunaModel extends EntityModel<LivingEntityRenderState> {


    public static final EntityModelLayer TUNA = new EntityModelLayer(Identifier.of(MurkysManyFish.MOD_ID, "tuna"), "main");


    public ModelPart body;
    public ModelPart head;
    public ModelPart tail_front;
    public ModelPart tail_back;

    public TunaModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body_front");
        this.head = root.getChild("head");
        this.tail_front = root.getChild("tail_front");
        this.tail_back = root.getChild("tail_back");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body_front = modelPartData.addChild("body_front", ModelPartBuilder.create().uv(14, 23).cuboid(-3.0F, -4.0F, 0.0F, 6.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.0F, -3.0F));

        ModelPartData right_fin = body_front.addChild("right_fin", ModelPartBuilder.create().uv(-3, 17).cuboid(-4.0F, 0.0F, -1.0F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        ModelPartData left_fin = body_front.addChild("left_fin", ModelPartBuilder.create().uv(-3, 20).cuboid(0.0F, 0.0F, -1.0F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        ModelPartData body_mid = modelPartData.addChild("body_mid", ModelPartBuilder.create().uv(12, 13).cuboid(-3.0F, -8.0F, -4.0F, 6.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(12, 13).cuboid(-3.0F, -8.0F, -8.0F, 6.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 8.0F));

        ModelPartData bone = body_mid.addChild("bone", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(2, 10).cuboid(-2.0F, -3.0F, -3.0F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.0F, -3.0F));

        ModelPartData nose = modelPartData.addChild("nose", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.0F, -6.0F));

        ModelPartData fin_back = modelPartData.addChild("fin_back", ModelPartBuilder.create().uv(0, -4).cuboid(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 15.0F, -3.0F));

        ModelPartData tail_front = modelPartData.addChild("tail_front", ModelPartBuilder.create().uv(12, 13).cuboid(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(14, -3).cuboid(0.0F, -8.0F, -7.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.0F, 12.0F));

        ModelPartData tail_back = modelPartData.addChild("tail_back", ModelPartBuilder.create().uv(14, -4).cuboid(0.0F, -7.0F, 15.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 25).cuboid(-2.0F, -7.0F, 12.0F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    private void setHeadAngles(float headPitch, float headYaw) {

        headYaw = MathHelper.clamp(headYaw, -30F, 30F);
        headPitch = MathHelper.clamp(headPitch, -45F, 50F);

        this.head.yaw = headYaw * ((float)Math.PI / 180F);
        this.head.pitch = headPitch * ((float)Math.PI / 180F);
    }


    public void setAngles(LivingEntityRenderState livingEntityRenderState) {
        super.setAngles(livingEntityRenderState);
        float f = livingEntityRenderState.touchingWater ? 1.0F : 1.5F;
        this.tail_front.yaw = this.tail_back.yaw = -f * 0.06F * MathHelper.sin(0.6F * livingEntityRenderState.age);
    }

}
