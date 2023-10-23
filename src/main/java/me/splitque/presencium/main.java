package me.splitque.presencium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class main implements ModInitializer {
    String state = "";

    @Override
    public void onInitialize() {
        discord.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            states.setstates();

            if (client.isInSingleplayer()) {
                state = states.single_state;
            } else if (client.getCurrentServerEntry() != null) {
                String address = client.getCurrentServerEntry().address;
                state = states.multi_state + address;
            } else {
                state = states.main_state;
            }
            discord.update(state);
        });
    }
}