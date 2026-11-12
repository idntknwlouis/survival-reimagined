package net.mcreator.survivalreimagined.compat.jei.gui.recipe;

import net.mcreator.survivalreimagined.block.TanningBarrel;
import net.mcreator.survivalreimagined.compat.jei.plugin.IngredientCodecsJEI;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.DataResult;

public class TanningBarrelJEIRecipe implements Recipe<RecipeInput> {
    private final List<ItemStack> output;
    private final NonNullList<Ingredient> recipeItems;

    public TanningBarrelJEIRecipe(List<ItemStack> output, NonNullList<Ingredient> recipeItems) {
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider holder) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    public List<ItemStack> getResultItems() {
        return output;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Type implements RecipeType<TanningBarrelJEIRecipe> {
        private Type() {
        }

        public static final RecipeType<TanningBarrelJEIRecipe> INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<TanningBarrelJEIRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final MapCodec<TanningBarrelJEIRecipe> CODEC = RecordCodecBuilder
                .mapCodec(builder -> builder.group(
                        ItemStack.OPTIONAL_CODEC.listOf().fieldOf("outputs").forGetter(recipe -> recipe.output),
                        IngredientCodecsJEI.INGREDIENT_CODEC.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
                    Ingredient[] aingredient = ingredients.toArray(Ingredient[]::new); // Skip the empty check and create the array.
                    if (aingredient.length == 0) {
                        return DataResult.error(() -> "No ingredients found in custom recipe");
                    } else {
                        return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                    }
                }, DataResult::success).forGetter(recipe -> recipe.recipeItems)).apply(builder, TanningBarrelJEIRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, TanningBarrelJEIRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<TanningBarrelJEIRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TanningBarrelJEIRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static TanningBarrelJEIRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            int ingredientCount = buf.readVarInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);
            for (int i = 0; i < ingredientCount; i++) {
                boolean empty = buf.readBoolean();
                if (empty) inputs.set(i, Ingredient.EMPTY);
                else inputs.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            }
            int outputCount = buf.readVarInt();
            List<ItemStack> outputs = new ArrayList<>();
            for (int i = 0; i <outputCount; i++) outputs.add(ItemStack.STREAM_CODEC.decode(buf));
            return new TanningBarrelJEIRecipe(outputs, inputs);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, TanningBarrelJEIRecipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                boolean empty = IngredientCodecsJEI.isEmptySlot(ing);
                buf.writeBoolean(empty);
                if (!empty) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
            }
            buf.writeVarInt(recipe.getResultItems().size());
            for (ItemStack itemstack : recipe.getResultItems()) ItemStack.STREAM_CODEC.encode(buf, itemstack);
        }
    }

}
