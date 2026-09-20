package net.mcreator.survivalreimagined.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

import java.util.List;

public final class AAFUpgradeTooltip {
	private AAFUpgradeTooltip() {
	}

	public static void append(ItemStack stack, List<Component> lines) {
		if (stack.is(SurvivalReimaginedModItems.FUEL_UPGRADE.get())) {
			lines.add(Component.literal("x2 Fuel Capacity").withStyle(ChatFormatting.GRAY));
			lines.add(Component.literal("Does not stack").withStyle(ChatFormatting.GRAY));
		} else if (stack.is(SurvivalReimaginedModItems.FUEL_UPGRADE_MKII.get())) {
			lines.add(Component.literal("x3 Fuel Capacity").withStyle(ChatFormatting.GRAY));
			lines.add(Component.literal("Does not stack").withStyle(ChatFormatting.GRAY));
		} else if (stack.is(SurvivalReimaginedModItems.EFFICIENCY_UPGRADE.get())) {
			lines.add(Component.literal("Decreases fuel consumption").withStyle(ChatFormatting.GRAY));
			lines.add(Component.literal("Increases smelt time").withStyle(ChatFormatting.GRAY));
			lines.add(Component.literal("Does not stack").withStyle(ChatFormatting.GRAY));
		} else if (stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE.get())) {
			lines.add(Component.literal("x2 Smelt Yield").withStyle(ChatFormatting.GRAY));
			lines.add(Component.literal("Does not stack").withStyle(ChatFormatting.GRAY));
		} else if (stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE_MKII.get())) {
			lines.add(Component.literal("x3 Smelt Yield").withStyle(ChatFormatting.GRAY));
			lines.add(Component.literal("Does not stack").withStyle(ChatFormatting.GRAY));
		} else if (stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE_MKIII.get())) {
			lines.add(Component.literal("x4 Smelt Yield").withStyle(ChatFormatting.GRAY));
			lines.add(Component.literal("Does not stack").withStyle(ChatFormatting.GRAY));
		} else if (stack.is(SurvivalReimaginedModItems.BLOCK_PACKAGING_UPGRADE.get())) {
			lines.add(Component.literal("Does not stack").withStyle(ChatFormatting.GRAY));
			lines.add(Component.literal("Unaffected by Yield Upgrade").withStyle(ChatFormatting.GRAY));
		}
	}
}
