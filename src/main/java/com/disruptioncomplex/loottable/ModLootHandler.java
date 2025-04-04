package com.disruptioncomplex.loottable;

import com.disruptioncomplex.MurkysManyFish;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootTables;

public class ModLootHandler {

    public static void registerLoot() {

       MurkysManyFish.LOGGER.info("Registering loot for " + MurkysManyFish.MOD_ID);

        LootTableEvents.MODIFY.register((registryKey, builder, lootTableSource, wrapperLookup) -> {
            // Check if this is the "fishing fish" loot table
            if (lootTableSource.isBuiltin() && registryKey.equals(LootTables.FISHING_FISH_GAMEPLAY)) {


            }
        });

       }
}