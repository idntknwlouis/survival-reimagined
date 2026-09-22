package net.mcreator.survivalreimagined.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.entity.BoarEntity;
import net.mcreator.survivalreimagined.entity.PigletEntity;
import net.mcreator.survivalreimagined.entity.SowEntity;
import net.mcreator.survivalreimagined.util.RegistryEntry;

public final class SurvivalReimaginedModEntities {
	public static final RegistryEntry<EntityType<BoarEntity>> BOAR =
			register("boar", EntityType.Builder.of(BoarEntity::new, MobCategory.CREATURE).sized(1.0F, 1.0F));
	public static final RegistryEntry<EntityType<SowEntity>> SOW =
			register("sow", EntityType.Builder.of(SowEntity::new, MobCategory.CREATURE).sized(0.6F, 1.0F));
	public static final RegistryEntry<EntityType<PigletEntity>> PIGLET =
			register("piglet", EntityType.Builder.of(PigletEntity::new, MobCategory.CREATURE).sized(0.5F, 0.5F));

	private SurvivalReimaginedModEntities() {
	}

	private static <T extends net.minecraft.world.entity.Entity> RegistryEntry<EntityType<T>> register(
			String name, EntityType.Builder<T> builder) {
		ResourceLocation id = SurvivalReimaginedMod.asResource(name);
		EntityType<T> type = Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(id.toString()));
		return new RegistryEntry<>(id, type);
	}

	public static void register() {
		FabricDefaultAttributeRegistry.register(BOAR.get(), BoarEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(SOW.get(), SowEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(PIGLET.get(), PigletEntity.createAttributes());
	}
}
