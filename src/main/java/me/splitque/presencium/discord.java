package me.splitque.presencium;

import club.minnced.discord.rpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import me.splitque.presencium.config.config;

public class discord {
    public static final DiscordRPC presencium = DiscordRPC.INSTANCE;
    public static Boolean isWorking;
    public static final Logger logger = LogManager.getLogger();
    public static final DiscordRichPresence presence = new DiscordRichPresence();
    public static final DiscordEventHandlers handlers = new DiscordEventHandlers();

    public static void start() {
        presencium.Discord_Initialize("1119950313047208006", handlers, true, null);
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = "mclogo";
        presence.largeImageText = "Presencium by splittque";
        presencium.Discord_UpdatePresence(presence);

        handlers.disconnected = (errorCode, message) -> start();
        logger.info("[Presencium] DiscordRPC has been started!");
        isWorking = true;
    }
    public static void stop() {
        presencium.Discord_Shutdown();
        logger.info("[Presencium] DiscordRPC has been stopped!");
        isWorking = false;
    }
    public static void update(String state) {
        if (isWorking) {
            if (config.get("rpc_onoff")) {
                presence.details = state;
                presencium.Discord_UpdatePresence(presence);
            }
            if (!config.get("rpc_onoff")) {
                stop();
            }
        }
        if (!isWorking) {
            if (config.get("rpc_onoff")) {
                start();
            }
        }
    }
}
