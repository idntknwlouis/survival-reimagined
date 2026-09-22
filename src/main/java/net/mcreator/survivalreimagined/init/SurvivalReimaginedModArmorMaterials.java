package net.mcreator.survivalreimagined.init;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public final class SurvivalReimaginedModArmorMaterials {
	public static Holder<ArmorMaterial> WOODEN;
	public static Holder<ArmorMaterial> BRONZE;
	public static Holder<ArmorMaterial> STEEL;

	private SurvivalReimaginedModArmorMaterials() {
	}

	public static void register() {
		WOODEN = register("wooden", new ArmorMaterial(
				Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
					map.put(ArmorItem.Type.BOOTS, 2);
					map.put(ArmorItem.Type.LEGGINGS, 3);
					map.put(ArmorItem.Type.CHESTPLATE, 4);
					map.put(ArmorItem.Type.HELMET, 2);
					map.put(ArmorItem.Type.BODY, 4);
				}),
				9,
				BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ARMOR_EQUIP_LEATHER),
				Ingredient::of,
				List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "wooden"))),
				0.0F,
				0.0F
		));

		BRONZE = register("bronze_armor", new ArmorMaterial(
				Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
					map.put(ArmorItem.Type.BOOTS, 2);
					map.put(ArmorItem.Type.LEGGINGS, 5);
					map.put(ArmorItem.Type.CHESTPLATE, 6);
					map.put(ArmorItem.Type.HELMET, 2);
					map.put(ArmorItem.Type.BODY, 6);
				}),
				9,
				BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ARMOR_EQUIP_IRON),
				() -> Ingredient.of(new ItemStack(SurvivalReimaginedModItems.BRONZE_INGOT.get())),
				List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "bronze"))),
				0.0F,
				0.0F
		));

		STEEL = register("steel_armor", new ArmorMaterial(
				Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
					map.put(ArmorItem.Type.BOOTS, 3);
					map.put(ArmorItem.Type.LEGGINGS, 6);
					map.put(ArmorItem.Type.CHESTPLATE, 7);
					map.put(ArmorItem.Type.HELMET, 3);
					map.put(ArmorItem.Type.BODY, 7);
				}),
				10,
				BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ARMOR_EQUIP_NETHERITE),
				() -> Ingredient.of(new ItemStack(SurvivalReimaginedModItems.STEEL_INGOT.get())),
				List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("survival_reimagined", "steel"))),
				1.0F,
				0.0F
		));
	}

	private static Holder<ArmorMaterial> register(String name, ArmorMaterial material) {
		Registry.register(BuiltInRegistries.ARMOR_MATERIAL,
				ResourceLocation.fromNamespaceAndPath("survival_reimagined", name), material);
		return BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(material);
	}
}
