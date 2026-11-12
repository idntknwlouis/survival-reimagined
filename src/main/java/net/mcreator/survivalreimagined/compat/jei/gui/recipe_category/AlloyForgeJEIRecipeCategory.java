package net.mcreator.survivalreimagined.compat.jei.gui.recipe_category;

import mezz.jei.api.recipe.RecipeType;
import net.mcreator.survivalreimagined.compat.jei.gui.recipe.AlloyForgeJEIRecipe;
import net.mcreator.survivalreimagined.compat.jei.plugin.JEIPlugin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;


import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;

import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.constants.VanillaTypes;

import java.util.List;
import java.util.ArrayList;

public class AlloyForgeJEIRecipeCategory implements IRecipeCategory<AlloyForgeJEIRecipe> {
    public final static ResourceLocation UID = ResourceLocation.parse("survival_reimagined:alloy_forge");
    public final static ResourceLocation TEXTURE = ResourceLocation.parse("survival_reimagined:textures/screens/aaf_gui.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Minecraft mc = Minecraft.getInstance();

    public AlloyForgeJEIRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 255, 86);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(SurvivalReimaginedModBlocks.ADVANCED_ALLOY_FORGE.get().asItem()));
    }

    @Override
    public RecipeType<AlloyForgeJEIRecipe> getRecipeType() {
        return JEIPlugin.AlloyForgeJEI_Type;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Alloy Forge");
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
    public void draw(AlloyForgeJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);

        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/arrow2-sheet.png"), 112, 37, 0, 16 * ((mc.player.tickCount / 10) % 15) - 1, 16, 16, 16, 240);

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyForgeJEIRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> stacks = new ArrayList<>();
        List<ItemStack> recipeOutputs = recipe.getResultItems();
        List<ItemStack> actualOutputs = NonNullList.withSize(1, ItemStack.EMPTY);
        for (int i = 0; i < recipeOutputs.size(); i++) {
            actualOutputs.set(i, recipeOutputs.get(i));
        }
        stacks.clear();
        for (ItemStack item : List.of(recipe.getIngredients().get(0).getItems()))
            stacks.add(new ItemStack(item.getItem(), recipe.integers().get(0)));
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 57).addItemStacks(stacks);
        stacks.clear();
        for (ItemStack item : List.of(recipe.getIngredients().get(1).getItems()))
            stacks.add(new ItemStack(item.getItem(), recipe.integers().get(1)));
        builder.addSlot(RecipeIngredientRole.INPUT, 71, 39).addItemStacks(stacks);
        stacks.clear();
        for (ItemStack item : List.of(recipe.getIngredients().get(2).getItems()))
            stacks.add(new ItemStack(item.getItem(), recipe.integers().get(2)));
        builder.addSlot(RecipeIngredientRole.INPUT, 89, 39).addItemStacks(stacks);
        stacks.clear();
        for (ItemStack item : List.of(recipe.getIngredients().get(3).getItems()))
            stacks.add(new ItemStack(item.getItem(), recipe.integers().get(3)));
        builder.addSlot(RecipeIngredientRole.INPUT, 199, 21).addItemStacks(stacks);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 39).addItemStack(actualOutputs.get(0));
    }

}
