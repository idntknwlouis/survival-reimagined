package net.mcreator.survivalreimagined;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBiomeModifications;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModFeatures;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModParticleTypes;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModRecipeTypes;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModTabs;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModWoodTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		SurvivalReimaginedModParticleTypes.register();
		SurvivalReimaginedModBlocks.register();
		SurvivalReimaginedModItems.register();
		SurvivalReimaginedModBlockEntities.register();
		SurvivalReimaginedModMenus.register();
		SurvivalReimaginedModMobEffects.register();
		SurvivalReimaginedModFeatures.register();
		SurvivalReimaginedModRecipeTypes.register();
		SurvivalReimaginedModWoodTypes.register();
		SurvivalReimaginedModTabs.register();
		SurvivalReimaginedModBiomeModifications.register();

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
