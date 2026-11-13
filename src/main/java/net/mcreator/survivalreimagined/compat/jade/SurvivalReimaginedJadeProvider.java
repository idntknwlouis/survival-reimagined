package net.mcreator.survivalreimagined.compat.jade;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public final class SurvivalReimaginedJadeProvider implements IBlockComponentProvider {
	public static final SurvivalReimaginedJadeProvider INSTANCE = new SurvivalReimaginedJadeProvider();

	private SurvivalReimaginedJadeProvider() {
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(accessor.getBlockState().getBlock());
		if (!"survival_reimagined".equals(blockId.getNamespace())) {
			return;
		}

		BlockEntity blockEntity = accessor.getBlockEntity();
		if (blockEntity instanceof ForgeBlockEntity || blockEntity instanceof AdvancedAlloyForgeBlockEntity
				|| !(blockEntity instanceof Container container) || container.getContainerSize() <= 0) {
			return;
		}

		int occupiedSlots = 0;
		for (int slot = 0; slot < container.getContainerSize(); slot++) {
			if (!container.getItem(slot).isEmpty()) {
				occupiedSlots++;
			}
		}

		tooltip.add(Component.translatable(
				"tooltip.survival_reimagined.jade.inventory",
				occupiedSlots,
				container.getContainerSize()));
	}

	@Override
	public ResourceLocation getUid() {
		return SurvivalReimaginedJadePlugin.MACHINE_INFO;
	}
}
