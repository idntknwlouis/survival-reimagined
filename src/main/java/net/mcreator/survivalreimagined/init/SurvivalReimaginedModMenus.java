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

public final class SurvivalReimaginedModMenus {
	public static final RegistryEntry<MenuType<ForgeGUIMenu>> FORGE_GUI = register(
			"forge_gui",
			new ExtendedScreenHandlerType<>(ForgeGUIMenu::new, BlockPos.STREAM_CODEC)
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
