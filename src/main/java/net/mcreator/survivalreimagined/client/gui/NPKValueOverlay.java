package net.mcreator.survivalreimagined.client.gui;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NPKValueOverlay {

    public static void onHudRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen != null || minecraft.options.hideGui) {
            return;
        }

        Player entity = minecraft.player;
        if (entity == null) return;
        Level world = entity.level();

        if (NPKDisplay.execute(world, entity)) {
            String textToRender = NPKValues.execute(world, entity);

            int w = guiGraphics.guiWidth();
            int h = guiGraphics.guiHeight();

            int textX = (w / 2) + 20;
            int textY = (h / 2) + 10;

            guiGraphics.drawString(minecraft.font, textToRender, textX, textY, 0xFFFFFF, true);
        }
    }
}
