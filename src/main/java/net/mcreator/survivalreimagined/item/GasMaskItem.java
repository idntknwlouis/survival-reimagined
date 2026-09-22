package net.mcreator.survivalreimagined.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModArmorMaterials;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;

import java.util.List;

public class GasMaskItem extends ArmorItem {
	private static final ResourceLocation RADIATED_FOREST =
			ResourceLocation.fromNamespaceAndPath("survival_reimagined", "radiant_forest");

	public GasMaskItem() {
		super(SurvivalReimaginedModArmorMaterials.GAS_MASK, Type.HELMET,
				new Item.Properties().durability(Type.HELMET.getDurability(11)));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);

		if (!(entity instanceof Player player)
				|| player.getItemBySlot(EquipmentSlot.HEAD) != stack
				|| level.isClientSide()) {
			return;
		}

		boolean inRadiatedForest = level.getBiome(entity.blockPosition()).unwrapKey()
				.map(key -> key.location().equals(RADIATED_FOREST))
				.orElse(false);
		if (!inRadiatedForest || level.getGameTime() % 200L != 0L) {
			return;
		}

		double filter = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
				.copyTag().getDouble("FilterPercentage");
		if (filter <= 0.0D) {
			return;
		}

		double next = Math.max(0.0D, filter - 1.0D);
		CustomData.update(DataComponents.CUSTOM_DATA, stack,
				tag -> tag.putDouble("FilterPercentage", next));

		level.playSound(null, entity.blockPosition(), SurvivalReimaginedModSounds.GAS_MASK_BREATH.get(),
				SoundSource.PLAYERS, 0.8F, 0.8F);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);

		int protection = (int) Math.round(stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
				.copyTag().getDouble("FilterPercentage"));
		protection = Math.max(0, Math.min(100, protection));
		tooltip.add(Component.literal("§7 Protection: " + protection + "%"));
	}
}
