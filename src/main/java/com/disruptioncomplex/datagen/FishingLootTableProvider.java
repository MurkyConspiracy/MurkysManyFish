package com.disruptioncomplex.datagen;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.item.ModItemHandler;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FishingLootTableProvider extends SimpleFabricLootTableProvider {
    public FishingLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.FISHING);
    }

    // Crab Trap Loot Tables
    public static final RegistryKey<LootTable> CRAB_TRAP_GARBAGE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MurkysManyFish.MOD_ID, "crab_trap_garbage"));
    public static final RegistryKey<LootTable> CRAB_TRAP_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MurkysManyFish.MOD_ID, "crab_trap_loot"));

    // Magnet Fishing Loot Tables
    public static final RegistryKey<LootTable> MAGNET_FISHING_GARBAGE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MurkysManyFish.MOD_ID, "magnet_fishing_garbage"));
    public static final RegistryKey<LootTable> MAGNET_FISHING_TREASURE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MurkysManyFish.MOD_ID, "magnet_fishing_treasure"));
    public static final RegistryKey<LootTable> MAGNET_FISHING_ORE_LOW = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MurkysManyFish.MOD_ID, "magnet_fishing_ore_low"));
    public static final RegistryKey<LootTable> MAGNET_FISHING_ORE_MEDIUM = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MurkysManyFish.MOD_ID, "magnet_fishing_ore_medium"));
    public static final RegistryKey<LootTable> MAGNET_FISHING_ORE_HIGH = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MurkysManyFish.MOD_ID, "magnet_fishing_ore_high"));

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        // Crab Trap Loot Tables
        lootTableBiConsumer.accept(CRAB_TRAP_GARBAGE, LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(ModItemHandler.TRASH_PILE).weight(10))
                .with(ItemEntry.builder(ModItemHandler.EMPTY_BOTTLE).weight(10))
                .with(ItemEntry.builder(ModItemHandler.CONCH_SHELL).weight(1))
                .with(ItemEntry.builder(ModItemHandler.FISH_SCALES).weight(10))
                .with(ItemEntry.builder(ModItemHandler.EARTHWORM).weight(1))
                .with(ItemEntry.builder(ModItemHandler.CENTIPEDE).weight(5))
        ));
        lootTableBiConsumer.accept(CRAB_TRAP_LOOT, LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(ModItemHandler.CRAB))
                .with(ItemEntry.builder(ModItemHandler.CRAYFISH))
                .with(ItemEntry.builder(ModItemHandler.EARTHWORM))
                .with(ItemEntry.builder(ModItemHandler.CLAM))
        ));

        // Magnet Fishing Loot Tables
        lootTableBiConsumer.accept(MAGNET_FISHING_GARBAGE, LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(ModItemHandler.TRASH_PILE).weight(MurkysManyFish.CONFIG.TrashPileWeight()))
                .with(ItemEntry.builder(Items.TRIPWIRE_HOOK).weight(MurkysManyFish.CONFIG.TripwireHookWeight()))
                .with(ItemEntry.builder(Items.FLINT_AND_STEEL).weight(MurkysManyFish.CONFIG.FlintAndSteelWeight()))
                .with(ItemEntry.builder(Items.CHAIN).weight(MurkysManyFish.CONFIG.ChainWeight()))
                .with(ItemEntry.builder(Items.HEAVY_WEIGHTED_PRESSURE_PLATE).weight(MurkysManyFish.CONFIG.HeavyWeightedPressurePlateWeight()))
                .with(ItemEntry.builder(Items.IRON_TRAPDOOR).weight(MurkysManyFish.CONFIG.IronTrapdoorWeight()))
                .with(ItemEntry.builder(Items.RAIL).weight(MurkysManyFish.CONFIG.RailWeight()))
                .with(ItemEntry.builder(Items.ACTIVATOR_RAIL).weight(MurkysManyFish.CONFIG.ActivatorRailWeight()))
                .with(ItemEntry.builder(Items.DETECTOR_RAIL).weight(MurkysManyFish.CONFIG.DetectorRailWeight()))
                .with(ItemEntry.builder(Items.POWERED_RAIL).weight(MurkysManyFish.CONFIG.PoweredRailWeight()))
                .with(ItemEntry.builder(Items.IRON_DOOR).weight(MurkysManyFish.CONFIG.IronDoorWeight()))
                .with(ItemEntry.builder(Items.IRON_BARS).weight(MurkysManyFish.CONFIG.IronBarsWeight()))
                .with(ItemEntry.builder(Items.IRON_HORSE_ARMOR).weight(MurkysManyFish.CONFIG.IronHorseArmorWeight()))
        ));
        lootTableBiConsumer.accept(MAGNET_FISHING_TREASURE, LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(Items.NETHERITE_HOE).weight(MurkysManyFish.CONFIG.NetheriteHoeWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_SWORD).weight(MurkysManyFish.CONFIG.NetheriteSwordWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_AXE).weight(MurkysManyFish.CONFIG.NetheriteAxeWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_PICKAXE).weight(MurkysManyFish.CONFIG.NetheritePickaxeWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_SHOVEL).weight(MurkysManyFish.CONFIG.NetheriteShovelWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_HELMET).weight(MurkysManyFish.CONFIG.NetheriteHelmetWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_CHESTPLATE).weight(MurkysManyFish.CONFIG.NetheriteChestplateWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_LEGGINGS).weight(MurkysManyFish.CONFIG.NetheriteLeggingsWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_BOOTS).weight(MurkysManyFish.CONFIG.NetheriteBootsWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_SCRAP).weight(MurkysManyFish.CONFIG.NetheriteScrapWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_INGOT).weight(MurkysManyFish.CONFIG.NetheriteIngotWeight()))
                .with(ItemEntry.builder(Items.NETHERITE_BLOCK).weight(MurkysManyFish.CONFIG.NetheriteBlockWeight()))
                .with(ItemEntry.builder(Items.ANCIENT_DEBRIS).weight(MurkysManyFish.CONFIG.AncientDebrisWeight()))

        ));
        lootTableBiConsumer.accept(MAGNET_FISHING_ORE_LOW, LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(Items.IRON_NUGGET).weight(MurkysManyFish.CONFIG.IronNuggetWeight()))
                .with(ItemEntry.builder(Items.RAW_IRON).weight(MurkysManyFish.CONFIG.RawIronWeight()))
                .with(ItemEntry.builder(Items.IRON_INGOT).weight(MurkysManyFish.CONFIG.IronIngotWeight()))
        ));
        lootTableBiConsumer.accept(MAGNET_FISHING_ORE_MEDIUM, LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(Items.IRON_SWORD).weight(MurkysManyFish.CONFIG.IronSwordWeight()))
                .with(ItemEntry.builder(Items.IRON_PICKAXE).weight(MurkysManyFish.CONFIG.IronPickaxeWeight()))
                .with(ItemEntry.builder(Items.IRON_AXE).weight(MurkysManyFish.CONFIG.IronAxeWeight()))
                .with(ItemEntry.builder(Items.IRON_SHOVEL).weight(MurkysManyFish.CONFIG.IronShovelWeight()))
                .with(ItemEntry.builder(Items.IRON_HOE).weight(MurkysManyFish.CONFIG.IronHoeWeight()))
                .with(ItemEntry.builder(Items.IRON_HELMET).weight(MurkysManyFish.CONFIG.IronHelmetWeight()))
                .with(ItemEntry.builder(Items.IRON_CHESTPLATE).weight(MurkysManyFish.CONFIG.IronChestplateWeight()))
                .with(ItemEntry.builder(Items.IRON_LEGGINGS).weight(MurkysManyFish.CONFIG.IronLeggingsWeight()))
                .with(ItemEntry.builder(Items.IRON_BOOTS).weight(MurkysManyFish.CONFIG.IronBootsWeight()))
                .with(ItemEntry.builder(Items.MINECART).weight(MurkysManyFish.CONFIG.MinecartWeight()))
                .with(ItemEntry.builder(Items.TNT_MINECART).weight(MurkysManyFish.CONFIG.TntMinecartWeight()))
                .with(ItemEntry.builder(Items.HOPPER_MINECART).weight(MurkysManyFish.CONFIG.HopperMinecartWeight()))
                .with(ItemEntry.builder(Items.CHEST_MINECART).weight(MurkysManyFish.CONFIG.ChestMinecartWeight()))
                .with(ItemEntry.builder(Items.FURNACE_MINECART).weight(MurkysManyFish.CONFIG.FurnaceMinecartWeight()))
                .with(ItemEntry.builder(Items.CAULDRON).weight(MurkysManyFish.CONFIG.CauldronWeight()))

        ));
        lootTableBiConsumer.accept(MAGNET_FISHING_ORE_HIGH, LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(Items.ANCIENT_DEBRIS).weight(MurkysManyFish.CONFIG.AncientDebrisHighWeight()))
                .with(ItemEntry.builder(Items.LODESTONE).weight(MurkysManyFish.CONFIG.LodestoneWeight()))
                .with(ItemEntry.builder(Items.IRON_BLOCK).weight(MurkysManyFish.CONFIG.IronBlockWeight()))
                .with(ItemEntry.builder(Items.RAW_IRON_BLOCK).weight(MurkysManyFish.CONFIG.RawIronBlockWeight()))
                .with(ItemEntry.builder(Items.ANVIL).weight(MurkysManyFish.CONFIG.AnvilWeight()))
                .with(ItemEntry.builder(Items.CRAFTER).weight(MurkysManyFish.CONFIG.CrafterWeight()))
        ));
    }


}
