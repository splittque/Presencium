package me.splitque.presencium.config;

import net.minecraft.client.MinecraftClient;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class config {
    private static String path = "config/presencium.json";
    private static JSONObject json;
    private static MinecraftClient mc = MinecraftClient.getInstance();
    private static Path configfile = mc.runDirectory.toPath().resolve(path);

    public static void save() {
        try {
            Files.createDirectories(configfile.getParent());
            Files.writeString(configfile, json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void load() {
        try {
            json = configfile.toFile().exists() ? new JSONObject(Files.readString(configfile)) : new JSONObject();
            if (json.isNull("server_ip")) {
                json.put("server_ip", true);
            }
            if (json.isNull("rpc_onoff")) {
                json.put("rpc_onoff", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            save();
        }
    }
    public static void set(String name, boolean value){
        json.put(name, value);
    }
    public static boolean get(String name) {
        boolean value = json.getBoolean(name);
        return value;
    }
}