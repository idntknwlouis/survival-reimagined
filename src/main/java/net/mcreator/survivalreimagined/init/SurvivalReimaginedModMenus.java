package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;
import net.mcreator.survivalreimagined.world.inventory.ForgeGUIMenu;
import net.mcreator.survivalreimagined.world.inventory.AdvancedAlloyForgeGUIMenu;
import net.mcreator.survivalreimagined.world.inventory.AAFScriptureGUIMenu;
import net.mcreator.survivalreimagined.world.inventory.RMIScriptureGUIMenu;
import net.mcreator.survivalreimagined.world.inventory.MetalRefiningTableGUIMenu;
import net.mcreator.survivalreimagined.world.inventory.MPTGUIMenu;
import net.mcreator.survivalreimagined.world.inventory.RMIMenu;

public final class SurvivalReimaginedModMenus {
	public static final RegistryEntry<MenuType<ForgeGUIMenu>> FORGE_GUI = register(
			"forge_gui",
			new ExtendedScreenHandlerType<>(ForgeGUIMenu::new, BlockPos.STREAM_CODEC)
	);
	public static final RegistryEntry<MenuType<MetalRefiningTableGUIMenu>> METAL_REFINING_TABLE_GUI = register(
			"metal_refining_table_gui",
			new ExtendedScreenHandlerType<>(MetalRefiningTableGUIMenu::new, BlockPos.STREAM_CODEC)
	);
	public static final RegistryEntry<MenuType<MPTGUIMenu>> MPTGUI = register(
			"mptgui",
			new ExtendedScreenHandlerType<>(MPTGUIMenu::new, BlockPos.STREAM_CODEC)
	);
	public static final RegistryEntry<MenuType<RMIMenu>> RMI = register(
			"rmi",
			new ExtendedScreenHandlerType<>(RMIMenu::new, BlockPos.STREAM_CODEC)
	);
	public static final RegistryEntry<MenuType<AdvancedAlloyForgeGUIMenu>> ADVANCED_ALLOY_FORGE_GUI = register(
			"advanced_alloy_forge_gui",
			new ExtendedScreenHandlerType<>(AdvancedAlloyForgeGUIMenu::new, BlockPos.STREAM_CODEC)
	);
	public static final RegistryEntry<MenuType<AAFScriptureGUIMenu>> AAF_SCRIPTURE_GUI = register(
			"aaf_scripture_gui",
			new ExtendedScreenHandlerType<>(AAFScriptureGUIMenu::new, BlockPos.STREAM_CODEC)
	);
	public static final RegistryEntry<MenuType<RMIScriptureGUIMenu>> RMI_SCRIPTURE_GUI = register(
			"rmi_scripture_gui",
			new ExtendedScreenHandlerType<>(RMIScriptureGUIMenu::new, BlockPos.STREAM_CODEC)
	);

	private SurvivalReimaginedModMenus() {
	}

	private static <T extends AbstractContainerMenu> RegistryEntry<MenuType<T>> register(String path, MenuType<T> type) {
		var id = SurvivalReimaginedMod.asResource(path);
		MenuType<T> menuType = Registry.register(BuiltInRegistries.MENU, id, type);
		return new RegistryEntry<>(id, menuType);
	}

	public static void register() {
		// Forces class initialization.
	}
}
