package net.mcreator.survivalreimagined.procedures.loot_tables;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

public class StickDrops {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            execute(player, pos, state.is(BlockTags.LEAVES));
        });
    }

    public static void execute(Player player, BlockPos pos, boolean isLeaves) {
        if (!isLeaves) return;

        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

            if (Math.random() < 0.1) {
                for (int i = 0; i < 2; i++) {
                    ItemEntity stick = new ItemEntity(serverPlayer.serverLevel(), pos.getX() + 0.5, pos.getY() + 0., pos.getZ() + 0., new ItemStack(Items.STICK));
                    stick.setPickUpDelay(10);
                    serverPlayer.serverLevel().addFreshEntity(stick);
                }
            }
        }
    }
}
