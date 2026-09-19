package net.mcreator.survivalreimagined.init;

import net.minecraft.client.gui.screens.MenuScreens;

import net.mcreator.survivalreimagined.client.gui.ForgeGUIScreen;

public final class SurvivalReimaginedModScreens {
	private SurvivalReimaginedModScreens() {
	}

	public static void register() {
		MenuScreens.register(SurvivalReimaginedModMenus.FORGE_GUI.get(), ForgeGUIScreen::new);
	}
}
