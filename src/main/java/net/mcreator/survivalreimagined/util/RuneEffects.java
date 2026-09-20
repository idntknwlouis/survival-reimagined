package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

public final class RuneEffects {
	private static final TagKey<Item> INFUSABLE_TOOL = itemTag("c", "rmi_infusable/tool");
	private static final TagKey<Item> INFUSABLE_WEAPON = itemTag("c", "rmi_infusable/weapon");
	private static final TagKey<Item> INFUSABLE_ARMOR = itemTag("c", "rmi_infusable/armor");
	private static final TagKey<Block> COMMON_ORES = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores"));

	private RuneEffects() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (var player : server.getPlayerList().getPlayers()) {
				tickArmor(player);
			}
		});

		ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
			if (!(source.getEntity() instanceof LivingEntity attacker)) return;
			ItemStack weapon = attacker.getMainHandItem();
			if (!weapon.is(INFUSABLE_WEAPON)) return;

			if (has(weapon, "AmberInfused")) {
				if (has(weapon, "GoldInfused")) {
					if (attacker.getRandom().nextFloat() < 0.60F) entity.igniteForSeconds(6);
				} else if (has(weapon, "SilverInfused")) {
					if (attacker.getRandom().nextFloat() < 0.40F) entity.igniteForSeconds(3);
				}
			}

			if (has(weapon, "RubyInfused")) {
				if (has(weapon, "GoldInfused")) {
					attacker.heal(4.0F);
				} else if (has(weapon, "SilverInfused")) {
					attacker.heal(2.0F);
				}
			}
		});

		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (!(source.getEntity() instanceof LivingEntity attacker)) return;
			ItemStack weapon = attacker.getMainHandItem();
			if (!weapon.is(INFUSABLE_WEAPON) || !has(weapon, "LapisInfused")) return;
			if (!(entity.level() instanceof ServerLevel level)) return;

			float chance = has(weapon, "GoldInfused") ? 0.20F : has(weapon, "SilverInfused") ? 0.10F : 0.0F;
			if (chance <= 0.0F || attacker.getRandom().nextFloat() >= chance) return;

			int orbs = 1 + attacker.getRandom().nextInt(3);
			for (int i = 0; i < orbs; i++) {
				int value = has(weapon, "GoldInfused")
						? 3 + attacker.getRandom().nextInt(5)
						: 1 + attacker.getRandom().nextInt(3);
				level.addFreshEntity(new ExperienceOrb(level, entity.getX(), entity.getY(), entity.getZ(), value));
			}
		});

		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			ItemStack tool = player.getMainHandItem();
			if (!tool.is(INFUSABLE_TOOL) || !(level instanceof ServerLevel serverLevel)) return;

			if (has(tool, "RubyInfused")) {
				int repair = has(tool, "GoldInfused") ? 8 : has(tool, "SilverInfused") ? 4 : 0;
				if (repair > 0 && tool.isDamageableItem()) tool.setDamageValue(Math.max(0, tool.getDamageValue() - repair));
			}

			if (has(tool, "LapisInfused") && tool.is(net.minecraft.tags.ItemTags.PICKAXES)) {
				boolean ore = state.is(COMMON_ORES);
				float chance = ore ? 1.0F : has(tool, "GoldInfused") ? 0.20F : has(tool, "SilverInfused") ? 0.10F : 0.0F;
				if (chance > 0.0F && player.getRandom().nextFloat() < chance) {
					int valueMin = has(tool, "GoldInfused") ? 3 : 1;
					int valueRange = has(tool, "GoldInfused") ? 5 : 3;
					for (int i = 0; i < 3; i++) {
						serverLevel.addFreshEntity(new ExperienceOrb(serverLevel,
								pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
								valueMin + player.getRandom().nextInt(valueRange)));
					}
				}
			}

			if (has(tool, "EmeraldInfused")) {
				float chance = emeraldChance(tool, state);
				if (chance > 0.0F && player.getRandom().nextFloat() < chance) {
					ItemEntity drop = new ItemEntity(serverLevel,
							pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
							new ItemStack(SurvivalReimaginedModItems.ROUGH_EMERALD.get()));
					drop.setPickUpDelay(10);
					serverLevel.addFreshEntity(drop);
				}
			}
		});
	}

	private static void tickArmor(Player player) {
		boolean sapphire = false;
		boolean sapphireGold = false;
		boolean sapphireSilver = false;
		boolean diamond = false;
		boolean diamondGold = false;
		boolean diamondSilver = false;
		boolean allAmber = true;
		boolean amberGold = false;
		boolean amberSilver = false;

		for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}) {
			ItemStack stack = player.getItemBySlot(slot);
			if (!stack.is(INFUSABLE_ARMOR)) {
				allAmber = false;
				continue;
			}

			if (has(stack, "SapphireInfused")) {
				sapphire = true;
				sapphireGold |= has(stack, "GoldInfused");
				sapphireSilver |= has(stack, "SilverInfused");
			}
			if (has(stack, "DiamondInfused")) {
				diamond = true;
				diamondGold |= has(stack, "GoldInfused");
				diamondSilver |= has(stack, "SilverInfused");
			}
			if (has(stack, "AmberInfused")) {
				amberGold |= has(stack, "GoldInfused");
				amberSilver |= has(stack, "SilverInfused");
			} else {
				allAmber = false;
			}
		}

		if (sapphire) {
			int duration = sapphireGold ? 80 : sapphireSilver ? 60 : 0;
			if (duration > 0) player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, duration, 0, true, false));
		}
		if (diamond) {
			if (diamondGold) {
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 1, true, false));
			} else if (diamondSilver) {
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 0, true, false));
			}
		}
		if (allAmber && (amberGold || amberSilver)) {
			player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 80, 0, true, false));
		}
	}

	private static float emeraldChance(ItemStack tool, BlockState state) {
		boolean gold = has(tool, "GoldInfused");
		boolean silver = has(tool, "SilverInfused");
		if (!gold && !silver) return 0.0F;

		if (tool.is(net.minecraft.tags.ItemTags.PICKAXES) && state.is(COMMON_ORES)) {
			return gold ? 0.20F : 0.10F;
		}
		if (tool.is(net.minecraft.tags.ItemTags.AXES) && state.is(BlockTags.LOGS)) {
			return gold ? 0.15F : 0.075F;
		}
		if (tool.is(net.minecraft.tags.ItemTags.SHOVELS) && state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
			return gold ? 0.15F : 0.075F;
		}
		if (tool.is(net.minecraft.tags.ItemTags.HOES) && state.is(BlockTags.MINEABLE_WITH_HOE)) {
			return gold ? 0.15F : 0.075F;
		}
		return 0.0F;
	}

	public static boolean has(ItemStack stack, String key) {
		return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean(key);
	}

	private static TagKey<Item> itemTag(String namespace, String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
	}
}
