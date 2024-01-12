package me.splitque.presencium;

import me.splitque.presencium.config.config;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class main implements ModInitializer {
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
                discord.update(state.getString());
            } else if (client.getCurrentServerEntry() != null) {
                if (client.getCurrentServerEntry().isRealm()) {
                    state = Text.translatable("realm_state");
                    discord.update(state.getString());
                }

                if (config.get()) {
                    String address = client.getCurrentServerEntry().address;
                    state = Text.translatable("multi_state");
                    discord.update(state.getString() + ": " + address);
                } else if (!config.get()) {
                    state = Text.translatable("multi_state");
                    discord.update(state.getString());
                }
            } else {
                state = Text.translatable("main_state");
                discord.update(state.getString());
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            discord.stop();
            config.save();
        });
    }
}