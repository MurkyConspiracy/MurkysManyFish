package com.disruptioncomplex.entity.data;

import com.disruptioncomplex.MurkysManyFish;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.util.Identifier;

public class ModTrackedDataHandlers {
    
    public static final TrackedDataHandler<MagnetFishingData> MAGNET_FISHING_DATA_HANDLER = 
        TrackedDataHandler.create(MagnetFishingData.PACKET_CODEC);
    
    public static void register() {
        // Register your custom tracked data handler
        FabricTrackedDataRegistry.register(Identifier.of(MurkysManyFish.MOD_ID, "magnet_fishing_data"), MAGNET_FISHING_DATA_HANDLER);
    }
}