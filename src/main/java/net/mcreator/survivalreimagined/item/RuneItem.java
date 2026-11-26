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
	}

	public void appendDetailedDescription(List<Component> tooltip) {
		tooltip.add(Component.empty());
		tooltip.add(Component.literal("Item and Effects:").withStyle(ChatFormatting.WHITE));
		appendEffectDescription(tooltip);
	}

	private void appendEffectDescription(List<Component> tooltip) {
		switch (crystal) {
			case "Sapphire" -> {
				category(tooltip, "Weapons", "Ocean's Wrath grows by defeating aquatic mobs");
				category(tooltip, "Armor", "Grants Dolphin's Grace while underwater");
				category(tooltip, "Tools", "Increases underwater mining speed");
			}
			case "Amber" -> {
				category(tooltip, "Weapons", "Has a chance to ignite enemies");
				category(tooltip, "Armor", "Full set can grant Fire Resistance");
				category(tooltip, "Tools", "Grants bonus drops from certain blocks");
			}
			case "Diamond" -> {
				category(tooltip, "Weapons", "Undead kills progress Unbreaking I-IV");
				category(tooltip, "Armor", "Can grant Resistance when hit");
				category(tooltip, "Tools", "Ore mining progresses Unbreaking I-IV");
			}
			case "Emerald" -> {
				category(tooltip, "Weapons", "Illager kills can drop Rough Emerald");
				category(tooltip, "Armor", "Can grant Hero of the Village");
				category(tooltip, "Tools", "Can drop Rough Emerald while mining");
			}
			case "Ruby" -> {
				category(tooltip, "Weapons", "Kills can restore health");
				category(tooltip, "Armor", "Full set can heal and regenerate at low health");
				category(tooltip, "Tools", "Can repair itself while mining");
			}
			case "Lapis" -> {
				category(tooltip, "Weapons", "Kills can grant extra experience");
				category(tooltip, "Armor", "Periodically grants experience");
				category(tooltip, "Tools", "Mining can spawn experience orbs");
			}
			default -> category(tooltip, "Effects", "Infuses equipment with a crystal effect");
		}
	}

	private static void category(List<Component> tooltip, String category, String description) {
		tooltip.add(Component.literal(category + ":").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.literal("  " + description).withStyle(ChatFormatting.DARK_GRAY));
	}

	private static void gray(List<Component> tooltip, String text) {
		tooltip.add(Component.literal(text).withStyle(ChatFormatting.GRAY));
	}
}
