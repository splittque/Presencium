package me.splitque.common;

import tools.configurator.Configurator;
import tools.configurator.serializators.PropertiesConfigurator;

public class SettingsHandler {
    private static Configurator configHandler;

    public static void loadSettings(String path) {
        configHandler = new PropertiesConfigurator("presencium.properties", path);
        configHandler.load();
        configHandler.addBoolean("rpc_switcher", true);
        configHandler.addBoolean("show_server_ip", true);
        configHandler.addBoolean("debug", false);
        configHandler.save();
        LogHandler.settingsLoaded();
    }
    public static void setOption(String key, Boolean value) {
        configHandler.setBoolean(key, value);
        configHandler.save();
        LogHandler.settingChanged(key, String.valueOf(value));
    }
    public static Boolean getOption(String key) {
        return configHandler.getBoolean(key);
    }
}