package com.disruptioncomplex.entity;

import com.disruptioncomplex.MurkysManyFish;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

@SuppressWarnings("unused")
public class ModEntitySpawns {

    public static void addEntitySpawns() {

        // Angler spawn settings
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN),
                SpawnGroup.WATER_AMBIENT, ModEntityHandler.ANGLER, MurkysManyFish.CONFIG.AnglerSpawnWeight(), MurkysManyFish.CONFIG.AnglerMinGroupSize(), MurkysManyFish.CONFIG.AnglerMaxGroupSize());
        SpawnRestriction.register(ModEntityHandler.ANGLER, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn);

        // Anchovy spawn settings
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.RIVER, BiomeKeys.OCEAN),
                SpawnGroup.WATER_AMBIENT, ModEntityHandler.ANCHOVY, MurkysManyFish.CONFIG.AnchovySpawnWeight(), MurkysManyFish.CONFIG.AnchovyMinGroupSize(), MurkysManyFish.CONFIG.AnchovyMaxGroupSize());
        SpawnRestriction.register(ModEntityHandler.ANCHOVY, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn);

        // Angelfish spawn settings
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN, BiomeKeys.BEACH, BiomeKeys.LUKEWARM_OCEAN),
                SpawnGroup.WATER_AMBIENT, ModEntityHandler.ANGELFISH, MurkysManyFish.CONFIG.AngelfishSpawnWeight(), MurkysManyFish.CONFIG.AngelfishMinGroupSize(), MurkysManyFish.CONFIG.AngelfishMaxGroupSize());
        SpawnRestriction.register(ModEntityHandler.ANGELFISH, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn);

        // Mackerel spawn settings
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN, BiomeKeys.BEACH, BiomeKeys.LUKEWARM_OCEAN),
                SpawnGroup.WATER_AMBIENT, ModEntityHandler.MACKEREL, MurkysManyFish.CONFIG.MackerelSpawnWeight(), MurkysManyFish.CONFIG.MackerelMinGroupSize(), MurkysManyFish.CONFIG.MackerelMaxGroupSize());
        SpawnRestriction.register(ModEntityHandler.MACKEREL, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn);

        // Tuna spawn settings  
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN, BiomeKeys.BEACH, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.RIVER),
                SpawnGroup.WATER_AMBIENT, ModEntityHandler.TUNA, MurkysManyFish.CONFIG.TunaSpawnWeight(), MurkysManyFish.CONFIG.TunaMinGroupSize(), MurkysManyFish.CONFIG.TunaMaxGroupSize());
        SpawnRestriction.register(ModEntityHandler.TUNA, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn);

        // Betta spawn settings
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN, BiomeKeys.LUKEWARM_OCEAN),
                SpawnGroup.WATER_AMBIENT, ModEntityHandler.BETTA, MurkysManyFish.CONFIG.BettaSpawnWeight(), MurkysManyFish.CONFIG.BettaMinGroupSize(), MurkysManyFish.CONFIG.BettaMaxGroupSize());
        SpawnRestriction.register(ModEntityHandler.BETTA, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn);
    }

}
