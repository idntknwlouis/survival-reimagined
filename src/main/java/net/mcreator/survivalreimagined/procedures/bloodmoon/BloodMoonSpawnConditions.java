package net.mcreator.survivalreimagined.procedures.bloodmoon;

import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerLevelAccessor;

public class BloodMoonSpawnConditions {
    public static boolean execute(ServerLevelAccessor world) {
        ServerLevel level = world.getLevel();
        return SurvivalReimaginedModVariables.MapVariables.get(level).isBloodMoon;
    }
}
