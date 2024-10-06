package me.splitque.presencium;

import me.splitque.common.SettingsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.event.GameShuttingDownEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import me.splitque.common.DiscordRPCHandler;

@Mod("presencium")
public class Main {
    public static Minecraft mc = Minecraft.getInstance();
    String path = String.valueOf(mc.gameDirectory.toPath().resolve("config"));

    public Main(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::start);
    }
    private void start(FMLCommonSetupEvent e) {
        DiscordRPCHandler.start();
        SettingsHandler.load(path);
        ScreenImplementation.registerSettingsScreen();
    }

    @Mod.EventBusSubscriber(modid = "presencium")
    public static class Events {
        public static String state = "";

        @SubscribeEvent
        public static void rpcUpdater(TickEvent.ClientTickEvent e) {
            if (e.phase == TickEvent.Phase.END) {
                if (mc.isSingleplayer()) {
                    state = I18n.get("single_state");
                } else if (mc.getCurrentServer() != null) {
                    if (mc.getCurrentServer().isRealm()) {
                        state = I18n.get("realm_state");
                    }

                    if (SettingsHandler.get("server_ip")) {
                        String address = mc.getCurrentServer().ip;
                        state = I18n.get("multi_state") + ": " + address;
                    } else if (!SettingsHandler.get("server_ip")) {
                        state = I18n.get("multi_state");
                    }
                } else {
                    state = I18n.get("main_state");
                }

                DiscordRPCHandler.update(state);
            }
        }

        @SubscribeEvent
        public static void stop(GameShuttingDownEvent e) {
            DiscordRPCHandler.stop();
        }
    }
}
