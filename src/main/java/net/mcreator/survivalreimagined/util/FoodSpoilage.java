package net.mcreator.survivalreimagined.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public final class FoodSpoilage {
	private static final TagKey<Item> CAN_ROT = TagKey.create(
			Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "food/can_rot"));
	private static final int ROT_THRESHOLD = 2000;

	private FoodSpoilage() {
	}

	public static void tick(MinecraftServer server) {
		if (server.getTickCount() % 60 != 0) return;

		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			Inventory inventory = player.getInventory();
			for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
				ItemStack stack = inventory.getItem(slot);
				if (stack.isEmpty() || !stack.is(CAN_ROT)) continue;

				CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
				int spoilage = (int) data.copyTag().getDouble("SpoilageMax") + 1;
				if (spoilage >= ROT_THRESHOLD) {
					inventory.setItem(slot, new ItemStack(SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get(), stack.getCount()));
				} else {
					CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
						tag.putDouble("SpoilageMax", spoilage);
						tag.putDouble("InventoryClock", 0);
					});
				}
			}
		}
	}

	public static void setSpoilage(ItemStack stack, int spoilage) {
		CustomData.update(DataComponents.CUSTOM_DATA, stack,
				tag -> tag.putDouble("SpoilageMax", Math.max(0, spoilage)));
	}
}
