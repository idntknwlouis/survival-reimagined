package net.mcreator.survivalreimagined.compat.jei.gui.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mcreator.survivalreimagined.compat.jei.plugin.IngredientCodecsJEI;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MineralProcessingJEIRecipe implements Recipe<RecipeInput> {
    private final List<ItemStack> output;
    private final NonNullList<Ingredient> recipeItems;

    public MineralProcessingJEIRecipe(List<ItemStack> output, NonNullList<Ingredient> recipeItems) {
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
       pLevel.isClientSide();
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

    public static class Type implements RecipeType<MineralProcessingJEIRecipe> {
        private Type() {
        }

        public static final RecipeType<MineralProcessingJEIRecipe> INSTANCE = new Type();
    }


    public static class Serializer implements RecipeSerializer<MineralProcessingJEIRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final MapCodec<MineralProcessingJEIRecipe> CODEC = RecordCodecBuilder
                .mapCodec(builder -> builder.group(
                        ItemStack.OPTIONAL_CODEC.listOf().fieldOf("outputs").forGetter(recipe -> recipe.output),
                        IngredientCodecsJEI.INGREDIENT_CODEC.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
                    Ingredient[] aingredient = ingredients.toArray(Ingredient[]::new); // Skip the empty check and create the array.
                    if (aingredient.length == 0) {
                        return DataResult.error(() -> "No ingredients found in custom recipe");
                    } else {
                        return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                    }
                }, DataResult::success).forGetter(recipe -> recipe.recipeItems)).apply(builder, MineralProcessingJEIRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, MineralProcessingJEIRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<MineralProcessingJEIRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MineralProcessingJEIRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static MineralProcessingJEIRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
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
            return new MineralProcessingJEIRecipe(outputs, inputs);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, MineralProcessingJEIRecipe recipe) {
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

