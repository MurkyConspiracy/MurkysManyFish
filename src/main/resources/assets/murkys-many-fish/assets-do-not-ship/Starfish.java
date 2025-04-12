// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class Starfish extends EntityModel<Entity> {
	private final ModelPart arm_middle;
	private final ModelPart arm_top_left;
	private final ModelPart arm_bottom_left;
	private final ModelPart arm_bottom_right;
	private final ModelPart arm_top_right;
	private final ModelPart base;
	public Starfish(ModelPart root) {
		this.arm_middle = root.getChild("arm_middle");
		this.arm_top_left = root.getChild("arm_top_left");
		this.arm_bottom_left = root.getChild("arm_bottom_left");
		this.arm_bottom_right = root.getChild("arm_bottom_right");
		this.arm_top_right = root.getChild("arm_top_right");
		this.base = root.getChild("base");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData arm_middle = modelPartData.addChild("arm_middle", ModelPartBuilder.create().uv(-2, 5).cuboid(-6.0F, 0.0F, -1.0F, 7.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 24.0F, 0.0F));

		ModelPartData arm_top_left = modelPartData.addChild("arm_top_left", ModelPartBuilder.create().uv(-2, 5).cuboid(-7.0F, 0.0F, -1.0F, 7.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		ModelPartData arm_bottom_left = modelPartData.addChild("arm_bottom_left", ModelPartBuilder.create().uv(-2, 5).cuboid(-7.0F, 0.0F, -1.0F, 7.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, -2.2689F, 0.0F));

		ModelPartData arm_bottom_right = modelPartData.addChild("arm_bottom_right", ModelPartBuilder.create().uv(-2, 5).cuboid(-7.0F, 0.0F, -1.0F, 7.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

		ModelPartData arm_top_right = modelPartData.addChild("arm_top_right", ModelPartBuilder.create().uv(-2, 5).cuboid(-7.0F, 0.0F, -1.0F, 7.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 7).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 16, 10);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		arm_middle.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		arm_top_left.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		arm_bottom_left.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		arm_bottom_right.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		arm_top_right.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}