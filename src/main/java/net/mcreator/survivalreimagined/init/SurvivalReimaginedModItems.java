package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.item.AndesiteRockItem;
import net.mcreator.survivalreimagined.item.RuneItem;
import net.mcreator.survivalreimagined.item.RubyHeartItem;
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
	public static final RegistryEntry<Item> SHALE = block(SurvivalReimaginedModBlocks.SHALE);
	public static final RegistryEntry<Item> RADIATED_SHALE = block(SurvivalReimaginedModBlocks.RADIATED_SHALE);
	public static final RegistryEntry<Item> SHALE_ROCK = block(SurvivalReimaginedModBlocks.SHALE_ROCK);
	public static final RegistryEntry<Item> POLISHED_SHALE = block(SurvivalReimaginedModBlocks.POLISHED_SHALE);
	public static final RegistryEntry<Item> POLISHED_SHALE_BRICKS = block(SurvivalReimaginedModBlocks.POLISHED_SHALE_BRICKS);
	public static final RegistryEntry<Item> POLISHED_CHISELED_SHALE = block(SurvivalReimaginedModBlocks.POLISHED_CHISELED_SHALE);
	public static final RegistryEntry<Item> SHALE_TITANIUM_ORE = block(SurvivalReimaginedModBlocks.SHALE_TITANIUM_ORE);
	public static final RegistryEntry<Item> SHALE_URANINITE_ORE = block(SurvivalReimaginedModBlocks.SHALE_URANINITE_ORE);
	public static final RegistryEntry<Item> SHALE_STAIRS = block(SurvivalReimaginedModBlocks.SHALE_STAIRS);
	public static final RegistryEntry<Item> SHALE_SLAB = block(SurvivalReimaginedModBlocks.SHALE_SLAB);
	public static final RegistryEntry<Item> SHALE_WALL = block(SurvivalReimaginedModBlocks.SHALE_WALL);
	public static final RegistryEntry<Item> POLISHED_SHALE_STAIRS = block(SurvivalReimaginedModBlocks.POLISHED_SHALE_STAIRS);
	public static final RegistryEntry<Item> POLISHED_SHALE_SLAB = block(SurvivalReimaginedModBlocks.POLISHED_SHALE_SLAB);
	public static final RegistryEntry<Item> POLISHED_SHALE_WALL = block(SurvivalReimaginedModBlocks.POLISHED_SHALE_WALL);
	public static final RegistryEntry<Item> POLISHED_SHALE_BRICK_STAIRS = block(SurvivalReimaginedModBlocks.POLISHED_SHALE_BRICK_STAIRS);
	public static final RegistryEntry<Item> POLISHED_SHALE_BRICK_SLAB = block(SurvivalReimaginedModBlocks.POLISHED_SHALE_BRICK_SLAB);
	public static final RegistryEntry<Item> POLOSHED_SHALE_BRICK_WALL = block(SurvivalReimaginedModBlocks.POLOSHED_SHALE_BRICK_WALL);
	public static final RegistryEntry<Item> OBSIDIAN_HANDLE = simple("obsidian_handle");
	public static final RegistryEntry<Item> SMALL_OBSIDIAN_HANDLE = simple("small_obsidian_handle");
	public static final RegistryEntry<Item> FLINT_TOOL = register("flint_tool", FlintToolItem::new);
	public static final RegistryEntry<Item> RYE = simple("rye");
	public static final RegistryEntry<Item> RYE_SEEDS = cropSeed("rye_seeds", SurvivalReimaginedModBlocks.RYE_SEEDS);
	public static final RegistryEntry<Item> SPELT = simple("spelt");
	public static final RegistryEntry<Item> SPELT_SEEDS = cropSeed("spelt_seeds", SurvivalReimaginedModBlocks.SPELT_SEEDS);
	public static final RegistryEntry<Item> WHEAT_FLOUR = simple("wheat_flour");
	public static final RegistryEntry<Item> RYE_FLOUR = simple("rye_flour");
	public static final RegistryEntry<Item> SPELT_FLOUR = simple("spelt_flour");
	public static final RegistryEntry<Item> HEMP_LEAF = simple("hemp_leaf");
	public static final RegistryEntry<Item> HEMP_SEEDS = cropSeed("hemp_seeds", SurvivalReimaginedModBlocks.HEMP);
	public static final RegistryEntry<Item> WHEAT_SEEDS = cropSeed("wheat_seeds", SurvivalReimaginedModBlocks.WHEAT_CROP);
	public static final RegistryEntry<Item> POTATO = plantFood("potato", SurvivalReimaginedModBlocks.POTATOES, 1, 0.6f);
	public static final RegistryEntry<Item> STRAWBERRY = plantFood("strawberry", SurvivalReimaginedModBlocks.STRAWBERRY_PLANT, 4, 0.3f);
	public static final RegistryEntry<Item> RASPBERRY = plantFood("raspberry", SurvivalReimaginedModBlocks.RASPBERRY_PLANT, 2, 0.2f);
	public static final RegistryEntry<Item> CORN = simple("corn");
	public static final RegistryEntry<Item> CORN_SEEDS = cropSeed("corn_seeds", SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM);
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
	public static final RegistryEntry<Item> HEMATITE_CHUNK = simple("hematite_chunk");
	public static final RegistryEntry<Item> HEMATITE_NUGGET = simple("hematite_nugget");
	public static final RegistryEntry<Item> MAGNETITE_CHUNK = simple("magnetite_chunk");
	public static final RegistryEntry<Item> MAGNETITE_NUGGET = simple("magnetite_nugget");
	public static final RegistryEntry<Item> CALAVERITE = simple("calaverite");
	public static final RegistryEntry<Item> CALAVERITE_NUGGET = simple("calaverite_nugget");
	public static final RegistryEntry<Item> PYROLUSITE = simple("pyrolusite");
	public static final RegistryEntry<Item> PYROLUSITE_NUGGET = simple("pyrolusite_nugget");
	public static final RegistryEntry<Item> URANOPHANE = simple("uranophane");
	public static final RegistryEntry<Item> URANOPHANE_NUGGET = simple("uranophane_nugget");
	public static final RegistryEntry<Item> ILMENITE = simple("ilmenite");
	public static final RegistryEntry<Item> ILMENITE_NUGGET = simple("ilmenite_nugget");
	public static final RegistryEntry<Item> ANTHRACITE = simple("anthracite");
	public static final RegistryEntry<Item> SMALL_ANTHRACITE = simple("small_anthracite");
	public static final RegistryEntry<Item> LIGINITE = simple("liginite");
	public static final RegistryEntry<Item> SMALL_LIGINITE = simple("small_liginite");
	public static final RegistryEntry<Item> ROUGH_MANGANESE = simple("rough_manganese");
	public static final RegistryEntry<Item> MANGANESE_INGOT = simple("manganese_ingot");
	public static final RegistryEntry<Item> MANGANESE_NUGGET = simple("manganese_nugget");
	public static final RegistryEntry<Item> RAW_MANGANESE = simple("raw_manganese");
	public static final RegistryEntry<Item> MANGANESE_CHUNK = simple("manganese_chunk");
	public static final RegistryEntry<Item> ROUGH_STEEL = simple("rough_steel");
	public static final RegistryEntry<Item> ROUGH_PLATED_DIAMOND = simple("rough_plated_diamond");
	public static final RegistryEntry<Item> ROUGH_NETHERITE = simple("rough_netherite");
	public static final RegistryEntry<Item> RAW_TITANIUM = simple("raw_titanium");
	public static final RegistryEntry<Item> RAW_TITANIUM_NUGGET = simple("raw_titanium_nugget");
	public static final RegistryEntry<Item> ROUGH_TITANIUM = simple("rough_titanium");
	public static final RegistryEntry<Item> TITANIUM_INGOT = simple("titanium_ingot");
	public static final RegistryEntry<Item> TITANIUM_NUGGET = simple("titanium_nugget");
	public static final RegistryEntry<Item> RAW_URANINITE = simple("raw_uraninite");
	public static final RegistryEntry<Item> URANIUM_DUST = simple("uranium_dust");
	public static final RegistryEntry<Item> REDSTONE_CHARGED_URANIUM_DUST = simple("redstone_charged_uranium_dust");
	public static final RegistryEntry<Item> GOLD_ROD = simple("gold_rod");
	public static final RegistryEntry<Item> RAW_URANINITE_NUGGET = simple("raw_uraninite_nugget");
	public static final RegistryEntry<Item> ROUGH_URANIUM = simple("rough_uranium");
	public static final RegistryEntry<Item> URANIUM_INGOT = simple("uranium_ingot");
	public static final RegistryEntry<Item> URANIUM_NUGGET = simple("uranium_nugget");
	public static final RegistryEntry<Item> URANIUM_ROD = block(SurvivalReimaginedModBlocks.URANIUM_ROD);
	public static final RegistryEntry<Item> ROUGH_TURANITE = simple("rough_turanite");
	public static final RegistryEntry<Item> TURANITE_INGOT = simple("turanite_ingot");
	public static final RegistryEntry<Item> QUICK_LIME = simple("quick_lime");
	public static final RegistryEntry<Item> DARK_CINDER_POWDER = simple("dark_cinder_powder");
	public static final RegistryEntry<Item> DARK_CINDER_COAL = simple("dark_cinder_coal");
	public static final RegistryEntry<Item> DARK_CINDER = block(SurvivalReimaginedModBlocks.DARK_CINDER);
	public static final RegistryEntry<Item> SILVER_INGOT = simple("silver_ingot");
	public static final RegistryEntry<Item> RAW_SILVER = simple("raw_silver");
	public static final RegistryEntry<Item> RAW_SILVER_NUGGET = simple("raw_silver_nugget");
	public static final RegistryEntry<Item> ROUGH_SILVER = simple("rough_silver");
	public static final RegistryEntry<Item> SILVER_NUGGET = simple("silver_nugget");
	public static final RegistryEntry<Item> ARGENTITE = simple("argentite");
	public static final RegistryEntry<Item> ARGENTITE_NUGGET = simple("argentite_nugget");
	public static final RegistryEntry<Item> ROUGH_DIAMOND = simple("rough_diamond");
	public static final RegistryEntry<Item> ROUGH_EMERALD = simple("rough_emerald");
	public static final RegistryEntry<Item> ROUGH_SAPPHIRE = simple("rough_sapphire");
	public static final RegistryEntry<Item> ROUGH_RUBY = simple("rough_ruby");
	public static final RegistryEntry<Item> ROUGH_AMBER = simple("rough_amber");
	public static final RegistryEntry<Item> REACTOR_ROD = simpleUnstackable("reactor_rod");
	public static final RegistryEntry<Item> DEPLETED_REACTOR_ROD = simpleUnstackable("depleted_reactor_rod");
	public static final RegistryEntry<Item> ADVANCED_REACTOR_ROD = simpleUnstackable("advanced_reactor_rod");
	public static final RegistryEntry<Item> DRAINED_ADVANCED_REACTOR_ROD = simpleUnstackable("drained_advanced_reactor_rod");
	public static final RegistryEntry<Item> FUEL_UPGRADE = simpleUnstackable("fuel_upgrade");
	public static final RegistryEntry<Item> FUEL_UPGRADE_MKII = simpleUnstackable("fuel_upgrade_mkii");
	public static final RegistryEntry<Item> YIELD_UPGRADE = simpleUnstackable("yield_upgrade");
	public static final RegistryEntry<Item> YIELD_UPGRADE_MKII = simpleUnstackable("yield_upgrade_mkii");
	public static final RegistryEntry<Item> YIELD_UPGRADE_MKIII = simpleUnstackable("yield_upgrade_mkiii");
	public static final RegistryEntry<Item> EFFICIENCY_UPGRADE = simpleUnstackable("efficiency_upgrade");
	public static final RegistryEntry<Item> BLOCK_PACKAGING_UPGRADE = simpleUnstackable("block_packaging_upgrade");
	public static final RegistryEntry<Item> WOODEN_RUNE = simple("wooden_rune");
	public static final RegistryEntry<Item> EMPTY_GOLD_RUNE = simple("empty_gold_rune");
	public static final RegistryEntry<Item> EMPTY_SILVER_RUNE = simple("empty_silver_rune");
	public static final RegistryEntry<Item> HEART = simple("heart");
	public static final RegistryEntry<Item> SAPPHIRE = simple("sapphire");
	public static final RegistryEntry<Item> AMBER = simple("amber");
	public static final RegistryEntry<Item> RUBY = simple("ruby");
	public static final RegistryEntry<Item> RUBY_HEART_SHARD = simple("ruby_heart_shard");
	public static final RegistryEntry<Item> GLASS_SHARD = simple("glass_shard");
	public static final RegistryEntry<Item> RUBY_HEART = register("ruby_heart", RubyHeartItem::new);
	public static final RegistryEntry<Item> SAPPHIRE_SILVER_RUNE = rune("sapphire_silver_rune", "Silver", ChatFormatting.WHITE, "Sapphire", ChatFormatting.BLUE);
	public static final RegistryEntry<Item> SAPPHIRE_GOLD_RUNE = rune("sapphire_gold_rune", "Gold", ChatFormatting.GOLD, "Sapphire", ChatFormatting.BLUE);
	public static final RegistryEntry<Item> SILVER_AMBER_RUNE = rune("silver_amber_rune", "Silver", ChatFormatting.WHITE, "Amber", ChatFormatting.GOLD);
	public static final RegistryEntry<Item> GOLD_AMBER_RUNE = rune("gold_amber_rune", "Gold", ChatFormatting.GOLD, "Amber", ChatFormatting.GOLD);
	public static final RegistryEntry<Item> SILVER_DIAMOND_RUNE = rune("silver_diamond_rune", "Silver", ChatFormatting.WHITE, "Diamond", ChatFormatting.AQUA);
	public static final RegistryEntry<Item> GOLD_DIAMOND_RUNE = rune("gold_diamond_rune", "Gold", ChatFormatting.GOLD, "Diamond", ChatFormatting.AQUA);
	public static final RegistryEntry<Item> SILVER_EMERALD_RUNE = rune("silver_emerald_rune", "Silver", ChatFormatting.WHITE, "Emerald", ChatFormatting.DARK_GREEN);
	public static final RegistryEntry<Item> GOLD_EMERALD_RUNE = rune("gold_emerald_rune", "Gold", ChatFormatting.GOLD, "Emerald", ChatFormatting.DARK_GREEN);
	public static final RegistryEntry<Item> SILVER_RUBY_RUNE = rune("silver_ruby_rune", "Silver", ChatFormatting.WHITE, "Ruby", ChatFormatting.DARK_RED);
	public static final RegistryEntry<Item> GOLD_RUBY_RUNE = rune("gold_ruby_rune", "Gold", ChatFormatting.GOLD, "Ruby", ChatFormatting.DARK_RED);
	public static final RegistryEntry<Item> SILVER_LAPIS_RUNE = rune("silver_lapis_rune", "Silver", ChatFormatting.WHITE, "Lapis", ChatFormatting.DARK_BLUE);
	public static final RegistryEntry<Item> GOLD_LAPIS_RUNE = rune("gold_lapis_rune", "Gold", ChatFormatting.GOLD, "Lapis", ChatFormatting.DARK_BLUE);
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
	public static final RegistryEntry<Item> CASSITERITE_ORE = block(SurvivalReimaginedModBlocks.CASSITERITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_CASSITERITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_CASSITERITE_ORE);
	public static final RegistryEntry<Item> RAW_CASSITERITE_BLOCK = block(SurvivalReimaginedModBlocks.RAW_CASSITERITE_BLOCK);
	public static final RegistryEntry<Item> CASSITERITE = simple("cassiterite");
	public static final RegistryEntry<Item> CASSITERITE_NUGGET = simple("cassiterite_nugget");
	public static final RegistryEntry<Item> DEEPSLATE_TIN_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_TIN_ORE);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_TIN = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_TIN);
	public static final RegistryEntry<Item> BLOCK_OF_TIN = block(SurvivalReimaginedModBlocks.BLOCK_OF_TIN);
	public static final RegistryEntry<Item> FORGE = block(SurvivalReimaginedModBlocks.FORGE);
	public static final RegistryEntry<Item> METAL_REFINING_TABLE = block(SurvivalReimaginedModBlocks.METAL_REFINING_TABLE);
	public static final RegistryEntry<Item> MINERAL_PROCESSING_TABLE = block(SurvivalReimaginedModBlocks.MINERAL_PROCESSING_TABLE);
	public static final RegistryEntry<Item> MILLSTONE = block(SurvivalReimaginedModBlocks.MILLSTONE);
	public static final RegistryEntry<Item> RUNE_MAGIC_INFUSER = block(SurvivalReimaginedModBlocks.RUNE_MAGIC_INFUSER);
	public static final RegistryEntry<Item> ADVANCED_ALLOY_FORGE = block(SurvivalReimaginedModBlocks.ADVANCED_ALLOY_FORGE);
	public static final RegistryEntry<Item> ANTHRACITE_BLOCK = block(SurvivalReimaginedModBlocks.ANTHRACITE_BLOCK);
	public static final RegistryEntry<Item> LIGINITE_BLOCK = block(SurvivalReimaginedModBlocks.LIGINITE_BLOCK);
	public static final RegistryEntry<Item> MANGANESE_ORE = block(SurvivalReimaginedModBlocks.MANGANESE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_MANGANESE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_MANGANESE_ORE);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_MANGANESE = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_MANGANESE);
	public static final RegistryEntry<Item> MANGANITE_ORE = block(SurvivalReimaginedModBlocks.MANGANITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_MANGANITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_MANGANITE_ORE);
	public static final RegistryEntry<Item> MANGANITE_BLOCK = block(SurvivalReimaginedModBlocks.MANGANITE_BLOCK);
	public static final RegistryEntry<Item> MANGANITE = simple("manganite");
	public static final RegistryEntry<Item> MANGANITE_NUGGET = simple("manganite_nugget");
	public static final RegistryEntry<Item> BLOCK_OF_MANGANESE = block(SurvivalReimaginedModBlocks.BLOCK_OF_MANGANESE);
	public static final RegistryEntry<Item> BLOCK_OF_STEEL = block(SurvivalReimaginedModBlocks.BLOCK_OF_STEEL);
	public static final RegistryEntry<Item> BLOCK_OF_BRONZE = block(SurvivalReimaginedModBlocks.BLOCK_OF_BRONZE);
	public static final RegistryEntry<Item> TITANIUM_ORE = block(SurvivalReimaginedModBlocks.TITANIUM_ORE);
	public static final RegistryEntry<Item> SILVER_ORE = block(SurvivalReimaginedModBlocks.SILVER_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_SILVER_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_SILVER_ORE);
	public static final RegistryEntry<Item> ARGENTITE_ORE = block(SurvivalReimaginedModBlocks.ARGENTITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_ARGENTITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_ARGENTITE_ORE);
	public static final RegistryEntry<Item> SAPPHIRE_ORE = block(SurvivalReimaginedModBlocks.SAPPHIRE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_SAPPHIRE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_SAPPHIRE_ORE);
	public static final RegistryEntry<Item> RUBY_ORE = block(SurvivalReimaginedModBlocks.RUBY_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_RUBY_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_RUBY_ORE);
	public static final RegistryEntry<Item> AMBER_ORE = block(SurvivalReimaginedModBlocks.AMBER_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_AMBER_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_AMBER_ORE);
	public static final RegistryEntry<Item> BASALT_DIAMOND_ORE = block(SurvivalReimaginedModBlocks.BASALT_DIAMOND_ORE);
	public static final RegistryEntry<Item> BASALT_EMERALD_ORE = block(SurvivalReimaginedModBlocks.BASALT_EMERALD_ORE);
	public static final RegistryEntry<Item> BASALT_LAPIS_ORE = block(SurvivalReimaginedModBlocks.BASALT_LAPIS_ORE);
	public static final RegistryEntry<Item> BASALT_ARGENTITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_ARGENTITE_ORE);
	public static final RegistryEntry<Item> BASALT_URANINITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_URANINITE_ORE);
	public static final RegistryEntry<Item> HEMATITE_ORE = block(SurvivalReimaginedModBlocks.HEMATITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_HEMATITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_HEMATITE_ORE);
	public static final RegistryEntry<Item> MAGNETITE_ORE = block(SurvivalReimaginedModBlocks.MAGNETITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_MAGNETITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_MAGNETITE_ORE);
	public static final RegistryEntry<Item> CALAVERITE_ORE = block(SurvivalReimaginedModBlocks.CALAVERITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_CALAVERITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_CALAVERITE_ORE);
	public static final RegistryEntry<Item> PYROLUSITE_ORE = block(SurvivalReimaginedModBlocks.PYROLUSITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_PYROLUSITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_PYROLUSITE_ORE);
	public static final RegistryEntry<Item> URANOPHANE_ORE = block(SurvivalReimaginedModBlocks.URANOPHANE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_URANOPHANE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_URANOPHANE_ORE);
	public static final RegistryEntry<Item> ILMENITE_ORE = block(SurvivalReimaginedModBlocks.ILMENITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_ILMENITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_ILMENITE_ORE);
	public static final RegistryEntry<Item> ANTHRACITE_ORE = block(SurvivalReimaginedModBlocks.ANTHRACITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_ANTHRACITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_ANTHRACITE_ORE);
	public static final RegistryEntry<Item> LIGINITE_ORE = block(SurvivalReimaginedModBlocks.LIGINITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_LIGINITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_LIGINITE_ORE);
	public static final RegistryEntry<Item> HEMATITE_BLOCK = block(SurvivalReimaginedModBlocks.HEMATITE_BLOCK);
	public static final RegistryEntry<Item> MAGNETITE_BLOCK = block(SurvivalReimaginedModBlocks.MAGNETITE_BLOCK);
	public static final RegistryEntry<Item> CALAVERITE_BLOCK = block(SurvivalReimaginedModBlocks.CALAVERITE_BLOCK);
	public static final RegistryEntry<Item> PYROLUSITE_BLOCK = block(SurvivalReimaginedModBlocks.PYROLUSITE_BLOCK);
	public static final RegistryEntry<Item> URANOPHANE_BLOCK = block(SurvivalReimaginedModBlocks.URANOPHANE_BLOCK);
	public static final RegistryEntry<Item> ILMENITE_BLOCK = block(SurvivalReimaginedModBlocks.ILMENITE_BLOCK);
	public static final RegistryEntry<Item> SHALE_URANOPHANE_ORE = block(SurvivalReimaginedModBlocks.SHALE_URANOPHANE_ORE);
	public static final RegistryEntry<Item> BASALT_HEMATITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_HEMATITE_ORE);
	public static final RegistryEntry<Item> BASALT_MAGNETITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_MAGNETITE_ORE);
	public static final RegistryEntry<Item> BASALT_CALAVERITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_CALAVERITE_ORE);
	public static final RegistryEntry<Item> BASALT_PYROLUSITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_PYROLUSITE_ORE);
	public static final RegistryEntry<Item> BASALT_URANOPHANE_ORE = block(SurvivalReimaginedModBlocks.BASALT_URANOPHANE_ORE);
	public static final RegistryEntry<Item> BASALT_ILMENITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_ILMENITE_ORE);
	public static final RegistryEntry<Item> BASALT_ANTHRACITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_ANTHRACITE_ORE);
	public static final RegistryEntry<Item> BASALT_LIGINITE_ORE = block(SurvivalReimaginedModBlocks.BASALT_LIGINITE_ORE);
	public static final RegistryEntry<Item> BASALT_POINTED_STONE = block(SurvivalReimaginedModBlocks.BASALT_POINTED_STONE);
	public static final RegistryEntry<Item> KIMBERLITE = block(SurvivalReimaginedModBlocks.KIMBERLITE);
	public static final RegistryEntry<Item> KIMBERLITE_POINTED_STONE = block(SurvivalReimaginedModBlocks.KIMBERLITE_POINTED_STONE);
	public static final RegistryEntry<Item> KIMBERLITE_ROCK = block(SurvivalReimaginedModBlocks.KIMBERLITE_ROCK);
	public static final RegistryEntry<Item> KIMBERLITE_SAPPHIRE_ORE = block(SurvivalReimaginedModBlocks.KIMBERLITE_SAPPHIRE_ORE);
	public static final RegistryEntry<Item> KIMBERLITE_DIAMOND_ORE = block(SurvivalReimaginedModBlocks.KIMBERLITE_DIAMOND_ORE);
	public static final RegistryEntry<Item> KIMBERLITE_EMERALD_ORE = block(SurvivalReimaginedModBlocks.KIMBERLITE_EMERALD_ORE);
	public static final RegistryEntry<Item> KIMBERLITE_RUBY_ORE = block(SurvivalReimaginedModBlocks.KIMBERLITE_RUBY_ORE);
	public static final RegistryEntry<Item> KIMBERLITE_LAPIS_ORE = block(SurvivalReimaginedModBlocks.KIMBERLITE_LAPIS_ORE);
	public static final RegistryEntry<Item> KIMBERLITE_AMBER_ORE = block(SurvivalReimaginedModBlocks.KIMBERLITE_AMBER_ORE);
	public static final RegistryEntry<Item> BASALT_SAPPHIRE_ORE = block(SurvivalReimaginedModBlocks.BASALT_SAPPHIRE_ORE);
	public static final RegistryEntry<Item> BASALT_RUBY_ORE = block(SurvivalReimaginedModBlocks.BASALT_RUBY_ORE);
	public static final RegistryEntry<Item> BASALT_AMBER_ORE = block(SurvivalReimaginedModBlocks.BASALT_AMBER_ORE);
	public static final RegistryEntry<Item> RAW_SILVER_BLOCK = block(SurvivalReimaginedModBlocks.RAW_SILVER_BLOCK);
	public static final RegistryEntry<Item> SILVER_BLOCK = block(SurvivalReimaginedModBlocks.SILVER_BLOCK);
	public static final RegistryEntry<Item> ARGENTITE_BLOCK = block(SurvivalReimaginedModBlocks.ARGENTITE_BLOCK);
	public static final RegistryEntry<Item> SAPPHIRE_BLOCK = block(SurvivalReimaginedModBlocks.SAPPHIRE_BLOCK);
	public static final RegistryEntry<Item> BLOCK_OF_RUBY = block(SurvivalReimaginedModBlocks.BLOCK_OF_RUBY);
	public static final RegistryEntry<Item> BLOCK_OF_AMBER = block(SurvivalReimaginedModBlocks.BLOCK_OF_AMBER);
	public static final RegistryEntry<Item> DEEPSLATE_TITANIUM_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_TITANIUM_ORE);
	public static final RegistryEntry<Item> URANINITE_ORE = block(SurvivalReimaginedModBlocks.URANINITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_URANINITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_URANINITE_ORE);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_TITANIUM = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_TITANIUM);
	public static final RegistryEntry<Item> BLOCK_OF_TITANIUM = block(SurvivalReimaginedModBlocks.BLOCK_OF_TITANIUM);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_URANINITE = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_URANINITE);
	public static final RegistryEntry<Item> BLOCK_OF_URANIUM = block(SurvivalReimaginedModBlocks.BLOCK_OF_URANIUM);
	public static final RegistryEntry<Item> TURANITE_BLOCK = block(SurvivalReimaginedModBlocks.TURANITE_BLOCK);
	public static final RegistryEntry<Item> PLATED_DIAMOND_BLOCK = block(SurvivalReimaginedModBlocks.PLATED_DIAMOND_BLOCK);
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

	private static RegistryEntry<Item> seed(String path, RegistryEntry<? extends Block> crop) {
		return register(path, () -> new ItemNameBlockItem(crop.get(), new Item.Properties()));
	}

	private static RegistryEntry<Item> plantFood(String path, RegistryEntry<? extends Block> crop, int nutrition, float saturation) {
		return register(path, () -> new ItemNameBlockItem(crop.get(), new Item.Properties()
				.food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build())));
	}

	private static RegistryEntry<Item> cropSeed(String path, RegistryEntry<? extends Block> crop) {
		return register(path, () -> new ItemNameBlockItem(crop.get(), new Item.Properties()));
	}

	private static RegistryEntry<Item> simpleUnstackable(String path) {
		return register(path, () -> new Item(new Item.Properties().stacksTo(1)));
	}

	private static RegistryEntry<Item> rune(String path, String runeType, ChatFormatting runeTypeColor, String crystal, ChatFormatting crystalColor) {
		return register(path, () -> new RuneItem(runeType, runeTypeColor, crystal, crystalColor));
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
