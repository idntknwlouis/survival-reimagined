package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.item.*;
import net.mcreator.survivalreimagined.world.inventory.AAFScriptureGUIMenu;
import net.minecraft.core.Registry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.world.item.Items.registerItem;

public final class SurvivalReimaginedModItems {
	public static final RegistryEntry<Item> FLINTBLOCK = block(SurvivalReimaginedModBlocks.FLINTBLOCK);
	public static final RegistryEntry<Item> COW_CARCASS = block(SurvivalReimaginedModBlocks.COW_CARCASS);
	public static final RegistryEntry<Item> COW_HEAD = block(SurvivalReimaginedModBlocks.COW_HEAD);
	public static final RegistryEntry<Item> COW_LEG = block(SurvivalReimaginedModBlocks.COW_LEG);
	public static final RegistryEntry<Item> COW_HIDE = simple("cow_hide");
	public static final RegistryEntry<Item> PIG_CARCASS = block(SurvivalReimaginedModBlocks.PIG_CARCASS);
	public static final RegistryEntry<Item> PIG_HEAD = block(SurvivalReimaginedModBlocks.PIG_HEAD);
	public static final RegistryEntry<Item> PIG_LEG = block(SurvivalReimaginedModBlocks.PIG_LEG);
	public static final RegistryEntry<Item> PIG_SKIN = simple("pig_skin");
	public static final RegistryEntry<Item> SHEEP_CARCASS = block(SurvivalReimaginedModBlocks.SHEEP_CARCASS);
	public static final RegistryEntry<Item> SHEEP_HEAD = block(SurvivalReimaginedModBlocks.SHEEP_HEAD);
	public static final RegistryEntry<Item> SHEEP_LEG = block(SurvivalReimaginedModBlocks.SHEEP_LEG);
	public static final RegistryEntry<Item> SHEEP_HIDE = simple("sheep_hide");
	public static final RegistryEntry<Item> GOAT_CARCASS = block(SurvivalReimaginedModBlocks.GOAT_CARCASS);
	public static final RegistryEntry<Item> GOAT_HEAD = block(SurvivalReimaginedModBlocks.GOAT_HEAD);
	public static final RegistryEntry<Item> GOAT_LEG = block(SurvivalReimaginedModBlocks.GOAT_LEG);
	public static final RegistryEntry<Item> GOAT_HIDE = simple("goat_hide");
	public static final RegistryEntry<Item> CHICKEN_CARCASS = block(SurvivalReimaginedModBlocks.CHICKEN_CARCASS);
	public static final RegistryEntry<Item> BRAIN = register("brain", () -> new SpoilingFoodItem(new Item.Properties()));
	public static final RegistryEntry<Item> LUNGS = simple("lungs");
	public static final RegistryEntry<Item> HEART_ITEM = simple("heart_item");
	public static final RegistryEntry<Item> LIVER = simple("liver");
	public static final RegistryEntry<Item> STOMACH = simple("stomach");
	public static final RegistryEntry<Item> INTESTINES = simple("intestines");
	public static final RegistryEntry<Item> STONE_ROCK = register("stone_rock", StoneRockItem::new);
	public static final RegistryEntry<Item> OBSIDIAN_ROCK = register("obsidian_rock", () -> new SurfaceRockItem(SurvivalReimaginedModBlocks.OBSIDIAN_ROCK::get, "block.stone.place"));
	public static final RegistryEntry<Item> SHALE = block(SurvivalReimaginedModBlocks.SHALE);
	public static final RegistryEntry<Item> RADIATED_SHALE = block(SurvivalReimaginedModBlocks.RADIATED_SHALE);
	public static final RegistryEntry<Item> RADIANT_LOG = block(SurvivalReimaginedModBlocks.RADIANT_LOG);
	public static final RegistryEntry<Item> RADIATED_LEAVES = block(SurvivalReimaginedModBlocks.RADIATED_LEAVES);
	public static final RegistryEntry<Item> RADIATED_MOSS = block(SurvivalReimaginedModBlocks.RADIATED_MOSS);
	public static final RegistryEntry<Item> RADIATED_TALL_GRASS = block(SurvivalReimaginedModBlocks.RADIATED_TALL_GRASS);
	public static final RegistryEntry<Item> RADIATED_ORCHID = block(SurvivalReimaginedModBlocks.RADIATED_ORCHID);
	public static final RegistryEntry<Item> WISTERIA_LOG = block(SurvivalReimaginedModBlocks.WISTERIA_LOG);
	public static final RegistryEntry<Item> WISTERIA_LEAVES = block(SurvivalReimaginedModBlocks.WISTERIA_LEAVES);
	public static final RegistryEntry<Item> FLOWING_WISTERIA_LEAVES = block(SurvivalReimaginedModBlocks.FLOWING_WISTERIA_LEAVES);
	public static final RegistryEntry<Item> WISTERIA_SPIDER_LILY = block(SurvivalReimaginedModBlocks.WISTERIA_SPIDER_LILY);
	public static final RegistryEntry<Item> WISTERIA_SAPLING = block(SurvivalReimaginedModBlocks.WISTERIA_SAPLING);
	public static final RegistryEntry<Item> WISTERIA_LEAF_LITTER = block(SurvivalReimaginedModBlocks.WISTERIA_LEAF_LITTER);
	public static final RegistryEntry<Item> SMALL_PALM_LOG = block(SurvivalReimaginedModBlocks.SMALL_PALM_LOG);
	public static final RegistryEntry<Item> PALM_LOG = block(SurvivalReimaginedModBlocks.PALM_LOG);
	public static final RegistryEntry<Item> PALM_LOG_BARKED_TOP = block(SurvivalReimaginedModBlocks.PALM_LOG_BARKED_TOP);
	public static final RegistryEntry<Item> PALM_CROWN = block(SurvivalReimaginedModBlocks.PALM_CROWN);
	public static final RegistryEntry<Item> PALM_LEAVES = block(SurvivalReimaginedModBlocks.PALM_LEAVES);
	public static final RegistryEntry<Item> ALOE_VERA = block(SurvivalReimaginedModBlocks.ALOE_VERA);
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
	public static final RegistryEntry<Item> CRIMSON_THREAD = simple("crimson_thread");

	public static final RegistryEntry<Item> WOODEN_HELMET = register("wooden_helmet",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.WOODEN, ArmorItem.Type.HELMET, 7));
	public static final RegistryEntry<Item> WOODEN_CHESTPLATE = register("wooden_chestplate",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.WOODEN, ArmorItem.Type.CHESTPLATE, 7));
	public static final RegistryEntry<Item> WOODEN_LEGGINGS = register("wooden_leggings",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.WOODEN, ArmorItem.Type.LEGGINGS, 7));
	public static final RegistryEntry<Item> WOODEN_BOOTS = register("wooden_boots",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.WOODEN, ArmorItem.Type.BOOTS, 7));

	public static final RegistryEntry<Item> BRONZE_ARMOR_HELMET = register("bronze_armor_helmet",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.BRONZE, ArmorItem.Type.HELMET, 15));
	public static final RegistryEntry<Item> BRONZE_ARMOR_CHESTPLATE = register("bronze_armor_chestplate",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.BRONZE, ArmorItem.Type.CHESTPLATE, 15));
	public static final RegistryEntry<Item> BRONZE_ARMOR_LEGGINGS = register("bronze_armor_leggings",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.BRONZE, ArmorItem.Type.LEGGINGS, 15));
	public static final RegistryEntry<Item> BRONZE_ARMOR_BOOTS = register("bronze_armor_boots",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.BRONZE, ArmorItem.Type.BOOTS, 15));

	public static final RegistryEntry<Item> STEEL_ARMOR_HELMET = register("steel_armor_helmet",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.STEEL, ArmorItem.Type.HELMET, 16));
	public static final RegistryEntry<Item> STEEL_ARMOR_CHESTPLATE = register("steel_armor_chestplate",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, 16));
	public static final RegistryEntry<Item> STEEL_ARMOR_LEGGINGS = register("steel_armor_leggings",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, 16));
	public static final RegistryEntry<Item> STEEL_ARMOR_BOOTS = register("steel_armor_boots",
			() -> new SurvivalArmorItem(SurvivalReimaginedModArmorMaterials.STEEL, ArmorItem.Type.BOOTS, 16));

	public static final RegistryEntry<Item> BLOOD_MOON_ZOMBIE_SPAWN_EGG = register("blood_moon_zombie_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.BLOOD_MOON_ZOMBIE.get(), -14150636, -12046045, new Item.Properties()));
	public static final RegistryEntry<Item> GHOST_SPAWN_EGG = register("ghost_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.GHOST.get(), -12500671, -13421773, new Item.Properties()));
	public static final RegistryEntry<Item> BOAR_SPAWN_EGG = register("boar_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.BOAR.get(), -13949662, -14410214, new Item.Properties()));
	public static final RegistryEntry<Item> SOW_SPAWN_EGG = register("sow_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.SOW.get(), -13949662, -14410214, new Item.Properties()));
	public static final RegistryEntry<Item> PIGLET_SPAWN_EGG = register("piglet_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.PIGLET.get(), -12111577, -13886446, new Item.Properties()));
	public static final RegistryEntry<Item> BLACK_BEAR_SPAWN_EGG = register("black_bear_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.BLACK_BEAR.get(), -13882324, -15790321, new Item.Properties()));
	public static final RegistryEntry<Item> BROWN_BEAR_SPAWN_EGG = register("brown_bear_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.BROWN_BEAR.get(), -11386310, -14411245, new Item.Properties()));
	public static final RegistryEntry<Item> CRIMSON_ARACHNID_SPAWN_EGG = register("crimson_arachnid_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.CRIMSON_ARACHNID.get(), -14941694, -426884, new Item.Properties()));
	public static final RegistryEntry<Item> ALPHA_CRIMSON_ARACHNID_SPAWN_EGG = register("alpha_crimson_arachnid_spawn_egg",
			() -> new SpawnEggItem(SurvivalReimaginedModEntities.ALPHA_CRIMSON_ARACHNID.get(), -14941694, -426884, new Item.Properties()));

	public static final RegistryEntry<Item> GAS_MASK_HELMET = register("gas_mask_helmet", GasMaskItem.Helmet::new);
	public static final RegistryEntry<Item> GAS_MASK_FILTER = register("gas_mask_filter", GasMaskFilterItem::new);
	public static final RegistryEntry<Item> USED_FILTER = simple("used_filter");
	public static final RegistryEntry<Item> HEMP_MESH = simple("hemp_mesh");
	public static final RegistryEntry<Item> RYE = simple("rye");
	public static final RegistryEntry<Item> RYE_SEEDS = cropSeed("rye_seeds", SurvivalReimaginedModBlocks.RYE_SEEDS);
	public static final RegistryEntry<Item> SPELT = simple("spelt");
	public static final RegistryEntry<Item> SPELT_SEEDS = cropSeed("spelt_seeds", SurvivalReimaginedModBlocks.SPELT_SEEDS);
	public static final RegistryEntry<Item> WHEAT_FLOUR = simple("wheat_flour");
	public static final RegistryEntry<Item> WHEAT_DOUGH = simple("wheat_dough");
	public static final RegistryEntry<Item> RYE_FLOUR = simple("rye_flour");
	public static final RegistryEntry<Item> RYE_DOUGH = simple("rye_dough");
	public static final RegistryEntry<Item> SPELT_FLOUR = simple("spelt_flour");
	public static final RegistryEntry<Item> SPELT_DOUGH = simple("spelt_dough");
	public static final RegistryEntry<Item> RYE_BREAD = register("rye_bread", () -> new Item(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build())));
	public static final RegistryEntry<Item> SPELT_BREAD = register("spelt_bread", () -> new Item(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).build())));
	public static final RegistryEntry<Item> HEMP_LEAF = simple("hemp_leaf");
	public static final RegistryEntry<Item> HEMP_SEEDS = cropSeed("hemp_seeds", SurvivalReimaginedModBlocks.HEMP);
	public static final RegistryEntry<Item> WHEAT_SEEDS = cropSeed("wheat_seeds", SurvivalReimaginedModBlocks.WHEAT_CROP);
	public static final RegistryEntry<Item> POTATO = plantFood("potato", SurvivalReimaginedModBlocks.POTATOES, 1, 0.6f);
	public static final RegistryEntry<Item> CARROT = plantFood("carrot", SurvivalReimaginedModBlocks.CARROT, 4, 3.6f);
	public static final RegistryEntry<Item> BURNT_POTATO = register("burnt_potato", () -> new Item(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build())));
	public static final RegistryEntry<Item> CHARRED_POTATO = register("charred_potato", () -> new Item(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(1).saturationModifier(0f).build())));
	public static final RegistryEntry<Item> STRAWBERRY = plantFood("strawberry", SurvivalReimaginedModBlocks.STRAWBERRY_PLANT, 4, 0.3f);
	public static final RegistryEntry<Item> RASPBERRY = plantFood("raspberry", SurvivalReimaginedModBlocks.RASPBERRY_PLANT, 2, 0.2f);
	public static final RegistryEntry<Item> CORN = simple("corn");
	public static final RegistryEntry<Item> CORN_ON_THE_COB = simple("corn_on_the_cob");
	public static final RegistryEntry<Item> COOKED_CORN_ON_THE_COB = register("cooked_corn_on_the_cob", () -> new Item(new Item.Properties()
			.food(new FoodProperties.Builder().nutrition(6).saturationModifier(1.5f).build())));
	public static final RegistryEntry<Item> BURNT_CORN_ON_THE_COB = simple("burnt_corn_on_the_cob");
	public static final RegistryEntry<Item> BEEF = food("beef", 3, 0.3f);
	public static final RegistryEntry<Item> COOKED_BEEF = food("cooked_beef", 5, 12.8f);
	public static final RegistryEntry<Item> BURNT_BEEF = food("burnt_beef", 2, 0.1f);

	public static final RegistryEntry<Item> RAW_MUTTON = food("raw_mutton", 2, 0.3f);
	public static final RegistryEntry<Item> COOKED_MUTTON = food("cooked_mutton", 4, 6.8f);
	public static final RegistryEntry<Item> BURNT_MUTTON = food("burnt_mutton", 1, 0.1f);

	public static final RegistryEntry<Item> RAW_PORKCHOP = food("raw_porkchop", 3, 0.3f);
	public static final RegistryEntry<Item> COOKED_PORKCHOP = food("cooked_porkchop", 4, 12.8f);
	public static final RegistryEntry<Item> BURNT_PORKCHOP = food("burnt_porkchop", 2, 0.1f);

	public static final RegistryEntry<Item> RAW_CHICKEN = food("raw_chicken", 2, 0.2f);
	public static final RegistryEntry<Item> COOKED_CHICKEN = food("cooked_chicken", 5, 6.8f);
	public static final RegistryEntry<Item> BURNT_CHICKEN = food("burnt_chicken", 1, 0.1f);

	public static final RegistryEntry<Item> RAW_RABBIT = food("raw_rabbit", 2, 0.1f);
	public static final RegistryEntry<Item> COOKED_RABBIT = food("cooked_rabbit", 4, 6.8f);
	public static final RegistryEntry<Item> BURNT_RABBIT = food("burnt_rabbit", 1, 0.1f);
	public static final RegistryEntry<Item> ROTTEN_BIOMATTER = simple("rotten_biomatter");
	public static final RegistryEntry<Item> SALT = simple("salt");
	public static final RegistryEntry<Item> CURED_STEAK = stableFood("cured_steak", 10, 0.6f);
	public static final RegistryEntry<Item> CURED_PORKCHOP = stableFood("cured_porkchop", 10, 0.6f);
	public static final RegistryEntry<Item> CURED_CHICKEN = stableFood("cured_chicken", 8, 0.5f);
	public static final RegistryEntry<Item> CURED_MUTTON = stableFood("cured_mutton", 8, 0.5f);
	public static final RegistryEntry<Item> CURED_RABBIT = stableFood("cured_rabbit", 8, 0.5f);

	public static final RegistryEntry<Item> RAW_COD = food("raw_cod", 2, 0.1f);
	public static final RegistryEntry<Item> COOKED_COD = food("cooked_cod", 4, 0.3f);
	public static final RegistryEntry<Item> BURNT_COD = food("burnt_cod", 1, 0.1f);

	public static final RegistryEntry<Item> RAW_SALMON = food("raw_salmon", 2, 0.2f);
	public static final RegistryEntry<Item> COOKED_SALMON = food("cooked_salmon", 4, 0.3f);
	public static final RegistryEntry<Item> BURNT_SALMON = food("burnt_salmon", 1, 0.1f);

	public static final RegistryEntry<Item> RAW_EQUINE = food("raw_equine", 5, 2.4f);
	public static final RegistryEntry<Item> COOKED_EQUINE = food("cooked_equine", 10, 13.4f);
	public static final RegistryEntry<Item> BURNT_EQUINE = food("burnt_equine", 7, 11.5f);
	public static final RegistryEntry<Item> CORN_SEEDS = cropSeed("corn_seeds", SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM);
	public static final RegistryEntry<Item> HEMP_FIBER = simple("hemp_fiber");
	public static final RegistryEntry<Item> CAMPFIRE = block(SurvivalReimaginedModBlocks.CAMPFIRE);
	public static final RegistryEntry<Item> FIRE_STARTER = register("fire_starter", () -> new Item(new Item.Properties().durability(16)));
	public static final RegistryEntry<Item> BLOCK_OF_CHARCOAL = block(SurvivalReimaginedModBlocks.BLOCK_OF_CHARCOAL);
	public static final RegistryEntry<Item> CHARCOAL_POWDER = simple("charcoal_powder");
	public static final RegistryEntry<Item> COPPER_NUGGET = simple("copper_nugget");
	public static final RegistryEntry<Item> SMALL_COAL_CHUNK = simple("small_coal_chunk");
	public static final RegistryEntry<Item> SMALL_STICK = simple("small_stick");
	public static final RegistryEntry<Item> WOOD_INGOT = simple("wood_ingot");
	public static final RegistryEntry<Item> BRONZE_INGOT = simple("bronze_ingot");
	public static final RegistryEntry<Item> BRONZE_NUGGET = simple("bronze_nugget");
	public static final RegistryEntry<Item> COPPER_HANDLE = simple("copper_handle");
	public static final RegistryEntry<Item> SMALL_COPPER_HANDLE = simple("small_copper_handle");
	public static final RegistryEntry<Item> BRONZE_HANDLE = simple("bronze_handle");
	public static final RegistryEntry<Item> SMALL_BRONZE_HANDLE = simple("small_bronze_handle");
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
	public static final RegistryEntry<Item> DIAMOND_SWORD_BLADE = simpleUnstackable("diamond_sword_blade");
	public static final RegistryEntry<Item> DIAMOND_PICKAXE_HEAD = simpleUnstackable("diamond_pickaxe_head");
	public static final RegistryEntry<Item> DIAMOND_AXE_HEAD = simpleUnstackable("diamond_axe_head");
	public static final RegistryEntry<Item> DIAMOND_SHOVEL_HEAD = simpleUnstackable("diamond_shovel_head");
	public static final RegistryEntry<Item> DIAMOND_HOE_BLADE = simpleUnstackable("diamond_hoe_blade");
	public static final RegistryEntry<Item> DIAMOND_HAMMER_HEAD = simpleUnstackable("diamond_hammer_head");
	public static final RegistryEntry<Item> DIAMOND_SAW_BLADE = simpleUnstackable("diamond_saw_blade");
	public static final RegistryEntry<Item> DIAMOND_KNIFE_BLADE = simpleUnstackable("diamond_knife_blade");
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
	public static final RegistryEntry<Item> BASALT_STALAGMITE_BASE = block(SurvivalReimaginedModBlocks.BASALT_STALAGMITE_BASE);
	public static final RegistryEntry<Item> BASALT_STALAGMITE_MIDDLE = block(SurvivalReimaginedModBlocks.BASALT_STALAGMITE_MIDDLE);
	public static final RegistryEntry<Item> BASALT_STALAGMITE_TOP = block(SurvivalReimaginedModBlocks.BASALT_STALAGMITE_TOP);
	public static final RegistryEntry<Item> BASALT_STALAGTITE_BASE = block(SurvivalReimaginedModBlocks.BASALT_STALAGTITE_BASE);
	public static final RegistryEntry<Item> BASALT_STALAGTITE_MIDDLE = block(SurvivalReimaginedModBlocks.BASALT_STALAGTITE_MIDDLE);
	public static final RegistryEntry<Item> BASALT_STALAGTITE_TIP = block(SurvivalReimaginedModBlocks.BASALT_STALAGTITE_TIP);
	public static final RegistryEntry<Item> KIMBERLITE = block(SurvivalReimaginedModBlocks.KIMBERLITE);
	public static final RegistryEntry<Item> KIMBERLITE_STALAGMITE_BASE = block(SurvivalReimaginedModBlocks.KIMBERLITE_STALAGMITE_BASE);
	public static final RegistryEntry<Item> KIMBERLITE_STALAGMITE_MIDDLE = block(SurvivalReimaginedModBlocks.KIMBERLITE_STALAGMITE_MIDDLE);
	public static final RegistryEntry<Item> KIMBERLITE_STALAGMITE_TOP = block(SurvivalReimaginedModBlocks.KIMBERLITE_STALAGMITE_TOP);
	public static final RegistryEntry<Item> KIMBERLITE_STALAGTITE_BASE = block(SurvivalReimaginedModBlocks.KIMBERLITE_STALAGTITE_BASE);
	public static final RegistryEntry<Item> KIMBERLITE_STALAGTITE_MIDDLE = block(SurvivalReimaginedModBlocks.KIMBERLITE_STALAGTITE_MIDDLE);
	public static final RegistryEntry<Item> KIMBERLITE_STALAGTITE_TIP = block(SurvivalReimaginedModBlocks.KIMBERLITE_STALAGTITE_TIP);
	public static final RegistryEntry<Item> STONE_STALAGMITE_BASE = block(SurvivalReimaginedModBlocks.STONE_STALAGMITE_BASE);
	public static final RegistryEntry<Item> STONE_STALAGMITE_MIDDLE = block(SurvivalReimaginedModBlocks.STONE_STALAGMITE_MIDDLE);
	public static final RegistryEntry<Item> STONE_STALAGMITE_TOP = block(SurvivalReimaginedModBlocks.STONE_STALAGMITE_TOP);
	public static final RegistryEntry<Item> STONE_STALAGTITE_BASE = block(SurvivalReimaginedModBlocks.STONE_STALAGTITE_BASE);
	public static final RegistryEntry<Item> STONE_STALAGTITE_MIDDLE = block(SurvivalReimaginedModBlocks.STONE_STALAGTITE_MIDDLE);
	public static final RegistryEntry<Item> STONE_STALAGTITE_TIP = block(SurvivalReimaginedModBlocks.STONE_STALAGTITE_TIP);
	public static final RegistryEntry<Item> SHALE_STALAGMITE_BASE = block(SurvivalReimaginedModBlocks.SHALE_STALAGMITE_BASE);
	public static final RegistryEntry<Item> SHALE_STALAGMITE_MIDDLE = block(SurvivalReimaginedModBlocks.SHALE_STALAGMITE_MIDDLE);
	public static final RegistryEntry<Item> SHALE_STALAGMITE_TOP = block(SurvivalReimaginedModBlocks.SHALE_STALAGMITE_TOP);
	public static final RegistryEntry<Item> SHALE_STALAGTITE_BASE = block(SurvivalReimaginedModBlocks.SHALE_STALAGTITE_BASE);
	public static final RegistryEntry<Item> SHALE_STALAGTITE_MIDDLE = block(SurvivalReimaginedModBlocks.SHALE_STALAGTITE_MIDDLE);
	public static final RegistryEntry<Item> SHALE_STALAGTITE_TIP = block(SurvivalReimaginedModBlocks.SHALE_STALAGTITE_TIP);

	public static final RegistryEntry<Item> DEEPSLATE_STALAGMITE_BASE = block(SurvivalReimaginedModBlocks.DEEPSLATE_STALAGMITE_BASE);
	public static final RegistryEntry<Item> DEEPSLATE_STALAGMITE_MIDDLE = block(SurvivalReimaginedModBlocks.DEEPSLATE_STALAGMITE_MIDDLE);
	public static final RegistryEntry<Item> DEEPSLATE_STALAGMITE_TOP = block(SurvivalReimaginedModBlocks.DEEPSLATE_STALAGMITE_TOP);
	public static final RegistryEntry<Item> DEEPSLATE_STALAGTITE_BASE = block(SurvivalReimaginedModBlocks.DEEPSLATE_STALAGTITE_BASE);
	public static final RegistryEntry<Item> DEEPSLATE_STALAGTITE_MIDDLE = block(SurvivalReimaginedModBlocks.DEEPSLATE_STALAGTITE_MIDDLE);
	public static final RegistryEntry<Item> DEEPSLATE_STALAGTITE_TIP = block(SurvivalReimaginedModBlocks.DEEPSLATE_STALAGTITE_TIP);
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
	public static final RegistryEntry<Item> CLAY_CRUCIBLE = block(SurvivalReimaginedModBlocks.CLAY_CRUCIBLE);
	public static final RegistryEntry<Item> CRUCIBLE = block(SurvivalReimaginedModBlocks.CRUCIBLE);
	public static final RegistryEntry<Item> BRONZE_PLATE = block(SurvivalReimaginedModBlocks.BRONZE_PLATE);
	public static final RegistryEntry<Item> STEEL_PLATE = block(SurvivalReimaginedModBlocks.STEEL_PLATE);
	public static final RegistryEntry<Item> DIAMOND_PLATE = block(SurvivalReimaginedModBlocks.DIAMOND_PLATE);
	public static final RegistryEntry<Item> NETHERITE_PLATE = block(SurvivalReimaginedModBlocks.NETHERITE_PLATE);



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
	public static final RegistryEntry<Item> THIN_RADIATED_VINES = block(SurvivalReimaginedModBlocks.THIN_RADIATED_VINES);
	public static final RegistryEntry<Item> THICK_RADIATED_VINES = block(SurvivalReimaginedModBlocks.THICK_RADIATED_VINES);
	public static final RegistryEntry<Item> SAND_SALT_DEPOSIT = block(SurvivalReimaginedModBlocks.SAND_SALT_DESPOSIT);
	public static final RegistryEntry<Item> BLOCK_OF_RAW_REDSTONE = block(SurvivalReimaginedModBlocks.BLOCK_OF_RAW_REDSTONE);
	public static final RegistryEntry<Item> EMBEDDED_OBSIDIAN = block(SurvivalReimaginedModBlocks.EMBEDDED_OBSIDIAN);
	public static final RegistryEntry<Item> APPLE_OAK_LEAVES = block(SurvivalReimaginedModBlocks.APPLE_OAK_LEAVES);
 	public static final RegistryEntry<Item> APPLE_OAK_SAPLING = block(SurvivalReimaginedModBlocks.APPLE_TREE_SAPLING);
	public static final RegistryEntry<Item> MANDARIN = stableFood("mandarin", 5, 0.8f);
	public static final RegistryEntry<Item> RED_CHERRIES = stableFood("cherries", 3, 0.4f);
	public static final RegistryEntry<Item> BANANA_LEAVES = block(SurvivalReimaginedModBlocks.BANANA_LEAVES);
	public static final RegistryEntry<Item> BANANA = stableFood("banana", 4, 0.3f);
	public static final RegistryEntry<Item> BANANA_JUNGLE_LOG = block(SurvivalReimaginedModBlocks.BANANA_JUNGLE_LOG);
	public static final RegistryEntry<Item> SMALL_BANANA_JUNGLE_LOG = block(SurvivalReimaginedModBlocks.SMALL_BANANA_JUNGLE_LOG);

	public static final RegistryEntry<Item> APPLE = block(SurvivalReimaginedModBlocks.APPLE_FRUIT);
	public static final RegistryEntry<Item> MANDARIN_FRUIT = block(SurvivalReimaginedModBlocks.MANDARIN_FRUIT);
	public static final RegistryEntry<Item> BANANA_FRUIT = block(SurvivalReimaginedModBlocks.BANANA_FRUIT);
	public static final RegistryEntry<Item> RED_CHERRIES_FRUIT = block(SurvivalReimaginedModBlocks.RED_CHERRIES_FRUIT);

	public static final RegistryEntry<Item> STRIPPED_RADIANT_LOG = block(SurvivalReimaginedModBlocks.STRIPPED_RADIANT_LOG);
	public static final RegistryEntry<Item> RADIANT_PLANKS = block(SurvivalReimaginedModBlocks.RADIANT_PLANKS);
	public static final RegistryEntry<Item> RADIATED_SAPLING = block(SurvivalReimaginedModBlocks.RADIATED_SAPLING);
	public static final RegistryEntry<Item> RADIATED_STAIRS = block(SurvivalReimaginedModBlocks.RADIATED_STAIRS);
	public static final RegistryEntry<Item> RADIATED_SLAB = block(SurvivalReimaginedModBlocks.RADIATED_SLAB);
	public static final RegistryEntry<Item> RADIATED_FENCE = block(SurvivalReimaginedModBlocks.RADIATED_FENCE);
	public static final RegistryEntry<Item> RADIATED_FENCE_GATE = block(SurvivalReimaginedModBlocks.RADIATED_FENCE_GATE);
	public static final RegistryEntry<Item> RADIATED_PRESSURE_PLATE = block(SurvivalReimaginedModBlocks.RADIATED_PRESSURE_PLATE);
	public static final RegistryEntry<Item> RADIATED_BUTTON = block(SurvivalReimaginedModBlocks.RADIATED_BUTTON);
	public static final RegistryEntry<Item> RADIANT_TRAPDOOR = block(SurvivalReimaginedModBlocks.RADIANT_TRAPDOOR);
	public static final RegistryEntry<Item> RADIANT_DOOR = block(SurvivalReimaginedModBlocks.RADIANT_DOOR);
	public static final RegistryEntry<Item> STEEL_DOOR = block(SurvivalReimaginedModBlocks.STEEL_DOOR);
	public static final RegistryEntry<Item> STEEL_TRAPDOOR = block(SurvivalReimaginedModBlocks.STEEL_TRAPDOOR);
	public static final RegistryEntry<Item> NETHERITE_SCRAP_BLOCK = block(SurvivalReimaginedModBlocks.NETHERITE_SCRAP_BLOCK);
	public static final RegistryEntry<Item> NITRE_POWDER = block(SurvivalReimaginedModBlocks.NITRE_POWDER);
	public static final RegistryEntry<Item> MALACHITE_ORE = block(SurvivalReimaginedModBlocks.MALACHITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_MALACHITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_MALACHITE_ORE);
	public static final RegistryEntry<Item> RAW_MALACHITE_BLOCK = block(SurvivalReimaginedModBlocks.RAW_MALACHITE_BLOCK);
	public static final RegistryEntry<Item> AZURITE_ORE = block(SurvivalReimaginedModBlocks.AZURITE_ORE);
	public static final RegistryEntry<Item> DEEPSLATE_AZURITE_ORE = block(SurvivalReimaginedModBlocks.DEEPSLATE_AZURITE_ORE);
	public static final RegistryEntry<Item> SULFUR_ORE = block(SurvivalReimaginedModBlocks.SULFUR_ORE);
	public static final RegistryEntry<Item> NITRE_BLOCK = block(SurvivalReimaginedModBlocks.NITRE_BLOCK);
	public static final RegistryEntry<Item> STRIPPED_WISTERIA_LOG = block(SurvivalReimaginedModBlocks.STRIPPED_WISTERIA_LOG);
	public static final RegistryEntry<Item> WISTERIA_WOOD = block(SurvivalReimaginedModBlocks.WISTERIA_WOOD);
	public static final RegistryEntry<Item> WISTERIA_PLANKS = block(SurvivalReimaginedModBlocks.WISTERIA_PLANKS);
	public static final RegistryEntry<Item> WISTERIA_STAIRS = block(SurvivalReimaginedModBlocks.WISTERIA_STAIRS);
	public static final RegistryEntry<Item> WISTERIA_SLAB = block(SurvivalReimaginedModBlocks.WISTERIA_SLAB);
	public static final RegistryEntry<Item> WISTERIA_FENCE = block(SurvivalReimaginedModBlocks.WISTERIA_FENCE);
	public static final RegistryEntry<Item> WISTERIA_FENCE_GATE = block(SurvivalReimaginedModBlocks.WISTERIA_FENCE_GATE);
	public static final RegistryEntry<Item> WISTERIA_DOOR = block(SurvivalReimaginedModBlocks.WISTERIA_DOOR);
	public static final RegistryEntry<Item> WISTERIA_TRAPDOOR = block(SurvivalReimaginedModBlocks.WISTERIA_TRAPDOOR);
	public static final RegistryEntry<Item> WISTERIA_BUTTON = block(SurvivalReimaginedModBlocks.WISTERIA_BUTTON);
	public static final RegistryEntry<Item> WISTERIA_PRESSURE_PLATE = block(SurvivalReimaginedModBlocks.WISTERIA_PRESSURE_PLATE);
	public static final RegistryEntry<Item> PURE_SALT_BLOCK = block(SurvivalReimaginedModBlocks.PURE_SALT_BLOCK);
	public static final RegistryEntry<Item> SALT_DEPOSIT = block(SurvivalReimaginedModBlocks.SALT_DEPOSIT);
	public static final RegistryEntry<Item> MANDARIN_LOG = block(SurvivalReimaginedModBlocks.MANDARIN_LOG);
	public static final RegistryEntry<Item> MANDARIN_LEAVES = block(SurvivalReimaginedModBlocks.MANDARIN_LEAVES);
	public static final RegistryEntry<Item> FRUITING_MANDARIN_LEAVES = block(SurvivalReimaginedModBlocks.FRUITING_MANDARIN_LEAVES);
	public static final RegistryEntry<Item> MANDARIN_SAPLING = block(SurvivalReimaginedModBlocks.MANDARIN_SAPLING);
	public static final RegistryEntry<Item> LOW_FERTILITY_FARMLAND = block(SurvivalReimaginedModBlocks.LOW_FERTILITY_FARMLAND);
	public static final RegistryEntry<Item> MEDIUM_FERTILITY_DIRT = block(SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_DIRT);
	public static final RegistryEntry<Item> MEDIUM_FERTILITY_GRASS = block(SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_GRASS);
	public static final RegistryEntry<Item> MEDIUM_FERTILITY_SOIL = block(SurvivalReimaginedModBlocks.MEDIUM_FERTILITY_SOIL);
	public static final RegistryEntry<Item> HIGH_FERTILITY_DIRT = block(SurvivalReimaginedModBlocks.HIGH_FERTILITY_DIRT);
	public static final RegistryEntry<Item> HIGH_FERTILITY_GRASS = block(SurvivalReimaginedModBlocks.HIGH_FERTILITY_GRASS);
	public static final RegistryEntry<Item> HIGH_FERTILITY_SOIL = block(SurvivalReimaginedModBlocks.HIGH_FERTILITY_SOIL);
	public static final RegistryEntry<Item> BRITTLE_OBSIDIAN = block(SurvivalReimaginedModBlocks.BRITTLE_OBSIDIAN);
	public static final RegistryEntry<Item> STONE_SALT_DEPOSIT = block(SurvivalReimaginedModBlocks.STONE_SALT_DEPOSIT);
	public static final RegistryEntry<Item> TEOSINTE = block(SurvivalReimaginedModBlocks.TEOSINTE);
	public static final RegistryEntry<Item> MANDARIN_PLANKS = block(SurvivalReimaginedModBlocks.MANDARIN_PLANKS);
	public static final RegistryEntry<Item> MANDARIN_STAIRS = block(SurvivalReimaginedModBlocks.MANDARIN_STAIRS);
	public static final RegistryEntry<Item> MANDARIN_SLAB = block(SurvivalReimaginedModBlocks.MANDARIN_SLAB);
	public static final RegistryEntry<Item> MANDARIN_FENCE = block(SurvivalReimaginedModBlocks.MANDARIN_FENCE);
	public static final RegistryEntry<Item> MANDARIN_FENCE_GATE = block(SurvivalReimaginedModBlocks.MANDARIN_FENCE_GATE);
	public static final RegistryEntry<Item> MANDARIN_BUTTON = block(SurvivalReimaginedModBlocks.MANDARIN_BUTTON);
	public static final RegistryEntry<Item> MANDARIN_TRAPDOOR = block(SurvivalReimaginedModBlocks.MANDARIN_TRAPDOOR);
	public static final RegistryEntry<Item> MANDARIN_DOOR = block(SurvivalReimaginedModBlocks.MANDARIN_DOOR);
	public static final RegistryEntry<Item> MANDARIN_PRESSURE_PLATE = block(SurvivalReimaginedModBlocks.MANDARIN_PRESSURE_PLATE);
	public static final RegistryEntry<Item> STRIPPED_MANDARIN_LOG = block(SurvivalReimaginedModBlocks.STRIPPED_MANDARIN_LOG);
	public static final RegistryEntry<Item> RED_CHERRY_LEAVES = block(SurvivalReimaginedModBlocks.RED_CHERRY_LEAVES);
	public static final RegistryEntry<Item> FLOWERING_RED_CHERRY_LEAVES = block(SurvivalReimaginedModBlocks.FLOWERING_RED_CHERRY_LEAVES);
	public static final RegistryEntry<Item> RED_CHERRY_SAPLING = block(SurvivalReimaginedModBlocks.RED_CHERRY_SAPLING);

	public static final Item OAK_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_oak_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item DARK_OAK_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_dark_oak_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item SPRUCE_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_spruce_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item BIRCH_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_birch_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item MANGROVE_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_mangrove_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item ACACIA_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_acacia_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item JUNGLE_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_jungle_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item CHERRY_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_cherry_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item MANDARIN_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "mandarin_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item RADIATED_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "radiated_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item WISTERIA_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "wisteria_bark"), new BarkItem(new Item.Properties(), 400));
	public static final Item CRIMSON_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_crimson_bark"), new BarkItem(new Item.Properties(), 0));
	public static final Item WARPED_BARK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "large_warped_bark"), new BarkItem(new Item.Properties(), 0));

	public static final Item OAK_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "oak_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item DARK_OAK_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "dark_oak_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item SPRUCE_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "spruce_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item BIRCH_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "birch_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item ACACIA_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "acacia_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item JUNGLE_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "jungle_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item MANGROVE_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "mangrove_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item CHERRY_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "cherry_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item RADIATED_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "radiated_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item WISTERIA_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "wisteria_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item MANDARIN_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "mandarin_plank"), new BarkItem(new Item.Properties(), 400));
	public static final Item CRIMSON_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "crimson_plank"), new BarkItem(new Item.Properties(), 0));
	public static final Item WARPED_PLANK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "warped_plank"), new BarkItem(new Item.Properties(), 0));

	public static final Item BRONZE_SWORD = registerTool("bronze_sword", new SwordItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(SwordItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 5, -2.4F))));
	public static final Item BRONZE_PICKAXE = registerTool("bronze_pickaxe", new PickaxeItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 3, -2.8F))));
	public static final Item BRONZE_AXE = registerTool("bronze_axe", new AxeItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 8, -3.2F))));
	public static final Item BRONZE_SHOVEL = registerTool("bronze_shovel", new ShovelItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 1, -3F))));
	public static final Item BRONZE_HOE = registerTool("bronze_hoe", new HoeItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 0, -1))));
	public static final Item BRONZE_HAMMER = registerTool("bronze_hammer", new HammerItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 5.5F, -3.4F))));
	public static final Item BRONZE_SAW = registerTool("bronze_saw", new SawItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 1, -3))));
	public static final Item BRONZE_KNIFE = registerTool("bronze_knife", new KnifeItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 2, -2))));

	public static final Item STEEL_SWORD = registerTool("steel_sword", new SwordItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(SwordItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 6, -2.4F))));
	public static final Item STEEL_PICKAXE = registerTool("steel_pickaxe", new PickaxeItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 3.5F, -2.8F))));
	public static final Item STEEL_AXE = registerTool("steel_axe", new AxeItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 9F, -3.1F))));
	public static final Item STEEL_SHOVEL = registerTool("steel_shovel", new ShovelItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 1.5F, -3F))));
	public static final Item STEEL_HOE = registerTool("steel_hoe", new HoeItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 0, -1))));
	public static final Item STEEL_HAMMER = registerTool("steel_hammer", new HammerItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 6, -3.4F))));
	public static final Item STEEL_SAW = registerTool("steel_saw", new SawItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 2, -3))));
	public static final Item STEEL_KNIFE = registerTool("steel_knife", new KnifeItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 2.5F, -2))));

	public static final Item DIAMOND_HAMMER = registerTool("diamond_hammer", new HammerItem(Tiers.DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.DIAMOND, 7, -3.4F))));
	public static final Item DIAMOND_SAW = registerTool("diamond_saw", new SawItem(Tiers.DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.DIAMOND, 3, -3))));
	public static final Item DIAMOND_KNIFE = registerTool("diamond_knife", new KnifeItem(Tiers.DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.DIAMOND, 2.5F, -2))));

	public static final Item WOODEN_HAMMER = registerTool("wooden_hammer", new HammerItem(Tiers.WOOD, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.WOOD, 2, -3.4F))));
	public static final Item WOODEN_SAW = registerTool("wooden_saw", new SawItem(Tiers.WOOD, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.WOOD, 1, -3))));
	public static final Item WOODEN_KNIFE = registerTool("wooden_knife", new KnifeItem(Tiers.WOOD, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.WOOD, 2, -2))));

	public static final Item STONE_HAMMER = registerTool("stone_hammer", new HammerItem(Tiers.STONE, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.STONE, 2.5f, -3.4F))));
	public static final Item STONE_SAW = registerTool("stone_saw", new SawItem(Tiers.STONE, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.STONE, 1.5f, -3))));
	public static final Item STONE_KNIFE = registerTool("stone_knife", new KnifeItem(Tiers.STONE, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.STONE, 2.5f, -2))));

	public static final Item NETHERITE_HAMMER = registerTool("netherite_hammer", new HammerItem(Tiers.NETHERITE, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.NETHERITE, 8, -3.4F))));
	public static final Item NETHERITE_SAW = registerTool("netherite_saw", new SawItem(Tiers.NETHERITE, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.NETHERITE, 3.5f, -3))));
	public static final Item NETHERITE_KNIFE = registerTool("netherite_knife", new KnifeItem(Tiers.NETHERITE, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.NETHERITE, 4, -2))));

	public static final Item BRONZE_CHISEL = registerTool("bronze_chisel", new ChiselItem(SurvivalReimaginedModToolMaterials.BRONZE, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.BRONZE, 2, -2))));
	public static final Item STEEL_CHISEL = registerTool("steel_chisel", new ChiselItem(SurvivalReimaginedModToolMaterials.STEEL, new Item.Properties().attributes(DiggerItem.createAttributes(SurvivalReimaginedModToolMaterials.STEEL, 2, -2))));
	public static final Item DIAMOND_CHISEL = registerTool("diamond_chisel", new ChiselItem(Tiers.DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.DIAMOND, 2, -2))));

	public static final RegistryEntry<Item> BOAT_PADDLES = simple("boat_paddles");
	public static final RegistryEntry<Item> MORTAR_PESTAL = damageOnCrafting("mortar_pestal", new Item.Properties().stacksTo(1).durability(64), ItemStack.EMPTY);

	public static final RegistryEntry<Item> CLAY_BRICK = simple("clay_brick");
	public static final RegistryEntry<Item> IRON_CHUNK = simple("iron_chunk");
	public static final RegistryEntry<Item> GOLD_CHUNK = simple("gold_chunk");
	public static final RegistryEntry<Item> REDSTONE_CHUNK = simple("redstone_chunk");
	public static final RegistryEntry<Item> STEEL_NUGGET = simple("steel_nugget");
	public static final RegistryEntry<Item> ROUGH_REDSTONE = simple("rough_redstone");
	public static final RegistryEntry<Item> REDSTONE_INGOT = simple("redstone_ingot");
	public static final RegistryEntry<Item> REDSTONE_NUGGET = simple("redstone_nugget");
	public static final RegistryEntry<Item> SULFUR = simple("sulfur");
	public static final RegistryEntry<Item> NITRE = simple("nitre");
	public static final RegistryEntry<Item> SALTPETER = simple("saltpeter");
	public static final RegistryEntry<Item> SULFUR_POWDER = simple("sulfur_powder");
	public static final RegistryEntry<Item> MALACHITE = simple("malachite");
	public static final RegistryEntry<Item> MALACHITE_NUGGET = simple("malachite_nugget");
	public static final RegistryEntry<Item> AZURITE = simple("azurite");
	public static final RegistryEntry<Item> AZURITE_NUGGET = simple("azurite_nugget");
	public static final RegistryEntry<Item> TURANITE_NUGGET = simple("turanite_nugget");
	public static final RegistryEntry<Item> MUD_ROCK = simple("turanite_nugget");
	public static final RegistryEntry<Item> CRYING_OBSIDIAN_ROCK = simple("crying_obsidian_rock");
	public static final RegistryEntry<Item> ANCIENT_DEBRIS_CHUNK = simple("ancient_debris_chunk");
	public static final RegistryEntry<Item> ARGILLITE_ROCK = simple("argillite_rock");
	public static final RegistryEntry<Item> CHALK_ROCK = simple("chalk_rock");
	public static final RegistryEntry<Item> SALT_ROCK = simple("salt_rock");
	public static final RegistryEntry<Item> DIRT_PILE = simple("dirt_pile");
	public static final RegistryEntry<Item> SAND_PILE = simple("sand_pile");
	public static final RegistryEntry<Item> RED_SAND_PILE = simple("red_sand_pile");
	public static final RegistryEntry<Item> COARSE_DIRT_PILE = simple("coarse_dirt_pile");
	public static final RegistryEntry<Item> ROOTED_DIRT_PILE = simple("rooted_dirt_pile");
	public static final RegistryEntry<Item> GRAVEL_PILE = simple("gravel_pile");
	public static final RegistryEntry<Item> SOUL_SAND_PILE = simple("soul_sand_pile");
	public static final RegistryEntry<Item> MUD_PILE = simple("mud_pile");
	public static final RegistryEntry<Item> SANDSTONE_CHUNK = simple("sandstone_chunk");
	public static final RegistryEntry<Item> RED_SANDSTONE_CHUNK = simple("red_sandstone_chunk");
	public static final RegistryEntry<Item> ENDER_DUST = simple("ender_dust");
	public static final RegistryEntry<Item> TIN_CHUNK = simple("tin_chunk");
	public static final RegistryEntry<Item> TIN_INGOT = simple("tin_ingot");
	public static final RegistryEntry<Item> TIN_NUGGET = simple("tin_nugget");
	public static final RegistryEntry<Item> RAW_TIN = simple("raw_tin");
	public static final RegistryEntry<Item> ROUGH_TIN = simple("rough_tin");

	public static final RegistryEntry<Item> GLASS_SHARD = simple("glass_shard");
	public static final RegistryEntry<Item> WHITE_GLASS_SHARD = simple("white_glass_shard");
	public static final RegistryEntry<Item> ORANGE_GLASS_SHARD = simple("orange_glass_shard");
	public static final RegistryEntry<Item> MAGENTA_GLASS_SHARD = simple("magenta_glass_shard");
	public static final RegistryEntry<Item> LIGHT_BLUE_GLASS_SHARD = simple("light_blue_glass_shard");
	public static final RegistryEntry<Item> YELLOW_GLASS_SHARD = simple("yellow_glass_shard");
	public static final RegistryEntry<Item> LIME_GLASS_SHARD = simple("lime_green_glass_shard");
	public static final RegistryEntry<Item> PINK_GLASS_SHARD = simple("pink_glass_shard");
	public static final RegistryEntry<Item> BLACK_GLASS_SHARD = simple("black_glass_shard");
	public static final RegistryEntry<Item> LIGHT_GREY_GLASS_SHARD = simple("light_grey_glass_shard");
	public static final RegistryEntry<Item> CYAN_GLASS_SHARD = simple("cyan_glass_shard");
	public static final RegistryEntry<Item> PURPLE_GLASS_SHARD = simple("purple_glass_shard");
	public static final RegistryEntry<Item> BLUE_GLASS_SHARD = simple("blue_glass_shard");
	public static final RegistryEntry<Item> GREEN_GLASS_SHARD = simple("green_glass_shard");
	public static final RegistryEntry<Item> BROWN_GLASS_SHARD = simple("brown_glass_shard");
	public static final RegistryEntry<Item> RED_GLASS_SHARD = simple("red_glass_shard");
	public static final RegistryEntry<Item> GREY_GLASS_SHARD = simple("grey_glass_shard");

	public static final RegistryEntry<Item> HEMP_PAPER = simple("hemp_paper");
	public static final RegistryEntry<Item> GOLD_HEART = register("gold_heart", GoldHeartItem::new);

	public static final RegistryEntry<Item> BLOOD_MOON_ITEM = simple("blood_moon_item");
	public static final RegistryEntry<Item> RADIATION_ITEM = simple("radiation_item");
	public static final RegistryEntry<Item> ZOMBIE_FACE = simple("zombie_face");
	public static final RegistryEntry<Item> PARANOIA_EYE = simple("paranoia_eye");
	public static final RegistryEntry<Item> FEAR_EYE = simple("fear_eye");
	public static final RegistryEntry<Item> EYE_CLOSED = simple("eye_closed");

	public static final RegistryEntry<Item> FLESH = food("flesh", 2, 0.3f);
	public static final RegistryEntry<Item> HEART_OF_THE_BLOOD_MOON = uniqueItem("heart_of_the_blood_moon", Rarity.RARE);
	public static final RegistryEntry<Item> HEART_OF_THE_NETHER = uniqueItem("heart_of_the_nether", Rarity.EPIC);



	public static final RegistryEntry<Item> AAF_BOOK_SCRIPTURE = register(
			"aaf_scripture",
			() -> new BookScriptureItem(
					Rarity.EPIC,
					Component.literal(" §7Advanced Alloy Forge"),
					List.of(),
                    AAFScriptureGUIMenu::new
			)
	);







	public static final RegistryEntry<Item> MANDARIN_SIGN = registerItem("mandarin_sign",
			(properties) -> new SignItem(properties,
					SurvivalReimaginedModBlocks.MANDARIN_SIGN.get(),
					SurvivalReimaginedModBlocks.MANDARIN_WALL_SIGN.get()
				) {
				@Override
				public String getDescriptionId() {
					return "item.survival_reimagined.mandarin_sign";
				}
			}
	);
	public static final RegistryEntry<Item> MANDARIN_HANGING_SIGN = registerItem("mandarin_hanging_sign",
			(properties) -> new HangingSignItem(
					SurvivalReimaginedModBlocks.MANDARIN_HANGING_SIGN.get(),
					SurvivalReimaginedModBlocks.MANDARIN_CEILING_HANGING_SIGN.get(),
					properties
			) {
				@Override
				public String getDescriptionId() {
					return "item.survival_reimagined.mandarin_hanging_sign";
				}
			}
	);
	public static final RegistryEntry<Item> RADIATED_SIGN = registerItem("radiant_sign",
			(properties) -> new SignItem(properties,
					SurvivalReimaginedModBlocks.RADIATED_SIGN.get(),
					SurvivalReimaginedModBlocks.RADIATED_WALL_SIGN.get()
			) {
				@Override
				public String getDescriptionId() {
					return "item.survival_reimagined.radiant_sign";
				}
			}
	);
	public static final RegistryEntry<Item> RADIATED_HANGING_SIGN = registerItem("radiant_hanging_sign",
			(properties) -> new HangingSignItem(
					SurvivalReimaginedModBlocks.RADIATED_HANGING_SIGN.get(),
					SurvivalReimaginedModBlocks.RADIATED_CEILING_HANGING_SIGN.get(),
					properties
			) {
				@Override
				public String getDescriptionId() {
					return "item.survival_reimagined.radiant_hanging_sign";
				}
			}
	);
	public static final RegistryEntry<Item> WISTERIA_SIGN = registerItem("wisteria_sign",
			(properties) -> new SignItem(properties,
					SurvivalReimaginedModBlocks.WISTERIA_SIGN.get(),
					SurvivalReimaginedModBlocks.WISTERIA_WALL_SIGN.get()
			) {
				@Override
				public String getDescriptionId() {
					return "item.survival_reimagined.wisteria_sign";
				}
			}
	);
	public static final RegistryEntry<Item> WISTERIA_HANGING_SIGN = registerItem("wisteria_hanging_sign",
			(properties) -> new HangingSignItem(
					SurvivalReimaginedModBlocks.WISTERIA_HANGING_SIGN.get(),
					SurvivalReimaginedModBlocks.WISTERIA_CEILING_HANGING_SIGN.get(),
					properties
			) {
				@Override
				public String getDescriptionId() {
					return "item.survival_reimagined.wisteria_hanging_sign";
				}
			}
	);




	private SurvivalReimaginedModItems() {
	}

	private static RegistryEntry<Item> registerItem(String id, Function<Item.Properties, Item> itemFactory) {
		Item.Properties properties = new Item.Properties();
		return register(id, () -> itemFactory.apply(properties));
	}

	private static Item registerTool(String name, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, name), item);
	}


	private static RegistryEntry<Item> simple(String path) {
		return register(path, () -> new Item(new Item.Properties()));
	}
	private static RegistryEntry<Item> simpleProperties(String path, Item.Properties properties) {
		return register(path, () -> new Item(properties));
	}



	private static RegistryEntry<Item> damageOnCrafting(String path, Item.Properties properties, ItemStack retval) {
		return register(path, () -> new Item(properties) {
			@Override
			public boolean hasCraftingRemainingItem() {
				return true;
			}

			@Override
			public ItemStack getRecipeRemainder(ItemStack stack) {
				ItemStack copy = stack.copy();
				copy.setDamageValue(copy.getDamageValue() + 1);

				if (copy.getDamageValue() >= copy.getMaxDamage()) {
					return retval != null ? retval.copy() : ItemStack.EMPTY;
				}
				return copy;
			}
		});
	}

	private static RegistryEntry<Item> food(String path, int nutrition, float saturation) {
		return register(path, () -> new SpoilingFoodItem(new Item.Properties()
				.food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build())));
	}

	private static RegistryEntry<Item> stableFood(String path, int nutrition, float saturation) {
		return register(path, () -> new Item(new Item.Properties()
				.food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build())));
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

	private static RegistryEntry<Item> uniqueItem(String path, Rarity rarity) {
		return register(path, () -> new Item(new Item.Properties().rarity(rarity)));
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
