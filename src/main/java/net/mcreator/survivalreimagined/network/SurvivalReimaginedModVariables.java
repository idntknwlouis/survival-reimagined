package net.mcreator.survivalreimagined.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SurvivalReimaginedModVariables {
    public static double OceansWrathDamageMultiplier = 0;
    public static AttributeModifier OceansWrath = null;

    private static final Map<UUID, PlayerVariables> PLAYER_VARIABLES_MAP = new ConcurrentHashMap<>();
    private static PlayerVariables getPlayerVariables(ServerPlayer player) {
        return PLAYER_VARIABLES_MAP.computeIfAbsent(player.getUUID(), id -> new PlayerVariables());
    }

    public static void register() {


        PayloadTypeRegistry.playS2C().register(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server ) -> {
            ServerPlayer player = handler.getPlayer();
            syncPlayerVariables(player);
            syncWorldData(player);
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            PLAYER_VARIABLES_MAP.remove(handler.getPlayer().getUUID());
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerVariables original = PLAYER_VARIABLES_MAP.getOrDefault(oldPlayer.getUUID(), new PlayerVariables());
            PlayerVariables clone = new PlayerVariables();

            clone.GasMaskDamage = original.GasMaskDamage;
            clone.GasMaskHeal = original.GasMaskHeal;
            clone.DiamondLogicNumber = original.DiamondLogicNumber;
            clone.ZombificationImmune = original.ZombificationImmune;
            clone.PlayerPositionSet = original.PlayerPositionSet;
            clone.NBTPercentage = original.NBTPercentage;


            if (alive) {
                clone.SpoilTimer = original.SpoilTimer;
                clone.HungerSprinting = original.HungerSprinting;
                clone.HungerSwimming = original.HungerSwimming;
                clone.ScrapeHandler = original.ScrapeHandler;
                clone.ItemCount = original.ItemCount;
                //clone.WeightMediumItems = original.WeightMediumItems;
                //clone.HungryProc = original.HungryProc;
                //clone.Hungry = original.Hungry;
            }
            PLAYER_VARIABLES_MAP.put(newPlayer.getUUID(), clone);
            syncPlayerVariables(newPlayer);
        });
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            syncPlayerVariables(player);

            SavedData worldData = WorldVariables.get(destination);
            if (worldData != null) {
                ServerPlayNetworking.send(player, new SavedDataSyncMessage(1, worldData));
            }
        });

        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                PlayerVariables vars = getPlayerVariables(player);
                if (vars._syncDirty) {
                    syncPlayerVariables(player);
                    vars._syncDirty = false;
                }

            }
        });

        ServerTickEvents.END_WORLD_TICK.register(level -> {
            WorldVariables worldVariables = WorldVariables.get(level);
            if (worldVariables._syncDirty) {
                for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
                    ServerPlayNetworking.send(player, new SavedDataSyncMessage(1, worldVariables));
                }
                worldVariables._syncDirty = false;
            }

            MapVariables mapVariables = MapVariables.get(level);
            if (mapVariables._syncDirty) {
                for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
                    ServerPlayNetworking.send(player, new SavedDataSyncMessage(0, mapVariables));
                }
                mapVariables._syncDirty = false;
            }
        });
    }
    public static void syncPlayerVariables(ServerPlayer player) {
        PlayerVariables vars = getPlayerVariables(player);
        ServerPlayNetworking.send(player, new PlayerVariablesSyncMessage(vars));
    }

    private static void syncWorldData(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        SavedData mapData = MapVariables.get(level);
        SavedData worldData = WorldVariables.get(level);
        if (mapData != null)
            ServerPlayNetworking.send(player, new SavedDataSyncMessage(0, mapData));
        if (worldData != null)
            ServerPlayNetworking.send(player, new SavedDataSyncMessage(1, worldData));
    }
    public static class WorldVariables extends SavedData {
        public static final String DATA_NAME = "survival_reimagined_worldvars";
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

        public static WorldVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
            WorldVariables data = new WorldVariables();
            data.read(tag, lookupProvider);
            return data;
        }
        public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
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

        @Override
        public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupHolder) {
            nbt.putDouble("HeartBeat", HeartBeat);
            nbt.putDouble("EffectDanger", EffectDanger);
            nbt.putDouble("SkyboxAlpha", SkyboxAlpha);
            nbt.putDouble("SkyboxClock", SkyboxClock);
            nbt.putDouble("BloodmoonFog", BloodmoonFog);
            nbt.putDouble("FogStart", FogStart);
            nbt.putDouble("FogEnd", FogEnd);
            nbt.putDouble("FruitTree", FruitTree);
            nbt.putBoolean("GhostSpawned", GhostSpawned);
            nbt.putDouble("GhostSpawnChance", GhostSpawnChance);
            nbt.putDouble("GhostSpawnNumberVariable", GhostSpawnNumberVariable);
            nbt.putDouble("GhostDespawnTimer", GhostDespawnTimer);
            return nbt;
        }

        public static WorldVariables get(ServerLevel level) {
            DimensionDataStorage storage = level.getDataStorage();
            return storage.computeIfAbsent(new SavedData.Factory<>(WorldVariables::new, WorldVariables::load, null), DATA_NAME);
        }
    }
    public static class MapVariables extends SavedData {
        public static final String DATA_NAME = "survival_reimagined_mapvars";
        public boolean _syncDirty = false;

        public static MapVariables get(ServerLevel level) {
            DimensionDataStorage storage = level.getServer().overworld().getDataStorage();
            return storage.computeIfAbsent(new SavedData.Factory<>(MapVariables::new, (tag, lookup) -> new MapVariables(), null), DATA_NAME);
        }
        @Override
        public CompoundTag save(CompoundTag tag, HolderLookup.Provider lookupProvider) {return tag;}
    }
}
