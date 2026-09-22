package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.block.entity.CampfireBlockEntity;
import net.mcreator.survivalreimagined.world.inventory.CampfireGUIMenu;

public class CampfireGUIScreen extends AbstractContainerScreen<CampfireGUIMenu> {
	private static final ResourceLocation BACKGROUND =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/screens/campfire_gui.png");
	private static final ResourceLocation FIRE =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/screens/campfire_fire.png");

	public CampfireGUIScreen(CampfireGUIMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight,
				this.imageWidth, this.imageHeight);

		int fuel = Mth.clamp(this.menu.getFuelProgress(), 0, CampfireBlockEntity.MAX_FUEL);
		int frame = Mth.clamp(fuel / 40, 0, 13);
		graphics.blit(FIRE, this.leftPos + 80, this.topPos + 48,
				0, frame * 16, 16, 16, 16, 224);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		// The original GUI texture already contains its visual framing and labels.
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
}
