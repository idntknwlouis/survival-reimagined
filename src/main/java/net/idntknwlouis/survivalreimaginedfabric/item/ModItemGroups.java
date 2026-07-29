package net.idntknwlouis.survivalreimaginedfabric.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.idntknwlouis.survivalreimaginedfabric.SurvivalReimaginedFabric;
import net.idntknwlouis.survivalreimaginedfabric.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup SURVIVAL_REIMAGINED_ITEM_GROUPS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(SurvivalReimaginedFabric.MOD_ID, "survival_reimagined_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.ACACIAPLANK))
                    .displayName(Text.translatable("Survival Reimagined"))
                    .entries((displayContext, entries) -> {
                        // Blocks

                        // Ores
                        entries.add(ModBlocks.TIN_ORE);

                        // Planks
                        entries.add(ModItems.OAKPLANK);
                        entries.add(ModItems.DARKOAKPLANK);
                        entries.add(ModItems.SPRUCEPLANK);
                        entries.add(ModItems.BIRCHPLANK);
                        entries.add(ModItems.ACACIAPLANK);
                        entries.add(ModItems.JUNGLEPLANK);
                        entries.add(ModItems.CHERRYPLANK);
                        entries.add(ModItems.MANGROVEPLANK);
                        entries.add(ModItems.CRIMSONPLANK);
                        entries.add(ModItems.WARPEDPLANK);

                        // Barks
                        entries.add(ModItems.LARGEOAKBARK);
                        entries.add(ModItems.LARGEDARKOAKBARK);
                        entries.add(ModItems.LARGESPRUCEBARK);
                        entries.add(ModItems.LARGEBIRCHBARK);
                        entries.add(ModItems.LARGEACACIABARK);
                        entries.add(ModItems.LARGEJUNGLEBARK);
                        entries.add(ModItems.LARGECHERRYBARK);
                        entries.add(ModItems.LARGEMANGROVEBARK);
                        entries.add(ModItems.LARGECRIMSONBARK);
                        entries.add(ModItems.LARGEWARPEDBARK);

                    }).build());

    public static final ItemGroup SURVIVAL_REIMAGINED_COMPAT_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(SurvivalReimaginedFabric.MOD_ID, "survival_reimagined_compat_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.ALPHAOAKPLANK))
                    .displayName(Text.translatable("SR: Compat Items"))
                    .entries((displayContext, entries) -> {

                        // Planks
                        entries.add(ModItems.ALPHAOAKPLANK);
                        entries.add(ModItems.BAOBABPLANK);
                        entries.add(ModItems.BLACKWOODPLANK);
                        entries.add(ModItems.BRIMWOODPLANK);
                        entries.add(ModItems.COBALTPLANK);
                        entries.add(ModItems.CYPRESSPLANK);
                        entries.add(ModItems.DEADPLANK);
                        entries.add(ModItems.EUCALYPTUSPLANK);
                        entries.add(ModItems.JOSHUAPLANK);

                    }).build());

    public static void registerItemGroups() {
        SurvivalReimaginedFabric.LOGGER.info("Registering Item Groups for " + SurvivalReimaginedFabric.MOD_ID);
    }
}
