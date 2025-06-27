package com.disruptioncomplex;

import com.disruptioncomplex.datagen.ModEnchantmentTagProvider;
import com.disruptioncomplex.datagen.ModRegistryDataGenerator;
import com.disruptioncomplex.enchantment.ModEnchantments;
import com.disruptioncomplex.datagen.FishingLootTableProvider;
import com.disruptioncomplex.world.ModConfiguredFeatures;
import com.disruptioncomplex.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

@SuppressWarnings("unused")
public class MurkysManyFishDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        // Register providers in order - registry data first, then tags
        pack.addProvider(ModRegistryDataGenerator::new);
        pack.addProvider(FishingLootTableProvider::new);
        // Tag provider should come after registry data generation
        pack.addProvider(ModEnchantmentTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        // Ensure enchantments are registered first before other features
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, ModEnchantments::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
}