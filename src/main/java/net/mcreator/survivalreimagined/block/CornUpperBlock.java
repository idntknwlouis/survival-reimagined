package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.function.Supplier;

public class CornUpperBlock extends BushBlock {
	private final Supplier<? extends Block> lower;
	private final int maxAge;

	public CornUpperBlock(Supplier<? extends Block> lower, int maxAge) {
		super(BlockBehaviour.Properties.of().sound(SoundType.CROP).instabreak().noCollission());
		this.lower = lower;
		this.maxAge = maxAge;
		this.registerDefaultState(this.stateDefinition.any().setValue(SimpleAgeCropBlock.AGE, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SimpleAgeCropBlock.AGE);
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter level, BlockPos pos) {
		return floor.is(lower.get());
	}

	public BlockState withAge(int age) {
		return defaultBlockState().setValue(SimpleAgeCropBlock.AGE, Math.max(0, Math.min(maxAge, age)));
	}
}
