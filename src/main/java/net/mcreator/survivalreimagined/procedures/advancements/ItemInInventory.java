package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemInInventory {
    private static final Map<UUID, Integer> inventoryHashes = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                int hash = getInventoryHash(player);

                if (inventoryHashes.getOrDefault(player.getUUID(), Integer.MIN_VALUE) != hash) {
                    inventoryHashes.put(player.getUUID(), hash);
                    execute(player);
                }
            }
        });
    }

    private static int getInventoryHash(ServerPlayer player) {
        int hash = 1;

        for (ItemStack stack : player.getInventory().items) {
            hash = 31 * hash + ItemStack.hashItemAndComponents(stack);
            hash = 31 * hash + stack.getCount();
        }
        return hash;
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

        if (!found) return;

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
