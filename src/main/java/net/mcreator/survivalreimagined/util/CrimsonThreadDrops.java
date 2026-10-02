package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import net.mcreator.survivalreimagined.entity.AlphaCrimsonArachnidEntity;
import net.mcreator.survivalreimagined.entity.CrimsonArachnidEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public final class CrimsonThreadDrops {
	private CrimsonThreadDrops() {
	}

	public static void register() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (!(entity instanceof CrimsonArachnidEntity) && !(entity instanceof AlphaCrimsonArachnidEntity)) {
				return;
			}
			if (!(entity.level() instanceof ServerLevel level)) {
				return;
			}

			int looting = 0;
			if (source.getEntity() instanceof LivingEntity killer) {
				var lootingHolder = level.registryAccess()
						.lookupOrThrow(Registries.ENCHANTMENT)
						.getOrThrow(Enchantments.LOOTING);
				looting = EnchantmentHelper.getItemEnchantmentLevel(lootingHolder, killer.getMainHandItem());
			}

			float chance;
			int min;
			int max;
			switch (Math.min(looting, 3)) {
				case 1 -> {
					chance = 0.30F;
					min = 2;
					max = 4;
				}
				case 2 -> {
					chance = 0.35F;
					min = 3;
					max = 5;
				}
				case 3 -> {
					chance = 0.50F;
					min = 4;
					max = 6;
				}
				default -> {
					chance = 0.20F;
					min = 1;
					max = 2;
				}
			}

			if (entity.getRandom().nextFloat() >= chance) {
				return;
			}

			int count = min + entity.getRandom().nextInt(max - min + 1);
			ItemEntity drop = new ItemEntity(
					level,
					entity.getX(),
					entity.getY(),
					entity.getZ(),
					new ItemStack(SurvivalReimaginedModItems.CRIMSON_THREAD.get(), count));
			drop.setPickUpDelay(10);
			level.addFreshEntity(drop);
		});
	}
}
