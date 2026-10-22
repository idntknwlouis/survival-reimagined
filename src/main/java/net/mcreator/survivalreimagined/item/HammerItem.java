package net.mcreator.survivalreimagined.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier tier, Item.Properties properties) {
        super(tier, BlockTags.AIR, properties);
    }
}
