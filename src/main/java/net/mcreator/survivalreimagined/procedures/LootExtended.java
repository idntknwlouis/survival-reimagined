package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.mcreator.survivalreimagined.entity.BloodMoonZombieEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;

public class LootExtended {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            Entity killer = damageSource.getEntity();
            if (!(entity instanceof BloodMoonZombieEntity) || !(killer instanceof Player player)) return;

            ServerLevel level = (ServerLevel) entity.level();
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();

            int looting = player.getMainHandItem().getEnchantments().getLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING));

            if (Math.random() < getFleshChance(looting)) {
                int amount = looting >= 3 ? (RandomSource.create().nextInt(2) + 1) : 1;
                drop(level, x,y,z, SurvivalReimaginedModItems.FLESH.get(), amount);
            }

            if (Math.random() < getFragmentChance(looting)) {
                int amount = looting >= 3 ? (RandomSource.create().nextInt(2) + 1) : 1;
                drop(level,x,y,z,SurvivalReimaginedModItems.BLOOD_MOON_DISC_FRAGMENT.get(), amount);
            }
        });
    }

    private static double getFleshChance(int looting) {
        return switch (looting) {
            case 1 -> 0.2;
            case 2 -> 0.3;
            case 3 -> 0.5;
            default -> 0.2;
        };
    }

    private static double getFragmentChance(int looting) {
        return switch (looting) {
            case 1 -> 0.15;
            case 2 -> 0.2;
            case 3 -> 0.3;
            default -> 0.1;
        };
    }

    private static void drop(ServerLevel level, double x, double y, double z, ItemLike item, int amount) {
        ItemEntity itemEntity = new ItemEntity(level, x, y, z, new ItemStack(item, amount));
        itemEntity.setPickUpDelay(10);
        level.addFreshEntity(itemEntity);
    }
}
