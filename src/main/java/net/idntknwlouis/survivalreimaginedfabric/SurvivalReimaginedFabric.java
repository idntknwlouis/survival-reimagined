package net.idntknwlouis.survivalreimaginedfabric;

import net.fabricmc.api.ModInitializer;

import net.idntknwlouis.survivalreimaginedfabric.block.ModBlocks;
import net.idntknwlouis.survivalreimaginedfabric.item.ModItems;
import net.idntknwlouis.survivalreimaginedfabric.item.ModItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurvivalReimaginedFabric implements ModInitializer {
	public static final String MOD_ID = "survival-reimagined-fabric";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		LOGGER.info("Hello Fabric world!");
	}
}