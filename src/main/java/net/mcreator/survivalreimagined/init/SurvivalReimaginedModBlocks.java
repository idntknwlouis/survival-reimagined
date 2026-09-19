package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.AndesiteRockBlockBlock;
import net.mcreator.survivalreimagined.block.BlockOfRawTinBlock;
import net.mcreator.survivalreimagined.block.BlockOfTinBlock;
import net.mcreator.survivalreimagined.block.CopperRockBlockBlock;
import net.mcreator.survivalreimagined.block.DeepslateTinOreBlock;
import net.mcreator.survivalreimagined.block.FlintblockBlock;
import net.mcreator.survivalreimagined.block.ForgeBlock;
import net.mcreator.survivalreimagined.block.IngotMoldBlock;
import net.mcreator.survivalreimagined.block.ToolMoldBlock;
import net.mcreator.survivalreimagined.block.ClayMoldBlock;
import net.mcreator.survivalreimagined.block.PlateBlock;
import net.mcreator.survivalreimagined.block.StoneRockBlocBlock;
import net.mcreator.survivalreimagined.block.SurfaceRockBlock;
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
	public static final RegistryEntry<Block> FORGE = register("forge", ForgeBlock::new);
	public static final RegistryEntry<Block> ANTHRACITE_BLOCK = register("anthracite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> LIGINITE_BLOCK = register("liginite_block", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_RAW_MANGANESE = register("block_of_raw_manganese", () -> new Block(BlockBehaviour.Properties.of().strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_MANGANESE = register("block_of_manganese", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5f, 6f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_STEEL = register("block_of_steel", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(6f, 7f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> BLOCK_OF_BRONZE = register("block_of_bronze", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4f).requiresCorrectToolForDrops()));
	public static final RegistryEntry<Block> INGOT_MOLD = register("ingot_mold", IngotMoldBlock::new);
	public static final RegistryEntry<Block> SWORD_BLADE_MOLD = register("sword_blade_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> PICKAXE_HEAD_MOLD = register("pickaxe_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> AXE_HEAD_MOLD = register("axe_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> SHOVEL_HEAD_MOLD = register("shovel_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> HOE_HEAD_MOLD = register("hoe_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> HAMMER_HEAD_MOLD = register("hammer_head_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> SAW_BLADE_MOLD = register("saw_blade_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> KNIFE_BLADE_MOLD = register("knife_blade_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> WOODEN_PLATE = register("wooden_plate", () -> new PlateBlock(SoundType.WOOD, 1.0F));
	public static final RegistryEntry<Block> METAL_PLATE_MOLD = register("metal_plate_mold", ToolMoldBlock::new);
	public static final RegistryEntry<Block> BRONZE_PLATE = register("bronze_plate", () -> new PlateBlock(SoundType.METAL, 2.5F));
	public static final RegistryEntry<Block> STEEL_PLATE = register("steel_plate", () -> new PlateBlock(SoundType.METAL, 3.5F));
	public static final RegistryEntry<Block> CLAY_METAL_PLATE_MOLD = register("clay_metal_plate_mold", () -> new ClayMoldBlock(() -> METAL_PLATE_MOLD.get(), true));

	public static final RegistryEntry<Block> INGOT_CLAY_MOLD = register("ingot_clay_mold", () -> new ClayMoldBlock(() -> INGOT_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_SWORD_BLADE_MOLD = register("clay_sword_blade_mold", () -> new ClayMoldBlock(() -> SWORD_BLADE_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_PICKAXE_HEAD_MOLD = register("clay_pickaxe_head_mold", () -> new ClayMoldBlock(() -> PICKAXE_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_AXE_HEAD_MOLD = register("clay_axe_head_mold", () -> new ClayMoldBlock(() -> AXE_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_SHOVEL_HEAD_MOLD = register("clay_shovel_head_mold", () -> new ClayMoldBlock(() -> SHOVEL_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_HOE_BLADE_MOLD = register("clay_hoe_blade_mold", () -> new ClayMoldBlock(() -> HOE_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_HAMMER_HEAD_MOLD = register("clay_hammer_head_mold", () -> new ClayMoldBlock(() -> HAMMER_HEAD_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_SAW_BLADE_MOLD = register("clay_saw_blade_mold", () -> new ClayMoldBlock(() -> SAW_BLADE_MOLD.get()));
	public static final RegistryEntry<Block> CLAY_KNIFE_MOLD = register("clay_knife_mold", () -> new ClayMoldBlock(() -> KNIFE_BLADE_MOLD.get()));

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
