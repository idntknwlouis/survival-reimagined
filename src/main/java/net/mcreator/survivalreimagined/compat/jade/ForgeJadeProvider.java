package net.mcreator.survivalreimagined.compat.jade;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

public enum ForgeJadeProvider implements IBlockComponentProvider, StreamServerDataProvider<BlockAccessor, ForgeJadeProvider.Data> {
	INSTANCE;

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		Data data = decodeFromData(accessor).orElse(null);
		if (data == null || data.inventory().stream().allMatch(ItemStack::isEmpty)) {
			return;
		}

		IElementHelper helper = IElementHelper.get();
		tooltip.add(helper.item(data.inventory().get(0)));
		tooltip.append(helper.item(data.inventory().get(1)));
		tooltip.append(helper.spacer(4, 0));
		tooltip.append(helper.progress(data.total() == 0 ? 0F : (float) data.progress() / data.total()).translate(new Vec2(-2, 0)));
		tooltip.append(helper.item(data.inventory().get(2)));
		if (data.advanced()) {
			tooltip.add(Component.literal("Reactor Rod Capacity: " + data.capacity() + " / " + data.maxCapacity()));
			if (!data.upgrades().isEmpty()) {
				Component upgrades = Component.literal("Upgrades: ");
				for (int i = 0; i < data.upgrades().size(); i++) {
					if (i > 0) upgrades.append(Component.literal(", "));
					upgrades.append(data.upgrades().get(i).getHoverName());
				}
				tooltip.add(upgrades);
			}
		}
	}

	@Override
	public Data streamData(BlockAccessor accessor) {
		if (accessor.getBlockEntity() instanceof ForgeBlockEntity forge) {
			return new Data(forge.getBurnTime(), ForgeBlockEntity.MAX_BURN_TIME,
					List.of(forge.getItem(0), forge.getItem(1), forge.getItem(3)), false, 0, 0, List.of());
		}
		AdvancedAlloyForgeBlockEntity forge = (AdvancedAlloyForgeBlockEntity) accessor.getBlockEntity();
		return new Data(forge.getProgress(), AdvancedAlloyForgeBlockEntity.MAX_PROGRESS,
				List.of(forge.getItem(1), forge.getItem(2), forge.getItem(0)), true, forge.getFuelCapacity(), forge.getMaxFuelCapacity(), forge.getInstalledUpgrades());
	}

	@Override
	public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
		return Data.STREAM_CODEC;
	}

	@Override
	public ResourceLocation getUid() {
		return SurvivalReimaginedJadePlugin.FORGE_PROGRESS;
	}

	public record Data(int progress, int total, List<ItemStack> inventory, boolean advanced, int capacity, int maxCapacity, List<ItemStack> upgrades) {
		public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.VAR_INT, Data::progress,
				ByteBufCodecs.VAR_INT, Data::total,
				ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::inventory,
				ByteBufCodecs.BOOL, Data::advanced,
				ByteBufCodecs.VAR_INT, Data::capacity,
				ByteBufCodecs.VAR_INT, Data::maxCapacity,
				ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::upgrades,
				Data::new);
	}
}
