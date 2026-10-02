package net.mcreator.survivalreimagined.jei_recipes;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class MetalRefiningRecipe implements Recipe<RecipeInput> {
	private final List<ItemStack> output;
	private final NonNullList<Ingredient> recipeItems;

	public MetalRefiningRecipe(List<ItemStack> output, NonNullList<Ingredient> recipeItems) {
		this.output = output;
		this.recipeItems = recipeItems;
	}

	@Override
	public boolean matches(RecipeInput input, Level level) {
		return false;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return recipeItems;
	}

	@Override
	public ItemStack assemble(RecipeInput input, HolderLookup.Provider provider) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
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

	public static final class Type implements RecipeType<MetalRefiningRecipe> {
		public static final Type INSTANCE = new Type();

		private Type() {
		}
	}

	public static final class Serializer implements RecipeSerializer<MetalRefiningRecipe> {
		public static final Serializer INSTANCE = new Serializer();

		private static final MapCodec<MetalRefiningRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
				ItemStack.OPTIONAL_CODEC.listOf().fieldOf("outputs").forGetter(recipe -> recipe.output),
				Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(ingredients -> {
					Ingredient[] array = ingredients.toArray(Ingredient[]::new);
					return array.length == 0
							? DataResult.error(() -> "No ingredients found in custom recipe")
							: DataResult.success(NonNullList.of(Ingredient.EMPTY, array));
				}, DataResult::success).forGetter(recipe -> recipe.recipeItems)
		).apply(builder, MetalRefiningRecipe::new));

		private static final StreamCodec<RegistryFriendlyByteBuf, MetalRefiningRecipe> STREAM_CODEC =
				StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

		@Override
		public MapCodec<MetalRefiningRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, MetalRefiningRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static MetalRefiningRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
			NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
			inputs.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
			List<ItemStack> outputs = NonNullList.withSize(buf.readVarInt(), ItemStack.EMPTY);
			outputs.replaceAll(ignored -> ItemStack.STREAM_CODEC.decode(buf));
			return new MetalRefiningRecipe(outputs, inputs);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buf, MetalRefiningRecipe recipe) {
			buf.writeVarInt(recipe.getIngredients().size());
			for (Ingredient ingredient : recipe.getIngredients()) {
				ItemStack[] stacks = ingredient.getItems();
				Ingredient.CONTENTS_STREAM_CODEC.encode(buf,
						stacks.length > 0 && stacks[0].is(Items.AIR) ? Ingredient.EMPTY : ingredient);
			}
			buf.writeVarInt(recipe.getResultItems().size());
			for (ItemStack stack : recipe.getResultItems()) {
				ItemStack.STREAM_CODEC.encode(buf, stack);
			}
		}
	}
}
