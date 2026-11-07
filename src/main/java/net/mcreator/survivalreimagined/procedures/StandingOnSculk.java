package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.entity.EntityTypeTest;

public class StandingOnSculk {
    private static final ResourceKey<Enchantment> SOUL_PURIFICATION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("survival_reimagined:soul_purification"));

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(level -> {
            for (Entity entity : level.getAllEntities()) {
                execute(level, entity);
            }
        });
    }

    public static void execute(ServerLevel world, Entity entity) {
        if (entity == null || !SurvivalReimaginedModConfig.VALUES.binding_sculk) return;
        if (!(entity instanceof LivingEntity livingEntity)) return;

        BlockPos pos = BlockPos.containing(entity.getX(), entity.getY() - 0.75, entity.getZ());
        if (!world.getBlockState(pos).is(Blocks.SCULK)) return;

        var enchantment = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(SOUL_PURIFICATION);
        if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, livingEntity.getItemBySlot(EquipmentSlot.FEET)) > 0) return;

        if (livingEntity instanceof Player player) {
            if (!(player instanceof ServerPlayer serverPlayer)) return;

            GameType gameType = serverPlayer.gameMode.getGameModeForPlayer();

            if (gameType != GameType.SURVIVAL && gameType != GameType.ADVENTURE) return;
        } else if (entity instanceof Warden) {
            return;
        }


        var effect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.BINDING_SCULK.get());
        MobEffectInstance currentEffect = livingEntity.getEffect(effect);

        if (currentEffect == null || currentEffect.getDuration() <= 10) {
            livingEntity.addEffect(new MobEffectInstance(effect, 60, 0));
        }
    }
}
