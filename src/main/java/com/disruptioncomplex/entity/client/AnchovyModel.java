
package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("unused")
public class AnchovyModel extends EntityModel<AnchovyRenderState> {

    public static final EntityModelLayer ANCHOVY = new EntityModelLayer(Identifier.of(MurkysManyFish.MOD_ID, "anchovy"), "main");

    public ModelPart body;
    public ModelPart tail;
    
    public AnchovyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -4.0F, -3.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(8, 7).cuboid(0.0F, -5.0F, -2.0F, 0.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 8).cuboid(0.0F, -2.0F, -2.0F, 0.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(8, 11).cuboid(0.0F, -5.0F, 2.0F, 0.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 11).cuboid(0.0F, -3.0F, 3.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(0.0F, -5.0F, 3.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 25.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(AnchovyRenderState anchovyRenderState) {
        super.setAngles(anchovyRenderState);
        float f = anchovyRenderState.touchingWater ? 1.0F : 1.5F;
        // Faster tail movement for anchovy
        this.tail.yaw = -f * 0.3F * MathHelper.sin(0.4F * anchovyRenderState.age);
    }
}