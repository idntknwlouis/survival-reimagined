package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.world.inventory.AdvancedAlloyForgeGUIMenu;

import java.util.List;

public class AdvancedAlloyForgeGUIScreen extends AbstractContainerScreen<AdvancedAlloyForgeGUIMenu> {
	private static final ResourceLocation BACKGROUND = tex("advanced_alloy_forge_gui.png");
	private static final ResourceLocation UPGRADES = tex("alloy_forge_upgrades.png");
	private static final ResourceLocation ROD = tex("rod_texture.png");
	private static final ResourceLocation UPGRADE = tex("upgrade_texture.png");
	private static final ResourceLocation ARROW = tex("arrow.png");

	public AdvancedAlloyForgeGUIScreen(AdvancedAlloyForgeGUIMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static ResourceLocation tex(String file) {
		return ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/screens/" + file);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, 176, 166, 176, 166);
		graphics.blit(UPGRADES, this.leftPos + 178, this.topPos, 0, 0, 72, 75, 72, 75);

		int progress = Math.max(0, Math.min(AdvancedAlloyForgeBlockEntity.MAX_PROGRESS, this.menu.getProgress()));
		int arrowWidth = Math.max(1, Math.min(16, 1 + progress * 15 / AdvancedAlloyForgeBlockEntity.MAX_PROGRESS));
		graphics.blit(ARROW, this.leftPos + 111, this.topPos + 39, 0, 0, arrowWidth, 16, 16, 16);

		graphics.blit(ROD, this.leftPos + 8, this.topPos + 57, 0, 0, 16, 16, 16, 16);
		graphics.blit(UPGRADE, this.leftPos + 197, this.topPos + 19, 0, 0, 16, 16, 16, 16);
		graphics.blit(UPGRADE, this.leftPos + 215, this.topPos + 19, 0, 0, 16, 16, 16, 16);
		graphics.blit(UPGRADE, this.leftPos + 197, this.topPos + 37, 0, 0, 16, 16, 16, 16);
		graphics.blit(UPGRADE, this.leftPos + 215, this.topPos + 37, 0, 0, 16, 16, 16, 16);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(this.font, Component.translatable("gui.survival_reimagined.advanced_alloy_forge_gui.label_advanced_alloy_forge"), 52, 7, 0x3c3c3c, false);
		graphics.drawString(this.font, Component.translatable("gui.survival_reimagined.advanced_alloy_forge_gui.label_upgrades"), 190, 5, 0x3c3c3c, false);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		if (mouseX > this.leftPos + 8 && mouseX < this.leftPos + 30 && mouseY > this.topPos + 5 && mouseY < this.topPos + 54) {
			graphics.renderComponentTooltip(this.font,
					List.of(Component.literal("Fuel Capacity: " + this.menu.getFuelCapacity() + " / " + this.menu.getMaxFuelCapacity())),
					mouseX, mouseY);
		} else {
			this.renderTooltip(graphics, mouseX, mouseY);
		}
	}
}
