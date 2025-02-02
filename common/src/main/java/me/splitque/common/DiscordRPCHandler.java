package me.splitque.common;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;

import de.jcm.discordgamesdk.activity.Activity;

import java.time.Instant;
import java.util.Calendar;

public class DiscordRPCHandler {
    private static Boolean closed = false;
    private static Boolean rpcIsStarted;
    private static CreateParams params;
    private static Core core;
    private static Activity activity;
    private static Calendar calendar = Calendar.getInstance();

    public static void startCheck() {
        Runnable check = () -> {
            while (true) {
                if (core != null && !closed) rpcIsStarted = core.isDiscordRunning();
                if (core == null && !closed) rpcIsStarted = false;
                if (!closed) LogHandler.debug("Checks...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        };
        Thread thread = new Thread(check);
        thread.setName("Presencium-RPC-Check");
        thread.start();
    }

    public static void startRPC() {
        params = new CreateParams();

        params.setClientID(Long.parseLong("1119950313047208006"));
        params.setFlags(CreateParams.Flags.NO_REQUIRE_DISCORD);

        try {
            core = new Core(params);
            activity = new Activity();

            activity.timestamps().setStart(Instant.now());
            activity.assets().setLargeImage("mclogo");
            activity.assets().setLargeText(getImageText());

            core.activityManager().updateActivity(activity);
            LogHandler.started();
        } catch (Exception e) {
            stopRPC();
        }
    }
    public static void stopRPC() {
        if (core != null) core.close();
        if (core != null) LogHandler.stopped();
        core = null;
        params = null;
        activity = null;
    }
    public static void close() {
        stopRPC();
        closed = true;
        rpcIsStarted = null;
    }
    public static void updateState(String translatedState, String vanillaState) {
        if (rpcIsStarted != null) {
            if (rpcIsStarted) {
                if (SettingsHandler.getOption("rpc_switcher")) {
                    activity.setState(translatedState);
                    LogHandler.stateUpdate(vanillaState);
                    try {
                        core.activityManager().updateActivity(activity);
                    } catch (Exception e) {}
                    LogHandler.debug("Update state... (rpcIsStarted = true : rpc_switcher = true)");
                }
                if (!SettingsHandler.getOption("rpc_switcher")) {
                    LogHandler.debug("Shutting down RPC... (rpcIsStarted = true : rpc_switcher = false)");
                    stopRPC();
                }
            }
            if (!rpcIsStarted) {
                if (SettingsHandler.getOption("rpc_switcher")) {
                    LogHandler.debug("Starting RPC... (rpcIsStarted = false : rpc_switcher = true)");
                    startRPC();
                }
                if (!SettingsHandler.getOption("rpc_switcher")) {
                    LogHandler.debug("Nothing happened... (rpcIsStarted = false : rpc_switcher = false)");
                }
            }
        } else {
            LogHandler.debug("Isn't initialized or closed... (rpcIsStarted = null)");
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