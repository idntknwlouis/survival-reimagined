package net.mcreator.survivalreimagined.compat.jei.plugin;


import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;

import net.mcreator.survivalreimagined.compat.jei.gui.recipe.*;
import net.mcreator.survivalreimagined.compat.jei.gui.recipe_category.*;

import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.RecipeType;

import java.util.stream.Collectors;
import java.util.Objects;
import java.util.List;


@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static RecipeType<ForgeJEIRecipe> ForgeJEI_Type = new RecipeType<>(ForgeJEIRecipeCategory.UID, ForgeJEIRecipe.class);
    public static RecipeType<MillstoneJEIRecipe> MillstoneJEI_Type = new RecipeType<>(MillstoneJEIRecipeCategory.UID, MillstoneJEIRecipe.class);
    public static RecipeType<MetalRefiningJEIRecipe> MetalRefiningJEI_Type = new RecipeType<>(MetalRefiningJEIRecipeCategory.UID, MetalRefiningJEIRecipe.class);
    public static RecipeType<MineralProcessingJEIRecipe> MineralProcessingJEI_Type = new RecipeType<>(MineralProcessingJEIRecipeCategory.UID, MineralProcessingJEIRecipe.class);
    public static RecipeType<MagicInfuserJEIRecipe> MagicInfuserJEI_Type = new RecipeType<>(MagicInfuserJEIRecipeCategory.UID, MagicInfuserJEIRecipe.class);
    public static RecipeType<AlloyForgeJEIRecipe> AlloyForgeJEI_Type = new RecipeType<>(AlloyForgeJEIRecipeCategory.UID, AlloyForgeJEIRecipe.class);
    public static RecipeType<CampfireJEIRecipe> CampfireJEI_Type = new RecipeType<>(CampfireJEIRecipeCategory.UID, CampfireJEIRecipe.class);
    public static RecipeType<TanningBarrelJEIRecipe> TanningBarrelJEI_Type = new RecipeType<>(TanningBarrelJEIRecipeCategory.UID, TanningBarrelJEIRecipe.class);



    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.parse("survival_reimagined:jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ForgeJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MillstoneJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MetalRefiningJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MineralProcessingJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MagicInfuserJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AlloyForgeJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CampfireJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new TanningBarrelJEIRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<ForgeJEIRecipe> ForgeJEIRecipes = recipeManager.getAllRecipesFor(ForgeJEIRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
        List<MillstoneJEIRecipe> MillstoneJEIRecipes = recipeManager.getAllRecipesFor(MillstoneJEIRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
        List<MetalRefiningJEIRecipe> MetalRefiningJEIRecipes = recipeManager.getAllRecipesFor(MetalRefiningJEIRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
        List<MineralProcessingJEIRecipe> MineralProcessingJEIRecipes = recipeManager.getAllRecipesFor(MineralProcessingJEIRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
        List<MagicInfuserJEIRecipe> MagicInfuserJEIRecipes = recipeManager.getAllRecipesFor(MagicInfuserJEIRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
        List<AlloyForgeJEIRecipe> AlloyForgeJEIRecipes = recipeManager.getAllRecipesFor(AlloyForgeJEIRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
        List<CampfireJEIRecipe> CampfireJEIRecipes = recipeManager.getAllRecipesFor(CampfireJEIRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());
        List<TanningBarrelJEIRecipe> TanningBarrelJEIRecipes = recipeManager.getAllRecipesFor(TanningBarrelJEIRecipe.Type.INSTANCE).stream().map(RecipeHolder::value).collect(Collectors.toList());


        registration.addRecipes(ForgeJEI_Type, ForgeJEIRecipes);
        registration.addRecipes(MillstoneJEI_Type, MillstoneJEIRecipes);
        registration.addRecipes(MetalRefiningJEI_Type, MetalRefiningJEIRecipes);
        registration.addRecipes(MineralProcessingJEI_Type, MineralProcessingJEIRecipes);
        registration.addRecipes(MagicInfuserJEI_Type, MagicInfuserJEIRecipes);
        registration.addRecipes(AlloyForgeJEI_Type, AlloyForgeJEIRecipes);
        registration.addRecipes(CampfireJEI_Type, CampfireJEIRecipes);
        registration.addRecipes(TanningBarrelJEI_Type, TanningBarrelJEIRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.FORGE.get().asItem()), ForgeJEI_Type);
        registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.MILLSTONE.get().asItem()), MillstoneJEI_Type);
        registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.METAL_REFINING_TABLE.get().asItem()), MetalRefiningJEI_Type);
        registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.MINERAL_PROCESSING_TABLE.get().asItem()), MineralProcessingJEI_Type);
        registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.RUNE_MAGIC_INFUSER.get().asItem()), MagicInfuserJEI_Type);
        registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.ADVANCED_ALLOY_FORGE.get().asItem()), AlloyForgeJEI_Type);
        registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.CAMPFIRE.get().asItem()), CampfireJEI_Type);
        registration.addRecipeCatalyst(new ItemStack(SurvivalReimaginedModBlocks.TANNING_BARREL.get().asItem()), TanningBarrelJEI_Type);
    }
}
