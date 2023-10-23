package me.splitque.presencium;

import net.minecraft.client.MinecraftClient;

public class states {
    public static String single_state = "";
    public static String multi_state = "";
    public static String main_state = "";
    public static String language = MinecraftClient.getInstance().getLanguageManager().getLanguage();

    public static void setstates() {
        switch (language) {
            case "ru_ru":
                single_state = "В одиночной игре";
                multi_state = "В сетевой игре: ";
                main_state = "В игре";
                break;
            default:
                single_state = "In singleplayer";
                multi_state = "In multiplayer: ";
                main_state = "In game";
                break;
        }
    }
}
