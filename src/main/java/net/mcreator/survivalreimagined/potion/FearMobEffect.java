package net.mcreator.survivalreimagined.potion;

import net.mcreator.survivalreimagined.potion.EffectActiveTick.FearOnEffectActiveTick;
import net.mcreator.survivalreimagined.potion.EffectStarted.FearEffectStartedApplied;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FearMobEffect extends MobEffect {
    public FearMobEffect() {
        super(MobEffectCategory.HARMFUL, -14803940);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        FearEffectStartedApplied.execute(entity);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        FearOnEffectActiveTick.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
        return super.applyEffectTick(entity, amplifier);
    }
}
