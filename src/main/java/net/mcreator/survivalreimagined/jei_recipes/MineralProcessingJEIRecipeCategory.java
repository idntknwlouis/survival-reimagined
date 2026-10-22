package net.mcreator.survivalreimagined.jei_recipes;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModJeiPlugin;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;

public class MineralProcessingJEIRecipeCategory implements IRecipeCategory<MineralProcessingJEIRecipe> {
	public static final ResourceLocation UID = ResourceLocation.parse("survival_reimagined:mineral_processing_jei");
	private static final ResourceLocation TEXTURE = ResourceLocation.parse("survival_reimagined:textures/screens/mptgui.png");

	private final IDrawable background;
	private final IDrawable icon;

	public MineralProcessingJEIRecipeCategory(IGuiHelper helper) {
		this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 90);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(SurvivalReimaginedModBlocks.MINERAL_PROCESSING_TABLE.get()));
	}

	@Override
	public mezz.jei.api.recipe.RecipeType<MineralProcessingJEIRecipe> getRecipeType() {
		return SurvivalReimaginedModJeiPlugin.MINERAL_PROCESSING;
	}

	@Override
	public Component getTitle() {
		return Component.literal("Mineral Processing");
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public int getWidth() {
		return this.background.getWidth();
	}

	@Override
	public int getHeight() {
		return this.background.getHeight();
	}

	@Override
	public void draw(MineralProcessingJEIRecipe recipe, IRecipeSlotsView recipeSlotsView,
			GuiGraphics graphics, double mouseX, double mouseY) {
		this.background.draw(graphics);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, MineralProcessingJEIRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 62, 35).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.INPUT, 80, 62).addIngredients(recipe.getIngredients().get(1));
		if (!recipe.getResultItems().isEmpty()) {
			builder.addSlot(RecipeIngredientRole.OUTPUT, 98, 35).addItemStack(recipe.getResultItems().get(0));
		}
	}
}
