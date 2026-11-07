package net.mcreator.survivalreimagined.procedures;


import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.mcreator.survivalreimagined.init.SurvivalReimaginedModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DisableNetherPortal {
    public static void register() {
        UseBlockCallback.EVENT.register((Player player, Level world, InteractionHand hand, BlockHitResult hitResult) -> {
            if (!world.isClientSide()) {
                BlockPos clickedPos = hitResult.getBlockPos();
                BlockState placedAgainst = world.getBlockState(clickedPos);
                ItemStack heldItem = player.getItemInHand(hand);

                if (!SurvivalReimaginedModConfig.VALUES.vanilla_portal) {
                    if (heldItem.is(TagKey.create(Registries.ITEM, ResourceLocation.parse("minecraft:netherportal_items")))) {
                        if (placedAgainst.getBlock() == Blocks.OBSIDIAN) {
                            BlockPos firePos = clickedPos.relative(hitResult.getDirection());
                            execute(world, firePos.getX(), firePos.getY(), firePos.getZ(), player);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
            return InteractionResult.PASS;
        });
    }

    private static void execute(Level world, double x, double y, double z, Player player) {
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT, (x + 0.5), (y + 0.5), (z + 0.5), 40, 0.1, 0.1, 0.1, 0.1);

            if (player instanceof ServerPlayer serverPlayer) {
                Component message = Component.literal("Something happened, but didn't have enough energy to initiate...").withStyle(style -> style.withColor(ChatFormatting.BLUE));
                serverPlayer.displayClientMessage(message, true);
            }
            serverLevel.playSound(null, BlockPos.containing(x,y,z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.trident.thunder")), SoundSource.BLOCKS, 1, 1);
        }
    }
}
