package net.mcreator.survivalreimagined.block;

import net.mcreator.survivalreimagined.block.entity.CrudeTorchBlockEntity;
import net.mcreator.survivalreimagined.procedures.torch.LitClock;
import net.mcreator.survivalreimagined.procedures.torch.TorchAdded;
import net.mcreator.survivalreimagined.procedures.torch.TorchParticles;
import net.mcreator.survivalreimagined.procedures.torch.TorchPlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrudeTorch extends Block implements EntityBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private static final VoxelShape STANDING = Block.box(6, 0, 6, 10,10,10);
    private static final VoxelShape SOUTH = Block.box(6, 3, 11, 10,13,16);
    private static final VoxelShape NORTH = Block.box(6, 3, 0, 10, 13, 5);
    private static final VoxelShape EAST = Block.box(11, 3, 6, 16,13,10);
    private static final VoxelShape WEST = Block.box(0, 3, 6, 5, 13, 10);

    public CrudeTorch(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(LIT) ? 14 : 0));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, true).setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();

        if (clickedFace == Direction.UP) {
            return defaultBlockState().setValue(FACING, Direction.UP).setValue(LIT, false);
        }

        if (clickedFace.getAxis().isHorizontal()) {
            BlockState state = defaultBlockState().setValue(FACING, clickedFace.getOpposite()).setValue(LIT, false);
            return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
        }
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> STANDING;
        };
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (level instanceof LevelAccessor world) {
            return TorchPlacement.execute(world, pos.getX(), pos.getY(), pos.getZ());
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, facingState, level, pos, facingPos);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, level, pos, oldState, moving);
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 20);
            TorchAdded.execute(level, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        LitClock.execute(level, pos.getX(), pos.getY(), pos.getZ(), state);
        level.scheduleTick(pos, this, 20);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        TorchParticles.execute(level, pos.getX(), pos.getY(), pos.getZ(), state);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrudeTorchBlockEntity(pos, state);
    }
}
