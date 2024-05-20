package me.splitque.presencium.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.network.chat.Component;

public class screen extends SimpleOptionsSubScreen {
    private static final OptionInstance<Boolean> server_ip = OptionInstance.createBoolean(
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
                Minecraft.getInstance().options,
                Component.literal("Presencium"),
                new OptionInstance[]{server_ip}
        );
    }
}
