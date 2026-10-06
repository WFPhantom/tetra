package se.mickelus.tetra.effect.lunge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.AbstractPacket;
import se.mickelus.tetra.TetraMod;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class LungeEchoPacket extends AbstractPacket {
    public static final CustomPacketPayload.Type<LungeEchoPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "lunge_echo"));

    boolean isVertical;

    public LungeEchoPacket() {
    }

    public LungeEchoPacket(boolean isVertical) {
        this.isVertical = isVertical;
    }

    @Override
    public Type<LungeEchoPacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(isVertical);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        isVertical = buffer.readBoolean();
    }

    @Override
    public void handle(Player player) {
        LungeEffect.receiveEchoPacket(player, isVertical);
    }
}
