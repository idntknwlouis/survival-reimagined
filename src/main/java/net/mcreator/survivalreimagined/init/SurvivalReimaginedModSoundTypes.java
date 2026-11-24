package net.mcreator.survivalreimagined.init;

import net.minecraft.sounds.SoundEvents;
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

	public static final SoundType METAL_STONE_ORE = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.STONE_METAL_ORE_PLACE_BREAK.get(),
			SurvivalReimaginedModSounds.STONE_METAL_ORE_STEP_HIT.get(),
			SurvivalReimaginedModSounds.STONE_METAL_ORE_PLACE_BREAK.get(),
			SurvivalReimaginedModSounds.STONE_METAL_ORE_STEP_HIT.get(),
			SurvivalReimaginedModSounds.STONE_METAL_ORE_PLACE_BREAK.get()
	);

	public static final SoundType METAL_DEEPSLATE_ORE = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.DEEPSLATE_METAL_ORE_BREAK.get(),
			SurvivalReimaginedModSounds.DEEPSLATE_METAL_ORE_STEP_HIT.get(),
			SurvivalReimaginedModSounds.DEEPSLATE_METAL_ORE_PLACE.get(),
			SurvivalReimaginedModSounds.DEEPSLATE_METAL_ORE_STEP_HIT.get(),
			SurvivalReimaginedModSounds.DEEPSLATE_METAL_ORE_PLACE.get()
	);

	public static final SoundType METAL_BASALT_ORE = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.BASALT_METAL_ORE_PLACE_BREAK.get(),
			SurvivalReimaginedModSounds.BASALT_METAL_ORE_STEP_HIT.get(),
			SurvivalReimaginedModSounds.BASALT_METAL_ORE_PLACE_BREAK.get(),
			SurvivalReimaginedModSounds.BASALT_METAL_ORE_STEP_HIT.get(),
			SurvivalReimaginedModSounds.BASALT_METAL_ORE_PLACE_BREAK.get()
	);

	public static final SoundType GEM_STONE_ORE = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.STONE_GEM_ORE_BREAK.get(),
			SoundEvents.STONE_STEP,
			SurvivalReimaginedModSounds.STONE_GEM_ORE_PLACE.get(),
			SoundEvents.STONE_HIT,
			SurvivalReimaginedModSounds.STONE_GEM_ORE_PLACE.get()
	);

	public static final SoundType GEM_DEEPSLATE_ORE = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.DEEPSLATE_GEM_ORE_BREAK.get(),
			SoundEvents.DEEPSLATE_STEP,
			SurvivalReimaginedModSounds.DEEPSLATE_GEM_ORE_PLACE.get(),
			SoundEvents.DEEPSLATE_HIT,
			SurvivalReimaginedModSounds.DEEPSLATE_GEM_ORE_PLACE.get()
	);

	public static final SoundType GEM_BASALT_ORE = new SoundType(
			1.0F, 1.0F,
			SurvivalReimaginedModSounds.BASALT_GEM_ORE_BREAK.get(),
			SoundEvents.BASALT_STEP,
			SurvivalReimaginedModSounds.BASALT_GEM_ORE_PLACE.get(),
			SoundEvents.BASALT_HIT,
			SurvivalReimaginedModSounds.BASALT_GEM_ORE_PLACE.get()
	);


	private SurvivalReimaginedModSoundTypes() {
	}
}
