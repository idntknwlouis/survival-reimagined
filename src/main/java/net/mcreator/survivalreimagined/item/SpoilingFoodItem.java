package net.mcreator.survivalreimagined.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class SpoilingFoodItem extends Item {
	public SpoilingFoodItem(Properties properties) {
		super(properties);
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
