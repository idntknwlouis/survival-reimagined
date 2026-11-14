package net.mcreator.survivalreimagined.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
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
    private static final long CONTAINER_SCAN_INTERVAL = 20L;

    private record OpenMenuTimer(AbstractContainerMenu menu, long nextScanTick) {
    }

    private static final Map<UUID, OpenMenuTimer> OPEN_MENU_TIMERS = new HashMap<>();

    private FoodSpoilage() {
    }

    /**
     * Called when a player opens/interacts with a block container. This performs
     * an immediate pass; tickOpenContainers keeps the open container refreshed.
     */
    public static void updateWoodenContainer(Level level, BlockPos pos) {
        if (level.isClientSide() || !level.getBlockState(pos).is(WOODEN_CONTAINERS)) {
            return;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof Container container)) {
            return;
        }

        updateContainer(level, container);
    }

    private static void updateContainer(Level level, Container container) {
        long currentTime = level.getGameTime();
        boolean changed = false;

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (stack.isEmpty() || !stack.is(CAN_ROT)) {
                continue;
            }

            var tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            // SpoilageLastTick is the shared baseline for both inventory and
            // container time. Fall back to the older container-only key for
            // stacks created by earlier versions of this Fabric port.
            long lastChecked = tag.contains("SpoilageLastTick")
                    ? tag.getLong("SpoilageLastTick")
                    : tag.getLong("ContainerSpoilageLastTick");

            if ((!tag.contains("SpoilageLastTick") && !tag.contains("ContainerSpoilageLastTick"))
                    || currentTime < lastChecked) {
                CustomData.update(DataComponents.CUSTOM_DATA, stack, nbt -> {
                    nbt.putLong("SpoilageLastTick", currentTime);
                    nbt.putLong("ContainerSpoilageLastTick", currentTime);
                });
                changed = true;
                continue;
            }

            long elapsed = currentTime - lastChecked;
            int spoilage = (int) tag.getDouble("SpoilageMax");

            if (spoilage >= ROT_THRESHOLD) {
                container.setItem(slot,
                        new ItemStack(SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get(), stack.getCount()));
                changed = true;
                continue;
            }

            if (elapsed >= CONTAINER_TICKS_PER_SPOILAGE) {
                // Match the NeoForge container rate: one spoilage point per 100
                // elapsed world ticks. Keep the fractional remainder for the next scan.
                long spoilageSteps = elapsed / CONTAINER_TICKS_PER_SPOILAGE;
                spoilage = Math.min(ROT_THRESHOLD, spoilage + (int) spoilageSteps);
                long nextTick = lastChecked + spoilageSteps * CONTAINER_TICKS_PER_SPOILAGE;

                if (spoilage >= ROT_THRESHOLD) {
                    container.setItem(slot,
                            new ItemStack(SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get(), stack.getCount()));
                    changed = true;
                    continue;
                }

                final int newSpoilage = spoilage;
                final long newLastChecked = nextTick;
                CustomData.update(DataComponents.CUSTOM_DATA, stack, nbt -> {
                    nbt.putDouble("SpoilageMax", newSpoilage);
                    nbt.putLong("SpoilageLastTick", newLastChecked);
                    nbt.putLong("ContainerSpoilageLastTick", newLastChecked);
                });
                changed = true;
            }
        }

        if (changed) {
            container.setChanged();
        }
    }

    /**
     * Periodically scans block containers represented by the slots in each
     * player's currently-open menu. A new menu gets a fresh timer immediately;
     * while it remains open, the scan repeats once per second.
     */
    public static void tickOpenContainers(MinecraftServer server) {
        long currentTick = server.getTickCount();
        Set<UUID> onlinePlayers = new HashSet<>();

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            UUID playerId = player.getUUID();
            onlinePlayers.add(playerId);

            AbstractContainerMenu menu = player.containerMenu;
            if (menu == player.inventoryMenu) {
                OPEN_MENU_TIMERS.remove(playerId);
                continue;
            }

            OpenMenuTimer timer = OPEN_MENU_TIMERS.get(playerId);
            if (timer == null || timer.menu() != menu || currentTick >= timer.nextScanTick()) {
                updateOpenWoodenContainers(player.level(), menu);
                OPEN_MENU_TIMERS.put(playerId,
                        new OpenMenuTimer(menu, currentTick + CONTAINER_SCAN_INTERVAL));
            }
        }

        OPEN_MENU_TIMERS.keySet().removeIf(playerId -> !onlinePlayers.contains(playerId));
    }

    private static void updateOpenWoodenContainers(Level level, AbstractContainerMenu menu) {
        Set<BlockEntity> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        Set<Container> visitedComposite = Collections.newSetFromMap(new IdentityHashMap<>());

        for (Slot slot : menu.slots) {
            if (!(slot.container instanceof Container container) || !hasSpoilableItem(container)) {
                continue;
            }

            if (slot.container instanceof BlockEntity blockEntity) {
                if (!visited.add(blockEntity)) {
                    continue;
                }
                BlockPos pos = blockEntity.getBlockPos();
                if (level.getBlockState(pos).is(WOODEN_CONTAINERS)) {
                    updateContainer(level, container);
                }
            } else if (slot.container instanceof CompoundContainer && visitedComposite.add(container)) {
                // Vanilla double chests expose a CompoundContainer rather than either
                // individual ChestBlockEntity through their menu slots.
                updateContainer(level, container);
            }
        }
    }

    private static boolean hasSpoilableItem(Container container) {
        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (!stack.isEmpty() && stack.is(CAN_ROT)) {
                return true;
            }
        }
        return false;
    }

    public static void setSpoilage(ItemStack stack, int spoilage) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack,
                tag -> tag.putDouble("SpoilageMax", Math.max(0, spoilage)));
    }
}
