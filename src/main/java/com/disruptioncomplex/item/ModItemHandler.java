package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModItemHandler {

    public static final Item TRASH_PILE = registerItem(ItemTrashPile.ITEM_ID, new ItemTrashPile());
    public static final Item STARFISH = registerItem(ItemStarfish.ITEM_ID, new ItemStarfish());
    public static final Item ANGELFISH = registerItem(ItemAngelfish.ITEM_ID, new ItemAngelfish());
    public static final Item MACKEREL = registerItem(ItemMackerel.ITEM_ID, new ItemMackerel());
    public static final Item TUNA = registerItem(ItemTuna.ITEM_ID, new ItemTuna());


    public static void registerModItems() {

        MurkysManyFish.LOGGER.info("Registering modded items for " + MurkysManyFish.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((content) -> {
            content.add(TRASH_PILE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((content) -> {
            content.add(STARFISH);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((content) -> {
            content.add(ANGELFISH);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((content) -> {
            content.add(MACKEREL);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((content) -> {
            content.add(TUNA);
        });

    }

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(MurkysManyFish.MOD_ID, name), item);
    }

}
