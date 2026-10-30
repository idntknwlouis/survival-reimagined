package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.procedures.AddFertilizer;
import net.mcreator.survivalreimagined.procedures.ApplyRadiation;
import net.mcreator.survivalreimagined.procedures.BrokenLeg;
import net.mcreator.survivalreimagined.procedures.FertilitySoilTilling;
import net.mcreator.survivalreimagined.procedures.bloodmoon.BloodMoonAnnouncement;
import net.mcreator.survivalreimagined.procedures.bloodmoon.Bloodmoon;
import net.mcreator.survivalreimagined.procedures.loot_tables.CrimsonThreadDrops;
import net.mcreator.survivalreimagined.util.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;

public class SurvivalReimaginedModProcedures {
    public static void register() {
        ServerTickEvents.START_SERVER_TICK.register((MinecraftServer server) -> {
            for (ServerLevel world : server.getAllLevels()) {
                for (Entity entity : world.getAllEntities()) {
                    if (entity != null && entity.isAlive()) {
                        ApplyRadiation.execute(world, entity);
                    }
                }
            }
        });
        RuneEffects.register();
        CropEffects.register();
        CrimsonThreadDrops.register();
        FertilitySoilTilling.register();
        AddFertilizer.register();
        HideTooltip.register();
        FertilizerTooltip.register();


        //Bloodmoon
        Bloodmoon.register();
        BloodMoonAnnouncement.register();

        ServerLivingEntityEvents.ALLOW_DAMAGE.register(
                ((livingEntity, damageSource, amount) -> {
                    if (damageSource.is(DamageTypes.FALL) && !livingEntity.level().isClientSide()) {
                        System.out.println(
                                "FALL EVENT: " + livingEntity.getName().getString()
                                        + " | damage = " + amount
                                        + " | fallDistance = " + livingEntity.fallDistance
                        );
                        double fallDistance = livingEntity.fallDistance;
                        BrokenLeg.onEntityFall(livingEntity, fallDistance);
                    }
                    return true;
                })
        );



    }
}
