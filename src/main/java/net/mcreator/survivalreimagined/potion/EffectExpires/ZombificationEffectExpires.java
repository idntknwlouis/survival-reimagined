package net.mcreator.survivalreimagined.potion.EffectExpires;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ZombificationEffectExpires {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        if (getEntityGameType(entity) == GameType.SURVIVAL) {
            if (world instanceof ServerLevel serverLevel) {
                if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger == 0) {
                    SurvivalReimaginedMod.queueServerWork(1, () -> {
                        if (entity instanceof LivingEntity living && !living.level().isClientSide())
                            living.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.ZOMBIFICATION.get()), (int) (double) SurvivalReimaginedModConfig.VALUES.zombification_time, 1, true, false));
                    });
                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger = 1;
                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).setDirty();

                    if (world instanceof Level level) {
                        if (!level.isClientSide()) {
                            level.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1);
                        } else {
                            level.playLocalSound(x,y,z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1, false);
                        }
                    }
                } else if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger == 1) {
                    SurvivalReimaginedMod.queueServerWork(1, () -> {
                        if (entity instanceof LivingEntity living && !living.level().isClientSide())
                            living.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.ZOMBIFICATION.get()), (int) (double) SurvivalReimaginedModConfig.VALUES.zombification_time, 2, true, false));
                    });
                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger = 2;
                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).setDirty();

                    if (world instanceof Level level) {
                        if (!level.isClientSide()) {
                            level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1);
                        } else {
                            level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1, false);
                        }
                    }
                } else if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger == 2) {
                    SurvivalReimaginedMod.queueServerWork(1, () -> {
                        if (entity instanceof LivingEntity living && !living.level().isClientSide())
                            living.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.ZOMBIFICATION.get()), (int) (double) SurvivalReimaginedModConfig.VALUES.zombification_time, 3, true, false));
                    });
                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger = 3;
                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).setDirty();

                    if (world instanceof Level level) {
                        if (!level.isClientSide()) {
                            level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1);
                        } else {
                            level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1, false);
                        }
                    }
                } else if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger == 3) {
                    SurvivalReimaginedMod.queueServerWork(1, () -> {
                        if (entity instanceof LivingEntity living && !living.level().isClientSide())
                            living.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.ZOMBIFICATION.get()), (int) (double) SurvivalReimaginedModConfig.VALUES.zombification_time, 4, true, false));
                    });
                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger = 4;
                    SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).setDirty();

                    if (world instanceof Level level) {
                        if (!level.isClientSide()) {
                            level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1);
                        } else {
                            level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.converted")), SoundSource.PLAYERS, 1, 1, false);
                        }
                    }
                } else if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger == 4) {
                    entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("survival_reimagined:zombification_damage")))), 40);
                    if (world.getBiome(BlockPos.containing(x,y,z)).is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_desert"))) || world.getBiome(BlockPos.containing(x,y,z)).is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_badlands")))) {
                        if (world instanceof ServerLevel level) {
                            Entity entityToSpawn = EntityType.HUSK.spawn(level, BlockPos.containing(x,y,z), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                entityToSpawn.setYRot(world.getRandom().nextFloat() * 360f);
                            }
                        }
                    } else if (world.getBiome(BlockPos.containing(x,y,z)).is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_ocean"))) || world.getBiome(BlockPos.containing(x,y,z)).is(TagKey.create(Registries.BIOME, ResourceLocation.parse("minecraft:is_badlands")))) {
                        if (world instanceof ServerLevel level) {
                            Entity entityToSpawn = EntityType.DROWNED.spawn(level, BlockPos.containing(x,y,z), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                entityToSpawn.setYRot(world.getRandom().nextFloat() * 360f);
                            }
                        }
                    } else {
                        if (world instanceof ServerLevel level) {
                            Entity entityToSpawn = EntityType.ZOMBIE.spawn(level, BlockPos.containing(x,y,z), MobSpawnType.MOB_SUMMONED);
                            if (entityToSpawn != null) {
                                entityToSpawn.setYRot(world.getRandom().nextFloat() * 360f);
                            }
                        }
                    }
                }
            }
        }
    }

    private static GameType getEntityGameType(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer();
        } else if (entity instanceof Player player && player.level().isClientSide()) {
            PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            if (playerInfo != null) return playerInfo.getGameMode();
        }
        return null;
    }
}
