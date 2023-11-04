package me.splitque.presencium;

import net.minecraft.client.MinecraftClient;

public class translate {
    public static String single_state = "";
    public static String multi_state = "";
    public static String realm_state = "";
    public static String main_state = "";
    public static String server_ip = "";
    public static String language = MinecraftClient.getInstance().getLanguageManager().getLanguage();

    public static void setlang() {
        switch (language) {
            case "ru_ru":
                single_state = "В одиночной игре";
                multi_state = "В сетевой игре";
                realm_state = "В Realms";
                main_state = "В игре";
                server_ip = "Показывать IP сервера";
                break;
            default:
                single_state = "In singleplayer";
                multi_state = "In multiplayer";
                realm_state = "In Realms";
                main_state = "In game";
                server_ip = "Show server IP";
                break;
        }
    }
}
