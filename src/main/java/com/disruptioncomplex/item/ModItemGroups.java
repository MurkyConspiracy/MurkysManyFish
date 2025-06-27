package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.block.ModBlockHandler;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    @SuppressWarnings("unused")
    public static final ItemGroup MURKYS_MANY_FISH_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MurkysManyFish.MOD_ID, "murkys_many_fish_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItemHandler.TRASH_PILE))
                    .displayName(Text.translatable("itemgroup.murkys-many-fish.item_group"))
                    .entries((displayContext, entries) -> {
                        //Non-fish items
                        entries.add(ModItemHandler.TRASH_PILE);
                        entries.add(ModItemHandler.CONCH_SHELL);
                        entries.add(ModItemHandler.EMPTY_BOTTLE);
                        entries.add(ModItemHandler.FISH_SCALES);

                        //Fish items
                        entries.add(ModItemHandler.ANCHOVY);
                        entries.add(ModItemHandler.ANCHOVY_COOKED);
                        entries.add(ModItemHandler.ANCHOVY_SPAWN_EGG);
                        entries.add(ModItemHandler.ANCHOVY_BUCKET);
                        entries.add(ModItemHandler.ANGELFISH);
                        entries.add(ModItemHandler.ANGLER);
                        entries.add(ModItemHandler.ANGLER_SPAWN_EGG);
                        entries.add(ModItemHandler.ANGLER_BUCKET);
                        entries.add(ModItemHandler.ANGELFISH_COOKED);
                        entries.add(ModItemHandler.ANGELFISH_SPAWN_EGG);
                        entries.add(ModItemHandler.ANGELFISH_BUCKET);
                        entries.add(ModItemHandler.BLUEGILL);
                        entries.add(ModItemHandler.BREAM);
                        entries.add(ModItemHandler.CATFISH);
                        entries.add(ModItemHandler.CENTIPEDE);
                        entries.add(ModItemHandler.CHUB);
                        entries.add(ModItemHandler.CLAM);
                        entries.add(ModItemHandler.CLAM_DEAD);
                        entries.add(ModItemHandler.CRAB);
                        entries.add(ModItemHandler.CRAYFISH);
                        entries.add(ModItemHandler.EARTHWORM);
                        entries.add(ModItemHandler.EEL);
                        entries.add(ModItemHandler.MACKEREL);
                        entries.add(ModItemHandler.MACKEREL_COOKED);
                        entries.add(ModItemHandler.MACKEREL_SPAWN_EGG);
                        entries.add(ModItemHandler.MACKEREL_BUCKET);
                        entries.add(ModItemHandler.STARFISH);
                        entries.add(ModItemHandler.TUNA);
                        entries.add(ModItemHandler.TUNA_COOKED);
                        entries.add(ModItemHandler.TUNA_SPAWN_EGG);
                        entries.add(ModItemHandler.TUNA_BUCKET);
                        entries.add(ModItemHandler.BETTA);
                        entries.add(ModItemHandler.BETTA_SPAWN_EGG);
                        entries.add(ModItemHandler.BETTA_BUCKET);
                        entries.add(ModItemHandler.CARP);
                        entries.add(ModItemHandler.GOLDFISH);
                        entries.add(ModItemHandler.KOI);


                        //Functional
                        entries.add(ModBlockHandler.CRAB_TRAP);
                        entries.add(ModBlockHandler.FISH_TANK);
                        entries.add(ModBlockHandler.STARFISH);
                        entries.add(ModItemHandler.BAMBOO_SPEAR);
                        entries.add(ModItemHandler.MAGNET_FISHING_ROD);

                    }).build());

    public static void registerItemGroups() {
        MurkysManyFish.LOGGER.info("Registering Item Groups for " + MurkysManyFish.MOD_ID);

    }
}