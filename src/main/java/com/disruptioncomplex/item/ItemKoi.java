package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemKoi extends Item{

    public static final String ITEM_ID = "koi";

    public ItemKoi() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"koi")))
                        .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F).build())
        );

        MurkysManyFish.LOGGER.info("Building Koi Fish");
    }
}
