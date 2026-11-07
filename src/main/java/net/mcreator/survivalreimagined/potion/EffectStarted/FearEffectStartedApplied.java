package net.mcreator.survivalreimagined.potion.EffectStarted;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class FearEffectStartedApplied {
    public static void execute(Entity entity) {
        if (entity == null) return;
        if (entity instanceof Player player && !player.level().isClientSide()) {
            player.displayClientMessage(Component.literal("Darkness fills you with fear, seek light immediately"), true);
        }
    }
}
