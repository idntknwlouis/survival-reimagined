package net.mcreator.survivalreimagined.jei_recipes;

import java.util.List;
import java.util.function.Function;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class MachineJeiRecipeCategory<T> implements IRecipeCategory<T> {
	private final mezz.jei.api.recipe.RecipeType<T> recipeType;
	private final Component title;
	private final IDrawable background;
	private final IDrawable icon;
	private final Function<T, List<Ingredient>> inputs;
	private final Function<T, List<ItemStack>> outputs;

	public MachineJeiRecipeCategory(mezz.jei.api.recipe.RecipeType<T> recipeType, Component title,
			ItemStack iconStack, IGuiHelper helper, Function<T, List<Ingredient>> inputs,
			Function<T, List<ItemStack>> outputs) {
		this.recipeType = recipeType;
		this.title = title;
		this.background = helper.createBlankDrawable(176, 90);
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, iconStack);
		this.inputs = inputs;
		this.outputs = outputs;
	}

	@Override
	public mezz.jei.api.recipe.RecipeType<T> getRecipeType() {
		return recipeType;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public int getWidth() {
		return background.getWidth();
	}

	@Override
	public int getHeight() {
		return background.getHeight();
	}

	@Override
	public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		background.draw(graphics);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
		List<Ingredient> ingredients = inputs.apply(recipe);
		for (int i = 0; i < ingredients.size(); i++) {
			int column = i % 6;
			int row = i / 6;
			builder.addSlot(RecipeIngredientRole.INPUT, 8 + column * 22, 20 + row * 22)
					.addIngredients(ingredients.get(i));
		}

		List<ItemStack> resultItems = outputs.apply(recipe);
		for (int i = 0; i < resultItems.size(); i++) {
			int column = i % 2;
			int row = i / 2;
			builder.addSlot(RecipeIngredientRole.OUTPUT, 132 + column * 22, 31 + row * 22)
					.addItemStack(resultItems.get(i));
		}
	}
}
