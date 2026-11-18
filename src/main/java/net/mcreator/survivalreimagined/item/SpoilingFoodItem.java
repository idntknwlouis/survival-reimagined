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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

import java.util.List;
import java.util.Locale;

public class SpoilingFoodItem extends Item {
	private static final TagKey<Item> CAN_ROT = TagKey.create(
			Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "food/can_rot"));
	private static final int ROT_THRESHOLD = 2000;
	private static final int INVENTORY_TICKS_PER_SPOILAGE = 120;

	public SpoilingFoodItem(Properties properties) {
		super(properties);
	}

	/**
	 * NeoForge parity: each spoilable item carries its own inventory clock and
	 * gains one spoilage point every 120 inventory ticks.
	 */
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, level, entity, slot, selected);
		// Player inventories are ticked centrally by FoodSpoilage so every slot
		// advances, not just the currently selected hotbar stack.
		if (level.isClientSide() || entity instanceof Player || !stack.is(CAN_ROT)) {
			return;
		}

		var tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		double spoilage = tag.getDouble("SpoilageMax");
		if (spoilage >= ROT_THRESHOLD) {
			if (entity instanceof Player player && slot >= 0 && slot < player.getInventory().getContainerSize()) {
				ItemStack rottenItem;
				if (stack.is(SurvivalReimaginedModItems.FLESH.get())) {
					rottenItem = new ItemStack(Items.ROTTEN_FLESH, stack.getCount());
				} else {
					rottenItem = new ItemStack(SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get(), stack.getCount());
				}
				player.getInventory().setItem(slot, rottenItem);
			}
			return;
		}

		double inventoryClock = tag.getDouble("InventoryClock") + 1.0D;
		if (inventoryClock >= INVENTORY_TICKS_PER_SPOILAGE) {
			inventoryClock = 0.0D;
			spoilage += 1.0D;
		}
		final double updatedClock = inventoryClock;
		final double updatedSpoilage = spoilage;
		CustomData.update(DataComponents.CUSTOM_DATA, stack, data -> {
			data.putDouble("InventoryClock", updatedClock);
			if (updatedSpoilage > 0.0D || data.contains("SpoilageMax")) {
				data.putDouble("SpoilageMax", updatedSpoilage);
			}
		});
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);

		var spoilageTag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		double spoilage = spoilageTag.getDouble("SpoilageMax");
		double inventoryClock = spoilageTag.getDouble("InventoryClock");
		// Include the partial 60-tick interval so the displayed percentage
		// advances before a full spoilage point is added to SpoilageMax.
		double spoilageProgress = spoilage + Math.min(59.0D, inventoryClock) / INVENTORY_TICKS_PER_SPOILAGE;

		if (spoilage >= 0 && spoilage <= 499) {
			tooltip.add(Component.literal("\u00A72 Fresh"));
		} else if (spoilage >= 500 && spoilage <= 999) {
			tooltip.add(Component.literal("\u00A76 Microbial Spoilage"));
		} else if (spoilage >= 1000 && spoilage <= 1499) {
			tooltip.add(Component.literal("\u00A74 Spoiling"));
		} else if (spoilage >= 1500 && spoilage <= 1999) {
			tooltip.add(Component.literal("\u00A78 Rotten"));
		}

		double spoilagePercent = Math.max(0.0D, Math.min(100.0D,
				(spoilageProgress / ROT_THRESHOLD) * 100.0D));
		if (spoilagePercent > 1.0D) {
			tooltip.add(Component.literal("\u00A77 " + String.format(Locale.ROOT, "%.1f%% Spoiled", spoilagePercent)));
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		double spoilage = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
				.copyTag().getDouble("SpoilageMax");
		ItemStack result = super.finishUsingItem(stack, level, entity);

		if (!level.isClientSide()) {
			if (spoilage >= 500 && spoilage <= 999) {
				if (entity.getRandom().nextFloat() < 0.25F) {
					entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 1, false, false));
				}
			} else if (spoilage >= 1000 && spoilage <= 1499) {
				entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, 2, false, false));
				entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1, false, false));
			} else if (spoilage >= 1500 && spoilage <= 1999) {
				entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1200, 2, false, false));
				entity.addEffect(new MobEffectInstance(MobEffects.POISON, 150, 1, false, false));
				entity.hurt(entity.damageSources().generic(), 2.0F);
			}
		}

		return result;
	}
}
