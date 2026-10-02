package net.mcreator.survivalreimagined.block;

import com.google.common.collect.ImmutableMap;
import net.mcreator.survivalreimagined.block.entity.BananaGrowBlockEntity;
import net.mcreator.survivalreimagined.procedures.GrowBanana;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class BananaGrowBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private final Map<BlockState, VoxelShape> shapes;

    public BananaGrowBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.WOOD)
                .strength(2, 3)
                .noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(AXIS, Direction.Axis.Y));
        this.shapes = this.getShapeForEachState(state -> {
            return switch (state.getValue(AXIS)) {
                case X -> Block.box(0,4,4,16,12,12);
                case Y -> Block.box(4,0,4,12,16,12);
                case Z -> Block.box(4,4,0,12,12,16);
            };
        });
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.shapes.get(state);
    }
    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState defaultState = super.getStateForPlacement(context);
        return defaultState != null ? defaultState.setValue(AXIS, context.getClickedFace().getAxis()) : this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }
    @Override
    protected void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 400);
    }
    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        GrowBanana.execute(world, pos.getX(), pos.getY(), pos.getZ());
        world.scheduleTick(pos, this, 400);
    }
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BananaGrowBlockEntity(pos, state);
    }
    @Override
    protected boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(id, param);
    }
    @Override
    protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BananaGrowBlockEntity be) {
                Containers.dropContents(world, pos, be);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }
    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
    @Override
    protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BananaGrowBlockEntity be) {
            return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
        }
        return 0;
    }
}
