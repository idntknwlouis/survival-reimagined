package net.mcreator.survivalreimagined.network;
import net.minecraft.nbt.CompoundTag;

public class WorldVariables {
    public boolean _syncDirty = false;

    public double HeartBeat = 0;
    public double EffectDanger = 0;
    public double SkyboxAlpha = 0;
    public double SkyboxClock = 0.0;
    public double BloodmoonFog = 0;
    public double FogStart = 0;
    public double FogEnd = 0;
    public double FruitTree = 0;
    public boolean GhostSpawned = false;
    public double GhostSpawnChance = 0;
    public double GhostSpawnNumberVariable = 0;
    public double GhostDespawnTimer = 0;

    public void read(CompoundTag nbt) {
        HeartBeat = nbt.getDouble("HeartBeat");
        EffectDanger = nbt.getDouble("EffectDanger");
        SkyboxAlpha = nbt.getDouble("SkyboxAlpha");
        SkyboxClock = nbt.getDouble("SkyboxClock");
        BloodmoonFog = nbt.getDouble("BloodmoonFog");
        FogStart = nbt.getDouble("FogStart");
        FogEnd = nbt.getDouble("FogEnd");
        FruitTree = nbt.getDouble("FruitTree");
        GhostSpawned = nbt.getBoolean("GhostSpawned");
        GhostSpawnChance = nbt.getDouble("GhostSpawnChance");
        GhostSpawnNumberVariable = nbt.getDouble("GhostSpawnNumberVariable");
        GhostDespawnTimer = nbt.getDouble("GhostDespawnTimer");
    }
    public CompoundTag write(CompoundTag nbt) {
        nbt.putDouble("HeartBeat", this.HeartBeat);
        nbt.putDouble("EffectDanger", this.EffectDanger);
        nbt.putDouble("SkyboxAlpha", this.SkyboxAlpha);
        nbt.putDouble("SkyboxClock", this.SkyboxClock);
        nbt.putDouble("BloodmoonFog", this.BloodmoonFog);
        nbt.putDouble("FogStart", this.FogStart);
        nbt.putDouble("FodEnd", this.FogEnd);
        nbt.putDouble("FruitTree", this.FruitTree);
        nbt.putBoolean("GhostSpawned", this.GhostSpawned);
        nbt.putDouble("GhostSpawnChance", this.GhostSpawnChance);
        nbt.putDouble("GhostSpawnNumberVariable", this.GhostSpawnNumberVariable);
        nbt.putDouble("GhostDespawnTimer", this.GhostDespawnTimer);
        return nbt;
    }
}
