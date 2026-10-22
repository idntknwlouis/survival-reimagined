package net.mcreator.survivalreimagined.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class RadiationOnEffectActiveTickProcedure {
	private static final ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(
			Registries.DAMAGE_TYPE, ResourceLocation.parse("survival_reimagined:radiation_damage"));

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null) {
			return;
		}

		entity.hurt(new DamageSource(entity.level().registryAccess()
				.registryOrThrow(Registries.DAMAGE_TYPE)
				.getHolderOrThrow(DAMAGE_TYPE)), 4);

		if (world instanceof Level level && !level.isClientSide()) {
			level.playSound(null, BlockPos.containing(x, y, z),
					BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:radiation/damage")),
					SoundSource.HOSTILE, 0.8f, 1);
		}

		entity.setDeltaMovement(Vec3.ZERO);
	}
}
