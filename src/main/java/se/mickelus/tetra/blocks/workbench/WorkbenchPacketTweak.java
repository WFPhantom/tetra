package se.mickelus.tetra.blocks.workbench;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.BlockPosPacket;
import se.mickelus.mutil.util.CastOptional;
import se.mickelus.tetra.TetraMod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class WorkbenchPacketTweak extends BlockPosPacket {
    public static final CustomPacketPayload.Type<WorkbenchPacketTweak> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "workbench_tweak"));

    String slot;
    Map<String, Integer> tweaks;

    public WorkbenchPacketTweak() {
        tweaks = new HashMap<>();
    }

    public WorkbenchPacketTweak(BlockPos pos, String slot, Map<String, Integer> tweaks) {
        super(pos);

        this.slot = slot;
        this.tweaks = tweaks;
    }

    @Override
    public Type<WorkbenchPacketTweak> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        super.toBytes(buffer);

        buffer.writeUtf(slot);
        buffer.writeInt(tweaks.size());
        tweaks.forEach((tweakKey, step) -> {
            buffer.writeUtf(tweakKey);
            buffer.writeInt(step);
        });
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        super.fromBytes(buffer);

        slot = buffer.readUtf();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            tweaks.put(buffer.readUtf(), buffer.readInt());
        }
    }

    @Override
    public void handle(Player player) {
        CastOptional.cast(player.level().getBlockEntity(pos), WorkbenchTile.class)
                .ifPresent(workbench -> workbench.tweak(player, slot, tweaks));
    }
}
