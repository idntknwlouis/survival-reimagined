package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GoldenAppleEaten {
    private static final Map<UUID, ItemStack> USING_ITEMS = new HashMap<>();



    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                UUID uuid = player.getUUID();

                if (player.isUsingItem()) {
                    ItemStack current = player.getUseItem();

                    if (isFood(current)) {
                        USING_ITEMS.put(uuid, current.copy());
                    }
                } else {
                    ItemStack previous = USING_ITEMS.remove(uuid);

                    if (previous != null && isFood(previous)) {
                        execute(player, previous);
                    }
                }
            }
            USING_ITEMS.keySet().removeIf(uuid -> server.getPlayerList().getPlayer(uuid) == null);
        });
    }
    private static boolean isFood(ItemStack stack) {
        return stack.is(Items.ENCHANTED_GOLDEN_APPLE) || stack.is(Items.GOLDEN_APPLE) || stack.is(Items.GOLDEN_CARROT);
    }

    public static void execute(LivingEntity entity, ItemStack itemstack) {
        Holder<MobEffect> ZOMBIFICATION = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.ZOMBIFICATION.get());
        if (entity == null || entity.level().isClientSide()) return;
        if (!entity.hasEffect(ZOMBIFICATION)) return;
        if (!isFood(itemstack)) return;

        if (entity.level() instanceof ServerLevel level) {
            SurvivalReimaginedModVariables.WorldVariables.get(level).EffectDanger = 5;
            SurvivalReimaginedModVariables.WorldVariables.get(level).setDirty();

            entity.removeEffect(ZOMBIFICATION);
            entity.removeEffect(MobEffects.WEAKNESS);

            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 0, false, true));

            if (itemstack.is(Items.GOLDEN_CARROT)) {
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, true));
            }
            level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()),
                    BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.zombie_villager.cure")), SoundSource.PLAYERS, 1.0f, 1.0f
            );

            if (entity instanceof ServerPlayer player) {
                AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:cure_zombification"));
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
