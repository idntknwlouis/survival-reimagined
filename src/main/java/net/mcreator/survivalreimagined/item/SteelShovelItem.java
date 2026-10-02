package net.mcreator.survivalreimagined.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public class SteelShovelItem extends ShovelItem {
	private static final Tier TOOL_TIER = new Tier() {
		public int getUses() { return 650; }
		public float getSpeed() { return 7f; }
		public float getAttackDamageBonus() { return 0; }
		public TagKey<Block> getIncorrectBlocksForDrops() { return BlockTags.INCORRECT_FOR_IRON_TOOL; }
		public int getEnchantmentValue() { return 15; }
		public Ingredient getRepairIngredient() { return Ingredient.of(new ItemStack(SurvivalReimaginedModItems.STEEL_INGOT.get())); }
	};
	public SteelShovelItem() {
		super(TOOL_TIER, new Item.Properties().attributes(DiggerItem.createAttributes(TOOL_TIER, 1.5f, -3f)));
	}
}
