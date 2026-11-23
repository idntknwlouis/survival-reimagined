package net.mcreator.survivalreimagined.compat.jade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.AdvancedAlloyForgeBlock;
import net.mcreator.survivalreimagined.block.ForgeBlock;
import net.mcreator.survivalreimagined.block.MillstoneBlock;
import net.mcreator.survivalreimagined.block.DryingRack;
import net.mcreator.survivalreimagined.block.MetalRefiningTableBlock;
import net.mcreator.survivalreimagined.block.TanningBarrel;
import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.MineralProcessingTableBlockEntity;
import net.mcreator.survivalreimagined.block.entity.MillstoneBlockEntity;
import net.mcreator.survivalreimagined.block.entity.DryingRackBlockEntity;
import net.mcreator.survivalreimagined.block.entity.MetalRefiningTableBlockEntity;
import net.mcreator.survivalreimagined.block.entity.TanningBarrelBlockEntity;
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
	public static final ResourceLocation MILLSTONE_PROGRESS = ResourceLocation.fromNamespaceAndPath(
			SurvivalReimaginedMod.MODID, "millstone_progress");
	public static final ResourceLocation DRYING_RACK_PROGRESS = ResourceLocation.fromNamespaceAndPath(
			SurvivalReimaginedMod.MODID, "drying_rack_progress");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(ForgeJadeProvider.INSTANCE, ForgeBlockEntity.class);
		registration.registerBlockDataProvider(ForgeJadeProvider.INSTANCE, AdvancedAlloyForgeBlockEntity.class);
		registration.registerBlockDataProvider(MineralProcessingJadeProvider.INSTANCE, MineralProcessingTableBlockEntity.class);
		registration.registerBlockDataProvider(MillstoneJadeProvider.INSTANCE, MillstoneBlockEntity.class);
		registration.registerBlockDataProvider(DryingRackJadeProvider.INSTANCE, DryingRackBlockEntity.class);
		registration.registerBlockDataProvider(MetalRefiningTableJadeProvider.INSTANCE, MetalRefiningTableBlockEntity.class);
		registration.registerBlockDataProvider(TanningBarrelJadeProvider.INSTANCE, TanningBarrelBlockEntity.class);
		registration.registerItemStorage(ForgeInventoryJadeProvider.INSTANCE, ForgeBlockEntity.class);
		registration.registerItemStorage(ForgeInventoryJadeProvider.INSTANCE, AdvancedAlloyForgeBlockEntity.class);
		// Override Jade's generic container item listing for the MPT; its custom
		// progress component already displays the relevant input/output icons.
		registration.registerItemStorage(ForgeInventoryJadeProvider.INSTANCE, MineralProcessingTableBlockEntity.class);
		// The custom Millstone component already displays its input and output.
		registration.registerItemStorage(ForgeInventoryJadeProvider.INSTANCE, MillstoneBlockEntity.class);
		registration.registerItemStorage(ForgeInventoryJadeProvider.INSTANCE, DryingRackBlockEntity.class);
		registration.registerItemStorage(ForgeInventoryJadeProvider.INSTANCE, MetalRefiningTableBlockEntity.class);
		registration.registerItemStorage(ForgeInventoryJadeProvider.INSTANCE, TanningBarrelBlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		
		registration.registerBlockComponent(ForgeJadeProvider.INSTANCE, ForgeBlock.class);
		registration.registerBlockComponent(ForgeJadeProvider.INSTANCE, AdvancedAlloyForgeBlock.class);
		registration.registerBlockComponent(MineralProcessingJadeProvider.INSTANCE, net.mcreator.survivalreimagined.block.MineralProcessingTableBlock.class);
		registration.registerBlockComponent(MillstoneJadeProvider.INSTANCE, MillstoneBlock.class);
		registration.registerBlockComponent(DryingRackJadeProvider.INSTANCE, DryingRack.class);
		registration.registerBlockComponent(MetalRefiningTableJadeProvider.INSTANCE, MetalRefiningTableBlock.class);
		registration.registerBlockComponent(TanningBarrelJadeProvider.INSTANCE, TanningBarrel.class);
		registration.registerItemStorageClient(ForgeInventoryJadeProvider.INSTANCE);
	}
}
