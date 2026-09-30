package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class GrowBanana {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        BlockPos currentPos = BlockPos.containing(x,y,z);

        if (Math.random() < 0.05) {
            double numGen = getBlockNBTNumber(world, currentPos, "NumberGen");

            if (numGen == 1) {
                BlockPos targetPos = BlockPos.containing(x,y,z -1);
                if (world.isEmptyBlock(targetPos)) {
                    world.setBlock(targetPos, blockStateWithDirection(SurvivalReimaginedModBlocks.BANANA_CLUSTER.get().defaultBlockState(), Direction.NORTH),3);
                }
            } else if (numGen == 2) {
                BlockPos targetPos = BlockPos.containing(x,y,z -1);
                if (world.isEmptyBlock(targetPos)) {
                    world.setBlock(targetPos, blockStateWithDirection(SurvivalReimaginedModBlocks.BANANA_CLUSTER.get().defaultBlockState(), Direction.SOUTH),3);
                }
            } else if (numGen == 3) {
                BlockPos targetPos = BlockPos.containing(x,y,z -1);
                if (world.isEmptyBlock(targetPos)) {
                    world.setBlock(targetPos, blockStateWithDirection(SurvivalReimaginedModBlocks.BANANA_CLUSTER.get().defaultBlockState(), Direction.WEST),3);
                }
            } else if (numGen == 4) {
                BlockPos targetPos = BlockPos.containing(x,y,z -1);
                if (world.isEmptyBlock(targetPos)) {
                    world.setBlock(targetPos, blockStateWithDirection(SurvivalReimaginedModBlocks.BANANA_CLUSTER.get().defaultBlockState(), Direction.EAST),3);
                }
            }
        }
        if (!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(currentPos);
            BlockState bs = world.getBlockState(currentPos);
            if (blockEntity != null) {
                setBlockNBTNumber(world, currentPos, "NumberGen", Mth.nextInt(RandomSource.create(),1,4));
            }
            if (world instanceof Level level) {
                level.sendBlockUpdated(currentPos, bs, bs, 3);
            }
        }
    }
    private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            CompoundTag nbt = new CompoundTag();
            blockEntity.saveWithFullMetadata(world.registryAccess());
            return nbt.getDouble(tag);
        }
        return -1;
    }
    private static void setBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag, double value) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            CompoundTag nbt = new CompoundTag();
            blockEntity.saveWithFullMetadata(world.registryAccess());
            nbt.putDouble(tag, value);
            blockEntity.loadWithComponents(nbt, world.registryAccess());
            blockEntity.setChanged();
        }
    }
    private static BlockState blockStateWithDirection(BlockState blockState, Direction newValue) {
        Property<?> prop = blockState.getBlock().getStateDefinition().getProperty("facing");
        if (prop instanceof DirectionProperty dp && dp.getPossibleValues().contains(newValue))
            return blockState.setValue(dp, newValue);
        prop = blockState.getBlock().getStateDefinition().getProperty("axis");
        return prop instanceof EnumProperty ep && ep.getPossibleValues().contains(newValue.getAxis()) ? blockState.setValue(ep, newValue.getAxis()) : blockState;
    }
}
