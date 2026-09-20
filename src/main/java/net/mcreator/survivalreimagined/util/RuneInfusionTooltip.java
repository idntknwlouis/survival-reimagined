package net.mcreator.survivalreimagined.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public final class RuneInfusionTooltip {
	private RuneInfusionTooltip() {
	}

	public static void append(ItemStack stack, List<Component> lines) {
		CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

		boolean gold = tag.getBoolean("GoldInfused");
		boolean silver = tag.getBoolean("SilverInfused");
		if (!gold && !silver) return;

		lines.add(Component.empty());
		lines.add(Component.literal("Rune Infusion").withStyle(ChatFormatting.DARK_PURPLE));

		if (gold) {
			lines.add(Component.literal("Gold Rune").withStyle(ChatFormatting.GOLD));
		} else {
			lines.add(Component.literal("Silver Rune").withStyle(ChatFormatting.GRAY));
		}

		if (tag.getBoolean("SapphireInfused")) {
			lines.add(Component.literal("Sapphire").withStyle(ChatFormatting.BLUE));
		} else if (tag.getBoolean("AmberInfused")) {
			lines.add(Component.literal("Amber").withStyle(ChatFormatting.GOLD));
		} else if (tag.getBoolean("DiamondInfused")) {
			lines.add(Component.literal("Diamond").withStyle(ChatFormatting.AQUA));
		} else if (tag.getBoolean("EmeraldInfused")) {
			lines.add(Component.literal("Emerald").withStyle(ChatFormatting.DARK_GREEN));
		} else if (tag.getBoolean("RubyInfused")) {
			lines.add(Component.literal("Ruby").withStyle(ChatFormatting.DARK_RED));
		} else if (tag.getBoolean("LapisInfused")) {
			lines.add(Component.literal("Lapis").withStyle(ChatFormatting.DARK_BLUE));
		}
	}
}
