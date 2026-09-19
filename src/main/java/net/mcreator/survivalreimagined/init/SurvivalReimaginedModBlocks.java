package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.AndesiteRockBlockBlock;
import net.mcreator.survivalreimagined.block.BlockOfRawTinBlock;
import net.mcreator.survivalreimagined.block.BlockOfTinBlock;
import net.mcreator.survivalreimagined.block.CopperRockBlockBlock;
import net.mcreator.survivalreimagined.block.DeepslateTinOreBlock;
import net.mcreator.survivalreimagined.block.FlintblockBlock;
import net.mcreator.survivalreimagined.block.StoneRockBlocBlock;
import net.mcreator.survivalreimagined.block.TinOreBlock;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import java.util.function.Supplier;

public final class SurvivalReimaginedModBlocks {
	public static final RegistryEntry<Block> FLINTBLOCK = register("flintblock", FlintblockBlock::new);
	public static final RegistryEntry<Block> STONE_ROCK_BLOC = register("stone_rock_bloc", StoneRockBlocBlock::new);

	public static final RegistryEntry<Block> TIN_ORE = register("tin_ore", TinOreBlock::new);
	public static final RegistryEntry<Block> DEEPSLATE_TIN_ORE = register("deepslate_tin_ore", DeepslateTinOreBlock::new);
	public static final RegistryEntry<Block> BLOCK_OF_RAW_TIN = register("block_of_raw_tin", BlockOfRawTinBlock::new);
	public static final RegistryEntry<Block> BLOCK_OF_TIN = register("block_of_tin", BlockOfTinBlock::new);

	public static final RegistryEntry<Block> COPPER_ROCK_BLOCK = register("copper_rock_block", CopperRockBlockBlock::new);
	public static final RegistryEntry<Block> ANDESITE_ROCK_BLOCK = register("andesite_rock_block", AndesiteRockBlockBlock::new);

	private SurvivalReimaginedModBlocks() {
	}

	private static RegistryEntry<Block> register(String path, Supplier<? extends Block> factory) {
		var id = SurvivalReimaginedMod.asResource(path);
		Block block = Registry.register(BuiltInRegistries.BLOCK, id, factory.get());
		return new RegistryEntry<>(id, block);
	}

	public static void register() {
		// Forces class initialization.
	}
}
