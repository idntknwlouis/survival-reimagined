package net.mcreator.survivalreimagined.mixin;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "net.minecraft.world.level.block.ComposterBlock")
public class ComposterHopperMixin {
    @ModifyArg(
            method = "getContainer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/ComposterBlock$OutputContainer;<init>(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"
            ),
            index = 3
    )
    private ItemStack replaceBonemeal(ItemStack original) {
        return new ItemStack(SurvivalReimaginedModItems.BIO_COMPOST.get());
    }
}
