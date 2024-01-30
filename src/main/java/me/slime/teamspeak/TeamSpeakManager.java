package me.slime.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import me.slime.SlimeClient;

/**
 * Created by Kevloe on 12.04.2020.
 */

public class TeamSpeakManager {

  private boolean connected;

  private SlimeClient client;

  public TeamSpeakManager(SlimeClient client){
    this.connected = false;
    this.client = client;

    this.init();
  }
  private void init(){
    TS3Config config = new TS3Config();
    config.setHost("localhost");
    config.setQueryPort(25639);
    TS3Query query = new TS3Query(config);
    restartBot(query);
  }
  public void restartBot(TS3Query query){
    query.connect();
    this.connected = query.isConnected();
    this.client.setApi(query.getApi());
    if(this.connected){
      System.out.println("Connectet");
      this.client.getApi().addTS3Listeners(new DisplayInfo());
      new DisplayInfo().init();
      return;
    }
    System.out.println("Can't Connect!");
    return;
  }
}
