package me.slime;

import com.github.theholywaffle.teamspeak3.TS3Api;
import me.slime.discord.DiscordClient;
import me.slime.splash.SplashScreen;
import org.lwjgl.opengl.Display;

public class SlimeClient {

    private static final SlimeClient instance = new SlimeClient();

    private DiscordClient discordClient;
    private SplashScreen splashScreen;

    private TS3Api api = null;

    public static final SlimeClient getInstance() {
        return instance;
    }

    public void initClient() {
        this.discordClient = new DiscordClient();
        this.splashScreen = new SplashScreen(8);
        this.splashScreen.setStat(1, "Client init");
    }

    public void startClient() {
        Display.setTitle("Slime Client | 1.8.8");
    }

    public DiscordClient getDiscordClient() {
        return discordClient;
    }

    public SplashScreen getSplashScreen() {
        return splashScreen;
    }

    public TS3Api getApi() {
        return api;
    }

    public void setApi(TS3Api api) {
        this.api = api;
    }
}