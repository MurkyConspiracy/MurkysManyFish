package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class FishingMagnetModel extends EntityModel<FishingMagenetRenderState> {
    public static final EntityModelLayer FISHING_MAGNET = new EntityModelLayer(Identifier.of(MurkysManyFish.MOD_ID, "fishing_magnet"), "main");

    public ModelPart body;

    public FishingMagnetModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body",
                ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-3.0F, -1.5F, -2.0F, 6.0F, 3.0F, 4.0F,
                                new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(FishingMagenetRenderState renderState) {
        // Add any animation logic here if needed
        // For example, you could make it slowly rotate:
        // this.body.yaw = renderState.age * 0.1F;
    }
}