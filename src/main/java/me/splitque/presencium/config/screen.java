package me.splitque.presencium.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.*;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public class screen extends GameOptionsScreen {
    private final SimpleOption<Boolean> server_ip = SimpleOption.ofBoolean(
            "server_ip",
            config.get("server_ip"),
            value -> {
                config.set("server_ip", value);
            }
    );
    public final SimpleOption<Boolean> rpc_onoff = SimpleOption.ofBoolean(
            "rpc_onoff",
            config.get("rpc_onoff"),
            value -> {
                config.set("rpc_onoff", value);
            }
    );

    @Override
    protected void addOptions() {
        this.body.addAll(server_ip, rpc_onoff);
    }

    public screen(Screen screen) {
        super(
                screen,
                MinecraftClient.getInstance().options,
                Text.of("Presencium")
        );
    }
}