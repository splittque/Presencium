package me.splitque.presencium.config;

import me.splitque.configuration.ConfigHandler;
import net.minecraft.client.MinecraftClient;

public class config {
    private static MinecraftClient mc = MinecraftClient.getInstance();
    private static String configfile = String.valueOf(mc.runDirectory.toPath().resolve("config"));
    public static ConfigHandler configHandler = new ConfigHandler(false, configfile, "presencium", "PROPERTIES");

    public static void load() {
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