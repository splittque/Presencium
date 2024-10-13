package me.splitque.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {
    private static Logger logger = LogManager.getLogger("Presencium");
    private static String _state = "";

    public static void started() {
        logger.info("DiscordRPC has been started!");
    }
    public static void stopped() {
        logger.info("DiscordRPC has been stopped!");
    }
    public static void debug(String message, int code) {
        if (SettingsHandler.getOption("debug")) {
            logger.info("[DEBUG]: " + code + " - " + message);
        }
    }
    public static void stateUpdate(String state) {
        if (!_state.equals(state)) {
            _state = state;
            logger.info("State changed to: " + state);
        }
    }
    public static void settingChanged(String name, String value) {
        logger.info("Setting " + name + " changed to: " + value);
    }
}
