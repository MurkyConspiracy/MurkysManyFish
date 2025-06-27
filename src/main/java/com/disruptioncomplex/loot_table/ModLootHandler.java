package com.disruptioncomplex.loot_table;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.item.ModItemHandler;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.ArrayList;
import java.util.List;

public class ModLootHandler {

    //public static final Identifier CRAP_TRAP_LOOT = Identifier.of(MurkysManyFish.MOD_ID, "fishing/crab_trap_loot");
    public static void registerLoot() {

       MurkysManyFish.LOGGER.info("Registering loot for: {}", MurkysManyFish.MOD_ID);

       //Modify existing Fishing Loot Tables
       LootTableEvents.MODIFY.register((registryKey, builder, lootTableSource, wrapperLookup) -> {
            // Check if this is the "fishing fish" loot table
            if (lootTableSource.isBuiltin() && registryKey.equals(LootTables.FISHING_FISH_GAMEPLAY)) {
                // Add Fishing items to the loot table
                builder.modifyPools(
                        pool -> pool
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.ANGELFISH).weight(10).conditionally(createLocationCheckLootCondition(
                                        wrapperLookup,
                                        BiomeKeys.WARM_OCEAN,
                                        BiomeKeys.BEACH,
                                        BiomeKeys.LUKEWARM_OCEAN)))
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.BETTA).weight(5).conditionally(createLocationCheckLootCondition(
                                        wrapperLookup,
                                        BiomeKeys.SWAMP,
                                        BiomeKeys.MANGROVE_SWAMP,
                                        BiomeKeys.RIVER)))
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.MACKEREL).weight(25).conditionally(createLocationCheckLootCondition(
                                        wrapperLookup,
                                        BiomeKeys.WARM_OCEAN,
                                        BiomeKeys.BEACH,
                                        BiomeKeys.LUKEWARM_OCEAN,
                                        BiomeKeys.DEEP_LUKEWARM_OCEAN,
                                        BiomeKeys.DEEP_OCEAN)))
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.STARFISH).weight(2).conditionally(createLocationCheckLootCondition(
                                        wrapperLookup,
                                        BiomeKeys.LUKEWARM_OCEAN,
                                        BiomeKeys.DEEP_LUKEWARM_OCEAN,
                                        BiomeKeys.DEEP_OCEAN)))
                                .with(net.minecraft.loot.entry.ItemEntry.builder(ModItemHandler.TUNA).weight(9).conditionally(createLocationCheckLootCondition(
                                        wrapperLookup,
                                        BiomeKeys.WARM_OCEAN,
                                        BiomeKeys.BEACH,
                                        BiomeKeys.LUKEWARM_OCEAN,
                                        BiomeKeys.DEEP_LUKEWARM_OCEAN,
                                        BiomeKeys.DEEP_OCEAN,
                                        BiomeKeys.COLD_OCEAN)))
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



    @SafeVarargs
    private static LootCondition.Builder createLocationCheckLootCondition(RegistryWrapper.WrapperLookup wrapperLookup, RegistryKey<Biome>... biomeKeys) {

        if (biomeKeys == null || biomeKeys.length == 0) {
            throw new IllegalArgumentException("biomeKeys must not be null or empty.");
        }


        List<RegistryEntry<Biome>> biomes = new ArrayList<>();

        for (RegistryKey<Biome> biomeKey : biomeKeys) {

            try {
                biomes.add(wrapperLookup.getEntryOrThrow(biomeKey));
            } catch (Exception e) {
                MurkysManyFish.LOGGER.error("Failed to retrieve registry entry for biome: {}", biomeKey, e);
            }
        }
        return LocationCheckLootCondition.builder(
                new LocationPredicate.Builder().biome(
                        RegistryEntryList.of(
                                biomes)));
    }


}