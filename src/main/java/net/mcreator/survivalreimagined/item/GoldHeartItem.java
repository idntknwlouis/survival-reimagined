package net.mcreator.survivalreimagined.item;

import net.mcreator.survivalreimagined.network.PlayerVariables;
import net.mcreator.survivalreimagined.network.SurvivalReimaginedModVariables;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class GoldHeartItem extends Item {
    public GoldHeartItem() {
        super(new Item.Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(ItemStack itemstack) { return true; }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, tooltip, flag);
        tooltip.add(Component.literal("Grants immunity to Zombification").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (SurvivalReimaginedModVariables.getPlayerVariables(player).ZombificationImmune) return InteractionResultHolder.pass(stack);


        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }



        if (!SurvivalReimaginedModVariables.getPlayerVariables(player).ZombificationImmune) {
            if (!level.isClientSide()) {
                player.displayClientMessage(Component.literal("You are now immune to Zombification"), true);
                level.playSound(null, player.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.PLAYERS, 0.2F, 1.3F);
                level.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.6F, 1.2F);
                SurvivalReimaginedModVariables.getPlayerVariables(player).ZombificationImmune = true;
                SurvivalReimaginedModVariables.getPlayerVariables(player)._syncDirty = true;
                SurvivalReimaginedModVariables.PlayerVariablesData.get((ServerPlayer) player).markDirty();
                if (!player.getAbilities().instabuild) stack.shrink(1);
                player.getCooldowns().addCooldown(this, 100);
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(this));
            }
            return InteractionResultHolder.fail(stack);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
