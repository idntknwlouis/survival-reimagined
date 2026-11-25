package net.mcreator.survivalreimagined.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.survivalreimagined.world.inventory.BackpackGUI1Menu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class BackpackGUI1Screen extends AbstractContainerScreen<BackpackGUI1Menu> {
    private final Level world;
    private final int x,y,z;
    private final Player entity;
    private boolean menuStateUpdateActive = false;
    private static final ResourceLocation BACKGROUND = ResourceLocation.parse("survival_reimagined:textures/screens/backpack_gui_1.png");
    private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_1 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_2 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_3 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_4 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_5 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_6 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_7 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_8 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_9 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_10 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_11 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_12 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_13 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_14 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_15 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_16 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_17 = ResourceLocation.parse("survival_reimagined:textures/screens/fake_slot.png");
    private static final ResourceLocation IMAGE_18 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_19 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_20 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_21 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_22 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_23 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_24 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_25 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_26 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_27 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_28 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_29 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_30 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_31 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_32 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_33 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_34 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");
    private static final ResourceLocation IMAGE_35 = ResourceLocation.parse("survival_reimagined:textures/screens/slot_x.png");

    public BackpackGUI1Screen(BackpackGUI1Menu container, Inventory inventory, Component text) {
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
        guiGraphics.blit(IMAGE_9, this.leftPos + 7, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_10, this.leftPos + 25, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_11, this.leftPos + 43, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_12, this.leftPos + 61, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_13, this.leftPos + 79, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_14, this.leftPos + 97, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_15, this.leftPos + 115, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_16, this.leftPos + 133, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_17, this.leftPos + 151, this.topPos + 38, 0, 0, 18, 18, 18, 18);
        guiGraphics.blit(IMAGE_18, this.leftPos + 9, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_19, this.leftPos + 27, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_20, this.leftPos + 45, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_21, this.leftPos + 63, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_22, this.leftPos + 81, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_23, this.leftPos + 99, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_24, this.leftPos + 117, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_25, this.leftPos + 135, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_26, this.leftPos + 153, this.topPos + 40, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_27, this.leftPos + 9, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_28, this.leftPos + 27, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_29, this.leftPos + 45, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_30, this.leftPos + 63, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_31, this.leftPos + 81, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_32, this.leftPos + 99, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_33, this.leftPos + 117, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_34, this.leftPos + 135, this.topPos + 58, 0, 0, 13, 13, 13, 13);
        guiGraphics.blit(IMAGE_35, this.leftPos + 153, this.topPos + 58, 0, 0, 13, 13, 13, 13);
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
