package com.disruptioncomplex.world.gen.feature;


import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;

public class ModFeatureHandler{
    public static final Feature<RandomPatchFeatureConfig> DEAD_CLAM_PATCH_FEATURE = register("dead_clam_patch_feature", new DeadClamFeature(RandomPatchFeatureConfig.CODEC));
    public static final Feature<CountConfig> CLAM_PATCH_FEATURE = register("clam_patch_feature", new ClamFeature(CountConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, Identifier.of(MurkysManyFish.MOD_ID,name), feature);
    }

    public static void registerFeatures() {
        MurkysManyFish.LOGGER.info("Registering Features for " + MurkysManyFish.MOD_ID);
    }
}
