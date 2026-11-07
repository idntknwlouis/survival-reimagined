package net.mcreator.survivalreimagined.item;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class KnifeItem extends DiggerItem implements FabricItem {
    public KnifeItem(Tier tier, Item.Properties properties) {
        super(tier, SurvivalReimaginedModTags.MINEABLE_WITH_KNIFE, properties);
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
