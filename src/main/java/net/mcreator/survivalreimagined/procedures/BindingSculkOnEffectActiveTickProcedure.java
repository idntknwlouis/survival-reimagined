package net.mcreator.survivalreimagined.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class BindingSculkOnEffectActiveTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null) {
			return;
		}

		if (entity instanceof ServerPlayer player) {
			GameType gameType = player.gameMode.getGameModeForPlayer();
			if (gameType == GameType.SURVIVAL || gameType == GameType.ADVENTURE) {
				if (world instanceof Level level && !level.isClientSide()) {
					level.playSound(null, BlockPos.containing(x, y, z),
							BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")),
							SoundSource.PLAYERS, 2, 1);
				}
			}
		}

		entity.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(
				Registries.DAMAGE_TYPE,
				ResourceLocation.parse("survival_reimagined:binding_sculk_damage")
		))), 2);
		entity.setDeltaMovement(Vec3.ZERO);
	}
}
