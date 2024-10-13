package me.splitque.presencium;

import me.splitque.common.SettingsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

public class SettingsScreen extends OptionsSubScreen {
    private final OptionInstance<Boolean> show_server_ip = OptionInstance.createBoolean(
            "show_server_ip",
            SettingsHandler.getOption("show_server_ip"),
            value -> {
                SettingsHandler.setOption("show_server_ip", value);
            }
    );
    public final OptionInstance<Boolean> rpc_switcher = OptionInstance.createBoolean(
            "rpc_switcher",
            SettingsHandler.getOption("rpc_switcher"),
            value -> {
                SettingsHandler.setOption("rpc_switcher", value);
            }
    );

    @Override
    protected void addOptions() {
        this.list.addSmall(show_server_ip, rpc_switcher);
    }

    public SettingsScreen(Screen screen) {
        super(
                screen,
                Minecraft.getInstance().options,
                Component.literal("Presencium")
        );
    }
}

