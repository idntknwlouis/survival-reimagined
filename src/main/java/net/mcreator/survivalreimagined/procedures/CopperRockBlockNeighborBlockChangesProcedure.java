package net.mcreator.survivalreimagined.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public class CopperRockBlockNeighborBlockChangesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (!world.getBlockState(BlockPos.containing(x, y - 1, z)).is(Blocks.AIR)) {
			return;
		}

		world.destroyBlock(BlockPos.containing(x, y, z), false);
		if (world instanceof ServerLevel level) {
			ItemEntity drop = new ItemEntity(level, x + 0.5, y + 0.5, z + 0.5,
					new ItemStack(SurvivalReimaginedModItems.COPPER_CHUNK.get()));
			drop.setPickUpDelay(10);
			level.addFreshEntity(drop);
		}
	}
}
