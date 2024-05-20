package me.splitque.presencium;

import me.splitque.presencium.config.modmenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import me.splitque.presencium.config.config;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.GameShuttingDownEvent;
import net.neoforged.neoforge.event.TickEvent;

@Mod("presencium")
public class main {
    public main(IEventBus modEventBus) {
        modEventBus.addListener(this::start);
    }

    private void start(final FMLCommonSetupEvent event) {
        discord.start();
        config.load();
        modmenu.registerModsPage();
    }
    @Mod.EventBusSubscriber(modid = "presencium")
    public static class events {
        public static Minecraft mc = Minecraft.getInstance();
        public static String state = "";

        @SubscribeEvent
        public static void discordupdate(TickEvent.ClientTickEvent event) {
            // singleplayer state
            if (event.phase == TickEvent.Phase.END) {
                if (mc.isSingleplayer()) {
                    state = I18n.get("single_state");
                // realm state
                } else if (mc.getCurrentServer() != null) {
                    if (mc.getCurrentServer().isRealm()) {
                        state = I18n.get("realm_state");
                    }
                    // multiplayer state
                    if (config.get("server_ip")) {
                        String address = mc.getCurrentServer().ip;
                        state = I18n.get("multi_state") + ": " + address;
                    } else if (!config.get("server_ip")) {
                        state = I18n.get("multi_state");
                    }
                // main state
                } else {
                    state = I18n.get("main_state");
                }

                discord.update(state);
            }
        }

        @SubscribeEvent
        public static void stop(GameShuttingDownEvent event) {
            discord.stop();
            config.save();
        }
    }
}
