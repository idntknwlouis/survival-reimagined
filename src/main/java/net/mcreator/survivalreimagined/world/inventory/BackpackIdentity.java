package net.mcreator.survivalreimagined.world.inventory;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.UUID;

public class BackpackIdentity {
    private static final String UUID_KEY = "BackpackUUID";

    public static UUID getOrCreate(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);

        CompoundTag tag = customData == null ? new CompoundTag() : customData.copyTag();

        if (!tag.hasUUID(UUID_KEY)) {
            UUID uuid = UUID.randomUUID();
            tag.putUUID(UUID_KEY, uuid);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            return uuid;
        }
        return tag.getUUID(UUID_KEY);
    }

    public static boolean hasUUID(ItemStack stack, UUID uuid) {
        if (stack.isEmpty()) return false;
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) return false;
        CompoundTag tag = customData.copyTag();
        return tag.hasUUID(UUID_KEY) && tag.getUUID(UUID_KEY).equals(uuid);
    }
}
