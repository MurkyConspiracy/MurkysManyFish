package com.disruptioncomplex.gui;

import com.disruptioncomplex.MurkysManyFish;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CrabTrapScreen extends HandledScreen<CrabTrapScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MurkysManyFish.MOD_ID, "textures/gui/crab_trap_gui.png");

    public CrabTrapScreen(CrabTrapScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;  // Standard minecraft GUI width
        this.backgroundHeight = 166; // Adjust based on your texture
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        // Calculate the centered position
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(
                RenderLayer::getGuiTextured,
                TEXTURE,
                x, y,                     // screen position (where to draw)
                0, 0,                     // texture UV coordinates (where in texture to start)
                backgroundWidth,          // width to draw on screen
                backgroundHeight,         // height to draw on screen
                256, 256                  // total texture dimensions (typically 256x256 for MC GUIs)
        );

        // Adjust text position relative to the GUI

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

}