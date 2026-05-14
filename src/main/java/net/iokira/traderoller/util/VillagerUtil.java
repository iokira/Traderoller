package net.iokira.traderoller.util;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class VillagerUtil {

    public static boolean isEmployed(Villager villager) {
        VillagerProfession profession = villager.getVillagerData().getProfession();
        return profession != VillagerProfession.NONE && profession != VillagerProfession.NITWIT;
    }

    public static boolean isUntraded(Villager villager) {
        return villager.getVillagerXp() == 0;
    }
}
