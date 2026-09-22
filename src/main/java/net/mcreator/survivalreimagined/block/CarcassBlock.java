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
import net.minecraft.world.item.Items;
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
		PIG,
		SHEEP,
		GOAT,
		CHICKEN
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
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		int stage = state.getValue(CARCASS_STATE);
		return switch (species) {
			case COW -> cowShape(stage);
			case PIG -> pigShape(stage);
			case SHEEP -> sheepShape(stage);
			case GOAT -> goatShape(stage);
			case CHICKEN -> chickenShape(stage);
		};
	}

	private static VoxelShape cowShape(int stage) {
		if (stage == 1) {
			return Shapes.or(
					box(-7, 2, -7, 1, 10, -1),
					box(4, 8, 13, 16, 12, 17),
					box(4, 0, 13, 16, 4, 17),
					box(4, 8, -1, 16, 12, 3),
					box(4, 0, -1, 16, 4, 3),
					box(-6, 0, -1, 4, 12, 17)
			);
		}
		if (stage == 2) {
			return Shapes.or(
					box(-7, 2, -7, 1, 10, -1),
					box(-6, 0, -1, 4, 12, 17)
			);
		}
		if (stage == 3 || stage == 4) {
			return box(-6, 0, -1, 4, 12, 17);
		}
		return Shapes.or(
				box(-7, 2, -7, 1, 10, -1),
				box(-8, 10, -5, -5, 11, -4),
				box(-8, 1, -5, -5, 2, -4),
				box(4, 8, 13, 16, 12, 17),
				box(4, 0, 13, 16, 4, 17),
				box(4, 8, -1, 16, 12, 3),
				box(4, 0, -1, 16, 4, 3),
				box(-6, 0, -1, 4, 12, 17)
		);
	}

	private static VoxelShape pigShape(int stage) {
		if (stage == 2) {
			return Shapes.or(
					box(-0.64286, 1.35714, -2, 7.35714, 9.35714, 6),
					box(3.35714, 3.35714, -3, 6.35714, 7.35714, -2),
					box(1.35714, 0.35714, 4, 9.35714, 10.35714, 20)
			);
		}
		if (stage == 3 || stage == 4) {
			return box(1.35714, 0.35714, 4, 9.35714, 10.35714, 20);
		}
		return Shapes.or(
				box(9.35714, 0.35714, 5, 15.35714, 4.35714, 9),
				box(9.35714, 6.35714, 5, 15.35714, 10.35714, 9),
				box(9.35714, 0.35714, 17, 15.35714, 4.35714, 21),
				box(9.35714, 6.35714, 17, 15.35714, 10.35714, 21),
				box(-0.64286, 1.35714, -2, 7.35714, 9.35714, 6),
				box(3.35714, 3.35714, -3, 6.35714, 7.35714, -2),
				box(1.35714, 0.35714, 4, 9.35714, 10.35714, 20)
		);
	}

	private static VoxelShape sheepShape(int stage) {
		if (stage == 1) {
			return Shapes.or(
					box(5.66667, 5.66667, 0, 17.66667, 9.66667, 4),
					box(5.66667, -0.33333, 0, 17.66667, 3.66667, 4),
					box(5.66667, 5.66667, 12, 17.66667, 9.66667, 16),
					box(5.66667, -0.33333, 12, 17.66667, 3.66667, 16),
					box(-4.33333, 1.66667, -7, 1.66667, 7.66667, 1),
					box(1.16667, 0.33334, -0.83333, 7.16667, 8.33334, 15.16667)
			);
		}
		if (stage == 2) {
			return Shapes.or(
					box(-4.33333, 1.66667, -7, 1.66667, 7.66667, 1),
					box(1.16667, 0.33334, -0.83333, 7.16667, 8.33334, 15.16667)
			);
		}
		if (stage == 3 || stage == 4) {
			return box(1.16667, 0.33334, -0.83333, 7.16667, 8.33334, 15.16667);
		}
		return Shapes.or(
				box(5.66667, 5.66667, 0, 17.66667, 9.66667, 4),
				box(5.66667, -0.33333, 0, 17.66667, 3.66667, 4),
				box(5.66667, 5.66667, 12, 17.66667, 9.66667, 16),
				box(5.66667, -0.33333, 12, 17.66667, 3.66667, 16),
				box(-4.33333, 1.66667, -7, 1.66667, 7.66667, 1),
				box(1.16667, 0.33334, -0.83333, 7.16667, 8.33334, 15.16667)
		);
	}

	private static VoxelShape goatShape(int stage) {
		if (stage == 1) {
			return Shapes.or(
					box(2, 0, -1, 13, 9, 15),
					box(13, 1, 10, 19, 4, 13),
					box(13, 5, 10, 19, 8, 13),
					box(9, 5, 0, 19, 8, 3),
					box(-9, 5, -2, -2, 7, 0),
					box(-9, 2, -2, -2, 4, 0),
					box(9, 1, 0, 19, 4, 3),
					box(-2, 2, -10, 5, 7, 0)
			);
		}
		if (stage == 2) {
			return Shapes.or(
					box(2, 0, -1, 13, 9, 15),
					box(-9, 5, -2, -2, 7, 0),
					box(-9, 2, -2, -2, 4, 0),
					box(-2, 2, -10, 5, 7, 0)
			);
		}
		if (stage == 3 || stage == 4) {
			return box(2, 0, -1, 13, 9, 15);
		}
		return Shapes.or(
				box(2, 0, -1, 13, 9, 15),
				box(1, -1, -2, 15, 10, 9),
				box(13, 1, 10, 19, 4, 13),
				box(13, 5, 10, 19, 8, 13),
				box(9, 5, 0, 19, 8, 3),
				box(-1, -1, -3, 1, 2, -2),
				box(-1, 7, -3, 1, 10, -2),
				box(-9, 5, -2, -2, 7, 0),
				box(-9, 2, -2, -2, 4, 0),
				box(9, 1, 0, 19, 4, 3),
				box(-2, 2, -10, 5, 7, 0)
		);
	}

	private static VoxelShape chickenShape(int stage) {
		return Shapes.or(
				box(4.17525, -0.16789, 5.34315, 10.17525, 5.83211, 13.34315),
				box(5.83211, -1.16789, 6, 9.83211, -0.16789, 12),
				box(5.83211, 5.83211, 6, 9.83211, 6.83211, 12),
				box(1.83211, 0.83211, 3, 7.83211, 4.83211, 6),
				box(5.83211, 1.83211, 2, 7.83211, 3.83211, 4),
				box(3.83211, 0.83211, 1, 5.83211, 4.83211, 3)
		);
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

		if (species == Species.CHICKEN) {
			if (stage != 0 || !stack.isEmpty()) {
				return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
			}

			player.swing(hand, true);
			if (level.isClientSide()) {
				return ItemInteractionResult.SUCCESS;
			}

			level.playSound(null, pos, SoundEvents.WOOL_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (carcass.incrementProgress() >= 10) {
				carcass.resetProgress();
				level.setBlock(pos, state.setValue(CARCASS_STATE, 1), 3);
				int feathers = 3 + level.random.nextInt(7);
				for (int i = 0; i < feathers; i++) {
					Block.popResource(level, pos, new ItemStack(Items.FEATHER));
				}
				level.playSound(null, pos, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.BLOCKS, 1.0F, 1.0F);
			}
			return ItemInteractionResult.SUCCESS;
		}

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
			case SHEEP -> new ItemStack(SurvivalReimaginedModItems.SHEEP_HIDE.get());
			case GOAT -> new ItemStack(SurvivalReimaginedModItems.GOAT_HIDE.get());
			case CHICKEN -> new ItemStack(Items.FEATHER);
		};
	}

	private ItemStack leg() {
		return switch (species) {
			case COW -> new ItemStack(SurvivalReimaginedModBlocks.COW_LEG.get());
			case PIG -> new ItemStack(SurvivalReimaginedModBlocks.PIG_LEG.get());
			case SHEEP -> new ItemStack(SurvivalReimaginedModBlocks.SHEEP_LEG.get());
			case GOAT -> new ItemStack(SurvivalReimaginedModBlocks.GOAT_LEG.get());
			case CHICKEN -> ItemStack.EMPTY;
		};
	}

	private ItemStack head() {
		return switch (species) {
			case COW -> new ItemStack(SurvivalReimaginedModBlocks.COW_HEAD.get());
			case PIG -> new ItemStack(SurvivalReimaginedModBlocks.PIG_HEAD.get());
			case SHEEP -> new ItemStack(SurvivalReimaginedModBlocks.SHEEP_HEAD.get());
			case GOAT -> new ItemStack(SurvivalReimaginedModBlocks.GOAT_HEAD.get());
			case CHICKEN -> ItemStack.EMPTY;
		};
	}

	private ItemStack meat() {
		return switch (species) {
			case COW -> new ItemStack(SurvivalReimaginedModItems.BEEF.get());
			case PIG -> new ItemStack(SurvivalReimaginedModItems.RAW_PORKCHOP.get());
			case SHEEP -> new ItemStack(SurvivalReimaginedModItems.RAW_MUTTON.get());
			case GOAT -> new ItemStack(SurvivalReimaginedModItems.RAW_MUTTON.get());
			case CHICKEN -> new ItemStack(SurvivalReimaginedModItems.RAW_CHICKEN.get());
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
