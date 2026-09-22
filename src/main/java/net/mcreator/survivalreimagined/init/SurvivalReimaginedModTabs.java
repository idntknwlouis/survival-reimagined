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
							.thenComparing(item -> BuiltInRegistries.ITEM.getKey(item).getPath()));

					items.forEach(output::accept);
				})
				.build();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab);
		return new RegistryEntry<>(id, tab);
	}

	private static int creativeGroup(Item item) {
		String path = BuiltInRegistries.ITEM.getKey(item).getPath();

		// Vanilla-like ordering inside the single Survival Reimagined tab:
		// Building Blocks, Natural Blocks, Functional Blocks, Tools & Utilities,
		// Combat, Food & Drinks, then Ingredients / miscellaneous content.
		if (item instanceof BlockItem) {
			if (containsAny(path,
					"ore", "rock", "shale", "basalt", "kimberlite", "stalag", "stalagt",
					"deposit", "crop", "plant", "vines", "leaves", "sapling", "wild_",
					"rye_seeds", "spelt_seeds", "wheat_seeds", "potatoes")) {
				return 1;
			}
			if (containsAny(path,
					"forge", "table", "millstone", "infuser", "mold", "campfire",
					"crucible", "uranium_rod")) {
				return 2;
			}
			return 0;
		}

		if (containsAny(path,
				"pickaxe", "axe", "shovel", "hoe", "knife", "hammer", "saw", "chisel",
				"fire_starter", "flint_tool")) {
			return 3;
		}

		if (containsAny(path, "sword", "helmet", "chestplate", "leggings", "boots")) {
			return 4;
		}

		if (containsAny(path,
				"beef", "mutton", "porkchop", "chicken", "rabbit", "cod", "salmon", "equine",
				"bread", "potato", "corn_on_the_cob", "strawberry", "raspberry",
				"cured_", "cooked_", "burnt_", "raw_", "rotten_biomatter")) {
			return 5;
		}

		return 6;
	}

	private static boolean containsAny(String path, String... values) {
		for (String value : values) {
			if (path.contains(value)) {
				return true;
			}
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
