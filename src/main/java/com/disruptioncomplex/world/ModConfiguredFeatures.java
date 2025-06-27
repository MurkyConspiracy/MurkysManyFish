package com.disruptioncomplex.world;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.block.ModBlockHandler;
import com.disruptioncomplex.world.gen.feature.ModFeatureHandler;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> DEAD_CLAM_PATCH_KEY = registryKey("dead_clam_patch");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CLAM_PATCH_KEY = registryKey("clam_patch");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> registerable) {
        MurkysManyFish.LOGGER.info("Registering Configured Features for " + MurkysManyFish.MOD_ID);
        register(registerable,
                DEAD_CLAM_PATCH_KEY,
                ModFeatureHandler.DEAD_CLAM_PATCH_FEATURE,
                ConfiguredFeatures.createRandomPatchFeatureConfig(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlockHandler.CLAM_DEAD.getDefaultState())),
                        List.of(Blocks.SAND)
                ));


        register(registerable, CLAM_PATCH_KEY, ModFeatureHandler.CLAM_PATCH_FEATURE, new CountConfig(20));


    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registryKey(String name)
    {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MurkysManyFish.MOD_ID, name));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> registerable,
                                                                                  RegistryKey<ConfiguredFeature<?, ?>> key,
                                                                                  F feature,
                                                                                  FC featureConfig) {
        registerable.register(key, new ConfiguredFeature<>(feature, featureConfig));
    }

}
