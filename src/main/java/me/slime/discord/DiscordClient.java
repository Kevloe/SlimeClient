package me.slime.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

/**
 * Created by Kevloe on 06.04.2020.
 */

public class DiscordClient {

  private boolean start;
  private long created;

  public DiscordClient(){
    this.startClient();
  }
  private void startClient(){
    this.created = System.currentTimeMillis();

    DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback(){
          @Override
          public void apply(DiscordUser discordUser) {
            System.out.println("User "+discordUser.username+"#"+discordUser.discriminator);
            setStatus("Boting...", " ");
          }
        }).build();
    DiscordRPC.discordInitialize("696746965131722783", discordEventHandlers, true);
    new Thread("Discord RPC"){
      @Override
      public void run() {
        while(start){
          DiscordRPC.discordRunCallbacks();
        }
      }
    }.start();
    this.start = true;
  }
  public void stopClient(){
    this.start = false;
    DiscordRPC.discordShutdown();
  }
  public void setStatus(String fistline, String secondLine){
    DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(secondLine);
    builder.setBigImage("icon_512x512", "");
    builder.setDetails(fistline);
    builder.setStartTimestamps(this.created);

    DiscordRPC.discordUpdatePresence(builder.build());
  }
}
