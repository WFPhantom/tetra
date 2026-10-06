package se.mickelus.tetra.blocks.workbench;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.BlockPosPacket;
import se.mickelus.tetra.TetraMod;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class WorkbenchPacketCraft extends BlockPosPacket {
    public static final CustomPacketPayload.Type<WorkbenchPacketCraft> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "workbench_craft"));

    public WorkbenchPacketCraft() {
    }

    public WorkbenchPacketCraft(BlockPos pos) {
        super(pos);
    }

    @Override
    public Type<WorkbenchPacketCraft> type() {
        return TYPE;
    }

    @Override
    public void handle(Player player) {
        if (player.level().getBlockEntity(pos) instanceof WorkbenchTile workbench) {
            workbench.craft(player);
        }
    }
}
