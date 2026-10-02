package net.mcreator.survivalreimagined.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public class SteelSwordItem extends SwordItem {
	private static final Tier TOOL_TIER = new Tier() {
		public int getUses() { return 350; }
		public float getSpeed() { return 4f; }
		public float getAttackDamageBonus() { return 0; }
		public TagKey<Block> getIncorrectBlocksForDrops() { return BlockTags.INCORRECT_FOR_IRON_TOOL; }
		public int getEnchantmentValue() { return 15; }
		public Ingredient getRepairIngredient() { return Ingredient.of(new ItemStack(SurvivalReimaginedModItems.STEEL_INGOT.get())); }
	};
	public SteelSwordItem() {
		super(TOOL_TIER, new Item.Properties().attributes(ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 5.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
	}
}
