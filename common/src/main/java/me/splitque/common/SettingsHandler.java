package me.splitque.common;

import me.splitque.configuration.ConfigHandler;

public class SettingsHandler {
    public static ConfigHandler configHandler;

    public static void load(String path) {
        configHandler = new ConfigHandler(false, path, "presencium", "PROPERTIES");
        configHandler.putOption("rpc_switcher", "true");
        configHandler.putOption("show_server_ip", "true");
    }
    public static void set(String name, Boolean value) {
        configHandler.setOption(name, String.valueOf(value));
    }
    public static Boolean get(String name) {
        return configHandler.getBoolean(name);
    }
}