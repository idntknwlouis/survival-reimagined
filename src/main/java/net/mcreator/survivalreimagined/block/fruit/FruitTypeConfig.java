package net.mcreator.survivalreimagined.block.fruit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public record FruitTypeConfig(
        String name,
        Supplier<BlockEntityType<?>> blockEntityType,
        BiFunction<BlockState, Integer, VoxelShape> shapeProvider,
        PlacementCondition placementCondition,
        HarvestCondition harvestCondition,
        boolean usesXzOffset,
        boolean hasFacing
) {
    @FunctionalInterface
    public interface PlacementCondition {
        boolean canSurvive(LevelAccessor world, BlockPos pos);
    }
    @FunctionalInterface
    public interface HarvestCondition {
        boolean canHarvest(Level world, BlockPos pos, Player player, BlockState state);
    }
}
