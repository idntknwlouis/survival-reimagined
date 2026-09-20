package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.world.inventory.AAFScriptureGUIMenu;

public class AAFScriptureGUIScreen extends AbstractContainerScreen<AAFScriptureGUIMenu> {
	private static final ResourceLocation COVER = tex("book_cover.png");
	private static final ResourceLocation PAGE = tex("book_page2.png");
	private int page = 0;

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
		this.addRenderableWidget(Button.builder(Component.literal("<"), b -> page = Math.max(0, page - 1))
				.bounds(this.leftPos - 51, this.topPos + 150, 20, 20).build());
		this.addRenderableWidget(Button.builder(Component.literal(">"), b -> page = Math.min(1, page + 1))
				.bounds(this.leftPos + 206, this.topPos + 150, 20, 20).build());
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(COVER, this.leftPos - 66, this.topPos - 12, 0, 0, 304, 192, 304, 192);
		graphics.blit(PAGE, this.leftPos - 66, this.topPos - 11, 0, 0, 304, 192, 304, 192);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		int left = -42;
		int top = 10;
		if (page == 0) {
			graphics.drawString(this.font, "Advanced Alloy Forge", left, top, 0x3c2d24, false);
			graphics.drawString(this.font, "Setup", left, top + 16, 0x3c2d24, false);
			graphics.drawString(this.font, "Build the forge into its", left, top + 30, 0x3c2d24, false);
			graphics.drawString(this.font, "titanium frame with four", left, top + 42, 0x3c2d24, false);
			graphics.drawString(this.font, "Uranium Rod supports.", left, top + 54, 0x3c2d24, false);
			graphics.drawString(this.font, "Reactor Rods supply fuel.", left, top + 78, 0x3c2d24, false);
			graphics.drawString(this.font, "Combine two alloy inputs", left, top + 90, 0x3c2d24, false);
			graphics.drawString(this.font, "to create advanced metals.", left, top + 102, 0x3c2d24, false);
		} else {
			graphics.drawString(this.font, "Forge Upgrades", left, top, 0x3c2d24, false);
			graphics.drawString(this.font, "Fuel: x2 / x3 capacity", left, top + 18, 0x3c2d24, false);
			graphics.drawString(this.font, "Efficiency: less fuel,", left, top + 32, 0x3c2d24, false);
			graphics.drawString(this.font, "faster processing", left, top + 44, 0x3c2d24, false);
			graphics.drawString(this.font, "Yield: x2 / x3 / x4", left, top + 62, 0x3c2d24, false);
			graphics.drawString(this.font, "Block Packaging: outputs", left, top + 80, 0x3c2d24, false);
			graphics.drawString(this.font, "full alloy blocks.", left, top + 92, 0x3c2d24, false);
			graphics.drawString(this.font, "Advanced Reactor Rods", left, top + 110, 0x3c2d24, false);
			graphics.drawString(this.font, "last twice as long.", left, top + 122, 0x3c2d24, false);
		}
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
}
