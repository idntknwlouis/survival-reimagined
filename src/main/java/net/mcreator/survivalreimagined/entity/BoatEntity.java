package net.mcreator.survivalreimagined.entity;

import net.mcreator.survivalreimagined.init.SurvivalReimaginedModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class BoatEntity extends Boat {
    public enum CustomType {
        WISTERIA, RADIATED, MANDARIN
    }

    private final CustomType customType;

    public BoatEntity(EntityType<? extends Boat> type, Level level, CustomType customType) {
        super(type, level);
        this.customType = customType;
    }

    @Override
    public Item getDropItem() {
        return switch (this.customType) {
            case WISTERIA -> SurvivalReimaginedModItems.WISTERIA_BOAT;
            case RADIATED -> SurvivalReimaginedModItems.RADIATED_BOAT;
            case MANDARIN -> SurvivalReimaginedModItems.MANDARIN_BOAT;
        };
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
        return super.getAddEntityPacket(serverEntity);
    }

    public CustomType getCustomType() {
        return this.customType;
    }
}
