package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemTrashPile extends Item{

    public static final String ITEM_ID = "trash_pile";

    public ItemTrashPile() {
        super(
                new Item.Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"trash_pile")))
                .maxCount(4)
        );

        MurkysManyFish.LOGGER.info("Building Trash Pile");
    }
}
