package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CraftedItemADV {
    private static final Map<UUID, Map<String, Integer>> LAST_INVENTORY = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                Map<String, Integer> current = new HashMap<>();

                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);

                    if (!stack.isEmpty()) {
                        String key = stack.getItem().toString();
                        current.merge(key, stack.getCount(), Integer::sum);
                    }
                }
                Map<String, Integer> previous = LAST_INVENTORY.getOrDefault(player.getUUID(), new HashMap<>());

                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack stack = player.getInventory().getItem(i);

                    if (stack.isEmpty()) continue;

                    String key = stack.getItem().toString();

                    int oldCount = previous.getOrDefault(key, 0);
                    int newCount = current.getOrDefault(key, 0);

                    if (newCount > oldCount) {
                        execute(player, stack);
                    }
                }
                LAST_INVENTORY.put(player.getUUID(), current);
            }
        });
    }

    private static void execute(ServerPlayer player, ItemStack stack) {
        if (stack.is(TagKey.create(Registries.ITEM, ResourceLocation.parse("c:clay_molds")))) {
            awardAdvancement(player, "survival_reimagined:molds");
        }
        if (stack.is(SurvivalReimaginedModItems.HEART_OF_THE_BLOOD_MOON.get())) {
            awardAdvancement(player, "survival_reimagined:heart_bloodmoon");
        }
        if (stack.is(Items.ENDER_EYE)) {
            awardAdvancement(player, "survival_reimagined:eye_of_ender");
        }
        if (stack.is(TagKey.create(Registries.ITEM, ResourceLocation.parse("c:adv_forge_upgrades")))) {
            awardAdvancement(player, "survival_reimagined:a_forge_upgrade");
        }
        if (stack.is(TagKey.create(Registries.ITEM, ResourceLocation.parse("c:planks/single")))) {
            awardAdvancement(player, "survival_reimagined:planks");
        }
    }

    private static void awardAdvancement(ServerPlayer player, String advancementID) {
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
