package net.mcreator.survivalreimagined.procedures.bloodmoon;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class BloodMoonHeartRightClicked {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;
        if (!(world instanceof Level level && level.isDay())) {
            if (world instanceof ServerLevel serverLevel) {
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).AnnouncementPlayed = true;
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).isBloodMoon = true;
                SurvivalReimaginedModVariables.MapVariables.get(serverLevel).setDirty();
                serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§4Heart Of The Blood Moon essence is released into the air, forcing a BloodMoon"), false);
            }
            if (world instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.cure")), SoundSource.NEUTRAL, 1, 1);
                } else {
                    level.playLocalSound(x,y,z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.cure")), SoundSource.NEUTRAL, 1, 1, false);
                }
            }
            if (entity instanceof Player player) {
                ItemStack stackToRemove = new ItemStack(SurvivalReimaginedModItems.HEART_OF_THE_BLOOD_MOON.get());
                player.getInventory().clearOrCountMatchingItems(item -> stackToRemove.getItem() == item.getItem(), 1, player.inventoryMenu.getCraftSlots());
            }
        } else {
            if (world instanceof ServerLevel serverLevel) {
                serverLevel.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§4Heart Of The Blood Moon can only be used at Night"), false);
            }
        }
    }
}
