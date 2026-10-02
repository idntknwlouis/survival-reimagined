package net.mcreator.survivalreimagined.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import net.mcreator.survivalreimagined.world.inventory.ForgeGUIMenu;

public class ForgeGUIScreen extends AbstractContainerScreen<ForgeGUIMenu> {
	private static final ResourceLocation BACKGROUND = screenTexture("forge_gui.png");
	private static final ResourceLocation[] FUEL_METER_FRAMES = createFuelMeterFrames();
	private static final ResourceLocation[] ARROW_FRAMES = createArrowFrames();

	public ForgeGUIScreen(ForgeGUIMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		// Match the original NeoForge GUI: 15 discrete fuel-meter frames,
		// with each frame representing a 40-point band of the 0..600 meter.
		int fuel = Mth.clamp(this.menu.getFuelMeter(), 0, ForgeBlockEntity.MAX_FUEL);
		int fuelFrame = Mth.clamp(15 - Mth.ceil(fuel / 40.0F), 0, 14);
		graphics.blit(FUEL_METER_FRAMES[fuelFrame], this.leftPos + 12, this.topPos + 50, 0, 0, 8, 29, 8, 29);

		// The original Forge uses 15 arrow frames over a 0..60 BurnTime range.
		int burnTime = Mth.clamp(this.menu.getBurnTime(), 0, ForgeBlockEntity.MAX_BURN_TIME);
		int arrowFrame = Mth.clamp(burnTime / 4, 0, 14);
		graphics.blit(ARROW_FRAMES[arrowFrame], this.leftPos + 112, this.topPos + 35, 0, 0, 16, 16, 16, 16);

		RenderSystem.disableBlend();
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(this.font, Component.translatable("gui.survival_reimagined.forge_gui.label_forge"), 75, 6, -12829636, false);
	}

	private static ResourceLocation screenTexture(String name) {
		return ResourceLocation.parse("survival_reimagined:textures/screens/" + name);
	}

	private static ResourceLocation[] createFuelMeterFrames() {
		ResourceLocation[] frames = new ResourceLocation[15];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = screenTexture("fuel_meter_" + i + ".png");
		}
		return frames;
	}

	private static ResourceLocation[] createArrowFrames() {
		ResourceLocation[] frames = new ResourceLocation[15];
		frames[0] = screenTexture("arrow.png");
		for (int i = 1; i < frames.length; i++) {
			frames[i] = screenTexture("arrow" + (i + 1) + ".png");
		}
		return frames;
	}
}
