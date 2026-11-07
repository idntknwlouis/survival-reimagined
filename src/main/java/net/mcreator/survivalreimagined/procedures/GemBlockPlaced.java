package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class GemBlockPlaced {
    private static final TagKey<Block> GEM_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:gem_blocks"));
    private static final List<PendingCheck> PENDING = new ArrayList<>();

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClientSide()) return InteractionResult.PASS;
            if (!(world instanceof ServerLevel level)) return InteractionResult.PASS;

            BlockPos targetPos = hitResult.getBlockPos().relative(hitResult.getDirection());

            PENDING.add(new PendingCheck(level, targetPos.immutable(), level.getServer().getTickCount() + 1));
            return InteractionResult.PASS;
        });
        ServerTickEvents.END_SERVER_TICK.register(GemBlockPlaced::processPending);
    }

    private static void processPending(MinecraftServer server) {
        for (int i = PENDING.size() - 1; i >= 0; i--) {
            PendingCheck check = PENDING.get(i);

            if (server.getTickCount() >= check.executeAt()) {
                BlockState state = check.level.getBlockState(check.pos());

                if (state.is(GEM_BLOCKS)) {
                    check.level().playSound(null, check.pos(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.amethyst_block.resonate")), SoundSource.BLOCKS, 0.5f, 1.0f);
                }
                PENDING.remove(i);
            }
        }
    }


    private record PendingCheck(ServerLevel level, BlockPos pos, int executeAt) {}
}
