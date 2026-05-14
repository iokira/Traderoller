package net.iokira.traderoller.util;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.trading.MerchantOffer;

public class VillagerUtil {

    public static boolean isEmployed(Villager villager) {
        VillagerProfession profession = villager.getVillagerData().getProfession();
        return profession != VillagerProfession.NONE && profession != VillagerProfession.NITWIT;
    }

    public static boolean isUntraded(Villager villager) {
        for (MerchantOffer offer : villager.getOffers()) {
            if (offer.getUses() > 0) return false;
        }
        return true;
    }
}
