package net.mcreator.survivalreimagined.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

import net.mcreator.survivalreimagined.block.entity.RuneMagicInfuserBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModMenus;

public class RMIMenu extends AbstractContainerMenu {
	private static final int MACHINE_SLOTS = 3;
	private static final int PLAYER_INV_END = MACHINE_SLOTS + 27;

	private static final TagKey<Item> RMI_INFUSABLE = tag("rmi_infusable");
	private static final TagKey<Item> RMI_INFUSABLE_TOOL = tag("rmi_infusable/tool");
	private static final TagKey<Item> RMI_INFUSABLE_WEAPON = tag("rmi_infusable/weapon");
	private static final TagKey<Item> RMI_INFUSABLE_ARMOR = tag("rmi_infusable/armor");
	private static final TagKey<Item> RMI_RUNES = tag("rmi_runes");

	private final Container container;
	private final BlockPos blockPos;

	public RMIMenu(int id, Inventory inventory, BlockPos pos) {
		this(id, inventory, new SimpleContainer(RuneMagicInfuserBlockEntity.CONTAINER_SIZE), pos);
	}

	public RMIMenu(int id, Inventory inventory, RuneMagicInfuserBlockEntity infuser) {
		this(id, inventory, infuser, infuser.getBlockPos());
	}

	private RMIMenu(int id, Inventory inventory, Container container, BlockPos pos) {
		super(SurvivalReimaginedModMenus.RMI.get(), id);
		checkContainerSize(container, RuneMagicInfuserBlockEntity.CONTAINER_SIZE);
		this.container = container;
		this.blockPos = pos;
		container.startOpen(inventory.player);

		this.addSlot(new Slot(container, 0, 80, 21) {
			@Override public boolean mayPlace(ItemStack stack) { return isInfusable(stack); }
		});
		this.addSlot(new Slot(container, 1, 80, 41) {
			@Override public boolean mayPlace(ItemStack stack) { return isRune(stack); }
			@Override public int getMaxStackSize() { return 1; }
		});
		this.addSlot(new Slot(container, 2, 116, 61) {
			@Override public boolean mayPlace(ItemStack stack) { return stack.is(Items.LAPIS_LAZULI); }
		});

		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 9; col++)
				this.addSlot(new Slot(inventory, col + (row + 1) * 9, 8 + col * 18, 84 + row * 18));
		for (int col = 0; col < 9; col++)
			this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));
	}

	private static TagKey<Item> tag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
	}

	public static boolean isInfusable(ItemStack stack) { return stack.getItem() instanceof ArmorItem || stack.is(RMI_INFUSABLE) || stack.is(RMI_INFUSABLE_TOOL) || stack.is(RMI_INFUSABLE_WEAPON) || stack.is(RMI_INFUSABLE_ARMOR); }
	public static boolean isRune(ItemStack stack) { return stack.is(RMI_RUNES); }
	public BlockPos getBlockPos() { return blockPos; }

	public boolean canInfuse(Player player) {
		ItemStack target = container.getItem(0);
		ItemStack rune = container.getItem(1);
		ItemStack lapis = container.getItem(2);
		if (target.isEmpty() || rune.isEmpty() || !isInfusable(target) || !isRune(rune) || !lapis.is(Items.LAPIS_LAZULI)) return false;
		if (isAlreadyInfused(target)) return false;
		int cost = isGoldRune(rune) ? 3 : isSilverRune(rune) ? 2 : 0;
		return cost > 0 && lapis.getCount() >= cost;
	}

	@Override
	public boolean clickMenuButton(Player player, int id) {
		if (id != 0 || !canInfuse(player)) return false;
		ItemStack target = container.getItem(0);
		ItemStack rune = container.getItem(1);
		ItemStack lapis = container.getItem(2);
		boolean gold = isGoldRune(rune);
		int cost = gold ? 3 : 2;
		String crystal = crystalFlag(rune);
		if (crystal == null) return false;

		CustomData.update(DataComponents.CUSTOM_DATA, target, tag -> {
			tag.putBoolean(gold ? "GoldInfused" : "SilverInfused", true);
			tag.putBoolean(crystal, true);
		});

		rune.shrink(1);
		lapis.shrink(cost);
		player.giveExperienceLevels(-cost);
		player.level().playSound(null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 0.8f, 1f);
		player.level().playSound(null, blockPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.3f, 1f);
		container.setChanged();
		if (player instanceof ServerPlayer serverPlayer) awardAdvancement(serverPlayer, "gem_runes");
		return true;
	}

	private static void awardAdvancement(ServerPlayer player, String id) {
		AdvancementHolder advancement = player.server.getAdvancements().get(ResourceLocation.fromNamespaceAndPath("survival_reimagined", id));
		if (advancement == null) return;
		var progress = player.getAdvancements().getOrStartProgress(advancement);
		if (progress.isDone()) return;
		for (String criterion : progress.getRemainingCriteria()) player.getAdvancements().award(advancement, criterion);
	}

	private static boolean isAlreadyInfused(ItemStack stack) {
		CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		return tag.getBoolean("GoldInfused") || tag.getBoolean("SilverInfused");
	}

	private static boolean isGoldRune(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.SAPPHIRE_GOLD_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.GOLD_AMBER_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.GOLD_DIAMOND_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.GOLD_EMERALD_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.GOLD_RUBY_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.GOLD_LAPIS_RUNE.get());
	}

	private static boolean isSilverRune(ItemStack stack) {
		return stack.is(SurvivalReimaginedModItems.SAPPHIRE_SILVER_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.SILVER_AMBER_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.SILVER_DIAMOND_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.SILVER_EMERALD_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.SILVER_RUBY_RUNE.get())
				|| stack.is(SurvivalReimaginedModItems.SILVER_LAPIS_RUNE.get());
	}

	private static String crystalFlag(ItemStack stack) {
		if (stack.is(SurvivalReimaginedModItems.SAPPHIRE_GOLD_RUNE.get()) || stack.is(SurvivalReimaginedModItems.SAPPHIRE_SILVER_RUNE.get())) return "SapphireInfused";
		if (stack.is(SurvivalReimaginedModItems.GOLD_AMBER_RUNE.get()) || stack.is(SurvivalReimaginedModItems.SILVER_AMBER_RUNE.get())) return "AmberInfused";
		if (stack.is(SurvivalReimaginedModItems.GOLD_DIAMOND_RUNE.get()) || stack.is(SurvivalReimaginedModItems.SILVER_DIAMOND_RUNE.get())) return "DiamondInfused";
		if (stack.is(SurvivalReimaginedModItems.GOLD_EMERALD_RUNE.get()) || stack.is(SurvivalReimaginedModItems.SILVER_EMERALD_RUNE.get())) return "EmeraldInfused";
		if (stack.is(SurvivalReimaginedModItems.GOLD_RUBY_RUNE.get()) || stack.is(SurvivalReimaginedModItems.SILVER_RUBY_RUNE.get())) return "RubyInfused";
		if (stack.is(SurvivalReimaginedModItems.GOLD_LAPIS_RUNE.get()) || stack.is(SurvivalReimaginedModItems.SILVER_LAPIS_RUNE.get())) return "LapisInfused";
		return null;
	}

	@Override public boolean stillValid(Player player) { return container.stillValid(player); }

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack result = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot == null || !slot.hasItem()) return result;
		ItemStack stack = slot.getItem();
		result = stack.copy();

		if (index < MACHINE_SLOTS) {
			if (!moveItemStackTo(stack, MACHINE_SLOTS, slots.size(), true)) return ItemStack.EMPTY;
		} else if (isInfusable(stack)) {
			if (!moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
		} else if (isRune(stack)) {
			if (!moveItemStackTo(stack, 1, 2, false)) return ItemStack.EMPTY;
		} else if (stack.is(Items.LAPIS_LAZULI)) {
			if (!moveItemStackTo(stack, 2, 3, false)) return ItemStack.EMPTY;
		} else if (index < PLAYER_INV_END) {
			if (!moveItemStackTo(stack, PLAYER_INV_END, slots.size(), false)) return ItemStack.EMPTY;
		} else if (!moveItemStackTo(stack, MACHINE_SLOTS, PLAYER_INV_END, false)) return ItemStack.EMPTY;

		if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY); else slot.setChanged();
		if (stack.getCount() == result.getCount()) return ItemStack.EMPTY;
		slot.onTake(player, stack);
		return result;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		container.stopOpen(player);
	}
}
