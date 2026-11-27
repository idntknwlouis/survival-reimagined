package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
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
     * Player inventory spoilage uses the same world-time interval as containers.
     * Changed visible menu slots are explicitly synchronized so tooltip data
     * reaches the client immediately.
     */
    public static void tickPlayerInventory(Player player) {
        if (player.level().isClientSide()
                || player.level().getGameTime() % SPOILAGE_INTERVAL != 0L) {
            return;
        }

        boolean changed = false;
        for (int inventorySlot = 0;
             inventorySlot < player.getInventory().getContainerSize();
             inventorySlot++) {
            ItemStack stack = player.getInventory().getItem(inventorySlot);
            if (stack.isEmpty() || !stack.is(CAN_ROT)) {
                continue;
            }

            double spoilage = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                    .copyTag().getDouble("SpoilageMax");
            if (spoilage >= MAX_SPOILAGE) {
                ItemStack rottenStack = getRottenStack(stack);
                player.getInventory().setItem(inventorySlot, rottenStack);
                syncPlayerInventoryStack(player, rottenStack);
                changed = true;
                continue;
            }

            double updatedSpoilage = Math.min(spoilage + 1.0D, MAX_SPOILAGE);
            if (updatedSpoilage >= MAX_SPOILAGE) {
                ItemStack rottenStack = getRottenStack(stack);
                player.getInventory().setItem(inventorySlot, rottenStack);
                syncPlayerInventoryStack(player, rottenStack);
                if (player instanceof ServerPlayer serverPlayer) {
                    var advancement = serverPlayer.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:bad_meat"));

                    if (advancement == null) return;

                    var progress= serverPlayer.getAdvancements().getOrStartProgress(advancement);
                    if (!progress.isDone()) serverPlayer.getAdvancements().award(advancement, "bad_meat_0");
                }
            } else {
                CustomData.update(DataComponents.CUSTOM_DATA, stack,
                        data -> {
                            data.putDouble("SpoilageMax", updatedSpoilage);
                            data.remove("InventoryClock");
                        });
                syncPlayerInventoryStack(player, stack);
            }
            changed = true;
        }

        if (changed) {
            player.getInventory().setChanged();
        }
    }

    private static void syncPlayerInventoryStack(Player player, ItemStack stack) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        for (int menuSlot = 0; menuSlot < serverPlayer.containerMenu.slots.size(); menuSlot++) {
            if (serverPlayer.containerMenu.slots.get(menuSlot).getItem() == stack) {
                serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(
                        serverPlayer.containerMenu.containerId,
                        serverPlayer.containerMenu.incrementStateId(),
                        menuSlot,
                        stack.copy()));
            }
        }
    }
}
