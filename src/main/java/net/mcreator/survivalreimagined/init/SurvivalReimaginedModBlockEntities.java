package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.MetalRefiningTableBlockEntity;
import net.mcreator.survivalreimagined.block.entity.MineralProcessingTableBlockEntity;
import net.mcreator.survivalreimagined.block.entity.MillstoneBlockEntity;
import net.mcreator.survivalreimagined.block.entity.CampfireBlockEntity;
import net.mcreator.survivalreimagined.block.entity.BlockOfCharcoalBlockEntity;
import net.mcreator.survivalreimagined.block.entity.RuneMagicInfuserBlockEntity;
import net.mcreator.survivalreimagined.block.entity.CarcassBlockEntity;
import net.mcreator.survivalreimagined.block.entity.PalmLeavesBlockEntity;
import net.mcreator.survivalreimagined.util.RegistryEntry;

public final class SurvivalReimaginedModBlockEntities {
	public static final RegistryEntry<BlockEntityType<PalmLeavesBlockEntity>> PALM_LEAVES = register(
			"palm_leaves",
			BlockEntityType.Builder.of(PalmLeavesBlockEntity::new, SurvivalReimaginedModBlocks.PALM_LEAVES.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<CarcassBlockEntity>> CARCASS = register(
			"carcass",
			BlockEntityType.Builder.of(CarcassBlockEntity::new, SurvivalReimaginedModBlocks.COW_CARCASS.get(), SurvivalReimaginedModBlocks.PIG_CARCASS.get(), SurvivalReimaginedModBlocks.SHEEP_CARCASS.get(), SurvivalReimaginedModBlocks.GOAT_CARCASS.get(), SurvivalReimaginedModBlocks.CHICKEN_CARCASS.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<ForgeBlockEntity>> FORGE = register(
			"forge",
			BlockEntityType.Builder.of(ForgeBlockEntity::new, SurvivalReimaginedModBlocks.FORGE.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<MetalRefiningTableBlockEntity>> METAL_REFINING_TABLE = register(
			"metal_refining_table",
			BlockEntityType.Builder.of(MetalRefiningTableBlockEntity::new, SurvivalReimaginedModBlocks.METAL_REFINING_TABLE.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<MineralProcessingTableBlockEntity>> MINERAL_PROCESSING_TABLE = register(
			"mineral_processing_table",
			BlockEntityType.Builder.of(MineralProcessingTableBlockEntity::new, SurvivalReimaginedModBlocks.MINERAL_PROCESSING_TABLE.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<MillstoneBlockEntity>> MILLSTONE = register(
			"millstone",
			BlockEntityType.Builder.of(MillstoneBlockEntity::new, SurvivalReimaginedModBlocks.MILLSTONE.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<CampfireBlockEntity>> CAMPFIRE = register(
			"campfire",
			BlockEntityType.Builder.of(CampfireBlockEntity::new, SurvivalReimaginedModBlocks.CAMPFIRE.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<BlockOfCharcoalBlockEntity>> BLOCK_OF_CHARCOAL = register(
			"block_of_charcoal",
			BlockEntityType.Builder.of(BlockOfCharcoalBlockEntity::new, SurvivalReimaginedModBlocks.BLOCK_OF_CHARCOAL.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<RuneMagicInfuserBlockEntity>> RUNE_MAGIC_INFUSER = register(
			"rune_magic_infuser",
			BlockEntityType.Builder.of(RuneMagicInfuserBlockEntity::new, SurvivalReimaginedModBlocks.RUNE_MAGIC_INFUSER.get()).build(null)
	);
	public static final RegistryEntry<BlockEntityType<AdvancedAlloyForgeBlockEntity>> ADVANCED_ALLOY_FORGE = register(
			"advanced_alloy_forge",
			BlockEntityType.Builder.of(AdvancedAlloyForgeBlockEntity::new, SurvivalReimaginedModBlocks.ADVANCED_ALLOY_FORGE.get()).build(null)
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
