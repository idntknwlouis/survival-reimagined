package net.mcreator.survivalreimagined.network;
import net.minecraft.nbt.CompoundTag;

public class PlayerVariables {
    public boolean _syncDirty = false;

    public double GasMaskDamage = 0;
    public double GasMaskHeal = 0;
    public double DiamondLogicNumber = 0;
    public boolean ZombificationImmune = false;
    public boolean PlayerPositionSet = false;
    public double NBTPercentage = 0;
    public double SpoilTimer = 0;
    public boolean HungerSprinting = false;
    public boolean HungerSwimming = false;
    public double ScrapeHandler = 0;
    public double ItemCount = 0;
    //public double WeightMediumItems = 0; <- Used in the experimental inventory weight system
    //public boolean HungryProc = false; <- Used in experimental custom animals systems such as Boar Family
    //public boolean Hungry = false;     <--|

    public void read(CompoundTag nbt) {
        if (nbt == null) return;
        this.GasMaskDamage = nbt.getDouble("GasMaskDamage");
        this.GasMaskHeal = nbt.getDouble("GasMaskHeal");
        this.DiamondLogicNumber = nbt.getDouble("DiamondLogicNumber");
        this.ZombificationImmune = nbt.getBoolean("ZombificationImmune");
        this.PlayerPositionSet = nbt.getBoolean("PlayerPositionSet");
        this.NBTPercentage = nbt.getDouble("NBTPercentage");
        this.SpoilTimer = nbt.getDouble("SpoilTimer");
        this.HungerSprinting = nbt.getBoolean("HungerSprinting");
        this.HungerSwimming = nbt.getBoolean("HungerSwimming");
        this.ScrapeHandler = nbt.getDouble("ScrapeHandler");
        this.ItemCount = nbt.getDouble("ItemCount");
        //this.WeightMediumItems = nbt.getDouble("WeightMediumItems");
        //this.HungryProc = nbt.getBoolean("HungryProc");
        //this.Hungry = nbt.getBoolean("Hungry");
    }
    public CompoundTag write(CompoundTag nbt) {
        nbt.putDouble("GasMaskDamage", this.GasMaskDamage);
        nbt.putDouble("GasMaskHeal", this.GasMaskHeal);
        nbt.putDouble("DiamondLogicNumber", this.DiamondLogicNumber);
        nbt.putBoolean("ZombificationImmune", this.ZombificationImmune);
        nbt.putBoolean("PlayerPositionSet", this.PlayerPositionSet);
        nbt.putDouble("NBTPercentage", this.NBTPercentage);
        nbt.putDouble("SpoilTimer", this.SpoilTimer);
        nbt.putBoolean("HungerSprinting", this.HungerSprinting);
        nbt.putBoolean("HungerSwimming", this.HungerSwimming);
        nbt.putDouble("ScrapeHandler", this.ScrapeHandler);
        nbt.putDouble("ItemCount", this.ItemCount);
        //nbt.putDouble("WeightMediumItems", this.WeightMediumItems);
        //nbt.putBoolean("HungryProc", this.HungryProc);
        //nbt.putBoolean("Hungry", this.Hungry);
        return nbt;
    }
}
