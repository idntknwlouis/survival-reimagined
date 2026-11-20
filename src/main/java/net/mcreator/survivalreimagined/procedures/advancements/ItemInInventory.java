package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemInInventory {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                execute(player);
            }
        });
    }

    public static void execute(ServerPlayer player) {
        if (player == null) return;

        checkTag(player, "c:cured_meat", "survival_reimagined:cured_meat");
        checkTag(player, "c:rmi_runes", "survival_reimagined:gem_runes");
        checkTag(player, "c:metal_rocks/uranium", "survival_reimagined:uranium");
    }


    private static void checkTag(ServerPlayer player, String tagID, String advancementID) {
        TagKey<Item> tag = TagKey.create(Registries.ITEM, ResourceLocation.parse(tagID));
        boolean found = player.getInventory().contains(stack -> !stack.isEmpty() && stack.is(tag));

        AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse(advancementID));
        if (advancement == null) return;
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);

        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria()) {
                player.getAdvancements().award(advancement, criteria);
            }
        }
    }
}
