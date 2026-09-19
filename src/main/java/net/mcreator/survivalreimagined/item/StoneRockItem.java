package net.mcreator.survivalreimagined.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

import net.mcreator.survivalreimagined.procedures.StoneRockRightclickedOnBlockProcedure;

public class StoneRockItem extends Item {
	public StoneRockItem() {
		super(new Item.Properties());
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		StoneRockRightclickedOnBlockProcedure.execute(
				context.getLevel(),
				context.getClickedPos().getX(),
				context.getClickedPos().getY(),
				context.getClickedPos().getZ(),
				context.getItemInHand());
		return InteractionResult.SUCCESS;
	}
}
