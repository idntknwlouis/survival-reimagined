package net.mcreator.survivalreimagined.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public final class FoodTooltip {
	private static final TagKey<Item> RAW = tag("campfire/raw");
	private static final TagKey<Item> COOKED = tag("campfire/cooked");
	private static final TagKey<Item> BURNT = tag("campfire/burnt");

	private FoodTooltip() {
	}

	private static TagKey<Item> tag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
	}

	public static void append(ItemStack stack, List<Component> lines) {
		CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		double percentage = data.copyTag().getDouble("PercentageNumber");
		if (percentage > 0.0D) {
			int pct = Math.max(1, Math.min(100, (int) Math.round((percentage / 30.0D) * 100.0D)));
			if (stack.is(RAW)) lines.add(Component.literal("§7 " + pct + "% Cooked"));
			else if (stack.is(COOKED)) lines.add(Component.literal("§7 " + pct + "% Burnt"));
			else if (stack.is(BURNT)) lines.add(Component.literal("§7 " + pct + "% Charred"));
		}
	}
}
