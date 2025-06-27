package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemTuna extends Item{

    public static final String ITEM_ID = "tuna";

    public ItemTuna() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"tuna")))
                        .food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.1F).build())
        );

        MurkysManyFish.LOGGER.info("Building Tuna");
    }
}
