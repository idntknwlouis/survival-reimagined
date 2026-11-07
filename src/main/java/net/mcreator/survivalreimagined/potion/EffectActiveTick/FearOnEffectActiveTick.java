package net.mcreator.survivalreimagined.potion.EffectActiveTick;

import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class FearOnEffectActiveTick {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        if (Math.random() < 0.005) {
            if (world instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), -10, 10), y + Mth.nextDouble(RandomSource.create(), -10, 10), z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.sculk_shrieker.shriek")), SoundSource.BLOCKS, 1, (float) 0.8);
                } else {
                    level.playLocalSound((x + Mth.nextDouble(RandomSource.create(), -10, 10)), (y + Mth.nextDouble(RandomSource.create(), -10, 10)), (z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.sculk_shrieker.shriek")), SoundSource.BLOCKS, 1, (float) 0.8, false);
                }
            }
        } else if (Math.random() < 0.005) {
            if (world instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), -10, 10), y + Mth.nextDouble(RandomSource.create(), -10, 10), z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.creeper.primed")), SoundSource.BLOCKS, 1, (float) 0.9);
                } else {
                    level.playLocalSound((x + Mth.nextDouble(RandomSource.create(), -10, 10)), (y + Mth.nextDouble(RandomSource.create(), -10, 10)), (z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.creeper.primed")), SoundSource.BLOCKS, 1, (float) 0.9, false);
                }
            }
        } else if (Math.random() < 0.0025) {
            if (world instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), -10, 10), y + Mth.nextDouble(RandomSource.create(), -10, 10), z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:player_digging.stone")), SoundSource.BLOCKS, 1, 1);
                } else {
                    level.playLocalSound((x + Mth.nextDouble(RandomSource.create(), -10, 10)), (y + Mth.nextDouble(RandomSource.create(), -10, 10)), (z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:player_digging.stone")), SoundSource.BLOCKS, 1, 1, false);
                }
            }
        } else if (Math.random() < 0.005) {
            if (world instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), -10, 10), y + Mth.nextDouble(RandomSource.create(), -10, 10), z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("ambient.cave")), SoundSource.AMBIENT, 1, 1);
                } else {
                    level.playLocalSound((x + Mth.nextDouble(RandomSource.create(), -10, 10)), (y + Mth.nextDouble(RandomSource.create(), -10, 10)), (z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("ambient.cave")), SoundSource.AMBIENT, 1, 1, false);
                }
            }
        } else if (world instanceof ServerLevel serverLevel) {
            if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).HeartBeat == 0) {
                if (world instanceof Level level) {
                    if (!level.isClientSide()) {
                        level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.warden.heartbeat")), SoundSource.PLAYERS, 2, (float) 0.8);
                    } else {
                        level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.warden.heartbeat")), SoundSource.PLAYERS, 2, (float) 0.8, false);
                    }
                }
                entity.hurt(new DamageSource(entity.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("survival_reimagined:fear_damage")))), 2.0f);
            }
        }
    }
}
