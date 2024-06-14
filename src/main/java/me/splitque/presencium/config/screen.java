package me.splitque.presencium.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SimpleOptionsScreen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

public class screen extends SimpleOptionsScreen {
    private static final SimpleOption<Boolean> server_ip = SimpleOption.ofBoolean(
            "server_ip",
            config.get("server_ip"),
            value -> {
                config.set("server_ip", value);
                config.save();
            }
    );
    private static final SimpleOption<Boolean> rpc_onoff = SimpleOption.ofBoolean(
            "rpc_onoff",
            config.get("rpc_onoff"),
            value -> {
                config.set("rpc_onoff", value);
                config.save();
            }
    );

    public screen(Screen parent) {
        super(
                parent,
                MinecraftClient.getInstance().options,
                Text.of("Presencium"),
                new SimpleOption[]{server_ip, rpc_onoff}
        );
    }
}
