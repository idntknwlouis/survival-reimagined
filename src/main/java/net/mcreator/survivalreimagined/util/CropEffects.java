package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.block.SimpleAgeCropBlock;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public final class CropEffects {
	private CropEffects() {
	}

	public static void register() {
		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			if (!(level instanceof ServerLevel serverLevel) || player.isCreative()) return;
			handleHarvest(serverLevel, pos, state);
		});
	}

	private static void handleHarvest(ServerLevel level, BlockPos pos, BlockState state) {
		Block block = state.getBlock();
		int age = state.hasProperty(SimpleAgeCropBlock.AGE) ? state.getValue(SimpleAgeCropBlock.AGE) : 0;

		if (block == SurvivalReimaginedModBlocks.HEMP.get()) {
			if (age >= 3) {
				drop(level, pos, SurvivalReimaginedModItems.HEMP_LEAF.get(), 1 + level.random.nextInt(2));
				drop(level, pos, SurvivalReimaginedModItems.HEMP_SEEDS.get(), 1 + level.random.nextInt(2));
			} else {
				drop(level, pos, SurvivalReimaginedModItems.HEMP_SEEDS.get(), 1);
			}
			return;
		}

		if (block == SurvivalReimaginedModBlocks.WILD_CARROT.get()) {
			drop(level, pos, Items.CARROT, age >= 3 ? 2 + level.random.nextInt(3) : 1);
			return;
		}

		if (block == SurvivalReimaginedModBlocks.WHEAT_CROP.get() || block == SurvivalReimaginedModBlocks.WILD_WHEAT.get()) {
			if (age >= 7) {
				drop(level, pos, Items.WHEAT, 1);
				drop(level, pos, SurvivalReimaginedModItems.WHEAT_SEEDS.get(), 1 + level.random.nextInt(3));
			} else {
				drop(level, pos, SurvivalReimaginedModItems.WHEAT_SEEDS.get(), 1);
			}
			return;
		}

		if (block == SurvivalReimaginedModBlocks.POTATOES.get() || block == SurvivalReimaginedModBlocks.WILD_POTATOES.get()) {
			drop(level, pos, SurvivalReimaginedModItems.POTATO.get(), age >= 3 ? 2 + level.random.nextInt(3) : 1);
			return;
		}

		if (block == SurvivalReimaginedModBlocks.STRAWBERRY_PLANT.get()) {
			drop(level, pos, SurvivalReimaginedModItems.STRAWBERRY.get(), age >= 5 ? 2 + level.random.nextInt(3) : 1);
			return;
		}

		if (block == SurvivalReimaginedModBlocks.RASPBERRY_PLANT.get()) {
			drop(level, pos, SurvivalReimaginedModItems.RASPBERRY.get(), age >= 5 ? 2 + level.random.nextInt(3) : 1);
			return;
		}

		if (block == SurvivalReimaginedModBlocks.RYE_SEEDS.get() || block == SurvivalReimaginedModBlocks.WILD_RYE.get()) {
			if (age >= 6) {
				drop(level, pos, SurvivalReimaginedModItems.RYE.get(), 1);
				drop(level, pos, SurvivalReimaginedModItems.RYE_SEEDS.get(), 1 + level.random.nextInt(3));
			} else {
				drop(level, pos, SurvivalReimaginedModItems.RYE_SEEDS.get(), 1);
			}
			return;
		}

		if (block == SurvivalReimaginedModBlocks.SPELT_SEEDS.get() || block == SurvivalReimaginedModBlocks.WILD_SPELT.get()) {
			if (age >= 6) {
				drop(level, pos, SurvivalReimaginedModItems.SPELT.get(), 1);
				drop(level, pos, SurvivalReimaginedModItems.SPELT_SEEDS.get(), 1 + level.random.nextInt(3));
			} else {
				drop(level, pos, SurvivalReimaginedModItems.SPELT_SEEDS.get(), 1);
			}
			return;
		}

		if (block == SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get()) {
			if (age >= 11) {
				drop(level, pos, SurvivalReimaginedModItems.CORN.get(), 2 + level.random.nextInt(2));
				drop(level, pos, SurvivalReimaginedModItems.CORN_SEEDS.get(), 1 + level.random.nextInt(2));
			} else {
				drop(level, pos, SurvivalReimaginedModItems.CORN_SEEDS.get(), 1);
			}
		}
	}

	private static void drop(ServerLevel level, BlockPos pos, Item item, int count) {
		if (count > 0) Block.popResource(level, pos, new ItemStack(item, count));
	}
}
