package net.mcreator.survivalreimagined.compat.jei.gui.recipe;

import net.mcreator.survivalreimagined.compat.jei.plugin.IngredientCodecsJEI;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.Ingredient;
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
import com.mojang.serialization.Codec;


public class ForgeJEIRecipe implements Recipe<RecipeInput> {
    private final List<ItemStack> output;
    private final NonNullList<Ingredient> recipeItems;
    private final List<Integer> integers;


    public ForgeJEIRecipe(List<ItemStack> output, NonNullList<Ingredient> recipeItems, List<Integer> integers) {
        this.output = output;
        this.recipeItems = recipeItems;
        this.integers = integers;
    }

    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        pLevel.isClientSide();
        return false;
    }

    public List<Integer> integers() {
        return this.integers;
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

    public static class Type implements RecipeType<ForgeJEIRecipe> {
        private Type() {}
        public static final RecipeType<ForgeJEIRecipe> INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<ForgeJEIRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final MapCodec<ForgeJEIRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                ItemStack.OPTIONAL_CODEC.listOf().fieldOf("outputs").forGetter(recipe -> recipe.output),
                IngredientCodecsJEI.INGREDIENT_CODEC.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
                    Ingredient[] aingredient = ingredients.toArray(Ingredient[]::new);
                    if (aingredient.length == 0) {
                        return DataResult.error(() -> "No ingredients found in custom recipe");
                    } else {
                        return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                    }
                 }, DataResult::success).forGetter(recipe -> recipe.recipeItems), Codec.INT.listOf().fieldOf("integers").forGetter(recipe -> recipe.integers)).apply(builder, ForgeJEIRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, ForgeJEIRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<ForgeJEIRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ForgeJEIRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ForgeJEIRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            int ingredientCount = buf.readVarInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);
            for (int i = 0; i < ingredientCount; i++) {
                boolean empty = buf.readBoolean();
                if (empty) inputs.set(i, Ingredient.EMPTY);
                else inputs.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            }
            int outputCount = buf.readVarInt();
            List<ItemStack> outputs = new ArrayList<>(outputCount);
            for (int i = 0; i < outputCount; i++) {
                outputs.add(ItemStack.STREAM_CODEC.decode(buf));
            }
            int integerCount = buf.readVarInt();
            List<Integer> numbers = new ArrayList<>(integerCount);
            for (int i = 0; i < integerCount; i++) {
                numbers.add(buf.readVarInt());
            }
            return new ForgeJEIRecipe(outputs, inputs, numbers);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, ForgeJEIRecipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                boolean empty = IngredientCodecsJEI.isEmptySlot(ingredient);
                buf.writeBoolean(empty);

                if (!empty) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            buf.writeVarInt(recipe.getResultItems().size());
            for (ItemStack stacks : recipe.getResultItems()) {
                ItemStack.STREAM_CODEC.encode(buf, stacks);
            }
            buf.writeVarInt(recipe.integers().size());
            for (Integer num : recipe.integers()) {
                buf.writeVarInt(num);
            }
        }
    }
}
