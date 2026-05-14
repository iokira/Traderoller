package net.iokira.traderoller.server;

import net.iokira.traderoller.util.VillagerUtil;
import net.minecraft.world.entity.npc.Villager;

public class VillagerRerollHandler {

    public static void reroll(Villager villager) {
        if (!VillagerUtil.isEmployed(villager)) return;
        if (!VillagerUtil.isUntraded(villager)) return;
        villager.getOffers().clear();
        villager.updateTrades();
        villager.restock(); // resets lastRestockGameTime + notifies client
    }
}
