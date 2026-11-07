package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.config.SurvivalReimaginedModMenu;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;
import net.mcreator.survivalreimagined.world.inventory.AdvancedAlloyForgeGUIMenu;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FullyUpgradedForge {
    private static final TagKey<Item> ADV_FORGE_UPGRADES = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:adv_forge_upgrades"));

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                execute(player);
            }
        });
    }


    public static void execute(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (!(player.containerMenu instanceof AdvancedAlloyForgeGUIMenu)) return;
        AdvancedAlloyForgeGUIMenu menu = (AdvancedAlloyForgeGUIMenu) player.containerMenu;

        for (int slot = 4; slot <= 7; slot++) {
            ItemStack stack = menu.getSlot(slot).getItem();

            if (!stack.is(ADV_FORGE_UPGRADES)) return;
        }
        AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:fully_upgrade_forge"));

        if (advancement == null) return;

        AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);

        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria()) {
                serverPlayer.getAdvancements().award(advancement, criteria);
            }
        }
    }
}
