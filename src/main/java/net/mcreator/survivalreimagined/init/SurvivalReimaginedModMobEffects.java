package net.mcreator.survivalreimagined.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.potion.BindingSculkMobEffect;
import net.mcreator.survivalreimagined.potion.BleedingMobEffect;
import net.mcreator.survivalreimagined.potion.RadiationMobEffect;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import java.util.function.Supplier;

public final class SurvivalReimaginedModMobEffects {
	public static final RegistryEntry<MobEffect> BINDING_SCULK = register("binding_sculk", BindingSculkMobEffect::new);
	public static final RegistryEntry<MobEffect> BLEEDING = register("bleeding", BleedingMobEffect::new);
	public static final RegistryEntry<MobEffect> RADIATION = register("radiation", RadiationMobEffect::new);

	private SurvivalReimaginedModMobEffects() {
	}

	private static RegistryEntry<MobEffect> register(String path, Supplier<? extends MobEffect> factory) {
		var id = SurvivalReimaginedMod.asResource(path);
		MobEffect effect = Registry.register(BuiltInRegistries.MOB_EFFECT, id, factory.get());
		return new RegistryEntry<>(id, effect);
	}

	public static void register() {
		// Forces class initialization.
	}
}
