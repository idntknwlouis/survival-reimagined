package net.mcreator.survivalreimagined.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.level.block.ComposterBlock$OutputContainer")
public class ComposterHopperCheckMixin {
    @ModifyExpressionValue(
            method = "canTakeItemThroughFace",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean allowBioCompost(boolean original, int slot, ItemStack stack) {
        return original || stack.is(SurvivalReimaginedModItems.BIO_COMPOST.get());
    }
}
