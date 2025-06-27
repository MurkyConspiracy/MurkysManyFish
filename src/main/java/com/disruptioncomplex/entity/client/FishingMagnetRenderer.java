package com.disruptioncomplex.entity.client;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.data.FishingMagnetEntity;
import com.disruptioncomplex.item.ItemMagnetFishingRod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FishingMagnetRenderer extends EntityRenderer<FishingMagnetEntity, FishingMagenetRenderState> {

    private static final Identifier BG_TEXTURE = Identifier.of(MurkysManyFish.MOD_ID,"textures/entity/fishing_magnet.png");
    private static final RenderLayer BG_LAYER = RenderLayer.getEntityCutout(BG_TEXTURE);

    private final FishingMagnetModel model;

    public FishingMagnetRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new FishingMagnetModel(context.getPart(FishingMagnetModel.FISHING_MAGNET));
    }

    public boolean shouldRender(FishingMagnetEntity fishingMagnetEntity, Frustum frustum, double d, double e, double f) {
        return super.shouldRender(fishingMagnetEntity, frustum, d, e, f) && fishingMagnetEntity.getPlayerOwner() != null;
    }

    public void render(FishingMagenetRenderState fishingMagenetRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        matrixStack.push();
        
        // Scale the model if needed
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        
        // Optional: Add rotation based on entity's yaw/pitch if you want
        // matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-fishingMagenetRenderState.yaw));
        // matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(fishingMagenetRenderState.pitch));
        
        // Render the 3D model instead of billboard
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(BG_LAYER);
        this.model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        
        // Render fishing line while still inside the scaled matrix context
        float f = (float)fishingMagenetRenderState.pos.x;
        float g = (float)fishingMagenetRenderState.pos.y;
        float h = (float)fishingMagenetRenderState.pos.z;
        VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getLineStrip());
        MatrixStack.Entry entry2 = matrixStack.peek();

        // Adjust the starting point to connect to the top of the magnet model
        // Since your model is centered and 3 units high (-1.5 to +1.5), the top is at y = 1.5
        // But we need to account for the 0.5 scale, so the actual top is at y = 1.5 * 0.5 = 0.75
        float magnetTopY = 0.75F;

        for (int k = 0; k <= 16; k++) {
            renderFishingLine(f, g - magnetTopY, h, vertexConsumer2, entry2, percentage(k), percentage(k + 1));
        }

        matrixStack.pop();
        super.render(fishingMagenetRenderState, matrixStack, vertexConsumerProvider, light);
    }

    // Keep all your existing methods (getArmHoldingRod, getHandPos, etc.) unchanged
    public static Arm getArmHoldingRod(PlayerEntity player) {
        return player.getMainHandStack().getItem() instanceof ItemMagnetFishingRod ? player.getMainArm() : player.getMainArm().getOpposite();
    }

    private Vec3d getHandPos(PlayerEntity player, float f, float tickProgress) {
        int i = getArmHoldingRod(player) == Arm.RIGHT ? 1 : -1;
        if (this.dispatcher.gameOptions.getPerspective().isFirstPerson() && player == MinecraftClient.getInstance().player) {
            double m = 960.0 / this.dispatcher.gameOptions.getFov().getValue();
            Vec3d vec3d = this.dispatcher.camera.getProjection().getPosition(i * 0.525F, -0.1F).multiply(m).rotateY(f * 0.5F).rotateX(-f * 0.7F);
            return player.getCameraPosVec(tickProgress).add(vec3d);
        } else {
            float g = MathHelper.lerp(tickProgress, player.lastBodyYaw, player.bodyYaw) * (float) (Math.PI / 180.0);
            double d = MathHelper.sin(g);
            double e = MathHelper.cos(g);
            float h = player.getScale();
            double j = i * 0.35 * h;
            double k = 0.8 * h;
            float l = player.isInSneakingPose() ? -0.1875F : 0.0F;
            return player.getCameraPosVec(tickProgress).add(-e * j - d * k, l - 0.45 * h, -d * j + e * k);
        }
    }

    private static float percentage(int value) {
        return (float)value / 16;
    }

    private static void renderFishingLine(float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {
        float f = x * segmentStart;
        float g = y * (segmentStart * segmentStart + segmentStart) * 0.5F + 0.25F;
        float h = z * segmentStart;
        float i = x * segmentEnd - f;
        float j = y * (segmentEnd * segmentEnd + segmentEnd) * 0.5F + 0.25F - g;
        float k = z * segmentEnd - h;
        float l = MathHelper.sqrt(i * i + j * j + k * k);
        i /= l;
        j /= l;
        k /= l;
        buffer.vertex(matrices, f, g, h).color(Colors.BLACK).normal(matrices, i, j, k);
    }

    public FishingMagenetRenderState createRenderState() {
        return new FishingMagenetRenderState();
    }

    public void updateRenderState(FishingMagnetEntity fishingMagnetEntity, FishingMagenetRenderState fishingMagenetRenderState, float f) {
        super.updateRenderState(fishingMagnetEntity, fishingMagenetRenderState, f);
        PlayerEntity playerEntity = fishingMagnetEntity.getPlayerOwner();
        if (playerEntity == null) {
            fishingMagenetRenderState.pos = Vec3d.ZERO;
        } else {
            float g = playerEntity.getHandSwingProgress(f);
            float h = MathHelper.sin(MathHelper.sqrt(g) * (float) Math.PI);
            Vec3d vec3d = this.getHandPos(playerEntity, h, f);
            Vec3d vec3d2 = fishingMagnetEntity.getLerpedPos(f).add(0.0, 0.25, 0.0);
            fishingMagenetRenderState.pos = vec3d.subtract(vec3d2);
        }
    }
}