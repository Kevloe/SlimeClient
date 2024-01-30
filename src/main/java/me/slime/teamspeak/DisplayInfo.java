package me.slime.teamspeak;

import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerQueryInfo;
import java.util.ArrayList;
import java.util.List;
import me.slime.SlimeClient;

/**
 * Created by Kevloe on 12.04.2020.
 */

public class DisplayInfo implements TS3Listener {

  private List<Client> clients;

  private int currentChannel;
  private int currentClientID;

  public void init(){
    this.clients = SlimeClient.getInstance().getApi().getClients();
    if(SlimeClient.getInstance().getApi().whoAmI() != null){
      ServerQueryInfo info = SlimeClient.getInstance().getApi().whoAmI() ;
      this.currentClientID = info.getId();
      this.currentChannel = info.getChannelId();
      System.out.println(this.currentClientID);
    }
    getClientsInChannel();
  }
  public List<Client> getClientsInChannel(){
    List<Client> clients = new ArrayList<>();
    for (Client client : this.clients) {
      if(client.getChannelId() == this.currentChannel){
        clients.add(client);
      }
    }
    return clients;
  }

  @Override
  public void onTextMessage(TextMessageEvent textMessageEvent) {

  }

  @Override
  public void onClientJoin(ClientJoinEvent clientJoinEvent) {

  }

  @Override
  public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {

  }

  @Override
  public void onServerEdit(ServerEditedEvent serverEditedEvent) {

  }

  @Override
  public void onChannelEdit(ChannelEditedEvent channelEditedEvent) {

  }

  @Override
  public void onChannelDescriptionChanged(
      ChannelDescriptionEditedEvent channelDescriptionEditedEvent) {

  }

  @Override
  public void onClientMoved(ClientMovedEvent clientMovedEvent) {

  }

  @Override
  public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {

  }

  @Override
  public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {

  }

  @Override
  public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {

  }

  @Override
  public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {

  }

  @Override
  public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {

  }
}
