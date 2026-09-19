package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.item.AndesiteRockItem;
import net.mcreator.survivalreimagined.item.CopperChunkItem;
import net.mcreator.survivalreimagined.item.RawTinItem;
import net.mcreator.survivalreimagined.item.RoughTinItem;
import net.mcreator.survivalreimagined.item.StoneRockItem;
import net.mcreator.survivalreimagined.item.SurfaceRockItem;
import net.mcreator.survivalreimagined.item.TinChunkItem;
import net.mcreator.survivalreimagined.item.TinIngotItem;
import net.mcreator.survivalreimagined.item.TinNuggetItem;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import java.util.function.Supplier;

public final class SurvivalReimaginedModItems {
	public static final RegistryEntry<Item> FLINTBLOCK = block(SurvivalReimaginedModBlocks.FLINTBLOCK);
	public static final RegistryEntry<Item> STONE_ROCK = register("stone_rock", StoneRockItem::new);
	public static final RegistryEntry<Item> STONE_ROCK_BLOC = block(SurvivalReimaginedModBlocks.STONE_ROCK_BLOC);

	public static final RegistryEntry<Item> TIN_ORE = block(SurvivalReimaginedModBlocks.TIN_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_TIN_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_TIN_ORE);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_TIN = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_TIN);
	public static final RegistryEntry<Item> BLOCK_OF_TIN = block(SurvivalReimaginedModBlocks.BLOCK_OF_TIN);

	public static final RegistryEntry<Item> RAW_TIN = register("raw_tin", RawTinItem::new);
	public static final RegistryEntry<Item> TIN_INGOT = register("tin_ingot", TinIngotItem::new);
	public static final RegistryEntry<Item> TIN_NUGGET = register("tin_nugget", TinNuggetItem::new);
	public static final RegistryEntry<Item> TIN_CHUNK = register("tin_chunk", TinChunkItem::new);
	public static final RegistryEntry<Item> ROUGH_TIN = register("rough_tin", RoughTinItem::new);

	public static final RegistryEntry<Item> COPPER_CHUNK = register("copper_chunk", CopperChunkItem::new);
	public static final RegistryEntry<Item> COPPER_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.COPPER_ROCK_BLOCK);

	public static final RegistryEntry<Item> ANDESITE_ROCK = register("andesite_rock", AndesiteRockItem::new);
	public static final RegistryEntry<Item> ANDESITE_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.ANDESITE_ROCK_BLOCK);

	public static final RegistryEntry<Item> GRANITE_ROCK = register("granite_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.GRANITE_ROCK_BLOCK::get, "block.stone.break"));
	public static final RegistryEntry<Item> GRANITE_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.GRANITE_ROCK_BLOCK);
	public static final RegistryEntry<Item> DIORITE_ROCK = register("diorite_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.DIORITE_ROCK_B_LOCK::get, "block.stone.break"));
	public static final RegistryEntry<Item> DIORITE_ROCK_B_LOCK = block(SurvivalReimaginedModBlocks.DIORITE_ROCK_B_LOCK);
	public static final RegistryEntry<Item> DRIPSTONE_ROCK = register("dripstone_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.DRIPSTONE_ROCK_BLOCK::get, "block.dripstone_block.place"));
	public static final RegistryEntry<Item> DRIPSTONE_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.DRIPSTONE_ROCK_BLOCK);
	public static final RegistryEntry<Item> CALCITE_ROCK = register("calcite_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.CALCITE_ROCK_BLOCK::get, "block.calcite.place"));
	public static final RegistryEntry<Item> CALCITE_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.CALCITE_ROCK_BLOCK);
	public static final RegistryEntry<Item> TUFF_ROCK = register("tuff_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.TUFF_ROCK_BLOCK::get, "block.polished_tuff.place"));
	public static final RegistryEntry<Item> TUFF_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.TUFF_ROCK_BLOCK);
	public static final RegistryEntry<Item> MOSSY_STONE_ROCK = register("mossy_stone_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.MOSSY_STONE_ROCK_BLOCK::get, "block.stone.break"));
	public static final RegistryEntry<Item> MOSSY_STONE_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.MOSSY_STONE_ROCK_BLOCK);
	public static final RegistryEntry<Item> NETHERRACK_ROCK = register("netherrack_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.NETHERRACK_ROCK_BLOCK::get, "block.netherrack.place"));
	public static final RegistryEntry<Item> NETHERRACK_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.NETHERRACK_ROCK_BLOCK);
	public static final RegistryEntry<Item> END_STONE_ROCK = register("end_stone_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.END_STONE_ROCK_BLOCK::get, "block.stone.break"));
	public static final RegistryEntry<Item> END_STONE_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.END_STONE_ROCK_BLOCK);
	public static final RegistryEntry<Item> BLACKSTONE_ROCK = register("blackstone_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.BLACKSTONE_ROCK_BLOCK::get, "block.stone.break"));
	public static final RegistryEntry<Item> BLACKSTONE_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.BLACKSTONE_ROCK_BLOCK);
	public static final RegistryEntry<Item> BASALT_ROCK = register("basalt_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.BASALT_ROCK_BLOCK::get, "block.basalt.place"));
	public static final RegistryEntry<Item> BASALT_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.BASALT_ROCK_BLOCK);
	public static final RegistryEntry<Item> DEEPSLATE_ROCK = register("deepslate_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.DEEPSLATE_ROCK_BLOCK::get, "block.deepslate.place"));
	public static final RegistryEntry<Item> DEEPSLATE_ROCK_BLOCK = block(SurvivalReimaginedModBlocks.DEEPSLATE_ROCK_BLOCK);

	private SurvivalReimaginedModItems() {
	}

	private static RegistryEntry<Item> register(String path, Supplier<? extends Item> factory) {
		var id = SurvivalReimaginedMod.asResource(path);
		Item item = Registry.register(BuiltInRegistries.ITEM, id, factory.get());
		return new RegistryEntry<>(id, item);
	}

	private static RegistryEntry<Item> block(RegistryEntry<? extends Block> block) {
		var id = block.getId();
		Item item = Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block.get(), new Item.Properties()));
		return new RegistryEntry<>(id, item);
	}

	public static void register() {
		// Forces class initialization.
	}
}
