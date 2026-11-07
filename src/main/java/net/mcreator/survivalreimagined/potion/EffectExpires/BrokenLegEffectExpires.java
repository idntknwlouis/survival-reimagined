package net.mcreator.survivalreimagined.potion.EffectExpires;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BrokenLegEffectExpires {
    public static void execute(Entity entity) {
        if (entity == null) return;
        if (entity instanceof LivingEntity livingEntity) {
            var attributeInstance = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attributeInstance != null) {
                attributeInstance.setBaseValue(0.1);
            }
        }
        entity.removeTag("Splint");
    }
}
