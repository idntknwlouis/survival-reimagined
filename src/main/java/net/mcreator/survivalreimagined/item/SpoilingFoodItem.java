package net.mcreator.survivalreimagined.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

import java.util.List;

public class SpoilingFoodItem extends Item {
	private static final int ROT_THRESHOLD = 2000;

	public SpoilingFoodItem(Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);

		if (level.isClientSide() || !(entity instanceof Player player)) {
			return;
		}

		CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		var tag = data.copyTag();
		int spoilage = (int) tag.getDouble("SpoilageMax");
		long currentTick = level.getGameTime();
		long lastTick = tag.getLong("SpoilageLastTick");

		// One spoilage percent is 20 points. At the original rate of one point
		// every 60 ticks, the stack only needs a data update once every 1200 ticks.
		if (!tag.contains("SpoilageLastTick")) {
			CustomData.update(DataComponents.CUSTOM_DATA, stack,
					nbt -> nbt.putLong("SpoilageLastTick", currentTick));
		} else {
			long elapsed = currentTick - lastTick;
			if (elapsed < 0) {
				CustomData.update(DataComponents.CUSTOM_DATA, stack,
						nbt -> nbt.putLong("SpoilageLastTick", currentTick));
			} else if (elapsed >= 1200) {
				long percentSteps = elapsed / 1200;
				spoilage = Math.min(ROT_THRESHOLD, spoilage + (int) (percentSteps * 20));
				long nextTick = lastTick + percentSteps * 1200;

				final int newSpoilage = spoilage;
				final long newLastTick = nextTick;
				CustomData.update(DataComponents.CUSTOM_DATA, stack, nbt -> {
					nbt.putDouble("SpoilageMax", newSpoilage);
					nbt.putLong("SpoilageLastTick", newLastTick);
					nbt.remove("InventoryClock");
				});
			}
		}

		if (spoilage >= ROT_THRESHOLD) {
			ItemStack rotten = new ItemStack(SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get(), stack.getCount());
			player.getInventory().setItem(slotId, rotten);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);

		double spoilage = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
				.copyTag().getDouble("SpoilageMax");

		if (spoilage <= 499) {
			tooltip.add(Component.literal("\u00A72 Fresh"));
		} else if (spoilage <= 999) {
			tooltip.add(Component.literal("\u00A76 Microbial Spoilage"));
		} else if (spoilage <= 1499) {
			tooltip.add(Component.literal("\u00A74 Spoiling"));
		} else if (spoilage <= 1999) {
			tooltip.add(Component.literal("\u00A78 Rotten"));
		}

		int spoilagePercent = Math.max(0, Math.min(100, (int) Math.floor((spoilage / ROT_THRESHOLD) * 100.0D)));
		if (spoilagePercent > 1) {
			tooltip.add(Component.literal("\u00A77 " + spoilagePercent + "% Spoiled"));
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		double spoilage = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
				.copyTag().getDouble("SpoilageMax");
		ItemStack result = super.finishUsingItem(stack, level, entity);

		if (!level.isClientSide()) {
			if (spoilage >= 500 && spoilage <= 999) {
				if (entity.getRandom().nextFloat() < 0.5F) {
					entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, 1, true, false));
				}
			} else if (spoilage >= 1000 && spoilage <= 1499) {
				entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 2, true, false));
				entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1, true, false));
			} else if (spoilage >= 1500 && spoilage <= 1999) {
				entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 2400, 2, true, false));
				entity.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 1, true, false));
				entity.hurt(entity.damageSources().generic(), 2.0F);
			}
		}

		return result;
	}
}
