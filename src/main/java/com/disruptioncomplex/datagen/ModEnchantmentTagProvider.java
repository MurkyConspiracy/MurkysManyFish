package com.disruptioncomplex.datagen;

import com.disruptioncomplex.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
    
    public ModEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // Register your enchantment as a curse
        getOrCreateTagBuilder(EnchantmentTags.CURSE)
                .add(ModEnchantments.SLIPPERY); // or whichever enchantment you want to be a curse

        getOrCreateTagBuilder(EnchantmentTags.TREASURE)
                .add(ModEnchantments.SLIPPERY); // or whichever enchantment you want to be a treasure
    }
}