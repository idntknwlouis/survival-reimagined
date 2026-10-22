package net.mcreator.survivalreimagined.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;

import java.util.List;

public class GasMaskFilterItem extends Item {
	public GasMaskFilterItem() {
		super(new Item.Properties());
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack filter = player.getItemInHand(hand);
		ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

		if (!helmet.is(SurvivalReimaginedModItems.GAS_MASK_HELMET.get())) {
			return InteractionResultHolder.pass(filter);
		}

		double current = helmet.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
				.copyTag().getDouble("FilterPercentage");
		if (current > 0.0D) {
			return InteractionResultHolder.fail(filter);
		}

		if (!level.isClientSide()) {
			CustomData.update(DataComponents.CUSTOM_DATA, helmet,
					tag -> tag.putDouble("FilterPercentage", 100.0D));
			level.playSound(null, player.blockPosition(), SurvivalReimaginedModSounds.GASMASK_FILTER.get(),
					SoundSource.PLAYERS, 1.0F, 1.0F);
			if (!player.getAbilities().instabuild) {
				filter.shrink(1);
			}
		}

		return InteractionResultHolder.sidedSuccess(filter, level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal("§7 Right-Click this item while wearing Gas Mask"));
	}
}
