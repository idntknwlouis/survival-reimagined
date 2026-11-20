package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class EnterRadiantForest {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                execute(player.level(), player.getX(), player.getY(), player.getZ(), player);
            }
        });
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        BlockPos pos = BlockPos.containing(x,y,z);
        if (world.getBiome(pos).is(ResourceLocation.parse("survival_reimagined:radiated_forest"))) {
            ItemStack helmet = entity instanceof LivingEntity livingEntity ? livingEntity.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY;
            if (helmet.is(SurvivalReimaginedModItems.GAS_MASK_HELMET.get())) {
                if (entity instanceof ServerPlayer player) {
                    AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:radiation_protected"));
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
