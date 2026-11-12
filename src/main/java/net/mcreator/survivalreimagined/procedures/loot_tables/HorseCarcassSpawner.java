package net.mcreator.survivalreimagined.procedures.loot_tables;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.mcreator.survivalreimagined.block.HorseCarcasses;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HorseCarcassSpawner {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity instanceof Horse horse)) return;

            Item carcassItem = HorseCarcasses.getCarcassItemForHorse(horse);

            if (carcassItem != null) {
                ItemStack drop = new ItemStack(carcassItem);

                ItemEntity itemEntity = new ItemEntity(
                        horse.level(),
                        horse.getX(),
                        horse.getY(),
                        horse.getZ(),
                        drop
                );
                horse.level().addFreshEntity(itemEntity);
            }
        });
    }
}
