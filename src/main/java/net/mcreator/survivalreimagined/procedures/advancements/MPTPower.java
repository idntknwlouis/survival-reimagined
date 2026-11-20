package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class MPTPower {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                execute(player.level(), player.getX(), player.getY(), player.getZ(), player);
            }
        });
    }
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        boolean found = false;

        for (int sx = -10; sx < 10; sx++) {
            for (int sy = -10; sy < 10; sy++) {
                for (int sz = -10; sz < 10; sz++) {
                    BlockPos pos = BlockPos.containing(x + sx, y + sy, z + sz);
                    BlockState state = world.getBlockState(pos);
                    if (state.is(SurvivalReimaginedModBlocks.MINERAL_PROCESSING_TABLE.get())) {
                        Property<?> property = getPropertyByName(state, "redstone_power");
                        if (property instanceof BooleanProperty booleanProperty && state.getValue(booleanProperty)) {
                            found = true;
                            if(entity instanceof ServerPlayer player) {
                                AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:additional_power"));
                                if (advancement != null) {
                                    AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
                                    if (!progress.isDone()) {
                                        for (String criteria : progress.getRemainingCriteria()) {
                                            player.getAdvancements().award(advancement, criteria);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static Property<?> getPropertyByName(BlockState state, String name) {
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }
}
