package net.mcreator.survivalreimagined.potion.EffectActiveTick;

import net.mcreator.survivalreimagined.potion.DarknessLevelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ParanoiaOnEffectActiveTick {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        if (Math.random() < 0.001) {
            if (world instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), -10, 10),y + Mth.nextDouble(RandomSource.create(), -10, 10), z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.sculk_shrieker.shriek")), SoundSource.BLOCKS, 1, 0.8f);
                } else {
                    level.playLocalSound((x + Mth.nextDouble(RandomSource.create(), -10, 10)), (y + Mth.nextDouble(RandomSource.create(), -10, 10)), (z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.sculk_shrieker.shriek")), SoundSource.BLOCKS, 1, 0.8f, false);
                }
            }
        } else if (Math.random() < 0.001) {
            if (world instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), -10, 10),y + Mth.nextDouble(RandomSource.create(), -10, 10), z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.creeper.primed")), SoundSource.BLOCKS, 1, 0.9f);
                } else {
                    level.playLocalSound((x + Mth.nextDouble(RandomSource.create(), -10, 10)), (y + Mth.nextDouble(RandomSource.create(), -10, 10)), (z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.creeper.primed")), SoundSource.BLOCKS, 1, 0.9f, false);
                }
            }
        } else if (Math.random() < 0.0005) {
            if (world instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, BlockPos.containing(x + Mth.nextDouble(RandomSource.create(), -10, 10),y + Mth.nextDouble(RandomSource.create(), -10, 10), z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:player_digging.stone")), SoundSource.BLOCKS, 1, 1);
                } else {
                    level.playLocalSound((x + Mth.nextDouble(RandomSource.create(), -10, 10)), (y + Mth.nextDouble(RandomSource.create(), -10, 10)), (z + Mth.nextDouble(RandomSource.create(), -10, 10)),
                            BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:player_digging.stone")), SoundSource.BLOCKS, 1, 1, false);
                }
            }
        }
        DarknessLevelHelper.incrementTick(entity);
    }

}
