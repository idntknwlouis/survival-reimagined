package net.mcreator.survivalreimagined.procedures.bloodmoon;

import net.mcreator.survivalreimagined.entity.BloodMoonZombieEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class DisableSpawning {
    public static void onEntityTick(Entity entity, ServerLevel world) {
        execute(world, entity);
    }

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        if (SurvivalReimaginedModConfig.VALUES.disable_normal_mob_spawning) {
            if (world instanceof ServerLevel serverLevel) {
                if (SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isBloodMoon) {
                    if ((entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:skeletons")))
                    || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:zombies")))
                    || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:spiders"))))
                    && !(entity instanceof BloodMoonZombieEntity)
                    && !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("c:blood_moon/spider")))) {
                        entity.discard();
                    }
                } else {
                    if (entity instanceof BloodMoonZombieEntity || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("c:blood_moon/spider")))) {
                        entity.discard();
                    }
                }
            }
        }
    }
}
