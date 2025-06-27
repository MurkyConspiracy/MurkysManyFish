package com.disruptioncomplex;

import com.disruptioncomplex.block.ModBlockHandler;
import com.disruptioncomplex.entity.ModEntityHandler;
import com.disruptioncomplex.entity.client.*;
import com.disruptioncomplex.entity.data.FishingMagnetEntity;
import com.disruptioncomplex.gui.CrabTrapScreen;
import com.disruptioncomplex.gui.FishingMagnetHudHandler;
import com.disruptioncomplex.gui.FishTankScreen;
import com.disruptioncomplex.gui.ModScreenHandlers;
import com.disruptioncomplex.item.ModItemHandler;
import com.disruptioncomplex.util.MagnetHookAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class MurkysManyFishClient implements ClientModInitializer {

    public static boolean IS_MAGNET_FISHING_ROD_ACTIVE = false;
    public static float MAGNET_DEPTH = 0;

    @Override
    public void onInitializeClient() {

        //Blocks
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlockHandler.CRAB_TRAP, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlockHandler.CLAM, RenderLayer.getTranslucent());

        //Non Fish Entites
        EntityModelLayerRegistry.registerModelLayer(BambooSpearEntityModel.BAMBOO_SPEAR, BambooSpearEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.BAMBOO_SPEAR, BambooSpearEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(FishingMagnetModel.FISHING_MAGNET, FishingMagnetModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.FISHING_MAGNET, FishingMagnetRenderer::new);

        // Small Fish
        EntityModelLayerRegistry.registerModelLayer(AnchovyModel.ANCHOVY, AnchovyModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.ANCHOVY, AnchovyRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(BettaModel.BETTA, BettaModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.BETTA, BettaRenderer::new);

        // Medium Fish
        EntityModelLayerRegistry.registerModelLayer(AngelfishModel.ANGELFISH, AngelfishModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.ANGELFISH, AngelfishRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(AnglerModel.ANGLER, AnglerModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.ANGLER, AnglerRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MackerelModel.MACKEREL, MackerelModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.MACKEREL, MackerelRenderer::new);

        // Large Fish
        EntityModelLayerRegistry.registerModelLayer(TunaModel.TUNA, TunaModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.TUNA, TunaRenderer::new);

        //Screens
        HandledScreens.register(ModScreenHandlers.CRAB_TRAP_SCREEN_HANDLER, CrabTrapScreen::new);
        HandledScreens.register(ModScreenHandlers.FISH_TANK_SCREEN_HANDLER, FishTankScreen::new);

        //GUI
        HudLayerRegistrationCallback.EVENT.register(layerDrawer -> layerDrawer.attachLayerAfter(IdentifiedLayer.CROSSHAIR, Identifier.of(MurkysManyFish.MOD_ID, "fish_game"), this::onHudRender));
    }

    private void onHudRender(DrawContext context, RenderTickCounter tickCounter) {

        
        // Don't render HUD if player is in any menu/screen
        if (MinecraftClient.getInstance().currentScreen != null) {
            return;
        }

        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            // Check if player is holding magnet fishing rod
            boolean holdingMagnetRod = player.getMainHandStack().isOf(ModItemHandler.MAGNET_FISHING_ROD) ||
                    player.getOffHandStack().isOf(ModItemHandler.MAGNET_FISHING_ROD);


            if (holdingMagnetRod) {
                // Look for active fishing magnet entity owned by this player
                FishingMagnetEntity magnetEntity = ((MagnetHookAccessor) player).getMagnetHook();

                // Temporarily remove some conditions to test basic display
                if (magnetEntity != null && magnetEntity.getOwner() == player) {
                    // Set some test values to force display
                    IS_MAGNET_FISHING_ROD_ACTIVE = true;
                    //MAGNET_DEPTH = 50; // Test value
                    
                    FishingMagnetHudHandler.updateFishingHud(context);
                } else {
                    IS_MAGNET_FISHING_ROD_ACTIVE = false;
                    MAGNET_DEPTH = 0;
                }
            } else {
                IS_MAGNET_FISHING_ROD_ACTIVE = false;
                MAGNET_DEPTH = 0;
            }
        }
    }
}