package net.mcreator.survivalreimagined.compat.jade;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class SurvivalReimaginedJadePlugin implements IWailaPlugin {
	public static final ResourceLocation MACHINE_INFO = ResourceLocation.fromNamespaceAndPath(
			SurvivalReimaginedMod.MODID, "machine_info");

	@Override
	public void register(IWailaCommonRegistration registration) {
		// Inventory state is already synchronized by the block entities.
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(SurvivalReimaginedJadeProvider.INSTANCE, Block.class);
	}
}
