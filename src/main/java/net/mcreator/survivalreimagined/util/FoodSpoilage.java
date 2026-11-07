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

public final class FoodSpoilage {
	private static final TagKey<Item> CAN_ROT = TagKey.create(
			Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "food/can_rot"));
	private static final TagKey<Block> WOODEN_CONTAINERS = TagKey.create(
			Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "wooden_containers"));
	private static final int ROT_THRESHOLD = 2000;
	private static final int SPOILAGE_PER_PERCENT = ROT_THRESHOLD / 100;
	private static final long CONTAINER_TICKS_PER_SPOILAGE = 100L;
	private static final long CONTAINER_TICKS_PER_PERCENT = CONTAINER_TICKS_PER_SPOILAGE * SPOILAGE_PER_PERCENT;

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

		long currentTime = level.getGameTime();
		boolean changed = false;

		for (int slot = 0; slot < container.getContainerSize(); slot++) {
			ItemStack stack = container.getItem(slot);
			if (stack.isEmpty() || !stack.is(CAN_ROT)) {
				continue;
			}

			CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
			var tag = data.copyTag();
			long lastChecked = tag.getLong("ContainerSpoilageLastTick");

			if (!tag.contains("ContainerSpoilageLastTick")) {
				CustomData.update(DataComponents.CUSTOM_DATA, stack,
						nbt -> nbt.putLong("ContainerSpoilageLastTick", currentTime));
				changed = true;
				continue;
			}

			long elapsed = currentTime - lastChecked;
			if (elapsed < 0) {
				CustomData.update(DataComponents.CUSTOM_DATA, stack,
						nbt -> nbt.putLong("ContainerSpoilageLastTick", currentTime));
				changed = true;
				continue;
			}

			if (elapsed < CONTAINER_TICKS_PER_PERCENT) {
				continue;
			}

			long percentSteps = elapsed / CONTAINER_TICKS_PER_PERCENT;
			int spoilage = (int) tag.getDouble("SpoilageMax");
			spoilage = Math.min(ROT_THRESHOLD, spoilage + (int) (percentSteps * SPOILAGE_PER_PERCENT));
			long nextTick = lastChecked + percentSteps * CONTAINER_TICKS_PER_PERCENT;

			if (spoilage >= ROT_THRESHOLD) {
				container.setItem(slot, new ItemStack(SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get(), stack.getCount()));
				changed = true;
				continue;
			}

			final int newSpoilage = spoilage;
			final long newLastChecked = nextTick;
			CustomData.update(DataComponents.CUSTOM_DATA, stack, nbt -> {
				nbt.putDouble("SpoilageMax", newSpoilage);
				nbt.putLong("ContainerSpoilageLastTick", newLastChecked);
				nbt.putLong("SpoilageLastTick", level.getGameTime());
			});
			changed = true;
		}

		if (changed) {
			container.setChanged();
		}
	}

	public static void setSpoilage(ItemStack stack, int spoilage) {
		CustomData.update(DataComponents.CUSTOM_DATA, stack,
				tag -> tag.putDouble("SpoilageMax", Math.max(0, spoilage)));
	}
}
