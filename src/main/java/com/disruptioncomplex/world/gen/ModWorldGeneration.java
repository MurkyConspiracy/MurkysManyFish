package com.disruptioncomplex.world.gen;

import com.disruptioncomplex.MurkysManyFish;

public class ModWorldGeneration {


    public static void generateModWorldGen() {
        MurkysManyFish.LOGGER.info("Registering Mod World Generation");
        ModPatchGeneration.generateRandomPatches();
    }
}
