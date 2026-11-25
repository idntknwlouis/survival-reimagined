package net.mcreator.survivalreimagined.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class PlaySoundBackpack {
    public static boolean execute(LevelAccessor world, double x, double y, double z) {
        if (world instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.armor.equip_leather")), SoundSource.PLAYERS, 1, 1);
            }
        }
        return true;
    }
}
