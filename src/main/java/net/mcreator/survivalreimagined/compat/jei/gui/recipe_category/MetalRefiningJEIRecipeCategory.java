package net.mcreator.survivalreimagined.compat.jei.gui.recipe_category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.mcreator.survivalreimagined.compat.jei.gui.recipe.MetalRefiningJEIRecipe;
import net.mcreator.survivalreimagined.compat.jei.plugin.JEIPlugin;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MetalRefiningJEIRecipeCategory implements IRecipeCategory<MetalRefiningJEIRecipe> {
    public final static ResourceLocation UID = ResourceLocation.parse("survival_reimagined:metal_refining");
    public final static ResourceLocation TEXTURE = ResourceLocation.parse("survival_reimagined:textures/screens/mrt_jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Minecraft mc = Minecraft.getInstance();

    public MetalRefiningJEIRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176,99);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(SurvivalReimaginedModBlocks.METAL_REFINING_TABLE.get().asItem()));
    }

    @Override
    public RecipeType<MetalRefiningJEIRecipe> getRecipeType() {
        return JEIPlugin.MetalRefiningJEI_Type;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Metal Refining");
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
    public void draw(MetalRefiningJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MetalRefiningJEIRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> recipeOutputs = recipe.getResultItems();

        builder.addSlot(RecipeIngredientRole.INPUT, 44, 38).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 38).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 152, 63).addIngredients(recipe.getIngredients().get(2));

        if (!recipeOutputs.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 38).addItemStack(recipeOutputs.get(0));
        }
    }
}
