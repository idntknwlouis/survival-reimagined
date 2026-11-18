package net.mcreator.survivalreimagined.procedures.torch;

import net.mcreator.survivalreimagined.block.CrudeTorch;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class TorchParticles {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState state) {
        if (!state.hasProperty(CrudeTorch.LIT) || !state.getValue(CrudeTorch.LIT)) return;

        Direction facing = state.getValue(CrudeTorch.FACING);

        double particleX = x + 0.5;
        double particleY = y + 0.7;
        double particleZ = z + 0.5;

        switch (facing) {
            case SOUTH -> {
                particleY = y + 0.9;
                particleZ = z + 0.75;
            }
            case NORTH -> {
                particleY = y + 0.9;
                particleZ = z + 0.25;
            }
            case EAST -> {
                particleY = y + 0.9;
                particleX = x + 0.75;
            }
            case WEST -> {
                particleY = y + 0.9;
                particleX = x + 0.25;
            }
            case UP -> {
                particleY = y +0.7;
            }
            default -> {
                return;
            }
        }
        world.addParticle(ParticleTypes.FLAME, particleX, particleY, particleZ, 0, 0, 0);
        world.addParticle(ParticleTypes.SMOKE, particleX, particleY, particleZ, 0, 0.05, 0);
    }
}
