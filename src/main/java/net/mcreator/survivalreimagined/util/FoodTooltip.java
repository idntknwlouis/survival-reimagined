package net.mcreator.survivalreimagined.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;
import java.util.Set;

public final class FoodTooltip {
	private static final TagKey<Item> RAW = tag("campfire/raw");
	private static final TagKey<Item> COOKED = tag("campfire/cooked");
	private static final TagKey<Item> BURNT = tag("campfire/burnt");
	private static final TagKey<Item> CAN_ROT = tag("food/can_rot");
	private static final Set<String> ROT_FOODS = Set.of(
			"beef", "cooked_beef", "burnt_beef",
			"raw_mutton", "cooked_mutton", "burnt_mutton",
			"raw_porkchop", "cooked_porkchop", "burnt_porkchop",
			"raw_chicken", "cooked_chicken", "burnt_chicken",
			"raw_rabbit", "cooked_rabbit", "burnt_rabbit",
			"raw_cod", "cooked_cod", "burnt_cod",
			"raw_salmon", "cooked_salmon", "burnt_salmon",
			"raw_equine", "cooked_equine", "burnt_equine"
	);

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

		String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
		if (stack.is(CAN_ROT) || ROT_FOODS.contains(id)) {
			double spoilage = data.copyTag().getDouble("SpoilageMax");
			if (spoilage <= 499) lines.add(Component.literal("§2 Fresh"));
			else if (spoilage <= 999) lines.add(Component.literal("§6 Microbial Spoilage"));
			else if (spoilage <= 1499) lines.add(Component.literal("§4 Spoiling"));
			else if (spoilage <= 1999) lines.add(Component.literal("§8 Rotten"));
		}
	}
}
