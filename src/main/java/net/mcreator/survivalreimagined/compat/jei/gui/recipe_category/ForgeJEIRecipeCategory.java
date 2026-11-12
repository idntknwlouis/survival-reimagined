package net.mcreator.survivalreimagined.compat.jei.gui.recipe_category;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.mcreator.survivalreimagined.compat.jei.plugin.*;
import net.mcreator.survivalreimagined.compat.jei.gui.recipe.*;

import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class ForgeJEIRecipeCategory implements IRecipeCategory<ForgeJEIRecipe> {
    public final static ResourceLocation UID = ResourceLocation.parse("survival_reimagined:forge_jei");
    public final static ResourceLocation TEXTURE = ResourceLocation.parse("survival_reimagined:textures/screens/forge_jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final Minecraft mc = Minecraft.getInstance();


    public ForgeJEIRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 87);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(SurvivalReimaginedModBlocks.FORGE.get().asItem()));
    }


    @Override
    public RecipeType<ForgeJEIRecipe> getRecipeType() {
        return JEIPlugin.ForgeJEI_Type;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Forge");
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public int getWidth() {
        return this.background.getWidth();
    }

    public int getHeight() {
        return this.background.getHeight();
    }

    @Override
    public void draw(ForgeJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);

        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/fuel_meter-sheet-export.png"), 12, 48, 0, 29 * ((mc.player.tickCount / 5) % 14) - 0, 8, 29, 8, 435);
        guiGraphics.blit(ResourceLocation.parse("survival_reimagined:textures/screens/arrow2-sheet.png"), 112, 34, 0, 16 * ((mc.player.tickCount / 10) % 15) - 1, 16, 16, 16, 240);
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ForgeJEIRecipe recipe, IFocusGroup focuses) {
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            List<ItemStack> stacks = new ArrayList<>();

            int amount = i < recipe.integers().size() ? recipe.integers().get(i) : 1;

            for (ItemStack item : recipe.getIngredients().get(i).getItems()) {
                ItemStack stack = item.copy();
                stack.setCount(amount);
                stacks.add(stack);
            }

            int x = switch (i) {
                case 0 -> 71;
                case 1 -> 89;
                case 2 -> 26;
                default -> 0;
            };

            int y = i == 2 ? 62 : 36;

            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStacks(stacks);
        }
        if (!recipe.getResultItems().isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 36).addItemStack(recipe.getResultItems().get(0));
        }
    }
}
