package net.mcreator.survivalreimagined.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RubyHeartItem extends Item {
	public RubyHeartItem() {
		super(new Item.Properties().rarity(Rarity.RARE));
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal("Grants one additional heart. Max of 20").withStyle(ChatFormatting.GRAY));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		var maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
		if (maxHealth == null) return InteractionResultHolder.pass(stack);

		if (player.getCooldowns().isOnCooldown(this)) {
			return InteractionResultHolder.fail(stack);
		}

		if (player.getMaxHealth() >= 40.0F) {
			if (!level.isClientSide()) {
				player.displayClientMessage(Component.literal("Max Health Reached"), true);
			}
			return InteractionResultHolder.fail(stack);
		}

		if (!level.isClientSide()) {
			level.playSound(null, player.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.PLAYERS, 0.2F, 1.3F);
			level.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.6F, 1.2F);
			maxHealth.setBaseValue(Math.min(40.0D, player.getMaxHealth() + 2.0D));
			if (!player.getAbilities().instabuild) stack.shrink(1);
			player.getCooldowns().addCooldown(this, 100);
		}

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
