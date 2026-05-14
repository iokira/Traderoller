package net.iokira.traderoller;

import net.iokira.traderoller.network.NetworkHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TradeRollerMod.MOD_ID)
public class TradeRollerMod {
    public static final String MOD_ID = "traderoller";

    public TradeRollerMod(IEventBus modEventBus) {
        NetworkHandler.register(modEventBus);
    }
}
