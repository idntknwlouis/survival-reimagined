package net.mcreator.survivalreimagined.procedures;


import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;

public class ApplyRadiation  {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;


        ItemStack headArmor = entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY;
        boolean hasGasMask = headArmor.getItem() == SurvivalReimaginedModItems.GAS_MASK_HELMET.get();
        BlockPos currentPos = BlockPos.containing(entity.getX(), entity.getY(), entity.getZ());
        boolean isInRadiatedForest = world.getBiome(currentPos).is(ResourceLocation.parse("survival_reimagined:radiated_forest"));
        MobEffect rawEffect = SurvivalReimaginedModMobEffects.RADIATION.get();
        Holder<MobEffect> radiation = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(rawEffect);

        

        if (hasGasMask ^ headArmor.isEmpty()
                && (isInRadiatedForest)
                && (entity instanceof Player)
                && (getEntityGameType(entity) != GameType.CREATIVE)) {
                if (((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(radiation) ? _livEnt.getEffect(radiation).getDuration() : 0) == 1) ^ !(entity instanceof LivingEntity _livEnt13 && _livEnt13.hasEffect(radiation))) {
                        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) _entity.addEffect(new MobEffectInstance(radiation, 60, 0, true, false));
                }
        } else if (headArmor.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("FilterPercentage") <= 0) {
            if ((isInRadiatedForest) && (entity instanceof Player) && getEntityGameType(entity) != GameType.CREATIVE) {
                if  (((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(radiation) ? _livEnt.getEffect(radiation).getDuration() : 0) == 1) ^ !(entity instanceof LivingEntity _livEnt25 && _livEnt25.hasEffect(radiation))) {
                    if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) _entity.addEffect(new MobEffectInstance(radiation, 60, 0, true, false));
                }
            }
        }
        if (isInRadiatedForest) {
            if (entity instanceof Slime || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("minecraft:undead")))) {
                if (entity instanceof LivingEntity _livEnt33 && _livEnt33.hasEffect(radiation)) {
                    if (entity instanceof LivingEntity _entity) _entity.removeEffect(radiation);
                }
            } else {
                if (!(entity instanceof Player)) {
                    if (((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(radiation) ? _livEnt.getEffect(radiation).getDuration() : 0) == 1) ^ !(entity instanceof LivingEntity _livEnt37 && _livEnt37.hasEffect(radiation))) {
                        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) _entity.addEffect(new MobEffectInstance(radiation, 60, 0, true, false));
                    }
                }
            }
        }
    }
    private static GameType getEntityGameType(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer();
        } else if (entity instanceof Player player && player.level().isClientSide()) {
            PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
            if (playerInfo != null) return playerInfo.getGameMode();
        }
        return null;
    }
}
