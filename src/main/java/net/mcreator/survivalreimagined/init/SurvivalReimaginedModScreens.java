package net.mcreator.survivalreimagined.init;

import net.minecraft.client.gui.screens.MenuScreens;

import net.mcreator.survivalreimagined.client.gui.ForgeGUIScreen;
import net.mcreator.survivalreimagined.client.gui.AdvancedAlloyForgeGUIScreen;
import net.mcreator.survivalreimagined.client.gui.MetalRefiningTableGUIScreen;
import net.mcreator.survivalreimagined.client.gui.MPTGUIScreen;
import net.mcreator.survivalreimagined.client.gui.MillstoneGUIScreen;
import net.mcreator.survivalreimagined.client.gui.CampfireGUIScreen;
import net.mcreator.survivalreimagined.client.gui.RMIScreen;

public final class SurvivalReimaginedModScreens {
	private SurvivalReimaginedModScreens() {
	}

	public static void register() {
		MenuScreens.register(SurvivalReimaginedModMenus.FORGE_GUI.get(), ForgeGUIScreen::new);
		MenuScreens.register(SurvivalReimaginedModMenus.METAL_REFINING_TABLE_GUI.get(), MetalRefiningTableGUIScreen::new);
		MenuScreens.register(SurvivalReimaginedModMenus.MPTGUI.get(), MPTGUIScreen::new);
		MenuScreens.register(SurvivalReimaginedModMenus.MILLSTONE_GUI.get(), MillstoneGUIScreen::new);
		MenuScreens.register(SurvivalReimaginedModMenus.CAMPFIRE_GUI.get(), CampfireGUIScreen::new);
		MenuScreens.register(SurvivalReimaginedModMenus.RMI.get(), RMIScreen::new);
		MenuScreens.register(SurvivalReimaginedModMenus.ADVANCED_ALLOY_FORGE_GUI.get(), AdvancedAlloyForgeGUIScreen::new);
	}
}
