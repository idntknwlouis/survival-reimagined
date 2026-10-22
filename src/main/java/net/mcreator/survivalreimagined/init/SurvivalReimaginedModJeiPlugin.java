package net.mcreator.survivalreimagined.init;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import net.mcreator.survivalreimagined.jei_recipes.MineralProcessingJEIRecipe;
import net.mcreator.survivalreimagined.jei_recipes.MineralProcessingJEIRecipeCategory;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class SurvivalReimaginedModJeiPlugin implements IModPlugin {
	public static final mezz.jei.api.recipe.RecipeType<MineralProcessingJEIRecipe> MINERAL_PROCESSING =
			new mezz.jei.api.recipe.RecipeType<>(MineralProcessingJEIRecipeCategory.UID, MineralProcessingJEIRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return ResourceLocation.parse("survival_reimagined:jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new MineralProcessingJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
		List<MineralProcessingJEIRecipe> recipes = recipeManager
				.getAllRecipesFor(MineralProcessingJEIRecipe.Type.INSTANCE)
				.stream()
				.map(RecipeHolder::value)
				.toList();
		registration.addRecipes(MINERAL_PROCESSING, recipes);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(
				new ItemStack(SurvivalReimaginedModBlocks.MINERAL_PROCESSING_TABLE.get()),
				MINERAL_PROCESSING);
	}
}
