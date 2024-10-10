package me.splitque.common;

import club.minnced.discord.rpc.*;

import java.util.Calendar;

public class DiscordRPCHandler {
    private static Boolean rpcIsStarted;
    private static DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static DiscordEventHandlers handlers = new DiscordEventHandlers();
    private static DiscordRichPresence presence = new DiscordRichPresence();
    private static Calendar calendar = Calendar.getInstance();

    public static void start() {
        handlers.ready = (user) -> {
            Logging.start();
            rpcIsStarted = true;
        };
        handlers.disconnected = (errorCode, message) -> {
            Logging.end(false, null);
            rpcIsStarted = false;
        };

        Runnable callbacks = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Logging.debug();
                    discordRPC.Discord_RunCallbacks();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(callbacks);
        thread.setName("RPC-Callback-Handler");
        if () {
            thread.start();
        }

        discordRPC.Discord_Initialize("1119950313047208006", handlers, true, null);
        discordRPC.Discord_UpdateHandlers(handlers);
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = "mclogo";
        presence.largeImageText = getImageText();
        discordRPC.Discord_UpdatePresence(presence);
    }
    public static void stop() {
        discordRPC.Discord_Shutdown();
    }
    public static void update(String state) {
        if (rpcIsStarted != null) {
            if (rpcIsStarted) {
                if (SettingsHandler.get("rpc_switcher")) {
                    presence.details = state;
                    discordRPC.Discord_UpdatePresence(presence);
                }
                if (!SettingsHandler.get("rpc_switcher")) {
                    stop();
                }
            }
            if (!rpcIsStarted) {
                if (SettingsHandler.get("rpc_switcher")) {
                    start();
                }
            }
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