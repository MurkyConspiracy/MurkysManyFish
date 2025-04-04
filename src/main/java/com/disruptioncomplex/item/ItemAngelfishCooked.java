package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemAngelfishCooked extends Item{

    public static final String ITEM_ID = "angelfish_cooked";

    public ItemAngelfishCooked() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"angelfish_cooked")))
                        .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.8F).build())
        );

        MurkysManyFish.LOGGER.info("Building Cooked Angelfish");
    }
}
