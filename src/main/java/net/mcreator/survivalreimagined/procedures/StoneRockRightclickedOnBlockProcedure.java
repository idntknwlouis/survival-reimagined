package net.mcreator.survivalreimagined.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;

public class StoneRockRightclickedOnBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
		BlockPos target = BlockPos.containing(x, y + 1, z);
		if (!world.isEmptyBlock(target)) {
			return;
		}

		world.setBlock(target, SurvivalReimaginedModBlocks.STONE_ROCK_BLOC.get().defaultBlockState(), 3);
		itemstack.shrink(1);
		SurvivalReimaginedMod.queueServerWork(1, () -> {
			if (world instanceof Level level) {
				level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.STONE_BREAK,
						SoundSource.BLOCKS, 1.0f, 0.8f);
			}
		});
	}
}
