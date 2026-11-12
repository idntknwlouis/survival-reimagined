package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InflictBleeding {
    public static void register() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamage, damageTaken, blocked) -> {
            if (blocked) return;
            execute(entity, source.getEntity());
        });
    }

    public static void execute(Entity entity, Entity sourcEntity) {
        if (entity == null || sourcEntity == null) return;


        if (sourcEntity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("c:inflicts_bleeding")))
        || (sourcEntity instanceof LivingEntity livingEntity ? livingEntity.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.SWORDS)
        || (sourcEntity instanceof LivingEntity livingEntity ? livingEntity.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.SWORDS)
        || (sourcEntity instanceof LivingEntity livingEntity ? livingEntity.getMainHandItem() : ItemStack.EMPTY).is(ItemTags.AXES)
        || (sourcEntity instanceof LivingEntity livingEntity ? livingEntity.getOffhandItem() : ItemStack.EMPTY).is(ItemTags.AXES)) {
            if (Math.random() < SurvivalReimaginedModConfig.VALUES.bleeding_chance) {
                if (entity instanceof LivingEntity livingEntity && !entity.level().isClientSide()) {
                    livingEntity.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.BLEEDING.get()), 300, 0, true, false));
                }
            }
        }
    }
}