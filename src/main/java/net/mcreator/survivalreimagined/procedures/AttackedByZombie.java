package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;

public class AttackedByZombie {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            Entity sourceEntity = source.getEntity();

            execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceEntity);
            return true;
        });
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceEntity) {
        if (entity == null || sourceEntity == null) return;

        if (SurvivalReimaginedModConfig.VALUES.zombification) {
            if (!(SurvivalReimaginedModVariables.getPlayerVariables(entity).ZombificationImmune)) {
                if (!(entity instanceof LivingEntity livingEntity && livingEntity.isBlocking())) {
                    if (sourceEntity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:zombies")))
                    || sourceEntity instanceof LivingEntity livingSource && livingSource.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.ZOMBIFICATION.get()))) {
                        if (!(entity instanceof LivingEntity livingTarget && livingTarget.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.ZOMBIFICATION.get())))) {
                            if (Math.random() < SurvivalReimaginedModConfig.VALUES.zombification_chance) {
                                if (world instanceof ServerLevel serverLevel) {
                                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger = 0;
                                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).setDirty();

                                    if (entity instanceof LivingEntity living && !living.level().isClientSide()) {
                                        living.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.ZOMBIFICATION.get()), (int) SurvivalReimaginedModConfig.VALUES.zombification_time, 1, true, false));
                                    }
                                    if (world instanceof ServerLevel level) {
                                        level.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1);
                                    }
                                    if (entity instanceof ServerPlayer player) {
                                        AdvancementHolder advancementHolder = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:zombification_adv"));
                                        if (advancementHolder != null) {
                                            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancementHolder);
                                            if (!progress.isDone()) {
                                                for (String criteria : progress.getRemainingCriteria()) {
                                                    player.getAdvancements().award(advancementHolder, criteria);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        ItemStack mainHand = entity instanceof LivingEntity living ? living.getMainHandItem() : ItemStack.EMPTY;
                        ItemStack offHand = entity instanceof LivingEntity living ? living.getOffhandItem() : ItemStack.EMPTY;
                        if (mainHand.getItem() == Items.SHIELD) {
                            if (world instanceof ServerLevel level) {
                                mainHand.hurtAndBreak(1, level, null, item -> {});
                            }
                        } else if (offHand.getItem() == Items.SHIELD) {
                            if (world instanceof ServerLevel level) {
                                offHand.hurtAndBreak(1, level, null, item -> {});
                            }
                        }
                    }
                }
            }
        }
    }
}
