package se.mickelus.tetra.blocks.forged.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.AbstractPacket;
import se.mickelus.mutil.util.CastOptional;
import se.mickelus.tetra.TetraMod;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ChangeCompartmentPacket extends AbstractPacket {
    public static final CustomPacketPayload.Type<ChangeCompartmentPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "change_compartment"));

    private int compartmentIndex;

    public ChangeCompartmentPacket() {
    }

    public ChangeCompartmentPacket(int compartmentIndex) {
        this.compartmentIndex = compartmentIndex;
    }

    @Override
    public Type<ChangeCompartmentPacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(compartmentIndex);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        compartmentIndex = buffer.readInt();
    }

    @Override
    public void handle(Player player) {
        CastOptional.cast(player.containerMenu, ForgedContainerMenu.class)
                .ifPresent(container -> container.changeCompartment(compartmentIndex));
    }
}
