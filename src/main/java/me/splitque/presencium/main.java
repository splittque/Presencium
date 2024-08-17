package me.splitque.presencium;

import me.splitque.presencium.config.config;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class main implements ModInitializer {
    MinecraftClient mc = MinecraftClient.getInstance();
    Text state = Text.of("");

    @Override
    public void onInitialize() {
        discord.start();
        config.load();

        Runnable rpc_updater = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (mc.isInSingleplayer()) {
                        state = Text.translatable("single_state");
                        discord.update(state.getString());
                    } else if (mc.getCurrentServerEntry() != null) {
                        if (mc.getCurrentServerEntry().isRealm()) {
                            state = Text.translatable("realm_state");
                            discord.update(state.getString());
                        }

                        if (config.get("server_ip")) {
                            String address = mc.getCurrentServerEntry().address;
                            state = Text.translatable("multi_state");
                            discord.update(state.getString() + ": " + address);
                        } else if (!config.get("server_ip")) {
                            state = Text.translatable("multi_state");
                            discord.update(state.getString());
                        }
                    } else {
                        state = Text.translatable("main_state");
                        discord.update(state.getString());
                    }

                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(rpc_updater);
        thread.start();
    }
}