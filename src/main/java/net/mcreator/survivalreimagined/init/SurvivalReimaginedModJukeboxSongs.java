package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.JukeboxSong;

public class SurvivalReimaginedModJukeboxSongs {
    public static final ResourceKey<JukeboxSong> BLOOD_MOON_SONG = ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "blood_moons_intimidation"));

    public static void register() {}
}
