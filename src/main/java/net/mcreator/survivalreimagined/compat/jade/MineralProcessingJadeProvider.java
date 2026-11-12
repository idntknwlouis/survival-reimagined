package net.mcreator.survivalreimagined.compat.jade;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.mcreator.survivalreimagined.block.entity.MineralProcessingTableBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

public enum MineralProcessingJadeProvider implements IBlockComponentProvider,
		StreamServerDataProvider<BlockAccessor, MineralProcessingJadeProvider.Data> {
	INSTANCE;

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		Data data = decodeFromData(accessor).orElse(null);
		if (data == null || data.inventory().stream().allMatch(ItemStack::isEmpty)) {
			return;
		}

		IElementHelper helper = IElementHelper.get();
		tooltip.add(helper.item(data.inventory().get(0)));
		tooltip.append(helper.spacer(4, 0));
		tooltip.append(helper.progress(data.total() == 0 ? 0F : (float) data.progress() / data.total()).translate(new Vec2(-2, 0)));
		tooltip.append(helper.item(data.inventory().get(1)));
		tooltip.add(Component.literal("Reactor Rod Capacity: " + (data.capacityTenths() / 10.0) + " / " + (data.maxCapacityTenths() / 10.0)));
	}

	@Override
	public Data streamData(BlockAccessor accessor) {
		MineralProcessingTableBlockEntity table = (MineralProcessingTableBlockEntity) accessor.getBlockEntity();
		return new Data(table.getProgress(), MineralProcessingTableBlockEntity.MAX_PROGRESS,
				List.of(table.getItem(0), table.getItem(2)), table.getReactorRodCapacityTenths(), MineralProcessingTableBlockEntity.MAX_ROD_CAPACITY_TENTHS);
	}

	@Override
	public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
		return Data.STREAM_CODEC;
	}

	@Override
	public ResourceLocation getUid() {
		return SurvivalReimaginedJadePlugin.MPT_PROGRESS;
	}

	public record Data(int progress, int total, List<ItemStack> inventory, int capacityTenths, int maxCapacityTenths) {
		public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.VAR_INT, Data::progress,
				ByteBufCodecs.VAR_INT, Data::total,
				ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::inventory,
				ByteBufCodecs.VAR_INT, Data::capacityTenths,
				ByteBufCodecs.VAR_INT, Data::maxCapacityTenths,
				Data::new);
	}
}
