package net.mcreator.survivalreimagined.mixin;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultSlot.class)
public abstract class CopyItemData {

    @Shadow @Final
    private CraftingContainer craftSlots;

    @Inject(method = "onTake", at = @At("HEAD"))
    private void copyBackpackData(Player player, ItemStack stack, CallbackInfo ci) {
        if (craftSlots.getContainerSize() <= 4) return;
        ItemStack slot5 = craftSlots.getItem(4);

        if (stack.is(SurvivalReimaginedModItems.BACKPACK_T2.get()) || stack.is(SurvivalReimaginedModItems.BACKPACK_T3.get())) {
            stack.applyComponents(slot5.getComponents());
        }
    }
}
