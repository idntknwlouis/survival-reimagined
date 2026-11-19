package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fabric port of the rewritten NeoForge container spoilage events.
 * Player inventory spoilage remains handled separately by tickPlayerInventory.
 */
public final class FoodSpoilage {
    private static final int SPOILAGE_INTERVAL = 120;
    private static final int MAX_SPOILAGE = 2000;

    private static final TagKey<Item> CAN_ROT = TagKey.create(
            Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "food/can_rot"));
    private static final TagKey<Block> WOODEN_CONTAINERS = TagKey.create(
            Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "wooden_containers"));

    private static final Map<ServerLevel, Set<Long>> LOADED_CHUNKS = new ConcurrentHashMap<>();

    private FoodSpoilage() {
    }

    public static void register() {
        ServerChunkEvents.CHUNK_LOAD.register((level, chunk) ->
                LOADED_CHUNKS.computeIfAbsent(level, ignored -> ConcurrentHashMap.newKeySet())
                        .add(chunk.getPos().toLong()));

        ServerChunkEvents.CHUNK_UNLOAD.register((level, chunk) -> {
            Set<Long> chunks = LOADED_CHUNKS.get(level);
            if (chunks != null) {
                chunks.remove(chunk.getPos().toLong());
                if (chunks.isEmpty()) {
                    LOADED_CHUNKS.remove(level, chunks);
                }
            }
        });

        ServerTickEvents.END_WORLD_TICK.register(FoodSpoilage::onWorldTick);
    }

    private static void onWorldTick(ServerLevel level) {
        if (level.getGameTime() % SPOILAGE_INTERVAL != 0) {
            return;
        }

        Set<Long> chunks = LOADED_CHUNKS.get(level);
        if (chunks == null || chunks.isEmpty()) {
            return;
        }

        for (long chunkKey : chunks) {
            ChunkPos pos = new ChunkPos(chunkKey);
            LevelChunk chunk = level.getChunkSource().getChunkNow(pos.x, pos.z);
            if (chunk == null) {
                continue;
            }

            for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                if (blockEntity.isRemoved() || !blockEntity.getBlockState().is(WOODEN_CONTAINERS)) {
                    continue;
                }
                if (blockEntity instanceof Container container) {
                    spoilContainer(container, level);
                }
            }
        }
    }

    private static ItemStack getRottenStack(ItemStack spoiledStack) {
        Item rottenItem = spoiledStack.is(SurvivalReimaginedModItems.FLESH.get())
                ? net.minecraft.world.item.Items.ROTTEN_FLESH
                : SurvivalReimaginedModItems.ROTTEN_BIOMATTER.get();
        return new ItemStack(rottenItem, spoiledStack.getCount());
    }

    private static void syncContainerStack(ServerLevel level, ItemStack stack) {
        for (ServerPlayer player : level.players()) {
            for (int menuSlot = 0; menuSlot < player.containerMenu.slots.size(); menuSlot++) {
                if (player.containerMenu.slots.get(menuSlot).getItem() == stack) {
                    player.connection.send(new ClientboundContainerSetSlotPacket(
                            player.containerMenu.containerId,
                            player.containerMenu.incrementStateId(),
                            menuSlot,
                            stack.copy()));
                }
            }
        }
    }

    private static void spoilContainer(Container container, ServerLevel level) {
        boolean changed = false;
        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);
            if (stack.isEmpty() || !stack.is(CAN_ROT)) {
                continue;
            }

            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            double currentSpoilage = customData == null
                    ? 0.0D
                    : customData.copyTag().getDouble("SpoilageMax");

            if (currentSpoilage >= MAX_SPOILAGE) {
                container.setItem(slot, getRottenStack(stack));
                changed = true;
                syncContainerStack(level, container.getItem(slot));
                continue;
            }

            double updatedSpoilage = Math.min(currentSpoilage + 1.0D, MAX_SPOILAGE);
            CustomData.update(DataComponents.CUSTOM_DATA, stack,
                    data -> data.putDouble("SpoilageMax", updatedSpoilage));
            if (updatedSpoilage >= MAX_SPOILAGE) {
                container.setItem(slot, getRottenStack(stack));
                syncContainerStack(level, container.getItem(slot));
            } else {
                syncContainerStack(level, stack);
            }
            changed = true;
        }

        if (changed) {
            container.setChanged();
        }
    }

    /**
     * Keep the existing player inventory spoilage behavior.
     */
    public static void tickPlayerInventory(Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        // InventoryClock is only synchronized once per second. Advancing it
        // by 20 ticks at a time avoids replacing every inventory stack every
        // server tick, which caused unnecessary menu packets and tooltip flicker.
        if (player.level().getGameTime() % 20L != 0L) {
            return;
        }

        boolean changed = false;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.isEmpty() || !stack.is(CAN_ROT)) {
                continue;
            }

            var tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            double spoilage = tag.getDouble("SpoilageMax");
            if (spoilage >= MAX_SPOILAGE) {
                player.getInventory().setItem(slot, getRottenStack(stack));
                changed = true;
                continue;
            }

            double inventoryClock = tag.getDouble("InventoryClock") + 20.0D;
            if (inventoryClock >= SPOILAGE_INTERVAL) {
                inventoryClock -= SPOILAGE_INTERVAL;
                spoilage += 1.0D;
            }

            if (spoilage >= MAX_SPOILAGE) {
                player.getInventory().setItem(slot, getRottenStack(stack));
            } else {
                final double updatedClock = inventoryClock;
                final double updatedSpoilage = spoilage;
                CustomData.update(DataComponents.CUSTOM_DATA, stack, data -> {
                    data.putDouble("InventoryClock", updatedClock);
                    if (updatedSpoilage > 0.0D || data.contains("SpoilageMax")) {
                        data.putDouble("SpoilageMax", updatedSpoilage);
                    }
                });
            }
            changed = true;
        }

        if (changed) {
            player.getInventory().setChanged();
        }
    }
}
