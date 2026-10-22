package net.mcreator.survivalreimagined.util;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class SoilTooltip {
    public static void append(ItemStack stack, List<Component> lines) {
        if (lines == null || stack == null)
            return;

        if (stack.is(Blocks.DIRT.asItem())) {
            lines.add(Component.literal(" Low Fertility").withStyle(ChatFormatting.GRAY));
            if (Screen.hasShiftDown()) {
                lines.add(Component.literal("  Nitrogen: 20%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  Phosphorus: 20%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  Potassium: 20%").withStyle(ChatFormatting.GRAY));
            } else {
                lines.add(Component.literal("  N: 20%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  P: 20%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  K: 20%").withStyle(ChatFormatting.GRAY));
            }
        } else if (stack.is(SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_DIRT.get().asItem())) {
            lines.add(Component.literal(" Medium Fertility").withStyle(ChatFormatting.GRAY));
            if (Screen.hasShiftDown()) {
                lines.add(Component.literal("  Nitrogen: 60%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  Phosphorus: 60%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  Potassium: 60%").withStyle(ChatFormatting.GRAY));
            } else {
                lines.add(Component.literal("  N: 60%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  P: 60%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  K: 60%").withStyle(ChatFormatting.GRAY));
            }
        } else if (stack.is(SurvivalReimaginedModBlocks.HIGH_FERTILITY_DIRT.get().asItem())) {
            lines.add(Component.literal(" High Fertility").withStyle(ChatFormatting.GRAY));
            if (Screen.hasShiftDown()) {
                lines.add(Component.literal("  Nitrogen: 80%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  Phosphorus: 80%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  Potassium: 80%").withStyle(ChatFormatting.GRAY));
            } else {
                lines.add(Component.literal("  N: 80%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  P: 80%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal("  K: 80%").withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
