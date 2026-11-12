package net.mcreator.survivalreimagined.compat.jade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.AdvancedAlloyForgeBlock;
import net.mcreator.survivalreimagined.block.ForgeBlock;
import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.MineralProcessingTableBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class SurvivalReimaginedJadePlugin implements IWailaPlugin {
	public static final ResourceLocation MACHINE_INFO = ResourceLocation.fromNamespaceAndPath(
			SurvivalReimaginedMod.MODID, "machine_info");
	public static final ResourceLocation FORGE_PROGRESS = ResourceLocation.fromNamespaceAndPath(
			SurvivalReimaginedMod.MODID, "forge_progress");
	public static final ResourceLocation MPT_PROGRESS = ResourceLocation.fromNamespaceAndPath(
			SurvivalReimaginedMod.MODID, "mpt_progress");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(ForgeJadeProvider.INSTANCE, ForgeBlockEntity.class);
		registration.registerBlockDataProvider(ForgeJadeProvider.INSTANCE, AdvancedAlloyForgeBlockEntity.class);
		registration.registerBlockDataProvider(MineralProcessingJadeProvider.INSTANCE, MineralProcessingTableBlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		
		registration.registerBlockComponent(ForgeJadeProvider.INSTANCE, ForgeBlock.class);
		registration.registerBlockComponent(ForgeJadeProvider.INSTANCE, AdvancedAlloyForgeBlock.class);
		registration.registerBlockComponent(MineralProcessingJadeProvider.INSTANCE, net.mcreator.survivalreimagined.block.MineralProcessingTableBlock.class);
	}
}
