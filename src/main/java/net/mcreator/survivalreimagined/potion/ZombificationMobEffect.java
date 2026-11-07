package net.mcreator.survivalreimagined.potion;

import net.mcreator.survivalreimagined.potion.EffectActiveTick.ZombificationOnEffectActiveTick;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ZombificationMobEffect extends MobEffect {
    public ZombificationMobEffect() {
        super(MobEffectCategory.HARMFUL, -12032461);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        ZombificationOnEffectActiveTick.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
        return super.applyEffectTick(entity, amplifier);
    }
}
