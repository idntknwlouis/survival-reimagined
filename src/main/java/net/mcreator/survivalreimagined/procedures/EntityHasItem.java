package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class EntityHasItem {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
        });
    }

    public static void execute(Level world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        double NumGen = 0;

        if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:zombies")))
        || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:skeletons")))
        || entity instanceof Piglin || entity instanceof PiglinBrute) {
            if (!(entity instanceof LivingEntity living)) return;

            // Head
            ItemStack head = living.getItemBySlot(EquipmentSlot.HEAD);
            if (head.getCount() == 1) {
                NumGen = Mth.nextInt(RandomSource.create(), 1, 5);
                head.setDamageValue(head.getMaxDamage() - (int) NumGen);

                ItemEntity drop = new ItemEntity(world, x + 0.5, y, z + 0.5, head.copy());
                drop.setPickUpDelay(10);
                world.addFreshEntity(drop);
                living.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
            }

            // Chest
            ItemStack chest = living.getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getCount() == 1) {
                NumGen = Mth.nextInt(RandomSource.create(), 1, 5);
                chest.setDamageValue(chest.getMaxDamage() - (int) NumGen);

                ItemEntity drop = new ItemEntity(world, x + 0.5, y, z + 0.5, chest.copy());
                drop.setPickUpDelay(10);
                world.addFreshEntity(drop);
                living.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            }

            // Legs
            ItemStack legs = living.getItemBySlot(EquipmentSlot.LEGS);
            if (legs.getCount() == 1) {
                NumGen = Mth.nextInt(RandomSource.create(), 1, 5);
                legs.setDamageValue(legs.getMaxDamage() - (int) NumGen);

                ItemEntity drop = new ItemEntity(world, x + 0.5, y, z + 0.5, legs.copy());
                drop.setPickUpDelay(10);
                world.addFreshEntity(drop);
                living.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
            }

            // Feet
            ItemStack feet = living.getItemBySlot(EquipmentSlot.FEET);
            if (feet.getCount() == 1) {
                NumGen = Mth.nextInt(RandomSource.create(), 1, 5);
                feet.setDamageValue(feet.getMaxDamage() - (int) NumGen);

                ItemEntity drop = new ItemEntity(world, x + 0.5, y, z + 0.5, feet.copy());
                drop.setPickUpDelay(10);
                world.addFreshEntity(drop);
                living.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
            }

            // MainHand
            ItemStack mainHand = living.getItemBySlot(EquipmentSlot.MAINHAND);
            if (mainHand.getCount() == 1) {
                NumGen = Mth.nextInt(RandomSource.create(), 1, 5);
                mainHand.setDamageValue(mainHand.getMaxDamage() - (int) NumGen);

                if (mainHand.getItem() != Items.TRIDENT) {
                    ItemEntity drop = new ItemEntity(world, x + 0.5, y, z + 0.5, mainHand.copy());
                    drop.setPickUpDelay(10);
                    world.addFreshEntity(drop);
                    living.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }

            //Offhand
            ItemStack offHand = living.getItemBySlot(EquipmentSlot.OFFHAND);
            if (offHand.getCount() == 1) {
                NumGen = Mth.nextInt(RandomSource.create(), 1, 5);
                offHand.setDamageValue(offHand.getMaxDamage() - (int) NumGen);

                if (offHand.getItem() != Items.TRIDENT) {
                    ItemEntity drop = new ItemEntity(world, x + 0.5, y, z + 0.5, offHand.copy());
                    drop.setPickUpDelay(10);
                    world.addFreshEntity(drop);
                    living.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
            }
        }
    }
}
