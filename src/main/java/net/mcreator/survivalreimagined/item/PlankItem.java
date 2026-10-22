package net.mcreator.survivalreimagined.item;

import net.minecraft.world.item.Item;

public class PlankItem extends Item {
    private final int burnTime;

    public PlankItem(Item.Properties properties, int burnTime) {
        super (properties);
        this.burnTime = burnTime;
    }

    public int getBurnTime() {
        return this.burnTime;
    }
}
