package net.mcreator.survivalreimagined.procedures.bloodmoon;

import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class BloodMoonSpawnConditions {
    public static boolean execute(LevelAccessor world) {
        if (world instanceof ServerLevel level) {
            return SurvivalReimaginedModVariables.MapVariables.get(level).isBloodMoon;
        }
        return false;
    }
}
