package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;


public class HeartbeatFearDamageTick {
    public static void onEntityTick(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        var FEAR = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.FEAR.get());

        if  (world instanceof ServerLevel serverLevel) {
            if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(FEAR)) {
                SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).HeartBeat = SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).HeartBeat + 1;
                SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).setDirty();
            } else if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).HeartBeat == 100) {
                SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).HeartBeat = 0;
                SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).setDirty();
            }
        }
    }
}
