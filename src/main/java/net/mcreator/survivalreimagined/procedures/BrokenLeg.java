package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class BrokenLeg {
    public static void onEntityFall(LivingEntity entity, double distance) {
        if (entity != null) {
            execute(entity.level(), entity, distance);
        }
    }
    public static void execute(LevelAccessor world, Entity entity) {
        execute(world, entity, entity.fallDistance);
    }

    private static void execute(LevelAccessor world, Entity entity, double fallDistance) {
        if (entity == null)
            return;

        if (entity instanceof LivingEntity livingEntity) {
            if (Boolean.TRUE.equals(SurvivalReimaginedModConfig.VALUES.broken_legs)) {
                if (fallDistance >= 5) {
                    if (Math.random() < (double) SurvivalReimaginedModConfig.VALUES.broken_leg_chance) {
                        var brokenleg = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.BROKEN_LEG.get());
                        MobEffectInstance effectInstance = new MobEffectInstance(
                                brokenleg, 1200, 0
                        );
                        System.out.println("Applying Broken Leg effect to: "
                                + livingEntity.getName().getString());


                        livingEntity.addEffect(effectInstance);
                        boolean applied = livingEntity.addEffect(effectInstance);

                        System.out.println("Effect applied: " + applied);

                    }
                }
            }
        }
    }
}
