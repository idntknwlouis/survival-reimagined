package net.mcreator.survivalreimagined.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.world.inventory.MetalRefiningTableGUIMenu;

import java.util.stream.IntStream;

public class MetalRefiningTableBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
	public static final int CONTAINER_SIZE = 9;
	public static final TagKey<Item> HAMMERS = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:tools/hammer"));
	private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();

	private NonNullList<ItemStack> stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

	public MetalRefiningTableBlockEntity(BlockPos pos, BlockState state) {
		super(SurvivalReimaginedModBlockEntities.METAL_REFINING_TABLE.get(), pos, state);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(tag)) {
			ContainerHelper.loadAllItems(tag, this.stacks, registries);
		}
		this.stacks.set(3, ItemStack.EMPTY);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		ItemStack preview = this.stacks.get(3);
		this.stacks.set(3, ItemStack.EMPTY);
		super.saveAdditional(tag, registries);
		if (!this.trySaveLootTable(tag)) {
			ContainerHelper.saveAllItems(tag, this.stacks, registries);
		}
		this.stacks.set(3, preview);
	}

	@Override
	public int getContainerSize() {
		return CONTAINER_SIZE;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.survival_reimagined.metal_refining_table");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
		this.refreshResult();
		return new MetalRefiningTableGUIMenu(id, inventory, this);
	}

	@Override
	public BlockPos getScreenOpeningData(ServerPlayer player) {
		return this.worldPosition;
	}

	public static boolean isHammer(ItemStack stack) {
		return stack.is(HAMMERS);
	}

	public void refreshResult() {
		if (this.level != null && this.level.isClientSide()) return;
		Item result = getRefiningResult(this.getItem(0), this.getItem(1));
		if (result != null && isHammer(this.getItem(2))) {
			this.stacks.set(3, new ItemStack(result));
		} else {
			this.stacks.set(3, ItemStack.EMPTY);
		}
		this.setChanged();
	}

	public void clearPreview() {
		this.stacks.set(3, ItemStack.EMPTY);
		this.setChanged();
	}

	public void takeResult(Player player) {
		if (getRefiningResult(this.getItem(0), this.getItem(1)) == null || !isHammer(this.getItem(2))) return;
		this.getItem(0).shrink(1);
		if (!this.getItem(1).isEmpty()) this.getItem(1).shrink(1);

		ItemStack hammer = this.getItem(2);
		if (player.level() instanceof ServerLevel serverLevel) {
			hammer.hurtAndBreak(1, serverLevel, null, item -> {});
		}
		if (this.level != null) {
			this.level.playSound(null, this.worldPosition, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 0.8F);
		}
		this.refreshResult();
	}

	private static Item getRefiningResult(ItemStack first, ItemStack second) {
		if (first.is(SurvivalReimaginedModItems.ROUGH_COPPER.get())) return Items.COPPER_INGOT;
		if (first.is(SurvivalReimaginedModItems.ROUGH_IRON.get())) return Items.IRON_INGOT;
		if (first.is(SurvivalReimaginedModItems.ROUGH_GOLD.get())) return Items.GOLD_INGOT;
		if (first.is(SurvivalReimaginedModItems.ROUGH_TIN.get())) return SurvivalReimaginedModItems.TIN_INGOT.get();

		if (first.is(SurvivalReimaginedModItems.COPPER_HANDLE.get())) {
			if (second.is(SurvivalReimaginedModItems.BRONZE_SWORD_BLADE.get())) return SurvivalReimaginedModItems.BRONZE_SWORD.get();
			if (second.is(SurvivalReimaginedModItems.BRONZE_PICKAXE_HEAD.get())) return SurvivalReimaginedModItems.BRONZE_PICKAXE.get();
			if (second.is(SurvivalReimaginedModItems.BRONZE_AXE_HEAD.get())) return SurvivalReimaginedModItems.BRONZE_AXE.get();
			if (second.is(SurvivalReimaginedModItems.BRONZE_SHOVEL_HEAD.get())) return SurvivalReimaginedModItems.BRONZE_SHOVEL.get();
			if (second.is(SurvivalReimaginedModItems.BRONZE_HOE_BLADE.get())) return SurvivalReimaginedModItems.BRONZE_HOE.get();
			if (second.is(SurvivalReimaginedModItems.BRONZE_HAMMER_HEAD.get())) return SurvivalReimaginedModItems.BRONZE_HAMMER.get();
			if (second.is(SurvivalReimaginedModItems.BRONZE_SAW_BLADE.get())) return SurvivalReimaginedModItems.BRONZE_SAW.get();
		}
		if (first.is(SurvivalReimaginedModItems.SMALL_COPPER_HANDLE.get()) && second.is(SurvivalReimaginedModItems.BRONZE_KNIFE_BLADE.get())) {
			return SurvivalReimaginedModItems.BRONZE_KNIFE.get();
		}
		return null;
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		super.setItem(index, stack);
		if (index != 3) this.refreshResult();
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		ItemStack result = super.removeItem(index, count);
		if (index != 3) this.refreshResult();
		return result;
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		ItemStack result = super.removeItemNoUpdate(index);
		if (index != 3) this.refreshResult();
		return result;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 3) return false;
		if (index == 2) return isHammer(stack);
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return SLOTS;
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
		return this.canPlaceItem(index, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return index > 1 && index != 3;
	}

	@Override
	public void startOpen(Player player) {
		super.startOpen(player);
		this.refreshResult();
	}

	@Override
	public void stopOpen(Player player) {
		super.stopOpen(player);
		this.clearPreview();
	}
}
