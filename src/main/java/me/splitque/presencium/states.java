package me.splitque.presencium;

import net.minecraft.client.MinecraftClient;

public class states {
    public static String single_state = "";
    public static String multi_state = "";
    public static String realm_state = "";
    public static String main_state = "";
    public static String language = MinecraftClient.getInstance().getLanguageManager().getLanguage();

    public static void setstates() {
        switch (language) {
            case "ru_ru":
                single_state = "В одиночной игре";
                multi_state = "В сетевой игре: ";
                realm_state = "В Realms";
                main_state = "В игре";
                break;
            default:
                single_state = "In singleplayer";
                multi_state = "In multiplayer: ";
                realm_state = "In Realms";
                main_state = "In game";
                break;
        }
    }
}
