package net.mcreator.survivalreimagined.item;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BookScriptureItem extends Item {
    private final MenuFactory menuFactory;
    private final Component title;
    private final List<Component> hoverTooltipLines;

    @FunctionalInterface
    public interface MenuFactory {
        AbstractContainerMenu create(int syncID, Inventory inventory, BlockPos pos);
    }

    public BookScriptureItem(Rarity rarity, Component title, List<Component> hoverTooltipLines, MenuFactory menuFactory) {
        super(new Item.Properties().stacksTo(1).rarity(rarity));
        this.title = title;
        this.hoverTooltipLines = hoverTooltipLines;
        this.menuFactory = menuFactory;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, context, tooltip, flag);
        if (this.hoverTooltipLines != null) {
            tooltip.add(this.title);
            tooltip.addAll(this.hoverTooltipLines);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new ExtendedScreenHandlerFactory<BlockPos>() {

                @Override
                public BlockPos getScreenOpeningData(ServerPlayer player) {
                    return player.blockPosition();
                }

                @Override
                public Component getDisplayName() {
                    return title;
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return menuFactory.create(id, inventory, player.blockPosition());
                }
            });
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
