package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.world.inventory.AAFScriptureGUIMenu;

public class AAFScriptureGUIScreen extends AbstractContainerScreen<AAFScriptureGUIMenu> {
	private static final ResourceLocation COVER = tex("book_cover.png");
	private static final ResourceLocation PAGE1 = tex("book_page1.png");
	private static final ResourceLocation PAGE2 = tex("book_page2.png");
	private static final ResourceLocation TITANIUM = tex("titanium_text.png");
	private static final ResourceLocation URANIUM_ROD = tex("uranium_rod_text.png");
	private static final ResourceLocation NEXT = tex("next_page_button.png");
	private static final ResourceLocation PREVIOUS = tex("previous_page_button.png");
	private static final ResourceLocation[] PAGE2_TEXT = {
			tex("page_2_text1.png"), tex("page_2_text2.png"), tex("page_2_text3.png"),
			tex("page_2_text4.png"), tex("page_2_text5.png"), tex("page_2_text6.png"),
			tex("page_2_text7.png"), tex("page_2_text8.png"), tex("page_2_text9.png"),
			tex("page_2_text10.png"), tex("page_2_text12.png"), tex("page_2_text13.png")
	};
	private static final ResourceLocation PAGE1_TITLE = tex("page_2_text14.png");

	private int page;

	public AAFScriptureGUIScreen(AAFScriptureGUIMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static ResourceLocation tex(String file) {
		return ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/screens/" + file);
	}

	@Override
	protected void init() {
		super.init();
		addBookButton(this.leftPos + 206, this.topPos + 150, NEXT, true);
		addBookButton(this.leftPos - 51, this.topPos + 150, PREVIOUS, false);
	}

	private void addBookButton(int x, int y, ResourceLocation texture, boolean next) {
		ImageButton button = new ImageButton(x, y, 16, 16, new WidgetSprites(texture, texture), b -> {
			if (next) page = Math.min(1, page + 1);
			else page = Math.max(0, page - 1);
		}) {
			@Override
			public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
				boolean visible = next ? page < 1 : page > 0;
				if (visible) graphics.blit(texture, getX(), getY(), 0, 0, 16, 16, 16, 16);
			}
		};
		this.addRenderableWidget(button);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(COVER, this.leftPos - 66, this.topPos - 12, 0, 0, 304, 192, 304, 192);
		if (page == 0) {
			graphics.blit(PAGE1, this.leftPos - 66, this.topPos - 11, 0, 0, 304, 192, 304, 192);
			graphics.blit(TITANIUM, this.leftPos + 97, this.topPos + 140, 0, 0, 32, 32, 32, 32);
			graphics.blit(URANIUM_ROD, this.leftPos + 97, this.topPos + 82, 0, 0, 64, 32, 64, 32);
			graphics.blit(PAGE1_TITLE, this.leftPos - 50, this.topPos + 16, 0, 0, 136, 16, 136, 16);
		} else {
			graphics.blit(PAGE2, this.leftPos - 66, this.topPos - 11, 0, 0, 304, 192, 304, 192);
			int[][] pos = {
					{-46,8},{-47,38},{-46,66},{91,8},{-45,96},{94,20},
					{94,49},{91,78},{91,104},{91,114},{91,131},{-45,124}
			};
			int[][] size = {
					{128,24},{128,24},{128,24},{128,24},{128,24},{128,24},
					{128,24},{128,24},{128,8},{128,16},{56,32},{136,32}
			};
			for (int i = 0; i < PAGE2_TEXT.length; i++) {
				graphics.blit(PAGE2_TEXT[i], this.leftPos + pos[i][0], this.topPos + pos[i][1],
						0, 0, size[i][0], size[i][1], size[i][0], size[i][1]);
			}
		}
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
}
