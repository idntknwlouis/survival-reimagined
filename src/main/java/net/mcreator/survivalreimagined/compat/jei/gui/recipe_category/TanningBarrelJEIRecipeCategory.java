package net.mcreator.survivalreimagined.compat.jei.gui.recipe_category;

import mezz.jei.api.recipe.RecipeType;
import net.mcreator.survivalreimagined.compat.jei.gui.recipe.TanningBarrelJEIRecipe;
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

public class TanningBarrelJEIRecipeCategory implements IRecipeCategory<TanningBarrelJEIRecipe> {
    public final static ResourceLocation UID = ResourceLocation.parse("survival_reimagined:tanning_barrel_jei");
    public final static ResourceLocation TEXTURE = ResourceLocation.parse("survival_reimagined:textures/screens/tanning_barrel_jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Minecraft mc = Minecraft.getInstance();

    public TanningBarrelJEIRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 183, 194);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(SurvivalReimaginedModBlocks.TANNING_BARREL.get().asItem()));
    }

    @Override
    public RecipeType<TanningBarrelJEIRecipe> getRecipeType() {
        return JEIPlugin.TanningBarrelJEI_Type;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Tanning Barrel JEI");
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
    public void draw(TanningBarrelJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);

        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/water_barrel_3.png"), 45, 6, 0, 48 * ((mc.player.tickCount / 3) % 31) - 0, 16, 48, 16, 1536);

        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/lime_water_barrel.png"), 84, 6, 0, 48 * ((mc.player.tickCount / 3) % 31) - 0, 16, 48, 16, 1536);

        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/water_barrel_3.png"), 120, 6, 0, 48 * ((mc.player.tickCount / 3) % 31) - 0, 16, 48, 16, 1536);

        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/tannin_3.png"), 159, 6, 0, 48 * ((mc.player.tickCount / 3) % 31) - 0, 16, 48, 16, 1536);

        guiGraphics.drawString(mc.font, Component.translatable("gui.survival_reimagined.tanning_barrel_jei.label_recipes_must_match_ratios"), 5, 141, -12829636, false);
        guiGraphics.drawString(mc.font, Component.translatable("gui.survival_reimagined.tanning_barrel_jei.label_full_fluid_tank_needs_64_items"), 5, 152, -12829636, false);
        guiGraphics.drawString(mc.font, Component.translatable("gui.survival_reimagined.tanning_barrel_jei.label_half_fluid_tank_needs_32_items"), 5, 164, -12829636, false);
        guiGraphics.drawString(mc.font, Component.translatable("gui.survival_reimagined.tanning_barrel_jei.label_leather_recipes_is_6_hide_rat"), 5, 174, -12829636, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TanningBarrelJEIRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> recipeOutputs = recipe.getResultItems();
        List<ItemStack> actualOutputs = NonNullList.withSize(2, ItemStack.EMPTY);
        for (int i = 0; i < recipeOutputs.size(); i++) {
            actualOutputs.set(i, recipeOutputs.get(i));
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 6, 74).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 45, 74).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 84, 74).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 120, 74).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 159, 74).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 84, 114).addItemStack(actualOutputs.get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 159, 114).addItemStack(actualOutputs.get(1));
    }

}
