package net.mcreator.survivalreimagined.procedures.advancements;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ConqueredAdvancement {
    public static void execute(Level world, double x, double y, double z, Entity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        if (!hasFearEffect(player)) return;
        boolean inLight = world.getMaxLocalRawBrightness(BlockPos.containing(x,y,z)) >= 5;
        boolean holdDynamicLight = false;

        if (FabricLoader.getInstance().isModLoaded("lambdynlights")) {
            TagKey<Item> lightTag = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:dynlights/light"));

            holdDynamicLight = player.getMainHandItem().is(lightTag) || player.getOffhandItem().is(lightTag);
        }

        if (inLight || holdDynamicLight) {
            awardAdvancement(player);
        }
    }

    private static boolean hasFearEffect(LivingEntity entity) {
        return entity.getActiveEffects().stream().anyMatch(effect ->
                effect.getEffect().value() == SurvivalReimaginedModMobEffects.PARANOIA.get()
                || effect.getEffect().value() == SurvivalReimaginedModMobEffects.FEAR.get());
    }

    private static void awardAdvancement(ServerPlayer player) {
        AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:conquered"));

        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);

            if (!progress.isDone()) {
                for (String criteria : progress.getRemainingCriteria()) {
                    player.getAdvancements().award(advancement, criteria);
                }
            }
        }
    }

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for (ServerPlayer player : world.players()) {
                execute(world, player.getX(), player.getY(), player.getZ(), player);
            }
        });
    }
}
