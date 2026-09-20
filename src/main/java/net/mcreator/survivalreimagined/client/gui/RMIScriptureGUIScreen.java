package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.world.inventory.RMIScriptureGUIMenu;

public class RMIScriptureGUIScreen extends AbstractContainerScreen<RMIScriptureGUIMenu> {
	private static final ResourceLocation COVER = tex("book_cover_rmi.png");
	private static final ResourceLocation PAGE = tex("book_page2.png");
	private static final ResourceLocation DIAMOND_TEXT = tex("diamond_text.png");
	private static final ResourceLocation RUNE_TEXT = tex("rune_text.png");
	private static final ResourceLocation RUNE_ICON = tex("rune_icon.png");
	private static final ResourceLocation DIAMOND_ICON = tex("diamond_icon.png");
	private static final ResourceLocation SWORD_ICON = tex("sword_icon.png");
	private static final ResourceLocation PICKAXE_ICON = tex("pickaxe_icon.png");
	private static final ResourceLocation ARMOR_ICON = tex("armor_icon.png");
	private static final ResourceLocation NEXT = tex("next_page_button.png");
	private static final ResourceLocation PREVIOUS = tex("previous_page_button.png");

	private int page;

	public RMIScriptureGUIScreen(RMIScriptureGUIMenu menu, Inventory inventory, Component title) {
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
			if (next) page = Math.min(5, page + 1);
			else page = Math.max(0, page - 1);
		}) {
			@Override
			public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
				boolean visible = next ? page < 5 : page > 0;
				if (visible) graphics.blit(texture, getX(), getY(), 0, 0, 16, 16, 16, 16);
			}
		};
		this.addRenderableWidget(button);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(COVER, this.leftPos - 66, this.topPos - 12, 0, 0, 304, 192, 304, 192);
		graphics.blit(PAGE, this.leftPos - 66, this.topPos - 11, 0, 0, 304, 192, 304, 192);

		if (page == 0) {
			graphics.blit(DIAMOND_TEXT, this.leftPos - 33, this.topPos + 16, 0, 0, 48, 16, 48, 16);
			graphics.blit(RUNE_TEXT, this.leftPos + 26, this.topPos + 17, 0, 0, 32, 16, 32, 16);
			graphics.blit(RUNE_ICON, this.leftPos + 44, this.topPos + 128, 0, 0, 32, 32, 32, 32);
			graphics.blit(DIAMOND_ICON, this.leftPos + 52, this.topPos + 134, 0, 0, 16, 16, 16, 16);
			graphics.blit(SWORD_ICON, this.leftPos - 39, this.topPos + 43, 0, 0, 32, 32, 32, 32);
			graphics.blit(PICKAXE_ICON, this.leftPos + 1, this.topPos + 43, 0, 0, 32, 32, 32, 32);
			graphics.blit(ARMOR_ICON, this.leftPos + 40, this.topPos + 43, 0, 0, 32, 32, 32, 32);
		}
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		if (page == 0) return;
		int x = -42;
		int y = 10;
		switch (page) {
			case 1 -> drawRune(graphics, x, y, "Sapphire", "Ocean / movement effects");
			case 2 -> drawRune(graphics, x, y, "Amber", "Fire-focused effects");
			case 3 -> drawRune(graphics, x, y, "Diamond", "Defense / durability");
			case 4 -> drawRune(graphics, x, y, "Emerald", "Utility / village effects");
			default -> {
				graphics.drawString(this.font, "Ruby", x, y, 0x3c2d24, false);
				graphics.drawString(this.font, "Healing / durability", x, y + 18, 0x3c2d24, false);
				graphics.drawString(this.font, "Lapis", x, y + 48, 0x3c2d24, false);
				graphics.drawString(this.font, "Experience effects", x, y + 66, 0x3c2d24, false);
				graphics.drawString(this.font, "Gold variants are", x, y + 96, 0x3c2d24, false);
				graphics.drawString(this.font, "generally stronger.", x, y + 108, 0x3c2d24, false);
			}
		}
	}

	private void drawRune(GuiGraphics graphics, int x, int y, String name, String effect) {
		graphics.drawString(this.font, name + " Rune", x, y, 0x3c2d24, false);
		graphics.drawString(this.font, effect, x, y + 22, 0x3c2d24, false);
		graphics.drawString(this.font, "Applies to supported tools,", x, y + 48, 0x3c2d24, false);
		graphics.drawString(this.font, "weapons, and armor.", x, y + 60, 0x3c2d24, false);
		graphics.drawString(this.font, "Gold = stronger effect", x, y + 88, 0x3c2d24, false);
		graphics.drawString(this.font, "Silver = lighter effect", x, y + 100, 0x3c2d24, false);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
}
