package net.iokira.traderoller.network;

import net.iokira.traderoller.server.ServerEventHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(NetworkHandler::onRegisterPayloads);
    }

    private static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                RerollPayload.TYPE,
                RerollPayload.STREAM_CODEC,
                ServerEventHandler::handle
        );
    }
}
