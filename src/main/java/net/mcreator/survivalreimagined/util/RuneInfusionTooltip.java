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

		Component runeType = Component.literal(" Rune Type: ").withStyle(ChatFormatting.GRAY)
				.append(Component.literal(gold ? "Gold" : "Silver")
						.withStyle(gold ? ChatFormatting.GOLD : ChatFormatting.WHITE));

		Component crystal = crystalLine(tag);
		if (crystal == null) return;

		int insert = Math.min(1, lines.size());
		lines.add(insert++, runeType);
		lines.add(insert++, crystal);
		lines.add(insert, Component.empty());
	}

	private static Component crystalLine(CompoundTag tag) {
		if (tag.getBoolean("SapphireInfused")) return crystal("Sapphire", ChatFormatting.BLUE);
		if (tag.getBoolean("AmberInfused")) return crystal("Amber", ChatFormatting.GOLD);
		if (tag.getBoolean("DiamondInfused")) return crystal("Diamond", ChatFormatting.AQUA);
		if (tag.getBoolean("EmeraldInfused")) return crystal("Emerald", ChatFormatting.DARK_GREEN);
		if (tag.getBoolean("RubyInfused")) return crystal("Ruby", ChatFormatting.DARK_RED);
		if (tag.getBoolean("LapisInfused")) return crystal("Lapis", ChatFormatting.DARK_BLUE);
		if (tag.getBoolean("SpinelInfused")) return crystal("Spinel", ChatFormatting.LIGHT_PURPLE);
		return null;
	}

	private static Component crystal(String name, ChatFormatting color) {
		return Component.literal(" Crystal: ").withStyle(ChatFormatting.GRAY)
				.append(Component.literal(name).withStyle(color));
	}
}
