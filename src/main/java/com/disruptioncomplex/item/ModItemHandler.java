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
    public static final Item ANCHOVY = registerItem(ItemAnchovy.ITEM_ID, new ItemAnchovy());
    public static final Item ANCHOVY_SPAWN_EGG = registerItem(ItemAnchovySpawnEgg.ITEM_ID, new ItemAnchovySpawnEgg(ModEntityHandler.ANCHOVY));
    public static final Item ANCHOVY_BUCKET = registerItem(ItemAnchovyBucket.ITEM_ID, new ItemAnchovyBucket());
    public static final Item ANCHOVY_COOKED = registerItem(ItemAnchovyCooked.ITEM_ID, new ItemAnchovyCooked());
    public static final Item ANGELFISH = registerItem(ItemAngelfish.ITEM_ID, new ItemAngelfish());
    public static final Item ANGELFISH_SPAWN_EGG = registerItem(ItemAngelfishSpawnEgg.ITEM_ID, new ItemAngelfishSpawnEgg(ModEntityHandler.ANGELFISH));
    public static final Item ANGELFISH_BUCKET = registerItem(ItemAngelfishBucket.ITEM_ID, new ItemAngelfishBucket());
    public static final Item ANGLER = registerItem(ItemAngler.ITEM_ID, new ItemAngler());
    public static final Item ANGLER_SPAWN_EGG = registerItem(ItemAnglerSpawnEgg.ITEM_ID, new ItemAnglerSpawnEgg(ModEntityHandler.ANGLER));
    public static final Item ANGLER_BUCKET = registerItem(ItemAnglerBucket.ITEM_ID, new ItemAnglerBucket());
    public static final Item BLUEGILL = registerItem(ItemBluegill.ITEM_ID, new ItemBluegill());
    public static final Item BREAM = registerItem(ItemBream.ITEM_ID, new ItemBream());
    public static final Item CATFISH = registerItem(ItemCatfish.ITEM_ID, new ItemCatfish());
    public static final Item CENTIPEDE = registerItem(ItemCentipede.ITEM_ID, new ItemCentipede());
    public static final Item CHUB = registerItem(ItemChub.ITEM_ID, new ItemChub());
    public static final Item CLAM = registerItem(ItemClam.ITEM_ID, new ItemClam());
    public static final Item CLAM_DEAD = registerItem(ItemClamDead.ITEM_ID, new ItemClamDead());
    public static final Item CRAB = registerItem(ItemCrab.ITEM_ID, new ItemCrab());
    public static final Item CRAYFISH = registerItem(ItemCrayfish.ITEM_ID, new ItemCrayfish());
    public static final Item EARTHWORM = registerItem(ItemEarthworm.ITEM_ID, new ItemEarthworm());
    public static final Item EEL = registerItem(ItemEel.ITEM_ID, new ItemEel());
    public static final Item MACKEREL = registerItem(ItemMackerel.ITEM_ID, new ItemMackerel());
    public static final Item MACKEREL_SPAWN_EGG = registerItem(ItemMackerelSpawnEgg.ITEM_ID, new ItemMackerelSpawnEgg(ModEntityHandler.MACKEREL));
    public static final Item MACKEREL_BUCKET = registerItem(ItemMackerelBucket.ITEM_ID, new ItemMackerelBucket());
    public static final Item STARFISH = registerItem(ItemStarfish.ITEM_ID, new ItemStarfish());
    public static final Item TUNA = registerItem(ItemTuna.ITEM_ID, new ItemTuna());
    public static final Item TUNA_SPAWN_EGG = registerItem(ItemTunaSpawnEgg.ITEM_ID, new ItemTunaSpawnEgg(ModEntityHandler.TUNA));
    public static final Item TUNA_BUCKET = registerItem(ItemTunaBucket.ITEM_ID, new ItemTunaBucket());
    public static final Item BETTA = registerItem(ItemBetta.ITEM_ID, new ItemBetta());
    public static final Item BETTA_SPAWN_EGG = registerItem(ItemBettaSpawnEgg.ITEM_ID, new ItemBettaSpawnEgg(ModEntityHandler.BETTA));
    public static final Item BETTA_BUCKET = registerItem(ItemBettaBucket.ITEM_ID, new ItemBettaBucket());
    public static final Item CARP = registerItem(ItemCarp.ITEM_ID, new ItemCarp());
    public static final Item GOLDFISH = registerItem(ItemGoldfish.ITEM_ID, new ItemGoldfish());
    public static final Item KOI = registerItem(ItemKoi.ITEM_ID, new ItemKoi());


    /*
    Cooked Fish Items
     */
    public static final Item ANGELFISH_COOKED = registerItem(ItemAngelfishCooked.ITEM_ID, new ItemAngelfishCooked());
    public static final Item MACKEREL_COOKED = registerItem(ItemMackerelCooked.ITEM_ID, new ItemMackerelCooked());
    public static final Item TUNA_COOKED = registerItem(ItemTunaCooked.ITEM_ID, new ItemTunaCooked());

    /*
    Fishing Related Items
     */
    public static final Item BAMBOO_SPEAR = registerItem(ItemBambooSpear.ITEM_ID, new ItemBambooSpear());
    public static final Item TRASH_PILE = registerItem(ItemTrashPile.ITEM_ID, new ItemTrashPile());
    public static final Item CONCH_SHELL = registerItem(ItemConchShell.ITEM_ID, new ItemConchShell());
    public static final Item EMPTY_BOTTLE = registerItem(ItemEmptyBottle.ITEM_ID, new ItemEmptyBottle());
    public static final Item FISH_SCALES = registerItem(ItemFishScales.ITEM_ID, new ItemFishScales());
    public static final Item BAMBOO_SPEAR_LINE = registerItem(ItemBambooSpearLine.ITEM_ID, new ItemBambooSpearLine());
    public static final Item MAGNET_FISHING_ROD = registerItem(ItemMagnetFishingRod.ITEM_ID, new ItemMagnetFishingRod());


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
