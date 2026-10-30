package net.mcreator.survivalreimagined.procedures.gui.tanning;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.block.entity.TanningBarrelBlockEntity;
import net.mcreator.survivalreimagined.world.inventory.TanningBarrelGUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class SealBarrel {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x,y,z);
        if (getBlockNBTLogic(world, pos, "CanSeal")) {
            {
                BlockState blockState = world.getBlockState(pos);
                if (blockState.getBlock().getStateDefinition().getProperty("opened") instanceof BooleanProperty booleanProperty)
                    world.setBlock(pos, blockState.setValue(booleanProperty, false), 3);
            }
            SurvivalReimaginedMod.queueServerWork(1, () -> {
                if (world instanceof Level level) {
                    if (!level.isClientSide()) {
                        level.playSound(null, pos, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:barrel/seal")), SoundSource.BLOCKS, 2, 1);
                    } else {
                        level.playLocalSound(x,y,z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("survival_reimagined:barrel/seal")), SoundSource.BLOCKS,2,1,false);
                    }
                }
            });
            if (!world.isClientSide()) {
                BlockState blockState = world.getBlockState(pos);

                setBlockNBTLogic(world, pos, "CanSeal", false);
                setBlockNBTDouble(world, pos, "BarrelClock", 0);

                if (world instanceof Level level) {
                    level.sendBlockUpdated(pos, blockState, blockState, 3);
                }
                {
                    final Vec3 _center = new Vec3(x,y,z);
                    for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(_center))).toList()) {
                        if (entityiterator instanceof Player) {
                            if (entityiterator instanceof Player player1 && player1.containerMenu instanceof TanningBarrelGUIMenu) {
                                SurvivalReimaginedMod.queueServerWork(3, () -> {
                                    if (entityiterator instanceof ServerPlayer _serverPlayer) {
                                        _serverPlayer.closeContainer();
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            return barrel.getCustomData().getBoolean(tag);
        }
        return false;
    }

    private static void setBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag, boolean value) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            barrel.getCustomData().putBoolean(tag, value);
            barrel.setChanged();
        }
    }

    private static void setBlockNBTDouble(LevelAccessor world, BlockPos pos, String tag, double value) {
        BlockEntity blockEntity =  world.getBlockEntity(pos);
        if (blockEntity instanceof TanningBarrelBlockEntity barrel) {
            barrel.getCustomData().putDouble(tag, value);
            barrel.setChanged();
        }
    }
}
