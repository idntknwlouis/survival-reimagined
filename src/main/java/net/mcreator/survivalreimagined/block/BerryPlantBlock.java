package net.mcreator.survivalreimagined.block;

import net.mcreator.survivalreimagined.block.entity.BerryPlantBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.procedures.GrowingLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BerryPlantBlock extends SimpleAgeCropBlock {
	private final Supplier<? extends Item> berry;

	public BerryPlantBlock(Supplier<? extends Item> berry) {
		super(5);
		this.berry = berry;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return false;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, net.minecraft.world.level.BlockGetter level, BlockPos pos) {
		return floor.is(net.minecraft.tags.BlockTags.DIRT) || super.mayPlaceOn(floor, level, pos);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BerryPlantBlockEntity(pos, state);
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (getAge(state) < 5) return InteractionResult.PASS;

		if (!level.isClientSide()) {
			popResource(level, pos, new ItemStack(berry.get(), 2 + level.getRandom().nextInt(3)));
			level.setBlock(pos, state.setValue(AGE, 3), 2);
			level.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
		}
		return InteractionResult.SUCCESS;
	}
}
