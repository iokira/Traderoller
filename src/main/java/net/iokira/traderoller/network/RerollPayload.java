package net.iokira.traderoller.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record RerollPayload(int villagerEntityId) implements CustomPacketPayload {

    public static final Type<RerollPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("traderoller", "reroll"));

    public static final StreamCodec<FriendlyByteBuf, RerollPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, RerollPayload::villagerEntityId,
                    RerollPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
