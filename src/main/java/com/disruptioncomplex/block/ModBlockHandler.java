package com.disruptioncomplex.block;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlockHandler {


    public static final Block CRAB_TRAP = registerBlock(CrabTrapBlock.class);
    public static final Block STARFISH = registerBlock(StarfishBlock.class);
    public static final Block CLAM = registerBlock(ClamBlock.class);
    public static final Block CLAM_DEAD = registerBlock(ClamBlockDead.class);
    public static final Block FISH_TANK = registerBlock(FishTankBlock.class);


    private static <T extends Block> T registerBlock( Class<T> blockClass) {
        T block;
        String name;
        AbstractBlock.Settings blockSettings;
        try {

            name = blockClass.getDeclaredField("BLOCK_ID").get(null).toString();
            blockSettings = (AbstractBlock.Settings) blockClass.getDeclaredField("BLOCK_SETTINGS").get(null);
            RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MurkysManyFish.MOD_ID, name));
            block = blockClass.getConstructor(AbstractBlock.Settings.class).newInstance(blockSettings.registryKey(key));
            registerBlockItem(name, block);
            return Registry.register(Registries.BLOCK, key, block);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate block of type " + blockClass, e);
        }

    }

    private static void registerBlockItem(String name, Block block) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MurkysManyFish.MOD_ID, name));
        BlockItem item = new BlockItem(block, new Item.Settings().registryKey(key));
        Registry.register(Registries.ITEM, key, item);
    }

    public static void registerBlocks() {
        MurkysManyFish.LOGGER.info("Registering Blocks for " + MurkysManyFish.MOD_ID);
        /*
        This is important as without this method the variable in the class are not yet created.
        Calling this method triggers the static variables to be defined and doing so triggers the blocks to be registered.
         */

    }

}
