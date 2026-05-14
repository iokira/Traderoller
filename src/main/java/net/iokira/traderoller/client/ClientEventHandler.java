package net.iokira.traderoller.client;

import net.iokira.traderoller.TradeRollerMod;
import net.iokira.traderoller.network.RerollPayload;
import net.iokira.traderoller.util.VillagerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = TradeRollerMod.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

    private static int lastVillagerId = -1;
    private static AbstractWidget rerollButton = null;

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Villager villager) {
            lastVillagerId = villager.getId();
        }
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        rerollButton = null;
        if (!(event.getScreen() instanceof MerchantScreen screen)) return;
        if (lastVillagerId == -1) return;

        var level = Minecraft.getInstance().level;
        if (level == null) return;
        if (!(level.getEntity(lastVillagerId) instanceof Villager villager)) return;
        if (!VillagerUtil.isEmployed(villager)) return;

        var btn = getAbstractButton(screen);

        rerollButton = btn;
        event.addListener(btn);
    }

    @NotNull
    private static AbstractButton getAbstractButton(MerchantScreen screen) {
        int leftPos = (screen.width - 276) / 2;
        int topPos = (screen.height - 166) / 2;

        var btn = new AbstractButton(leftPos + 88, topPos + 4, 12, 12, Component.literal("R")) {
            @Override
            public void onPress() {
                PacketDistributor.sendToServer(new RerollPayload(lastVillagerId));
            }

            @Override
            public boolean isFocused() {
                return false;
            }

            @Override
            protected void updateWidgetNarration(NarrationElementOutput narration) {}
        };
        return btn;
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Pre event) {
        if (rerollButton == null) return;
        if (!(event.getScreen() instanceof MerchantScreen screen)) return;
        rerollButton.visible = screen.getMenu().getTraderXp() == 0;
    }
}
