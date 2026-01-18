package net.idntknwlouis.survivalreimaginedfabric.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.idntknwlouis.survivalreimaginedfabric.SurvivalReimaginedFabric;
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
                        entries.add(ModItems.ACACIAPLANK);

                    }).build());

    public static final ItemGroup SURVIVAL_REIMAGINED_COMPAT_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(SurvivalReimaginedFabric.MOD_ID, "survival_reimagined_compat_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.ALPHAOAKPLANK))
                    .displayName(Text.translatable("SR Compat Items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.ALPHAOAKPLANK);
                        entries.add(ModItems.BAOBABPLANK);
                        entries.add(ModItems.BLACKWOODPLANK);
                        entries.add(ModItems.BRIMWOODPLANK);

                    }).build());

    public static void registerItemGroups() {
        SurvivalReimaginedFabric.LOGGER.info("Registering Item Groups for " + SurvivalReimaginedFabric.MOD_ID);
    }
}
