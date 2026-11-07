package net.mcreator.survivalreimagined.potion;

import net.mcreator.survivalreimagined.potion.EffectActiveTick.ParanoiaOnEffectActiveTick;
import net.mcreator.survivalreimagined.potion.EffectStarted.ParanoiaEffectStartedApplied;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ParanoiaMobEffect extends MobEffect {
    public ParanoiaMobEffect() {
        super(MobEffectCategory.HARMFUL, -13884123);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        ParanoiaEffectStartedApplied.execute(entity);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        ParanoiaOnEffectActiveTick.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
        return super.applyEffectTick(entity, amplifier);
    }
}
