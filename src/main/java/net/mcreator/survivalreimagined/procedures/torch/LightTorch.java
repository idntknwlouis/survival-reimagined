package net.mcreator.survivalreimagined.procedures.torch;

import net.mcreator.survivalreimagined.block.CrudeTorch;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class LightTorch {
    private static final TagKey<Item> TORCH_LIGHTERS = TagKey.create(Registries.ITEM, ResourceLocation.parse("c:torch_lighters"));

    public static void execute(Level level, BlockPos pos, Player player) {
        if (level.isClientSide) return;

        ItemStack heldItem = player.getMainHandItem();

        if (!heldItem.is(TORCH_LIGHTERS)) return;

        BlockState state = level.getBlockState(pos);

        if (!state.is(SurvivalReimaginedModBlocks.CRUDE_TORCH.get())) return;
        if (state.getValue(CrudeTorch.LIT)) return;

        player.swing(InteractionHand.MAIN_HAND);
        level.setBlock(pos, state.setValue(CrudeTorch.LIT, true), 3);
        level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f,1.0f);

        if (!player.getAbilities().instabuild) {
            heldItem.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        }
    }

}
