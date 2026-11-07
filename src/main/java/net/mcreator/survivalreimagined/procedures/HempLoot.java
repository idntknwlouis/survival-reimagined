package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class HempLoot {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            execute((ServerLevel) world, pos, state, player);
        });
    }

    private static void execute(ServerLevel world, BlockPos pos, BlockState state, Player player) {
        if (player.isCreative()) return;
        if (!state.is(SurvivalReimaginedModBlocks.HEMP.get())) return;
        if (state.hasProperty(BlockStateProperties.AGE_3) && state.getValue(BlockStateProperties.AGE_3) == 3) {
            drop(world, pos, SurvivalReimaginedModItems.HEMP_LEAF.get(), 1);

            int seedAmount = RandomSource.create().nextInt(1, 4);
            drop(world, pos, SurvivalReimaginedModItems.HEMP_SEEDS.get(), seedAmount);
        } else {
            drop(world, pos, SurvivalReimaginedModItems.HEMP_SEEDS.get(), 1);
        }
    }
    private static void drop(ServerLevel world, BlockPos pos, ItemLike item, int amount) {
        ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(item, amount));
        entity.setPickUpDelay(10);
        world.addFreshEntity(entity);
    }
}
