package me.splitque.presencium;

import me.splitque.common.DiscordRPCHandler;
import me.splitque.common.SettingsHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;

public class Main implements ModInitializer {
    String translatedState;
    String vanillaState;
    Boolean isInitialized = false;
    Minecraft mc = Minecraft.getInstance();
    String path = String.valueOf(mc.gameDirectory.toPath().resolve("config"));

    @Override
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            SettingsHandler.loadSettings(path);
            DiscordRPCHandler.startCallbacks();
            DiscordRPCHandler.startRPC();
            isInitialized = true;
        });

        Runnable stateUpdater = new Runnable() {
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
        Thread thread = new Thread(stateUpdater);
        thread.setName("Presencium-RPC-State-Updater");
        thread.start();

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            DiscordRPCHandler.stopRPC();
        });
    }
}