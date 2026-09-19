package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.item.RawTinItem;
import net.mcreator.survivalreimagined.item.RoughTinItem;
import net.mcreator.survivalreimagined.item.TinChunkItem;
import net.mcreator.survivalreimagined.item.TinIngotItem;
import net.mcreator.survivalreimagined.item.TinNuggetItem;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import java.util.function.Supplier;

public final class SurvivalReimaginedModItems {
	public static final RegistryEntry<Item> TIN_ORE = block(SurvivalReimaginedModBlocks.TIN_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_TIN_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_TIN_ORE);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_TIN = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_TIN);
	public static final RegistryEntry<Item> BLOCK_OF_TIN = block(SurvivalReimaginedModBlocks.BLOCK_OF_TIN);

	public static final RegistryEntry<Item> RAW_TIN = register("raw_tin", RawTinItem::new);
	public static final RegistryEntry<Item> TIN_INGOT = register("tin_ingot", TinIngotItem::new);
	public static final RegistryEntry<Item> TIN_NUGGET = register("tin_nugget", TinNuggetItem::new);
	public static final RegistryEntry<Item> TIN_CHUNK = register("tin_chunk", TinChunkItem::new);
	public static final RegistryEntry<Item> ROUGH_TIN = register("rough_tin", RoughTinItem::new);

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
