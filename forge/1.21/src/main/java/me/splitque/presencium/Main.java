package me.splitque.presencium;

import me.splitque.common.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.event.GameShuttingDownEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import me.splitque.common.DiscordHandler;

@Mod("presencium")
public class Main {
    public static Minecraft mc = Minecraft.getInstance();
    String path = String.valueOf(mc.gameDirectory.toPath().resolve("config"));;

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::start);
    }
    private void start(FMLCommonSetupEvent e) {
        DiscordHandler.start();
        Settings.load(path);
        ScreenImplementation.registerModsPage();
    }

    @Mod.EventBusSubscriber(modid = "presencium")
    public static class events {
        public static String state = "";

        @SubscribeEvent
        public static void discordupdate(TickEvent.ClientTickEvent e) {
            if (e.phase == TickEvent.Phase.END) {
                if (mc.isSingleplayer()) {
                    state = I18n.get("single_state");
                } else if (mc.getCurrentServer() != null) {
                    if (mc.getCurrentServer().isRealm()) {
                        state = I18n.get("realm_state");
                    }

                    if (Settings.get("server_ip")) {
                        String address = mc.getCurrentServer().ip;
                        state = I18n.get("multi_state") + ": " + address;
                    } else if (!Settings.get("server_ip")) {
                        state = I18n.get("multi_state");
                    }
                } else {
                    state = I18n.get("main_state");
                }

                DiscordHandler.update(state);
            }
        }

        @SubscribeEvent
        public static void stop(GameShuttingDownEvent e) {
            DiscordHandler.stop();
        }
    }
}
