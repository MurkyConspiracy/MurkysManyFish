package com.disruptioncomplex.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@SuppressWarnings("unused")
@Modmenu(modId = "murkys-many-fish")
@Config(name = "murkysmanyfish-config", wrapperName = "MurkysManyFishConfigWrapper")
public class MurkysManyFishConfigModel {
    //Crab Trap Configuration
    public int CrabTrapBlockTickInterval = 2400;
    public int CrabTrapBlockTickRange = 1200;
    public int CrabTrapBlockWaterVolumeMin = 30;
    public float CrabTrapBlockGarbageChance = 0.02f;


    // Clam Generation
    public int ClamMinGroupSize = 1;
    public int ClamMaxGroupSize = 4;
    public int ClamSpawnChance = 16; // 1 in 16 chance

    // Dead Clam Generation
    public int DeadClamMinGroupSize = 1;
    public int DeadClamMaxGroupSize = 4;
    public int DeadClamSpawnChance = 16; // 1 in 16 chance

    // Spawn Weights
    public int AnchovySpawnWeight = 10;
    public int AngelfishSpawnWeight = 5;
    public int AnglerSpawnWeight = 5;
    public int BettaSpawnWeight = 5;
    public int MackerelSpawnWeight = 7;
    public int TunaSpawnWeight = 5;

    // Minimum Group Sizes
    public int AnchovyMinGroupSize = 7;
    public int AngelfishMinGroupSize = 3;
    public int AnglerMinGroupSize = 1;
    public int BettaMinGroupSize = 1;
    public int MackerelMinGroupSize = 3;
    public int TunaMinGroupSize = 1;

    // Maximum Group Sizes
    public int AnchovyMaxGroupSize = 25;
    public int AngelfishMaxGroupSize = 5;
    public int AnglerMaxGroupSize = 2;
    public int BettaMaxGroupSize = 2;
    public int MackerelMaxGroupSize = 5;
    public int TunaMaxGroupSize = 3;

    //Item Configuration
    public int magnetRodDurability = 100;

    //Enchantment Variables
    //Slippery
    public int slipperyDropChancePerLevel = 1;

    // Magnet Fishing Garbage Loot Weights
    public int TrashPileWeight = 30;              // Very common trash
    public int TripwireHookWeight = 15;           // Somewhat uncommon component
    public int FlintAndSteelWeight = 20;          // Easy to craft
    public int ChainWeight = 12;                  // Requires iron nuggets
    public int HeavyWeightedPressurePlateWeight = 8;  // Requires gold
    public int IronTrapdoorWeight = 18;           // Common iron item
    public int RailWeight = 25;                   // Very common
    public int ActivatorRailWeight = 8;           // Requires redstone
    public int DetectorRailWeight = 8;            // Requires redstone
    public int PoweredRailWeight = 6;             // Requires gold + redstone
    public int IronDoorWeight = 15;               // Common iron item
    public int IronBarsWeight = 20;               // Common iron item
    public int IronHorseArmorWeight = 5;          // Cannot be crafted, only found

    // Magnet Fishing Treasure Loot Weights (Ultra-rare items)
    public int NetheriteHoeWeight = 1;            // Extremely rare
    public int NetheriteSwordWeight = 2;          // More useful, slightly higher
    public int NetheriteAxeWeight = 2;            // More useful, slightly higher
    public int NetheritePickaxeWeight = 3;        // Most useful tool, highest weight
    public int NetheriteShovelWeight = 1;         // Least useful tool
    public int NetheriteHelmetWeight = 1;         // Least armor protection
    public int NetheriteChestplateWeight = 2;     // Most armor protection
    public int NetheriteLeggingsWeight = 2;       // Good armor protection
    public int NetheriteBootsWeight = 1;          // Least armor protection
    public int NetheriteScrapWeight = 8;          // Component item, more common
    public int NetheriteIngotWeight = 4;          // Crafted from scrap
    public int NetheriteBlockWeight = 1;          // 9 ingots worth
    public int AncientDebrisWeight = 6;           // Source material

    // Magnet Fishing Ore Low Loot Weights (Very common iron items)
    public int IronNuggetWeight = 35;             // Most common iron form
    public int RawIronWeight = 25;                // Common ore drop
    public int IronIngotWeight = 20;              // Processed form

    // Magnet Fishing Ore Medium Loot Weights (Crafted iron items)
    public int IronSwordWeight = 8;               // Useful weapon
    public int IronPickaxeWeight = 10;            // Most useful tool
    public int IronAxeWeight = 8;                 // Useful tool
    public int IronShovelWeight = 6;              // Less useful tool
    public int IronHoeWeight = 4;                 // Least useful tool
    public int IronHelmetWeight = 5;              // Least armor protection
    public int IronChestplateWeight = 7;          // Most armor protection
    public int IronLeggingsWeight = 6;            // Good armor protection
    public int IronBootsWeight = 5;               // Least armor protection
    public int MinecartWeight = 12;               // Useful transport
    public int TntMinecartWeight = 8;             // Requires TNT
    public int HopperMinecartWeight = 6;          // Requires hopper (expensive)
    public int ChestMinecartWeight = 10;          // Useful for storage
    public int FurnaceMinecartWeight = 8;         // Less commonly used
    public int CauldronWeight = 12;               // Common utility block

    // Magnet Fishing Ore High Loot Weights (Rare/expensive items)
    public int AncientDebrisHighWeight = 3;       // Extremely rare
    public int LodestoneWeight = 2;               // Very expensive (8 gold + netherite)
    public int IronBlockWeight = 15;              // 9 ingots worth, but still iron
    public int RawIronBlockWeight = 18;           // 9 raw iron, more common than ingot blocks
    public int AnvilWeight = 8;                   // Expensive (31 iron ingots)
    public int CrafterWeight = 12;                // New utility block, moderately expensive

    // Magnet Fishing Pool Multipliers
    public int MediumOreWeightMultiplier = 2;
    public int HighOreWeightMultiplier = 3;
}