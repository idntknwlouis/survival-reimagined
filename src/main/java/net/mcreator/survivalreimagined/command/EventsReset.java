package net.mcreator.survivalreimagined.command;

import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class EventsReset {
    public static void execute(LevelAccessor world) {
        if (world instanceof ServerLevel _serverLevel) {
            SurvivalReimaginedModVariables.MapVariables.get(_serverLevel).isBloodMoon = false;
            SurvivalReimaginedModVariables.MapVariables.get(_serverLevel).BloodMoonChanceRan = 0;
            SurvivalReimaginedModVariables.MapVariables.get(_serverLevel).AnnouncementPlayed = false;
            SurvivalReimaginedModVariables.MapVariables.get(_serverLevel).setDirty();
            if (world.isClientSide()) {
                Minecraft.getInstance().getTextureManager().release(ResourceLocation.parse("minecraft:textures/environment/moon_phases.png"));
            }
        }
    }
}
