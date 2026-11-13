package net.mcreator.survivalreimagined.compat.jade;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.mcreator.survivalreimagined.block.entity.AdvancedAlloyForgeBlockEntity;
import net.mcreator.survivalreimagined.block.entity.ForgeBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.ClientViewGroup;
import snownee.jade.api.view.IClientExtensionProvider;
import snownee.jade.api.view.IServerExtensionProvider;
import snownee.jade.api.view.ItemView;
import snownee.jade.api.view.ViewGroup;

public enum ForgeInventoryJadeProvider implements
        IServerExtensionProvider<ItemStack>,
        IClientExtensionProvider<ItemStack, ItemView> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(
            "survival_reimagined", "forge_inventory");

    @Override
    public List<ViewGroup<ItemStack>> getGroups(Accessor<?> accessor) {
        List<ItemStack> visibleItems = new ArrayList<>();

        if (accessor.getTarget() instanceof AdvancedAlloyForgeBlockEntity forge) {
            addReactorRodIfPresent(visibleItems, forge.getItem(3));

            for (int slot = 4; slot <= 7; slot++) {
                ItemStack stack = forge.getItem(slot);
                if (AdvancedAlloyForgeBlockEntity.isUpgrade(stack)) {
                    visibleItems.add(stack.copy());
                }
            }
        } else if (accessor.getTarget() instanceof ForgeBlockEntity forge) {
            ItemStack fuel = forge.getItem(2);
            if (fuel.is(Items.COAL) || fuel.is(Items.CHARCOAL)) {
                visibleItems.add(fuel.copy());
            }
        }

        return visibleItems.isEmpty() ? List.of() : List.of(new ViewGroup<>(visibleItems));
    }

    private static void addReactorRodIfPresent(List<ItemStack> visibleItems, ItemStack rod) {
        if (rod.is(SurvivalReimaginedModItems.REACTOR_ROD.get())
                || rod.is(SurvivalReimaginedModItems.ADVANCED_REACTOR_ROD.get())
                || rod.is(SurvivalReimaginedModItems.DEPLETED_REACTOR_ROD.get())
                || rod.is(SurvivalReimaginedModItems.DRAINED_ADVANCED_REACTOR_ROD.get())) {
            visibleItems.add(rod.copy());
        }
    }

    @Override
    public List<ClientViewGroup<ItemView>> getClientGroups(
            Accessor<?> accessor,
            List<ViewGroup<ItemStack>> groups) {
        return ClientViewGroup.map(groups, ItemView::new, null);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}
