package net.mcreator.survivalreimagined.item;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
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

import net.mcreator.survivalreimagined.world.inventory.AAFScriptureGUIMenu;
import net.mcreator.survivalreimagined.world.inventory.RMIScriptureGUIMenu;

import java.util.List;

public class ScriptureItem extends Item {
	public enum Kind { AAF, RMI }

	private final Kind kind;
	private final String subject;

	public ScriptureItem(Kind kind, String subject) {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
		this.kind = kind;
		this.subject = subject;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, context, tooltip, flag);
		tooltip.add(Component.literal(this.subject).withStyle(net.minecraft.ChatFormatting.GRAY));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
			BlockPos pos = player.blockPosition();
			serverPlayer.openMenu(new ExtendedScreenHandlerFactory<BlockPos>() {
				@Override
				public BlockPos getScreenOpeningData(ServerPlayer player) {
					return pos;
				}

				@Override
				public Component getDisplayName() {
					return Component.literal("Ancient Book Scripture");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player menuPlayer) {
					return kind == Kind.AAF
							? new AAFScriptureGUIMenu(id, inventory, pos)
							: new RMIScriptureGUIMenu(id, inventory, pos);
				}
			});
		}
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
