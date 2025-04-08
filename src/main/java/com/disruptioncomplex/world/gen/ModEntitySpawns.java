package com.disruptioncomplex.world.gen;

import com.disruptioncomplex.entity.ModEntityHandler;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {

    public static void addEntitySpawns() {

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN,BiomeKeys.BEACH,BiomeKeys.LUKEWARM_OCEAN),
                SpawnGroup.WATER_AMBIENT, ModEntityHandler.MACKEREL, 5, 2, 6);


        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARM_OCEAN,BiomeKeys.BEACH,BiomeKeys.LUKEWARM_OCEAN,BiomeKeys.DEEP_LUKEWARM_OCEAN,BiomeKeys.DEEP_OCEAN, BiomeKeys.COLD_OCEAN),
                SpawnGroup.WATER_AMBIENT, ModEntityHandler.TUNA, 5, 2, 6);

        SpawnRestriction.register(ModEntityHandler.MACKEREL, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn);

        SpawnRestriction.register(ModEntityHandler.TUNA, SpawnLocationTypes.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn);

    }

}
