package net.mcreator.survivalreimagined.potion.EffectStarted;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class BleedingEffectStartedAppliedProcedure {
	public static void execute(Entity entity) {
		if (entity instanceof Player player && !player.level().isClientSide()) {
			player.displayClientMessage(Component.literal("You are Bleeding"), true);
		}
	}
}
