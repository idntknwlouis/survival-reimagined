package net.mcreator.survivalreimagined.mixin;

import net.minecraft.world.level.block.state.properties.WoodType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WoodType.class)
public interface WoodTypeInvoker {
	@Invoker("register")
	static WoodType survivalReimagined$register(WoodType woodType) {
		throw new AssertionError();
	}
}
