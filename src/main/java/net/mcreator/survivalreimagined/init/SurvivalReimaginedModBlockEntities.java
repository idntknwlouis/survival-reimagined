package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import net.mcreator.survivalreimagined.util.RegistryEntry;

public final class SurvivalReimaginedModBlockEntities {
	public static final RegistryEntry<BlockEntityType<ForgeBlockEntity>> FORGE = register(
			"forge",
			BlockEntityType.Builder.of(ForgeBlockEntity::new, SurvivalReimaginedModBlocks.FORGE.get()).build(null)
	);

	private SurvivalReimaginedModBlockEntities() {
	}

	private static <T extends BlockEntity> RegistryEntry<BlockEntityType<T>> register(String path, BlockEntityType<T> type) {
		var id = SurvivalReimaginedMod.asResource(path);
		BlockEntityType<T> blockEntityType = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, type);
		return new RegistryEntry<>(id, blockEntityType);
	}

	public static void register() {
		// Forces class initialization.
	}
}
