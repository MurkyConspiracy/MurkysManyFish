package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("unused")
public class BettaModel extends EntityModel<BettaRenderState> {

    public static final EntityModelLayer BETTA = new EntityModelLayer(Identifier.of(MurkysManyFish.MOD_ID, "betta"), "main");

    public ModelPart body;
    public ModelPart head;
    public ModelPart tailFin;
    public ModelPart dorsalFin;
    public ModelPart backFin;

    public BettaModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.tailFin = root.getChild("tail");
        this.dorsalFin = root.getChild("fin_back");
        this.backFin = root.getChild("fin_bottom");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // Body - matches JSON coordinates exactly
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create()
                        // Main body cuboid with exact UV mapping
                        .uv(6, 0).cuboid(0.0F, -3.0F, 2.0F, 2.0F, 3.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 24.0F, 0.0F));

        // Right fin - using exact JSON rotation and UV mapping
        body.addChild("right_fin", ModelPartBuilder.create()
                        .uv(8, 4).cuboid(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(2.0F, -2.0F, 2.0F, 0.0F, 0.0F, -0.9F));

        // Left fin - using exact JSON rotation and UV mapping
        body.addChild("left_fin", ModelPartBuilder.create()
                        .uv(8, 6).cuboid(-1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -2.0F, 2.0F, 0.0F, 0.0F, 0.9F));

        // Head - matches JSON coordinates exactly with proper UV mapping
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                        .uv(2, 7).cuboid(0.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 24.0F, 0.0F));

        // Dorsal fin (fin_back) - matching JSON coordinates and UVs
        ModelPartData finBack = modelPartData.addChild("fin_back", ModelPartBuilder.create()
                        .uv(0, 5).cuboid(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.origin(1.0F, 21.5F, 0.0F));

        // Bottom fin - matching JSON coordinates and UVs
        ModelPartData finBottom = modelPartData.addChild("fin_bottom", ModelPartBuilder.create()
                        .uv(0, 3).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)),
                ModelTransform.origin(1.0F, 24.0F, 0.0F));

        // Tail fin - matching JSON coordinates and UVs
        ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(0.0F, -3.5F, 0.0F, 0.0F, 7.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.origin(1.0F, 23.5F, 7.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    private void setHeadAngles(float headPitch, float headYaw) {
        headYaw = MathHelper.clamp(headYaw, -30F, 30F);
        headPitch = MathHelper.clamp(headPitch, -45F, 50F);

        this.head.yaw = headYaw * ((float)Math.PI / 180F);
        this.head.pitch = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void setAngles(BettaRenderState bettaRenderState) {
        // Animate the fins with a gentle wave motion
        float swimSpeed = bettaRenderState.touchingWater ? 1.0F : 1.5F;

        // Tail has the primary movement
        this.tailFin.yaw = -swimSpeed * 0.45F * MathHelper.sin(0.6F * bettaRenderState.age);
        
        // Dorsal and back fins move slightly with a phase offset
        this.dorsalFin.roll = 0.1F * MathHelper.sin(0.3F * bettaRenderState.age + 1.0F);
        this.backFin.roll = -0.1F * MathHelper.sin(0.3F * bettaRenderState.age + 1.0F);
    }
}