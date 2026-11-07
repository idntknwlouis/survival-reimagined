package net.mcreator.survivalreimagined.item;

import net.mcreator.survivalreimagined.procedures.bloodmoon.BloodMoonHeartRightClicked;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class BloodMoonHeart extends Item {
    public BloodMoonHeart() {
        super(new Item.Properties().rarity(Rarity.RARE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> item = super.use(world, entity, hand);
        BloodMoonHeartRightClicked.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
        return item;
    }
}
