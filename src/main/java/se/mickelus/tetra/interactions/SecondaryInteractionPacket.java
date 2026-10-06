package se.mickelus.tetra.interactions;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.BlockPosPacket;
import se.mickelus.tetra.TetraMod;

import java.util.Optional;

public class SecondaryInteractionPacket extends BlockPosPacket {
    public static final CustomPacketPayload.Type<SecondaryInteractionPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "secondary_interaction"));

    private String key;
    private int targetId = -1;

    public SecondaryInteractionPacket() {
    }

    public SecondaryInteractionPacket(String key, BlockPos pos, Entity target) {
        super(pos);
        this.key = key;
        targetId = Optional.ofNullable(target)
                .map(Entity::getId)
                .orElse(-1);
    }

    @Override
    public Type<SecondaryInteractionPacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        super.toBytes(buffer);
        buffer.writeUtf(key);
        buffer.writeInt(targetId);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        super.fromBytes(buffer);
        this.key = buffer.readUtf();
        targetId = buffer.readInt();
    }

    @Override
    public void handle(Player player) {
        Entity target = Optional.of(targetId)
                .filter(id -> id != -1)
                .map(id -> player.level().getEntity(id))
                .orElse(null);

        SecondaryInteraction interaction = SecondaryInteractionHandler.getInteraction(key);
        if (interaction != null) {
            interaction.perform(player, player.level(), pos, target);
        }
    }
}
