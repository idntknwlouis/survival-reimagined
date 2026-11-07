package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class MechanicOverrides {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Player player = handler.player;

            if (player.getAttributes().hasAttribute(Attributes.BLOCK_BREAK_SPEED)) {
                player.getAttribute(Attributes.BLOCK_BREAK_SPEED).setBaseValue(SurvivalReimaginedModConfig.VALUES.block_break_speed);
            }
        });
    }
}
