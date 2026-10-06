package se.mickelus.tetra.blocks.workbench.action;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.BlockPosPacket;
import se.mickelus.tetra.TetraMod;
import se.mickelus.tetra.blocks.workbench.WorkbenchTile;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class WorkbenchActionPacket extends BlockPosPacket {
    public static final CustomPacketPayload.Type<WorkbenchActionPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "workbench_action"));

    private String actionKey;

    public WorkbenchActionPacket() {
    }

    public WorkbenchActionPacket(BlockPos pos, String actionKey) {
        super(pos);
        this.actionKey = actionKey;
    }

    @Override
    public Type<WorkbenchActionPacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        super.toBytes(buffer);
        buffer.writeUtf(actionKey);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        super.fromBytes(buffer);
        actionKey = buffer.readUtf();
    }

    @Override
    public void handle(Player player) {
        if (player.level().getBlockEntity(pos) instanceof WorkbenchTile workbench) {
            workbench.performAction(player, actionKey);
        }
    }
}
