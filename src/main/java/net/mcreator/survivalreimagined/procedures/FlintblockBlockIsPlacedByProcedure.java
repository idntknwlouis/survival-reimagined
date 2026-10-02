package net.mcreator.survivalreimagined.procedures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

public class FlintblockBlockIsPlacedByProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (!(entity instanceof ServerPlayer player)) {
			return;
		}

		if (player.gameMode.getGameModeForPlayer() == GameType.SURVIVAL
				&& (player.getMainHandItem().is(Items.FLINT) || player.getOffhandItem().is(Items.FLINT))) {
			itemstack.shrink(1);
		}
	}
}
