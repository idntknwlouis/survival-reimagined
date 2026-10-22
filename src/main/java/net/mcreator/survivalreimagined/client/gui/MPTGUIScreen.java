package net.mcreator.survivalreimagined.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import net.mcreator.survivalreimagined.block.entity.MineralProcessingTableBlockEntity;
import net.mcreator.survivalreimagined.world.inventory.MPTGUIMenu;

import java.util.List;

public class MPTGUIScreen extends AbstractContainerScreen<MPTGUIMenu> {
	private static final ResourceLocation BACKGROUND = texture("mptgui.png");
	private static final ResourceLocation ROD_OUTLINE = texture("item_thingy.png");
	private static final ResourceLocation REDSTONE = texture("redstome.png");
	private static final ResourceLocation[] PROGRESS = new ResourceLocation[15];

	static {
		PROGRESS[0] = texture("arrow.png");
		for (int i = 1; i < PROGRESS.length; i++) PROGRESS[i] = texture("arrow" + (i + 1) + ".png");
	}

	public MPTGUIScreen(MPTGUIMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.render(graphics, mouseX, mouseY, partialTick);
		if (this.menu.getRodCapacity() > 0
				&& mouseX > this.leftPos + 76 && mouseX < this.leftPos + 100
				&& mouseY > this.topPos + 31 && mouseY < this.topPos + 55) {
			graphics.renderComponentTooltip(this.font, List.of(Component.literal("Reactor Rod Capacity: " + this.menu.getRodCapacity())), mouseX, mouseY);
		} else if (mouseX > this.leftPos + 4 && mouseX < this.leftPos + 28 && mouseY > this.topPos + 59 && mouseY < this.topPos + 83) {
			graphics.renderComponentTooltip(this.font, List.of(Component.literal(this.menu.isPowered() ? "§4Redstone Powered" : "§4Redstone Unpowered")), mouseX, mouseY);
		} else {
			this.renderTooltip(graphics, mouseX, mouseY);
		}
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		int progress = Math.max(0, Math.min(MineralProcessingTableBlockEntity.MAX_PROGRESS, this.menu.getProgress()));
		int frame = Math.min(PROGRESS.length - 1, progress / 20);
		graphics.blit(PROGRESS[frame], this.leftPos + 80, this.topPos + 35, 0, 0, 16, 16, 16, 16);
		graphics.blit(ROD_OUTLINE, this.leftPos + 80, this.topPos + 62, 0, 0, 16, 16, 16, 16);
		graphics.blit(REDSTONE, this.leftPos + 8, this.topPos + 63, 0, 0, 16, 16, 16, 16);
		RenderSystem.disableBlend();
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(this.font, Component.translatable("gui.survival_reimagined.mptgui.label_mineral_processing_table"), 24, 7, -12829636, false);
	}

	private static ResourceLocation texture(String name) {
		return ResourceLocation.parse("survival_reimagined:textures/screens/" + name);
	}
}
