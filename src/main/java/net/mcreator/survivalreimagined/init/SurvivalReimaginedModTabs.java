package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

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
					BuiltInRegistries.ITEM.forEach(item -> {
						var key = BuiltInRegistries.ITEM.getKey(item);
						if (key == null || !SurvivalReimaginedMod.MODID.equals(key.getNamespace()) || isPairedRockBlock(item) || isInternalGeologySegment(item)) {
							return;
						}
						output.accept(item);
					});
				})
				.build();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab);
		return new RegistryEntry<>(id, tab);
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
