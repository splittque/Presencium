package me.splitque.common;

import me.splitque.configuration.ConfigHandler;

public class SettingsHandler {
    public static ConfigHandler configHandler;

    public static void loadSettings(String path) {
        configHandler = new ConfigHandler(false, path, "presencium", "PROPERTIES");
        configHandler.putOption("rpc_switcher", "true");
        configHandler.putOption("show_server_ip", "true");
        configHandler.putOption("debug", "false");
    }
    public static void setOption(String name, Boolean value) {
        configHandler.setOption(name, String.valueOf(value));
        Logging.settingChanged(name, String.valueOf(value));
    }
    public static Boolean getOption(String name) {
        return configHandler.getBoolean(name);
    }
}