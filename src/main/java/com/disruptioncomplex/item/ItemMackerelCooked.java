package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemMackerelCooked extends Item{

    public static final String ITEM_ID = "mackerel_cooked";

    public ItemMackerelCooked() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"mackerel_cooked")))
                        .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.8F).build())

        );

        MurkysManyFish.LOGGER.info("Building Cooked Mackerel");
    }
}
