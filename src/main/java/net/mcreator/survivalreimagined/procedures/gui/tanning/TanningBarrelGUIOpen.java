package net.mcreator.survivalreimagined.procedures.gui.tanning;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.mcreator.survivalreimagined.world.inventory.TanningBarrelGUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class TanningBarrelGUIOpen {
    public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState state, Entity entity) {
        if (entity == null) return InteractionResult.PASS;

        if (getPropertyByName(state, "opened") instanceof BooleanProperty boolProp && state.getValue(boolProp)) {
            if (entity instanceof ServerPlayer serverPlayer) {
                BlockPos pos = BlockPos.containing(x,y,z);

                serverPlayer.openMenu(new ExtendedScreenHandlerFactory<BlockPos>() {
                    @Override
                    public BlockPos getScreenOpeningData(ServerPlayer player) {
                        return pos;
                    }

                    @Override
                    public Component getDisplayName() {
                        return Component.literal("TanningBarrelGUI");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        return new TanningBarrelGUIMenu(id, inventory, pos);
                    }
                });
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private static Property<?> getPropertyByName(BlockState state, String name) {
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }
}
