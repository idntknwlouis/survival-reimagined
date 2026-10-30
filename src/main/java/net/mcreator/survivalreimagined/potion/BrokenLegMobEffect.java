package net.mcreator.survivalreimagined.potion;

import net.mcreator.survivalreimagined.potion.EffectActiveTick.BrokenLegEffectActiveTick;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BrokenLegMobEffect extends MobEffect {
    public BrokenLegMobEffect() {
        super(MobEffectCategory.NEUTRAL, -1);
        this.withSoundOnAdded(BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:limb_remove")));
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        BrokenLegEffectActiveTick.execute(entity.level(), entity);
        return super.applyEffectTick(entity, amplifier);
    }
}
