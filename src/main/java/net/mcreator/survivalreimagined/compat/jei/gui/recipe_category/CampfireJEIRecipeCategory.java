package net.mcreator.survivalreimagined.compat.jei.gui.recipe_category;

import mezz.jei.api.recipe.RecipeType;
import net.mcreator.survivalreimagined.compat.jei.gui.recipe.CampfireJEIRecipe;
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

public class CampfireJEIRecipeCategory implements IRecipeCategory<CampfireJEIRecipe> {
    public final static ResourceLocation UID = ResourceLocation.parse("survival_reimagined:campfire_jei");
    public final static ResourceLocation TEXTURE = ResourceLocation.parse("survival_reimagined:textures/screens/campfire_jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Minecraft mc = Minecraft.getInstance();

    public CampfireJEIRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 175, 89);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(SurvivalReimaginedModBlocks.CAMPFIRE.get().asItem()));
    }

    @Override
    public RecipeType<CampfireJEIRecipe> getRecipeType() {
        return JEIPlugin.CampfireJEI_Type;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Campfire");
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
    public void draw(CampfireJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);

        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/campfire_fire.png"), 79, 45, 0, 16 * ((mc.player.tickCount / 10) % 14) - 1, 16, 16, 16, 224);

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CampfireJEIRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> recipeOutputs = recipe.getResultItems();
        List<ItemStack> actualOutputs = NonNullList.withSize(1, ItemStack.EMPTY);
        for (int i = 0; i < recipeOutputs.size(); i++) {
            actualOutputs.set(i, recipeOutputs.get(i));
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 71, 11).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 89, 11).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 71, 29).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 89, 29).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 66).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 10000, 0).addItemStack(actualOutputs.get(0));
    }

}
