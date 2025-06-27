package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
@SuppressWarnings("unused")
public class BambooSpearEntityModel extends Model {


    public static final EntityModelLayer BAMBOO_SPEAR = new EntityModelLayer(Identifier.of(MurkysManyFish.MOD_ID, "bamboo_spear"), "main");
    public final ModelPart bone;
    public BambooSpearEntityModel(ModelPart root) {
        super(root, RenderLayer::getEntitySolid);
        this.bone = root.getChild("bone");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create()
                .uv(0, 0).cuboid(0.0F, -18.0F, 0.0F, 1.0F, 18.0F, 2.0F, new Dilation(0.0F))
                .uv(6, 0).cuboid(-1.0F, -18.0F, 0.0F, 1.0F, 18.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 0).cuboid(0.0F, -20.0F, 0.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(10, 4).cuboid(-1.0F, -20.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 7).cuboid(0.0F, -21.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

}