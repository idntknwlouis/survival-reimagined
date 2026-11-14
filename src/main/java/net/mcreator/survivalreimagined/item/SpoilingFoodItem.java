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
		boolean changed = false;

		if (!tag.contains("SpoilageLastTick") || currentTick < lastTick) {
			CustomData.update(DataComponents.CUSTOM_DATA, stack, nbt -> {
				nbt.putLong("SpoilageLastTick", currentTick);
				nbt.remove("ContainerSpoilageLastTick");
			});
			changed = true;
		} else if (tag.contains("ContainerSpoilageLastTick")) {
			// The stack has just moved from a container into the player's inventory.
			// First account for any container-time progress, then switch its clock
			// to the faster inventory rate without counting the same elapsed time twice.
			long containerElapsed = currentTick - lastTick;
			long containerSteps = containerElapsed / 100L;
			long containerRemainder = containerElapsed % 100L;
			spoilage = Math.min(ROT_THRESHOLD, spoilage + (int) containerSteps);

			// Preserve the fractional progress when converting from 1 point / 100
			// ticks in containers to 1 point / 60 ticks in the inventory.
			long inventoryRemainder = containerRemainder * 60L / 100L;
			long inventoryLastTick = currentTick - inventoryRemainder;
			final int convertedSpoilage = spoilage;
			CustomData.update(DataComponents.CUSTOM_DATA, stack, nbt -> {
				nbt.putDouble("SpoilageMax", convertedSpoilage);
				nbt.putLong("SpoilageLastTick", inventoryLastTick);
				nbt.remove("ContainerSpoilageLastTick");
				nbt.remove("InventoryClock");
			});
			lastTick = inventoryLastTick;
			changed = true;
		}

		// Inventory spoilage advances by one point every 60 ticks. Update the
		// stack at that same interval so open inventory tooltips receive fresh data.
		long elapsed = currentTick - lastTick;
		if (elapsed >= 60) {
			long spoilageSteps = elapsed / 60;
			spoilage = Math.min(ROT_THRESHOLD, spoilage + (int) spoilageSteps);
			long nextTick = lastTick + spoilageSteps * 60;

			final int newSpoilage = spoilage;
			final long newLastTick = nextTick;
			CustomData.update(DataComponents.CUSTOM_DATA, stack, nbt -> {
				nbt.putDouble("SpoilageMax", newSpoilage);
				nbt.putLong("SpoilageLastTick", newLastTick);
				nbt.remove("InventoryClock");
			});
			changed = true;
		}
		if (spoilage >= ROT_THRESHOLD) {
			ItemStack rotten = new ItemStack(SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get(), stack.getCount());
			player.getInventory().setItem(slotId, rotten);
			changed = true;
		}

		if (changed) {
			// Mark the inventory dirty and sync the current menu so the client tooltip
			// updates without requiring the player to move or consume the item.
			player.getInventory().setChanged();
			player.containerMenu.broadcastChanges();
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);

		var spoilageTag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		double spoilage = spoilageTag.getDouble("SpoilageMax");

		// SpoilageMax is synchronized by the server as spoilage advances.

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
