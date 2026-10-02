package net.mcreator.survivalreimagined.block.fruit;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.procedures.GrowingLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.mcreator.survivalreimagined.block.fruit.FruitTypeConfig;
import org.jetbrains.annotations.Nullable;

import static com.mojang.serialization.Codec.lazyInitialized;

public class FruitBlock extends BaseEntityBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 2);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final FruitTypeConfig config;
    private final ImmutableMap<BlockState, VoxelShape> shapes;

    private final MapCodec<FruitBlock> dynamicCodec = MapCodec.unit(() -> this);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return dynamicCodec;
    }


    public FruitBlock(FruitTypeConfig config) {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.CROP)
                .instabreak()
                .noCollission()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor((bs, br, bp) -> false)
                .offsetType(config.usesXzOffset() ? OffsetType.XZ : OffsetType.NONE));

        this.config = config;
        BlockState defaultBlockState = this.getStateDefinition().any().setValue(AGE, 0);
        if (config.hasFacing()) {
            defaultBlockState = defaultBlockState.setValue(FACING, Direction.NORTH);
        }
        this.registerDefaultState(defaultBlockState);
        this.shapes = this.makeShapes();
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (world.isClientSide()) return null;

        return createTickerHelper(type, this.config.blockEntityType().get(), (world1, pos1, state1, blockEntity1) -> {
            if (world1.getGameTime() % 20 == 0) {
                GrowingLogic.execute(world1, pos1.getX(), pos1.getY(), pos1.getZ(), state1);
            }
        });
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(world, player, pos, state, blockEntity, tool);
        if (this.config.harvestCondition() != null) {
            this.config.harvestCondition().canHarvest(world, pos, player, state);
        }
    }


    private ImmutableMap<BlockState, VoxelShape> makeShapes() {
        return this.getShapeForEachState(state -> config.shapeProvider().apply(state, state.getValue(AGE)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape rawShape = shapes.get(state);
        if (config.usesXzOffset()) {
            Vec3 offset = state.getOffset(world, pos);
            return rawShape.move(offset.x, offset.y, offset.z);
        }
        return rawShape;
    }
    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
        builder.add(FACING);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState().setValue(AGE, 0);
        if (config.hasFacing()) {
            Direction clickedFace = context.getClickedFace();
            if (clickedFace.getAxis() == Direction.Axis.Y) {
                return state.setValue(FACING, Direction.NORTH);
            }
            return state.setValue(FACING, clickedFace);
        }
        return state.setValue(FACING, Direction.NORTH);
    }
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if (config.hasFacing()) {
            return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
        }
        return super.rotate(state, rot);
    }
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        if (config.hasFacing()) {
            return state.rotate(mirror.getRotation(state.getValue(FACING)));
        }
        return super.mirror(state, mirror);
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (config.placementCondition() != null && world instanceof LevelAccessor levelAccessor) {
            return  config.placementCondition().canSurvive(levelAccessor, pos);
        }
        return world.getBlockState(pos.above()).is(BlockTags.LEAVES);
    }
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (config.placementCondition() != null && !state.canSurvive(world, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }
    public boolean checkHarvestCondition(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        boolean base = player.hasCorrectToolForDrops(state);
        if (config.harvestCondition() != null) {
            Level level = player.level();
            return base && config.harvestCondition().canHarvest(level, pos, player, state);
        }
        return base;
    }
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 30;
    }
    public PathType getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
        return PathType.BLOCKED;
    }
    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }
    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(state, world, pos, neighborBlock, fromPos, moving);
        FruitNeighborBlockChanges.execute(world, pos.getX(), pos.getY(), pos.getZ(), state);
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        var type = this.config.blockEntityType().get();
        if (type == null) {
            return null;
        }
        return new FruitBlockEntity(pos, state, config);
    }
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof FruitBlockEntity container) {
            return AbstractContainerMenu.getRedstoneSignalFromContainer(container);
        }
        return 0;
    }
    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(id, param);
    }
    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FruitBlockEntity container) {
                Containers.dropContents(world, pos, container);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }
}
