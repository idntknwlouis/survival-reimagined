package net.mcreator.survivalreimagined.item;

import net.mcreator.survivalreimagined.block.CrudeTorch;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.List;
import java.util.Locale;


public class LitCrudeTorchItem extends BlockItem {
    public LitCrudeTorchItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = super.getPlacementState(context);

        if (state != null) state = state.setValue(CrudeTorch.LIT, true);
        return state;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemStack, world, entity, slot, selected);

        if (world.isClientSide()) return;
        if (world.getGameTime() % 20 != 0) return;
        if (!(entity instanceof Player player)) return;

        CompoundTag tag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        double burn = tag.getDouble("Burn") + 1;

        if (burn >= 2000) {
            ItemStack burntTorch = new ItemStack(SurvivalReimaginedModItems.BURNT_TORCH.get());
            burntTorch.setCount(itemStack.getCount());
            player.getInventory().setItem(slot, burntTorch);
            return;
        }
        CustomData.update(DataComponents.CUSTOM_DATA, itemStack, data -> {
            data.putDouble("Burn", burn);
        });

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        double burn = tag.getDouble("Burn");

        double percentage = 100.0 - (burn / 2000.0 * 100.0);
        percentage = Math.max(0, Math.min(100, percentage));

        tooltip.add(Component.literal(" §7Burn Life: " + String.format(Locale.ROOT, "%.1f", percentage) + "%"));
    }
}
