package me.splitque.presencium;

import me.splitque.presencium.config.config;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;

public class main implements ModInitializer {
    Text state = Text.of("");

    @Override
    public void onInitialize(ModContainer mod) {
        ClientLifecycleEvents.READY.register(client -> {
            discord.start();
            config.load();
        });

        ClientTickEvents.END.register(client -> {
            // singleplayer state
            if (client.isInSingleplayer()) {
                state = Text.translatable("single_state");
                discord.update(state.getString());
            // realm state
            } else if (client.getCurrentServerEntry() != null) {
                if (client.getCurrentServerEntry().isRealm()) {
                    state = Text.translatable("realm_state");
                    discord.update(state.getString());
                }
                // multiplayer state
                if (config.get("server_ip")) {
                    String address = client.getCurrentServerEntry().address;
                    state = Text.translatable("multi_state");
                    discord.update(state.getString() + ": " + address);
                } else if (!config.get("server_ip")) {
                    state = Text.translatable("multi_state");
                    discord.update(state.getString());
                }
            // main state
            } else {
                state = Text.translatable("main_state");
                discord.update(state.getString());
            }
        });

        ClientLifecycleEvents.STOPPING.register(client -> {
            discord.stop();
            config.save();
        });
    }
}
