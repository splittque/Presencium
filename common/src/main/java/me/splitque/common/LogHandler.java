package me.splitque.common;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHandler {
    private static Logger log = LogManager.getLogger("Presencium");
    private static String _state = "";

    public static void started() {
        log.info("DiscordRPC has been started!");
    }
    public static void stopped() {
        log.info("DiscordRPC has been stopped!");
    }
    public static void debug(String message) {
        if (SettingsHandler.getOption("debug")) log.warn("[DEBUG] " + message); // [DEBUG] message
    }
    public static void stateUpdate(String state) {
        if (!_state.equals(state)) {
            _state = state;
            log.info("State changed to: " + state);
        }
    }
    public static void settingsLoaded() {
        log.info("Mod settings loaded!");
    }
    public static void settingChanged(String key, String value) {
        log.info("Setting " + key + " changed to: " + value);
    }
}
