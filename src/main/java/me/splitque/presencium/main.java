package me.splitque.presencium;

import me.splitque.presencium.config.config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class main implements ModInitializer {
    Text translated = Text.of("");
    Text state = Text.of("");

    @Override
    public void onInitialize() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            discord.start();
            config.load();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.isInSingleplayer()) {
                state = Text.translatable("single_state");
                translated = Text.of("");
            } else if (client.getCurrentServerEntry() != null) {
                if (client.getCurrentServerEntry().isRealm()) {
                    state = Text.translatable("realm_state");
                    translated = Text.of("");
                }

                if (config.get("server_ip")) {
                    String address = client.getCurrentServerEntry().address;
                    translated = Text.translatable("multi_state");
                    state = Text.of(": " + address);
                } else if (!config.get("server_ip")) {
                    state = Text.translatable("multi_state");
                    translated = Text.of("");
                }
            } else {
                state = Text.translatable("main_state");
                translated = Text.of("");
            }
            discord.update(translated.getString() + state.getString());
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            discord.stop();
            config.save();
        });
    }
}