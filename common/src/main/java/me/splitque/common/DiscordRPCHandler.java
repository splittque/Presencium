package me.splitque.common;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.GameSDKException;
import de.jcm.discordgamesdk.activity.Activity;

import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;

public class DiscordRPCHandler {
    private static Boolean rpcIsStarted;
    private static CreateParams params;
    private static Core core;
    private static Activity activity;
    private static Calendar calendar = Calendar.getInstance();

    public static void startCheck() {
        Runnable check = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (core != null) rpcIsStarted = core.isDiscordRunning();
                    if (core == null) rpcIsStarted = false;
                    LogHandler.debug("check", 2, SettingsHandler.getOption("debug"));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {}
                }
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

        LogHandler.debug("rpc init", 1, SettingsHandler.getOption("debug"));
    }
    public static void stopRPC() {
        if (core != null) core.close();
        if (core != null) LogHandler.stopped();
        core = null;
        params = null;
        activity = null;
    }
    public static void updateState(String translatedState, String vanillaState) {
        if (rpcIsStarted != null) {
            if (rpcIsStarted) {
                if (SettingsHandler.getOption("rpc_switcher")) {
                    LogHandler.debug("true (" + vanillaState + ")", 3, SettingsHandler.getOption("debug"));
                    activity.setState(translatedState);
                    LogHandler.stateUpdate(vanillaState);
                    try {
                        core.activityManager().updateActivity(activity);
                    } catch (Exception e) {}

                }
                if (!SettingsHandler.getOption("rpc_switcher")) {
                    LogHandler.debug("false", 3, SettingsHandler.getOption("debug"));
                    stopRPC();
                }
            }
            if (!rpcIsStarted) {
                if (SettingsHandler.getOption("rpc_switcher")) {
                    LogHandler.debug("true", 4, SettingsHandler.getOption("debug"));
                    startRPC();
                }
            }
        } else {
            LogHandler.debug("false", 5, SettingsHandler.getOption("debug"));
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