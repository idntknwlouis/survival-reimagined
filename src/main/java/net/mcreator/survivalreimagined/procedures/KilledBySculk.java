package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class KilledBySculk {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), damageSource, entity);
        });
    }

    public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damageSource, Entity entity) {
        if (damageSource == null || entity == null) return;

        if (entity instanceof Player player) {
            if (damageSource.is(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("survival_reimagined:binding_sculk_damage")))) {
                if (world instanceof Level level) {
                    if (!level.isClientSide()) {
                        level.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.sculk_shrieker.shriek")), SoundSource.PLAYERS, 1, 1);
                    } else {
                        level.playLocalSound(x,y,z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.sculk_shrieker.shriek")), SoundSource.PLAYERS, 1, 1, false);
                    }
                }
            }
        }
    }
}
