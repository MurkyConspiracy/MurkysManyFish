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
                    .displayName(Text.translatable("itemgoup.murkys-many-fish.item_group"))
                    .entries((displayContext, entries) -> {
                        //Non fish items
                        entries.add(ModItemHandler.TRASH_PILE);

                        //Fish items
                        entries.add(ModItemHandler.ANGELFISH);
                        entries.add(ModItemHandler.ANGELFISH_COOKED);
                        entries.add(ModItemHandler.MACKEREL);
                        entries.add(ModItemHandler.MACKEREL_COOKED);
                        entries.add(ModItemHandler.MACKEREL_SPAWN_EGG);
                        entries.add(ModItemHandler.MACKEREL_BUCKET);
                        entries.add(ModItemHandler.STARFISH);
                        entries.add(ModItemHandler.TUNA);
                        entries.add(ModItemHandler.TUNA_COOKED);
                        entries.add(ModItemHandler.TUNA_SPAWN_EGG);
                        entries.add(ModItemHandler.TUNA_BUCKET);


                        //Functional
                        entries.add(ModBlockHandler.CRAB_TRAP);
                        entries.add(ModBlockHandler.STARFISH);
                    }).build());



    public static void registerItemGroups() {
        MurkysManyFish.LOGGER.info("Registering Item Groups for " + MurkysManyFish.MOD_ID);
        /*
        This is important as without this method the variable in the class are not yet created.
        Calling this method triggers the static variables to be defined and doing so triggers the blocks to be registered.
         */
    }
}
