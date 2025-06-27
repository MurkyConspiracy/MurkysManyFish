package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemAnchovyCooked extends Item {

    public static final String ITEM_ID = "anchovy_cooked";

    public ItemAnchovyCooked() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MurkysManyFish.MOD_ID, "anchovy_cooked")))
                        .food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F).build())
        );

        MurkysManyFish.LOGGER.info("Building Cooked Anchovy");
    }
}