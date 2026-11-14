package net.mcreator.survivalreimagined.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

/**
 * Container spoilage follows the original NeoForge right-click check:
 * elapsed world day-time is rounded to 100-tick spoilage units and applied
 * when the player interacts with a wooden container.
 *
 * Inventory spoilage is handled by SpoilingFoodItem.inventoryTick().
 */
public final class FoodSpoilage {
	private static final TagKey<Item> CAN_ROT = TagKey.create(
			Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "food/can_rot"));
	private static final TagKey<Block> WOODEN_CONTAINERS = TagKey.create(
			Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "wooden_containers"));
	private static final int ROT_THRESHOLD = 2000;
	private static final long CONTAINER_TICKS_PER_SPOILAGE = 100L;

	private FoodSpoilage() {
	}

	public static void updateWoodenContainer(Level level, BlockPos pos) {
		if (level.isClientSide() || !level.getBlockState(pos).is(WOODEN_CONTAINERS)) {
			return;
		}

		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (!(blockEntity instanceof Container container)) {
			return;
		}

		long currentTime = level.getDayTime();
		boolean changed = false;
		for (int slot = 0; slot < container.getContainerSize(); slot++) {
			ItemStack stack = container.getItem(slot);
			if (stack.isEmpty() || !stack.is(CAN_ROT)) {
				continue;
			}

			var tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
			double previousCheck = tag.getDouble("TimeLastChecked");
			double elapsed = currentTime - previousCheck;
			double spoilage = Math.round(elapsed / (double) CONTAINER_TICKS_PER_SPOILAGE);
			double currentSpoilage = tag.getDouble("SpoilageMax");

			// Match the source procedure's pre-increment rotten check.
			if (currentSpoilage >= ROT_THRESHOLD) {
				container.setItem(slot, new ItemStack(
						SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get(), stack.getCount()));
				changed = true;
				continue;
			}

			double updatedSpoilage = currentSpoilage + spoilage;
			CustomData.update(DataComponents.CUSTOM_DATA, stack, data -> {
				data.putDouble("CurrentTime", currentTime);
				data.putDouble("Elapsed", elapsed);
				data.putDouble("Spoilage", spoilage);
				data.putDouble("TimeLastChecked", currentTime);
				data.putDouble("SpoilageMax", updatedSpoilage);
				// A container's elapsed-time clock replaces the inventory clock
				// while the stack remains stored here.
				data.remove("InventoryClock");
			});
			changed = true;
		}

		if (changed) {
			container.setChanged();
		}
	}
}
