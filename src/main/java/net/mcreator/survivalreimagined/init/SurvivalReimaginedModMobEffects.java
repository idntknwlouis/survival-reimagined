package net.mcreator.survivalreimagined.init;

import net.mcreator.survivalreimagined.potion.*;
import net.mcreator.survivalreimagined.potion.EffectExpires.BrokenLegEffectExpires;
import net.mcreator.survivalreimagined.potion.EffectExpires.ZombificationEffectExpires;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.util.RegistryEntry;

import net.minecraft.world.entity.LivingEntity;

import java.util.*;
import java.util.function.Supplier;

public final class SurvivalReimaginedModMobEffects {
	public static final RegistryEntry<MobEffect> BINDING_SCULK = register("binding_sculk", BindingSculkMobEffect::new);
	public static final RegistryEntry<MobEffect> BLEEDING = register("bleeding", BleedingMobEffect::new);
	public static final RegistryEntry<MobEffect> RADIATION = register("radiation", RadiationMobEffect::new);
	public static final RegistryEntry<MobEffect> BROKEN_LEG = register("broken_leg_potion", BrokenLegMobEffect::new);
	public static final RegistryEntry<MobEffect> ZOMBIFICATION = register("zombification", ZombificationMobEffect::new);
	public static final RegistryEntry<MobEffect> PARANOIA = register("paranoia", ParanoiaMobEffect::new);
	public static final RegistryEntry<MobEffect> FEAR = register("fear", FearMobEffect::new);



	private static final Map<UUID, Set<MobEffect>> RECENTLY_ACTIVE_EFFECTS = new HashMap<>();

	private SurvivalReimaginedModMobEffects() {
	}

	private static RegistryEntry<MobEffect> register(String path, Supplier<? extends MobEffect> factory) {
		var id = SurvivalReimaginedMod.asResource(path);
		MobEffect effect = Registry.register(BuiltInRegistries.MOB_EFFECT, id, factory.get());
		return new RegistryEntry<>(id, effect);
	}
	public static void onEffectRemoved(LivingEntity entity) {
		if (entity == null || entity.level().isClientSide()) return;

		UUID uuid = entity.getUUID();
		Set<MobEffect> pastEffects = RECENTLY_ACTIVE_EFFECTS.computeIfAbsent(uuid, k -> new HashSet<>());
		Set<MobEffect> currentEffects = new HashSet<>();


		// Monitors Effects that has expiry condition
		checkEffectState(entity, BROKEN_LEG.get(), pastEffects, currentEffects);
		checkEffectState(entity, ZOMBIFICATION.get(), pastEffects, currentEffects);



		if (currentEffects.isEmpty()) {
			RECENTLY_ACTIVE_EFFECTS.remove(uuid);
		} else {
			RECENTLY_ACTIVE_EFFECTS.put(uuid, currentEffects);
		}
	}
	private static void checkEffectState(LivingEntity entity, MobEffect effect, Set<MobEffect> pastEffects, Set<MobEffect> currentEffects) {
		boolean hasEffect = entity.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect));
		boolean hadEffect = pastEffects.contains(effect);

		if (hasEffect) { currentEffects.add(effect); }
		if (hadEffect && !hasEffect) { executeEffectExpiry(entity, effect); }
	}
	private static void executeEffectExpiry(LivingEntity entity, MobEffect expiredEffect) {
		// Executes expiry conditions
		if (expiredEffect == BROKEN_LEG.get()) { BrokenLegEffectExpires.execute(entity); }
		if (expiredEffect == ZOMBIFICATION.get()) { ZombificationEffectExpires.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity); }
	}

	public static void register() {
		// Forces class initialization.
	}
}
