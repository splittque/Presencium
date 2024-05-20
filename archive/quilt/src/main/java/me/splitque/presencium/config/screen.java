package me.splitque.presencium.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SimpleOptionsScreen;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;

public class screen extends SimpleOptionsScreen {
    private static final Option<Boolean> server_ip = Option.ofBoolean(
            "server_ip",
            config.get("server_ip"),
            value -> {
                config.set("server_ip", value);
                config.save();
            }
    );

    public screen(Screen parent) {
        super(
                parent,
                MinecraftClient.getInstance().options,
                Text.of("Presencium"),
                new Option[]{server_ip}
        );
    }
}
