package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.world.inventory.RMIScriptureGUIMenu;

public class RMIScriptureGUIScreen extends AbstractContainerScreen<RMIScriptureGUIMenu> {
	private static final ResourceLocation COVER = tex("book_cover_rmi.png");
	private static final ResourceLocation PAGE = tex("book_page2.png");
	private int page = 0;

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
		this.addRenderableWidget(Button.builder(Component.literal("<"), b -> page = Math.max(0, page - 1))
				.bounds(this.leftPos - 51, this.topPos + 150, 20, 20).build());
		this.addRenderableWidget(Button.builder(Component.literal(">"), b -> page = Math.min(5, page + 1))
				.bounds(this.leftPos + 206, this.topPos + 150, 20, 20).build());
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(COVER, this.leftPos - 66, this.topPos - 12, 0, 0, 304, 192, 304, 192);
		graphics.blit(PAGE, this.leftPos - 66, this.topPos - 11, 0, 0, 304, 192, 304, 192);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		int x = -42;
		int y = 10;
		switch (page) {
			case 0 -> {
				graphics.drawString(this.font, "Rune Magic Infuser", x, y, 0x3c2d24, false);
				graphics.drawString(this.font, "Place an infusable item,", x, y + 20, 0x3c2d24, false);
				graphics.drawString(this.font, "a rune, and lapis into", x, y + 32, 0x3c2d24, false);
				graphics.drawString(this.font, "the infuser.", x, y + 44, 0x3c2d24, false);
				graphics.drawString(this.font, "Silver runes cost 2.", x, y + 68, 0x3c2d24, false);
				graphics.drawString(this.font, "Gold runes cost 3.", x, y + 80, 0x3c2d24, false);
			}
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
