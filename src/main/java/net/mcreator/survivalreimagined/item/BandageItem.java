package net.mcreator.survivalreimagined.item;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BandageItem extends Item {
    public BandageItem() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Holder<MobEffect> bleeding = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(SurvivalReimaginedModMobEffects.BLEEDING.get());

        if (player.hasEffect(bleeding)) {
            if (!level.isClientSide()) {
                player.removeEffect(bleeding);

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                SurvivalReimaginedMod.queueServerWork(1, () -> {
                    level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                            SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
                });
            } else {
                level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 1.0f, false);
            }
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal(" §7 Stops Bleeding"));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
