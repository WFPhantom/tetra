package se.mickelus.tetra.items.modular.impl.toolbelt.suspend;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.AbstractPacket;
import se.mickelus.tetra.TetraMod;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ToggleSuspendPacket extends AbstractPacket {
    public static final CustomPacketPayload.Type<ToggleSuspendPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "toggle_suspend"));

    boolean toggleOn;

    public ToggleSuspendPacket() {
    }

    public ToggleSuspendPacket(boolean toggleOn) {
        this.toggleOn = toggleOn;
    }

    @Override
    public Type<ToggleSuspendPacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(toggleOn);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        toggleOn = buffer.readBoolean();
    }

    @Override
    public void handle(Player player) {
        SuspendEffect.toggleSuspend(player, toggleOn);
    }
}
