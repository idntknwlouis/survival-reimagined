package net.mcreator.survivalreimagined.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SurfaceRockItem extends Item {
	private final Supplier<? extends Block> placedBlock;
	private final ResourceLocation placementSound;

	public SurfaceRockItem(Supplier<? extends Block> placedBlock, String placementSound) {
		super(new Item.Properties());
		this.placedBlock = placedBlock;
		this.placementSound = ResourceLocation.parse(placementSound);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos target = context.getClickedPos().above();
		if (!level.isEmptyBlock(target)) {
			return InteractionResult.PASS;
		}

		if (!level.isClientSide()) {
			level.setBlock(target, placedBlock.get().defaultBlockState(), 3);
			context.getItemInHand().shrink(1);
			SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(placementSound);
			if (sound != null) {
				level.playSound(null, context.getClickedPos(), sound, SoundSource.BLOCKS, 1.0F, 0.8F);
			}
		}
		return InteractionResult.SUCCESS;
	}
}
