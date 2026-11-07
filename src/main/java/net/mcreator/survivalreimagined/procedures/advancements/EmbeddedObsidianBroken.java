package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class EmbeddedObsidianBroken {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            execute(world, pos.getX(), pos.getY(), pos.getZ(), player);
        });
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        if ((world.getBlockState(BlockPos.containing(x,y,z))).getBlock() == SurvivalReimaginedModBlocks.EMBEDDED_OBSIDIAN.get()) {
            if (entity instanceof ServerPlayer player) {
                AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:obsidian_in_the_rocks"));
                if (advancement != null) {
                    AdvancementProgress advancementProgress = player.getAdvancements().getOrStartProgress(advancement);
                    if (!advancementProgress.isDone()) {
                        for (String criteria : advancementProgress.getRemainingCriteria())
                            player.getAdvancements().award(advancement, criteria);
                    }
                }
            }
        }
    }
}
