package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.procedures.*;
import net.mcreator.survivalreimagined.procedures.advancements.*;
import net.mcreator.survivalreimagined.procedures.bloodmoon.*;
import net.mcreator.survivalreimagined.procedures.loot_tables.CrimsonThreadDrops;
import net.mcreator.survivalreimagined.procedures.loot_tables.HorseCarcassSpawner;
import net.mcreator.survivalreimagined.procedures.loot_tables.PointedStonesLoot;
import net.mcreator.survivalreimagined.procedures.loot_tables.StickDrops;
import net.mcreator.survivalreimagined.util.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;

public class SurvivalReimaginedModProcedures {
    public static void register() {
        //Progression Events
        CropDrops.register();
        FertilitySoilTilling.register();
        AddFertilizer.register();
        DisableNetherPortal.register();
        FlintScrape.register();
        LeavesDropCondition.register();
        LogsDrops.register();
        PlantFiberDrops.register();
        PlantHarvested.register();
        StickDrops.register();
        StrippedLogs.register();
        StrippedLogToCraftingTable.register();

        //Survival/Difficulty Events
        EntityJumped.register();
        EntitySprinting.register();
        EntitySwimming.register();
        InflictBleeding.register();
        KilledBySculk.register();
        MechanicOverrides.register();
        GoldenAppleEaten.register();
        StandingOnSculk.register();

        //Mob Drops/LootTable
        CrimsonThreadDrops.register();
        EntityHasItem.register();
        LootExtended.register();
        PointedStonesLoot.register();
        HorseCarcassSpawner.register();

        //Advancements
        AnimalKilled.register();
        CelestialAdvancements.register();
        ConqueredAdvancement.register();
        AttackedByZombie.register();
        EmbeddedObsidianBroken.register();
        FullyUpgradedForge.register();
        LayersADV.register();
        CraftedItemADV.register();
        ProgressionStart.register();
        RuneAcquired.register();

        //Blood Moon
        Bloodmoon.register();
        BloodMoonAnnouncement.register();
        CantSleep.register();

        //Other - Client
        GemOreXP.register();
        GemBlockBroken.register();
        GemBlockPlaced.register();


        ServerLivingEntityEvents.ALLOW_DAMAGE.register(
                ((livingEntity, damageSource, amount) -> {
                    if (damageSource.is(DamageTypes.FALL) && !livingEntity.level().isClientSide()) {
                        double fallDistance = livingEntity.fallDistance;
                        BrokenLeg.onEntityFall(livingEntity, fallDistance);
                    }
                    return true;
                })
        );
        ServerTickEvents.START_SERVER_TICK.register((MinecraftServer server) -> {
            for (ServerLevel world : server.getAllLevels()) {
                for (Entity entity : world.getAllEntities()) {
                    if (entity != null && entity.isAlive()) {
                        ApplyRadiation.onServerTick(world, entity);
                        HeartbeatFearDamageTick.onEntityTick(world, entity);
                    }
                }
            }
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                DarknessEffectApplied.onPlayerTick(player);
            }
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            DisableSpawning.onEntityTick(entity, world);
        });
    }
}
