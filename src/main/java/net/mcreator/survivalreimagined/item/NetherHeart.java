package net.mcreator.survivalreimagined.item;

import net.mcreator.survivalreimagined.procedures.NetherHeartRightClickedOnBlock;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;

public class NetherHeart extends Item {
    public NetherHeart() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        return NetherHeartRightClickedOnBlock.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer());
    }
}
