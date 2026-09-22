package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.block.entity.CampfireBlockEntity;
import net.mcreator.survivalreimagined.world.inventory.CampfireGUIMenu;

public class CampfireGUIScreen extends AbstractContainerScreen<CampfireGUIMenu> {
	public CampfireGUIScreen(CampfireGUIMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		int x = this.leftPos;
		int y = this.topPos;

		graphics.fill(x, y, x + 176, y + 166, 0xFFC6C6C6);
		graphics.fill(x + 4, y + 4, x + 172, y + 162, 0xFF8B8B8B);
		graphics.fill(x + 5, y + 5, x + 171, y + 161, 0xFFC6C6C6);

		slotBox(graphics, x + 79, y + 65);
		slotBox(graphics, x + 70, y + 28);
		slotBox(graphics, x + 88, y + 28);
		slotBox(graphics, x + 70, y + 10);
		slotBox(graphics, x + 88, y + 10);

		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 9; col++)
				slotBox(graphics, x + 7 + col * 18, y + 83 + row * 18);
		for (int col = 0; col < 9; col++)
			slotBox(graphics, x + 7 + col * 18, y + 141);

		int fuel = Math.max(0, Math.min(CampfireBlockEntity.MAX_FUEL, menu.getFuelProgress()));
		int frame = Math.min(13, fuel / 40);
		int flameHeight = 4 + frame;
		graphics.fill(x + 82, y + 60 - flameHeight, x + 94, y + 60, 0xFFFF6A00);
		graphics.fill(x + 85, y + 60 - Math.max(2, flameHeight - 4), x + 91, y + 60, 0xFFFFFF55);
	}

	private static void slotBox(GuiGraphics graphics, int x, int y) {
		graphics.fill(x, y, x + 18, y + 18, 0xFF373737);
		graphics.fill(x + 1, y + 1, x + 17, y + 17, 0xFF8B8B8B);
		graphics.fill(x + 2, y + 2, x + 16, y + 16, 0xFF555555);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(this.font, Component.translatable("block.survival_reimagined.campfire"), 8, 72, 0x404040, false);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
}
