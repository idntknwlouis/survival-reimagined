package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.loader.api.FabricLoader;
import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.compat.sereneseasons.GetCurrentSeason;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class GrowingLogic {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockState) {
        if (FabricLoader.getInstance().isModLoaded("sereneseasons")) {
            if ((GetCurrentSeason.execute(world)).equals("Spring") && (world.getBlockState(BlockPos.containing(x,y,z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("sereneseasons:spring_crops")))) {
                if (world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    putBlockNBTNumber(world, _bp, "GrowClockMaxSpring", 100);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                    }
                }
            } else {
                if (!world.isClientSide()) {
                   BlockPos _bp = BlockPos.containing(x,y,z);
                   putBlockNBTNumber(world, _bp, "GrowClockMaxSpring", 400);
                   if (world instanceof Level _level) {
                       BlockState _bs = world.getBlockState(_bp);
                       _level.sendBlockUpdated(_bp, _bs, _bs,3);
                   }
                }
            }
            if ((GetCurrentSeason.execute(world)).equals("Summer") && (world.getBlockState(BlockPos.containing(x,y,z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("sereneseasons:summer_crops")))) {
                if (world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    putBlockNBTNumber(world, _bp, "GrowClockMaxSummer", 100);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                    }
                }
            } else {
                if (!world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    putBlockNBTNumber(world, _bp, "GrowClockMaxSummer", 400);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs,3);
                    }
                }
            }
            if ((GetCurrentSeason.execute(world)).equals("Autumn") && (world.getBlockState(BlockPos.containing(x,y,z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("sereneseasons:autumn_crops")))) {
                if (world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    putBlockNBTNumber(world, _bp, "GrowClockMaxAutumn", 100);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                    }
                }
            } else {
                if (!world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    putBlockNBTNumber(world, _bp, "GrowClockMaxAutumn", 400);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs,3);
                    }
                }
            }
            if ((GetCurrentSeason.execute(world)).equals("Winter") && (world.getBlockState(BlockPos.containing(x,y,z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("sereneseasons:winter_crops")))) {
                if (world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    putBlockNBTNumber(world, _bp, "GrowClockMaxWinter", 100);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                    }
                }
            } else {
                if (!world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    putBlockNBTNumber(world, _bp, "GrowClockMaxWinter", 400);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs,3);
                    }
                }
            }
            if (!world.isClientSide()) {
                BlockPos _bp = BlockPos.containing(x,y,z);
                putBlockNBTNumber(world, _bp, "GrowClockMax", 800);
                if (world instanceof Level _level) {
                    BlockState _bs = world.getBlockState(_bp);
                    _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                }
            }
        } else {
            if (!world.isClientSide()) {
                BlockPos _bp = BlockPos.containing(x,y,z);
                putBlockNBTNumber(world, _bp, "GrowClockMax", 200);
                if (world instanceof Level _level) {
                    BlockState _bs = world.getBlockState(_bp);
                    _level.sendBlockUpdated(_bp, _bs,_bs,3);
                }
            }
        }
        if ((world.getBlockState(BlockPos.containing(x,y - 1,z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:soil")))) {
            if ((getPropertyName(blockState, "age") instanceof IntegerProperty _getip23 ? blockState.getValue(_getip23) : -1) < (blockState.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _max25
            ? _max25.getPossibleValues().stream().max(Integer::compareTo).get() : -1)
            && getBlockNBTNumber(world, BlockPos.containing(x,y - 1,z), "N") > 0 && getBlockNBTNumber(world, BlockPos.containing(x, y - 1,z), "P") > 0 && getBlockNBTNumber(world, BlockPos.containing(x, y - 1,z), "K") > 0
            && ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock().getStateDefinition().getProperty("moisture") instanceof IntegerProperty _max30
            ? _max30.getPossibleValues().stream().max(Integer::compareTo).get()
            : -1) <= 7) {
                if (!world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    putBlockNBTNumber(world, _bp, "GrowClock", (getBlockNBTNumber(world, _bp, "GrowClock") + 1));
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                    }
                }
            }
        } else if ((world.getBlockState(BlockPos.containing(x,y,z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:fruit_of_tree")))
                || (world.getBlockState(BlockPos.containing(x,y,z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:berry_bush")))) {
            if (!world.isClientSide()) {
                BlockPos _bp = BlockPos.containing(x,y,z);
                putBlockNBTNumber(world, _bp, "GrowClock", (getBlockNBTNumber(world, _bp, "GrowClock") + 1));
                if (world instanceof Level _level) {
                    BlockState _bs = world.getBlockState(_bp);
                    _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                }
            }
        }
        if (FabricLoader.getInstance().isModLoaded("sereneseasons")) {
            if (getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClock") > getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClockMax")
                    || getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClock") > getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClockMaxSpring")
                    || getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClock") > getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClockMaxSummer")
                    || getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClock") > getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowBlockMaxAutumn")
                    || getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClock") > getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowBlockMaxAutumn")
            ) {
                if (Math.random() < 0.45) {
                    SurvivalReimaginedMod.queueServerWork(1, () -> {
                        {
                            int _value = (getPropertyName(blockState, "age") instanceof IntegerProperty _getip52 ? blockState.getValue(_getip52) : -1) + 1;
                            BlockPos _pos = BlockPos.containing(x,y,z);
                            BlockState _bs = world.getBlockState(_pos);
                            if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                                world.setBlock(_pos, _bs.setValue(_intProp, _value), 3);
                        }
                    });
                    if (!world.isClientSide()) {
                        BlockPos _bp = BlockPos.containing(x,y,z);
                        putBlockNBTNumber(world, _bp, "GrowClock", 0);
                        if (world instanceof Level _level) {
                            BlockState _bs = world.getBlockState(_bp);
                            _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                        }
                    }
                } else {
                    if (!world.isClientSide()) {
                        BlockPos _bp = BlockPos.containing(x,y,z);
                        putBlockNBTNumber(world, _bp, "GrowClock", 0);
                        if (world instanceof Level _level) {
                            BlockState _bs = world.getBlockState(_bp);
                            _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                        }
                    }
                }
            }
        } else if (!FabricLoader.getInstance().isModLoaded("sereneseasons")) {
            if (getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClock") > getBlockNBTNumber(world, BlockPos.containing(x,y,z), "GrowClockMax")) {
                if (Math.random() < 0.45) {
                    SurvivalReimaginedMod.queueServerWork(1, () -> {
                        {
                            int _value = (getPropertyName(blockState, "age") instanceof IntegerProperty _getip61 ? blockState.getValue(_getip61) : -1) + 1;
                            BlockPos _pos = BlockPos.containing(x,y,z);
                            BlockState _bs = world.getBlockState(_pos);
                            if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                                world.setBlock(_pos, _bs.setValue(_intProp, _value), 3);
                        }
                    });
                    if (!world.isClientSide()) {
                        BlockPos _bp = BlockPos.containing(x,y,z);
                        putBlockNBTNumber(world, _bp, "GrowClock", 0);
                        if (world instanceof Level _level) {
                            BlockState _bs = world.getBlockState(_bp);
                            _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                        }
                    }
                } else {
                    if (!world.isClientSide()) {
                        BlockPos _bp = BlockPos.containing(x,y,z);
                        putBlockNBTNumber(world, _bp, "GrowClock", 0);
                        if (world instanceof Level _level) {
                            BlockState _bs = world.getBlockState(_bp);
                            _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                        }
                    }
                }
            }
        }
        if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 4)) && world.isEmptyBlock(BlockPos.containing(x,y + 1,z))) {
            world.setBlock(BlockPos.containing(x, y + 1, z), SurvivalReimaginedModBlocks.CORN_STALK_MIDDLE.get().defaultBlockState(), 3);
        }
        if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 7)) && world.isEmptyBlock(BlockPos.containing(x,y + 2,z))) {
            world.setBlock(BlockPos.containing(x, y + 2, z), SurvivalReimaginedModBlocks.CORN_STALK_TOP.get().defaultBlockState(), 3);
        }
        if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 5))) {
            {
                int _value = 1;
                BlockPos _pos = BlockPos.containing(x,y + 1,z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value), 3);
            }
        } else if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 6))) {
            {
                int _value = 2;
                BlockPos _pos = BlockPos.containing(x, y + 1, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
        } else if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 7))) {
            {
                int _value = 3;
                BlockPos _pos = BlockPos.containing(x, y + 1, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
        } else if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 8))) {
            {
                int _value = 4;
                BlockPos _pos = BlockPos.containing(x, y + 1, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
            {
                int _value = 1;
                BlockPos _pos = BlockPos.containing(x, y + 2, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
        } else if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 9))) {
            {
                int _value = 5;
                BlockPos _pos = BlockPos.containing(x, y + 1, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
            {
                int _value = 2;
                BlockPos _pos = BlockPos.containing(x, y + 2, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
        } else if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 10))) {
            {
                int _value = 6;
                BlockPos _pos = BlockPos.containing(x, y + 1, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
            {
                int _value = 3;
                BlockPos _pos = BlockPos.containing(x, y + 2, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
        } else if ((world.getBlockState(BlockPos.containing(x,y,z))) == (blockStateWithInt(SurvivalReimaginedModBlocks.CORN_STALK_BOTTOM.get().defaultBlockState(), "age", 10))) {
            {
                int _value = 7;
                BlockPos _pos = BlockPos.containing(x, y + 1, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
            {
                int _value = 4;
                BlockPos _pos = BlockPos.containing(x, y + 2, z);
                BlockState _bs = world.getBlockState(_pos);
                if (_bs.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _intProp && _intProp.getPossibleValues().contains(_value))
                    world.setBlock(_pos, _bs.setValue(_intProp, _value),3);
            }
        }
        if ((world.getBlockState(BlockPos.containing(x,y - 1,z))).is((TagKey.create(Registries.BLOCK, ResourceLocation.parse("c:soil"))))) {
            if (getBlockNBTNumber(world, BlockPos.containing(x,y - 1, z), "N") <= 0
                    || getBlockNBTNumber(world, BlockPos.containing(x,y - 1, z), "P") <= 0
                    || getBlockNBTNumber(world, BlockPos.containing(x,y - 1, z), "K") <= 0)
            {
                if (!world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x, y - 1, z);
                    putBlockNBTNumber(world, _bp, "DeathClock", (getBlockNBTNumber(world, _bp, "DeathClock") + 1));
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                    }
                }
            } else {
                if (!world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x, y - 1, z);
                    putBlockNBTNumber(world, _bp, "DeathClock", 0);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                    }
                }
            }
            if (getBlockNBTNumber(world, BlockPos.containing(x,y - 1,z), "DeathClock") >= 2000) {
                if (!world.isClientSide()) {
                    BlockPos _bp = BlockPos.containing(x,y - 1,z);
                    putBlockNBTNumber(world, _bp, "DeathClock", 0);
                    if (world instanceof Level _level) {
                        BlockState _bs = world.getBlockState(_bp);
                        _level.sendBlockUpdated(_bp, _bs, _bs, 3);
                    }
                }
                {
                    BlockPos _bp = BlockPos.containing(x,y,z);
                    BlockState _bs = Blocks.DEAD_BUSH.defaultBlockState();
                    BlockState _bso = world.getBlockState(_bp);
                    for (Property<?> _propertyOld : _bso.getProperties()) {
                        Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
                        if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
                            try {
                                _bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
                            } catch (Exception e) {}
                    }
                    world.setBlock(_bp, _bs, 3);
                }
                {
                    final Vec3 _center = new Vec3(x,y,z);
                    for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
                        if (entityIterator instanceof ServerPlayer) {
                            if (entityIterator instanceof ServerPlayer _player) {
                                AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("survival_reimagined:terrible_farmer"));
                                if (_adv != null) {
                                    AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
                                    if (!_ap.isDone()) {
                                        for (String criteria : _ap.getRemainingCriteria())
                                            _player.getAdvancements().award(_adv, criteria);
                                    }
                                }
                            }
                        }
                    }
                }
                if ((world.getBlockState(BlockPos.containing(x,y + 1,z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("survival_reimagined:corn")))
                && (world.getBlockState(BlockPos.containing(x, y + 2, z))).is(TagKey.create(Registries.BLOCK, ResourceLocation.parse("survival_reimagined:corn")))) {
                    world.setBlock(BlockPos.containing(x,y + 1,z), Blocks.AIR.defaultBlockState(), 3);
                    world.setBlock(BlockPos.containing(x, y + 2, z), Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
    }
    private static Property<?> getPropertyName(BlockState state, String name) {
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }

    private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            CompoundTag compound = blockEntity.saveWithFullMetadata(world.registryAccess());
            if (compound.contains(tag)) {
                return compound.getDouble(tag);
            }
        }
        return -1;
    }
    private static void putBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag, double value) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            CompoundTag compound = blockEntity.saveWithFullMetadata(world.registryAccess());
            compound.putDouble(tag, value);
            blockEntity.loadWithComponents(compound, world.registryAccess());
            blockEntity.setChanged();
        }
    }
    private static BlockState blockStateWithInt(BlockState blockState, String property, int newValue) {
        Property<?> prop = blockState.getBlock().getStateDefinition().getProperty(property);
        return prop instanceof IntegerProperty ip && prop.getPossibleValues().contains(newValue) ? blockState.setValue(ip, newValue) : blockState;
    }
}
