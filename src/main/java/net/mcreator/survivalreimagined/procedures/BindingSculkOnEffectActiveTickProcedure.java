package net.mcreator.survivalreimagined.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class BindingSculkOnEffectActiveTickProcedure {
	private static final ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(
			Registries.DAMAGE_TYPE, ResourceLocation.parse("survival_reimagined:binding_sculk_damage"));

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null) {
			return;
		}

		if (entity instanceof ServerPlayer player) {
			GameType gameType = player.gameMode.getGameModeForPlayer();
			if ((gameType == GameType.SURVIVAL || gameType == GameType.ADVENTURE)
					&& world instanceof Level level && !level.isClientSide()) {
				level.playSound(null, BlockPos.containing(x, y, z),
						BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")),
						SoundSource.PLAYERS, 2, 1);
			}
		}

		entity.hurt(new DamageSource(entity.level().registryAccess()
				.registryOrThrow(Registries.DAMAGE_TYPE)
				.getHolderOrThrow(DAMAGE_TYPE)), 2);
		entity.setDeltaMovement(Vec3.ZERO);
	}
}
