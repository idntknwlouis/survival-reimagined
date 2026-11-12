package net.mcreator.survivalreimagined.compat.jei.plugin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.crafting.Ingredient;

public final class IngredientCodecsJEI {
    private IngredientCodecsJEI() {}

    private static final String EMPTY_TAG = "survival_reimagined:jei_empty_tag";

    public static final Codec<Ingredient> INGREDIENT_CODEC = Codec.PASSTHROUGH.flatXmap(
            IngredientCodecsJEI::decodeIngredient, ingredient -> Ingredient.CODEC.encodeStart(
                    JsonOps.INSTANCE, ingredient).map(json -> new Dynamic<>(JsonOps.INSTANCE, json))
    );
    private static <T> DataResult<Ingredient> decodeIngredient(Dynamic<T> dynamic) {
        JsonElement json = dynamic.convert(JsonOps.INSTANCE).getValue();

        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();

            if (object.has("tag")
                    && object.get("tag").isJsonPrimitive()
                    && EMPTY_TAG.equals(object.get("tag").getAsString())) {
                return DataResult.success(Ingredient.EMPTY);
            }
        }
        return Ingredient.CODEC.parse(dynamic.getOps(), dynamic.getValue());
    }

    public static boolean isEmptySlot(Ingredient ingredient) {
        return ingredient == Ingredient.EMPTY;
    }
}
