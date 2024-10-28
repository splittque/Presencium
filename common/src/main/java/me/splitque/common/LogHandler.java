package me.splitque.common;

import me.splitque.log.Logger;
import me.splitque.log.variables.Color;

public class LogHandler {
    private static Logger log = new Logger("Presencium", Color.GREEN, false, true);
    private static String _state = "";

    public static void started() {
        log.info("DiscordRPC has been started!", null);
    }
    public static void stopped() {
        log.info("DiscordRPC has been stopped!", null);
    }
    public static void debug(String message, int code) {
        log.custom(message, null, code, "DEBUG", null,  SettingsHandler.getOption("debug"));
    }
    public static void stateUpdate(String state) {
        if (!_state.equals(state)) {
            _state = state;
            log.info("State changed to: " + state, null);
        }
    }
    public static void settingChanged(String key, String value) {
        log.info("Setting " + key + " changed to: " + value, null);
    }
}
