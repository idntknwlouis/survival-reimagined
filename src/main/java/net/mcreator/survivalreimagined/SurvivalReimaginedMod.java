package net.mcreator.survivalreimagined;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.mcreator.survivalreimagined.init.*;
import net.mcreator.survivalreimagined.world.worldgen.SurvivalReimaginedModOverworldRegion;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.MinecraftServer;


import net.mcreator.survivalreimagined.util.RuneEffects;
import net.mcreator.survivalreimagined.util.CropEffects;
import net.mcreator.survivalreimagined.util.FoodSpoilage;
import net.mcreator.survivalreimagined.util.CrimsonThreadDrops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terrablender.api.Regions;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SurvivalReimaginedMod implements ModInitializer {
	public static final String MODID = "survival_reimagined";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	private record ScheduledWork(long executeAtTick, Runnable action) implements Comparable<ScheduledWork> {
		@Override
		public int compareTo(ScheduledWork other) {
			return Long.compare(executeAtTick, other.executeAtTick);
		}
	}

	private static final Queue<ScheduledWork> WORK_TO_BE_SCHEDULED = new ConcurrentLinkedQueue<>();
	private static final PriorityQueue<ScheduledWork> WORK_QUEUE = new PriorityQueue<>();

	@Override
	public void onInitialize() {
		SurvivalReimaginedModSounds.register();
		SurvivalReimaginedModEntities.register();
		SurvivalReimaginedModParticleTypes.register();
		SurvivalReimaginedModBlocks.register();
		SurvivalReimaginedModArmorMaterials.register();
		SurvivalReimaginedModItems.register();
		SurvivalReimaginedModBlockEntities.register();
		SurvivalReimaginedModMenus.register();
		SurvivalReimaginedModMobEffects.register();
		SurvivalReimaginedModFeatures.register();
		SurvivalReimaginedModRecipeTypes.register();
		SurvivalReimaginedModWoodTypes.register();
		SurvivalReimaginedModTabs.register();
		SurvivalReimaginedModBiomeModifications.register();
		RuneEffects.register();
		CropEffects.register();
		CrimsonThreadDrops.register();

		UseBlockCallback.EVENT.register((player, level, hand, hit) -> {
			if (hand == net.minecraft.world.InteractionHand.MAIN_HAND) {
				FoodSpoilage.updateWoodenContainer(level, hit.getBlockPos());
			}

			ItemStack stack = player.getItemInHand(hand);
			if (!stack.is(Items.FLINT)) {
				return InteractionResult.PASS;
			}

			BlockPos clicked = hit.getBlockPos();
			BlockPos placePos = level.getBlockState(clicked).canBeReplaced()
					? clicked
					: clicked.relative(hit.getDirection());
			BlockState placed = SurvivalReimaginedModBlocks.FLINTBLOCK.get().defaultBlockState();

			if (!level.getBlockState(placePos).canBeReplaced() || !placed.canSurvive(level, placePos)) {
				return InteractionResult.PASS;
			}

			if (!level.isClientSide()) {
				level.setBlock(placePos, placed, 11);
				level.playSound(null, placePos, SoundEvents.DRIPSTONE_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
			}
			return InteractionResult.SUCCESS;
		});

		ServerTickEvents.END_SERVER_TICK.register(SurvivalReimaginedMod::onServerTick);
		LOGGER.info("Initializing Survival Reimagined Fabric port");
	}

	public static ResourceLocation asResource(String path) {
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}

	public static void queueServerWork(int delay, Runnable action) {
		if (action == null) {
			return;
		}

		// The actual target tick is assigned from the next server tick.
		WORK_TO_BE_SCHEDULED.add(new ScheduledWork(Math.max(delay, 0), action));
	}

	private static void onServerTick(MinecraftServer server) {
		long currentTick = server.getTickCount();

		ScheduledWork work;
		while ((work = WORK_TO_BE_SCHEDULED.poll()) != null) {
			WORK_QUEUE.add(new ScheduledWork(currentTick + work.executeAtTick(), work.action()));
		}

		while (!WORK_QUEUE.isEmpty() && WORK_QUEUE.peek().executeAtTick() <= currentTick) {
			WORK_QUEUE.poll().action().run();
		}
	}
}
