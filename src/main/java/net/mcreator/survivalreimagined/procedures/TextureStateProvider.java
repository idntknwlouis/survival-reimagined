package net.mcreator.survivalreimagined.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class TextureStateProvider {
    private static final TagKey<Item> CAN_ROT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "food/can_rot"));

    public static double execute(ItemStack itemstack) {
        if (itemstack.is(CAN_ROT)) {
            if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag().getDouble("SpoilageMax") >= 500
            && itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("SpoilageMax") <= 999) {
                return 0.1f;
            } else if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag().getDouble("SpoilageMax") >= 1000
                    && itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("SpoilageMax") <= 1499) {
                return 0.2f;
            } else if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag().getDouble("SpoilageMax") >= 1500
                    && itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("SpoilageMax") <= 1999) {
                return 0.3f;
            }
        }
        return 0;
    }
}
