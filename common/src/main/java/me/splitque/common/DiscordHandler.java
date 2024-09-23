package me.splitque.common;

import club.minnced.discord.rpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

public class DiscordHandler {
    private static DiscordRPC presencium = DiscordRPC.INSTANCE;
    private static Boolean isWorking;
    private static Logger log = LogManager.getLogger("Presencium");
    private static DiscordRichPresence presence = new DiscordRichPresence();
    private static DiscordEventHandlers handlers = new DiscordEventHandlers();
    private static Calendar calendar = Calendar.getInstance();

    public static void start() {
        presencium.Discord_Initialize("1119950313047208006", handlers, true, null);
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = "mclogo";
        if (calendar.get(Calendar.DAY_OF_MONTH) == 26 && calendar.get(Calendar.MONTH) == Calendar.OCTOBER) {
            presence.largeImageText = "HAPPY BIRTHDAY, PRESENCIUM!";
        } else {
            presence.largeImageText = "Presencium by splittque";
        }
        presencium.Discord_UpdatePresence(presence);

        handlers.disconnected = (errorCode, message) -> start();
        log.info("RPC has been started!");
        isWorking = true;
    }
    public static void stop() {
        presencium.Discord_Shutdown();
        log.info("RPC has been stopped!");
        isWorking = false;
    }
    public static void update(String state) {
        if (isWorking) {
            if (Settings.get("rpc_onoff")) {
                presence.details = state;
                presencium.Discord_UpdatePresence(presence);
            }
            if (!Settings.get("rpc_onoff")) {
                stop();
            }
        }
        if (!isWorking) {
            if (Settings.get("rpc_onoff")) {
                start();
            }
        }
    }
}