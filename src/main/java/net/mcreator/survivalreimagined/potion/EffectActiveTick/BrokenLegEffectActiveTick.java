package net.mcreator.survivalreimagined.potion.EffectActiveTick;

import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;

public class BrokenLegEffectActiveTick {
    public static void execute(LevelAccessor level, Entity entity) {
        if (entity == null) {
            return;
        }
        if (getEntityGameType(entity)  == GameType.SURVIVAL) {
            if (entity instanceof LivingEntity livingEntity) {
                var attributeInstance = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attributeInstance != null) {
                    attributeInstance.setBaseValue(0.05);
                }
            }

            if (entity.isSprinting() && !entity.getTags().contains("Splint")) {
                entity.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 2.0f);

                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.getServer().tell(new TickTask(serverLevel.getServer().getTickCount() + 2, () -> {
                        entity.setSprinting(false);
                    }));
                }
            }
        }
    }

    private static GameType getEntityGameType(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer();
        } else if (entity instanceof Player player && player.level().isClientSide()) {
            PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
        }
        return null;
    }
}
