package net.mcreator.survivalreimagined.item;

import net.minecraft.world.item.Item;

public class BarkItem extends Item {
    private final int burnTime;

    public BarkItem(Item.Properties properties, int burnTime) {
        super (properties);
        this.burnTime = burnTime;
    }

    public int getBurnTime() {
        return this.burnTime;
    }
}
