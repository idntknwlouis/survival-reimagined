package net.mcreator.survivalreimagined.init;

import java.util.List;
import java.util.Objects;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.mcreator.survivalreimagined.jei_recipes.ForgeJEIRecipe;
import net.mcreator.survivalreimagined.jei_recipes.MachineJeiRecipeCategory;
import net.mcreator.survivalreimagined.jei_recipes.MetalRefiningRecipe;
import net.mcreator.survivalreimagined.jei_recipes.MillstoneJEIRecipe;
import net.mcreator.survivalreimagined.jei_recipes.MineralProcessingJEIRecipe;
import net.mcreator.survivalreimagined.jei_recipes.MineralProcessingJEIRecipeCategory;

@JeiPlugin
public class SurvivalReimaginedModJeiPlugin implements IModPlugin {
	public static final mezz.jei.api.recipe.RecipeType<ForgeJEIRecipe> FORGE =
			new mezz.jei.api.recipe.RecipeType<>(ResourceLocation.parse("survival_reimagined:forge_jei"), ForgeJEIRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<MetalRefiningRecipe> METAL_REFINING =
			new mezz.jei.api.recipe.RecipeType<>(ResourceLocation.parse("survival_reimagined:metal_refining"), MetalRefiningRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<MillstoneJEIRecipe> MILLSTONE =
			new mezz.jei.api.recipe.RecipeType<>(ResourceLocation.parse("survival_reimagined:millstone_jei"), MillstoneJEIRecipe.class);
	public static final mezz.jei.api.recipe.RecipeType<MineralProcessingJEIRecipe> MINERAL_PROCESSING =
			new mezz.jei.api.recipe.RecipeType<>(MineralProcessingJEIRecipeCategory.UID, MineralProcessingJEIRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return ResourceLocation.parse("survival_reimagined:jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		var helper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(
				new MachineJeiRecipeCategory<>(FORGE, Component.literal("Forge"),
						new ItemStack(SurvivalReimaginedModBlocks.FORGE.get()), helper,
						ForgeJEIRecipe::getIngredients, ForgeJEIRecipe::getResultItems),
				new MachineJeiRecipeCategory<>(METAL_REFINING, Component.literal("Metal Refining"),
						new ItemStack(SurvivalReimaginedModBlocks.METAL_REFINING_TABLE.get()), helper,
						MetalRefiningRecipe::getIngredients, MetalRefiningRecipe::getResultItems),
				new MachineJeiRecipeCategory<>(MILLSTONE, Component.literal("Millstone"),
						new ItemStack(SurvivalReimaginedModBlocks.MILLSTONE.get()), helper,
						MillstoneJEIRecipe::getIngredients, MillstoneJEIRecipe::getResultItems),
				new MineralProcessingJEIRecipeCategory(helper));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
		registration.addRecipes(FORGE, manager.getAllRecipesFor(SurvivalReimaginedModRecipeTypes.FORGE_JEI)
				.stream().map(RecipeHolder::value).toList());
		registration.addRecipes(METAL_REFINING, manager.getAllRecipesFor(SurvivalReimaginedModRecipeTypes.METAL_REFINING)
				.stream().map(RecipeHolder::value).toList());
		registration.addRecipes(MILLSTONE, manager.getAllRecipesFor(SurvivalReimaginedModRecipeTypes.MILLSTONE_JEI)
				.stream().map(RecipeHolder::value).toList());
		registration.addRecipes(MINERAL_PROCESSING, manager.getAllRecipesFor(SurvivalReimaginedModRecipeTypes.MINERAL_PROCESSING_JEI)
				.stream().map(RecipeHolder::value).toList());
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.FORGE.get()), FORGE);
		registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.METAL_REFINING_TABLE.get()), METAL_REFINING);
		registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.MILLSTONE.get()), MILLSTONE);
		registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.MINERAL_PROCESSING_TABLE.get()), MINERAL_PROCESSING);
	}
}
