package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("unused")
public class AngelfishModel extends EntityModel<LivingEntityRenderState> {


    public static final EntityModelLayer ANGELFISH = new EntityModelLayer(Identifier.of(MurkysManyFish.MOD_ID, "angelfish"), "main");


    public ModelPart head;
    public ModelPart body;
    public ModelPart tail;
    public AngelfishModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(16, 5).cuboid(0.0F, -4.0F, -4.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(18, 18).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 18).cuboid(0.0F, -6.0F, -2.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(20, 0).cuboid(-1.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(20, 0).cuboid(1.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.0F, -2.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 8).cuboid(-1.0F, -7.0F, -1.0F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F))
                .uv(12, 8).cuboid(-1.0F, -8.0F, 2.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 0).cuboid(-1.0F, -10.0F, 4.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 18).cuboid(-1.0F, 0.0F, 4.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(12, 13).cuboid(-1.0F, -2.0F, 2.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -5.0F, 2.0F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 20.0F, -2.0F));

        ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(22, 5).cuboid(0.0F, -3.0F, 4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(22, 9).cuboid(0.0F, -15.0F, 4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(18, 22).cuboid(0.0F, -15.0F, 5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(22, 19).cuboid(0.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(14, 18).cuboid(0.0F, -10.0F, 5.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(22, 13).cuboid(0.0F, -11.0F, 6.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(22, 16).cuboid(0.0F, -6.0F, 6.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
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
        this.tail.yaw = -f * 0.25F * MathHelper.sin(0.3F * livingEntityRenderState.age);
    }

}

