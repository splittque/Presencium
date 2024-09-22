package me.splitque.common;

import me.splitque.configuration.ConfigHandler;

public class Settings {
    public static ConfigHandler configHandler;

    public static void load(String path) {
        configHandler = new ConfigHandler(false, path, "presencium", "PROPERTIES");
        configHandler.putOption("rpc_onoff", "true");
        configHandler.putOption("server_ip", "true");
    }
    public static void set(String name, Boolean value) {
        configHandler.setOption(name, String.valueOf(value));
    }
    public static Boolean get(String name) {
        return configHandler.getBoolean(name);
    }
}