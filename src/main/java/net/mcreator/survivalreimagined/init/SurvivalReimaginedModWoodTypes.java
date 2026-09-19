package net.mcreator.survivalreimagined.init;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

import net.mcreator.survivalreimagined.mixin.WoodTypeInvoker;

public final class SurvivalReimaginedModWoodTypes {
	public static final WoodType RADIATED_SIGN_WOOD_TYPE = register("survival_reimagined:radiated_sign");
	public static final WoodType HANGING_RADIATED_SIGN_WOOD_TYPE = register("survival_reimagined:hanging_radiated_sign");
	public static final WoodType WISTERIA_SIGN_WOOD_TYPE = register("survival_reimagined:wisteria_sign");
	public static final WoodType HANGING_WISTERIA_SIGN_WOOD_TYPE = register("survival_reimagined:hanging_wisteria_sign");
	public static final WoodType MANDARIN_SIGN_WOOD_TYPE = register("survival_reimagined:mandarin_sign");
	public static final WoodType MANDARIN_HANGING_SIGN_WOOD_TYPE = register("survival_reimagined:mandarin_hanging_sign");

	private SurvivalReimaginedModWoodTypes() {
	}

	private static WoodType register(String name) {
		return WoodTypeInvoker.survivalReimagined$register(new WoodType(name, BlockSetType.OAK));
	}

	public static void register() {
		// Forces class initialization.
	}
}
