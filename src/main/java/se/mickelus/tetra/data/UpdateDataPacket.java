package se.mickelus.tetra.data;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.data.AbstractUpdateDataPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import se.mickelus.tetra.TetraMod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@ParametersAreNonnullByDefault
public class UpdateDataPacket extends AbstractUpdateDataPacket {
    public static final CustomPacketPayload.Type<UpdateDataPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "update_data"));

    public UpdateDataPacket() {
    }

    public UpdateDataPacket(String directory, Map<ResourceLocation, JsonElement> data) {
        super(directory, data);
    }

    @Override
    public Type<UpdateDataPacket> type() {
        return TYPE;
    }

    @Override
    public void handle(Player player) {
        DataManager.instance.onDataRecieved(directory, data);
    }
}
