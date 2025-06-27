package com.disruptioncomplex.world.gen;

import com.disruptioncomplex.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModPatchGeneration {

    public static void generateRandomPatches() {
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_BEACH), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DEAD_CLAM_PATCH_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                BiomeKeys.OCEAN,
                BiomeKeys.RIVER,
                BiomeKeys.DEEP_OCEAN,
                BiomeKeys.WARM_OCEAN,
                BiomeKeys.LUKEWARM_OCEAN,
                BiomeKeys.COLD_OCEAN,
                BiomeKeys.FROZEN_OCEAN,
                BiomeKeys.DEEP_LUKEWARM_OCEAN,
                BiomeKeys.DEEP_COLD_OCEAN,
                BiomeKeys.DEEP_FROZEN_OCEAN,
                BiomeKeys.FROZEN_RIVER
        ), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CLAM_PATCH_KEY);
    }
}
