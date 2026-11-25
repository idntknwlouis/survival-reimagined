package net.mcreator.survivalreimagined.compat.jei.gui;

import java.util.List;

import net.minecraft.client.renderer.Rect2i;
import net.mcreator.survivalreimagined.client.gui.AdvancedAlloyForgeGUIScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;

/** Keeps JEI's ingredient list clear of the Advanced Alloy Forge's custom upgrade panel. */
public class AdvancedAlloyForgeGuiHandler implements IGuiContainerHandler<AdvancedAlloyForgeGUIScreen> {
    @Override
    public List<Rect2i> getGuiExtraAreas(AdvancedAlloyForgeGUIScreen screen) {
        return List.of(screen.getUpgradePanelArea());
    }
}
