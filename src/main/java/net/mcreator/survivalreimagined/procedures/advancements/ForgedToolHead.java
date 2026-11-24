package net.mcreator.survivalreimagined.procedures.advancements;

import net.mcreator.survivalreimagined.world.inventory.ForgeGUIMenu;
import net.mcreator.survivalreimagined.world.inventory.MPTGUIMenu;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class ForgedToolHead {
    public static void execute(LevelAccessor world, Entity entity) {
        if (!(entity instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        if (player instanceof ServerPlayer serverPlayer) {
            if (player.containerMenu instanceof ForgeGUIMenu) {
                checkInventoryTag(serverPlayer, "c:tool_part/bronze", "survival_reimagined:forged_bronze");
                checkInventoryTag(serverPlayer, "c:tool_part/steel", "survival_reimagined:forged_steel");
                checkInventoryTag(serverPlayer, "c:tool_part/diamond", "survival_reimagined:forged_plated_diamond");
            }
            if (player.containerMenu instanceof MPTGUIMenu) {
                checkInventoryTag(serverPlayer, "c:processed_item",  "survival_reimagined:mineral_processed");
            }
        }
    }

    private static void checkInventoryTag(ServerPlayer player, String tagID, String advancementID) {
        ResourceLocation tagLocation = ResourceLocation.parse(tagID);
        ResourceLocation advancementLocation = ResourceLocation.parse(advancementID);

        var tag = player.registryAccess().lookupOrThrow(Registries.ITEM).get(TagKey.create(Registries.ITEM, tagLocation));
        if (tag.isEmpty()) return;

        boolean found = false;
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.is(tag.get())) {
                found = true;
                break;
            }
        }
        if (!found) return;

        AdvancementHolder advancement = player.server.getAdvancements().get(advancementLocation);
        if (advancement == null) return;
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria()) {
                player.getAdvancements().award(advancement, criteria);
            }
        }
    }
}
