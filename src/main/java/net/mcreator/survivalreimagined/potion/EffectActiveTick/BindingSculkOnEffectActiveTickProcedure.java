package net.mcreator.survivalreimagined.potion.EffectActiveTick;

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
import net.minecraft.world.phys.Vec3;

public class BindingSculkOnEffectActiveTickProcedure {
	public static void execute(Level world, double x, double y, double z, Entity entity) {
		if (entity == null) return;

		boolean notCreative = false;

		if (entity instanceof ServerPlayer player) {
			GameType gameType = player.gameMode.getGameModeForPlayer();
			notCreative = gameType == GameType.SURVIVAL || gameType == GameType.ADVENTURE;
		}

		if (notCreative && world.isClientSide()) {
			world.playLocalSound(x,y,z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")), SoundSource.PLAYERS, 2.0f, 1.0f, false);
		} else if (notCreative && !world.isClientSide()) {
			world.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")), SoundSource.PLAYERS, 2.0f, 1.0f);
		}

		if (!world.isClientSide()) {
			ResourceKey<DamageType> damageType = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("survival_reimagined:binding_sculk_damage"));
			DamageSource damageSource = world.damageSources().source(damageType);

			entity.hurt(damageSource, 2.0f);
			entity.setDeltaMovement(Vec3.ZERO);
		}
	}
}
