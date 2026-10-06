package se.mickelus.tetra.effect;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import se.mickelus.mutil.network.AbstractPacket;
import se.mickelus.tetra.TetraMod;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TruesweepPacket extends AbstractPacket {
    public static final CustomPacketPayload.Type<TruesweepPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TetraMod.MOD_ID, "truesweep"));

    public TruesweepPacket() {
    }

    @Override
    public Type<TruesweepPacket> type() {
        return TYPE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
    }

    @Override
    public void fromBytes(FriendlyByteBuf buffer) {
    }

    @Override
    public void handle(Player player) {
        ItemStack itemStack = player.getMainHandItem();
        if (player.getAttackStrengthScale(0.5f) > 0.9f && EffectHelper.getEffectLevel(itemStack, ItemEffect.truesweep) > 0) {
            boolean hasSweepingStrike = EffectHelper.getEffectLevel(itemStack, ItemEffect.sweepingStrike) > 0;
            if (player.onGround() && !player.isSprinting()) {
                SweepingEffect.truesweep(itemStack, player, !hasSweepingStrike);
            }

            if (hasSweepingStrike) {
                SweepingStrikeEffect.causeTruesweepEffect(player, itemStack);
            }
        }
    }
}
