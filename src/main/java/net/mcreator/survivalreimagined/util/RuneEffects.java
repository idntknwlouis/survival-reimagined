package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RuneEffects {
	private static final TagKey<Item> INFUSABLE_TOOL = itemTag("c", "rmi_infusable/tool");
	private static final TagKey<Item> INFUSABLE_WEAPON = itemTag("c", "rmi_infusable/weapon");
	private static final TagKey<Item> INFUSABLE_ARMOR = itemTag("c", "rmi_infusable/armor");
	private static final TagKey<Block> COMMON_ORES = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores"));

	private static final Map<UUID, Integer> EMERALD_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Integer> LAPIS_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Integer> SAPPHIRE_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Integer> RUBY_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Double> SAPPHIRE_TOOL_BASE = new HashMap<>();

	private RuneEffects() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (var player : server.getPlayerList().getPlayers()) {
				tickArmor(player);
				tickTool(player);
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

			if (has(weapon, "EmeraldInfused") && entity.level() instanceof ServerLevel level) {
				float chance = has(weapon, "GoldInfused") ? 0.25F : has(weapon, "SilverInfused") ? 0.15F : 0.0F;
				if (chance > 0.0F && attacker.getRandom().nextFloat() < chance) {
					ItemEntity drop = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(),
							new ItemStack(SurvivalReimaginedModItems.ROUGH_EMERALD.get()));
					drop.setPickUpDelay(10);
					level.addFreshEntity(drop);
				}
			}

			if (has(weapon, "LapisInfused") && entity.level() instanceof ServerLevel level) {
				float chance = has(weapon, "GoldInfused") ? 0.20F : has(weapon, "SilverInfused") ? 0.10F : 0.0F;
				if (chance > 0.0F && attacker.getRandom().nextFloat() < chance) {
					int orbCount = 1 + attacker.getRandom().nextInt(3);
					for (int i = 0; i < orbCount; i++) {
						int value = has(weapon, "GoldInfused")
								? 3 + attacker.getRandom().nextInt(5)
								: 1 + attacker.getRandom().nextInt(3);
						level.addFreshEntity(new ExperienceOrb(level, entity.getX(), entity.getY(), entity.getZ(), value));
					}
				}
			}
		});

		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (!(source.getEntity() instanceof LivingEntity attacker)) return;
			ItemStack weapon = attacker.getMainHandItem();
			if (!weapon.is(INFUSABLE_WEAPON) || !has(weapon, "LapisInfused") || true) return;
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

			if (has(tool, "AmberInfused") && !player.isCreative()) {
				if (tool.is(net.minecraft.tags.ItemTags.AXES) && state.is(BlockTags.LOGS)) {
					int count = 2 + player.getRandom().nextInt(3);
					serverLevel.addFreshEntity(new ItemEntity(serverLevel,
							pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
							new ItemStack(Items.CHARCOAL, count)));
				} else if (tool.is(net.minecraft.tags.ItemTags.SHOVELS) && state.is(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "sands")))) {
					int count = 2 + player.getRandom().nextInt(3);
					serverLevel.addFreshEntity(new ItemEntity(serverLevel,
							pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
							new ItemStack(SurvivalReimaginedModItems.GLASS_SHARD.get(), count)));
				}
			}

			if (has(tool, "DiamondInfused")) {
				advanceUnbreaking(serverLevel, player, tool);
			}

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
		boolean sapphire = false, sapphireGold = false, sapphireSilver = false, allSapphire = true;
		boolean diamond = false, diamondGold = false, diamondSilver = false;
		boolean allAmber = true, amberGold = false, amberSilver = false;
		boolean emerald = false, emeraldGold = false, emeraldSilver = false;
		boolean ruby = false, rubyAllGold = true, rubyAllSilver = true;
		boolean lapis = false, lapisGold = false, lapisSilver = false;

		for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}) {
			ItemStack stack = player.getItemBySlot(slot);
			if (!stack.is(INFUSABLE_ARMOR)) {
				allAmber = false;
				allSapphire = false;
				rubyAllGold = false;
				rubyAllSilver = false;
				continue;
			}

			if (has(stack, "SapphireInfused")) {
				sapphire = true;
				sapphireGold |= has(stack, "GoldInfused");
				sapphireSilver |= has(stack, "SilverInfused");
			} else {
				allSapphire = false;
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

			if (has(stack, "EmeraldInfused")) {
				emerald = true;
				emeraldGold |= has(stack, "GoldInfused");
				emeraldSilver |= has(stack, "SilverInfused");
			}

			if (has(stack, "RubyInfused")) {
				ruby = true;
				rubyAllGold &= has(stack, "GoldInfused");
				rubyAllSilver &= has(stack, "SilverInfused");
			} else {
				rubyAllGold = false;
				rubyAllSilver = false;
			}

			if (has(stack, "LapisInfused")) {
				lapis = true;
				lapisGold |= has(stack, "GoldInfused");
				lapisSilver |= has(stack, "SilverInfused");
			}
		}

		UUID id = player.getUUID();

		if (sapphire && player.isUnderWater()) {
			int timer = SAPPHIRE_ARMOR_TICKS.merge(id, 1, Integer::sum);
			int threshold = sapphireGold ? 60 : 80;
			float chance = sapphireGold ? 0.25F : sapphireSilver ? 0.15F : 0.0F;
			if (allSapphire && timer >= 20) {
				player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 60, 0, true, false));
				SAPPHIRE_ARMOR_TICKS.put(id, 0);
			} else if (chance > 0.0F && timer >= threshold) {
				if (player.getRandom().nextFloat() < chance) {
					player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, sapphireGold ? 80 : 60, 0, true, false));
				}
				SAPPHIRE_ARMOR_TICKS.put(id, 0);
			}
		} else {
			SAPPHIRE_ARMOR_TICKS.remove(id);
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

		if (emerald && !player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
			int timer = EMERALD_ARMOR_TICKS.merge(id, 1, Integer::sum);
			if (timer >= 40) {
				float chance = emeraldGold ? 0.20F : emeraldSilver ? 0.10F : 0.0F;
				if (chance > 0.0F && player.getRandom().nextFloat() < chance) {
					player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, emeraldGold ? 6000 : 3000, 0));
					player.level().playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
				}
				EMERALD_ARMOR_TICKS.put(id, 0);
			}
		} else {
			EMERALD_ARMOR_TICKS.remove(id);
		}

		if (ruby && player.getHealth() <= 4.0F && (rubyAllGold || rubyAllSilver)) {
			int timer = RUBY_ARMOR_TICKS.merge(id, 1, Integer::sum);
			if (timer >= 20) {
				float chance = rubyAllGold ? 0.50F : 0.25F;
				if (player.getRandom().nextFloat() < chance) {
					player.heal(rubyAllGold ? 4.0F : 2.0F);
					player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, rubyAllGold ? 40 : 20, 2));
					player.level().playSound(null, player.blockPosition(), SoundEvents.WITCH_DRINK, SoundSource.PLAYERS, 0.6F, 1.0F);
				}
				RUBY_ARMOR_TICKS.put(id, 0);
			}
		} else {
			RUBY_ARMOR_TICKS.remove(id);
		}

		if (lapis) {
			int timer = LAPIS_ARMOR_TICKS.merge(id, 1, Integer::sum);
			if (timer >= 100) {
				float chance = lapisGold ? 0.10F : lapisSilver ? 0.05F : 0.0F;
				if (chance > 0.0F && player.getRandom().nextFloat() < chance) {
					int xp = lapisGold
							? Math.round(player.getXpNeededForNextLevel() / 4.0F) + 4 + player.getRandom().nextInt(5)
							: Math.round(player.getXpNeededForNextLevel() / 6.0F) + 2 + player.getRandom().nextInt(3);
					player.giveExperiencePoints(xp);
					player.level().playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.8F + player.getRandom().nextFloat() * 0.4F);
				}
				LAPIS_ARMOR_TICKS.put(id, 0);
			}
		} else {
			LAPIS_ARMOR_TICKS.remove(id);
		}
	}

	private static void tickTool(Player player) {
		ItemStack tool = player.getMainHandItem();
		UUID id = player.getUUID();
		if (!tool.is(INFUSABLE_TOOL) || !has(tool, "SapphireInfused")) {
			Double original = SAPPHIRE_TOOL_BASE.remove(id);
			if (original != null && player.getAttribute(Attributes.BLOCK_BREAK_SPEED) != null) {
				player.getAttribute(Attributes.BLOCK_BREAK_SPEED).setBaseValue(original);
			}
			return;
		}

		var attribute = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);
		if (attribute == null) return;

		if (player.isUnderWater()) {
			if (!SAPPHIRE_TOOL_BASE.containsKey(id)) {
				SAPPHIRE_TOOL_BASE.put(id, attribute.getBaseValue());
			}
			double base = SAPPHIRE_TOOL_BASE.get(id);
			attribute.setBaseValue(base * 1.6D);
		} else {
			Double original = SAPPHIRE_TOOL_BASE.remove(id);
			if (original != null) attribute.setBaseValue(original);
		}
	}

	private static void advanceUnbreaking(ServerLevel level, Player player, ItemStack tool) {
		boolean gold = has(tool, "GoldInfused");
		boolean silver = has(tool, "SilverInfused");
		if (!gold && !silver) return;

		var enchantment = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING);
		int current = tool.getEnchantmentLevel(enchantment);
		if (current >= 4) return;

		String key = gold ? "GoldNumber" : "SilverNumber";
		int step = gold ? 30 : 50;
		int target = step * (current + 1);
		CompoundTag data = tool.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		int progress = data.getInt(key) + 1;

		final int stored = progress >= target ? 0 : progress;
		CustomData.update(DataComponents.CUSTOM_DATA, tool, tag -> tag.putInt(key, stored));

		player.displayClientMessage(Component.literal("Unbreaking " + (current + 1) + " Progress - " + progress + " / " + target), true);

		if (progress >= target) {
			tool.enchant(enchantment, current + 1);
			level.playSound(null, player.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.3F);
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
