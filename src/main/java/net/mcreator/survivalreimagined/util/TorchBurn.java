package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TorchBurn {
    private static final int BURN_INTERVAL = 20;
    private static final int MAX_BURN = 2000;
    private static final int BURN_PER_INTERVAL = 1;
    private static final TagKey<Block> WOODEN_CONTAINERS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "wooden_containers"));

    private static final Map<ServerLevel, Set<Long>> LOADED_CHUNKS = new ConcurrentHashMap<>();

    private TorchBurn() {}

    public static void register() {
        ServerChunkEvents.CHUNK_LOAD.register((level, chunk) -> {
            LOADED_CHUNKS.computeIfAbsent(level, ignored -> ConcurrentHashMap.newKeySet()).add(chunk.getPos().toLong());
        });
        ServerChunkEvents.CHUNK_UNLOAD.register((level, chunk) -> {
            Set<Long> chunks = LOADED_CHUNKS.get(level);

            if (chunks != null) {
                chunks.remove(chunk.getPos().toLong());
                if (chunks.isEmpty()) LOADED_CHUNKS.remove(level, chunks);
            }
        });
        ServerTickEvents.END_WORLD_TICK.register(TorchBurn::onWorldTick);
    }

    private static void onWorldTick(ServerLevel level) {
        if (level.getGameTime() % BURN_INTERVAL != 0) return;
        Set<Long> chunks = LOADED_CHUNKS.get(level);

        if (chunks == null || chunks.isEmpty()) return;
        for (long chunkKey : chunks) {
            ChunkPos pos = new ChunkPos(chunkKey);
            LevelChunk chunk = level.getChunkSource().getChunkNow(pos.x, pos.z);

            if (chunk == null) continue;

            for(BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                if (blockEntity.isRemoved() || !blockEntity.getBlockState().is(WOODEN_CONTAINERS)) continue;
                if (blockEntity instanceof Container container) burnContainer(container, level);
            }
        }
    }

    private static void burnContainer(Container container, ServerLevel level) {
        boolean changed = false;

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack stack = container.getItem(slot);

            if (stack.isEmpty() || !stack.is(SurvivalReimaginedModItems.LIT_CRUDE_TORCH.get())) continue;
            double currentBurn = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("Burn");
            double updateBurn = Math.min(currentBurn + BURN_PER_INTERVAL, MAX_BURN);
            if (updateBurn >= MAX_BURN) {
                ItemStack burntTorch = new ItemStack(SurvivalReimaginedModItems.BURNT_TORCH.get(), stack.getCount());
                container.setItem(slot, burntTorch);
                syncContainerStack(level, burntTorch);
            } else {
                CustomData.update(DataComponents.CUSTOM_DATA, stack, data -> data.putDouble("Burn", updateBurn));
                syncContainerStack(level, stack);
            }
            changed = true;
        }
        if (changed) container.setChanged();
    }

    private static void syncContainerStack(ServerLevel level, ItemStack stack) {
        for (ServerPlayer player : level.players()) {
            for (int menuSlot = 0; menuSlot < player.containerMenu.slots.size(); menuSlot++) {
                ItemStack menuStack = player.containerMenu.slots.get(menuSlot).getItem();

                if (menuStack == stack) {
                    player.connection.send(new ClientboundContainerSetSlotPacket(
                            player.containerMenu.containerId,
                            player.containerMenu.incrementStateId(),
                            menuSlot,
                            stack.copy()
                    ));
                }
            }
        }
    }
}
