package net.mcreator.survivalreimagined.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ScriptureItem extends Item {
	private final String subject;

	public ScriptureItem(String subject) {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
		this.subject = subject;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal(this.subject).withStyle(ChatFormatting.GRAY));
	}
}
