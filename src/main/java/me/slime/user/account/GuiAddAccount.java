package me.slime.user.account;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class GuiAddAccount extends GuiScreen implements GuiYesNoCallback {

  private GuiScreen parentScreen;
  private GuiTextField usernameField;
  private GuiTextField passwordField;
  private String status = "";
  private AccountManager manager = new AccountManager();

  public GuiAddAccount(GuiScreen parentScreen){
    this.parentScreen = parentScreen;
  }

  @Override
  public void initGui() {
    Minecraft.getMinecraft().getSlimeClient().getDiscordClient().setStatus("Idle", "Account Menü");

    Keyboard.enableRepeatEvents(true);

    this.registerButtons();

    this.usernameField = new GuiTextField(2, fontRendererObj, this.width / 2 - 100, 76, 200, 20);
    this.passwordField = new GuiTextField(2, fontRendererObj, this.width / 2 - 100, 116, 200, 20);

    this.usernameField.setText("");
    this.passwordField.setText("");

  }
  //public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
  public void registerButtons(){
    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 60, "Login"));
    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + 85, "Back"));
  }

  @Override
  protected void keyTyped(char typedChar, int keyCode) throws IOException {
    usernameField.textboxKeyTyped(typedChar, keyCode);
    passwordField.textboxKeyTyped(typedChar, keyCode);

    if(typedChar == '\t') {
      if(usernameField.isFocused()) {
        usernameField.setFocused(false);
        passwordField.setFocused(true);
      }else {
        usernameField.setFocused(true);
        passwordField.setFocused(false);
      }
    }

    if(typedChar == '\r') {
      actionPerformed(this.buttonList.get(1));
    }
  }

  @Override
  protected void mouseClicked(int x, int y, int button) throws IOException {
    super.mouseClicked(x, y, button);
    usernameField.mouseClicked(x, y, button);
    passwordField.mouseClicked(x, y, button);
  }

  @Override
  public void onGuiClosed() {
    Keyboard.enableRepeatEvents(false);
  }

  @Override
  protected void actionPerformed(GuiButton button) throws IOException {
    if(button.enabled){
      if(button.id == 0) {
        if(usernameField.getText() != null && !usernameField.getText().isEmpty()) {
          String username = this.usernameField.getText();
          String password = this.passwordField.getText();
          if (!password.isEmpty()) {
            manager.addAcount(username, password);
          }
        }
      }
      if(button.id == 1){
        this.mc.displayGuiScreen(this.parentScreen);
      }
    }
    drawString(fontRendererObj, status, width / 2, 20, 0x66FF33);
  }

  @Override
  public void updateScreen() {
    super.updateScreen();
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    drawWorldBackground(100);
    this.drawCenteredString(this.fontRendererObj, I18n.format("Accounts", new Object[0]), this.width / 2, 20, 16777215);

    drawString(fontRendererObj, "Username", width / 2 - 100, 63, 0xa0a0a0);
    drawString(fontRendererObj, "Password", width / 2 - 100, 104, 0xa0a0a0);
    usernameField.drawTextBox();
    passwordField.drawTextBox();

    if(status != null) {
      drawString(fontRendererObj, status, width / 2 - 100, 10, 0xa0a0a0);
    }

    super.drawScreen(mouseX, mouseY, partialTicks);
  }


}
