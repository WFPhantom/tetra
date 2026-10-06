package se.mickelus.tetra.blocks.multischematic;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.AbstractPacket;
import se.mickelus.tetra.TetraMod;

public class MultiblockSchematicScrollPacket extends AbstractPacket {
    public static final CustomPacketPayload.Type<MultiblockSchematicScrollPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "multiblock_schematic_scroll"));
    boolean isIncrease;

    public MultiblockSchematicScrollPacket() {
    }

    public MultiblockSchematicScrollPacket(boolean isIncrease) {
        this.isIncrease = isIncrease;
    }

    @Override
    public Type<MultiblockSchematicScrollPacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(isIncrease);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        isIncrease = buffer.readBoolean();
    }

    @Override
    public void handle(Player player) {
        MultiblockSchematicScrollHandler.shiftSchematic(player, isIncrease);
    }
}
