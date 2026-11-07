package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class GemBlockBroken {
    private static final TagKey<Block> GEM_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:gem_blocks"));
    private static final SoundEvent RESONATE_SOUND = BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.amethyst_block.resonate"));
    private static final List<PendingSound> PENDING_SOUNDS = new ArrayList<>();

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (world.isClientSide()) return;
            if (!state.is(GEM_BLOCKS)) return;

            PENDING_SOUNDS.add(new PendingSound((ServerLevel) world, pos.immutable(), world.getServer().getTickCount() + 1));


        });
        ServerTickEvents.END_SERVER_TICK.register(GemBlockBroken::processPendingSounds);
    }
    private static void processPendingSounds(MinecraftServer server) {
        for (int i = PENDING_SOUNDS.size() - 1; i >= 0; i--) {
            PendingSound pendingSound = PENDING_SOUNDS.get(i);

            if (server.getTickCount() >= pendingSound.executeAt()) {
                pendingSound.level().playSound(null, pendingSound.pos(), RESONATE_SOUND, SoundSource.BLOCKS, 0.5f, 1.0f);
                PENDING_SOUNDS.remove(i);
            }
        }
    }
    private record PendingSound(ServerLevel level, BlockPos pos, int executeAt) {}
}
