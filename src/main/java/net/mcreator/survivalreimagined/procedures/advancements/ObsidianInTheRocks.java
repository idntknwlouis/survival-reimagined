package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ObsidianInTheRocks {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if (world.isClientSide()) return;

            if (state.is(SurvivalReimaginedModBlocks.EMBEDDED_OBSIDIAN.get())) {
                if (player instanceof ServerPlayer serverPlayer) {
                    AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:obsidian_in_the_rocks"));
                    if (advancement != null) {
                        AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);

                        if (!progress.isDone()) {
                            for (String criteria : progress.getRemainingCriteria()) {
                                serverPlayer.getAdvancements().award(advancement, criteria);
                            }
                        }
                    }
                }
            }
        });
    }
}
