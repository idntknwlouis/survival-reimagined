package net.mcreator.survivalreimagined.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.stream.Stream;

public class BronzeKnifeItem extends Item {
	public BronzeKnifeItem() {
		super(new Item.Properties().durability(350).attributes(ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
	}
	@Override public float getDestroySpeed(ItemStack stack, BlockState state) {
		return Stream.of(tag("c:carcasses"), tag("c:smaller_carcasses"), tag("c:carcass/equine")).anyMatch(state::is) ? 6f : 1;
	}
	@Override public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		stack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand())); return true;
	}
	@Override public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(2, attacker, LivingEntity.getSlotForHand(attacker.getUsedItemHand())); return true;
	}
	@Override public int getEnchantmentValue() { return 14; }

	private static TagKey<Block> tag(String id) {
		return TagKey.create(Registries.BLOCK, ResourceLocation.parse(id));
	}
}
