package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

public final class SurvivalReimaginedModTabs {
	public static final RegistryEntry<CreativeModeTab> SURVIVAL_REBORN = registerTab();

	private SurvivalReimaginedModTabs() {
	}

	private static RegistryEntry<CreativeModeTab> registerTab() {
		var id = SurvivalReimaginedMod.asResource("survival_reborn");
		CreativeModeTab tab = FabricItemGroup.builder()
				.title(Component.translatable("item_group.survival_reimagined.survival_reborn"))
				.icon(() -> new ItemStack(SurvivalReimaginedModItems.TIN_INGOT.get()))
				.displayItems((parameters, output) -> {
					output.accept(SurvivalReimaginedModItems.FLINTBLOCK.get());
					output.accept(SurvivalReimaginedModItems.STONE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.FLINT_TOOL.get());
					output.accept(SurvivalReimaginedModItems.HEMP_FIBER.get());
					output.accept(SurvivalReimaginedModItems.SMALL_COAL_CHUNK.get());
					output.accept(SurvivalReimaginedModItems.COPPER_NUGGET.get());
					output.accept(SurvivalReimaginedModItems.COPPER_CHISEL.get());
					output.accept(SurvivalReimaginedModItems.STONE_HAMMER.get());
					output.accept(SurvivalReimaginedModItems.COPPER_CHUNK.get());
					output.accept(SurvivalReimaginedModItems.ANDESITE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.GRANITE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.DIORITE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.DRIPSTONE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.CALCITE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.TUFF_ROCK.get());
					output.accept(SurvivalReimaginedModItems.MOSSY_STONE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.NETHERRACK_ROCK.get());
					output.accept(SurvivalReimaginedModItems.END_STONE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.BLACKSTONE_ROCK.get());
					output.accept(SurvivalReimaginedModItems.BASALT_ROCK.get());
					output.accept(SurvivalReimaginedModItems.DEEPSLATE_ROCK.get());

					output.accept(SurvivalReimaginedModItems.FORGE.get());
					output.accept(SurvivalReimaginedModItems.METAL_REFINING_TABLE.get());
					output.accept(SurvivalReimaginedModItems.TIN_ORE.get());
					output.accept(SurvivalReimaginedModItems.DEEPSLATE_TIN_ORE.get());
					output.accept(SurvivalReimaginedModItems.RAW_TIN.get());
					output.accept(SurvivalReimaginedModItems.TIN_CHUNK.get());
					output.accept(SurvivalReimaginedModItems.TIN_NUGGET.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_TIN.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_COPPER.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_IRON.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_GOLD.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_BRONZE.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_INGOT.get());
					output.accept(SurvivalReimaginedModItems.TIN_INGOT.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_RAW_TIN.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_TIN.get());

					output.accept(SurvivalReimaginedModItems.ANTHRACITE.get());
					output.accept(SurvivalReimaginedModItems.SMALL_ANTHRACITE.get());
					output.accept(SurvivalReimaginedModItems.ANTHRACITE_BLOCK.get());
					output.accept(SurvivalReimaginedModItems.LIGINITE.get());
					output.accept(SurvivalReimaginedModItems.SMALL_LIGINITE.get());
					output.accept(SurvivalReimaginedModItems.LIGINITE_BLOCK.get());

					output.accept(SurvivalReimaginedModItems.RAW_MANGANESE.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_MANGANESE.get());
					output.accept(SurvivalReimaginedModItems.MANGANESE_INGOT.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_RAW_MANGANESE.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_MANGANESE.get());

					output.accept(SurvivalReimaginedModItems.ROUGH_STEEL.get());
					output.accept(SurvivalReimaginedModItems.STEEL_INGOT.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_STEEL.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_BRONZE.get());

					output.accept(SurvivalReimaginedModItems.SMALL_STICK.get());
					output.accept(SurvivalReimaginedModItems.WOOD_INGOT.get());
					output.accept(SurvivalReimaginedModItems.WOODEN_HAMMER.get());
					output.accept(SurvivalReimaginedModItems.WOODEN_SAW.get());
					output.accept(SurvivalReimaginedModItems.WOODEN_KNIFE.get());
					output.accept(SurvivalReimaginedModItems.INGOT_CLAY_MOLD.get());
					output.accept(SurvivalReimaginedModItems.CLAY_SWORD_BLADE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.CLAY_PICKAXE_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.CLAY_AXE_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.CLAY_SHOVEL_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.CLAY_HOE_BLADE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.CLAY_HAMMER_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.CLAY_SAW_BLADE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.CLAY_KNIFE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.INGOT_MOLD.get());
					output.accept(SurvivalReimaginedModItems.SWORD_BLADE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.PICKAXE_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.AXE_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.SHOVEL_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.HOE_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.HAMMER_HEAD_MOLD.get());
					output.accept(SurvivalReimaginedModItems.SAW_BLADE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.KNIFE_BLADE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.WOODEN_PLATE.get());
					output.accept(SurvivalReimaginedModItems.CLAY_METAL_PLATE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.METAL_PLATE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_PLATE.get());
					output.accept(SurvivalReimaginedModItems.STEEL_PLATE.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_PLATED_DIAMOND.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_NETHERITE.get());
					output.accept(SurvivalReimaginedModItems.RAW_TITANIUM.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_TITANIUM.get());
					output.accept(SurvivalReimaginedModItems.TITANIUM_INGOT.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_RAW_TITANIUM.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_TITANIUM.get());
					output.accept(SurvivalReimaginedModItems.RAW_URANINITE.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_URANIUM.get());
					output.accept(SurvivalReimaginedModItems.URANIUM_INGOT.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_RAW_URANINITE.get());
					output.accept(SurvivalReimaginedModItems.BLOCK_OF_URANIUM.get());
					output.accept(SurvivalReimaginedModItems.ROUGH_TURANITE.get());
					output.accept(SurvivalReimaginedModItems.TURANITE_INGOT.get());
					output.accept(SurvivalReimaginedModItems.TURANITE_BLOCK.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_PLATED_INGOT.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_PLATE.get());
					output.accept(SurvivalReimaginedModItems.NETHERITE_PLATE.get());
					output.accept(SurvivalReimaginedModItems.QUICK_LIME.get());
					output.accept(SurvivalReimaginedModItems.DARK_CINDER_POWDER.get());
					output.accept(SurvivalReimaginedModItems.DARK_CINDER_COAL.get());
					output.accept(SurvivalReimaginedModItems.SILVER_INGOT.get());
					output.accept(SurvivalReimaginedModItems.WOODEN_RUNE.get());
					output.accept(SurvivalReimaginedModItems.RUNE_CLAY_MOLD.get());
					output.accept(SurvivalReimaginedModItems.RUNE_MOLD.get());
					output.accept(SurvivalReimaginedModItems.EMPTY_GOLD_RUNE.get());
					output.accept(SurvivalReimaginedModItems.EMPTY_SILVER_RUNE.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_SWORD_BLADE.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_PICKAXE_HEAD.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_AXE_HEAD.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_SHOVEL_HEAD.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_HOE_BLADE.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_HAMMER_HEAD.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_SAW_BLADE.get());
					output.accept(SurvivalReimaginedModItems.DIAMOND_KNIFE_BLADE.get());

					output.accept(SurvivalReimaginedModItems.BRONZE_SWORD_BLADE.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_PICKAXE_HEAD.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_AXE_HEAD.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_SHOVEL_HEAD.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_HOE_BLADE.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_HAMMER_HEAD.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_SAW_BLADE.get());
					output.accept(SurvivalReimaginedModItems.BRONZE_KNIFE_BLADE.get());

					output.accept(SurvivalReimaginedModItems.STEEL_SWORD_BLADE.get());
					output.accept(SurvivalReimaginedModItems.STEEL_PICKAXE_HEAD.get());
					output.accept(SurvivalReimaginedModItems.STEEL_AXE_HEAD.get());
					output.accept(SurvivalReimaginedModItems.STEEL_SHOVEL_HEAD.get());
					output.accept(SurvivalReimaginedModItems.STEEL_HOE_BLADE.get());
					output.accept(SurvivalReimaginedModItems.STEEL_HAMMER_HEAD.get());
					output.accept(SurvivalReimaginedModItems.STEEL_SAW_BLADE.get());
					output.accept(SurvivalReimaginedModItems.STEEL_KNIFE_BLADE.get());
				})
				.build();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab);
		return new RegistryEntry<>(id, tab);
	}

	public static void register() {
		// Forces class initialization.
	}
}
