package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FertilizerTooltip {
    public static void register() {
        ItemTooltipCallback.EVENT.register((ItemStack stack, Item.TooltipContext context, TooltipFlag flag, List<Component> lines) -> {
            execute(stack, lines);
        });
    }

    public static void execute(ItemStack itemstack,List<Component> tooltip) {
        if (itemstack == null || tooltip == null || itemstack.isEmpty()) {
            return;
        }

        TagKey<Item> nitrogenTag = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:fertilizers/nitrogen"));
        TagKey<Item> phosphorusTag = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:fertilizers/phosphorus"));
        TagKey<Item> potassiumTag = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:fertilizers/potassium"));

        if (itemstack.is(nitrogenTag)) {
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.literal("§7 Nitrogen: 20%"));
            } else {
                tooltip.add(Component.literal("§7 N: 20%"));
            }
        }

        if (itemstack.is(phosphorusTag)) {
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.literal("§7 Phosphorus: 20%"));
            } else {
                tooltip.add(Component.literal("§7 P: 20%"));
            }
        }

        if (itemstack.is(potassiumTag)) {
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.literal("§7 Potassium: 20%"));
            } else {
                tooltip.add(Component.literal("§7 K: 20%"));
            }
        }
    }
}
