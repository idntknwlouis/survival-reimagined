package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class PlantFiberDrops {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(world instanceof ServerLevel serverLevel)) return;
            if (!(player instanceof ServerPlayer serverPlayer) || serverPlayer.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;
            if (!state.is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("survival_reimagined:drops_fiber")))) return;
            if (!player.getMainHandItem().is(TagKey.create(Registries.ITEM, ResourceLocation.parse("c:tools/knife")))) return;

            if (Math.random() < 0.1) {
                ItemStack drop = new ItemStack(SurvivalReimaginedModItems.HEMP_FIBER.get());
                ItemEntity entity = new ItemEntity(serverLevel, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, drop);
                entity.setPickUpDelay(10);
                serverLevel.addFreshEntity(entity);
            } else if (Math.random() < 0.2) {
                ItemStack drop = new ItemStack(SurvivalReimaginedModItems.HEMP_SEEDS.get());
                ItemEntity entity = new ItemEntity(serverLevel, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, drop);
                entity.setPickUpDelay(10);
                serverLevel.addFreshEntity(entity);
            }
        });
    }
}
