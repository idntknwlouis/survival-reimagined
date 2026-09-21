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
	private static final ResourceLocation AAF_EMPTY = tex("aaf_empty.png");
	private static final ResourceLocation AAF_1 = tex("aaf_1.png");
	private static final ResourceLocation AAF_2 = tex("aaf_2.png");
	private static final ResourceLocation AAF_3 = tex("aaf_3.png");
	private static final ResourceLocation AAF_4 = tex("aaf_4.png");
	private static final ResourceLocation AAF_5 = tex("aaf_5.png");
	private static final ResourceLocation AAF_6 = tex("aaf_6.png");
	private static final ResourceLocation AAF_7 = tex("aaf_7.png");
	private static final ResourceLocation AAF_8 = tex("aaf_8.png");
	private static final ResourceLocation AAF_9 = tex("aaf_9.png");
	private static final ResourceLocation AAF_10 = tex("aaf_10.png");
	private static final ResourceLocation AAF_11 = tex("aaf_11.png");
	private static final ResourceLocation AAF_12 = tex("aaf_12.png");
	private static final ResourceLocation CAPACITY_MARKER = tex("capacity_marker.png");
	private static final ResourceLocation AAF_X = tex("aaf_x.png");

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
		int arrowFrame = Math.max(0, Math.min(15, (int) Math.ceil(progress * 15.0D / AdvancedAlloyForgeBlockEntity.MAX_PROGRESS)));
		if (arrowFrame > 0) {
			ResourceLocation arrow = arrowFrame == 1 ? ARROW : tex("arrow" + arrowFrame + ".png");
			graphics.blit(arrow, this.leftPos + 111, this.topPos + 39, 0, 0, 16, 16, 16, 16);
		}

		int fuel = Math.max(0, this.menu.getFuelCapacity());
		ResourceLocation fuelGauge = switch (Math.max(0, Math.min(12, (int) Math.ceil(fuel / 750.0D)))) {
			case 1 -> AAF_1;
			case 2 -> AAF_2;
			case 3 -> AAF_3;
			case 4 -> AAF_4;
			case 5 -> AAF_5;
			case 6 -> AAF_6;
			case 7 -> AAF_7;
			case 8 -> AAF_8;
			case 9 -> AAF_9;
			case 10 -> AAF_10;
			case 11 -> AAF_11;
			case 12 -> AAF_12;
			default -> AAF_EMPTY;
		};
		graphics.blit(fuelGauge, this.leftPos + 8, this.topPos + 5, 0, 0, 14, 49, fuel == 0 ? 14 : 84, 49);

		graphics.blit(ROD, this.leftPos + 8, this.topPos + 57, 0, 0, 16, 16, 16, 16);
		graphics.blit(UPGRADE, this.leftPos + 197, this.topPos + 19, 0, 0, 16, 16, 16, 16);
		graphics.blit(UPGRADE, this.leftPos + 215, this.topPos + 19, 0, 0, 16, 16, 16, 16);
		graphics.blit(UPGRADE, this.leftPos + 197, this.topPos + 37, 0, 0, 16, 16, 16, 16);
		graphics.blit(UPGRADE, this.leftPos + 215, this.topPos + 37, 0, 0, 16, 16, 16, 16);

		int maxFuel = this.menu.getMaxFuelCapacity();
		int markerY = maxFuel >= 9000 ? 3 : maxFuel >= 6000 ? 19 : 35;
		graphics.blit(CAPACITY_MARKER, this.leftPos + 23, this.topPos + markerY, 0, 0, 7, 5, 7, 5);

		if (this.menu.getProgress() < 0) {
			graphics.blit(AAF_X, this.leftPos + 114, this.topPos + 43, 0, 0, 8, 8, 8, 8);
		}
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
