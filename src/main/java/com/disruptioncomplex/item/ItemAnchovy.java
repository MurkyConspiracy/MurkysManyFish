package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemAnchovy extends Item{

    public static final String ITEM_ID = "anchovy";

    public ItemAnchovy() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"anchovy")))
                        .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.1F).build())
        );

        MurkysManyFish.LOGGER.info("Building Anchovy");
    }
}
