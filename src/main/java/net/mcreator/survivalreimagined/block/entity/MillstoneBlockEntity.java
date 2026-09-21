package net.mcreator.survivalreimagined.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlockEntities;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.world.inventory.MillstoneGUIMenu;

import java.util.stream.IntStream;

public class MillstoneBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory<BlockPos> {
    public static final int CONTAINER_SIZE = 2;
    public static final int OUTPUT_SLOT = 0;
    public static final int INPUT_SLOT = 1;
    public static final int MAX_PROGRESS = 8;
    private static final int[] SLOTS = IntStream.range(0, CONTAINER_SIZE).toArray();

    private NonNullList<ItemStack> stacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private int progress;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return index == 0 ? MillstoneBlockEntity.this.progress : 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) MillstoneBlockEntity.this.progress = value;
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public MillstoneBlockEntity(BlockPos pos, BlockState state) {
        super(SurvivalReimaginedModBlockEntities.MILLSTONE.get(), pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.stacks, registries);
        }
        this.progress = tag.getInt("Milling");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.stacks, registries);
        }
        tag.putInt("Milling", this.progress);
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
        return Component.translatable("block.survival_reimagined.millstone");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new MillstoneGUIMenu(id, inventory, this);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player) {
        return this.worldPosition;
    }

    public ContainerData getDataAccess() {
        return this.dataAccess;
    }

    public static boolean isMillingInput(ItemStack stack) {
        return getMillingResult(stack) != null;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MillstoneBlockEntity millstone) {
        if (level.isClientSide()) return;

        Item result = getMillingResult(millstone.getItem(INPUT_SLOT));
        if (result == null || !millstone.canOutput(result)) {
            if (millstone.progress != 0) {
                millstone.progress = 0;
                millstone.setChanged();
            }
            return;
        }

        millstone.progress++;
        if (millstone.progress >= MAX_PROGRESS) {
            millstone.getItem(INPUT_SLOT).shrink(1);
            ItemStack output = millstone.getItem(OUTPUT_SLOT);
            if (output.isEmpty()) millstone.setItem(OUTPUT_SLOT, new ItemStack(result));
            else output.grow(1);
            millstone.progress = 0;
        }
        millstone.setChanged();
    }

    private boolean canOutput(Item result) {
        ItemStack output = this.getItem(OUTPUT_SLOT);
        return output.isEmpty() || output.is(result) && output.getCount() < output.getMaxStackSize();
    }

    private static Item getMillingResult(ItemStack input) {
        if (input.is(Items.WHEAT)) return SurvivalReimaginedModItems.WHEAT_FLOUR.get();
        if (input.is(SurvivalReimaginedModItems.RYE.get())) return SurvivalReimaginedModItems.RYE_FLOUR.get();
        if (input.is(SurvivalReimaginedModItems.SPELT.get())) return SurvivalReimaginedModItems.SPELT_FLOUR.get();
        return null;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return index == INPUT_SLOT && isMillingInput(stack);
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
        return index == OUTPUT_SLOT;
    }
}
