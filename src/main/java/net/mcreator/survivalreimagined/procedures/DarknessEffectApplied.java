
package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.mcreator.survivalreimagined.potion.DarknessLevelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

import net.mcreator.survivalreimagined.network.DynamicLightNetworking;

public class DarknessEffectApplied {

    public static void onPlayerTick(ServerPlayer player) {
        execute(player.serverLevel(), player);
    }

    public static void execute(LevelAccessor world, Entity entity) {
        if (!(entity instanceof ServerPlayer player)) return;

        if (!SurvivalReimaginedModConfig.VALUES.paranoia_and_fear) return;

        if (player.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) return;

        int brightness = world.getMaxLocalRawBrightness(BlockPos.containing(entity.getX(), entity.getY() + 0.2, entity.getZ()));

        boolean hasDynamicLightExemption = DynamicLightNetworking.hasDynamicLightExemption(player);

        var PARANOIA = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.PARANOIA.get());
        var FEAR = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.FEAR.get());

        if (brightness <= SurvivalReimaginedModConfig.VALUES.darkness_level && !hasDynamicLightExemption) {

            int currentTick = DarknessLevelHelper.getEffectTick(player);
            double delayThreshold = SurvivalReimaginedModConfig.VALUES.paranoia_to_fear_delay;

            if (currentTick < delayThreshold) {
                player.addEffect(new MobEffectInstance(PARANOIA, 60, 0));
                awardAdvancement(player, "survival_reimagined:paranoia_adv");
            } else {
                player.removeEffect(PARANOIA);
                boolean firstFearApplication = !player.hasEffect(FEAR);
                player.addEffect(new MobEffectInstance(FEAR, 60, 0));
                if (firstFearApplication) {
                    awardAdvancement(player, "survival_reimagined:fear_adv");
                }
            }

        } else if (brightness >= SurvivalReimaginedModConfig.VALUES.light_level) {
            DarknessLevelHelper.resetEffectTick(player);
        }
    }

    private static void awardAdvancement(ServerPlayer player, String advancementId) {
        var advancement = player.server.getAdvancements().get(
                ResourceLocation.parse(advancementId)
        );

        if (advancement == null) return;
        var progress = player.getAdvancements().getOrStartProgress(advancement);
        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria()) {
                player.getAdvancements().award(advancement, criteria);
            }
        }
    }
}
