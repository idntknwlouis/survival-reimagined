package net.mcreator.survivalreimagined.block;


import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public abstract class CornStalkBlock extends Block implements EntityBlock {
    protected CornStalkBlock(Properties properties) {
        super(properties);
    }

    protected abstract IntegerProperty getAgeProperty();

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(getAgeProperty(), 0);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }

    public void syncUpper(ServerLevel level, BlockPos pos, BlockState grownState) {
        if (!(this instanceof CornStalkBottomBlock)) return;

        int bottomAge = grownState.getValue(CornStalkBottomBlock.AGE);

        BlockPos middlePos = pos.above();
        BlockPos topPos = pos.above(2);
        BlockState middleState = level.getBlockState(middlePos);
        BlockState topState = level.getBlockState(topPos);

        if (bottomAge == 4 && middleState.isAir()) {
            level.setBlock(middlePos, SurvivalReimaginedModBlocks.CORN_STALK_MIDDLE.get().defaultBlockState(), 3);
        }

        if (bottomAge == 7 && topState.isAir()) {
            level.setBlock(topPos, SurvivalReimaginedModBlocks.CORN_STALK_TOP.get().defaultBlockState(), 3);
        }

        if (bottomAge >= 5 && bottomAge <= 11 && middleState.getBlock() instanceof CornStalkMiddleBlock) {
            int middleAge = bottomAge - 4;
            level.setBlock(middlePos, middleState.setValue(CornStalkMiddleBlock.AGE, middleAge), 3);
        }

        if (bottomAge >= 8 && bottomAge <= 11 && topState.getBlock() instanceof CornStalkTopBlock) {
            int topAge = bottomAge - 7;
            level.setBlock(topPos, topState.setValue(CornStalkTopBlock.AGE, topAge), 3);
        }
    }

    public static void registerBreakEvent() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (world.isClientSide()) return true;

            if (state.is(SurvivalReimaginedModBlocks.CORN_STALK_TOP.get())) {
                world.destroyBlock(pos.below(), false);
                world.destroyBlock(pos.below(2), false);

            } else if (state.is(SurvivalReimaginedModBlocks.CORN_STALK_MIDDLE.get())) {
                world.destroyBlock(pos.above(), false);
                world.destroyBlock(pos.below(), false);

            } else if (state.is(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get())) {
                world.destroyBlock(pos.above(), false);
                world.destroyBlock(pos.above(2), false);
            }
            return true;
        });
    }
}




