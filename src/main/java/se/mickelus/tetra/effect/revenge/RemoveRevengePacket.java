package se.mickelus.tetra.effect.revenge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.AbstractPacket;
import se.mickelus.tetra.TetraMod;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RemoveRevengePacket extends AbstractPacket {
    public static final CustomPacketPayload.Type<RemoveRevengePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "remove_revenge"));

    private int entityId = -1;

    public RemoveRevengePacket(Entity attacker) {
        this.entityId = attacker.getId();
    }

    public RemoveRevengePacket() {
    }

    @Override
    public Type<RemoveRevengePacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeVarInt(entityId);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        entityId = buffer.readVarInt();
    }

    @Override
    public void handle(Player player) {
        RevengeTracker.removeEnemy(player, entityId);
    }
}
