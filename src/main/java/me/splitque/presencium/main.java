package me.splitque.presencium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class main implements ModInitializer {
    String state = "";

    @Override
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            discord.start();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            states.setstates();

            if (client.isInSingleplayer()) {
                state = states.single_state;
            } else if (client.getCurrentServerEntry() != null) {
                String address = client.getCurrentServerEntry().address;
                state = states.multi_state + address;
                if (client.getCurrentServerEntry().isRealm()) {
                    state = states.realm_state;
                }
            } else {
                state = states.main_state;
            }
            discord.update(state);
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            discord.stop();
        });
    }
}