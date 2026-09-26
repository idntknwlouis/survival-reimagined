package net.mcreator.survivalreimagined.procedures.bloodmoon;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public class Bloodmoon {
    private static void onWorldTick(ServerLevel level) {
        execute(level);
    }
    public static void execute(LevelAccessor world) {
        if (world == null) return;

        if(!(world instanceof ServerLevel serverLevel)) {
            return;
        }

        long dayTime = serverLevel.dayTime();


        if (dayTime % 24000 >= 0 && dayTime % 24000 <= 12000) {
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isDay = true;
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
        } else {
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isDay = false;
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
        }

        if (!SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isDay) {
            if (!SurvivalReimaginedModVariables.MapVariables.get(serverLevel).ValueSetBloodMoon) {
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).ValueSetBloodMoon = true;
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoonChanceRan = Math.round(Mth.nextInt(RandomSource.create(), 0, 1000));
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
            }
            if (SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoon < 1000) {
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoon = Math.round(SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoon + 1);
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
            }
            if (SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoon == SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoonChanceRan) {
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoonChanceRan = 1001;
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();

                int moonPhase = serverLevel.dimensionType().moonPhase(dayTime);

                if (moonPhase == 1) {
                    if (Math.random() < (double) SurvivalReimaginedModConfig.VALUES.full_moon_chance) {
                        SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isBloodMoon = true;
                        SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
                    }
                } else if (moonPhase == 2 || moonPhase == 3 || moonPhase == 7 || moonPhase == 8) {
                    if (Math.random() < (double) SurvivalReimaginedModConfig.VALUES.gibbous_quarter_chance) {
                        SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isBloodMoon = true;
                        SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
                    }
                } else if (moonPhase == 4 || moonPhase == 5 || moonPhase == 6) {
                    if (Math.random() < (double) SurvivalReimaginedModConfig.VALUES.new_moon_crescent_chance) {
                        SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isBloodMoon = true;
                        SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
                    }
                }
            }
        } else {
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoonChanceRan = 0;
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).BloodMoon = 0;
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isBloodMoon = false;
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).ValueSetBloodMoon = false;
            SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
        }
    }



    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(Bloodmoon::onWorldTick);
    }
}
