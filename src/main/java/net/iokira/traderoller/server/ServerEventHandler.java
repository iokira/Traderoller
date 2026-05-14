package net.iokira.traderoller.server;

import net.iokira.traderoller.network.RerollPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerEventHandler {

    public static void handle(RerollPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            ServerLevel level = player.serverLevel();

            Entity entity = level.getEntity(payload.villagerEntityId());
            if (!(entity instanceof Villager villager)) return;

            returnMerchantInputItems(player);
            VillagerRerollHandler.reroll(villager);
        });
    }

    private static void returnMerchantInputItems(ServerPlayer player) {
        if (!(player.containerMenu instanceof MerchantMenu merchantMenu)) return;
        int merchantSlotCount = merchantMenu.slots.size() - 36;
        for (int i = 0; i < merchantSlotCount; i++) {
            Slot slot = merchantMenu.getSlot(i);
            if (slot instanceof MerchantResultSlot) continue;
            ItemStack stack = slot.getItem().copy();
            if (stack.isEmpty()) continue;
            slot.set(ItemStack.EMPTY);
            if (!player.addItem(stack)) {
                player.drop(stack, false);
            }
        }
    }
}
