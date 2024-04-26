package me.splitque.presencium;

import me.splitque.presencium.config.modmenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.GameShuttingDownEvent;
import me.splitque.presencium.config.config;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("presencium")
public class main {
    public main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::start);
    }

    private void start(final FMLCommonSetupEvent event) {
        discord.start();
        config.load();
        modmenu.registerModsPage();
    }
    @Mod.EventBusSubscriber(modid = "presencium")
    public static class events {
        public static Minecraft mc = Minecraft.getInstance();
        public static String state = "";

        @SubscribeEvent
        public static void discordupdate(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                if (mc.isSingleplayer()) {
                    state = I18n.get("single_state");
                } else if (mc.getCurrentServer() != null) {
                    if (mc.getCurrentServer().isRealm()) {
                        state = I18n.get("realm_state");
                    }

                    if (config.get("server_ip")) {
                        String address = mc.getCurrentServer().ip;
                        state = I18n.get("multi_state") + ": " + address;
                    } else if (!config.get("server_ip")) {
                        state = I18n.get("multi_state");
                    }
                } else {
                    state = I18n.get("main_state");
                }

                discord.update(state);
            }
        }

        @SubscribeEvent
        public static void stop(GameShuttingDownEvent event) {
            discord.stop();
            config.save();
        }
    }
}
