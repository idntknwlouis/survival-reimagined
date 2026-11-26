package net.mcreator.survivalreimagined.block.fertilesoil;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class FertilitySoilBlock extends FarmBlock implements EntityBlock {
    private final Block dirtBlock;
    private final Supplier<BlockEntityType<? extends FertilitySoilBlockEntity>> blockEntityTypeSupplier;



    public FertilitySoilBlock(Properties properties, Supplier<BlockEntityType<? extends FertilitySoilBlockEntity>> blockEntityTypeSupplier, Block dirtBlock) {
        super(properties);
        this.dirtBlock = dirtBlock;
        this.blockEntityTypeSupplier = blockEntityTypeSupplier;
    }
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void onPlace(BlockState blockState, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockState, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide && level.random.nextFloat() < fallDistance - 0.5f && entity instanceof LivingEntity && (entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING))
                && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f) {
            level.setBlockAndUpdate(pos, dirtBlock.defaultBlockState());
        }
        entity.causeFallDamage(fallDistance, 1.0f, level.damageSources().fall());
        entity.setPos(entity.getX(), Math.max(entity.getY(), pos.getY() + 1.0f), entity.getZ());
    }

    @Override
    public void tick(BlockState blockState, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockState, world, pos, random);
        FertilitySoilTickUpdate.execute(world, pos, blockState);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FertilitySoilBlockEntity(this.blockEntityTypeSupplier.get(), pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FertilitySoilBlockEntity soilBlockEntity) {
                Containers.dropContents(world, pos, soilBlockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof FertilitySoilBlockEntity soilBlockEntity) {
            return AbstractContainerMenu.getRedstoneSignalFromContainer(soilBlockEntity);
        }
        return 0;
    }
}
