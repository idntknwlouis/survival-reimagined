package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.block.entity.BlockOfCharcoalBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockOfCharcoalTickUpdate {
    private static final BlockPos[] DIRECTIONS = {
      new BlockPos(1,0,0),
      new BlockPos(-1,0,0),
      new BlockPos(0,0,1),
      new BlockPos(0,0,-1),
      new BlockPos(0,1,0),
      new BlockPos(0,-1,0),
    };

    public static void execute(LevelAccessor world, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x,y,z);
        BlockPos above = pos.above();

        if (world.getBlockState(above).is(SurvivalReimaginedModBlocks.CAMPFIRE.get()) && world instanceof Level level && !level.isClientSide()) {
            setCanBurn(level, pos, true);
        }

        boolean enclosed =
                world.getBlockState(pos.east()).canOcclude()
                && world.getBlockState(pos.west()).canOcclude()
                && world.getBlockState(pos.south()).canOcclude()
                && world.getBlockState(pos.north()).canOcclude()
                && (world.getBlockState(above).canOcclude()
                        || world.getBlockState(above).is(SurvivalReimaginedModBlocks.CAMPFIRE.get()))
                && world.getBlockState(pos.below()).canOcclude();

        if (!enclosed) {
            return;
        }

        BlockOfCharcoalBlockEntity charcoal = getCharcoalEntity(world, pos);
        if (charcoal == null || !charcoal.canBurn()) {
            if (world instanceof Level level && !level.isClientSide()) {
                setCanBurn(level, pos, false);
            }
            return;
        }

        boolean hasAdjacentLog = false;
        for (BlockPos offset : DIRECTIONS) {
            BlockPos target = pos.offset(offset);
            BlockState state = world.getBlockState(target);
            if (state.is(BlockTags.LOGS)) {
                hasAdjacentLog = true;
                break;
            }
        }

        if (!hasAdjacentLog) {
            if (world instanceof Level level && !level.isClientSide()) {
                setCanBurn(level, pos, false);
            }
            return;
        }
        if (!(world instanceof Level level) || level.isClientSide()) {
            return;
        }
        charcoal.setCharcoalClock(charcoal.getCharcoalClock() + 1.0);

        if (charcoal.getCharcoalClock() == 500.0) {
            for (BlockPos offset : DIRECTIONS) {
                BlockPos target = pos.offset(offset);
                BlockState targetState = level.getBlockState(target);

                if (targetState.is(BlockTags.LOGS) && targetState.canOcclude()) {
                    BlockState charcoalState = copyCompatibleProperties(targetState, SurvivalReimaginedModBlocks.BLOCK_OF_CHARCOAL.get().defaultBlockState());
                    level.setBlock(target, charcoalState, 3);
                    setCanBurn(level, target, true);
                }
            }
            setCanBurn(level, pos, false);
        }
    }

    private static BlockState copyCompatibleProperties(BlockState from, BlockState to) {
        for (Property<?> oldProperty : from.getProperties()) {
            copyProperty(from, to, oldProperty);
            to = copyProperty(from, to, oldProperty);
        }
        return to;
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static BlockState copyProperty(BlockState from, BlockState to, Property<?> oldProperty) {
        var newProperty = to.getBlock().getStateDefinition().getProperty(oldProperty.getName());

        if (newProperty != null) {
            try {
                Comparable value = from.getValue((Property) oldProperty);
                to = to.setValue((Property) newProperty, value);
            } catch (IllegalArgumentException ignored) {}
        }
        return to;
    }
    private static BlockOfCharcoalBlockEntity getCharcoalEntity(LevelAccessor world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof BlockOfCharcoalBlockEntity charcoal ? charcoal : null;
    }
    private static void setCanBurn(Level level, BlockPos pos, boolean value) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BlockOfCharcoalBlockEntity charcoal) {
            charcoal.setCanBurn(value);
        }
    }
}
