package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.jei_recipes.ForgeJEIRecipe;
import net.mcreator.survivalreimagined.jei_recipes.MetalRefiningRecipe;
import net.mcreator.survivalreimagined.jei_recipes.MillstoneJEIRecipe;
import net.mcreator.survivalreimagined.jei_recipes.MineralProcessingJEIRecipe;

public final class SurvivalReimaginedModRecipeTypes {
	public static final RecipeType<MillstoneJEIRecipe> MILLSTONE_JEI =
			registerType("millstone_jei", MillstoneJEIRecipe.Type.INSTANCE);
	public static final RecipeSerializer<MillstoneJEIRecipe> MILLSTONE_JEI_SERIALIZER =
			registerSerializer("millstone_jei", MillstoneJEIRecipe.Serializer.INSTANCE);

	public static final RecipeType<ForgeJEIRecipe> FORGE_JEI =
			registerType("forge_jei", ForgeJEIRecipe.Type.INSTANCE);
	public static final RecipeSerializer<ForgeJEIRecipe> FORGE_JEI_SERIALIZER =
			registerSerializer("forge_jei", ForgeJEIRecipe.Serializer.INSTANCE);

	public static final RecipeType<MetalRefiningRecipe> METAL_REFINING =
			registerType("metal_refining", MetalRefiningRecipe.Type.INSTANCE);
	public static final RecipeSerializer<MetalRefiningRecipe> METAL_REFINING_SERIALIZER =
			registerSerializer("metal_refining", MetalRefiningRecipe.Serializer.INSTANCE);

	public static final RecipeType<MineralProcessingJEIRecipe> MINERAL_PROCESSING_JEI =
			registerType("mineral_processing_jei", MineralProcessingJEIRecipe.Type.INSTANCE);
	public static final RecipeSerializer<MineralProcessingJEIRecipe> MINERAL_PROCESSING_JEI_SERIALIZER =
			registerSerializer("mineral_processing_jei", MineralProcessingJEIRecipe.Serializer.INSTANCE);

	private SurvivalReimaginedModRecipeTypes() {
	}

	private static <T extends RecipeType<?>> T registerType(String path, T type) {
		return Registry.register(BuiltInRegistries.RECIPE_TYPE, SurvivalReimaginedMod.asResource(path), type);
	}

	private static <T extends RecipeSerializer<?>> T registerSerializer(String path, T serializer) {
		return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, SurvivalReimaginedMod.asResource(path), serializer);
	}

	public static void register() {
		// Forces class initialization.
	}
}
