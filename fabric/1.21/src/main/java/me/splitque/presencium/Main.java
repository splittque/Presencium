package me.splitque.presencium;

import me.splitque.common.DiscordHandler;
import me.splitque.common.Settings;
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
            Settings.load(path);
            DiscordHandler.start();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.isSingleplayer()) {
                state = I18n.get("single_state");
            } else if (client.getCurrentServer() != null) {
                if (client.getCurrentServer().isRealm()) {
                    state = I18n.get("realm_state");
                }

                if (Settings.get("server_ip")) {
                    String address = client.getCurrentServer().ip;
                    state = I18n.get("multi_state") + ": " + address;
                } else if (!Settings.get("server_ip")) {
                    state = I18n.get("multi_state");
                }
            } else {
                state = I18n.get("main_state");
            }
            DiscordHandler.update(state);
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            DiscordHandler.stop();
        });
    }
}