package com.disruptioncomplex.enchantment;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static final RegistryKey<Enchantment> SLIPPERY = of("slippery");
    public static final RegistryKey<Enchantment> HEFT = of("heft");

    private static RegistryKey<Enchantment> of(String name) {
        MurkysManyFish.LOGGER.info("Registering Enchantment: {}", name);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MurkysManyFish.MOD_ID, name));
    }

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var itemRegistry = registerable.getRegistryLookup(RegistryKeys.ITEM);

        registerable.register(SLIPPERY, Enchantment.builder(
                Enchantment.definition(
                        itemRegistry.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        itemRegistry.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        5,
                        3,
                        Enchantment.leveledCost(1, 10),
                        Enchantment.leveledCost(51, 10),
                        1,
                        AttributeModifierSlot.MAINHAND
                ))

                .build(Identifier.of(MurkysManyFish.MOD_ID, "slippery"))
        );

        // Register HEFT enchantment
        registerable.register(HEFT, Enchantment.builder(
                Enchantment.definition(
                        itemRegistry.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        itemRegistry.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        2, // weight (rare)
                        5, // max level
                        Enchantment.leveledCost(10, 20), // min cost
                        Enchantment.leveledCost(60, 20), // max cost
                        4, // anvil cost
                        AttributeModifierSlot.MAINHAND
                ))
                .build(Identifier.of(MurkysManyFish.MOD_ID, "heft"))
        );
    }
}