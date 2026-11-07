package net.mcreator.survivalreimagined.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import net.mcreator.survivalreimagined.potion.ActiveTickCondition.BleedingActiveTickConditionProcedure;
import net.mcreator.survivalreimagined.potion.EffectStarted.BleedingEffectStartedAppliedProcedure;
import net.mcreator.survivalreimagined.potion.EffectActiveTick.BleedingOnEffectActiveTickProcedure;

public class BleedingMobEffect extends MobEffect {
	public BleedingMobEffect() {
		super(MobEffectCategory.HARMFUL, -2935242);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		BleedingEffectStartedAppliedProcedure.execute(entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return BleedingActiveTickConditionProcedure.execute(amplifier, duration);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		BleedingOnEffectActiveTickProcedure.execute(entity.level(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}
