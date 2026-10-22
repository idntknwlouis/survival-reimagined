package net.mcreator.survivalreimagined.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.world.inventory.MetalRefiningTableGUIMenu;

public class MetalRefiningTableGUIScreen extends AbstractContainerScreen<MetalRefiningTableGUIMenu> {
	private static final ResourceLocation BACKGROUND = texture("metal_refining_table_gui.png");
	private static final ResourceLocation PLUS = texture("plus.png");
	private static final ResourceLocation ARROW = texture("arrow.png");
	private static final ResourceLocation HAMMER_OUTLINE = texture("hammer_outline.png");

	public MetalRefiningTableGUIScreen(MetalRefiningTableGUIMenu menu, Inventory inventory, Component title) {
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
		graphics.blit(PLUS, this.leftPos + 62, this.topPos + 38, 0, 0, 16, 16, 16, 16);
		graphics.blit(ARROW, this.leftPos + 98, this.topPos + 37, 0, 0, 16, 16, 16, 16);
		graphics.blit(HAMMER_OUTLINE, this.leftPos + 152, this.topPos + 63, 0, 0, 16, 16, 16, 16);
		RenderSystem.disableBlend();
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(this.font, Component.translatable("gui.survival_reimagined.metal_refining_table_gui.label_metal_refining_table"), 37, 3, -12829636, false);
	}

	private static ResourceLocation texture(String name) {
		return ResourceLocation.parse("survival_reimagined:textures/screens/" + name);
	}
}
