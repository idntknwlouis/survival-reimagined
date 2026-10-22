package net.mcreator.survivalreimagined.world.worldgen;

import com.sun.source.tree.Tree;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class SurvivalReimaginedModTreeGrowers {

    public static final ResourceKey<ConfiguredFeature<?, ?>> RADIATED_TREE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "radiated_tree")
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "apple_oak_tree_sapling")
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISTERIA_TREE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "wisteria_tree_1")
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANDARIN_TREE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "mandarin_tree_sapling")
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_CHERRY_TREE = ResourceKey.create(
      Registries.CONFIGURED_FEATURE,
      ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "rec_cherry_tree_sapling")
    );


    public static final TreeGrower RADIATED = new TreeGrower(
            "radiated_tree",
            Optional.empty(),
            Optional.of(RADIATED_TREE),
            Optional.empty()
    );
    public static final TreeGrower APPLE = new TreeGrower(
            "apple_oak_tree_sapling",
            Optional.empty(),
            Optional.of(APPLE_TREE),
            Optional.empty()
    );
    public static final TreeGrower WISTERIA = new TreeGrower(
            "wisteria_tree_1",
            Optional.empty(),
            Optional.of(WISTERIA_TREE),
            Optional.empty()
    );
    public static final TreeGrower MANDARIN = new TreeGrower(
            "mandarin_tree_sapling",
            Optional.empty(),
            Optional.of(MANDARIN_TREE),
            Optional.empty()
    );
    public static final TreeGrower RED_CHERRY = new TreeGrower(
      "rec_cherry_tree_sapling",
      Optional.empty(),
      Optional.of(RED_CHERRY_TREE),
      Optional.empty()
    );
}
