package net.mcreator.survivalreimagined.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DiamondSawItem extends TieredItem {
	private static final Tier TOOL_TIER = new Tier() {
		public int getUses() { return 1561; }
		public float getSpeed() { return 7.5f; }
		public float getAttackDamageBonus() { return 0; }
		public TagKey<Block> getIncorrectBlocksForDrops() { return BlockTags.INCORRECT_FOR_DIAMOND_TOOL; }
		public int getEnchantmentValue() { return 15; }
		public Ingredient getRepairIngredient() { return Ingredient.of(new ItemStack(Items.DIAMOND)); }
	};

	public DiamondSawItem() {
		super(TOOL_TIER, new Item.Properties().attributes(ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 2.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
	}
	@Override public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
		return state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_HOE) || state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL);
	}
	@Override public float getDestroySpeed(ItemStack stack, BlockState state) { return 7.5f; }
	@Override public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		stack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand())); return true;
	}
	@Override public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(2, attacker, LivingEntity.getSlotForHand(attacker.getUsedItemHand())); return true;
	}
}
