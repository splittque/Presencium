package me.splitque.presencium.config;

import net.minecraft.client.Minecraft;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class config {
    private static String path = "config/presencium.json";
    private static JSONObject json;
    private static Minecraft mc = Minecraft.getInstance();
    private static Path configfile = mc.gameDirectory.toPath().resolve(path);

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
        } catch (Exception e) {
            e.printStackTrace();
            save();
        }
    }

    public static void set(boolean value){
        json.put("server_ip", value);
    }
    public static boolean get() {
        boolean value = json.getBoolean("server_ip");
        return value;
    }
}
