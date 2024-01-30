package me.slime.user.friend;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import org.lwjgl.input.Keyboard;

/**
 * Created by Kevloe on 08.04.2020.
 */

public class GuiFriends extends GuiScreen implements GuiYesNoCallback {

  private GuiScreen parentScreen;

  public GuiFriends(GuiScreen parentScreen){
    this.parentScreen = parentScreen;
  }

  @Override
  public void initGui() {
    Minecraft.getMinecraft().getSlimeClient().getDiscordClient().setStatus("Idle", "Friend Menu");
    this.buttonList.clear();
    Keyboard.enableRepeatEvents(true);

    this.createButtons();
  }
  public void createButtons(){
    this.buttonList.add(new GuiButton(0, this.width / 2 - 50, this.height/2, 100, 20, "Back"));
  }

  @Override
  protected void actionPerformed(GuiButton button) throws IOException {
    if(button.enabled){
      if(button.id == 0){
        this.mc.displayGuiScreen(this.parentScreen);
      }
    }
  }

  @Override
  public void updateScreen() {
    super.updateScreen();
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    this.drawDefaultBackground();
    this.drawCenteredString(this.fontRendererObj, "Friend Menü", this.width / 2, 20, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
