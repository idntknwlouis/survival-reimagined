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
		tooltip.add(Component.literal("Applies to:").withStyle(ChatFormatting.WHITE));
		tooltip.add(Component.literal(" Weapons").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.literal(" Armor").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.literal(" Tools").withStyle(ChatFormatting.GRAY));
	}
}
