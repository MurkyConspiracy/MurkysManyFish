package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemTunaCooked extends Item {

    public static final String ITEM_ID = "tuna_cooked";

    public ItemTunaCooked() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MurkysManyFish.MOD_ID, "tuna_cooked")))
                        .food(new FoodComponent.Builder().nutrition(8).saturationModifier(1.0F).build())
        );

        MurkysManyFish.LOGGER.info("Building Cooked Tuna");
    }
}