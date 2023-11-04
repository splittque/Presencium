package me.splitque.presencium.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.SimpleOptionsScreen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import me.splitque.presencium.*;

public class screen extends SimpleOptionsScreen {
    private static final SimpleOption<Boolean> server_ip = SimpleOption.ofBoolean(
            translate.server_ip,
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
                new SimpleOption[]{server_ip}
        );
    }
}
