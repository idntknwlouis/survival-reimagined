package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class SurvivalReimaginedModTabs {
	public static final RegistryEntry<CreativeModeTab> SURVIVAL_REBORN = registerTab();

	private SurvivalReimaginedModTabs() {
	}

	private static RegistryEntry<CreativeModeTab> registerTab() {
		var id = SurvivalReimaginedMod.asResource("survival_reborn");
		CreativeModeTab tab = FabricItemGroup.builder()
				.title(Component.translatable("item_group.survival_reimagined.survival_reborn"))
				.icon(() -> new ItemStack(SurvivalReimaginedModItems.HEART.get()))
				.displayItems((parameters, output) -> {
					List<Item> items = new ArrayList<>();
					BuiltInRegistries.ITEM.forEach(item -> {
						var key = BuiltInRegistries.ITEM.getKey(item);
						if (key == null || !SurvivalReimaginedMod.MODID.equals(key.getNamespace()) || isPairedRockBlock(item) || isInternalGeologySegment(item)) {
							return;
						}
						items.add(item);
					});

					items.sort(Comparator
							.comparingInt(SurvivalReimaginedModTabs::creativeGroup)
							.thenComparing(SurvivalReimaginedModTabs::creativeSortKey)
							.thenComparing(item -> BuiltInRegistries.ITEM.getKey(item).getPath()));

					items.forEach(output::accept);
				})
				.build();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab);
		return new RegistryEntry<>(id, tab);
	}

	private static int creativeGroup(Item item) {
		String path = BuiltInRegistries.ITEM.getKey(item).getPath();

		// One-tab equivalent of vanilla's creative categories:
		// Building Blocks -> Natural Blocks -> Functional Blocks -> Ingredients
		// -> Tools & Utilities -> Combat -> Food & Drinks -> Misc.
		if (item instanceof BlockItem) {
			if (containsAny(path, "ore", "rock", "stalag", "stalagt", "deposit", "radiated_shale",
					"kimberlite", "dark_cinder", "crop", "plant", "vines", "leaves", "sapling", "wild_",
					"_seeds", "potato", "corn_stalk")) {
				return 1;
			}
			if (containsAny(path, "forge", "table", "millstone", "infuser", "mold", "campfire",
					"crucible", "uranium_rod")) {
				return 2;
			}
			return 0;
		}

		if (isFood(path)) return 6;
		if (isCombat(path)) return 5;
		if (isTool(path)) return 4;
		if (isIngredient(path)) return 3;
		return 7;
	}

	private static String creativeSortKey(Item item) {
		String path = BuiltInRegistries.ITEM.getKey(item).getPath();
		int group = creativeGroup(item);

		return switch (group) {
			case 0 -> buildingSortKey(path);
			case 1 -> naturalSortKey(path);
			case 2 -> functionalSortKey(path);
			case 3 -> ingredientSortKey(path);
			case 4, 5 -> equipmentSortKey(path);
			case 6 -> foodSortKey(path);
			default -> path.endsWith("_spawn_egg") ? "00_spawn_eggs_" + spawnEggRank(path) : "99_" + path;
		};
	}

	private static String spawnEggRank(String path) {
		if (path.equals("boar_spawn_egg")) return "00_boar";
		if (path.equals("sow_spawn_egg")) return "01_sow";
		if (path.equals("piglet_spawn_egg")) return "02_piglet";
		if (path.equals("black_bear_spawn_egg")) return "03_black_bear";
		if (path.equals("brown_bear_spawn_egg")) return "04_brown_bear";
		if (path.equals("crimson_arachnid_spawn_egg")) return "05_crimson_arachnid";
		if (path.equals("alpha_crimson_arachnid_spawn_egg")) return "06_alpha_crimson_arachnid";
		if (path.equals("ghost_spawn_egg")) return "07_ghost";
		if (path.equals("blood_moon_zombie_spawn_egg")) return "08_blood_moon_zombie";
		return "90_" + path;
	}

	private static String buildingSortKey(String path) {
		if (path.contains("_plate") || path.equals("wooden_plate")) {
			return "00_plates_" + plateRank(path);
		}
		if (path.contains("shale")) {
			return "00_shale_" + stage(path,
					"shale", "polished_shale", "polished_shale_bricks", "polished_chiseled_shale",
					"shale_stairs", "shale_slab", "shale_wall",
					"polished_shale_stairs", "polished_shale_slab", "polished_shale_wall",
					"polished_shale_brick_stairs", "polished_shale_brick_slab", "poloshed_shale_brick_wall");
		}
		if (containsAny(path, "block_of_", "_block", "raw_")) {
			return "10_storage_" + materialRank(path) + "_" + stage(path,
					"raw", "rough", "block_of_raw", "block", "plated");
		}
		return "90_" + path;
	}

	private static String naturalSortKey(String path) {
		if (path.contains("stalag") || path.contains("stalagt")) {
			return "00_formations_" + geologyRank(path) + "_" + stage(path, "stalagmite_top", "stalagtite_tip");
		}
		if (path.contains("rock")) {
			return "10_rocks_" + rockRank(path);
		}
		if (path.contains("ore")) {
			return "20_ores_" + materialRank(path) + "_" + geologyRank(path) + "_" + path;
		}
		if (containsAny(path, "rye", "spelt", "wheat", "potato", "corn", "hemp", "strawberry", "raspberry", "vines")) {
			return "30_plants_" + cropRank(path) + "_" + path;
		}
		if (path.contains("deposit")) return "40_deposits_" + path;
		return "90_" + path;
	}

	private static String functionalSortKey(String path) {
		if (path.equals("forge")) return "00_forge";
		if (path.equals("metal_refining_table")) return "01_metal_refining_table";
		if (path.equals("mineral_processing_table")) return "02_mineral_processing_table";
		if (path.equals("advanced_alloy_forge")) return "03_advanced_alloy_forge";
		if (path.equals("rune_magic_infuser")) return "04_rune_magic_infuser";
		if (path.equals("millstone")) return "05_millstone";
		if (path.equals("campfire")) return "06_campfire";
		if (path.contains("mold")) return "20_molds_" + moldFamilyRank(path) + "_" + moldStage(path);
		if (path.contains("uranium_rod")) return "30_uranium_rod";
		return "90_" + path;
	}

	private static String ingredientSortKey(String path) {
		if (containsAny(path, "rune", "heart", "sapphire", "ruby", "amber", "emerald", "diamond")
				&& !containsAny(path, "rough_", "_ore", "_block", "plated_diamond")) {
			return "20_magic_" + magicRank(path) + "_" + path;
		}
		if (containsAny(path, "upgrade", "reactor_rod", "depleted_reactor", "drained_advanced")) {
			return "30_aaf_" + stage(path, "reactor_rod", "advanced_reactor_rod", "depleted", "drained", "fuel", "yield", "efficiency", "packaging");
		}
		if (containsAny(path, "ingot", "nugget", "chunk", "rough_", "raw_", "dust", "powder", "cassiterite",
				"manganite", "hematite", "magnetite", "calaverite", "pyrolusite", "uranophane", "ilmenite",
				"anthracite", "liginite", "salt", "quick_lime", "handle", "blade", "_head", "hemp_fiber",
				"small_stick", "wood_ingot", "crimson_thread")) {
			return "00_materials_" + materialFamilyRank(path) + "_" + materialRank(path) + "_" + path;
		}
		return "90_" + path;
	}

	private static String equipmentSortKey(String path) {
		return materialRank(path) + "_" + toolPartRank(path) + "_" + path;
	}

	private static String foodSortKey(String path) {
		if (containsAny(path, "rye_bread", "spelt_bread", "potato", "corn_on_the_cob", "strawberry", "raspberry")) {
			return "00_crops_" + cropRank(path) + "_" + foodStage(path);
		}
		if (containsAny(path, "beef", "steak")) return "10_beef_" + foodStage(path);
		if (path.contains("porkchop")) return "11_pork_" + foodStage(path);
		if (path.contains("mutton")) return "12_mutton_" + foodStage(path);
		if (path.contains("chicken")) return "13_chicken_" + foodStage(path);
		if (path.contains("rabbit")) return "14_rabbit_" + foodStage(path);
		if (path.contains("cod")) return "15_cod_" + foodStage(path);
		if (path.contains("salmon")) return "16_salmon_" + foodStage(path);
		if (path.contains("equine")) return "17_equine_" + foodStage(path);
		if (path.equals("rotten_biomatter")) return "90_rotten";
		return "80_" + path;
	}

	private static boolean isFood(String path) {
		return containsAny(path,
				"beef", "steak", "mutton", "porkchop", "chicken", "rabbit", "cod", "salmon", "equine",
				"bread", "potato", "corn_on_the_cob", "strawberry", "raspberry", "cured_", "rotten_biomatter");
	}

	private static boolean isTool(String path) {
		return containsAny(path, "pickaxe", "_axe", "shovel", "_hoe", "knife", "hammer", "saw", "chisel",
				"fire_starter", "flint_tool");
	}

	private static boolean isCombat(String path) {
		return containsAny(path, "sword", "helmet", "chestplate", "leggings", "boots");
	}

	private static boolean isIngredient(String path) {
		return containsAny(path,
				"ingot", "nugget", "chunk", "rough_", "raw_", "dust", "powder", "handle", "blade", "_head",
				"rune", "upgrade", "reactor_rod", "cassiterite", "manganite", "hematite", "magnetite",
				"calaverite", "pyrolusite", "uranophane", "ilmenite", "anthracite", "liginite", "sapphire",
				"ruby", "amber", "heart", "salt", "quick_lime", "hemp_fiber", "small_stick", "wood_ingot", "crimson_thread");
	}

	private static String materialRank(String path) {
		if (path.contains("wood")) return "00_wood";
		if (path.contains("stone") || path.contains("flint")) return "01_stone";
		if (path.contains("copper")) return "02_copper";
		if (path.contains("tin") || path.contains("cassiterite")) return "03_tin";
		if (path.contains("bronze")) return "04_bronze";
		if (path.contains("iron") || path.contains("hematite") || path.contains("magnetite")) return "05_iron";
		if (path.contains("manganese") || path.contains("manganite") || path.contains("pyrolusite")) return "06_manganese";
		if (path.contains("steel")) return "07_steel";
		if (path.contains("silver") || path.contains("argentite")) return "08_silver";
		if (path.contains("gold") || path.contains("calaverite")) return "09_gold";
		if (path.contains("sapphire")) return "10_sapphire";
		if (path.contains("ruby")) return "11_ruby";
		if (path.contains("amber")) return "12_amber";
		if (path.contains("diamond")) return "13_diamond";
		if (path.contains("titanium") || path.contains("ilmenite")) return "14_titanium";
		if (path.contains("uran")) return "15_uranium";
		if (path.contains("turanite")) return "16_turanite";
		if (path.contains("netherite")) return "17_netherite";
		if (path.contains("anthracite")) return "18_anthracite";
		if (path.contains("liginite")) return "19_liginite";
		return "90_other";
	}

	private static String materialFamilyRank(String path) {
		if (path.contains("raw_")) return "00_raw";
		if (path.contains("chunk")) return "01_chunks";
		if (path.contains("rough_")) return "02_rough";
		if (path.contains("ingot")) return "03_ingots";
		if (path.contains("nugget")) return "04_nuggets";
		if (path.contains("dust") || path.contains("powder")) return "05_dusts";
		if (path.contains("handle")) return "06_handles";
		if (path.contains("sword_blade")) return "07_sword_blades";
		if (path.contains("pickaxe_head")) return "08_pickaxe_heads";
		if (path.contains("axe_head")) return "09_axe_heads";
		if (path.contains("shovel_head")) return "10_shovel_heads";
		if (path.contains("hoe_blade")) return "11_hoe_blades";
		if (path.contains("knife_blade")) return "12_knife_blades";
		if (path.contains("hammer_head")) return "13_hammer_heads";
		if (path.contains("saw_blade")) return "14_saw_blades";
		if (path.contains("blade") || path.contains("_head")) return "15_tool_parts";
		return "90_misc";
	}

	private static String plateRank(String path) {
		if (path.contains("wooden")) return "00_wooden";
		if (path.contains("bronze")) return "01_bronze";
		if (path.contains("steel")) return "02_steel";
		if (path.contains("diamond")) return "03_diamond";
		if (path.contains("netherite")) return "04_netherite";
		return "90_" + path;
	}

	private static String moldFamilyRank(String path) {
		if (path.contains("ingot")) return "00_ingot";
		if (path.contains("metal_plate")) return "01_plate";
		if (path.contains("sword")) return "02_sword";
		if (path.contains("pickaxe")) return "03_pickaxe";
		if (path.contains("axe")) return "04_axe";
		if (path.contains("shovel")) return "05_shovel";
		if (path.contains("hoe")) return "06_hoe";
		if (path.contains("knife")) return "07_knife";
		if (path.contains("hammer")) return "08_hammer";
		if (path.contains("saw")) return "09_saw";
		if (path.contains("rune")) return "10_rune";
		return "90_" + path;
	}

	private static String moldStage(String path) {
		// Clay is the unfired form; the corresponding non-clay mold follows it.
		return path.contains("clay") ? "00_clay" : "01_dried";
	}

	private static String toolPartRank(String path) {
		if (path.contains("sword")) return "00_sword";
		if (path.contains("pickaxe")) return "01_pickaxe";
		if (path.contains("_axe")) return "02_axe";
		if (path.contains("shovel")) return "03_shovel";
		if (path.contains("_hoe")) return "04_hoe";
		if (path.contains("knife")) return "05_knife";
		if (path.contains("hammer")) return "06_hammer";
		if (path.contains("saw")) return "07_saw";
		if (path.contains("chisel")) return "08_chisel";
		if (path.contains("helmet")) return "20_helmet";
		if (path.contains("chestplate")) return "21_chestplate";
		if (path.contains("leggings")) return "22_leggings";
		if (path.contains("boots")) return "23_boots";
		return "90_misc";
	}

	private static String geologyRank(String path) {
		if (path.startsWith("stone_")) return "00_stone";
		if (path.startsWith("deepslate_")) return "01_deepslate";
		if (path.startsWith("shale_")) return "02_shale";
		if (path.startsWith("basalt_")) return "03_basalt";
		if (path.startsWith("kimberlite_") || path.equals("kimberlite")) return "04_kimberlite";
		return "90_other";
	}

	private static String rockRank(String path) {
		return stage(path, "stone", "andesite", "granite", "diorite", "dripstone", "calcite", "tuff",
				"mossy", "deepslate", "basalt", "blackstone", "netherrack", "end_stone", "obsidian", "kimberlite");
	}

	private static String cropRank(String path) {
		return stage(path, "wheat", "rye", "spelt", "potato", "corn", "hemp", "strawberry", "raspberry");
	}

	private static String foodStage(String path) {
		return stage(path, "raw_", "beef", "cooked_", "burnt_", "charred_", "cured_");
	}

	private static String magicRank(String path) {
		return stage(path, "wooden_rune", "empty_silver", "empty_gold", "silver_", "gold_", "sapphire", "ruby", "amber", "heart");
	}

	private static String stage(String path, String... values) {
		for (int i = 0; i < values.length; i++) {
			if (path.contains(values[i])) {
				return String.format("%02d_%s", i, path);
			}
		}
		return "99_" + path;
	}

	private static boolean containsAny(String path, String... values) {
		for (String value : values) {
			if (path.contains(value)) return true;
		}
		return false;
	}

	private static boolean isInternalGeologySegment(Item item) {
		return item == SurvivalReimaginedModItems.STONE_STALAGMITE_BASE.get()
				|| item == SurvivalReimaginedModItems.STONE_STALAGMITE_MIDDLE.get()
				|| item == SurvivalReimaginedModItems.STONE_STALAGTITE_BASE.get()
				|| item == SurvivalReimaginedModItems.STONE_STALAGTITE_MIDDLE.get()
				|| item == SurvivalReimaginedModItems.DEEPSLATE_STALAGMITE_BASE.get()
				|| item == SurvivalReimaginedModItems.DEEPSLATE_STALAGMITE_MIDDLE.get()
				|| item == SurvivalReimaginedModItems.DEEPSLATE_STALAGTITE_BASE.get()
				|| item == SurvivalReimaginedModItems.DEEPSLATE_STALAGTITE_MIDDLE.get()
				|| item == SurvivalReimaginedModItems.BASALT_STALAGMITE_BASE.get()
				|| item == SurvivalReimaginedModItems.BASALT_STALAGMITE_MIDDLE.get()
				|| item == SurvivalReimaginedModItems.BASALT_STALAGTITE_BASE.get()
				|| item == SurvivalReimaginedModItems.BASALT_STALAGTITE_MIDDLE.get()
				|| item == SurvivalReimaginedModItems.KIMBERLITE_STALAGMITE_BASE.get()
				|| item == SurvivalReimaginedModItems.KIMBERLITE_STALAGMITE_MIDDLE.get()
				|| item == SurvivalReimaginedModItems.KIMBERLITE_STALAGTITE_BASE.get()
				|| item == SurvivalReimaginedModItems.KIMBERLITE_STALAGTITE_MIDDLE.get();
	}

	private static boolean isPairedRockBlock(Item item) {
		return item == SurvivalReimaginedModItems.STONE_ROCK_BLOC.get()
				|| item == SurvivalReimaginedModItems.ANDESITE_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.GRANITE_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.DIORITE_ROCK_B_LOCK.get()
				|| item == SurvivalReimaginedModItems.DRIPSTONE_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.CALCITE_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.TUFF_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.MOSSY_STONE_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.NETHERRACK_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.END_STONE_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.BLACKSTONE_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.BASALT_ROCK_BLOCK.get()
				|| item == SurvivalReimaginedModItems.DEEPSLATE_ROCK_BLOCK.get();
	}

	public static void register() {
		// Forces class initialization.
	}
}
