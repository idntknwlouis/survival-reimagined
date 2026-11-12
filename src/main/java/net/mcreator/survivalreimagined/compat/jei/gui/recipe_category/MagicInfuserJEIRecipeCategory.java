package net.mcreator.survivalreimagined.compat.jei.gui.recipe_category;

import net.mcreator.survivalreimagined.compat.jei.gui.recipe.MagicInfuserJEIRecipe;
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

public class MagicInfuserJEIRecipeCategory implements IRecipeCategory<MagicInfuserJEIRecipe> {
    public final static ResourceLocation UID = ResourceLocation.parse("survival_reimagined:magic_infuser");
    public final static ResourceLocation TEXTURE = ResourceLocation.parse("survival_reimagined:textures/screens/rmi_jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Minecraft mc = Minecraft.getInstance();

    public MagicInfuserJEIRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 175, 89);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(SurvivalReimaginedModBlocks.RUNE_MAGIC_INFUSER.get().asItem()));
    }

    @Override
    public mezz.jei.api.recipe.RecipeType<MagicInfuserJEIRecipe> getRecipeType() {
        return JEIPlugin.MagicInfuserJEI_Type;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Magic Infuser");
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
    public void draw(MagicInfuserJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);

        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/rmi_button.png"), 72, 61, 32 * ((mc.player.tickCount / 10) % 1) - 0, 0, 32, 16, 32, 16);

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MagicInfuserJEIRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> recipeOutputs = recipe.getResultItems();
        List<ItemStack> actualOutputs = NonNullList.withSize(1, ItemStack.EMPTY);
        for (int i = 0; i < recipeOutputs.size(); i++) {
            actualOutputs.set(i, recipeOutputs.get(i));
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 21).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 41).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 61).addItemStack(actualOutputs.get(0));
    }

}
