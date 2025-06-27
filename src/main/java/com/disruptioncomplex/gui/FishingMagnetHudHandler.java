package com.disruptioncomplex.gui;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.MurkysManyFishClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class FishingMagnetHudHandler {

    // Define the texture identifier for your image
    private static final Identifier FISHING_MAGNET_GUI_TEXTURE = Identifier.of(MurkysManyFish.MOD_ID, "textures/gui/magnet_fishing_gui.png");
    // Add water texture identifier
    private static final Identifier WATER_TEXTURE = Identifier.of("minecraft", "textures/block/water_still.png");
    // Add Magnet texture identifier
    private static final Identifier MAGNET_ICON_TEXTURE = Identifier.of(MurkysManyFish.MOD_ID, "textures/gui/magnet_icon.png");

    public static void updateFishingHud(DrawContext context) {

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        // Define image dimensions
        int imageWidth = 32;
        int imageHeight = 128;

        // Position to the right of a screen center
        int x = screenWidth / 2 + 20; // 20 pixels right of center
        int y = (screenHeight - imageHeight) / 2; // Vertically centered

        try {
            
            // Then draw the fishing magnet UI image on top
            context.drawTexture(
                    RenderLayer::getGuiTextured,
                    FISHING_MAGNET_GUI_TEXTURE,
                    x, y,
                    0, 0,
                    imageWidth, imageHeight,
                    imageWidth, imageHeight
            );
            // First, draw the water fill with gradient effect
            drawWaterFill(context, x + 9, y + 9, imageWidth - 18, imageHeight - 18);
            drawMagnetIcon(context, x, y, imageWidth, imageHeight);
        } catch (Exception e) {
            MurkysManyFish.LOGGER.error("Error drawing texture: " + e.getMessage());

            // Fallback: Draw a simple colored rectangle to test if HUD rendering works at all
            context.fill(x, y, x + imageWidth, y + imageHeight, 0xFFFF0000); // Red rectangle
            MurkysManyFish.LOGGER.info("Drew fallback red rectangle");
        }
        
    }

    private static void drawMagnetIcon(DrawContext context, int x, int y, int imageWidth, int imageHeight) {

        // Get depth percentage (0-100) and convert to decimal (0.0-1.0)
        float depthPercentage = MurkysManyFishClient.MAGNET_DEPTH / 100;

        // Calculate the available height range for the magnet
        int heightRange = imageHeight - 20; // Subtract padding

        // Define minimum Y position (top padding)
        int minY = y + 10;

        // Calculate final magnet position
        float magnetY = minY + (depthPercentage * heightRange);

        // Draw the magnet icon
        context.drawTexture(
                RenderLayer::getGuiTextured,
                MAGNET_ICON_TEXTURE,
                x + imageWidth / 2 - 4, (int) magnetY,
                0, 0,
                8, 8,
                8, 8
        );

        /*
        // Draw bubble particles under the magnet (only if it's moving/has depth)
        if (depthPercentage > 0.01f) {
            drawBubbleParticles(context, x + imageWidth / 2, (int) magnetY + 8, depthPercentage);
        }
         */
    }

    @SuppressWarnings("unused")
    private static void drawBubbleParticles(DrawContext context, int centerX, int startY, float depthPercentage) {
        long currentTime = System.currentTimeMillis();
        
        // Number of potential bubbles based on depth
        int maxBubbles = Math.min(12, (int) (depthPercentage * 15));
        
        for (int i = 0; i < maxBubbles; i++) {
            // Create unique timing for each bubble slot
            float bubblePhase = (currentTime / 800.0f) + (i * 0.7f);
            
            // Pop in/out based on sine wave - bubbles appear and disappear
            float popCycle = (float) Math.sin(bubblePhase);
            if (popCycle < 0.3f) continue; // Skip drawing this bubble if it's "popped out"
            
            // Random position around the magnet
            float randomSeed = i * 12.34f; // Consistent per bubble index
            float timeVariation = (currentTime / 1200.0f) + randomSeed;
            
            // Random offset from center (within 12 pixel radius)
            float offsetX = (float) Math.sin(timeVariation * 1.3f) * 12.0f;
            float offsetY = (float) Math.cos(timeVariation * 0.9f) * 8.0f;
            
            // Add some vertical drift
            float bubbleY = startY + offsetY + (i * 1.5f) + (float) Math.sin(timeVariation * 0.5f) * 3.0f;
            float bubbleX = centerX + offsetX;
            
            // Bubble size varies with pop cycle and individual variation
            float sizeMultiplier = popCycle * (0.7f + 0.3f * (float) Math.sin(timeVariation * 2.1f));
            int bubbleSize = Math.max(2, (int) (6 * sizeMultiplier));
            
            // Alpha based on pop cycle
            float alpha = popCycle * 0.8f;
            int alphaInt = (int) (alpha * 255);
            
            // Draw bubble as simple colored circles (more reliable than texture)
            // Outer bubble (light blue with transparency)
            int bubbleColor = (alphaInt << 24) | 0x87CEEB; // Sky blue with alpha
            context.fill(
                (int) bubbleX - bubbleSize / 2,
                (int) bubbleY - bubbleSize / 2,
                (int) bubbleX + bubbleSize / 2,
                (int) bubbleY + bubbleSize / 2,
                bubbleColor
            );
            
            // Inner highlight (white center for bubble effect)
            if (bubbleSize > 2) {
                int centerSize = Math.max(1, bubbleSize / 3);
                int centerAlpha = (int) (alpha * 200);
                int centerColor = (centerAlpha << 24) | 0xFFFFFF; // White center
                context.fill(
                    (int) bubbleX - centerSize / 2,
                    (int) bubbleY - centerSize / 2,
                    (int) bubbleX + centerSize / 2,
                    (int) bubbleY + centerSize / 2,
                    centerColor
                );
            }
            
            // Rim highlight (slightly darker blue for depth)
            if (bubbleSize > 3) {
                int rimAlpha = (int) (alpha * 150);
                int rimColor = (rimAlpha << 24) | 0x4682B4; // Steel blue rim
                
                // Top rim
                context.fill(
                    (int) bubbleX - bubbleSize / 2,
                    (int) bubbleY - bubbleSize / 2,
                    (int) bubbleX + bubbleSize / 2,
                    (int) bubbleY - bubbleSize / 2 + 1,
                    rimColor
                );
                
                // Left rim
                context.fill(
                    (int) bubbleX - bubbleSize / 2,
                    (int) bubbleY - bubbleSize / 2,
                    (int) bubbleX - bubbleSize / 2 + 1,
                    (int) bubbleY + bubbleSize / 2,
                    rimColor
                );
            }
        }
    }

    private static void drawWaterFill(DrawContext context, int x, int y, int width, int height) {
        // Get current game time for animation
        long currentTime = System.currentTimeMillis();
        
        // Animation settings
        int animationSpeed = 200; // Milliseconds per frame
        int totalFrames = 32; // Minecraft water has 32 animation frames
        int currentFrame = (int) ((currentTime / animationSpeed) % totalFrames);
        
        // First, draw the properly tiled animated water texture
        int tilesX = (width + 15) / 16;
        int tilesY = (height + 15) / 16;
        
        for (int tileY = 0; tileY < tilesY; tileY++) {
            for (int tileX = 0; tileX < tilesX; tileX++) {
                int tileStartX = x + (tileX * 16);
                int tileStartY = y + (tileY * 16);
                int tileWidth = Math.min(16, x + width - tileStartX);
                int tileHeight = Math.min(16, y + height - tileStartY);
                
                if (tileWidth > 0 && tileHeight > 0) {
                    // Add phase offset for flowing effect
                    int phaseOffset = (tileX + tileY * 2) % totalFrames;
                    int animatedFrame = (currentFrame + phaseOffset) % totalFrames;
                    
                    // Use animated texture coordinates
                    int textureU = 0;
                    int textureV = animatedFrame * 16; // Move down the atlas for each frame
                    
                    try {
                        context.drawTexture(
                            RenderLayer::getGuiTextured,
                            WATER_TEXTURE,
                            tileStartX, tileStartY,
                            textureU, textureV,
                            tileWidth, tileHeight,
                            16, 16 * totalFrames, // Proper atlas dimensions
                            0xFFFFFFFF // No tint for base texture
                        );
                    } catch (Exception e) {
                        // Base fallback color
                        context.fill(tileStartX, tileStartY, tileStartX + tileWidth, tileStartY + tileHeight, 0xFF4488CC);
                    }
                }
            }
        }
        
        // Then apply smooth depth gradient overlay
        for (int i = 0; i < height; i++) {
            int overlayColor = getOverlayColor(height, i, currentTime);

            // Draw single pixel high strip for smooth gradient
            context.fill(x, y + i, x + width, y + i + 1, overlayColor);
        }
        
        // Optional: Add subtle animated highlight strips for more water effect
        int highlightStrips = 8;
        for (int strip = 0; strip < highlightStrips; strip++) {
            float stripPosition = (float) strip / highlightStrips;
            int stripY = y + (int) (stripPosition * height);
            
            // Animate the highlight position
            float animationOffset = (float) Math.sin((currentTime / 2000.0) + (strip * 0.5)) * 0.1f;
            stripY += (int) (animationOffset * height);
            
            // Keep within bounds
            if (stripY >= y && stripY < y + height - 1) {
                float highlightAlpha = (float) Math.sin((currentTime / 1500.0) + (strip * 0.3)) * 0.3f + 0.4f;
                int alpha = (int) (highlightAlpha * 40);
                int highlightColor = (alpha << 24) | 0x6699FF; // Light blue highlight
                
                context.fill(x, stripY, x + width, stripY + 1, highlightColor);
            }
        }
    }

    private static int getOverlayColor(int height, int i, long currentTime) {
        float depthProgress = (float) i / height;

        // Use smooth sine curve for natural gradient
        float smoothProgress = (float) (Math.sin(depthProgress * Math.PI / 2));

        // Create depth darkening effect
        int baseAlpha = 60;  // Very subtle at top
        int maxAlpha = 180;  // Moderate darkening at bottom
        int alpha = (int) (baseAlpha + (maxAlpha - baseAlpha) * smoothProgress);

        // Add subtle animation to the overlay
        float waveEffect = (float) Math.sin((currentTime / 1000.0) + (i * 0.02)) * 0.1f;
        alpha = (int) Math.max(0, Math.min(255, alpha + waveEffect * 20));

        // Dark blue overlay for depth
        // Dark blue
        return (alpha << 24) | 0x002244;
    }
}