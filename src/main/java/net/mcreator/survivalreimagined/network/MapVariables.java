package net.mcreator.survivalreimagined.network;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class MapVariables {
    public boolean _syncDirty = false;

    public boolean BloodMoonTimer = false;
    public boolean AnnouncementPlayed = false;
    public boolean sculk_hearts = false;
    public double MPT_Time = 0;
    public double BloodMoonChanceRan = 0;
    public boolean isBloodMoon = false;
    public double BloodMoon = 0;
    public boolean ValueSetBloodMoon = false;
    public boolean isDay = false;
    public ItemStack MoldOutput = ItemStack.EMPTY;
    public boolean BunkerPlaced = false;


    public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
        BloodMoonTimer = nbt.getBoolean("BloodMoonTimer");
        AnnouncementPlayed = nbt.getBoolean("AnnouncementPlayed");
        sculk_hearts = nbt.getBoolean("sculk_hearts");
        MPT_Time = nbt.getDouble("MPT_Time");
        BloodMoonChanceRan = nbt.getDouble("BloodMoonChanceRan");
        isBloodMoon = nbt.getBoolean("isBloodMoon");
        BloodMoon = nbt.getDouble("BloodMoon");
        ValueSetBloodMoon = nbt.getBoolean("ValueSetBloodMoon");
        isDay = nbt.getBoolean("isDay");
        MoldOutput = ItemStack.parseOptional(lookupProvider, nbt.getCompound("MoldOutput"));
        BunkerPlaced = nbt.getBoolean("BunkerPlaced");

    }
    public CompoundTag write(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
        nbt.putBoolean("BloodMoonTimer", BloodMoonTimer);
        nbt.putBoolean("AnnouncementPlayed", AnnouncementPlayed);
        nbt.putBoolean("sculk_hearts", sculk_hearts);
        nbt.putDouble("MPT_Time", MPT_Time);
        nbt.putDouble("BloodMoonChanceRan", BloodMoonChanceRan);
        nbt.putBoolean("isBloodMoon", isBloodMoon);
        nbt.putDouble("BloodMoon", BloodMoon);
        nbt.putBoolean("ValueSetBloodMoon", ValueSetBloodMoon);
        nbt.putBoolean("isDay", isDay);
        nbt.put("MoldOutput", MoldOutput.saveOptional(lookupProvider));
        nbt.putBoolean("BunkerPlaced", BunkerPlaced);
        return nbt;
    }
}
