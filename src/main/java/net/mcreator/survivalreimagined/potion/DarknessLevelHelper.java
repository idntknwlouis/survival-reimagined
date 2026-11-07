package net.mcreator.survivalreimagined.potion;

import net.minecraft.world.entity.Entity;

public class DarknessLevelHelper {
    private static final String PREFIX = "survival_reimagined:effect_tick=";

    public static int getEffectTick(Entity entity) {
        for (String tag : entity.getTags()) {
            if (tag.startsWith(PREFIX)) {
                try {
                    return Integer.parseInt(tag.substring(PREFIX.length()));
                } catch (NumberFormatException ignored) {
                    return 0;
                }
            }
        }
        return 0;
    }

    public static void setEffectTick(Entity entity, int ticks) {
        for (String tag : entity.getTags()) {
            if (tag.startsWith(PREFIX)) {
                entity.removeTag(tag);
            }
        }
        if (ticks > 0) {
            entity.addTag(PREFIX + ticks);
        }
    }

    public static void incrementTick(Entity entity) {
        setEffectTick(entity, getEffectTick(entity) + 1);
    }
    public static void resetEffectTick(Entity entity) {
        setEffectTick(entity, 0);
    }
}
