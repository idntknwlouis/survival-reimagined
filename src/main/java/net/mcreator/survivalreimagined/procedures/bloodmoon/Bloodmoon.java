package net.mcreator.survivalreimagined.procedures.bloodmoon;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;

public class Bloodmoon {
    private static void onWorldTick(ServerLevel level) {
        execute(level);
    }
    public static void execute(ServerLevel level) {
        if (level == null || level.dimension() != ServerLevel.OVERWORLD) return;

        var vars = SurvivalReimaginedModVariables.MapVariables.get(level);

        long dayTime = level.dayTime();
        long timeOfDay = Math.floorMod(dayTime, 24000L);

        boolean isDay = timeOfDay < 12000;

        if (isDay) {
            if (!vars.isDay || vars.isBloodMoon || vars.ValueSetBloodMoon || vars.BloodMoon != 0) {
                vars.isDay = true;
                vars.isBloodMoon = false;
                vars.ValueSetBloodMoon = false;
                vars.BloodMoon = 0;
                vars.BloodMoonChanceRan = 0;

                vars.setDirty();
                vars._syncDirty = true;
            }
            return;
        }
        vars.isDay = false;

        if (!vars.ValueSetBloodMoon) {
            vars.ValueSetBloodMoon = true;
            boolean startBloodMoon = false;

            if (SurvivalReimaginedModConfig.VALUES.blood_moon) {
                int phase = level.dimensionType().moonPhase(dayTime);

                double chance = switch (phase) {
                    case 0 -> SurvivalReimaginedModConfig.VALUES.full_moon_chance;
                    case 1, 2, 6, 7 -> SurvivalReimaginedModConfig.VALUES.gibbous_quarter_chance;
                    case 3, 4, 5 -> SurvivalReimaginedModConfig.VALUES.new_moon_crescent_chance;
                    default -> 0.0;
                };

                chance = Mth.clamp(chance, 0.0, 1.0);

                startBloodMoon = level.getRandom().nextFloat() < chance;
            }
            vars.isBloodMoon = startBloodMoon;
            vars.BloodMoonChanceRan = 1001;
            vars.setDirty();
            vars._syncDirty = true;
        }
    }
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(Bloodmoon::onWorldTick);
    }
}
