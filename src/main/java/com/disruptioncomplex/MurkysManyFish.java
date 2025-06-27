package com.disruptioncomplex;

import com.disruptioncomplex.block.ModBlockHandler;
import com.disruptioncomplex.common.item.EnchantmentItemGroup;
import com.disruptioncomplex.component.ModComponentHandler;
import com.disruptioncomplex.config.MurkysManyFishConfigWrapper;
import com.disruptioncomplex.enchantment.ModEnchantments;
import com.disruptioncomplex.enchantment.effect.ModEnchantmentEffects;
import com.disruptioncomplex.entity.ModBlockEntityHandler;
import com.disruptioncomplex.entity.ModEntityHandler;
import com.disruptioncomplex.entity.data.ModTrackedDataHandlers;
import com.disruptioncomplex.events.ModEventHandler;
import com.disruptioncomplex.item.ModItemGroups;
import com.disruptioncomplex.item.ModItemHandler;
import com.disruptioncomplex.loot_table.ModLootHandler;
import com.disruptioncomplex.gui.ModScreenHandlers;
import com.disruptioncomplex.world.gen.ModWorldGeneration;
import com.disruptioncomplex.world.gen.feature.ModFeatureHandler;
import net.fabricmc.api.ModInitializer;
import java.util.ArrayList;
import java.util.List;

public class MurkysManyFish implements ModInitializer {

    // Set debug logging before creating the logger
    static {
        System.setProperty("log4j2.logger.murkys-many-fish", "DEBUG");
    }

    public static final String MOD_ID = "murkys-many-fish";


    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.


    public static final MurkysManyFishConfigWrapper CONFIG = MurkysManyFishConfigWrapper.createAndLoad();

    // Use Fabric's logger
    public static final org.slf4j.Logger LOGGER =
            org.slf4j.LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {



        MurkysManyFish.LOGGER.info("Initializing Murkys Many Fish!");
        LOGGER.debug(">>> DEBUG OUTPUT ACTIVE <<<");
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        ModTrackedDataHandlers.register();
        ModScreenHandlers.registerScreenHandlers();
        ModBlockHandler.registerBlocks();
        ModBlockEntityHandler.registerModBlockEntities();
        ModEventHandler.registerEvents();
        ModItemHandler.registerModItems();
        ModComponentHandler.registerComponents();
        ModItemGroups.registerItemGroups();
        ModLootHandler.registerLoot();
        ModEntityHandler.registerModEntities();
        ModFeatureHandler.registerFeatures();
        ModEnchantmentEffects.registerEnchantmentEffects();

        ModWorldGeneration.generateModWorldGen();
        registerEnchantmentsToSharedGroup();


    }

    private void registerEnchantmentsToSharedGroup() {
        List<EnchantmentItemGroup.EnchantmentEntry> enchantments = new ArrayList<>();

        // Add SHARPNESS_PLUS enchantment (levels 1-5)
        enchantments.add(new EnchantmentItemGroup.EnchantmentEntry(
                ModEnchantments.HEFT, 3));

        // Add FIRE_ASPECT_PLUS enchantment (levels 1-3)
        enchantments.add(new EnchantmentItemGroup.EnchantmentEntry(
                ModEnchantments.SLIPPERY, 3));

        // Register to the shared group
        boolean success = EnchantmentItemGroup.registerEnchantments(MOD_ID, enchantments);
        LOGGER.info("Registering shared enchantment group: {}", success);
        LOGGER.debug("Number of Enchantments: {}", enchantments.size());
        LOGGER.debug("Enchantment details: HEFT (max level: 3), SLIPPERY (max level: 3)");
    }



}