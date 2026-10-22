package net.mcreator.survivalreimagined.item;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class KnifeItem extends DiggerItem {
    public KnifeItem(Tier tier, Item.Properties properties) {
        super(tier, SurvivalReimaginedModTags.MINEABLE_WITH_KNIFE, properties);
    }
}
