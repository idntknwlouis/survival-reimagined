package net.mcreator.survivalreimagined.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class SurvivalArmorItem extends ArmorItem {
	public SurvivalArmorItem(Holder<ArmorMaterial> material, Type type, int durabilityMultiplier) {
		super(material, type, new Item.Properties().durability(type.getDurability(durabilityMultiplier)));
	}
}
