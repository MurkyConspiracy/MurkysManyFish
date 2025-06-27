package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
@SuppressWarnings("unused")
public class AnglerModel extends EntityModel<LivingEntityRenderState> {

    public static final EntityModelLayer ANGLER = new EntityModelLayer(Identifier.of(MurkysManyFish.MOD_ID, "angler"), "main");


    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart tail;

    public AnglerModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -8.0F, -5.0F, 6.0F, 7.0F, 4.0F, new Dilation(0.0F))
                .uv(20, 0).cuboid(-4.0F, -5.0F, -7.0F, 1.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 34).cuboid(-4.0F, -7.0F, -5.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(14, 23).cuboid(3.0F, -5.0F, -7.0F, 1.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(34, 13).cuboid(3.0F, -7.0F, -5.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(18, 18).cuboid(-2.0F, -1.0F, -5.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(26, 38).cuboid(-3.0F, -1.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(38, 28).cuboid(2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(26, 23).cuboid(-2.0F, -2.0F, -8.0F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(34, 18).cuboid(2.0F, -2.0F, -8.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(34, 32).cuboid(-3.0F, -2.0F, -8.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(32, 4).cuboid(-1.0F, -3.0F, -8.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(12, 31).cuboid(-3.0F, -2.0F, -9.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 41).cuboid(-2.0F, -3.0F, -9.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(26, 41).cuboid(-1.0F, -3.0F, -9.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(28, 41).cuboid(0.0F, -3.0F, -9.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(30, 41).cuboid(1.0F, -3.0F, -9.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 42).cuboid(2.0F, -3.0F, -9.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(26, 28).cuboid(-2.0F, -8.0F, -7.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 39).cuboid(0.0F, -9.0F, -9.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(22, 41).cuboid(0.0F, -9.0F, -7.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 40).cuboid(0.0F, -11.0F, -9.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(36, 40).cuboid(0.0F, -12.0F, -10.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(14, 20).cuboid(0.0F, -12.0F, -12.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 22).cuboid(-3.0F, -7.0F, -6.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(40, 24).cuboid(2.0F, -7.0F, -6.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(19, 7).cuboid(-3.0F, -6.0F, -7.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-3.0F, -5.0F, -8.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(9, 35).cuboid(-3.0F, -4.0F, -8.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(38, 40).cuboid(-3.0F, -3.0F, -9.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(9, 39).cuboid(3.0F, -4.0F, -8.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(40, 40).cuboid(3.0F, -3.0F, -9.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 1.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 11).cuboid(-3.0F, -8.0F, -1.0F, 6.0F, 6.0F, 3.0F, new Dilation(0.0F))
                .uv(12, 33).cuboid(0.0F, -10.0F, -1.0F, 0.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(18, 11).cuboid(-3.0F, -8.0F, 2.0F, 6.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 1.0F));

        ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(0, 20).cuboid(-2.0F, -7.0F, 4.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(26, 32).cuboid(0.0F, -6.0F, 7.0F, 0.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(12, 39).cuboid(0.0F, -4.0F, 7.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 39).cuboid(0.0F, -7.0F, 7.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(20, 33).cuboid(0.0F, -8.0F, 7.0F, 0.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 1.0F));
        return TexturedModelData.of(modelData, 48, 48);
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