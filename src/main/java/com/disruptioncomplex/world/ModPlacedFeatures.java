package com.disruptioncomplex.world;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {


    public static final RegistryKey<PlacedFeature> DEAD_CLAM_PATCH_KEY = registryKey("dead_clam_patch");
    public static final RegistryKey<PlacedFeature> CLAM_PATCH_KEY = registryKey("clam_patch");

    public static void bootstrap(Registerable<PlacedFeature> registerable) {
        MurkysManyFish.LOGGER.info("Registering Placed Features for " + MurkysManyFish.MOD_ID);
        var configuredFeature = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(registerable, DEAD_CLAM_PATCH_KEY, configuredFeature.getOrThrow(ModConfiguredFeatures.DEAD_CLAM_PATCH_KEY),
                RarityFilterPlacementModifier.of(MurkysManyFish.CONFIG.DeadClamSpawnChance()),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()

        );
        register(registerable, CLAM_PATCH_KEY, configuredFeature.getOrThrow(ModConfiguredFeatures.CLAM_PATCH_KEY),
                RarityFilterPlacementModifier.of(MurkysManyFish.CONFIG.ClamSpawnChance()),
                BiomePlacementModifier.of()

        );
    }


    public static RegistryKey<PlacedFeature> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MurkysManyFish.MOD_ID, name));
    }

    public static void register(Registerable<PlacedFeature> registerable,
                                RegistryKey<PlacedFeature> key,
                                RegistryEntry<ConfiguredFeature<?, ?>> configuredFeature,
                                List<PlacementModifier> modifiers) {

        registerable.register(key, new PlacedFeature(configuredFeature, List.copyOf(modifiers)));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> registerable,
                                                                                  RegistryKey<PlacedFeature> key,
                                                                                  RegistryEntry<ConfiguredFeature<?, ?>> configuredFeature,
                                                                                  PlacementModifier... modifiers) {
        register(registerable, key, configuredFeature, List.of(modifiers));

    }


}
