package com.disruptioncomplex.loottable;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.item.ModItemHandler;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootTables;

public class ModLootHandler {

    public static void registerLoot() {

       MurkysManyFish.LOGGER.info("Registering loot for: {}", MurkysManyFish.MOD_ID);

        LootTableEvents.MODIFY.register((registryKey, builder, lootTableSource, wrapperLookup) -> {
            // Check if this is the "fishing fish" loot table
            if (lootTableSource.isBuiltin() && registryKey.equals(LootTables.FISHING_FISH_GAMEPLAY)) {

                // Add Tuna item to the loot table
                builder.modifyPools(
                        pool -> pool
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.ANGELFISH).weight(10))
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.MACKEREL).weight(25))
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.STARFISH).weight(2))
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.TUNA).weight(9))
                );

                MurkysManyFish.LOGGER.info("{} Loot table modified: {}", MurkysManyFish.MOD_ID,registryKey.getValue());
            }

            if (lootTableSource.isBuiltin() && registryKey.equals(LootTables.FISHING_JUNK_GAMEPLAY)) {

                // Add Tuna item to the loot table
                builder.modifyPools(
                        pool -> pool
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.TRASH_PILE).weight(25))
                );

                MurkysManyFish.LOGGER.info("{} Loot table modified: {}", MurkysManyFish.MOD_ID,registryKey.getValue());
            }
        });


    }
}