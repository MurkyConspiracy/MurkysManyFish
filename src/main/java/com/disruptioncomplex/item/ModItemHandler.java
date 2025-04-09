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
    public static final Item MACKEREL_BUCKET = registerItem(ItemMackerelBucket.ITEM_ID, new ItemMackerelBucket());
    public static final Item STARFISH = registerItem(ItemStarfish.ITEM_ID, new ItemStarfish());
    public static final Item TUNA = registerItem(ItemTuna.ITEM_ID, new ItemTuna());
    public static final Item TUNA_SPAWN_EGG = registerItem(ItemTunaSpawnEgg.ITEM_ID, new ItemTunaSpawnEgg(ModEntityHandler.TUNA));
    public static final Item TUNA_BUCKET = registerItem(ItemTunaBucket.ITEM_ID, new ItemTunaBucket());

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
        /*
        This is important as without this method the variable in the class are not yet created.
        Calling this method triggers the static variables to be defined and doing so triggers the blocks to be registered.
         */

    }

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(MurkysManyFish.MOD_ID, name), item);
    }

}
