package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.logging.Log;

public class StrippedLogs {
    private record LogData(Block stripped, Item bark) {}
    private record TaggedLog(String barkedLog, String strippedLog, Item bark) {}

//    REGIONS UNEXPLORED COMPAT
//    private static final TaggedLog[] RU_LOGS = {
//            new TaggedLog("regions_unexplored:barked/pine_logs", "regions_unexplored:stripped/pine_logs", SurvivalReimaginedModItems.PINE_BARK_RU.get()),
//            new TaggedLog("regions_unexplored:barked/willow_logs", "regions_unexplored:stripped/willow_logs", SurvivalReimaginedModItems.WILLOW_BARK_RU.get()),
//            new TaggedLog("regions_unexplored:barked/redwood_logs", "regions_unexplored:stripped/redwood_logs", SurvivalReimaginedModItems.REDWOOD_BARK_RU.get()),
//            new TaggedLog("regions_unexplored:barked/magnolia_logs", "regions_unexplored:stripped/magnolia_logs", SurvivalReimaginedModItems.MAGNOILA_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/kapok_logs", "regions_unexplored:stripped/kapok_logs", SurvivalReimaginedModItems.KAPOK_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/larch_logs", "regions_unexplored:stripped/larch_logs", SurvivalReimaginedModItems.LARCH_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/eucalyptus_logs", "regions_unexplored:stripped/eucalyptus_logs", SurvivalReimaginedModItems.EUCALYPTUS_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/cypress_logs", "regions_unexplored:stripped/cypress_logs", SurvivalReimaginedModItems.CYPRESS_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/socotra_logs", "regions_unexplored:stripped/socotra_logs", SurvivalReimaginedModItems.SOCOTRA_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/mauve_logs", "regions_unexplored:stripped/mauve_logs", SurvivalReimaginedModItems.MAUVE_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/dead_logs", "regions_unexplored:stripped/dead_logs", SurvivalReimaginedModItems.DEAD_BARK_RU.get()),
//            new TaggedLog("regions_unexplored:barked/cobalt_logs", "regions_unexplored:stripped/cobalt_logs", SurvivalReimaginedModItems.COBALT_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/brimwood_logs", "regions_unexplored:stripped/brimwood_logs", SurvivalReimaginedModItems.BRIMWOOD_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/magma_brimwood_logs", "regions_unexplored:stripped/brimwood_logs", SurvivalReimaginedModItems.BRIMWOOD_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/baobab_logs", "regions_unexplored:stripped/baobab_logs", SurvivalReimaginedModItems.BAOBAB_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/silver_birch_logs", "regions_unexplored:stripped/birch_logs", SurvivalReimaginedModItems.SILVER_BIRCH_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/joshua_logs", "regions_unexplored:stripped/joshua_logs", SurvivalReimaginedModItems.JOSHUA_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/blackwood_logs", "regions_unexplored:stripped/blackwood_logs", SurvivalReimaginedModItems.BLACKWOOD_BARK.get()),
//            new TaggedLog("regions_unexplored:barked/palm_logs", "regions_unexplored:stripped/palm_logs", SurvivalReimaginedModItems.PALM_BARK_RU.get())
//    };

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClientSide()) return InteractionResult.PASS;
            if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
            if (!(player instanceof LivingEntity living)) return InteractionResult.PASS;

            ItemStack main = living.getMainHandItem();
            ItemStack off = living.getOffhandItem();

            TagKey<Item> SAW = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:tools/saw"));

            if (!main.is(SAW) && !off.is(SAW)) {
                return InteractionResult.PASS;
            }

            BlockPos pos = hitResult.getBlockPos();
            BlockState original = world.getBlockState(pos);

            LogData data = getVannillaLogData(original);
            if (data == null) { data = getCustomLogData(original); }
            if (data == null) { data = getRegionsUnexploredLogData(original, world.getRandom()); }
            if (data == null) return InteractionResult.PASS;

            ItemEntity bark = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(data.bark()));
            bark.setPickUpDelay(10);
            world.addFreshEntity(bark);

            BlockState stripped = data.stripped().defaultBlockState();

            for (var property : original.getProperties()) {
                var newProperty = stripped.getBlock().getStateDefinition().getProperty(property.getName());

                if (newProperty != null) {
                    try {
                        stripped = copyProperty(original, stripped, property, newProperty);
                    } catch (Exception ignored) {}
                }
            }
            world.setBlock(pos, stripped, 3);
            world.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);

            if (main.is(SAW)) {
                living.swing(InteractionHand.MAIN_HAND, true);
            } else {
                living.swing(InteractionHand.OFF_HAND, true);
            }
            return InteractionResult.SUCCESS;
        });
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static BlockState copyProperty(BlockState orignal, BlockState stripped, Property oldProperty, Property newProperty) {
        return stripped.setValue(newProperty, orignal.getValue(oldProperty));
    }

    private static LogData getVannillaLogData(BlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.OAK_LOG) { return new LogData(Blocks.STRIPPED_OAK_LOG, SurvivalReimaginedModItems.OAK_BARK.asItem()); }
        if (block == Blocks.BIRCH_LOG) { return new LogData(Blocks.STRIPPED_BIRCH_LOG, SurvivalReimaginedModItems.BIRCH_BARK.asItem()); }
        if (block == Blocks.SPRUCE_LOG) { return new LogData(Blocks.STRIPPED_SPRUCE_LOG, SurvivalReimaginedModItems.SPRUCE_BARK.asItem()); }
        if (block == Blocks.DARK_OAK_LOG) { return new LogData(Blocks.STRIPPED_DARK_OAK_LOG, SurvivalReimaginedModItems.DARK_OAK_BARK.asItem()); }
        if (block == Blocks.ACACIA_LOG) { return new LogData(Blocks.STRIPPED_ACACIA_LOG, SurvivalReimaginedModItems.ACACIA_BARK.asItem()); }
        if (block == Blocks.JUNGLE_LOG) { return new LogData(Blocks.STRIPPED_JUNGLE_LOG, SurvivalReimaginedModItems.JUNGLE_BARK.asItem()); }
        if (block == Blocks.MANGROVE_LOG) { return new LogData(Blocks.STRIPPED_MANGROVE_LOG, SurvivalReimaginedModItems.MANGROVE_BARK.asItem()); }
        if (block == Blocks.CHERRY_LOG) { return new LogData(Blocks.STRIPPED_CHERRY_LOG, SurvivalReimaginedModItems.CHERRY_BARK.asItem()); }
        if (block == Blocks.CRIMSON_STEM) { return new LogData(Blocks.STRIPPED_CRIMSON_STEM, SurvivalReimaginedModItems.CRIMSON_BARK.asItem()); }
        if (block == Blocks.WARPED_STEM) { return new LogData(Blocks.STRIPPED_WARPED_STEM, SurvivalReimaginedModItems.WARPED_BARK.asItem()); }
        return null;
    }

    private static LogData getCustomLogData(BlockState state) {
        Block block = state.getBlock();

        if (block == SurvivalReimaginedModBlocks.RADIANT_LOG.get()) return new LogData(SurvivalReimaginedModBlocks.STRIPPED_RADIANT_LOG.get(), SurvivalReimaginedModItems.RADIATED_BARK.asItem());
        if (block == SurvivalReimaginedModBlocks.WISTERIA_LOG.get()) return new LogData(SurvivalReimaginedModBlocks.STRIPPED_WISTERIA_LOG.get(), SurvivalReimaginedModItems.WISTERIA_BARK.asItem());
        if (block == SurvivalReimaginedModBlocks.MANDARIN_LOG.get()) return new LogData(SurvivalReimaginedModBlocks.STRIPPED_MANDARIN_LOG.get(), SurvivalReimaginedModItems.MANDARIN_BARK.asItem());
        return null;
    }

    private static LogData getRegionsUnexploredLogData(BlockState state, RandomSource random) {
//        for (TaggedLog entry : RU_LOGS) {
//            TagKey<Block> barkedTag = TagKey.create(Registries.BLOCK, ResourceLocation.parse(entry.barkedLog));
//            if (!state.is(barkedTag)) continue;
//
//            TagKey<Block> strippedTag = TagKey.create(Registries.BLOCK, ResourceLocation.parse(entry.strippedLog));
//            var strippedBlock = BuiltInRegistries.BLOCK.getTag(strippedTag).flatMap(tag -> tag.getRandomElement(random)).map(holder -> holder.value()).orElse(null);
//            if (strippedBlock == null) return null;
//            return new LogData(strippedBlock, entry.bark());
//        }
        return null;
    }
}
