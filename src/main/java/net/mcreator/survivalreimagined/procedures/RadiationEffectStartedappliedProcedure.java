package net.mcreator.survivalreimagined.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class RadiationEffectStartedappliedProcedure {
	public static void execute(Entity entity) {
		if (entity instanceof Player player && !player.level().isClientSide()) {
			player.displayClientMessage(Component.literal("§aYou are sick with Radiation"), true);
		}
	}
}
