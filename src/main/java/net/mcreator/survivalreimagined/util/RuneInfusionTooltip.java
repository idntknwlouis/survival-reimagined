package net.mcreator.survivalreimagined.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public final class RuneInfusionTooltip {
	private static final TagKey<Item> TOOL = tag("rmi_infusable/tool");
	private static final TagKey<Item> WEAPON = tag("rmi_infusable/weapon");

	private RuneInfusionTooltip() {
	}

	public static void append(ItemStack stack, List<Component> lines) {
		CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

		boolean gold = tag.getBoolean("GoldInfused");
		boolean silver = tag.getBoolean("SilverInfused");
		if (!gold && !silver) return;

		lines.add(Component.empty());
		lines.add(Component.literal("Rune Infusion").withStyle(ChatFormatting.DARK_PURPLE));
		lines.add(Component.literal(gold ? "Rune Type: Gold" : "Rune Type: Silver")
				.withStyle(gold ? ChatFormatting.GOLD : ChatFormatting.WHITE));

		boolean armor = stack.getItem() instanceof ArmorItem;
		boolean weapon = stack.is(WEAPON);
		boolean tool = stack.is(TOOL);

		if (tag.getBoolean("SapphireInfused")) {
			lines.add(Component.literal("Crystal: Sapphire").withStyle(ChatFormatting.BLUE));
			if (armor) {
				lines.add(effect("Underwater armor can grant Dolphin's Grace"));
			} else if (tool) {
				lines.add(effect("+60% underwater mining speed"));
			}
		} else if (tag.getBoolean("AmberInfused")) {
			lines.add(Component.literal("Crystal: Amber").withStyle(ChatFormatting.GOLD));
			if (weapon) {
				lines.add(effect(gold ? "60% chance to ignite targets for 6s" : "40% chance to ignite targets for 3s"));
			} else if (armor) {
				lines.add(effect(gold ? "Full set: 50% fire-resistance proc while burning" : "Full set: 30% fire-resistance proc while burning"));
			} else if (tool) {
				lines.add(effect(gold ? "Pickaxes: 60% ore-conversion proc" : "Pickaxes: 40% ore-conversion proc"));
				lines.add(effect("Axes/shovels gain special material drops"));
			}
		} else if (tag.getBoolean("DiamondInfused")) {
			lines.add(Component.literal("Crystal: Diamond").withStyle(ChatFormatting.AQUA));
			if (weapon) {
				lines.add(effect("Undead kills progress Unbreaking I-IV"));
			} else if (armor) {
				lines.add(effect(gold ? "40% chance for Resistance II when hit" : "20% chance for Resistance I when hit"));
			} else if (tool) {
				lines.add(effect("Mining progresses Unbreaking I-IV"));
			}
		} else if (tag.getBoolean("EmeraldInfused")) {
			lines.add(Component.literal("Crystal: Emerald").withStyle(ChatFormatting.DARK_GREEN));
			if (weapon) {
				lines.add(effect(gold ? "25% chance for Rough Emerald on illager kill" : "15% chance for Rough Emerald on illager kill"));
			} else if (armor) {
				lines.add(effect("Can grant Hero of the Village"));
			} else if (tool) {
				lines.add(effect("Chance to find Rough Emerald while mining"));
			}
		} else if (tag.getBoolean("RubyInfused")) {
			lines.add(Component.literal("Crystal: Ruby").withStyle(ChatFormatting.DARK_RED));
			if (weapon) {
				lines.add(effect(gold ? "10% chance to heal 2 hearts on kill" : "7.5% chance to heal 1 heart on kill"));
			} else if (armor) {
				lines.add(effect("Full matching set can save you at low health"));
			} else if (tool) {
				lines.add(effect(gold ? "8% repair/Ruby Heart Shard proc" : "5% repair/Ruby Heart Shard proc"));
			}
		} else if (tag.getBoolean("LapisInfused")) {
			lines.add(Component.literal("Crystal: Lapis").withStyle(ChatFormatting.DARK_BLUE));
			if (weapon) {
				lines.add(effect(gold ? "20% chance for bonus XP on kill" : "10% chance for bonus XP on kill"));
			} else if (armor) {
				lines.add(effect(gold ? "Periodic 10% chance for bonus XP" : "Periodic 5% chance for bonus XP"));
			} else if (tool) {
				lines.add(effect("Pickaxes can generate bonus XP"));
			}
		}
	}

	private static Component effect(String text) {
		return Component.literal("Effect: " + text).withStyle(ChatFormatting.GRAY);
	}

	private static TagKey<Item> tag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
	}
}
