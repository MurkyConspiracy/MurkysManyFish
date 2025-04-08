package com.disruptioncomplex;

import com.disruptioncomplex.block.ModBlockHandler;
import com.disruptioncomplex.entity.ModEntityHandler;
import com.disruptioncomplex.entity.client.MackerelModel;
import com.disruptioncomplex.entity.client.MackerelRenderer;
import com.disruptioncomplex.entity.client.TunaModel;
import com.disruptioncomplex.entity.client.TunaRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@SuppressWarnings("unused")
public class MurkysManyFishClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlockHandler.CRAB_TRAP, RenderLayer.getTranslucent());


        EntityModelLayerRegistry.registerModelLayer(MackerelModel.MACKEREL, MackerelModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.MACKEREL, (context) -> new MackerelRenderer(context, new MackerelModel(context.getPart(MackerelModel.MACKEREL)), 0.1f));


        EntityModelLayerRegistry.registerModelLayer(TunaModel.TUNA, TunaModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntityHandler.TUNA, (context) -> new TunaRenderer(context, new TunaModel(context.getPart(TunaModel.TUNA)), 0.1f));
    }


}
