package net.mcreator.survivalreimagined.init;

import net.minecraft.world.level.block.SoundType;

public final class SurvivalReimaginedModSoundTypes {
	public static final SoundType STEEL = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.BLOCK_STEEL_PLACE.get(),
			SurvivalReimaginedModSounds.BLOCK_STEEL_STEP.get(),
			SurvivalReimaginedModSounds.BLOCK_STEEL_PLACE.get(),
			SurvivalReimaginedModSounds.BLOCK_STEEL_STEP.get(),
			SurvivalReimaginedModSounds.BLOCK_STEEL_PLACE.get()
	);

	public static final SoundType MOSSY_SHALE = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.BLOCK_MOSSY_SHALE_PLACE.get(),
			SurvivalReimaginedModSounds.BLOCK_SHALE_MOSS_STEP.get(),
			SurvivalReimaginedModSounds.BLOCK_MOSSY_SHALE_PLACE.get(),
			SurvivalReimaginedModSounds.BLOCK_SHALE_MOSS_STEP.get(),
			SurvivalReimaginedModSounds.BLOCK_MOSSY_SHALE_PLACE.get()
	);

	public static final SoundType NETHERITE_SCRAP = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.NETHERITE_SCRAP_SOUNDS.get(),
			SurvivalReimaginedModSounds.NETHERITE_SCRAP_SOUNDS.get(),
			SurvivalReimaginedModSounds.NETHERITE_SCRAP_SOUNDS.get(),
			SurvivalReimaginedModSounds.NETHERITE_SCRAP_SOUNDS.get(),
			SurvivalReimaginedModSounds.NETHERITE_SCRAP_SOUNDS.get()
	);

	public static final SoundType WISTERIA_LITTER = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.BREAK_WISTERIA_LITTER.get(),
			SurvivalReimaginedModSounds.STEP_WISTERA_LITTER.get(),
			SurvivalReimaginedModSounds.PLACE_WISTERA_LITTER.get(),
			SurvivalReimaginedModSounds.STEP_WISTERA_LITTER.get(),
			SurvivalReimaginedModSounds.PLACE_WISTERA_LITTER.get()
	);

	private SurvivalReimaginedModSoundTypes() {
	}
}
