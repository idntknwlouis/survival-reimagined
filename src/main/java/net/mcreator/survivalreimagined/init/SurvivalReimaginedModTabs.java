package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

public final class SurvivalReimaginedModTabs {
	public static final RegistryEntry<CreativeModeTab> SURVIVAL_REBORN = register();

	private SurvivalReimaginedModTabs() {
	}

	private static RegistryEntry<CreativeModeTab> register() {
		var id = SurvivalReimaginedMod.asResource("survival_reborn");
		CreativeModeTab tab = FabricItemGroup.builder()
				.title(Component.translatable("item_group.survival_reimagined.survival_reborn"))
				.icon(() -> new ItemStack(SurvivalReimaginedModItems.TIN_INGOT.get()))
				.displayItems((parameters, output) -> {
					output.accept(SurvivalReimaginedModItems.TIN_ORE.get());
					output.accept(SurvivalReimaginedModItems.DEEPSLATE_TIN_ORE.get());
					output.accept(SurvivalReimaginedModItems.RAW_TIN.get());
					output.accept(SurvivalReimaginedModItems.TIN_CHUNK.get());
					output.accept(SurvivalReimaginedModItems.TIN_NUGGET.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_TIN.get());
					output.accept(SurvivalReimaginedModItems.TIN_INGOT.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_RAW_TIN.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_TIN.get());
				})
				.build();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab);
		return new RegistryEntry<>(id, tab);
	}

	public static void register() {
		// Forces class initialization.
	}
}
