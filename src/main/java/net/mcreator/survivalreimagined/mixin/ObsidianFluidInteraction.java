package net.mcreator.survivalreimagined.mixin;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LiquidBlock.class)
public class ObsidianFluidInteraction {
    @ModifyArg(
            method = "shouldSpreadLiquid",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            ),
            index = 1
    )
    private BlockState survivalreimagined$replaceObsidian(BlockState state) {
        if (state.is(Blocks.OBSIDIAN)) {
            return SurvivalReimaginedModBlocks.BRITTLE_OBSIDIAN.get().defaultBlockState();
        }
        return state;
    }
}
