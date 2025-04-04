package com.disruptioncomplex;

import com.disruptioncomplex.block.ModBlockHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MurkysManyFishClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlockHandler.CRAB_TRAP, RenderLayer.getTranslucent());
    }


}
