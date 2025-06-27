package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemBetta extends Item{

    public static final String ITEM_ID = "betta";

    public ItemBetta() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"betta")))
                        .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F).build())
        );

        MurkysManyFish.LOGGER.info("Building Betta Fish");
    }
}
