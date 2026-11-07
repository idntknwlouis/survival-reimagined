package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

public final class SurvivalReimaginedModSounds {
	public static final RegistryEntry<SoundEvent> BLOCK_CARVING_CRAFTING_TABLE = register("block.carving_crafting_table");
	public static final RegistryEntry<SoundEvent> BLOCK_CARVE_PROGRESS = register("block.carve_progress");
	public static final RegistryEntry<SoundEvent> FORGE_OPEN = register("forge_open");
	public static final RegistryEntry<SoundEvent> CLOSE_FORGE = register("close_forge");
	public static final RegistryEntry<SoundEvent> PLAYER_DIGGING_STONE = register("player_digging.stone");
	public static final RegistryEntry<SoundEvent> LIMB_REMOVE = register("limb_remove");
	public static final RegistryEntry<SoundEvent> CHIMES = register("chimes");
	public static final RegistryEntry<SoundEvent> BLOODZOMBIE_HURT = register("bloodzombie_hurt");
	public static final RegistryEntry<SoundEvent> BLOODZOMBIE_IDLE = register("bloodzombie_idle");
	public static final RegistryEntry<SoundEvent> BLOODZOMBIE_DEATH = register("bloodzombie_death");
	public static final RegistryEntry<SoundEvent> PORTAL_OPEN = register("portal_open");
	public static final RegistryEntry<SoundEvent> BLOODMOON_SONG = register("bloodmoon_song");
	public static final RegistryEntry<SoundEvent> MPT_AMBIENCE = register("mpt/ambience");
	public static final RegistryEntry<SoundEvent> BLOCK_SHALE_MOSS_STEP = register("block/shale_moss_step");
	public static final RegistryEntry<SoundEvent> BLOCK_MOSSY_SHALE_PLACE = register("block/mossy_shale_place");
	public static final RegistryEntry<SoundEvent> AMBIENT_RADIATED_FOREST = register("ambient/radiated_forest");
	public static final RegistryEntry<SoundEvent> MUSIC_RADIANT_ABYSS = register("music/radiant_abyss");
	public static final RegistryEntry<SoundEvent> RADIATION_DAMAGE = register("radiation/damage");
	public static final RegistryEntry<SoundEvent> GASMASK_FILTER = register("gasmask/filter");
	public static final RegistryEntry<SoundEvent> NETHERITE_SCRAP_SOUNDS = register("netherite_scrap/sounds");
	public static final RegistryEntry<SoundEvent> GEODE_CRACK = register("geode/crack");
	public static final RegistryEntry<SoundEvent> ECLIPSE_GUST = register("eclipse_gust");
	public static final RegistryEntry<SoundEvent> GAS_MASK_BREATH = register("gas_mask_breath");
	public static final RegistryEntry<SoundEvent> FILTER_MENDS = register("filter_mends");
	public static final RegistryEntry<SoundEvent> CRIMSON_ARACHNID_AMBIENT = register("crimson_arachnid/ambient");
	public static final RegistryEntry<SoundEvent> CRIMSON_ARACHNID_DEATH = register("crimson_arachnid/death");
	public static final RegistryEntry<SoundEvent> CRIMSON_ARACHNID_HURT = register("crimson_arachnid/hurt");
	public static final RegistryEntry<SoundEvent> GHOST_CREEPER_DEATH = register("ghost_creeper/death");
	public static final RegistryEntry<SoundEvent> GHOST_CREEPER_HIT = register("ghost_creeper/hit");
	public static final RegistryEntry<SoundEvent> GHOST_CREEPER_AMBIENT = register("ghost_creeper/ambient");
	public static final RegistryEntry<SoundEvent> SKELETON_MOB_HURT = register("skeleton_mob/hurt");
	public static final RegistryEntry<SoundEvent> SKELETON_MOB_DEATH = register("skeleton_mob/death");
	public static final RegistryEntry<SoundEvent> BREAK_WISTERIA_LITTER = register("break/wisteria_litter");
	public static final RegistryEntry<SoundEvent> PLACE_WISTERA_LITTER = register("place/wistera_litter");
	public static final RegistryEntry<SoundEvent> STEP_WISTERA_LITTER = register("step/wistera_litter");
	public static final RegistryEntry<SoundEvent> BLOCK_STEEL_PLACE = register("block/steel_place");
	public static final RegistryEntry<SoundEvent> BLOCK_STEEL_STEP = register("block/steel_step");
	public static final RegistryEntry<SoundEvent> FLINT_SCRAPE = register("flint_scrape");
	public static final RegistryEntry<SoundEvent> DEMON_DEATH = register("demon/death");
	public static final RegistryEntry<SoundEvent> DEMON_HIT = register("demon/hit");
	public static final RegistryEntry<SoundEvent> DEMON_AMBIENT = register("demon/ambient");
	public static final RegistryEntry<SoundEvent> DEMON_STEP = register("demon/step");
	public static final RegistryEntry<SoundEvent> AMBIENT_BAOR = register("ambient/baor");
	public static final RegistryEntry<SoundEvent> DEATH_BOAR = register("death/boar");
	public static final RegistryEntry<SoundEvent> HIT_BOAR = register("hit/boar");
	public static final RegistryEntry<SoundEvent> SQUEAL_BOAR = register("squeal/boar");
	public static final RegistryEntry<SoundEvent> AMBIENT_PIGLET = register("ambient/piglet");
	public static final RegistryEntry<SoundEvent> HURT_PIGLET = register("hurt/piglet");
	public static final RegistryEntry<SoundEvent> SQUEAL_PIGLET = register("squeal/piglet");
	public static final RegistryEntry<SoundEvent> BARREL_SEAL = register("barrel/seal");
	public static final RegistryEntry<SoundEvent> MILLSTONE = register("millstone");

	private SurvivalReimaginedModSounds() {
	}

	private static RegistryEntry<SoundEvent> register(String path) {
		var id = SurvivalReimaginedMod.asResource(path);
		var sound = Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
		return new RegistryEntry<>(id, sound);
	}

	public static void register() {
		// Forces class initialization.
	}
}
