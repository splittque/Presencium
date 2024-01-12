package me.splitque.presencium;

import club.minnced.discord.rpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class discord {
    public static final DiscordRPC presencium = DiscordRPC.INSTANCE;
    public static final Logger logger = LogManager.getLogger();
    public static final DiscordRichPresence presence = new DiscordRichPresence();

    public static void start() {
        presencium.Discord_Initialize("1119950313047208006", null, true, null);
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = "mclogo";
        presence.largeImageText = "Presencium by splittque";
        presencium.Discord_UpdatePresence(presence);
        logger.info("[Presencium] DiscordRPC has been started!");
    }
    public static void stop() {
        presencium.Discord_Shutdown();
        logger.info("[Presencium] DiscordRPC has been stopped!");
    }
    public static void update(String state) {
        presence.details = state;
        presencium.Discord_UpdatePresence(presence);
    }
}
