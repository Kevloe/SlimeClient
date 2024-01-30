package me.slime.splash;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Kevloe on 08.04.2020.
 */

public class SplashScreen {

  private int MAX;
  private int PROGRESS;
  private String CURRENT;
  private ResourceLocation resourceLocation;
  private UnicodeFontRenderer unicodeFontRenderer;

  public SplashScreen(int max){
    this.MAX = max;
  }
  public void setStat(int status, String statusMessage){
    if(Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null){
      return;
    }
    this.PROGRESS = status;
    this.CURRENT = statusMessage;
    this.drawSplashScreen(Minecraft.getMinecraft().getTextureManager());
  }
  public void drawSplashScreen(TextureManager textureManager){
    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
    int scaleFactor = scaledResolution.getScaleFactor();

    Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth()*scaleFactor, scaledResolution.getScaledHeight()*scaleFactor, true);
    framebuffer.bindFramebuffer(false);

    GlStateManager.matrixMode(GL11.GL_PROJECTION);
    GlStateManager.loadIdentity();
    GlStateManager.ortho(0.0D, (double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
    GlStateManager.matrixMode(GL11.GL_MODELVIEW);
    GlStateManager.loadIdentity();
    GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    GlStateManager.disableLighting();
    GlStateManager.disableFog();
    GlStateManager.disableDepth();
    GlStateManager.enableTexture2D();

    if(this.resourceLocation == null){
      this.resourceLocation = new ResourceLocation("slimeclient/background/splash.png");
    }

    textureManager.bindTexture(this.resourceLocation);

    GlStateManager.resetColor();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

    Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1920, 1080, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 1920, 1080);
    drawProgress();
    framebuffer.unbindFramebuffer();
    framebuffer.framebufferRender(scaledResolution.getScaledWidth()*scaleFactor, scaledResolution.getScaledHeight()*scaleFactor);

    GlStateManager.enableAlpha();
    GlStateManager.alphaFunc(516, 0.1F);

    Minecraft.getMinecraft().updateDisplay();
  }
  private void drawProgress(){
    if(Minecraft.getMinecraft().gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null){
      return;
    }

    if(this.unicodeFontRenderer == null){
      this.unicodeFontRenderer = UnicodeFontRenderer.getFontOnPC("Arial", 20);
    }

    ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

    double Progress = (double)PROGRESS;
    double calc = (Progress / this.MAX) * scaledResolution.getScaledWidth();

    Gui.drawRect(0, scaledResolution.getScaledHeight() - 35, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(0, 0, 0, 50).getRGB());

    GlStateManager.resetColor();
    this.removeAll();

    this.unicodeFontRenderer.drawString(this.CURRENT, 20, scaledResolution.getScaledHeight() - 25, 0xFFFFFFFF);

    String step = this.PROGRESS +"/"+this.MAX;
    this.unicodeFontRenderer.drawString(step, scaledResolution.getScaledWidth()-20-this.unicodeFontRenderer.getStringWidth(step), scaledResolution.getScaledHeight()-25, 0xe1e1e1FF);

    GlStateManager.resetColor();
    this.removeAll();

    Gui.drawRect(0, scaledResolution.getScaledHeight()-2, (int)calc, scaledResolution.getScaledHeight(), new Color(149, 201, 144).getRGB());

    Gui.drawRect(0, scaledResolution.getScaledHeight() - 2, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(0, 0, 0, 30).getRGB());
  }
  private void removeAll(){
    GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
  }
}
