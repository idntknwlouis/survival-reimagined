package net.mcreator.survivalreimagined.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.MillstoneBlockEntity;
import net.mcreator.survivalreimagined.world.inventory.MillstoneGUIMenu;

public class MillstoneGUIScreen extends AbstractContainerScreen<MillstoneGUIMenu> {
    private static final ResourceLocation BACKGROUND = SurvivalReimaginedMod.asResource("textures/screens/millstone_gui.png");
    private static final ResourceLocation ARROW = SurvivalReimaginedMod.asResource("textures/screens/millstoe_arrow.png");

    public MillstoneGUIScreen(MillstoneGUIMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        int frame = Mth.clamp(this.menu.getProgress(), 0, MillstoneBlockEntity.MAX_PROGRESS);
        graphics.blit(ARROW, this.leftPos + 79, this.topPos + 41, 0, frame * 18, 18, 18, 18, 144);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    }
}
