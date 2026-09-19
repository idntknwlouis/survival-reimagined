package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.item.AndesiteRockItem;
import net.mcreator.survivalreimagined.item.DiamondKnifeItem;
import net.mcreator.survivalreimagined.item.DiamondSawItem;
import net.mcreator.survivalreimagined.item.DiamondHammerItem;
import net.mcreator.survivalreimagined.item.SteelKnifeItem;
import net.mcreator.survivalreimagined.item.SteelSawItem;
import net.mcreator.survivalreimagined.item.SteelHammerItem;
import net.mcreator.survivalreimagined.item.SteelHoeItem;
import net.mcreator.survivalreimagined.item.SteelShovelItem;
import net.mcreator.survivalreimagined.item.SteelAxeItem;
import net.mcreator.survivalreimagined.item.SteelPickaxeItem;
import net.mcreator.survivalreimagined.item.SteelSwordItem;
import net.mcreator.survivalreimagined.item.BronzeKnifeItem;
import net.mcreator.survivalreimagined.item.BronzeSawItem;
import net.mcreator.survivalreimagined.item.BronzeHammerItem;
import net.mcreator.survivalreimagined.item.BronzeHoeItem;
import net.mcreator.survivalreimagined.item.BronzeShovelItem;
import net.mcreator.survivalreimagined.item.BronzeAxeItem;
import net.mcreator.survivalreimagined.item.BronzePickaxeItem;
import net.mcreator.survivalreimagined.item.BronzeSwordItem;
import net.mcreator.survivalreimagined.item.CopperChunkItem;
import net.mcreator.survivalreimagined.item.CopperChiselItem;
import net.mcreator.survivalreimagined.item.FlintToolItem;
import net.mcreator.survivalreimagined.item.StoneHammerItem;
import net.mcreator.survivalreimagined.item.WoodenHammerItem;
import net.mcreator.survivalreimagined.item.WoodenSawItem;
import net.mcreator.survivalreimagined.item.WoodenKnifeItem;
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
	public static final RegistryEntry<Item> OBSIDIAN_ROCK = register("obsidian_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.OBSIDIAN_ROCK::get, "block.stone.place"));
	public static final RegistryEntry<Item> OBSIDIAN_HANDLE = simple("obsidian_handle");
	public static final RegistryEntry<Item> SMALL_OBSIDIAN_HANDLE = simple("small_obsidian_handle");
	public static final RegistryEntry<Item> FLINT_TOOL = register("flint_tool", FlintToolItem::new);
	public static final RegistryEntry<Item> HEMP_FIBER = simple("hemp_fiber");
	public static final RegistryEntry<Item> COPPER_NUGGET = simple("copper_nugget");
	public static final RegistryEntry<Item> COPPER_CHISEL = register("copper_chisel", CopperChiselItem::new);
	public static final RegistryEntry<Item> SMALL_COAL_CHUNK = simple("small_coal_chunk");
	public static final RegistryEntry<Item> SMALL_STICK = simple("small_stick");
	public static final RegistryEntry<Item> WOOD_INGOT = simple("wood_ingot");
	public static final RegistryEntry<Item> WOODEN_HAMMER = register("wooden_hammer", WoodenHammerItem::new);
	public static final RegistryEntry<Item> WOODEN_SAW = register("wooden_saw", WoodenSawItem::new);
	public static final RegistryEntry<Item> WOODEN_KNIFE = register("wooden_knife", WoodenKnifeItem::new);
	public static final RegistryEntry<Item> BRONZE_INGOT = simple("bronze_ingot");
	public static final RegistryEntry<Item> BRONZE_NUGGET = simple("bronze_nugget");
	public static final RegistryEntry<Item> COPPER_HANDLE = simple("copper_handle");
	public static final RegistryEntry<Item> SMALL_COPPER_HANDLE = simple("small_copper_handle");
	public static final RegistryEntry<Item> BRONZE_HANDLE = simple("bronze_handle");
	public static final RegistryEntry<Item> SMALL_BRONZE_HANDLE = simple("small_bronze_handle");
	public static final RegistryEntry<Item> BRONZE_SWORD = register("bronze_sword", BronzeSwordItem::new);
	public static final RegistryEntry<Item> BRONZE_PICKAXE = register("bronze_pickaxe", BronzePickaxeItem::new);
	public static final RegistryEntry<Item> BRONZE_AXE = register("bronze_axe", BronzeAxeItem::new);
	public static final RegistryEntry<Item> BRONZE_SHOVEL = register("bronze_shovel", BronzeShovelItem::new);
	public static final RegistryEntry<Item> BRONZE_HOE = register("bronze_hoe", BronzeHoeItem::new);
	public static final RegistryEntry<Item> BRONZE_HAMMER = register("bronze_hammer", BronzeHammerItem::new);
	public static final RegistryEntry<Item> BRONZE_SAW = register("bronze_saw", BronzeSawItem::new);
	public static final RegistryEntry<Item> BRONZE_KNIFE = register("bronze_knife", BronzeKnifeItem::new);
	public static final RegistryEntry<Item> ROUGH_BRONZE = simple("rough_bronze");
	public static final RegistryEntry<Item> ROUGH_IRON = simple("rough_iron");
	public static final RegistryEntry<Item> ROUGH_GOLD = simple("rough_gold");
	public static final RegistryEntry<Item> ROUGH_COPPER = simple("rough_copper");
	public static final RegistryEntry<Item> ANTHRACITE = simple("anthracite");
	public static final RegistryEntry<Item> SMALL_ANTHRACITE = simple("small_anthracite");
	public static final RegistryEntry<Item> LIGINITE = simple("liginite");
	public static final RegistryEntry<Item> SMALL_LIGINITE = simple("small_liginite");
	public static final RegistryEntry<Item> ROUGH_MANGANESE = simple("rough_manganese");
	public static final RegistryEntry<Item> MANGANESE_INGOT = simple("manganese_ingot");
	public static final RegistryEntry<Item> RAW_MANGANESE = simple("raw_manganese");
	public static final RegistryEntry<Item> ROUGH_STEEL = simple("rough_steel");
	public static final RegistryEntry<Item> ROUGH_PLATED_DIAMOND = simple("rough_plated_diamond");
	public static final RegistryEntry<Item> ROUGH_NETHERITE = simple("rough_netherite");
	public static final RegistryEntry<Item> RAW_TITANIUM = simple("raw_titanium");
	public static final RegistryEntry<Item> RAW_TITANIUM_NUGGET = simple("raw_titanium_nugget");
	public static final RegistryEntry<Item> ROUGH_TITANIUM = simple("rough_titanium");
	public static final RegistryEntry<Item> TITANIUM_INGOT = simple("titanium_ingot");
	public static final RegistryEntry<Item> RAW_URANINITE = simple("raw_uraninite");
	public static final RegistryEntry<Item> RAW_URANINITE_NUGGET = simple("raw_uraninite_nugget");
	public static final RegistryEntry<Item> ROUGH_URANIUM = simple("rough_uranium");
	public static final RegistryEntry<Item> URANIUM_INGOT = simple("uranium_ingot");
	public static final RegistryEntry<Item> ROUGH_TURANITE = simple("rough_turanite");
	public static final RegistryEntry<Item> TURANITE_INGOT = simple("turanite_ingot");
	public static final RegistryEntry<Item> QUICK_LIME = simple("quick_lime");
	public static final RegistryEntry<Item> DARK_CINDER_POWDER = simple("dark_cinder_powder");
	public static final RegistryEntry<Item> DARK_CINDER_COAL = simple("dark_cinder_coal");
	public static final RegistryEntry<Item> SILVER_INGOT = simple("silver_ingot");
	public static final RegistryEntry<Item> WOODEN_RUNE = simple("wooden_rune");
	public static final RegistryEntry<Item> EMPTY_GOLD_RUNE = simple("empty_gold_rune");
	public static final RegistryEntry<Item> EMPTY_SILVER_RUNE = simple("empty_silver_rune");
	public static final RegistryEntry<Item> SAPPHIRE = simple("sapphire");
	public static final RegistryEntry<Item> AMBER = simple("amber");
	public static final RegistryEntry<Item> RUBY = simple("ruby");
	public static final RegistryEntry<Item> SAPPHIRE_SILVER_RUNE = rare("sapphire_silver_rune");
	public static final RegistryEntry<Item> SAPPHIRE_GOLD_RUNE = rare("sapphire_gold_rune");
	public static final RegistryEntry<Item> SILVER_AMBER_RUNE = rare("silver_amber_rune");
	public static final RegistryEntry<Item> GOLD_AMBER_RUNE = rare("gold_amber_rune");
	public static final RegistryEntry<Item> SILVER_DIAMOND_RUNE = rare("silver_diamond_rune");
	public static final RegistryEntry<Item> GOLD_DIAMOND_RUNE = rare("gold_diamond_rune");
	public static final RegistryEntry<Item> SILVER_EMERALD_RUNE = rare("silver_emerald_rune");
	public static final RegistryEntry<Item> GOLD_EMERALD_RUNE = rare("gold_emerald_rune");
	public static final RegistryEntry<Item> SILVER_RUBY_RUNE = rare("silver_ruby_rune");
	public static final RegistryEntry<Item> GOLD_RUBY_RUNE = rare("gold_ruby_rune");
	public static final RegistryEntry<Item> SILVER_LAPIS_RUNE = rare("silver_lapis_rune");
	public static final RegistryEntry<Item> GOLD_LAPIS_RUNE = rare("gold_lapis_rune");
	public static final RegistryEntry<Item> DIAMOND_PLATED_INGOT = simple("diamond_plated_ingot");
	public static final RegistryEntry<Item> STEEL_INGOT = simple("steel_ingot");
	public static final RegistryEntry<Item> BRONZE_SWORD_BLADE = simpleUnstackable("bronze_sword_blade");
	public static final RegistryEntry<Item> BRONZE_PICKAXE_HEAD = simpleUnstackable("bronze_pickaxe_head");
	public static final RegistryEntry<Item> BRONZE_AXE_HEAD = simpleUnstackable("bronze_axe_head");
	public static final RegistryEntry<Item> BRONZE_SHOVEL_HEAD = simpleUnstackable("bronze_shovel_head");
	public static final RegistryEntry<Item> BRONZE_HOE_BLADE = simpleUnstackable("bronze_hoe_blade");
	public static final RegistryEntry<Item> BRONZE_HAMMER_HEAD = simpleUnstackable("bronze_hammer_head");
	public static final RegistryEntry<Item> BRONZE_SAW_BLADE = simpleUnstackable("bronze_saw_blade");
	public static final RegistryEntry<Item> BRONZE_KNIFE_BLADE = simpleUnstackable("bronze_knife_blade");
	public static final RegistryEntry<Item> STEEL_SWORD_BLADE = simpleUnstackable("steel_sword_blade");
	public static final RegistryEntry<Item> STEEL_PICKAXE_HEAD = simpleUnstackable("steel_pickaxe_head");
	public static final RegistryEntry<Item> STEEL_AXE_HEAD = simpleUnstackable("steel_axe_head");
	public static final RegistryEntry<Item> STEEL_SHOVEL_HEAD = simpleUnstackable("steel_shovel_head");
	public static final RegistryEntry<Item> STEEL_HOE_BLADE = simpleUnstackable("steel_hoe_blade");
	public static final RegistryEntry<Item> STEEL_HAMMER_HEAD = simpleUnstackable("steel_hammer_head");
	public static final RegistryEntry<Item> STEEL_SAW_BLADE = simpleUnstackable("steel_saw_blade");
	public static final RegistryEntry<Item> STEEL_KNIFE_BLADE = simpleUnstackable("steel_knife_blade");
	public static final RegistryEntry<Item> STEEL_SWORD = register("steel_sword", SteelSwordItem::new);
	public static final RegistryEntry<Item> STEEL_PICKAXE = register("steel_pickaxe", SteelPickaxeItem::new);
	public static final RegistryEntry<Item> STEEL_AXE = register("steel_axe", SteelAxeItem::new);
	public static final RegistryEntry<Item> STEEL_SHOVEL = register("steel_shovel", SteelShovelItem::new);
	public static final RegistryEntry<Item> STEEL_HOE = register("steel_hoe", SteelHoeItem::new);
	public static final RegistryEntry<Item> STEEL_HAMMER = register("steel_hammer", SteelHammerItem::new);
	public static final RegistryEntry<Item> STEEL_SAW = register("steel_saw", SteelSawItem::new);
	public static final RegistryEntry<Item> STEEL_KNIFE = register("steel_knife", SteelKnifeItem::new);
	public static final RegistryEntry<Item> DIAMOND_SWORD_BLADE = simpleUnstackable("diamond_sword_blade");
	public static final RegistryEntry<Item> DIAMOND_PICKAXE_HEAD = simpleUnstackable("diamond_pickaxe_head");
	public static final RegistryEntry<Item> DIAMOND_AXE_HEAD = simpleUnstackable("diamond_axe_head");
	public static final RegistryEntry<Item> DIAMOND_SHOVEL_HEAD = simpleUnstackable("diamond_shovel_head");
	public static final RegistryEntry<Item> DIAMOND_HOE_BLADE = simpleUnstackable("diamond_hoe_blade");
	public static final RegistryEntry<Item> DIAMOND_HAMMER_HEAD = simpleUnstackable("diamond_hammer_head");
	public static final RegistryEntry<Item> DIAMOND_SAW_BLADE = simpleUnstackable("diamond_saw_blade");
	public static final RegistryEntry<Item> DIAMOND_KNIFE_BLADE = simpleUnstackable("diamond_knife_blade");
	public static final RegistryEntry<Item> DIAMOND_HAMMER = register("diamond_hammer", DiamondHammerItem::new);
	public static final RegistryEntry<Item> DIAMOND_SAW = register("diamond_saw", DiamondSawItem::new);
	public static final RegistryEntry<Item> DIAMOND_KNIFE = register("diamond_knife", DiamondKnifeItem::new);
	public static final RegistryEntry<Item> STONE_HAMMER = register("stone_hammer", StoneHammerItem::new);
	public static final RegistryEntry<Item> STONE_ROCK_BLOC = block(SurvivalReimaginedModBlocks.STONE_ROCK_BLOC);

	public static final RegistryEntry<Item> TIN_ORE = block(SurvivalReimaginedModBlocks.TIN_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_TIN_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_TIN_ORE);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_TIN = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_TIN);
	public static final RegistryEntry<Item> BLOCK_OF_TIN = block(SurvivalReimaginedModBlocks.BLOCK_OF_TIN);
	public static final RegistryEntry<Item> FORGE = block(SurvivalReimaginedModBlocks.FORGE);
	public static final RegistryEntry<Item> METAL_REFINING_TABLE = block(SurvivalReimaginedModBlocks.METAL_REFINING_TABLE);
	public static final RegistryEntry<Item> ANTHRACITE_BLOCK = block(SurvivalReimaginedModBlocks.ANTHRACITE_BLOCK);
	public static final RegistryEntry<Item> LIGINITE_BLOCK = block(SurvivalReimaginedModBlocks.LIGINITE_BLOCK);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_MANGANESE = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_MANGANESE);
	public static final RegistryEntry<Item> BLOCK_OF_MANGANESE = block(SurvivalReimaginedModBlocks.BLOCK_OF_MANGANESE);
	public static final RegistryEntry<Item> BLOCK_OF_STEEL = block(SurvivalReimaginedModBlocks.BLOCK_OF_STEEL);
	public static final RegistryEntry<Item> BLOCK_OF_BRONZE = block(SurvivalReimaginedModBlocks.BLOCK_OF_BRONZE);
	public static final RegistryEntry<Item> TITANIUM_ORE = block(SurvivalReimaginedModBlocks.TITANIUM_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_TITANIUM_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_TITANIUM_ORE);
	public static final RegistryEntry<Item> URANINITE_ORE = block(SurvivalReimaginedModBlocks.URANINITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_URANINITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_URANINITE_ORE);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_TITANIUM = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_TITANIUM);
	public static final RegistryEntry<Item> BLOCK_OF_TITANIUM = block(SurvivalReimaginedModBlocks.BLOCK_OF_TITANIUM);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_URANINITE = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_URANINITE);
	public static final RegistryEntry<Item> BLOCK_OF_URANIUM = block(SurvivalReimaginedModBlocks.BLOCK_OF_URANIUM);
	public static final RegistryEntry<Item> TURANITE_BLOCK = block(SurvivalReimaginedModBlocks.TURANITE_BLOCK);
	public static final RegistryEntry<Item> INGOT_MOLD = block(SurvivalReimaginedModBlocks.INGOT_MOLD);
	public static final RegistryEntry<Item> INGOT_CLAY_MOLD = block(SurvivalReimaginedModBlocks.INGOT_CLAY_MOLD);
	public static final RegistryEntry<Item> CLAY_SWORD_BLADE_MOLD = block(SurvivalReimaginedModBlocks.CLAY_SWORD_BLADE_MOLD);
	public static final RegistryEntry<Item> CLAY_PICKAXE_HEAD_MOLD = block(SurvivalReimaginedModBlocks.CLAY_PICKAXE_HEAD_MOLD);
	public static final RegistryEntry<Item> CLAY_AXE_HEAD_MOLD = block(SurvivalReimaginedModBlocks.CLAY_AXE_HEAD_MOLD);
	public static final RegistryEntry<Item> CLAY_SHOVEL_HEAD_MOLD = block(SurvivalReimaginedModBlocks.CLAY_SHOVEL_HEAD_MOLD);
	public static final RegistryEntry<Item> CLAY_HOE_BLADE_MOLD = block(SurvivalReimaginedModBlocks.CLAY_HOE_BLADE_MOLD);
	public static final RegistryEntry<Item> CLAY_HAMMER_HEAD_MOLD = block(SurvivalReimaginedModBlocks.CLAY_HAMMER_HEAD_MOLD);
	public static final RegistryEntry<Item> CLAY_SAW_BLADE_MOLD = block(SurvivalReimaginedModBlocks.CLAY_SAW_BLADE_MOLD);
	public static final RegistryEntry<Item> CLAY_KNIFE_MOLD = block(SurvivalReimaginedModBlocks.CLAY_KNIFE_MOLD);
	public static final RegistryEntry<Item> SWORD_BLADE_MOLD = block(SurvivalReimaginedModBlocks.SWORD_BLADE_MOLD);
	public static final RegistryEntry<Item> PICKAXE_HEAD_MOLD = block(SurvivalReimaginedModBlocks.PICKAXE_HEAD_MOLD);
	public static final RegistryEntry<Item> AXE_HEAD_MOLD = block(SurvivalReimaginedModBlocks.AXE_HEAD_MOLD);
	public static final RegistryEntry<Item> SHOVEL_HEAD_MOLD = block(SurvivalReimaginedModBlocks.SHOVEL_HEAD_MOLD);
	public static final RegistryEntry<Item> HOE_HEAD_MOLD = block(SurvivalReimaginedModBlocks.HOE_HEAD_MOLD);
	public static final RegistryEntry<Item> HAMMER_HEAD_MOLD = block(SurvivalReimaginedModBlocks.HAMMER_HEAD_MOLD);
	public static final RegistryEntry<Item> SAW_BLADE_MOLD = block(SurvivalReimaginedModBlocks.SAW_BLADE_MOLD);
	public static final RegistryEntry<Item> KNIFE_BLADE_MOLD = block(SurvivalReimaginedModBlocks.KNIFE_BLADE_MOLD);
	public static final RegistryEntry<Item> RUNE_MOLD = block(SurvivalReimaginedModBlocks.RUNE_MOLD);
	public static final RegistryEntry<Item> RUNE_CLAY_MOLD = block(SurvivalReimaginedModBlocks.RUNE_CLAY_MOLD);
	public static final RegistryEntry<Item> WOODEN_PLATE = block(SurvivalReimaginedModBlocks.WOODEN_PLATE);
	public static final RegistryEntry<Item> METAL_PLATE_MOLD = block(SurvivalReimaginedModBlocks.METAL_PLATE_MOLD);
	public static final RegistryEntry<Item> CLAY_METAL_PLATE_MOLD = block(SurvivalReimaginedModBlocks.CLAY_METAL_PLATE_MOLD);
	public static final RegistryEntry<Item> BRONZE_PLATE = block(SurvivalReimaginedModBlocks.BRONZE_PLATE);
	public static final RegistryEntry<Item> STEEL_PLATE = block(SurvivalReimaginedModBlocks.STEEL_PLATE);
	public static final RegistryEntry<Item> DIAMOND_PLATE = block(SurvivalReimaginedModBlocks.DIAMOND_PLATE);
	public static final RegistryEntry<Item> NETHERITE_PLATE = block(SurvivalReimaginedModBlocks.NETHERITE_PLATE);

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

	private static RegistryEntry<Item> simple(String path) {
		return register(path, () -> new Item(new Item.Properties()));
	}

	private static RegistryEntry<Item> simpleUnstackable(String path) {
		return register(path, () -> new Item(new Item.Properties().stacksTo(1)));
	}

	private static RegistryEntry<Item> rare(String path) {
		return register(path, () -> new Item(new Item.Properties().rarity(net.minecraft.world.item.Rarity.RARE)));
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
