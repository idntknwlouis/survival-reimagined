package net.mcreator.survivalreimagined.command;

import net.minecraft.server.level.ServerLevel;

import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.world.level.LevelAccessor;

public class BloodMoonCommand {
    public static void execute(LevelAccessor world) {
        if (world instanceof ServerLevel _serverLevel) {
            SurvivalReimaginedModVariables.MapVariables.get(_serverLevel).isBloodMoon = true;
            SurvivalReimaginedModVariables.MapVariables.get(_serverLevel).BloodMoonChanceRan = 1;
            SurvivalReimaginedModVariables.MapVariables.get(_serverLevel).setDirty();
        }
    }
}
