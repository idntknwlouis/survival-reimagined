package net.mcreator.survivalreimagined.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.survivalreimagined.world.inventory.BackpackGUI1Menu;
import net.mcreator.survivalreimagined.world.inventory.BackpackGUI2Menu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class BackpackGUI2Screen extends AbstractContainerScreen<BackpackGUI2Menu> {
    private final Level world;
    private final int x,y,z;
    private final Player entity;
    private boolean menuStateUpdateActive = false;
    private static final ResourceLocation BACKGROUND = ResourceLocation.parse("survival_reimagined:textures/screens/backpack_gui_2.png");
    private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_1 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_2 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_3 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_4 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_5 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_6 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_7 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_8 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_9 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_10 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_11 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_12 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_13 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_14 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_15 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_16 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_17 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");

    public BackpackGUI2Screen(BackpackGUI2Menu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        guiGraphics.blit(IMAGE_0, this.leftPos + 7, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_1, this.leftPos + 25, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_2, this.leftPos + 43, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_3, this.leftPos + 61, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_4, this.leftPos + 79, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_5, this.leftPos + 97, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_6, this.leftPos + 115, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_7, this.leftPos + 133, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_8, this.leftPos + 151, this.topPos + 56, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_9, this.leftPos + 9, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_10, this.leftPos + 27, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_11, this.leftPos + 45, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_12, this.leftPos + 63, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_13, this.leftPos + 81, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_14, this.leftPos + 99, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_15, this.leftPos + 117, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_16, this.leftPos + 135, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_17, this.leftPos + 153, this.topPos + 58, 0, 0, 13, 13, 13, 13);

        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.literal("Backpack"), 70, 2, -12829636, false);
    }

    @Override
    public void init() {
        super.init();
    }
}
