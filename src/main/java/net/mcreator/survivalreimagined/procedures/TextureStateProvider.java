package net.mcreator.survivalreimagined.procedures;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
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
        if (itemstack == null || itemstack.isEmpty()) {
            return 0;
        }

        if (itemstack.is(CAN_ROT)) {
            CustomData data = itemstack.get(DataComponents.CUSTOM_DATA);
            if (data != null) {
                double spoilageMax = data.copyTag().getDouble("SpoilageMax");
                if (spoilageMax >= 500 && spoilageMax <= 999) {
                    return 1;
                } else if (spoilageMax >= 1000 && spoilageMax <= 1499) {
                    return 2;
                } else if (spoilageMax >= 1500 && spoilageMax <= 1999) {
                    return 3;
                }
            }
        }
        return 0;
    }
}
