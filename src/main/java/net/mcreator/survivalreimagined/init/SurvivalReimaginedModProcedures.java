package net.mcreator.survivalreimagined.init;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.procedures.ApplyRadiation;
import net.mcreator.survivalreimagined.procedures.bloodmoon.BloodMoonAnnouncement;
import net.mcreator.survivalreimagined.procedures.bloodmoon.Bloodmoon;
import net.mcreator.survivalreimagined.util.CrimsonThreadDrops;
import net.mcreator.survivalreimagined.util.CropEffects;
import net.mcreator.survivalreimagined.util.RuneEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class SurvivalReimaginedModProcedures implements ModInitializer {

    @Override
    public void onInitialize() {
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

        //Bloodmoon
        Bloodmoon.register();
        BloodMoonAnnouncement.register();
    }
}
