package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.block.CarcassPartBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CarcassPartBroken {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(state.getBlock() instanceof CarcassPartBlock)) return;
            if (!(player instanceof ServerPlayer serverPlayer)) return;

            var advancement = serverPlayer.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:extra_animal_meat"));

            if (advancement == null) return;

            var progress= serverPlayer.getAdvancements().getOrStartProgress(advancement);
            if (!progress.isDone()) serverPlayer.getAdvancements().award(advancement, "extra_animal_meat_0");
        });
    }
}
