package net.mcreator.survivalreimagined.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class HideTooltip {
    @Environment(EnvType.CLIENT)
    public static void register() {
        ItemTooltipCallback.EVENT.register((ItemStack stack, Item.TooltipContext context, TooltipFlag flag, List<Component> lines) -> {
            execute(stack, lines);
        });
    }

    public static void execute(ItemStack itemstack, List<Component> tooltip) {
        if (tooltip == null || itemstack == null) return;

        Item item = itemstack.getItem();

        if (itemstack.getItem() == SurvivalReimaginedModItems.HIDE.get()) {
            CustomData customData = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            double drying = customData.copyTag().getDouble("Drying");

            if (drying == 0) {
                tooltip.add(Component.literal("§7 De-Haired"));
            } else if (drying > 0 && drying < 200.0) {
                long percent = Math.round((drying / 200.0) * 100.0);
                tooltip.add(Component.literal("§7 " + percent + "% Dried"));
            } else {
                tooltip.add(Component.literal("§7 De-Haired"));
            }
        } else if (item == SurvivalReimaginedModItems.DRIED_HIDE.get()) {
            tooltip.add(Component.literal("§7 Dried"));
        } else if (item == SurvivalReimaginedModItems.PREPARED_HIDE.get()) {
            tooltip.add(Component.literal("§7 Prepared"));
        } else if (itemstack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "animal/hide")))) {
            tooltip.add(Component.literal("§7 Raw"));
        }
    }
}
