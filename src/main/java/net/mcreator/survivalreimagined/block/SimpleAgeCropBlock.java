package net.mcreator.survivalreimagined.block;

import com.mojang.serialization.MapCodec;
import net.mcreator.survivalreimagined.block.entity.CropGrowthBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SimpleAgeCropBlock extends BushBlock implements BonemealableBlock, EntityBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 15);
    private static final MapCodec<SimpleAgeCropBlock> CODEC = MapCodec.unit(() -> new SimpleAgeCropBlock(15));
    private static final TagKey<net.minecraft.world.level.block.Block> FARMLAND =
            TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:farmland"));

    private final int maxAge;
    private final boolean farmlandOnly;

    public SimpleAgeCropBlock(int maxAge) {
        this(maxAge, true);
    }

    public SimpleAgeCropBlock(int maxAge, boolean farmlandOnly) {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.CROP)
                .instabreak()
                .noCollission()
                .randomTicks());
        this.maxAge = maxAge;
        this.farmlandOnly = farmlandOnly;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    public SimpleAgeCropBlock(IntegerProperty ignoredAgeProperty, int maxAge) {
        this(maxAge, true);
    }

    public SimpleAgeCropBlock(IntegerProperty ignoredAgeProperty, int maxAge, boolean farmlandOnly) {
        this(maxAge, farmlandOnly);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(AGE);
    }

    public int getAge(BlockState state) {
        return state.getValue(AGE);
    }

    public boolean isMaxAge(BlockState state) {
        return getAge(state) >= this.maxAge;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
        return this.farmlandOnly
                ? floor.is(FARMLAND)
                : floor.is(Blocks.GRASS_BLOCK) || floor.is(Blocks.DIRT) || floor.is(Blocks.COARSE_DIRT) || floor.is(Blocks.PODZOL);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        // Cultivated crops use the original Survival Reimagined clocked growth system.
        // Wild plants keep lightweight vanilla-style random growth.
        return !this.farmlandOnly && !isMaxAge(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.farmlandOnly && level.getRawBrightness(pos, 0) >= 9 && !isMaxAge(state) && random.nextInt(5) == 0) {
            level.setBlock(pos, state.setValue(AGE, getAge(state) + 1), 2);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.farmlandOnly ? new CropGrowthBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (!this.farmlandOnly || level.isClientSide() || type != SurvivalReimaginedModBlockEntities.CROP_GROWTH.get()) {
            return null;
        }
        return (world, pos, currentState, blockEntity) ->
                GrowingLogic.execute(world, pos.getX(), pos.getY(), pos.getZ(), currentState);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        double height = 2.0D + (14.0D * getAge(state) / Math.max(1, this.maxAge));
        return box(0.0D, 0.0D, 0.0D, 16.0D, Math.min(16.0D, height), 16.0D);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int age = Math.min(this.maxAge, getAge(state) + 1 + random.nextInt(2));
        level.setBlock(pos, state.setValue(AGE, age), 2);
    }
}
