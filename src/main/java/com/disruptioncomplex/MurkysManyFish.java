package com.disruptioncomplex;

import com.disruptioncomplex.block.ModBlockHandler;
import com.disruptioncomplex.entity.ModEntityHandler;
import com.disruptioncomplex.item.ModItemGroups;
import com.disruptioncomplex.item.ModItemHandler;
import com.disruptioncomplex.loottable.ModLootHandler;
import com.disruptioncomplex.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MurkysManyFish implements ModInitializer {
	public static final String MOD_ID = "murkys-many-fish";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModEntityHandler.registerModEntities();
		ModItemGroups.registerItemGroups();
		ModItemHandler.registerModItems();
		ModBlockHandler.registerBlocks();
		ModLootHandler.registerLoot();

		ModWorldGeneration.generateModWorldGen();

	}
}