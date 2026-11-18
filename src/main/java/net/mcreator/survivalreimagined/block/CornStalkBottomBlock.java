package net.mcreator.survivalreimagined.block;

import net.mcreator.survivalreimagined.block.entity.CropGrowthBlockEntity;
import net.mcreator.survivalreimagined.procedures.GrowingLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CornStalkBottomBlock extends CornStalkBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 11);
    private static final TagKey<Block> SOIL = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:soil"));

    public CornStalkBottomBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(SOIL);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AGE)) {
            case 0 -> box(0,0,0,16,5,16);
            case 1 -> box(0,0,0,16,8,16);
            case 2 -> box(0,0,0,16,12,16);
            default -> box(0,0,0,16,16,16);
        };
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CropGrowthBlockEntity(pos, state);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (oldState.getBlock() != this) {
            level.scheduleTick(pos, this, 20);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        GrowingLogic.execute(level, pos.getX(), pos.getY(), pos.getZ(), state);
        if (level.getBlockState(pos).getBlock() == this) {
            level.scheduleTick(pos, this, 20);
        }
    }
}
