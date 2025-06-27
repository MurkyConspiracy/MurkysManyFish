package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("unused")
public class MackerelModel extends EntityModel<LivingEntityRenderState> {


    public static final EntityModelLayer MACKEREL = new EntityModelLayer(Identifier.of(MurkysManyFish.MOD_ID, "mackerel"), "main");


    public ModelPart body;
    public ModelPart head;
    public ModelPart tailFin;

    public MackerelModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.tailFin = root.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create()
                        .uv(1, 1)
                        .cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 22.0F, 0.0F));

        ModelPartData right_fin = body.addChild("right_fin", ModelPartBuilder.create()
                        .uv(24, 1)
                        .cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        ModelPartData left_fin = body.addChild("left_fin", ModelPartBuilder.create()
                        .uv(24, 4)
                        .cuboid(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                        .uv(11, 0)
                        .cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 22.0F, 0.0F));

        ModelPartData nose = modelPartData.addChild("nose", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 22.0F, -3.0F));

        ModelPartData fin_left = modelPartData.addChild("fin_left", ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData fin_right = modelPartData.addChild("fin_right", ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData fin_back = modelPartData.addChild("fin_back", ModelPartBuilder.create()
                        .uv(20, -6)
                        .cuboid(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 20.0F, 0.0F));

        ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create()
                        .uv(22, 3)
                        .cuboid(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 22.0F, 7.0F));

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
        this.tailFin.yaw = -f * 0.45F * MathHelper.sin(0.6F * livingEntityRenderState.age);
    }

}
