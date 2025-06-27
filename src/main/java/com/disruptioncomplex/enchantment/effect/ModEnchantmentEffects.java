package com.disruptioncomplex.enchantment.effect;

import com.disruptioncomplex.MurkysManyFish;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.EnchantmentLocationBasedEffect;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class ModEnchantmentEffects {
    // Method for registering EnchantmentLocationBasedEffect types
    private static <T extends EnchantmentLocationBasedEffect> MapCodec<T> registerLocationEffect(String name, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE,
                Identifier.of(MurkysManyFish.MOD_ID, name), codec);
    }


    // Method for registering EnchantmentValueEffect types
    private static <T extends EnchantmentValueEffect> MapCodec<T> registerValueEffect(String name, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_VALUE_EFFECT_TYPE,
                Identifier.of(MurkysManyFish.MOD_ID, name), codec);
    }

    // Method for registering EnchantmentEntityEffect types
    private static <T extends EnchantmentEntityEffect> MapCodec<T> registerEntityEffect(String name, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, name), codec);
    }

    public static void registerEnchantmentEffects() {
        MurkysManyFish.LOGGER.info("Registering Enchantment Effects for " + MurkysManyFish.MOD_ID);
        // The registration happens in the static field initialization above
    }
}