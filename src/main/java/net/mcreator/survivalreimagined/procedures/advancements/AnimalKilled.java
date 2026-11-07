package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AnimalKilled {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((LivingEntity entity, DamageSource damageSource, float baseDamageTaken, float damageTaken, boolean blocked) -> {
            if (entity != null && damageSource.getEntity() != null) {
                execute(entity, damageSource.getEntity());
            }
        });
    }

    public static void execute(Entity entity, Entity sourceEntity) {
        if (entity == null || sourceEntity == null) return;

        if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("c:animal")))) {
            if (sourceEntity instanceof Player) {
                if (sourceEntity instanceof ServerPlayer player) {
                    AdvancementHolder adv = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:animal_slaughter"));
                    if (adv != null) {
                        AdvancementProgress advancementProgress = player.getAdvancements().getOrStartProgress(adv);
                        if (!advancementProgress.isDone()) {
                            for (String criteria : advancementProgress.getRemainingCriteria()) {
                                player.getAdvancements().award(adv, criteria);
                            }
                        }
                    }
                }
            }
        }
    }
}
