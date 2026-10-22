package net.mcreator.survivalreimagined.procedures.bloodmoon;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BloodMoonAnnouncement {
    private static final List<TickTask> PENDING_TASKS = new ArrayList<>();
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                execute(player.serverLevel(), player.getX(), player.getY(),player.getZ());
            }
            Iterator<TickTask> iterator = PENDING_TASKS.iterator();
            while (iterator.hasNext()) {
                TickTask task = iterator.next();
                if (task.tick()) {
                    iterator.remove();
                }
            }

        });
    }
    public static void execute(ServerLevel world, double x, double y, double z) {
        if (SurvivalReimaginedModVariables.MapVariables.get(world).isBloodMoon && !SurvivalReimaginedModVariables.MapVariables.get(world).AnnouncementPlayed) {
            SurvivalReimaginedModVariables.MapVariables.get(world).AnnouncementPlayed = true;
            SurvivalReimaginedModVariables.MapVariables.get(world).setDirty();

            queueServerWork(world, 1, () -> {
                if (world instanceof ServerLevel _level) {
                    _level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§4The Bloodmoon is Rising...."), false);
                }
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.wither.spawn")), SoundSource.MASTER, 0.6f, 1.0f);
                    }
                }
            });
        } else if (!SurvivalReimaginedModVariables.MapVariables.get(world).isBloodMoon && world instanceof Level _lvl3 && _lvl3.isDay()) {
            SurvivalReimaginedModVariables.MapVariables.get(world).AnnouncementPlayed = false;
            SurvivalReimaginedModVariables.MapVariables.get(world).setDirty();


        }
    }
    private static void queueServerWork(LevelAccessor world, int delay, Runnable callback) {
        if(world instanceof ServerLevel) {
            PENDING_TASKS.add(new TickTask(delay, callback));
        }
    }
    private static class TickTask {
        private int ticksLeft;
        private final Runnable action;

        public TickTask(int delay, Runnable action) {
            this.ticksLeft = delay;
            this.action = action;
        }
        public boolean tick() {
            this.ticksLeft--;
            if (this.ticksLeft <= 0) {
                this.action.run();
                return true;
            }
            return false;
        }
    }
}
