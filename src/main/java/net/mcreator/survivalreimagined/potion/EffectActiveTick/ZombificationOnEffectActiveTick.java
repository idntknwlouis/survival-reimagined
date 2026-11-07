package net.mcreator.survivalreimagined.potion.EffectActiveTick;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ZombificationOnEffectActiveTick {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        if (world instanceof ServerLevel serverLevel) {
            if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger >= 3
                    && world instanceof Level level && level.isDay()
                    && !((entity instanceof LivingEntity living ? living.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).is(ItemTags.HEAD_ARMOR))
                    && world.canSeeSkyFromBelowWater(BlockPos.containing(x,y,z))) {
                SurvivalReimaginedMod.queueServerWork(20, () -> {
                    entity.igniteForSeconds(3);
                });
            } else if (SurvivalReimaginedModVariables.WorldVariables.get(serverLevel).EffectDanger >= 2) {
                if (entity instanceof LivingEntity living && living.level().isClientSide())
                    living.addEffect(new MobEffectInstance(MobEffects.HUNGER, 2400, 1, false, true));
            }
            if (entity instanceof Player player && !player.level().isClientSide())
                player.displayClientMessage(Component.literal("Infected"), true);
        }

    }
}
