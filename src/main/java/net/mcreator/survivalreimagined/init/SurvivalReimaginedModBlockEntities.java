package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.block.entity.*;
import net.mcreator.survivalreimagined.block.fruit.FruitBlock;
import net.mcreator.survivalreimagined.block.fruit.FruitBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import java.util.function.Supplier;

public final class SurvivalReimaginedModBlockEntities {
    public static final RegistryEntry<BlockEntityType<PalmLeavesBlockEntity>> PALM_LEAVES = register(
            "palm_leaves",
            BlockEntityType.Builder.of(PalmLeavesBlockEntity::new, SurvivalReimaginedModBlocks.PALM_LEAVES.get()).build(null)
    );
    public static final RegistryEntry<BlockEntityType<BananaLeavesBlockEntity>> BANANA_LEAVES = register(
            "banana_leaves",
            BlockEntityType.Builder.of(BananaLeavesBlockEntity::new, SurvivalReimaginedModBlocks.BANANA_LEAVES.get()).build(null)
    );
    public static final RegistryEntry<BlockEntityType<BananaGrowBlockEntity>> BANANA_GROW_BLOCK = register(
            "banana_grow_block",
            BlockEntityType.Builder.of(BananaGrowBlockEntity::new, SurvivalReimaginedModBlocks.BANANA_GROW_BLOCK.get()).build(null)
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
    public static final RegistryEntry<BlockEntityType<CropGrowthBlockEntity>> CROP_GROWTH = register(
            "crop_growth",
            BlockEntityType.Builder.of(
                    CropGrowthBlockEntity::new,
                    SurvivalReimaginedModBlocks.RYE_SEEDS.get(),
                    SurvivalReimaginedModBlocks.SPELT_SEEDS.get(),
                    SurvivalReimaginedModBlocks.HEMP.get(),
                    SurvivalReimaginedModBlocks.WHEAT_CROP.get(),
                    SurvivalReimaginedModBlocks.POTATOES.get(),
                    SurvivalReimaginedModBlocks.BEETROOT.get(),
                    SurvivalReimaginedModBlocks.CARROT.get(),
                    SurvivalReimaginedModBlocks.STRAWBERRY_PLANT.get(),
                    SurvivalReimaginedModBlocks.RASPBERRY_PLANT.get(),
                    SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get()
            ).build(null)
    );
    public static BlockEntityType<FruitBlockEntity> FRUIT_BLOCK_ENTITY;

    public static Supplier<BlockEntityType<?>> getSupplier() {
        return () -> FRUIT_BLOCK_ENTITY;
    }

    private SurvivalReimaginedModBlockEntities() {
    }

    private static <T extends BlockEntity> RegistryEntry<BlockEntityType<T>> register(String path, BlockEntityType<T> type) {
        var id = SurvivalReimaginedMod.asResource(path);
        BlockEntityType<T> blockEntityType = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, type);
        return new RegistryEntry<>(id, blockEntityType);
    }

    public static void register() {
        FRUIT_BLOCK_ENTITY = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "fruit_block_entity"),
                BlockEntityType.Builder.of((pos, state) -> {
                            if (state.getBlock() instanceof FruitBlock fruitBlock) {
                                return (FruitBlockEntity) fruitBlock.newBlockEntity(pos, state);
                            }
                            return null;
                        },
                        SurvivalReimaginedModBlocks.APPLE_FRUIT.get(),
                        SurvivalReimaginedModBlocks.MANDARIN_FRUIT.get(),
                        SurvivalReimaginedModBlocks.BANANA_FRUIT.get(),
                        SurvivalReimaginedModBlocks.RED_CHERRIES_FRUIT.get()
                ).build(null)
        );
    }
}
