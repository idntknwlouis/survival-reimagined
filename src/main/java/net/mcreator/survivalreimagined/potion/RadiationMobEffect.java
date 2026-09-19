package net.mcreator.survivalreimagined.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import net.mcreator.survivalreimagined.procedures.RadiationActiveTickConditionProcedure;
import net.mcreator.survivalreimagined.procedures.RadiationEffectStartedappliedProcedure;
import net.mcreator.survivalreimagined.procedures.RadiationOnEffectActiveTickProcedure;

public class RadiationMobEffect extends MobEffect {
	public RadiationMobEffect() {
		super(MobEffectCategory.HARMFUL, -5258644);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		RadiationEffectStartedappliedProcedure.execute(entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return RadiationActiveTickConditionProcedure.execute(amplifier, duration);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		RadiationOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}
