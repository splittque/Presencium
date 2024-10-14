package me.splitque.common;

import club.minnced.discord.rpc.*;

import java.util.Calendar;

public class DiscordRPCHandler {
    private static Boolean rpcIsStarted;
    private static DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static DiscordRichPresence presence = new DiscordRichPresence();
    private static DiscordEventHandlers handlers = new DiscordEventHandlers();
    private static Calendar calendar = Calendar.getInstance();

    public static void startCallbacks() {
        Runnable callbacks = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    discordRPC.Discord_RunCallbacks();
                    Logging.debug("callback", 2);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {}
                }
            }
        };
        Thread thread = new Thread(callbacks);
        thread.setName("Presencium-RPC-Callbacks");
        thread.start();
    }

    public static void startRPC() {
        handlers.ready = (user) -> {
            Logging.started();
            rpcIsStarted = true;
        };
        handlers.disconnected = (errorCode, message) -> {
            Logging.stopped();
            rpcIsStarted = false;
        };
        discordRPC.Discord_Initialize("1119950313047208006", handlers, true, null);
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = "mclogo";
        presence.largeImageText = getImageText();
        discordRPC.Discord_UpdatePresence(presence);
        Logging.debug("rpc init", 1);
    }
    public static void stopRPC() {
        discordRPC.Discord_Shutdown();
        Logging.stopped();
        rpcIsStarted = false;
    }
    public static void updateState(String translatedState, String vanillaState) {
        if (rpcIsStarted != null) {
            if (rpcIsStarted) {
                if (SettingsHandler.getOption("rpc_switcher")) {
                    Logging.debug("true (" + vanillaState + ")", 3);
                    presence.details = translatedState;
                    Logging.stateUpdate(vanillaState);
                    discordRPC.Discord_UpdatePresence(presence);
                }
                if (!SettingsHandler.getOption("rpc_switcher")) {
                    Logging.debug("false", 3);
                    stopRPC();
                }
            }
            if (!rpcIsStarted) {
                if (SettingsHandler.getOption("rpc_switcher")) {
                    Logging.debug("true", 4);
                    startRPC();
                }
            }
        } else {
            Logging.debug("false", 5);
            rpcIsStarted = false;
        }
    }

    private static String getImageText() {
        if (calendar.get(Calendar.DAY_OF_MONTH) == 26 && calendar.get(Calendar.MONTH) == Calendar.OCTOBER) {
            return "HAPPY BIRTHDAY, PRESENCIUM!";
        } else {
            return "Presencium by splittque";
        }
    }
}