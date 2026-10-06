package se.mickelus.tetra.items.modular.impl.toolbelt;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import se.mickelus.mutil.network.AbstractPacket;
import se.mickelus.tetra.TetraMod;
import se.mickelus.tetra.items.modular.impl.toolbelt.inventory.ToolbeltSlotType;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EquipToolbeltItemPacket extends AbstractPacket {
    public static final CustomPacketPayload.Type<EquipToolbeltItemPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "equip_toolbelt_item"));

    private ToolbeltSlotType slotType;
    private int toolbeltItemIndex;

    private InteractionHand hand;

    public EquipToolbeltItemPacket() {
    }

    public EquipToolbeltItemPacket(ToolbeltSlotType inventoryType, int toolbeltSlot, InteractionHand hand) {
        this.slotType = inventoryType;
        this.toolbeltItemIndex = toolbeltSlot;
        this.hand = hand;
    }

    @Override
    public Type<EquipToolbeltItemPacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(slotType.ordinal());
        buffer.writeInt(hand.ordinal());
        buffer.writeInt(toolbeltItemIndex);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
        int typeOrdinal = buffer.readInt();
        if (typeOrdinal < ToolbeltSlotType.values().length) {
            slotType = ToolbeltSlotType.values()[typeOrdinal];
        }

        int handOrdinal = buffer.readInt();
        if (handOrdinal < InteractionHand.values().length) {
            hand = InteractionHand.values()[handOrdinal];
        }

        toolbeltItemIndex = buffer.readInt();
    }

    @Override
    public void handle(Player player) {
        ToolbeltHelper.equipItemFromToolbelt(player, slotType, toolbeltItemIndex, hand);
    }
}
