package net.mcreator.survivalreimagined.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.world.inventory.RMIMenu;

public class RMIScreen extends AbstractContainerScreen<RMIMenu> {
	private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/screens/rmi.png");
	private static final ResourceLocation RUNE_OUTLINE = ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/screens/rune_putline.png");
	private static final ResourceLocation LAPIS_OUTLINE = ResourceLocation.fromNamespaceAndPath("survival_reimagined", "textures/screens/lapis_outline.png");
	private static final ResourceLocation RMI_BUTTON = ResourceLocation.fromNamespaceAndPath("survival_reimagined", "rmi_button");
	private static final ResourceLocation RMI_HOVERED = ResourceLocation.fromNamespaceAndPath("survival_reimagined", "rmi_hovered");
	private static final ResourceLocation RMI_UNPRESSABLE = ResourceLocation.fromNamespaceAndPath("survival_reimagined", "rmi_unpressable");

	private ImageButton infuseButton;

	public RMIScreen(RMIMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	protected void init() {
		super.init();
		this.infuseButton = new ImageButton(
				this.leftPos + 72, this.topPos + 61, 32, 16,
				new WidgetSprites(RMI_BUTTON, RMI_HOVERED),
				button -> {
					if (this.minecraft != null && this.minecraft.gameMode != null && this.minecraft.player != null
							&& this.menu.canInfuse(this.minecraft.player)) {
						this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
					}
				});
		this.addRenderableWidget(this.infuseButton);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		if (this.infuseButton != null && this.minecraft != null && this.minecraft.player != null) {
			boolean canInfuse = this.menu.canInfuse(this.minecraft.player);
			this.infuseButton.active = canInfuse;
			this.infuseButton.visible = canInfuse;
		}
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		graphics.blit(RUNE_OUTLINE, this.leftPos + 80, this.topPos + 41, 0, 0, 16, 16, 16, 16);
		graphics.blit(LAPIS_OUTLINE, this.leftPos + 116, this.topPos + 61, 0, 0, 16, 16, 16, 16);
		graphics.blitSprite(RMI_UNPRESSABLE, this.leftPos + 72, this.topPos + 61, 32, 16);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(this.font,
				Component.translatable("gui.survival_reimagined.rmi.label_rune_magic_infuser"),
				39, 5, 0x3c3c3c, false);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	}
}
