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
			gray(lines, "x2 Fuel Capacity");
			gray(lines, "Does not stack");
		} else if (stack.is(SurvivalReimaginedModItems.FUEL_UPGRADE_MKII.get())) {
			gray(lines, "x3 Fuel Capacity");
			gray(lines, "Does not stack");
		} else if (stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE.get())) {
			gray(lines, "x2 Smelt Yield");
			gray(lines, "Does not stack");
		} else if (stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE_MKII.get())) {
			gray(lines, "x3 Smelt Yield");
			gray(lines, "Does not stack");
		} else if (stack.is(SurvivalReimaginedModItems.YIELD_UPGRADE_MKIII.get())) {
			gray(lines, "x4 Smelt Yield");
			gray(lines, "Does not stack");
		} else if (stack.is(SurvivalReimaginedModItems.EFFICIENCY_UPGRADE.get())) {
			gray(lines, "Decreases fuel consumption");
			gray(lines, "Increases smelt speed");
			gray(lines, "Does not stack");
		} else if (stack.is(SurvivalReimaginedModItems.BLOCK_PACKAGING_UPGRADE.get())) {
			gray(lines, "Enables block-form alloy recipes");
			gray(lines, "Does not stack");
			gray(lines, "Unaffected by Yield Upgrade");
		}
	}

	private static void gray(List<Component> lines, String text) {
		lines.add(Component.literal(text).withStyle(ChatFormatting.GRAY));
	}
}
