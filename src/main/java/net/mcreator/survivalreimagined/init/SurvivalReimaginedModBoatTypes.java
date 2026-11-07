package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.entity.BoatEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class SurvivalReimaginedModBoatTypes {
    public static final EntityType<BoatEntity> WISTERIA_BOAT = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "wisteria_boat"),
            EntityType.Builder.<BoatEntity>of((type, level) ->
                    new BoatEntity(type, level, BoatEntity.CustomType.WISTERIA), MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("wisteria_boat")
            );

    public static final EntityType<BoatEntity> RADIATED_BOAT = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "radiated_boat"),
            EntityType.Builder.<BoatEntity>of((type, level) ->
                            new BoatEntity(type, level, BoatEntity.CustomType.RADIATED), MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("radiated_boat")
    );

    public static final EntityType<BoatEntity> MANDARIN_BOAT = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(SurvivalReimaginedMod.MODID, "mandarin_boat"),
            EntityType.Builder.<BoatEntity>of((type, level) ->
                            new BoatEntity(type, level, BoatEntity.CustomType.MANDARIN), MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mandarin_boat")
    );

    public static void register() {}
}
