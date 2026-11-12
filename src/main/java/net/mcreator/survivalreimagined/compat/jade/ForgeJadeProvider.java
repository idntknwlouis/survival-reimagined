package net.mcreator.survivalreimagined.compat.jade;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
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
	}

	@Override
	public Data streamData(BlockAccessor accessor) {
		if (accessor.getBlockEntity() instanceof ForgeBlockEntity forge) {
			return new Data(forge.getBurnTime(), ForgeBlockEntity.MAX_BURN_TIME,
					List.of(forge.getItem(0), forge.getItem(1), forge.getItem(3)));
		}
		AdvancedAlloyForgeBlockEntity forge = (AdvancedAlloyForgeBlockEntity) accessor.getBlockEntity();
		return new Data(forge.getProgress(), AdvancedAlloyForgeBlockEntity.MAX_PROGRESS,
				List.of(forge.getItem(1), forge.getItem(2), forge.getItem(0)));
	}

	@Override
	public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
		return Data.STREAM_CODEC;
	}

	@Override
	public ResourceLocation getUid() {
		return SurvivalReimaginedJadePlugin.FORGE_PROGRESS;
	}

	public record Data(int progress, int total, List<ItemStack> inventory) {
		public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.VAR_INT, Data::progress,
				ByteBufCodecs.VAR_INT, Data::total,
				ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::inventory,
				Data::new);
	}
}
