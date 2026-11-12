package net.mcreator.survivalreimagined.item;

import net.minecraft.world.item.Item;

public class FuelItem extends Item {
    private final int burnTime;

    public FuelItem(Item.Properties properties, int burnTime) {
        super (properties);
        this.burnTime = burnTime;
    }

    public int getBurnTime() {
        return this.burnTime;
    }
}
