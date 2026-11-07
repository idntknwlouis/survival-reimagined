package net.mcreator.survivalreimagined.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.screenhandler.client.ClientNetworking;
import net.mcreator.survivalreimagined.procedures.gui.AAF.NextPageButtonDisplay;
import net.mcreator.survivalreimagined.procedures.gui.AAF.Page1;
import net.mcreator.survivalreimagined.procedures.gui.AAF.Page2;
import net.mcreator.survivalreimagined.procedures.gui.AAF.PreviousPageButtonDisplay;
import net.mcreator.survivalreimagined.world.inventory.AAFScriptureGUIMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.mcreator.survivalreimagined.network.gui.AAFScriptureGUIButtonMessage;

public class AAFScriptureGUIScreen extends AbstractContainerScreen<AAFScriptureGUIMenu> {
    private final Level level;
    private final int x, y, z;
    private final Player player;
    private boolean menuStateUpdateActive = false;
    private ImageButton nextPageUnhovered;
    private ImageButton previousPageUnhovered;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private int currentPage = 1;
    private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("survival_reimagined:textures/screens/book_cover.png");
    private static final ResourceLocation IMAGE_1 = ResourceLocation.parse("survival_reimagined:textures/screens/book_page1.png");
    private static final ResourceLocation IMAGE_2 = ResourceLocation.parse("survival_reimagined:textures/screens/titanium_text.png");
    private static final ResourceLocation IMAGE_3 = ResourceLocation.parse("survival_reimagined:textures/screens/uranium_rod_text.png");
    private static final ResourceLocation IMAGE_4 = ResourceLocation.parse("survival_reimagined:textures/screens/book_page2.png");
    private static final ResourceLocation IMAGE_5 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text1.png");
    private static final ResourceLocation IMAGE_6 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text2.png");
    private static final ResourceLocation IMAGE_7 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text3.png");
    private static final ResourceLocation IMAGE_8 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text4.png");
    private static final ResourceLocation IMAGE_9 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text5.png");
    private static final ResourceLocation IMAGE_10 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text6.png");
    private static final ResourceLocation IMAGE_11 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text7.png");
    private static final ResourceLocation IMAGE_12 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text8.png");
    private static final ResourceLocation IMAGE_13 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text9.png");
    private static final ResourceLocation IMAGE_14 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text10.png");
    private static final ResourceLocation IMAGE_15 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text12.png");
    private static final ResourceLocation IMAGE_16 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text13.png");
    private static final ResourceLocation IMAGE_17 = ResourceLocation.parse("survival_reimagined:textures/screens/page_2_text14.png");


    public AAFScriptureGUIScreen(AAFScriptureGUIMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.level = container.level;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.player = container.player;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        if (currentPage == 1
                && mouseX >= nextPageUnhovered.getX()
                && mouseX < nextPageUnhovered.getX() + nextPageUnhovered.getWidth()
                && mouseY >= nextPageUnhovered.getY()
                && mouseY < nextPageUnhovered.getY() + nextPageUnhovered.getHeight()) {
            guiGraphics.renderTooltip(
                    font,
                    Component.translatable("gui.survival_reimagined.aaf_scripture_gui.tooltip_next_page"),
                    mouseX, mouseY
            );
        } else if (currentPage == 2
                && mouseX >= previousPageUnhovered.getX()
                && mouseX < previousPageUnhovered.getX() + previousPageUnhovered.getWidth()
                && mouseY >= previousPageUnhovered.getY()
                && mouseY < previousPageUnhovered.getY() + previousPageUnhovered.getHeight()) {
            guiGraphics.renderTooltip(
                    font,
                    Component.translatable("gui.survival_reimagined.aaf_scripture_gui.tooltip_previous_page"),
                    mouseX, mouseY
            );
        } else {
            this.renderTooltip(guiGraphics, mouseX,mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(IMAGE_0, this.leftPos + -66, this.topPos + -12, 0, 0, 304, 192, 304, 192);
        if (currentPage == 1) {
            guiGraphics.blit(IMAGE_1, this.leftPos + -66, this.topPos + -11, 0, 0, 304, 192, 304, 192);
            guiGraphics.blit(IMAGE_2, this.leftPos + 97, this.topPos + 140, 0, 0, 32, 32, 32, 32);
            guiGraphics.blit(IMAGE_3, this.leftPos + 97, this.topPos + 82, 0, 0, 64, 32, 64, 32);
            guiGraphics.blit(IMAGE_17, this.leftPos + -50, this.topPos + 16, 0, 0, 136, 16, 136, 16);
        }
        if (currentPage == 2) {
            guiGraphics.blit(IMAGE_4, this.leftPos + -66, this.topPos + -11, 0, 0, 304, 192, 304, 192);
            guiGraphics.blit(IMAGE_5, this.leftPos + -46, this.topPos + 8, 0, 0, 128, 24, 128, 24);
            guiGraphics.blit(IMAGE_6, this.leftPos + -47, this.topPos + 38, 0, 0, 128, 24, 128, 24);
            guiGraphics.blit(IMAGE_7, this.leftPos + -46, this.topPos + 66, 0, 0, 128, 24, 128, 24);
            guiGraphics.blit(IMAGE_8, this.leftPos + 91, this.topPos + 8, 0, 0, 128, 24, 128, 24);
            guiGraphics.blit(IMAGE_9, this.leftPos + -45, this.topPos + 96, 0, 0, 128, 24, 128, 24);
            guiGraphics.blit(IMAGE_10, this.leftPos + 94, this.topPos + 20, 0, 0, 128, 24, 128, 24);
            guiGraphics.blit(IMAGE_11, this.leftPos + 94, this.topPos + 49, 0, 0, 128, 24, 128, 24);
            guiGraphics.blit(IMAGE_12, this.leftPos + 91, this.topPos + 78, 0, 0, 128, 24, 128, 24);
            guiGraphics.blit(IMAGE_13, this.leftPos + 91, this.topPos + 104, 0, 0, 128, 8, 128, 8);
            guiGraphics.blit(IMAGE_14, this.leftPos + 91, this.topPos + 114, 0, 0, 128, 16, 128, 16);
            guiGraphics.blit(IMAGE_15, this.leftPos + 91, this.topPos + 131, 0, 0, 56, 32, 56, 32);
            guiGraphics.blit(IMAGE_16, this.leftPos + -45, this.topPos + 124, 0, 0, 136, 32, 136, 32);
        }
        RenderSystem.disableBlend();
    }
    public void setCurrentPage(int page) {
        this.currentPage = page;
        updatePageButtons();
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
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}

    @Override
    public void init() {
        super.init();
        nextPageUnhovered = new ImageButton(this.leftPos + 206, this.topPos + 150, 16, 16,
                new WidgetSprites(ResourceLocation.parse("survival_reimagined:textures/screens/next_page_button.png"), ResourceLocation.parse("survival_reimagined:textures/screens/next_page_button.png")), e -> {

            ClientPlayNetworking.send(
                    new AAFScriptureGUIButtonMessage(0, x, y, z)
            );
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                int x = AAFScriptureGUIScreen.this.x;
                int y = AAFScriptureGUIScreen.this.y;
                if (currentPage == 1)
                    guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
            }
        };
        this.addRenderableWidget(nextPageUnhovered);
        previousPageUnhovered = new ImageButton(this.leftPos + -51, this.topPos + 150, 16, 16,
                new WidgetSprites(ResourceLocation.parse("survival_reimagined:textures/screens/previous_page_button.png"), ResourceLocation.parse("survival_reimagined:textures/screens/previous_page_button.png")), e -> {
            ClientPlayNetworking.send(
                    new AAFScriptureGUIButtonMessage(1, x, y, z)
            );
        }) {
          @Override
          public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
              int x = AAFScriptureGUIScreen.this.x;
              int y = AAFScriptureGUIScreen.this.y;
              if (currentPage == 2)
                  guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
          }
        };
        this.addRenderableWidget(previousPageUnhovered);
    }
    private void updatePageButtons() {
        if (nextButton != null) {
            nextButton.visible = currentPage == 1;
        }
        if (previousButton != null) {
            previousButton.visible = currentPage == 2;
        }
    }
}
