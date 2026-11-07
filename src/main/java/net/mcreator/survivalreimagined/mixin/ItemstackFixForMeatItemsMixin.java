package net.mcreator.survivalreimagined.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;


import net.minecraft.world.item.ItemStack;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModTags;


@Mixin(ItemStack.class)
public class ItemstackFixForMeatItemsMixin {
	@Inject(
		method = "isSameItemSameComponents",
		at = @At("HEAD"),
		cancellable = true	
	)
	private static void onlyCompareItems(
		ItemStack first,
		ItemStack second,
		CallbackInfoReturnable<Boolean> cir
	) {
		if (shouldIgnoreComponents(first)
		&& first.getItem() == second.getItem()) {
			cir.setReturnValue(true);
		}
	}
	private static boolean shouldIgnoreComponents(ItemStack stack) {
		return stack.is(SurvivalReimaginedModTags.MERGEABLE_MEATS);
	}
}