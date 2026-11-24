package net.mcreator.survivalreimagined.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.mcreator.survivalreimagined.util.BackpackMenuData;
import net.mcreator.survivalreimagined.util.BackpackTierType;
import net.mcreator.survivalreimagined.world.inventory.BackpackGUI3Menu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BackpackT3 extends Item {
    public BackpackT3() {
        super(new Properties().stacksTo(1));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, context, list, flag);
        Entity entity = stack.getEntityRepresentation() != null ? stack.getEntityRepresentation() : Minecraft.getInstance().player;;
        String hoverText = BackpackTierType.execute(stack);
        if (hoverText != null) {
            for (String line : hoverText.split("\n")) {
                list.add(Component.literal(line));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockPos pos = serverPlayer.blockPosition();

            serverPlayer.openMenu(new ExtendedScreenHandlerFactory() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Backpack");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new BackpackGUI3Menu(id, inventory, new BackpackMenuData(pos, itemstack));
                }

                @Override
                public BackpackMenuData getScreenOpeningData(ServerPlayer player) {
                    return new BackpackMenuData(pos, itemstack);
                }
            });

            world.playSound(null, serverPlayer.blockPosition(), SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
    }
}
