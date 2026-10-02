package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.block.CarcassPartBlock;
import net.mcreator.survivalreimagined.block.*;
import net.mcreator.survivalreimagined.block.fruit.FruitBlock;
import net.mcreator.survivalreimagined.block.fruit.FruitDropCondition;
import net.mcreator.survivalreimagined.block.fruit.FruitTypeConfig;
import net.mcreator.survivalreimagined.block.fruit.FruitValidPlacement;
import net.mcreator.survivalreimagined.util.SignInjection;
import net.mcreator.survivalreimagined.world.worldgen.SurvivalReimaginedModTreeGrowers;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.AndesiteRockBlockBlock;
import net.mcreator.survivalreimagined.block.AdvancedAlloyForgeBlock;
import net.mcreator.survivalreimagined.block.BlockOfRawTinBlock;
import net.mcreator.survivalreimagined.block.BlockOfTinBlock;
import net.mcreator.survivalreimagined.block.CopperRockBlockBlock;
import net.mcreator.survivalreimagined.block.DeepslateTinOreBlock;
import net.mcreator.survivalreimagined.block.FlintblockBlock;
import net.mcreator.survivalreimagined.block.ForgeBlock;
import net.mcreator.survivalreimagined.block.GeologySegmentBlock;
import net.mcreator.survivalreimagined.block.IngotMoldBlock;
import net.mcreator.survivalreimagined.block.MetalRefiningTableBlock;
import net.mcreator.survivalreimagined.block.MineralProcessingTableBlock;
import net.mcreator.survivalreimagined.block.MillstoneBlock;
import net.mcreator.survivalreimagined.block.RuneMagicInfuserBlock;
import net.mcreator.survivalreimagined.block.ToolMoldBlock;
import net.mcreator.survivalreimagined.block.ClayMoldBlock;
import net.mcreator.survivalreimagined.block.PlateBlock;
import net.mcreator.survivalreimagined.block.StoneRockBlocBlock;
import net.mcreator.survivalreimagined.block.ShaleBlock;
import net.mcreator.survivalreimagined.block.ShaleRockBlock;
import net.mcreator.survivalreimagined.block.CornUpperBlock;
import net.mcreator.survivalreimagined.block.CornCropBlock;
import net.mcreator.survivalreimagined.block.CampfireBlock;
import net.mcreator.survivalreimagined.block.BlockOfCharcoalBlock;
import net.mcreator.survivalreimagined.block.BerryPlantBlock;
import net.mcreator.survivalreimagined.block.SimpleAgeCropBlock;
import net.mcreator.survivalreimagined.block.SurfaceRockBlock;
import net.mcreator.survivalreimagined.block.TinOreBlock;
import net.mcreator.survivalreimagined.block.UraniumRodBlock;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import java.util.function.Supplier;

public final class SurvivalReimaginedModBlocks {
	public static final RegistryEntry<Block> FLINTBLOCK = register("flintblock", FlintblockBlock::new);
	public static final RegistryEntry<Block> COW_CARCASS = register("cow_carcass", () -> new CarcassBlock(CarcassBlock.Species.COW));
	public static final RegistryEntry<Block> COW_HEAD = register("cow_head", () -> new CarcassPartBlock(Block.box(4, 0, 5, 12, 8, 11)));
	public static final RegistryEntry<Block> COW_LEG = register("cow_leg", () -> new CarcassPartBlock(Block.box(6, 0, 6, 10, 12, 10)));
	public static final RegistryEntry<Block> PIG_CARCASS = register("pig_carcass", () -> new CarcassBlock(CarcassBlock.Species.PIG));
	public static final RegistryEntry<Block> PIG_HEAD = register("pig_head", () -> new CarcassPartBlock(Shapes.or(Block.box(4, 0, 4, 12, 8, 12), Block.box(6, 1, 3, 10, 4, 4))));
	public static final RegistryEntry<Block> PIG_LEG = register("pig_leg", () -> new CarcassPartBlock(Block.box(6, 0, 6, 10, 6, 10)));
	public static final RegistryEntry<Block> SHEEP_CARCASS = register("sheep_carcass", () -> new CarcassBlock(CarcassBlock.Species.SHEEP));
	public static final RegistryEntry<Block> SHEEP_HEAD = register("sheep_head", () -> new CarcassPartBlock(Block.box(5, 0, 4, 11, 6, 12)));
	public static final RegistryEntry<Block> SHEEP_LEG = register("sheep_leg", () -> new CarcassPartBlock(Block.box(6, 0, 6, 10, 12, 10)));
	public static final RegistryEntry<Block> GOAT_CARCASS = register("goat_carcass", () -> new CarcassBlock(CarcassBlock.Species.GOAT));
	public static final RegistryEntry<Block> GOAT_HEAD = register("goat_head", () -> new CarcassPartBlock(Block.box(5.5, 0, 3, 10.5, 7, 13)));
	public static final RegistryEntry<Block> GOAT_LEG = register("goat_leg", () -> new CarcassPartBlock(Block.box(6.5, 0, 6.5, 9.5, 10, 9.5)));
	public static final RegistryEntry<Block> CHICKEN_CARCASS = register("chicken_carcass", () -> new CarcassBlock(CarcassBlock.Species.CHICKEN));
	public static final RegistryEntry<Block> STONE_ROCK_BLOC = register("stone_rock_bloc", StoneRockBlocBlock::new);
	public static final RegistryEntry<Block> OBSIDIAN_ROCK = register("obsidian_rock", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.OBSIDIAN_ROCK.get()));
	public static final RegistryEntry<Block> SHALE = register("shale", ShaleBlock::new);
	public static final RegistryEntry<Block> RADIATED_SHALE = register("radiated_shale", () -> new Block(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> RADIANT_LOG = register("radiant_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2f)));
	public static final RegistryEntry<Block> RADIATED_LEAVES = register("radiated_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().sound(SoundType.AZALEA_LEAVES).strength(0.2f).noOcclusion()));
	public static final RegistryEntry<Block> RADIATED_MOSS = register("radiated_moss", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.MOSS).strength(0.1f)));
	public static final RegistryEntry<Block> RADIATED_TALL_GRASS = register("radiated_tall_grass", () -> new BiomePlantBlock(Block.box(0, 0, 0, 16, 11, 16), SoundType.MOSS, 0));
	public static final RegistryEntry<Block> RADIATED_ORCHID = register("radiated_orchid", () -> new BiomePlantBlock(Block.box(3, 0, 3, 13, 11, 13), SoundType.GRASS, 8));
	public static final RegistryEntry<Block> WISTERIA_LOG = register("wisteria_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2f)));
	public static final RegistryEntry<Block> WISTERIA_LEAVES = register("wisteria_leaves", WisteriaLeavesBlock::new);
	public static final RegistryEntry<Block> FLOWING_WISTERIA_LEAVES = register("flowing_wisteria_leaves", WisteriaLeavesBlock::new);
	public static final RegistryEntry<Block> WISTERIA_SPIDER_LILY = register("wisteria_spider_lily", () -> new BiomePlantBlock(Block.box(3, 0, 3, 10, 11, 10), SoundType.GRASS, 0));
	public static final RegistryEntry<Block> WISTERIA_SAPLING = register("wisteria_sapling", () -> new SaplingBlock(SurvivalReimaginedModTreeGrowers.WISTERIA, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_SAPLING).instabreak()));
	public static final RegistryEntry<Block> WISTERIA_LEAF_LITTER = register("wisteria_leaf_litter", WisteriaLeafLitterBlock::new);
	public static final RegistryEntry<Block> WISTERIA_FLOWER_UPPER = register("wisteria_flower_upper", () -> new WisteriaHangingFlowerBlock(true));
	public static final RegistryEntry<Block> WISTERIA_FLOWER_LOWER = register("wisteria_flower_lower", () -> new WisteriaHangingFlowerBlock(false));
	public static final RegistryEntry<Block> SMALL_PALM_LOG = register("small_palm_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(0.2f).noOcclusion()));
	public static final RegistryEntry<Block> PALM_LOG = register("palm_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2f)));
	public static final RegistryEntry<Block> PALM_LOG_BARKED_TOP = register("palm_log_barked_top", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2f).noOcclusion()));
	public static final RegistryEntry<Block> PALM_CROWN = register("palm_crown", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2f).noOcclusion()));
	public static final RegistryEntry<Block> PALM_LEAVES = register("palm_leaves", PalmLeavesBlock::new);
	public static final RegistryEntry<Block> ALOE_VERA = register("aloe_vera", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.CROP).strength(0.2f).noCollission().noOcclusion()));
	public static final RegistryEntry<Block> SHALE_ROCK = register("shale_rock", ShaleRockBlock::new);
	private static final IntegerProperty RYE_AGE = IntegerProperty.create("age", 0, 6);
	private static final IntegerProperty SPELT_AGE = IntegerProperty.create("age", 0, 6);
	private static final IntegerProperty HEMP_AGE = IntegerProperty.create("age", 0, 3);
	private static final IntegerProperty WHEAT_AGE = IntegerProperty.create("age", 0, 7);
	private static final IntegerProperty POTATO_AGE = IntegerProperty.create("age", 0, 3);
	private static final IntegerProperty CARROT_AGE = IntegerProperty.create("age", 0,3);
	private static final IntegerProperty BEETROOT_AGE = IntegerProperty.create("age", 0, 3);
	private static final IntegerProperty WILD_WHEAT_AGE = IntegerProperty.create("age", 0, 7);
	private static final IntegerProperty WILD_POTATO_AGE = IntegerProperty.create("age", 0, 3);
	private static final IntegerProperty WILD_BEETROOT_AGE = IntegerProperty.create("age", 0, 3);
	public static final RegistryEntry<Block> RYE_SEEDS = register("rye_seeds", () -> new SimpleAgeCropBlock(RYE_AGE, 6));
	public static final RegistryEntry<Block> SPELT_SEEDS = register("spelt_seeds", () -> new SimpleAgeCropBlock(SPELT_AGE, 6));
	public static final RegistryEntry<Block> HEMP = register("hemp", () -> new SimpleAgeCropBlock(HEMP_AGE, 3));
	public static final RegistryEntry<Block> WHEAT_CROP = register("wheat_crop", () -> new SimpleAgeCropBlock(WHEAT_AGE, 7));
	public static final RegistryEntry<Block> POTATOES = register("potatoes", () -> new SimpleAgeCropBlock(POTATO_AGE, 3));
	public static final RegistryEntry<Block> BEETROOT = register("beetroot", () -> new SimpleAgeCropBlock(BEETROOT_AGE, 3));
	public static final RegistryEntry<Block> CARROT = register("carrot", () -> new SimpleAgeCropBlock(CARROT_AGE, 3));
	public static final RegistryEntry<Block> WILD_WHEAT = register("wild_wheat", () -> new SimpleAgeCropBlock(WILD_WHEAT_AGE, 7, false));
	public static final RegistryEntry<Block> WILD_POTATOES = register("wild_potatoes", () -> new SimpleAgeCropBlock(WILD_POTATO_AGE, 3, false));
	public static final RegistryEntry<Block> WILD_BEETROOT = register("wild_beetroot", () -> new SimpleAgeCropBlock(WILD_BEETROOT_AGE, 3, false));
	public static final RegistryEntry<Block> STRAWBERRY_PLANT = register("strawberry_plant", () -> new BerryPlantBlock(() -> SurvivalReimaginedModItems.STRAWBERRY.get()));
	public static final RegistryEntry<Block> RASPBERRY_PLANT = register("raspberry_plant", () -> new BerryPlantBlock(() -> SurvivalReimaginedModItems.RASPBERRY.get()));
	public static final RegistryEntry<Block> CORN_STALK_MIDDLE = register("corn_stalk_middle",
			() -> new CornUpperBlock(() -> BuiltInRegistries.BLOCK.get(SurvivalReimaginedMod.asResource("corn_stalk_bottom")), 7));
	public static final RegistryEntry<Block> CORN_STALK_TOP = register("corn_stalk_top",
			() -> new CornUpperBlock(() -> BuiltInRegistries.BLOCK.get(SurvivalReimaginedMod.asResource("corn_stalk_middle")), 4));
	public static final RegistryEntry<Block> CORN_STALK_BOTTOM = register("corn_stalk_bottom",
			() -> new CornCropBlock(
					() -> BuiltInRegistries.BLOCK.get(SurvivalReimaginedMod.asResource("corn_stalk_middle")),
					() -> BuiltInRegistries.BLOCK.get(SurvivalReimaginedMod.asResource("corn_stalk_top"))));
	private static final IntegerProperty WILD_RYE_AGE = IntegerProperty.create("age", 0, 6);
	private static final IntegerProperty WILD_SPELT_AGE = IntegerProperty.create("age", 0, 6);
	private static final IntegerProperty WILD_CARROT_AGE = IntegerProperty.create("age", 0, 3);
	public static final RegistryEntry<Block> WILD_RYE = register("wild_rye", () -> new SimpleAgeCropBlock(WILD_RYE_AGE, 6, false));
	public static final RegistryEntry<Block> WILD_SPELT = register("wild_spelt", () -> new SimpleAgeCropBlock(WILD_SPELT_AGE, 6, false));
	public static final RegistryEntry<Block> WILD_CARROT = register("wild_carrot", () -> new SimpleAgeCropBlock(WILD_CARROT_AGE, 3, false));
	public static final RegistryEntry<Block> POLISHED_SHALE = register("polished_shale", () -> new Block(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> POLISHED_SHALE_BRICKS = register("polished_shale_bricks", () -> new Block(BlockBehaviour.Properties.of().strength(1.5f, 6f)));
	public static final RegistryEntry<Block> POLISHED_CHISELED_SHALE = register("polished_chiseled_shale", () -> new Block(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SHALE_TITANIUM_ORE = register("shale_titanium_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SHALE_URANINITE_ORE = register("shale_uraninite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SHALE_STAIRS = register("shale_stairs", () -> new StairBlock(SHALE.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SHALE_SLAB = register("shale_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SHALE_WALL = register("shale_wall", () -> new WallBlock(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> POLISHED_SHALE_STAIRS = register("polished_shale_stairs", () -> new StairBlock(POLISHED_SHALE.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> POLISHED_SHALE_SLAB = register("polished_shale_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> POLISHED_SHALE_WALL = register("polished_shale_wall", () -> new WallBlock(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> POLISHED_SHALE_BRICK_STAIRS = register("polished_shale_brick_stairs", () -> new StairBlock(POLISHED_SHALE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> POLISHED_SHALE_BRICK_SLAB = register("polished_shale_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> POLOSHED_SHALE_BRICK_WALL = register("poloshed_shale_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.of().strength(1.5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> TIN_ORE = register("tin_ore", TinOreBlock::new);
	public static final RegistryEntry<Block> CASSITERITE_ORE = register("cassiterite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_CASSITERITE_ORE = register("deepslate_cassiterite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> RAW_CASSITERITE_BLOCK = register("raw_cassiterite_block", () -> new Block(BlockBehaviour.Properties.of().strength(6f, 5f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_TIN_ORE = register("deepslate_tin_ore", DeepslateTinOreBlock::new);
	public static final RegistryEntry<Block> MANGANESE_ORE = register("manganese_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_MANGANESE_ORE = register("deepslate_manganese_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_RAW_TIN = register("block_of_raw_tin", BlockOfRawTinBlock::new);
	public static final RegistryEntry<Block> BLOCK_OF_TIN = register("block_of_tin", BlockOfTinBlock::new);
	public static final RegistryEntry<Block> FORGE = register("forge", ForgeBlock::new);
	public static final RegistryEntry<Block> CAMPFIRE = register("campfire", CampfireBlock::new);
	public static final RegistryEntry<Block> BLOCK_OF_CHARCOAL = register("block_of_charcoal", BlockOfCharcoalBlock::new);
	public static final RegistryEntry<Block> METAL_REFINING_TABLE = register("metal_refining_table", MetalRefiningTableBlock::new);
	public static final RegistryEntry<Block> MINERAL_PROCESSING_TABLE = register("mineral_processing_table", MineralProcessingTableBlock::new);
	public static final RegistryEntry<Block> MILLSTONE = register("millstone", MillstoneBlock::new);
	public static final RegistryEntry<Block> RUNE_MAGIC_INFUSER = register("rune_magic_infuser", RuneMagicInfuserBlock::new);
	public static final RegistryEntry<Block> ADVANCED_ALLOY_FORGE = register("advanced_alloy_forge", AdvancedAlloyForgeBlock::new);
	public static final RegistryEntry<Block> ANTHRACITE_BLOCK = register("anthracite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> LIGINITE_BLOCK = register("liginite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DARK_CINDER = register("dark_cinder", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.SAND).strength(0.3f)));
	public static final RegistryEntry<Block> BLOCK_OF_RAW_MANGANESE = register("block_of_raw_manganese", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> MANGANITE_ORE = register("manganite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_MANGANITE_ORE = register("deepslate_manganite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> MANGANITE_BLOCK = register("manganite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_MANGANESE = register("block_of_manganese", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_STEEL = register("block_of_steel", () -> new Block(BlockBehaviour.Properties.of().sound(SurvivalReimaginedModSoundTypes.STEEL).strength(6f, 7f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_BRONZE = register("block_of_bronze", () -> new Block(BlockBehaviour.Properties.of().sound(SurvivalReimaginedModSoundTypes.STEEL).strength(4f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> TITANIUM_ORE = register("titanium_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_TITANIUM_ORE = register("deepslate_titanium_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> URANINITE_ORE = register("uraninite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> URANIUM_ROD = register("uranium_rod", UraniumRodBlock::new);
	public static final RegistryEntry<Block> SILVER_ORE = register("silver_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> ARGENTITE_ORE = register("argentite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_ARGENTITE_ORE = register("deepslate_argentite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SAPPHIRE_ORE = register("sapphire_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_SAPPHIRE_ORE = register("deepslate_sapphire_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> RUBY_ORE = register("ruby_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_RUBY_ORE = register("deepslate_ruby_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> AMBER_ORE = register("amber_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_AMBER_ORE = register("deepslate_amber_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_DIAMOND_ORE = register("basalt_diamond_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_EMERALD_ORE = register("basalt_emerald_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_LAPIS_ORE = register("basalt_lapis_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_ARGENTITE_ORE = register("basalt_argentite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_URANINITE_ORE = register("basalt_uraninite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> HEMATITE_ORE = register("hematite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_HEMATITE_ORE = register("deepslate_hematite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> MAGNETITE_ORE = register("magnetite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_MAGNETITE_ORE = register("deepslate_magnetite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> CALAVERITE_ORE = register("calaverite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_CALAVERITE_ORE = register("deepslate_calaverite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> PYROLUSITE_ORE = register("pyrolusite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_PYROLUSITE_ORE = register("deepslate_pyrolusite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> URANOPHANE_ORE = register("uranophane_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_URANOPHANE_ORE = register("deepslate_uranophane_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> ILMENITE_ORE = register("ilmenite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_ILMENITE_ORE = register("deepslate_ilmenite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> ANTHRACITE_ORE = register("anthracite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_ANTHRACITE_ORE = register("deepslate_anthracite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> LIGINITE_ORE = register("liginite_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_LIGINITE_ORE = register("deepslate_liginite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> HEMATITE_BLOCK = register("hematite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> MAGNETITE_BLOCK = register("magnetite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> CALAVERITE_BLOCK = register("calaverite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> PYROLUSITE_BLOCK = register("pyrolusite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> URANOPHANE_BLOCK = register("uranophane_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> ILMENITE_BLOCK = register("ilmenite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SHALE_URANOPHANE_ORE = register("shale_uranophane_ore", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_HEMATITE_ORE = register("basalt_hematite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_MAGNETITE_ORE = register("basalt_magnetite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_CALAVERITE_ORE = register("basalt_calaverite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_PYROLUSITE_ORE = register("basalt_pyrolusite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_URANOPHANE_ORE = register("basalt_uranophane_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_ILMENITE_ORE = register("basalt_ilmenite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_ANTHRACITE_ORE = register("basalt_anthracite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_LIGINITE_ORE = register("basalt_liginite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_STALAGMITE_BASE = register("basalt_stalagmite_base", () -> new GeologySegmentBlock(SoundType.BASALT, GeologySegmentBlock.Segment.BASE, "basalt_stalagmite", true));
	public static final RegistryEntry<Block> BASALT_STALAGMITE_MIDDLE = register("basalt_stalagmite_middle", () -> new GeologySegmentBlock(SoundType.BASALT, GeologySegmentBlock.Segment.MIDDLE, "basalt_stalagmite", true));
	public static final RegistryEntry<Block> BASALT_STALAGMITE_TOP = register("basalt_stalagmite_top", () -> new GeologySegmentBlock(SoundType.BASALT, GeologySegmentBlock.Segment.TIP, "basalt_stalagmite", true));
	public static final RegistryEntry<Block> BASALT_STALAGTITE_BASE = register("basalt_stalagtite_base", () -> new GeologySegmentBlock(SoundType.BASALT, GeologySegmentBlock.Segment.BASE, "basalt_stalagtite", false));
	public static final RegistryEntry<Block> BASALT_STALAGTITE_MIDDLE = register("basalt_stalagtite_middle", () -> new GeologySegmentBlock(SoundType.BASALT, GeologySegmentBlock.Segment.MIDDLE, "basalt_stalagtite", false));
	public static final RegistryEntry<Block> BASALT_STALAGTITE_TIP = register("basalt_stalagtite_tip", () -> new GeologySegmentBlock(SoundType.BASALT, GeologySegmentBlock.Segment.TIP, "basalt_stalagtite", false));
	public static final RegistryEntry<Block> SHALE_STALAGMITE_BASE = register("shale_stalagmite_base", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.BASE, "shale_stalagmite", true));
	public static final RegistryEntry<Block> SHALE_STALAGMITE_MIDDLE = register("shale_stalagmite_middle", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.MIDDLE, "shale_stalagmite", true));
	public static final RegistryEntry<Block> SHALE_STALAGMITE_TOP = register("shale_stalagmite_top", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.TIP, "shale_stalagmite", true));
	public static final RegistryEntry<Block> SHALE_STALAGTITE_BASE = register("shale_stalagtite_base", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.BASE, "shale_stalagtite", false));
	public static final RegistryEntry<Block> SHALE_STALAGTITE_MIDDLE = register("shale_stalagtite_middle", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.MIDDLE, "shale_stalagtite", false));
	public static final RegistryEntry<Block> SHALE_STALAGTITE_TIP = register("shale_stalagtite_tip", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.TIP, "shale_stalagtite", false));
	public static final RegistryEntry<Block> KIMBERLITE = register("kimberlite", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> KIMBERLITE_STALAGMITE_BASE = register("kimberlite_stalagmite_base", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.BASE, "kimberlite_stalagmite", true));
	public static final RegistryEntry<Block> KIMBERLITE_STALAGMITE_MIDDLE = register("kimberlite_stalagmite_middle", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.MIDDLE, "kimberlite_stalagmite", true));
	public static final RegistryEntry<Block> KIMBERLITE_STALAGMITE_TOP = register("kimberlite_stalagmite_top", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.TIP, "kimberlite_stalagmite", true));
	public static final RegistryEntry<Block> KIMBERLITE_STALAGTITE_BASE = register("kimberlite_stalagtite_base", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.BASE, "kimberlite_stalagtite", false));
	public static final RegistryEntry<Block> KIMBERLITE_STALAGTITE_MIDDLE = register("kimberlite_stalagtite_middle", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.MIDDLE, "kimberlite_stalagtite", false));
	public static final RegistryEntry<Block> KIMBERLITE_STALAGTITE_TIP = register("kimberlite_stalagtite_tip", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.TIP, "kimberlite_stalagtite", false));
	public static final RegistryEntry<Block> STONE_STALAGMITE_BASE = register("stone_stalagmite_base", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.BASE, "stone_stalagmite", true));
	public static final RegistryEntry<Block> STONE_STALAGMITE_MIDDLE = register("stone_stalagmite_middle", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.MIDDLE, "stone_stalagmite", true));
	public static final RegistryEntry<Block> STONE_STALAGMITE_TOP = register("stone_stalagmite_top", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.TIP, "stone_stalagmite", true));
	public static final RegistryEntry<Block> STONE_STALAGTITE_BASE = register("stone_stalagtite_base", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.BASE, "stone_stalagtite", false));
	public static final RegistryEntry<Block> STONE_STALAGTITE_MIDDLE = register("stone_stalagtite_middle", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.MIDDLE, "stone_stalagtite", false));
	public static final RegistryEntry<Block> STONE_STALAGTITE_TIP = register("stone_stalagtite_tip", () -> new GeologySegmentBlock(SoundType.STONE, GeologySegmentBlock.Segment.TIP, "stone_stalagtite", false));
	public static final RegistryEntry<Block> DEEPSLATE_STALAGMITE_BASE = register("deepslate_stalagmite_base", () -> new GeologySegmentBlock(SoundType.DEEPSLATE, GeologySegmentBlock.Segment.BASE, "deepslate_stalagmite", true));
	public static final RegistryEntry<Block> DEEPSLATE_STALAGMITE_MIDDLE = register("deepslate_stalagmite_middle", () -> new GeologySegmentBlock(SoundType.DEEPSLATE, GeologySegmentBlock.Segment.MIDDLE, "deepslate_stalagmite", true));
	public static final RegistryEntry<Block> DEEPSLATE_STALAGMITE_TOP = register("deepslate_stalagmite_top", () -> new GeologySegmentBlock(SoundType.DEEPSLATE, GeologySegmentBlock.Segment.TIP, "deepslate_stalagmite", true));
	public static final RegistryEntry<Block> DEEPSLATE_STALAGTITE_BASE = register("deepslate_stalagtite_base", () -> new GeologySegmentBlock(SoundType.DEEPSLATE, GeologySegmentBlock.Segment.BASE, "deepslate_stalagtite", false));
	public static final RegistryEntry<Block> DEEPSLATE_STALAGTITE_MIDDLE = register("deepslate_stalagtite_middle", () -> new GeologySegmentBlock(SoundType.DEEPSLATE, GeologySegmentBlock.Segment.MIDDLE, "deepslate_stalagtite", false));
	public static final RegistryEntry<Block> DEEPSLATE_STALAGTITE_TIP = register("deepslate_stalagtite_tip", () -> new GeologySegmentBlock(SoundType.DEEPSLATE, GeologySegmentBlock.Segment.TIP, "deepslate_stalagtite", false));
	public static final RegistryEntry<Block> KIMBERLITE_ROCK = register("kimberlite_rock", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.KIMBERLITE_ROCK.get()));
	public static final RegistryEntry<Block> KIMBERLITE_SAPPHIRE_ORE = register("kimberlite_sapphire_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> KIMBERLITE_DIAMOND_ORE = register("kimberlite_diamond_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> KIMBERLITE_EMERALD_ORE = register("kimberlite_emerald_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> KIMBERLITE_RUBY_ORE = register("kimberlite_ruby_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> KIMBERLITE_LAPIS_ORE = register("kimberlite_lapis_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> KIMBERLITE_AMBER_ORE = register("kimberlite_amber_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_SAPPHIRE_ORE = register("basalt_sapphire_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_RUBY_ORE = register("basalt_ruby_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BASALT_AMBER_ORE = register("basalt_amber_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.BASALT).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> RAW_SILVER_BLOCK = register("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SILVER_BLOCK = register("silver_block", () -> new Block(BlockBehaviour.Properties.of().sound(SurvivalReimaginedModSoundTypes.STEEL).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> ARGENTITE_BLOCK = register("argentite_block", () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SAPPHIRE_BLOCK = register("sapphire_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_RUBY = register("block_of_ruby", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5f, 6f)));
	public static final RegistryEntry<Block> BLOCK_OF_AMBER = register("block_of_amber", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5f, 6f)));
	public static final RegistryEntry<Block> DEEPSLATE_URANINITE_ORE = register("deepslate_uraninite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_RAW_TITANIUM = register("block_of_raw_titanium", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_TITANIUM = register("block_of_titanium", () -> new Block(BlockBehaviour.Properties.of().sound(SurvivalReimaginedModSoundTypes.STEEL).strength(3f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_RAW_URANINITE = register("block_of_raw_uraninite", () -> new Block(BlockBehaviour.Properties.of().strength(3f, 6f)));
	public static final RegistryEntry<Block> BLOCK_OF_URANIUM = register("block_of_uranium", () -> new Block(BlockBehaviour.Properties.of().sound(SurvivalReimaginedModSoundTypes.STEEL).strength(3f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> TURANITE_BLOCK = register("turanite_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK).strength(6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> PLATED_DIAMOND_BLOCK = register("plated_diamond_block", () -> new Block(BlockBehaviour.Properties.of().sound(SurvivalReimaginedModSoundTypes.STEEL).strength(6f, 8f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> INGOT_MOLD = register("ingot_mold", IngotMoldBlock::new);
	public static final RegistryEntry<Block> SWORD_BLADE_MOLD = register("sword_blade_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> PICKAXE_HEAD_MOLD = register("pickaxe_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> AXE_HEAD_MOLD = register("axe_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> SHOVEL_HEAD_MOLD = register("shovel_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> HOE_HEAD_MOLD = register("hoe_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> HAMMER_HEAD_MOLD = register("hammer_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> SAW_BLADE_MOLD = register("saw_blade_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> KNIFE_BLADE_MOLD = register("knife_blade_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> RUNE_MOLD = register("rune_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> RUNE_CLAY_MOLD = register("rune_clay_mold", () -> new ClayMoldBlock(() -> RUNE_MOLD.get()));
	public static final RegistryEntry<Block> WOODEN_PLATE = register("wooden_plate", () -> new PlateBlock(SoundType.WOOD, 1.0F));
	public static final RegistryEntry<Block> METAL_PLATE_MOLD = register("metal_plate_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> BRONZE_PLATE = register("bronze_plate", () -> new PlateBlock(SurvivalReimaginedModSoundTypes.STEEL, 2.5F));
	public static final RegistryEntry<Block> STEEL_PLATE = register("steel_plate", () -> new PlateBlock(SurvivalReimaginedModSoundTypes.STEEL, 3.5F));
	public static final RegistryEntry<Block> DIAMOND_PLATE = register("diamond_plate", () -> new PlateBlock(SurvivalReimaginedModSoundTypes.STEEL, 4.0F));
	public static final RegistryEntry<Block> NETHERITE_PLATE = register("netherite_plate", () -> new PlateBlock(SoundType.NETHERITE_BLOCK, 5.0F));
	public static final RegistryEntry<Block> CLAY_METAL_PLATE_MOLD = register("clay_metal_plate_mold", () -> new ClayMoldBlock(() -> METAL_PLATE_MOLD.get(), true));
	public static final RegistryEntry<Block> CRUCIBLE = register("crucible", Crucible::new);
	public static final RegistryEntry<Block> INGOT_CLAY_MOLD = register("ingot_clay_mold", () -> new ClayMoldBlock(() -> INGOT_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_SWORD_BLADE_MOLD = register("clay_sword_blade_mold", () -> new ClayMoldBlock(() -> SWORD_BLADE_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_PICKAXE_HEAD_MOLD = register("clay_pickaxe_head_mold", () -> new ClayMoldBlock(() -> PICKAXE_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_AXE_HEAD_MOLD = register("clay_axe_head_mold", () -> new ClayMoldBlock(() -> AXE_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_SHOVEL_HEAD_MOLD = register("clay_shovel_head_mold", () -> new ClayMoldBlock(() -> SHOVEL_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_HOE_BLADE_MOLD = register("clay_hoe_blade_mold", () -> new ClayMoldBlock(() -> HOE_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_HAMMER_HEAD_MOLD = register("clay_hammer_head_mold", () -> new ClayMoldBlock(() -> HAMMER_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_SAW_BLADE_MOLD = register("clay_saw_blade_mold", () -> new ClayMoldBlock(() -> SAW_BLADE_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_KNIFE_MOLD = register("clay_knife_mold", () -> new ClayMoldBlock(() -> KNIFE_BLADE_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_CRUCIBLE = register("clay_crucible", () -> new ClayCrucible(() -> CRUCIBLE.get()));
	public static final RegistryEntry<Block> COPPER_ROCK_BLOCK = register("copper_rock_block", CopperRockBlockBlock::new);
	public static final RegistryEntry<Block> ANDESITE_ROCK_BLOCK = register("andesite_rock_block", AndesiteRockBlockBlock::new);
	public static final RegistryEntry<Block> GRANITE_ROCK_BLOCK = register("granite_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.GRANITE_ROCK.get()));
	public static final RegistryEntry<Block> DIORITE_ROCK_B_LOCK = register("diorite_rock_b_lock", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.DIORITE_ROCK.get()));
	public static final RegistryEntry<Block> DRIPSTONE_ROCK_BLOCK = register("dripstone_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.DRIPSTONE_ROCK.get()));
	public static final RegistryEntry<Block> CALCITE_ROCK_BLOCK = register("calcite_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.CALCITE_ROCK.get()));
	public static final RegistryEntry<Block> TUFF_ROCK_BLOCK = register("tuff_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.TUFF_ROCK.get()));
	public static final RegistryEntry<Block> MOSSY_STONE_ROCK_BLOCK = register("mossy_stone_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.MOSSY_STONE_ROCK.get()));
	public static final RegistryEntry<Block> NETHERRACK_ROCK_BLOCK = register("netherrack_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.NETHERRACK_ROCK.get()));
	public static final RegistryEntry<Block> END_STONE_ROCK_BLOCK = register("end_stone_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.END_STONE_ROCK.get()));
	public static final RegistryEntry<Block> BLACKSTONE_ROCK_BLOCK = register("blackstone_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.BLACKSTONE_ROCK.get()));
	public static final RegistryEntry<Block> BASALT_ROCK_BLOCK = register("basalt_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.BASALT_ROCK.get()));
	public static final RegistryEntry<Block> DEEPSLATE_ROCK_BLOCK = register("deepslate_rock_block", () -> new SurfaceRockBlock(() -> SurvivalReimaginedModItems.DEEPSLATE_ROCK.get()));
	public static final RegistryEntry<Block> THIN_RADIATED_VINES = register("thin_radiated_vines", ThinRadiatedVinesBlock::new);
	public static final RegistryEntry<Block> THIN_RADIATED_VINE_BASE = register("thin_radiated_vine_base", ThinRadiatedVineBaseBlock::new);
	public static final RegistryEntry<Block> THICK_RADIATED_VINES = register("thick_radiated_vines", ThickRadiatedVinesBlock::new);
	public static final RegistryEntry<Block> THICK_RADIATED_VINES_BASE = register("thick_radiated_vines_base", ThickRadiatedVinesBaseBlock::new);
	public static final RegistryEntry<Block> SAND_SALT_DESPOSIT = register("sand_salt_deposit", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.SAND).strength(0.7f, 0.7f)));
	public static final RegistryEntry<Block> BLOCK_OF_RAW_REDSTONE = register("block_of_raw_redstone", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3,4).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> EMBEDDED_OBSIDIAN = register("embedded_obsidian", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3,3).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> APPLE_TREE_SAPLING = register("apple_tree_sapling", () -> new SaplingBlock(SurvivalReimaginedModTreeGrowers.APPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).instabreak()));

	private static FruitTypeConfig AppleConfig() {
		return new FruitTypeConfig(
				"apple", () -> SurvivalReimaginedModBlockEntities.FRUIT_BLOCK_ENTITY,
				(state, age) -> age == 1
						? Shapes.or(Block.box(5.5, 10, 5.5, 10.5, 15, 10.5), Block.box(7.5, 15, 7.5, 8.5, 16, 8.5))
						: Shapes.or(Block.box(6.5, 12, 6.5, 9.5, 15, 9.5), Block.box(7.5, 15, 7.5, 8.5, 16, 8.5)),
				(world, pos) -> FruitValidPlacement.execute(world, pos.getX(), pos.getY(), pos.getZ()),
				(world, pos, player, state) -> FruitDropCondition.execute(world, pos, state, () -> Items.APPLE,1 ),
				true, false
		);
	}
	private static FruitTypeConfig MandarinConfig() {
		return new FruitTypeConfig(
				"mandarin", () -> SurvivalReimaginedModBlockEntities.FRUIT_BLOCK_ENTITY,
				(state, age) -> {
					if (age == 1) return Shapes.or(Block.box(6.5,11,6.5,9.5,14,9.5), Block.box(7.5, 14, 7.5, 8.5, 16, 8.5));
					if (age == 2) return Shapes.or(Block.box(6,10,6, 10, 14, 10), Block.box(7.5, 14, 7.5, 8.5, 16, 8.5));
					return Shapes.or(Block.box(6.5, 11, 6.5, 9.5, 14, 9.5), Block.box(7.5, 14, 7.5, 8.5, 16, 8.5));
				},
				(world, pos) -> FruitValidPlacement.execute(world, pos.getX(), pos.getY(), pos.getZ()),
				(world, pos, player, state) -> FruitDropCondition.execute(world, pos, state, SurvivalReimaginedModItems.MANDARIN,2 ),
				true, false
		);
	}
	private static FruitTypeConfig BananaConfig() {
		return new FruitTypeConfig(
				"banana", () -> SurvivalReimaginedModBlockEntities.FRUIT_BLOCK_ENTITY,
				(state, age) -> {
					Direction facing = state.hasProperty(FruitBlock.FACING) ? state.getValue(FruitBlock.FACING) : Direction.NORTH;
					return switch (facing) {
						case NORTH -> Block.box(4,4,14,12,12,20);
						case EAST -> Block.box(-4,4,4,2,12,12);
						case WEST -> Block.box(14,4,4,20,12,12);
						default -> Block.box(4,4,-4,12,12,2);
					};
				},
				null,
				(world, pos, player, state) -> FruitDropCondition.execute(world, pos, state, SurvivalReimaginedModItems.BANANA_FRUIT, 2),
				false, true
		);
	}
	private static FruitTypeConfig CherriesConfig() {
		return new FruitTypeConfig(
				"red_cherries", () -> SurvivalReimaginedModBlockEntities.FRUIT_BLOCK_ENTITY,
				(state, age) -> {
					if (age == 2) return Shapes.or(Block.box(5.5,10,4,10.5,16,12));
					return Shapes.or(Block.box(5.5,10,4,10.5,16,12));
				},
				(world, pos) -> FruitValidPlacement.execute(world, pos.getX(), pos.getY(), pos.getZ()),
				(world, pos, player, state) -> FruitDropCondition.execute(world, pos, state, SurvivalReimaginedModItems.RED_CHERRIES,2 ),
				true, false
		);

	}



	public static final RegistryEntry<Block> APPLE_FRUIT = register("apple", () -> new FruitBlock(AppleConfig()));
	public static final RegistryEntry<Block> MANDARIN_FRUIT = register("mandarin_fruit", () -> new FruitBlock(MandarinConfig()));
	public static final RegistryEntry<Block> BANANA_FRUIT = register("banana_cluster", () -> new FruitBlock(BananaConfig()));
	public static final RegistryEntry<Block> RED_CHERRIES_FRUIT = register("cherries_fruit", () -> new FruitBlock(CherriesConfig()));

	public static final RegistryEntry<Block> FRUITING_MANDARIN_LEAVES = register("fruiting_mandarin_leaves", () -> new FruitBearingLeavesBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_LEAVES).strength(0.2f).noOcclusion(), () -> SurvivalReimaginedModBlocks.MANDARIN_FRUIT.get(), 0.05));
	public static final RegistryEntry<Block> APPLE_OAK_LEAVES = register("apple_oak_leaves", () -> new FruitBearingLeavesBlock(BlockBehaviour.Properties.of().sound(SoundType.GRASS).strength(0.2f).noOcclusion(), () -> SurvivalReimaginedModBlocks.APPLE_FRUIT.get(), 0.05));
	public static final RegistryEntry<Block> FLOWERING_RED_CHERRY_LEAVES = register("flowering_red_cherry_leaves", () -> new FruitBearingLeavesBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_LEAVES).strength(0.2f).noOcclusion(), () -> SurvivalReimaginedModBlocks.RED_CHERRIES_FRUIT.get(), 0.05));


	public static final RegistryEntry<Block> STRIPPED_RADIANT_LOG = register("stripped_radiant_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3).ignitedByLava()));
	public static final RegistryEntry<Block> RADIANT_PLANKS = register("radiant_planks", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3).ignitedByLava()));
	public static final RegistryEntry<Block> RADIATED_SAPLING = register("radiated_sapling", () -> new SaplingBlock(SurvivalReimaginedModTreeGrowers.RADIATED, BlockBehaviour.Properties.of().sound(SoundType.FUNGUS).instabreak()));
	public static final RegistryEntry<Block> RADIATED_STAIRS = register("radiated_stairs", () -> new StairBlock(RADIANT_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(2,3).sound(SoundType.NETHER_WOOD)));
	public static final RegistryEntry<Block> RADIATED_SLAB = register("radiated_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> RADIATED_FENCE = register("radiated_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2,3).sound(SoundType.NETHER_WOOD)));
	public static final RegistryEntry<Block> RADIATED_FENCE_GATE = register("radiated_fence_gate", () -> new FenceGateBlock(WoodType.CRIMSON, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3).forceSolidOn().isRedstoneConductor(((blockState, blockGetter, blockPos) -> false))));
	public static final RegistryEntry<Block> RADIATED_PRESSURE_PLATE = register("radiated_pressure_plate", () -> new PressurePlateBlock(BlockSetType.CRIMSON, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3).isRedstoneConductor(((blockState, blockGetter, blockPos) -> false))));
	public static final RegistryEntry<Block> RADIATED_BUTTON = register("radiated_button", () -> new ButtonBlock(BlockSetType.CRIMSON, 1, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> RADIANT_TRAPDOOR = register("radiant_trapdoor", () -> new TrapDoorBlock(BlockSetType.CRIMSON, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3).dynamicShape()));
	public static final RegistryEntry<Block> RADIANT_DOOR = register("radiant_door", () -> new DoorBlock(BlockSetType.CRIMSON, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3).isRedstoneConductor(((blockState, blockGetter, blockPos) -> false)).dynamicShape()));
	public static final RegistryEntry<Block> STEEL_DOOR = register("steel_door", () -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().sound(SurvivalReimaginedModSoundTypes.STEEL).strength(5,5).noOcclusion().requiresCorrectToolForDrops().isRedstoneConductor(((blockState, blockGetter, blockPos) -> false)).dynamicShape()));
	public static final RegistryEntry<Block> STEEL_TRAPDOOR = register("steel_trapdoor", () -> new TrapDoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(5,5).sound(SurvivalReimaginedModSoundTypes.STEEL).noOcclusion().requiresCorrectToolForDrops().isRedstoneConductor(((blockState, blockGetter, blockPos) -> false)).dynamicShape()));
	public static final RegistryEntry<Block> NETHERITE_SCRAP_BLOCK = register("netherite_scrap_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.ANCIENT_DEBRIS).strength(30, 1200).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> NITRE_POWDER = register("nitre_powder", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.SAND).strength(0.5f, 0.5f)));
	public static final RegistryEntry<Block> MALACHITE_ORE = register("malachite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3,3).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_MALACHITE_ORE = register("deepslate_malachite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(3, 4.5f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> RAW_MALACHITE_BLOCK = register("raw_malachite_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3,4).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> AZURITE_ORE = register("azurite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3,3).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> DEEPSLATE_AZURITE_ORE = register("deepslate_azurite_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE).strength(3, 4.5f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> RAW_AZURITE_BLOCK = register("raw_azurite_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3, 4).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> SULFUR_ORE = register("sulfur_ore", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3,3).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> NITRE_BLOCK = register("nitre_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.TUFF).strength(1.5f, 6)));
	public static final RegistryEntry<Block> STRIPPED_WISTERIA_LOG = register("stripped_wisteria_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> WISTERIA_WOOD = register("wisteria_wood", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> WISTERIA_PLANKS = register("wisteria_planks", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> WISTERIA_STAIRS = register("wisteria_stairs", () -> new StairBlock(WISTERIA_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> WISTERIA_SLAB = register("wisteria_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> WISTERIA_FENCE = register("wisteria_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> WISTERIA_FENCE_GATE = register("wisteria_fence_gate", () -> new FenceGateBlock(WoodType.CHERRY, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).dynamicShape()));
	public static final RegistryEntry<Block> WISTERIA_DOOR = register("wisteria_door", () -> new DoorBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).dynamicShape().noOcclusion()));
	public static final RegistryEntry<Block> WISTERIA_TRAPDOOR = register("wisteria_trapdoor", () -> new TrapDoorBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).noOcclusion().dynamicShape()));
	public static final RegistryEntry<Block> WISTERIA_BUTTON = register("wisteria_button", () -> new ButtonBlock(BlockSetType.CHERRY, 1, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).noOcclusion().dynamicShape()));
	public static final RegistryEntry<Block> WISTERIA_PRESSURE_PLATE = register("wisteria_pressure_plate", () -> new PressurePlateBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> PURE_SALT_BLOCK = register("pure_salt_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.TUFF).strength(2,2)));
	public static final RegistryEntry<Block> SALT_DEPOSIT = register("salt_deposit", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3,3)));
	public static final RegistryEntry<Block> BANANA_JUNGLE_LOG = register("banana_jungle_log", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2,3)));
	public static final RegistryEntry<Block> SMALL_BANANA_JUNGLE_LOG = register("small_banana_jungle_log", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2,3)));
	public static final RegistryEntry<Block> BANANA_GROW_BLOCK = register("banana_grow_block", BananaGrowBlock::new);
	public static final RegistryEntry<Block> BANANA_LEAVES = register("banana_leaves", BananaLeavesBlock::new);
	public static final RegistryEntry<Block> MANDARIN_LOG = register("mandarin_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).ignitedByLava()));
	public static final RegistryEntry<Block> MANDARIN_LEAVES = register("mandarin_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_LEAVES).strength(0.2f,0.2f).noOcclusion().ignitedByLava()));
	public static final RegistryEntry<Block> MANDARIN_SAPLING = register("mandarin_sapling", () -> new SaplingBlock(SurvivalReimaginedModTreeGrowers.MANDARIN, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_SAPLING).instabreak().noOcclusion()));
	public static final RegistryEntry<Block> LOW_FERTILITY_FARMLAND = register("low_fertility_farmland", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(0.5f, 0.5f)));
	public static final RegistryEntry<Block> MEDIUM_FERTILITY_DIRT = register("medium_fertility_dirt", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(0.5f)));
	public static final RegistryEntry<Block> MEDIUM_FERTILITY_GRASS = register("medium_fertility_grass", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.GRASS).strength(0.5f)));
	public static final RegistryEntry<Block> MEDIUM_FERTILITY_SOIL = register("medium_fertility_soil", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(0.5f)));
	public static final RegistryEntry<Block> HIGH_FERTILITY_DIRT = register("high_fertility_dirt", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(0.5f)));
	public static final RegistryEntry<Block> HIGH_FERTILITY_GRASS = register("high_fertility_grass", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.GRASS).strength(0.5f)));
	public static final RegistryEntry<Block> HIGH_FERTILITY_SOIL = register("high_fertility_soil", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(0.5f)));
	public static final RegistryEntry<Block> BRITTLE_OBSIDIAN = register("brittle_obsidian", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(2,4).lightLevel(blockstate -> 5).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> STONE_SALT_DEPOSIT = register("stone_salt_deposit", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> TEOSINTE = register("teosinte", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.CROP).instabreak().noCollission().noOcclusion()));
	public static final RegistryEntry<Block> MANDARIN_PLANKS = register("mandarin_planks", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).ignitedByLava()));
	public static final RegistryEntry<Block> MANDARIN_STAIRS = register("mandarin_planks_stairs", () -> new StairBlock(MANDARIN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> MANDARIN_SLAB = register("mandarin_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
 	public static final RegistryEntry<Block> MANDARIN_FENCE = register("mandarin_planks_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> MANDARIN_FENCE_GATE = register("mandarin_planks_fence_gate", () -> new FenceGateBlock(WoodType.CHERRY, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).forceSolidOn()));
	public static final RegistryEntry<Block> MANDARIN_BUTTON = register("mandarin_planks_button", () -> new ButtonBlock(BlockSetType.CHERRY, 1, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).forceSolidOn()));
	public static final RegistryEntry<Block> MANDARIN_TRAPDOOR = register("mandarin_trapdoor", () -> new TrapDoorBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).forceSolidOn()));
	public static final RegistryEntry<Block> MANDARIN_DOOR = register("mandarin_door", () -> new DoorBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).forceSolidOn()));
	public static final RegistryEntry<Block> MANDARIN_PRESSURE_PLATE = register("mandarin_pressure_plate", () -> new PressurePlateBlock(BlockSetType.CHERRY, BlockBehaviour.Properties.of().strength(2,3).sound(SoundType.CHERRY_WOOD).noCollission()));
	public static final RegistryEntry<Block> STRIPPED_MANDARIN_LOG = register("stripped_mandarin_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3)));
	public static final RegistryEntry<Block> RED_CHERRY_LEAVES = register("red_cherry_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().sound(SoundType.CHERRY_LEAVES).strength(0.2f).noOcclusion()));
	public static final RegistryEntry<Block> RED_CHERRY_SAPLING = register("red_cherry_sapling", () -> new SaplingBlock(SurvivalReimaginedModTreeGrowers.RED_CHERRY, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_SAPLING).instabreak().noOcclusion().noCollission()));

	public static final RegistryEntry<Block> MANDARIN_SIGN = register("mandarin_sign", () -> new StandingSignBlock(SurvivalReimaginedModWoodTypes.MANDARIN_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).noCollission().noOcclusion()));
	public static final RegistryEntry<Block> MANDARIN_WALL_SIGN = register("mandarin_wall_sign", () -> new WallSignBlock(SurvivalReimaginedModWoodTypes.MANDARIN_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).noCollission().noOcclusion().dropsLike(SurvivalReimaginedModBlocks.MANDARIN_SIGN.get())));
	public static final RegistryEntry<Block> MANDARIN_HANGING_SIGN = register("mandarin_hanging_sign", () -> new WallHangingSignBlock(SurvivalReimaginedModWoodTypes.MANDARIN_HANGING_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD_HANGING_SIGN).strength(2,3).noCollission().noOcclusion()));
	public static final RegistryEntry<Block> MANDARIN_CEILING_HANGING_SIGN = register("mandarin_ceiling_hanging_sign", () -> new CeilingHangingSignBlock(SurvivalReimaginedModWoodTypes.MANDARIN_HANGING_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD_HANGING_SIGN).strength(2,3).noOcclusion().noCollission().dropsLike(SurvivalReimaginedModBlocks.MANDARIN_HANGING_SIGN.get())));
	public static final RegistryEntry<Block> RADIATED_SIGN = register("radiant_sign", () -> new StandingSignBlock(SurvivalReimaginedModWoodTypes.RADIATED_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3).noCollission()));
	public static final RegistryEntry<Block> RADIATED_WALL_SIGN = register("radiant_wall_sign", () -> new WallSignBlock(SurvivalReimaginedModWoodTypes.RADIATED_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD).strength(2,3).noCollission().dropsLike(SurvivalReimaginedModBlocks.RADIATED_SIGN.get())));
	public static final RegistryEntry<Block> RADIATED_HANGING_SIGN = register("radiant_hanging_sign", () -> new WallHangingSignBlock(SurvivalReimaginedModWoodTypes.RADIATED_HANGING_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD_HANGING_SIGN).strength(2,3).noCollission()));
	public static final RegistryEntry<Block> RADIATED_CEILING_HANGING_SIGN = register("radiant_ceiling_hanging_sign", () -> new CeilingHangingSignBlock(SurvivalReimaginedModWoodTypes.RADIATED_HANGING_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD_HANGING_SIGN).strength(2,3).noCollission().dropsLike(SurvivalReimaginedModBlocks.RADIATED_HANGING_SIGN.get())));
	public static final RegistryEntry<Block> WISTERIA_SIGN = register("wisteria_sign", () -> new StandingSignBlock(SurvivalReimaginedModWoodTypes.WISTERIA_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).noCollission()));
	public static final RegistryEntry<Block> WISTERIA_WALL_SIGN = register("wisteria_wall_sign", () -> new WallSignBlock(SurvivalReimaginedModWoodTypes.WISTERIA_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD).strength(2,3).noCollission().dropsLike(SurvivalReimaginedModBlocks.WISTERIA_SIGN.get())));
	public static final RegistryEntry<Block> WISTERIA_HANGING_SIGN = register("wisteria_hanging_sign", () -> new WallHangingSignBlock(SurvivalReimaginedModWoodTypes.WISTERIA_HANGING_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD_HANGING_SIGN).strength(2,3).noCollission()));
	public static final RegistryEntry<Block> WISTERIA_CEILING_HANGING_SIGN = register("wisteria_ceiling_hanging_sign", () -> new CeilingHangingSignBlock(SurvivalReimaginedModWoodTypes.WISTERIA_HANGING_SIGN_WOOD_TYPE, BlockBehaviour.Properties.of().sound(SoundType.CHERRY_WOOD_HANGING_SIGN).strength(2,3).noCollission().dropsLike(SurvivalReimaginedModBlocks.WISTERIA_HANGING_SIGN.get())));

	private SurvivalReimaginedModBlocks() {
	}

	private static RegistryEntry<Block> register(String path, Supplier<? extends Block> factory) {
		var id = SurvivalReimaginedMod.asResource(path);
		Block block = Registry.register(BuiltInRegistries.BLOCK, id, factory.get());
		return new RegistryEntry<>(id, block);
	}


	public static void register() {
		// Forces class initialization.

		// Sign Registration
		SignInjection.InjectAllSigns();
	}
}
