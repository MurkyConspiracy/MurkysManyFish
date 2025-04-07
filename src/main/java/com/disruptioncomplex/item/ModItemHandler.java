package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.ModEntityHandler;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModItemHandler {

    /*
    Raw Fish Items
     */
    public static final Item ANGELFISH = registerItem(ItemAngelfish.ITEM_ID, new ItemAngelfish());
    public static final Item MACKEREL = registerItem(ItemMackerel.ITEM_ID, new ItemMackerel());
    public static final Item MACKEREL_SPAWN_EGG = registerItem(ItemMackerelSpawnEgg.ITEM_ID, new ItemMackerelSpawnEgg(ModEntityHandler.MACKEREL));
    public static final Item STARFISH = registerItem(ItemStarfish.ITEM_ID, new ItemStarfish());
    public static final Item TUNA = registerItem(ItemTuna.ITEM_ID, new ItemTuna());

    /*
    Cooked Fish Items
     */
    public static final Item ANGELFISH_COOKED = registerItem(ItemAngelfishCooked.ITEM_ID, new ItemAngelfishCooked());
    public static final Item MACKEREL_COOKED = registerItem(ItemMackerelCooked.ITEM_ID, new ItemMackerelCooked());
    public static final Item TUNA_COOKED = registerItem(ItemTunaCooked.ITEM_ID, new ItemTunaCooked());

    /*
    Fishing Related Items
     */
    public static final Item TRASH_PILE = registerItem(ItemTrashPile.ITEM_ID, new ItemTrashPile());

    public static void registerModItems() {

        MurkysManyFish.LOGGER.info("Registering modded items for " + MurkysManyFish.MOD_ID);


    }

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(MurkysManyFish.MOD_ID, name), item);
    }

}
