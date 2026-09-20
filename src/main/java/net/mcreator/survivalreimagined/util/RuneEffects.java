package net.mcreator.survivalreimagined.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;

import net.minecraft.advancements.AdvancementHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RuneEffects {
	private static final TagKey<Item> INFUSABLE_TOOL = itemTag("c", "rmi_infusable/tool");
	private static final TagKey<Item> INFUSABLE_WEAPON = itemTag("c", "rmi_infusable/weapon");
	private static final TagKey<Item> INFUSABLE_ARMOR = itemTag("c", "rmi_infusable/armor");
	private static final TagKey<Block> COMMON_ORES = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ores"));
	private static final ResourceKey<Enchantment> OCEANS_WRATH = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("survival_reimagined", "oceans_wrath"));

	private static final Map<UUID, Integer> EMERALD_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Integer> LAPIS_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Integer> SAPPHIRE_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Integer> RUBY_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Integer> AMBER_ARMOR_TICKS = new HashMap<>();
	private static final Map<UUID, Double> SAPPHIRE_TOOL_BASE = new HashMap<>();

	private RuneEffects() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (var player : server.getPlayerList().getPlayers()) {
				tickArmor(player);
				tickTool(player);
				checkRuneAdvancement(player);
			}
		});

		ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
			if (entity instanceof Player defender) {
				procDiamondArmor(defender);
			}
			if (source.getEntity() instanceof Player attacker && entity.level() instanceof ServerLevel level) {
				procOceansWrathDamage(level, attacker, entity, damageTaken);
			}
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
		});


		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (!(source.getEntity() instanceof Player player)) return;
			ItemStack weapon = player.getMainHandItem();
			if (!weapon.is(INFUSABLE_WEAPON) || !(entity.level() instanceof ServerLevel level)) return;

			if (has(weapon, "SapphireInfused")
					&& entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("minecraft", "aquatic")))) {
				advanceOceansWrath(level, player, weapon);
			}

			if (has(weapon, "DiamondInfused")
					&& entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("minecraft", "undead")))) {
				advanceWeaponUnbreaking(level, player, weapon);
			}

			if (has(weapon, "RubyInfused")) {
				float chance = has(weapon, "GoldInfused") ? 0.10F : has(weapon, "SilverInfused") ? 0.075F : 0.0F;
				if (chance > 0.0F && player.getRandom().nextFloat() < chance) {
					player.heal(has(weapon, "GoldInfused") ? 4.0F : 2.0F);
				}
			}

			if (has(weapon, "LapisInfused")) {
				float chance = has(weapon, "GoldInfused") ? 0.20F : has(weapon, "SilverInfused") ? 0.10F : 0.0F;
				if (chance > 0.0F && player.getRandom().nextFloat() < chance) {
					int count = 1 + player.getRandom().nextInt(3);
					for (int i = 0; i < count; i++) {
						int value = has(weapon, "GoldInfused")
								? 3 + player.getRandom().nextInt(5)
								: 1 + player.getRandom().nextInt(3);
						level.addFreshEntity(new ExperienceOrb(level, entity.getX() + 0.5, entity.getY(), entity.getZ() + 0.5, value));
					}
				}
			}

			if (has(weapon, "EmeraldInfused")
					&& entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("minecraft", "illager")))) {
				float chance = has(weapon, "GoldInfused") ? 0.25F : has(weapon, "SilverInfused") ? 0.15F : 0.0F;
				if (chance > 0.0F && player.getRandom().nextFloat() < chance) {
					ItemEntity drop = new ItemEntity(level, entity.getX() + 0.5, entity.getY(), entity.getZ() + 0.5,
							new ItemStack(SurvivalReimaginedModItems.ROUGH_EMERALD.get()));
					drop.setPickUpDelay(10);
					level.addFreshEntity(drop);
				}
			}
		});

		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			ItemStack tool = player.getMainHandItem();
			if (!(level instanceof ServerLevel serverLevel) || !isRuneTool(tool)) return;

			if (has(tool, "AmberInfused") && !player.isCreative()) {
				if (tool.is(net.minecraft.tags.ItemTags.PICKAXES)) {
					spawnAmberOreDrops(serverLevel, player, pos, state, tool);
				} else if (tool.is(net.minecraft.tags.ItemTags.AXES) && state.is(BlockTags.LOGS)) {
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

			if (has(tool, "DiamondInfused")
					&& tool.is(net.minecraft.tags.ItemTags.PICKAXES)
					&& state.is(COMMON_ORES)) {
				advanceUnbreaking(serverLevel, player, tool);
			}

			if (has(tool, "RubyInfused")) {
				float chance = has(tool, "GoldInfused") ? 0.08F : has(tool, "SilverInfused") ? 0.05F : 0.0F;
				int repair = has(tool, "GoldInfused") ? 8 : has(tool, "SilverInfused") ? 4 : 0;
				if (chance > 0.0F && player.getRandom().nextFloat() < chance) {
					if (repair > 0 && tool.isDamageableItem()) {
						tool.setDamageValue(Math.max(0, tool.getDamageValue() - repair));
					}
					if (state.is(COMMON_ORES)) {
						ItemEntity shard = new ItemEntity(serverLevel,
								pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
								new ItemStack(SurvivalReimaginedModItems.RUBY_HEART_SHARD.get()));
						shard.setPickUpDelay(10);
						serverLevel.addFreshEntity(shard);
					}
				}
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

	private static void procDiamondArmor(Player player) {
		boolean diamond = false;
		boolean gold = false;
		boolean silver = false;
		for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD}) {
			ItemStack stack = player.getItemBySlot(slot);
			if (!(stack.getItem() instanceof ArmorItem) && !stack.is(INFUSABLE_ARMOR)) continue;
			if (!has(stack, "DiamondInfused")) continue;
			diamond = true;
			gold |= has(stack, "GoldInfused");
			silver |= has(stack, "SilverInfused");
		}
		if (!diamond) return;
		float chance = gold ? 0.40F : silver ? 0.20F : 0.0F;
		if (chance <= 0.0F || player.getRandom().nextFloat() >= chance || player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) return;
		player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, gold ? 80 : 60, gold ? 1 : 0));
		player.level().playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
		player.level().playSound(null, player.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.4F, 0.6F);
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
			if (!(stack.getItem() instanceof ArmorItem) && !stack.is(INFUSABLE_ARMOR)) {
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

		if (allAmber && player.isOnFire() && (amberGold || amberSilver)) {
			int amberTimer = AMBER_ARMOR_TICKS.merge(id, 1, Integer::sum);
			if (amberTimer >= 40) {
				float chance = amberGold ? 0.50F : 0.30F;
				if (player.getRandom().nextFloat() < chance) {
					player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 80, 0));
					player.level().playSound(null, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8F, 1.0F);
				}
				AMBER_ARMOR_TICKS.put(id, 0);
			}
		} else {
			AMBER_ARMOR_TICKS.remove(id);
		}

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

	private static void checkRuneAdvancement(ServerPlayer player) {
		boolean hasEmptyRune = player.getInventory().contains(stack -> !stack.isEmpty()
				&& (stack.is(SurvivalReimaginedModItems.EMPTY_GOLD_RUNE.get()) || stack.is(SurvivalReimaginedModItems.EMPTY_SILVER_RUNE.get())));
		if (!hasEmptyRune) return;
		awardAdvancement(player, "runes_adv");
	}

	private static void awardAdvancement(ServerPlayer player, String id) {
		AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath("survival_reimagined", id));
		if (advancement == null) return;
		var progress = player.getAdvancements().getOrStartProgress(advancement);
		if (progress.isDone()) return;
		for (String criterion : progress.getRemainingCriteria()) player.getAdvancements().award(advancement, criterion);
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
			player.displayClientMessage(Component.literal("Underwater Mining Speed Increased by 60%"), true);
		} else {
			Double original = SAPPHIRE_TOOL_BASE.remove(id);
			if (original != null) attribute.setBaseValue(original);
		}
	}

	private static void procOceansWrathDamage(ServerLevel level, Player attacker, LivingEntity target, float originalDamage) {
		if (!target.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("minecraft", "aquatic")))) return;
		ItemStack weapon = attacker.getMainHandItem();
		if (!weapon.is(INFUSABLE_WEAPON)) return;

		var enchantment = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(OCEANS_WRATH);
		int enchantLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, weapon);
		if (enchantLevel <= 0) return;

		float chance = Math.min(1.0F, enchantLevel * 0.20F);
		if (attacker.getRandom().nextFloat() >= chance) return;

		float bonus = 5.0F + enchantLevel * 5.0F;
		level.playSound(null, target.blockPosition(), SoundEvents.TRIDENT_RETURN, SoundSource.PLAYERS, 1.0F, 1.0F);
		DamageSource extraSource = new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.PLAYER_ATTACK));
		target.hurt(extraSource, originalDamage + bonus);
	}

	private static void advanceOceansWrath(ServerLevel level, Player player, ItemStack weapon) {
		boolean gold = has(weapon, "GoldInfused");
		boolean silver = has(weapon, "SilverInfused");
		if (!gold && !silver) return;

		int step = gold ? 30 : 50;
		int maxKills = step * 5;
		CompoundTag data = weapon.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		int kills = Math.min(maxKills, data.getInt("EntityKillCount") + 1);
		CustomData.update(DataComponents.CUSTOM_DATA, weapon, tag -> tag.putInt("EntityKillCount", kills));

		int levelNumber = Math.min(5, ((kills - 1) / step) + 1);
		int target = levelNumber * step;
		if (kills < maxKills) {
			player.displayClientMessage(Component.literal("Oceans Wrath " + roman(levelNumber) + " Progress - " + kills + "/" + target), true);
		} else {
			player.displayClientMessage(Component.literal(gold ? "Oceans Wrath Full" : "Oceans Wrath Maxed"), true);
		}

		if (kills % step == 0) {
			var enchantment = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(OCEANS_WRATH);
			int newLevel = Math.min(5, kills / step);
			weapon.enchant(enchantment, newLevel);
			level.playSound(null, player.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.3F);
		}
	}

	private static String roman(int value) {
		return switch (value) {
			case 1 -> "I";
			case 2 -> "II";
			case 3 -> "III";
			case 4 -> "IV";
			default -> "V";
		};
	}

	private static void advanceWeaponUnbreaking(ServerLevel level, Player player, ItemStack weapon) {
		boolean gold = has(weapon, "GoldInfused");
		boolean silver = has(weapon, "SilverInfused");
		if (!gold && !silver) return;

		var enchantment = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING);
		int current = EnchantmentHelper.getItemEnchantmentLevel(enchantment, weapon);
		if (current >= 4) return;

		String key = gold ? "GoldNumber" : "SilverNumber";
		int step = gold ? 15 : 25;
		int target = step * (current + 1);
		CompoundTag data = weapon.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		int progress = data.getInt(key) + 1;
		final int stored = progress >= target ? 0 : progress;
		CustomData.update(DataComponents.CUSTOM_DATA, weapon, tag -> tag.putInt(key, stored));

		player.displayClientMessage(Component.literal("Unbreaking " + (current + 1) + " Progress - " + progress + " / " + target), true);
		if (progress >= target) {
			weapon.enchant(enchantment, current + 1);
			level.playSound(null, player.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.3F);
		}
	}

	private static void spawnAmberOreDrops(ServerLevel level, Player player, net.minecraft.core.BlockPos pos, BlockState state, ItemStack tool) {
		boolean gold = has(tool, "GoldInfused");
		boolean silver = has(tool, "SilverInfused");
		float proc = gold ? 0.60F : silver ? 0.40F : 0.0F;
		if (proc <= 0.0F || player.getRandom().nextFloat() >= proc) return;

		ItemStack rough = ItemStack.EMPTY;
		ItemStack nugget = ItemStack.EMPTY;

		if (state.is(blockTag("c", "copper_ores"))) {
			rough = new ItemStack(SurvivalReimaginedModItems.ROUGH_COPPER.get());
			nugget = new ItemStack(SurvivalReimaginedModItems.COPPER_NUGGET.get());
		} else if (state.is(blockTag("c", "tin_ores"))) {
			rough = new ItemStack(SurvivalReimaginedModItems.ROUGH_TIN.get());
			nugget = new ItemStack(SurvivalReimaginedModItems.TIN_NUGGET.get());
		} else if (state.is(blockTag("c", "gold_ores"))) {
			rough = new ItemStack(SurvivalReimaginedModItems.ROUGH_GOLD.get());
			nugget = new ItemStack(Items.GOLD_NUGGET);
		} else if (state.is(blockTag("c", "iron_ores"))) {
			rough = new ItemStack(SurvivalReimaginedModItems.ROUGH_IRON.get());
			nugget = new ItemStack(Items.IRON_NUGGET);
		} else if (state.is(blockTag("c", "manganese_ores"))) {
			rough = new ItemStack(SurvivalReimaginedModItems.ROUGH_MANGANESE.get());
			nugget = new ItemStack(SurvivalReimaginedModItems.MANGANESE_NUGGET.get());
		} else if (state.is(blockTag("c", "titanium_ores"))) {
			rough = new ItemStack(SurvivalReimaginedModItems.ROUGH_TITANIUM.get());
			nugget = new ItemStack(SurvivalReimaginedModItems.TITANIUM_NUGGET.get());
		} else if (state.is(blockTag("c", "uraninite_ores"))) {
			rough = new ItemStack(SurvivalReimaginedModItems.ROUGH_URANIUM.get());
			nugget = new ItemStack(SurvivalReimaginedModItems.URANIUM_NUGGET.get());
		} else if (state.is(blockTag("c", "silver_ores"))) {
			rough = new ItemStack(SurvivalReimaginedModItems.ROUGH_SILVER.get());
			nugget = new ItemStack(SurvivalReimaginedModItems.SILVER_NUGGET.get());
		} else {
			return;
		}

		var fortune = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
		int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(fortune, tool);
		int nuggetMin = fortuneLevel > 0 ? 2 : 1;
		int nuggetExtra = gold ? Math.max(2, fortuneLevel + 3) : Math.max(2, fortuneLevel + 2);
		int nuggetCount = nuggetMin + player.getRandom().nextInt(nuggetExtra);

		if (player.getRandom().nextFloat() < 0.50F) {
			int roughCount = 1 + (fortuneLevel > 0 ? player.getRandom().nextInt(fortuneLevel + 1) : 0);
			rough.setCount(roughCount);
			ItemEntity roughDrop = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, rough);
			roughDrop.setPickUpDelay(10);
			level.addFreshEntity(roughDrop);
		}

		nugget.setCount(nuggetCount);
		ItemEntity nuggetDrop = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, nugget);
		nuggetDrop.setPickUpDelay(10);
		level.addFreshEntity(nuggetDrop);
	}

	private static void advanceUnbreaking(ServerLevel level, Player player, ItemStack tool) {
		boolean gold = has(tool, "GoldInfused");
		boolean silver = has(tool, "SilverInfused");
		if (!gold && !silver) return;

		var enchantment = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING);
		int current = EnchantmentHelper.getItemEnchantmentLevel(enchantment, tool);
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



	private static boolean isOreBlock(BlockState state) {
		if (state.is(COMMON_ORES)) return true;
		ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
		if (id == null) return false;
		String path = id.getPath();
		return path.endsWith("_ore")
				|| path.startsWith("deepslate_") && path.endsWith("_ore")
				|| path.startsWith("nether_") && path.endsWith("_ore")
				|| path.equals("ancient_debris");
	}

	private static boolean isRuneTool(ItemStack stack) {
		return stack.is(INFUSABLE_TOOL)
				|| stack.getItem() instanceof PickaxeItem
				|| stack.getItem() instanceof AxeItem
				|| stack.getItem() instanceof ShovelItem
				|| stack.getItem() instanceof HoeItem
				|| stack.is(SurvivalReimaginedModItems.BRONZE_HAMMER.get())
				|| stack.is(SurvivalReimaginedModItems.BRONZE_SAW.get())
				|| stack.is(SurvivalReimaginedModItems.BRONZE_KNIFE.get())
				|| stack.is(SurvivalReimaginedModItems.STEEL_HAMMER.get())
				|| stack.is(SurvivalReimaginedModItems.STEEL_SAW.get())
				|| stack.is(SurvivalReimaginedModItems.STEEL_KNIFE.get())
				|| stack.is(SurvivalReimaginedModItems.DIAMOND_HAMMER.get())
				|| stack.is(SurvivalReimaginedModItems.DIAMOND_SAW.get())
				|| stack.is(SurvivalReimaginedModItems.DIAMOND_KNIFE.get())
				|| stack.is(SurvivalReimaginedModItems.WOODEN_HAMMER.get())
				|| stack.is(SurvivalReimaginedModItems.WOODEN_SAW.get())
				|| stack.is(SurvivalReimaginedModItems.WOODEN_KNIFE.get())
				|| stack.is(SurvivalReimaginedModItems.STONE_HAMMER.get());
	}

	private static boolean isPickaxe(ItemStack stack) {
		return stack.getItem() instanceof PickaxeItem || stack.is(net.minecraft.tags.ItemTags.PICKAXES);
	}

	private static boolean isAxe(ItemStack stack) {
		return stack.getItem() instanceof AxeItem || stack.is(net.minecraft.tags.ItemTags.AXES);
	}

	private static boolean isShovel(ItemStack stack) {
		return stack.getItem() instanceof ShovelItem || stack.is(net.minecraft.tags.ItemTags.SHOVELS);
	}

	private static boolean isHoe(ItemStack stack) {
		return stack.getItem() instanceof HoeItem || stack.is(net.minecraft.tags.ItemTags.HOES);
	}

	public static boolean has(ItemStack stack, String key) {
		return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean(key);
	}

	private static TagKey<Block> blockTag(String namespace, String path) {
		return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, path));
	}

	private static TagKey<Item> itemTag(String namespace, String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
	}
}
