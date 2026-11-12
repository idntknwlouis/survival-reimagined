package net.mcreator.survivalreimagined.compat.jei.plugin;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.compat.jei.gui.recipe.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypes {
    public static final String MODID = SurvivalReimaginedMod.MODID;

    public static void register() {
        registerRecipe("forge_jei", ForgeJEIRecipe.Type.INSTANCE, ForgeJEIRecipe.Serializer.INSTANCE);
        registerRecipe("millstone_jei", MillstoneJEIRecipe.Type.INSTANCE, MillstoneJEIRecipe.Serializer.INSTANCE);
        registerRecipe("metal_refining", MetalRefiningJEIRecipe.Type.INSTANCE, MetalRefiningJEIRecipe.Serializer.INSTANCE);
        registerRecipe("mineral_processing_jei", MineralProcessingJEIRecipe.Type.INSTANCE, MineralProcessingJEIRecipe.Serializer.INSTANCE);
        registerRecipe("magic_infuser", MagicInfuserJEIRecipe.Type.INSTANCE, MagicInfuserJEIRecipe.Serializer.INSTANCE);
        registerRecipe("alloy_forge", AlloyForgeJEIRecipe.Type.INSTANCE, AlloyForgeJEIRecipe.Serializer.INSTANCE);
        registerRecipe("campfire_jei", CampfireJEIRecipe.Type.INSTANCE, CampfireJEIRecipe.Serializer.INSTANCE);
        registerRecipe("tanning_barrel_jei", TanningBarrelJEIRecipe.Type.INSTANCE, TanningBarrelJEIRecipe.Serializer.INSTANCE);
    }

    private static <T extends Recipe<?>> void registerRecipe(String name, RecipeType<T> type, RecipeSerializer<T> serializer) {
        ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID, name);

        Registry.register(BuiltInRegistries.RECIPE_TYPE, ID, type);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ID, serializer);
    }
}
