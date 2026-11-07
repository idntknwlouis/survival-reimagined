package net.mcreator.survivalreimagined.procedures;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LeavesDropCondition {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            execute(world, pos, player, state);
        });
    }

    public static void execute(Level world, BlockPos pos, Player player, BlockState state) {
        if (world.isClientSide()) return;
        if (!(world instanceof ServerLevel serverLevel)) return;

        ItemStack itemType = ItemStack.EMPTY;
        ItemStack itemBlockType = ItemStack.EMPTY;

        if (state.is(SurvivalReimaginedModBlocks.APPLE_OAK_LEAVES.get())) {
            itemType = new ItemStack(SurvivalReimaginedModItems.APPLE_OAK_SAPLING.get());
            itemBlockType = new ItemStack(Blocks.OAK_LEAVES);
        }

        else if (state.is(SurvivalReimaginedModBlocks.MANDARIN_LEAVES.get())
                || state.is(SurvivalReimaginedModBlocks.FRUITING_MANDARIN_LEAVES.get())) {
            itemType = new ItemStack(SurvivalReimaginedModBlocks.MANDARIN_SAPLING.get());
            itemBlockType = new ItemStack(SurvivalReimaginedModBlocks.MANDARIN_LEAVES.get());
        }

        else if (state.is(SurvivalReimaginedModBlocks.RED_CHERRY_LEAVES.get())
                || state.is(SurvivalReimaginedModBlocks.FLOWERING_RED_CHERRY_LEAVES.get())) {
            itemType = new ItemStack(SurvivalReimaginedModBlocks.RED_CHERRY_SAPLING.get());
            itemBlockType = new ItemStack(SurvivalReimaginedModBlocks.RED_CHERRY_LEAVES.get());
        }

        if (itemType.isEmpty() || itemBlockType.isEmpty()) return;

        ItemStack mainHand = player.getMainHandItem();
        int silkTouch = mainHand.getEnchantments().getLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH));
        int fortune = mainHand.getEnchantments().getLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE));

        if (mainHand.is(Items.SHEARS) || silkTouch > 0) {
            spawnDrop(serverLevel, pos, itemBlockType);
            return;
        }

        if (Math.random() < 0.2) {
            spawnDrop(serverLevel, pos, itemType);
            return;
        }

        double chance = switch (fortune) {
            case 1 -> 0.35;
            case 2 -> 0.45;
            case 3 -> 0.55;
            default -> 0.0;
        };

        if (fortune > 0 && Math.random() < chance) {
            spawnDrop(serverLevel, pos, itemType);
        }
    }

    private static void spawnDrop(ServerLevel world, BlockPos pos, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack.copy());
        itemEntity.setPickUpDelay(10);
        world.addFreshEntity(itemEntity);
    }
}
