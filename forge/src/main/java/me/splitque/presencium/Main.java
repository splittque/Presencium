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
    String translatedState;
    String vanillaState;
    Boolean isInitialized = false;
    Minecraft mc = Minecraft.getInstance();
    String path = String.valueOf(mc.gameDirectory.toPath().resolve("config"));

    public Main(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::start);
    }
    private void start(FMLCommonSetupEvent e) {
        SettingsHandler.loadSettings(path);
        DiscordRPCHandler.startRPC();
        DiscordRPCHandler.startCheck();
        ScreenImplementation.registerSettingsScreen();
        isInitialized = true;
        stateUpdater();
    }

    private void stateUpdater() {
        Runnable updater = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isInitialized) {
                        if (mc.isSingleplayer()) {
                            translatedState = I18n.get("single_state");
                            vanillaState = "single_state";
                        } else if (mc.getCurrentServer() != null) {
                            if (mc.getCurrentServer().isRealm()) {
                                translatedState = I18n.get("realm_state");
                                vanillaState = "realm_state";
                            }

                            if (SettingsHandler.getOption("show_server_ip")) {
                                translatedState = I18n.get("multi_state") + ": " + mc.getCurrentServer().ip;
                                vanillaState = "multi_state";
                            } else if (!SettingsHandler.getOption("show_server_ip")) {
                                translatedState = I18n.get("multi_state");
                                vanillaState = "multi_state";
                            }
                        } else {
                            translatedState  = I18n.get("main_state");
                            vanillaState = "main_state";
                        }
                        DiscordRPCHandler.updateState(translatedState, vanillaState);
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {}
                }
            }
        };
        Thread thread = new Thread(updater);
        thread.setName("Presencium-RPC-State-Updater");
        thread.start();
    }
}
