package me.splitque.presencium;

import me.splitque.presencium.config.config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class main implements ModInitializer {
    String state = "";

    @Override
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            discord.start();
            config.load();
            translate.setlang();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.isInSingleplayer()) {
                state = translate.single_state;
            } else if (client.getCurrentServerEntry() != null) {
                if (config.get("server_ip")) {
                    String address = client.getCurrentServerEntry().address;
                    state = translate.multi_state + ": " + address;
                } else {
                    state = translate.multi_state;
                }
                if (client.getCurrentServerEntry().isRealm()) {
                    state = translate.realm_state;
                }
            } else {
                state = translate.main_state;
            }
            discord.update(state);
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            discord.stop();
            config.save();
        });
    }
}