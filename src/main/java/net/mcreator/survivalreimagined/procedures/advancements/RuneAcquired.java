package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class RuneAcquired {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(level -> {
            for (ServerPlayer player : level.overworld().players()) {
                execute(player);
            }
        });
    }

    public static void execute(ServerPlayer player) {
        if (player == null) return;

        boolean isSilverRune = player.getInventory().contains(stack -> !stack.isEmpty() && ItemStack.isSameItem(stack, new ItemStack(SurvivalReimaginedModItems.EMPTY_SILVER_RUNE.get())));
        boolean isGoldRune = player.getInventory().contains(stack -> !stack.isEmpty() && ItemStack.isSameItem(stack, new ItemStack(SurvivalReimaginedModItems.EMPTY_GOLD_RUNE.get())));

        if (isSilverRune || isGoldRune) {
            AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:runes_adv"));
            if (advancement != null) {}
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getRemainingCriteria()) {
                    player.getAdvancements().award(advancement, criteria);
                }
            }
        }
    }
}
