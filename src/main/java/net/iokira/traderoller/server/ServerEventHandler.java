package net.iokira.traderoller.server;

import net.iokira.traderoller.network.RerollPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerEventHandler {

    public static void handle(RerollPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            ServerLevel level = player.serverLevel();

            Entity entity = level.getEntity(payload.villagerEntityId());
            if (!(entity instanceof Villager villager)) return;
            if (player.distanceToSqr(villager) > 16.0) return;

            VillagerRerollHandler.reroll(villager);
        });
    }
}
