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
	private static final ResourceLocation EMPTY_FUEL = tex("aaf_empty.png");
	private static final ResourceLocation CAPACITY_MARKER = tex("capacity_marker.png");
	private static final ResourceLocation AAF_X = tex("aaf_x.png");
	private static final ResourceLocation[] FUEL = new ResourceLocation[12];
	private static final ResourceLocation[] ARROWS = new ResourceLocation[15];

	static {
		for (int i = 0; i < FUEL.length; i++) FUEL[i] = tex("aaf_" + (i + 1) + ".png");
		for (int i = 0; i < ARROWS.length; i++) ARROWS[i] = tex(i == 0 ? "arrow.png" : "arrow" + (i + 1) + ".png");
	}

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

		int fuel = Math.max(0, this.menu.getFuelCapacity());
		if (fuel <= 0) {
			graphics.blit(EMPTY_FUEL, this.leftPos + 8, this.topPos + 5, 0, 0, 14, 49, 14, 49);
		} else {
			int fuelFrame = Math.min(11, Math.max(0, (fuel - 1) / 750));
			graphics.blit(FUEL[fuelFrame], this.leftPos + 8, this.topPos + 5, 0, 0, 14, 49, 84, 49);
		}

		int maxFuel = this.menu.getMaxFuelCapacity();
		int markerY = maxFuel >= 9000 ? 3 : maxFuel >= 6000 ? 19 : 35;
		graphics.blit(CAPACITY_MARKER, this.leftPos + 23, this.topPos + markerY, 0, 0, 7, 5, 7, 5);

		if (this.menu.isRecipeInvalid()) {
			graphics.blit(AAF_X, this.leftPos + 114, this.topPos + 43, 0, 0, 8, 8, 8, 8);
		}

		int progress = Math.max(0, Math.min(AdvancedAlloyForgeBlockEntity.MAX_PROGRESS, this.menu.getProgress()));
		int frame = Math.min(14, progress / 20);
		graphics.blit(ARROWS[frame], this.leftPos + 111, this.topPos + 39, 0, 0, 16, 16, 16, 16);

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
