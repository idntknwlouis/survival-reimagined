package net.mcreator.survivalreimagined.block;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSoundTypes;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class TinOreBlock extends Block {
	public TinOreBlock() {
		super(BlockBehaviour.Properties.of().strength(2f).sound(SurvivalReimaginedModSoundTypes.METAL_STONE_ORE).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM));
	}
}
