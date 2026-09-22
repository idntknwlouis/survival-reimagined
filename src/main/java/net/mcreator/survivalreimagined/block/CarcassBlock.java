package net.mcreator.survivalreimagined.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mcreator.survivalreimagined.block.entity.CarcassBlockEntity;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSounds;

public class CarcassBlock extends Block implements EntityBlock {
	public enum Species {
		COW,
		PIG
	}

	public static final IntegerProperty CARCASS_STATE = IntegerProperty.create("carcass_state", 0, 4);
	private static final TagKey<Item> KNIVES =
			TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/knife"));
	private static final TagKey<Item> SAWS =
			TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/saw"));

	private final Species species;

	public CarcassBlock(Species species) {
		super(BlockBehaviour.Properties.of()
				.sound(SoundType.MUD)
				.strength(1.0F)
				.noOcclusion()
				.isRedstoneConductor((state, level, pos) -> false));
		this.species = species;
		registerDefaultState(stateDefinition.any().setValue(CARCASS_STATE, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CARCASS_STATE);
	}

	@Override
	protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return true;
	}

	@Override
	protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return 0;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CarcassBlockEntity(pos, state);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
			Player player, InteractionHand hand, BlockHitResult hit) {
		if (hand != InteractionHand.MAIN_HAND || !(level.getBlockEntity(pos) instanceof CarcassBlockEntity carcass)) {
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}

		int stage = state.getValue(CARCASS_STATE);
		boolean knifeStage = stack.is(KNIVES) && (stage == 0 || stage == 3);
		boolean sawStage = stack.is(SAWS) && (stage == 1 || stage == 2);
		if (!knifeStage && !sawStage) {
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}

		player.swing(hand, true);
		if (level.isClientSide()) {
			return ItemInteractionResult.SUCCESS;
		}

		level.playSound(null, pos, SoundEvents.MUD_STEP, SoundSource.BLOCKS, 1.0F, 1.0F);
		damageTool(stack);

		if (carcass.incrementProgress() < 20) {
			return ItemInteractionResult.SUCCESS;
		}

		carcass.resetProgress();
		if (stage == 0) {
			level.setBlock(pos, state.setValue(CARCASS_STATE, 1), 3);
			Block.popResource(level, pos.above(), hide());
			level.playSound(null, pos, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.BLOCKS, 1.0F, 1.0F);
		} else if (stage == 1) {
			level.setBlock(pos, state.setValue(CARCASS_STATE, 2), 3);
			for (int i = 0; i < 4; i++) {
				Block.popResource(level, pos, leg());
			}
			level.playSound(null, pos, SurvivalReimaginedModSounds.LIMB_REMOVE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
		} else if (stage == 2) {
			level.setBlock(pos, state.setValue(CARCASS_STATE, 3), 3);
			Block.popResource(level, pos, head());
			level.playSound(null, pos, SurvivalReimaginedModSounds.LIMB_REMOVE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
		} else if (stage == 3) {
			level.setBlock(pos, state.setValue(CARCASS_STATE, 4), 3);
			Block.popResource(level, pos, meat());
			Block.popResource(level, pos, new ItemStack(SurvivalReimaginedModItems.LUNGS.get()));
			Block.popResource(level, pos, new ItemStack(SurvivalReimaginedModItems.HEART_ITEM.get()));
			Block.popResource(level, pos, new ItemStack(SurvivalReimaginedModItems.LIVER.get()));
			Block.popResource(level, pos, new ItemStack(SurvivalReimaginedModItems.STOMACH.get()));
			Block.popResource(level, pos, new ItemStack(SurvivalReimaginedModItems.INTESTINES.get()));
			level.playSound(null, pos, SoundEvents.MUD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
		}

		return ItemInteractionResult.SUCCESS;
	}

	private ItemStack hide() {
		return switch (species) {
			case COW -> new ItemStack(SurvivalReimaginedModItems.COW_HIDE.get());
			case PIG -> new ItemStack(SurvivalReimaginedModItems.PIG_SKIN.get());
		};
	}

	private ItemStack leg() {
		return switch (species) {
			case COW -> new ItemStack(SurvivalReimaginedModBlocks.COW_LEG.get());
			case PIG -> new ItemStack(SurvivalReimaginedModBlocks.PIG_LEG.get());
		};
	}

	private ItemStack head() {
		return switch (species) {
			case COW -> new ItemStack(SurvivalReimaginedModBlocks.COW_HEAD.get());
			case PIG -> new ItemStack(SurvivalReimaginedModBlocks.PIG_HEAD.get());
		};
	}

	private ItemStack meat() {
		return switch (species) {
			case COW -> new ItemStack(SurvivalReimaginedModItems.BEEF.get());
			case PIG -> new ItemStack(SurvivalReimaginedModItems.RAW_PORKCHOP.get());
		};
	}

	private static void damageTool(ItemStack stack) {
		if (!stack.isDamageableItem()) return;
		stack.setDamageValue(stack.getDamageValue() + 1);
		if (stack.getDamageValue() >= stack.getMaxDamage()) {
			stack.shrink(1);
		}
	}
}
