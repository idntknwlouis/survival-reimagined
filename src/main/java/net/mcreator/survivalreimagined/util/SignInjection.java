package net.mcreator.survivalreimagined.util;

import net.mcreator.survivalreimagined.SurvivalReimaginedMod;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public final class SignInjection {
    @SuppressWarnings("unchecked")
    public static void InjectAllSigns() {
        try {
            Field validBlockField = BlockEntityType.class.getDeclaredField("validBlocks");
            validBlockField.setAccessible(true);

            Set<Block> vanillaHangingBlocks = (Set<Block>) validBlockField.get(BlockEntityType.HANGING_SIGN);
            Set<Block> mutableHangingBlocks = new HashSet<>(vanillaHangingBlocks);
            mutableHangingBlocks.add(SurvivalReimaginedModBlocks.MANDARIN_HANGING_SIGN.get());
            mutableHangingBlocks.add(SurvivalReimaginedModBlocks.MANDARIN_CEILING_HANGING_SIGN.get());
            mutableHangingBlocks.add(SurvivalReimaginedModBlocks.RADIATED_HANGING_SIGN.get());
            mutableHangingBlocks.add(SurvivalReimaginedModBlocks.RADIATED_CEILING_HANGING_SIGN.get());
            mutableHangingBlocks.add(SurvivalReimaginedModBlocks.WISTERIA_HANGING_SIGN.get());
            mutableHangingBlocks.add(SurvivalReimaginedModBlocks.WISTERIA_CEILING_HANGING_SIGN.get());
            validBlockField.set(BlockEntityType.HANGING_SIGN, mutableHangingBlocks);

            Set<Block> vanillaStandardBlocks = (Set<Block>) validBlockField.get(BlockEntityType.SIGN);
            Set<Block> mutableStandardBlocks = new HashSet<>(vanillaStandardBlocks);
            mutableStandardBlocks.add(SurvivalReimaginedModBlocks.MANDARIN_SIGN.get());
            mutableStandardBlocks.add(SurvivalReimaginedModBlocks.MANDARIN_WALL_SIGN.get());
            mutableStandardBlocks.add(SurvivalReimaginedModBlocks.RADIATED_SIGN.get());
            mutableStandardBlocks.add(SurvivalReimaginedModBlocks.RADIATED_WALL_SIGN.get());
            mutableStandardBlocks.add(SurvivalReimaginedModBlocks.WISTERIA_SIGN.get());
            mutableStandardBlocks.add(SurvivalReimaginedModBlocks.WISTERIA_WALL_SIGN.get());
            validBlockField.set(BlockEntityType.SIGN, mutableStandardBlocks);


            SurvivalReimaginedMod.LOGGER.info("Registered Mod Signs");
        } catch (Exception e) {
            SurvivalReimaginedMod.LOGGER.info("Sign Registration Failed");
        }
    }
}
