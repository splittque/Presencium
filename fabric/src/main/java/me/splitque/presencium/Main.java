package me.splitque.presencium;

import me.splitque.common.DiscordRPCHandler;
import me.splitque.common.SettingsHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;

public class Main implements ModInitializer {
    private String translatedState;
    private String vanillaState;
    private final Minecraft mc = Minecraft.getInstance();
    private final String path = String.valueOf(mc.gameDirectory.toPath().resolve("config"));

    @Override
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            SettingsHandler.loadSettings(path);
            DiscordRPCHandler.startRPC();
            DiscordRPCHandler.startCheck();
            startStateUpdater();
        });
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> DiscordRPCHandler.close());
    }

    private void startStateUpdater() {
        Runnable updater = () -> {
            while (true) {
                translatedState  = I18n.get("main_state");
                vanillaState = "main_state";

                if (mc.hasSingleplayerServer()) {
                    translatedState = I18n.get("single_state");
                    vanillaState = "single_state";
                }
                if (mc.getCurrentServer() != null) {
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
                }

                DiscordRPCHandler.updateState(translatedState, vanillaState);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
            }
        };
        Thread thread = new Thread(updater);
        thread.setName("Presencium-RPC-State-Updater");
        thread.start();
    }

    private void testStateUpdater() {
        Runnable updater = () -> {
            int i = 0;
            while (true) {
                if (i == 0) {
                    translatedState  = I18n.get("main_state");
                    vanillaState = "main_state";
                }
                if (i == 1) {
                    translatedState = I18n.get("single_state");
                    vanillaState = "single_state";
                }
                if (i == 2) {
                    translatedState = I18n.get("realm_state");
                    vanillaState = "realm_state";
                }
                if (i == 3) {
                    translatedState = I18n.get("multi_state");
                    vanillaState = "multi_state";
                }

                if (i == 3) i = 0;
                else i++;

                DiscordRPCHandler.updateState(translatedState, vanillaState);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {}
            }
        };
        Thread thread = new Thread(updater);
        thread.setName("Presencium-RPC-State-Updater");
        thread.start();
    }
}