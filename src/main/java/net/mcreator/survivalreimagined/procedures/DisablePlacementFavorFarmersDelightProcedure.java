package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.loader.api.FabricLoader;

public class DisablePlacementFavorFarmersDelightProcedure {
	public static boolean execute() {
		return !FabricLoader.getInstance().isModLoaded("farmersdelight");
	}
}
