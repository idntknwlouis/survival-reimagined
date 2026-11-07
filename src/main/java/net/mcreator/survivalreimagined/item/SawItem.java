package net.mcreator.survivalreimagined.item;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class SawItem extends DiggerItem implements FabricItem {
    public SawItem(Tier tier, Item.Properties properties) {
        super(tier, BlockTags.AIR, properties);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        ItemStack remainder = stack.copy();
        remainder.setDamageValue(stack.getDamageValue() + 1);

        if (remainder.getDamageValue() >= remainder.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return remainder;
    }
}
