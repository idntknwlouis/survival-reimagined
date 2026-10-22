package net.mcreator.survivalreimagined.procedures;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class BleedingOnEffectActiveTickProcedure {
	private static final ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(
			Registries.DAMAGE_TYPE, ResourceLocation.parse("survival_reimagined:bleeding_dmg"));

	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null) {
			return;
		}

		entity.hurt(new DamageSource(entity.level().registryAccess()
				.registryOrThrow(Registries.DAMAGE_TYPE)
				.getHolderOrThrow(DAMAGE_TYPE)), 1);
		entity.setDeltaMovement(new Vec3(entity.getDeltaMovement().x(), 0, entity.getDeltaMovement().z()));
	}
}
