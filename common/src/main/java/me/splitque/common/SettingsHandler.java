package me.splitque.common;

import me.splitque.configuration.Configuration;
import me.splitque.configuration.handlers.Properties;

public class SettingsHandler {
    private static Configuration configHandler;

    public static void loadSettings(String path) {
        configHandler = new Configuration("presencium.properties", new Properties(), path);
        configHandler.addOption("rpc_switcher", "true");
        configHandler.addOption("show_server_ip", "true");
        configHandler.addOption("debug", "false");
        configHandler.save();
        LogHandler.settingsLoaded();
    }
    public static void setOption(String name, Boolean value) {
        configHandler.setOption(name, String.valueOf(value));
        configHandler.save();
        LogHandler.settingChanged(name, String.valueOf(value));
    }
    public static Boolean getOption(String name) {
        return configHandler.getBoolean(name);
    }
}