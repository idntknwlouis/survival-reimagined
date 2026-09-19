package net.mcreator.survivalreimagined.init;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public final class SurvivalReimaginedModWoodTypes {
	public static final WoodType RADIATED_SIGN_WOOD_TYPE = WoodType.register(new WoodType("survival_reimagined:radiated_sign", BlockSetType.OAK));
	public static final WoodType HANGING_RADIATED_SIGN_WOOD_TYPE = WoodType.register(new WoodType("survival_reimagined:hanging_radiated_sign", BlockSetType.OAK));
	public static final WoodType WISTERIA_SIGN_WOOD_TYPE = WoodType.register(new WoodType("survival_reimagined:wisteria_sign", BlockSetType.OAK));
	public static final WoodType HANGING_WISTERIA_SIGN_WOOD_TYPE = WoodType.register(new WoodType("survival_reimagined:hanging_wisteria_sign", BlockSetType.OAK));
	public static final WoodType MANDARIN_SIGN_WOOD_TYPE = WoodType.register(new WoodType("survival_reimagined:mandarin_sign", BlockSetType.OAK));
	public static final WoodType MANDARIN_HANGING_SIGN_WOOD_TYPE = WoodType.register(new WoodType("survival_reimagined:mandarin_hanging_sign", BlockSetType.OAK));

	private SurvivalReimaginedModWoodTypes() {
	}

	public static void register() {
		// Forces class initialization.
	}
}
