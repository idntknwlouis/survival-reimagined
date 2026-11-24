package net.mcreator.survivalreimagined.mixin;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModSoundTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class BlockSoundOverride {
    @Inject(
            method = "getSoundType",
            at = @At("HEAD"),
            cancellable = true
    )
    private void overrideBlockSound(BlockState state, CallbackInfoReturnable<SoundType> cir) {
        Block block = state.getBlock();

        if (block == Blocks.COPPER_ORE || block == Blocks.IRON_ORE || block == Blocks.GOLD_ORE) {
            cir.setReturnValue(SurvivalReimaginedModSoundTypes.METAL_STONE_ORE);
        }
        if (block == Blocks.DEEPSLATE_COPPER_ORE || block == Blocks.DEEPSLATE_IRON_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) {
            cir.setReturnValue(SurvivalReimaginedModSoundTypes.METAL_DEEPSLATE_ORE);
        }
        if (block == Blocks.DEEPSLATE_DIAMOND_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE) {
            cir.setReturnValue(SurvivalReimaginedModSoundTypes.GEM_DEEPSLATE_ORE);
        }
        if (block == Blocks.DIAMOND_ORE || block == Blocks.EMERALD_ORE || block == Blocks.LAPIS_ORE) {
            cir.setReturnValue(SurvivalReimaginedModSoundTypes.GEM_STONE_ORE);
        }
    }
}
