package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FruitBearingLeavesBlock extends LeavesBlock {
    private final Supplier<? extends Block> fruitBlockSupplier;
    private final double growthChance;
    private static final int TICK_RATE = 400;

    public static FruitBearingLeavesBlock create(Properties properties, Supplier<? extends Block> fruitBlockSupplier, double growthChance) {
        return new FruitBearingLeavesBlock(properties, fruitBlockSupplier, growthChance);
    }
    public FruitBearingLeavesBlock(Properties properties, Supplier<? extends Block> fruitBlockSupplier, double growthChance) {
        super(properties);
        this.fruitBlockSupplier = fruitBlockSupplier;
        this.growthChance = growthChance;
    }
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }


    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        if (!world.isClientSide()) {
            world.scheduleTick(pos, this, TICK_RATE);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        BlockState updatedShape = super.updateShape(state, direction, neighborState, world, pos, neighborPos);

        if (!world.isClientSide() && world instanceof ServerLevel serverWorld) {
            if (!serverWorld.getBlockTicks().hasScheduledTick(pos, this)) {
                serverWorld.scheduleTick(pos, this, TICK_RATE);
            }
        }
        return updatedShape;
    }


    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

        world.scheduleTick(pos, this, TICK_RATE);

        if (state.getValue(PERSISTENT) || state.getValue(DISTANCE) > 7) {
            return;
        }
        BlockPos belowPos = pos.below();
        if (world.isEmptyBlock(belowPos)) {
            double roll = random.nextDouble();

            if (roll < this.growthChance) {
                world.setBlock(belowPos, this.fruitBlockSupplier.get().defaultBlockState(), 3);
            }
        }
    }
}
