package me.splitque.presencium;

import me.splitque.common.DiscordRPCHandler;
import me.splitque.common.SettingsHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;

public class Main implements ModInitializer {
    String state;
    Minecraft mc = Minecraft.getInstance();
    String path = String.valueOf(mc.gameDirectory.toPath().resolve("config"));

    @Override
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            SettingsHandler.load(path);
            DiscordRPCHandler.start();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.isSingleplayer()) {
                state = I18n.get("single_state");
            } else if (client.getCurrentServer() != null) {
                if (client.getCurrentServer().isRealm()) {
                    state = I18n.get("realm_state");
                }

                if (SettingsHandler.get("show_server_ip")) {
                    String address = client.getCurrentServer().ip;
                    state = I18n.get("multi_state") + ": " + address;
                } else if (!SettingsHandler.get("show_server_ip")) {
                    state = I18n.get("multi_state");
                }
            } else {
                state = I18n.get("main_state");
            }
            DiscordRPCHandler.update(state);
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            DiscordRPCHandler.stop();
        });
    }
}