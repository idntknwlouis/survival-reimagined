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
						if (key != null && SurvivalReimaginedMod.MODID.equals(key.getNamespace())) {
							output.accept(item);
						}
					});
				})
				.build();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab);
		return new RegistryEntry<>(id, tab);
	}

	public static void register() {
		// Forces class initialization.
	}
}
