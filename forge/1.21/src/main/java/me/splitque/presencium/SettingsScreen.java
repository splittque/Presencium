package me.splitque.presencium;

import me.splitque.common.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

public class SettingsScreen extends OptionsSubScreen {
    private final OptionInstance<Boolean> server_ip = OptionInstance.createBoolean(
            "server_ip",
            Settings.get("server_ip"),
            value -> {
                Settings.set("server_ip", value);
            }
    );
    public final OptionInstance<Boolean> rpc_onoff = OptionInstance.createBoolean(
            "rpc_onoff",
            Settings.get("rpc_onoff"),
            value -> {
                Settings.set("rpc_onoff", value);
            }
    );

    @Override
    protected void addOptions() {
        this.list.addSmall(server_ip, rpc_onoff);
    }

    public SettingsScreen(Screen screen) {
        super(
                screen,
                Minecraft.getInstance().options,
                Component.literal("Presencium")
        );
    }
}