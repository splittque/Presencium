package me.splitque.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {
    private static Logger logger = LogManager.getLogger("Presencium");

    public static void start() {
        logger.info("DiscordRPC has been started!");
    }
    public static void debug() {
        logger.info("DEBUG");
    }
    public static void end(boolean isError, String error) {
        if (isError) {
            logger.info("DiscordRPC has been stopped! Error code: " + error);
        } else if (!isError) {
            logger.info("DiscordRPC has been stopped!");
        }
    }
}
