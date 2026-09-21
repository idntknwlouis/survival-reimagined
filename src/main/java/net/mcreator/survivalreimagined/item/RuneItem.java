package net.mcreator.survivalreimagined.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RuneItem extends Item {
	private final String runeType;
	private final ChatFormatting runeTypeColor;
	private final String crystal;
	private final ChatFormatting crystalColor;

	public RuneItem(String runeType, ChatFormatting runeTypeColor, String crystal, ChatFormatting crystalColor) {
		super(new Item.Properties().rarity(Rarity.RARE));
		this.runeType = runeType;
		this.runeTypeColor = runeTypeColor;
		this.crystal = crystal;
		this.crystalColor = crystalColor;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal(" Rune Type: ").withStyle(ChatFormatting.GRAY)
				.append(Component.literal(runeType).withStyle(runeTypeColor)));
		tooltip.add(Component.literal(" Crystal: ").withStyle(ChatFormatting.GRAY)
				.append(Component.literal(crystal).withStyle(crystalColor)));
		tooltip.add(Component.empty());
		appendEffectDescription(tooltip);
		tooltip.add(Component.empty());
		tooltip.add(Component.literal("Applies to:").withStyle(ChatFormatting.WHITE));
		tooltip.add(Component.literal(" Weapons").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.literal(" Armor").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.literal(" Tools").withStyle(ChatFormatting.GRAY));
	}

	private void appendEffectDescription(List<Component> tooltip) {
		switch (crystal) {
			case "Sapphire" -> {
				gray(tooltip, "Weapon: Ocean's Wrath grows by defeating aquatic mobs");
				gray(tooltip, "Armor: Grants Dolphin's Grace while underwater");
				gray(tooltip, "Tool: Increases underwater mining speed");
			}
			case "Amber" -> {
				gray(tooltip, "Weapon: Has a chance to ignite enemies");
				gray(tooltip, "Armor: Full set can grant Fire Resistance");
				gray(tooltip, "Tool: Grants bonus drops from certain blocks");
			}
			case "Diamond" -> {
				gray(tooltip, "Weapon: Undead kills progress Unbreaking I-IV");
				gray(tooltip, "Armor: Can grant Resistance when hit");
				gray(tooltip, "Tool: Ore mining progresses Unbreaking I-IV");
			}
			case "Emerald" -> {
				gray(tooltip, "Weapon: Illager kills can drop Rough Emerald");
				gray(tooltip, "Armor: Can grant Hero of the Village");
				gray(tooltip, "Tool: Can drop Rough Emerald while mining");
			}
			case "Ruby" -> {
				gray(tooltip, "Weapon: Kills can restore health");
				gray(tooltip, "Armor: Full set can heal and regenerate at low health");
				gray(tooltip, "Tool: Can repair itself while mining");
			}
			case "Lapis" -> {
				gray(tooltip, "Weapon: Kills can grant extra experience");
				gray(tooltip, "Armor: Periodically grants experience");
				gray(tooltip, "Tool: Mining can spawn experience orbs");
			}
			default -> gray(tooltip, "Infuses equipment with a crystal effect");
		}
	}

	private static void gray(List<Component> tooltip, String text) {
		tooltip.add(Component.literal(text).withStyle(ChatFormatting.GRAY));
	}
}
