package me.slime.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DrawUtils extends Gui {
  private Minecraft mc;

  public FontRenderer fontRenderer;

  private ScaledResolution scaledResolution;

  private final ModelSkeletonHead humanoidHead = (ModelSkeletonHead)new ModelHumanoidHead();

  private static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");

  public DrawUtils() {
    this.mc = Minecraft.getMinecraft();
    this.scaledResolution = new ScaledResolution(this.mc);
    this.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
  }

  public FontRenderer getFontRenderer() {
    return this.fontRenderer;
  }

  public void setFontRenderer(FontRenderer fontRenderer) {
    this.fontRenderer = fontRenderer;
  }

  public void bindTexture(ResourceLocation resourceLocation) {
    Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
  }

  public void bindTexture(String resourceLocation) {
    bindTexture(new ResourceLocation(resourceLocation));
  }

  public double getCustomScaling() {
    double factor = 1.0D + -1.0D * 0.03D;
    while ((Minecraft.getMinecraft()).displayWidth / factor < 320.0D)
      factor -= 0.1D;
    while ((Minecraft.getMinecraft()).displayHeight / factor < 240.0D)
      factor -= 0.1D;
    return factor;
  }

  public int getWidth() {
    return this.scaledResolution.getScaledWidth();
  }

  public int getHeight() {
    return this.scaledResolution.getScaledHeight();
  }

  public void setScaledResolution(ScaledResolution scaledResolution) {
    this.scaledResolution = scaledResolution;
  }

  public ScaledResolution getScaledResolution() {
    return this.scaledResolution;
  }

  public void drawString(String text, double x, double y) {
    this.fontRenderer.drawString(text, (float)x, (float)y, 16777215, true);
  }

  public void drawStringWithShadow(String text, double x, double y, int color) {
    getFontRenderer().drawStringWithShadow(text, (float)x, (float)y, color);
  }

  public void drawRightString(String text, double x, double y) {
    drawString(text, x - getStringWidth(text), y);
  }

  public void drawRightStringWithShadow(String text, int x, int y, int color) {
    getFontRenderer().drawStringWithShadow(text, (x - getStringWidth(text)), y, color);
  }

  public void drawCenteredString(String text, double x, double y) {
    drawString(text, x - (getStringWidth(text) / 2), y);
  }

  public void drawString(String text, double x, double y, double size) {
    GL11.glPushMatrix();
    GL11.glScaled(size, size, size);
    drawString(text, x / size, y / size);
    GL11.glPopMatrix();
  }

  public void drawCenteredString(String text, double x, double y, double size) {
    GL11.glPushMatrix();
    GL11.glScaled(size, size, size);
    drawCenteredString(text, x / size, y / size);
    GL11.glPopMatrix();
  }

  public void drawRightString(String text, double x, double y, double size) {
    GL11.glPushMatrix();
    GL11.glScaled(size, size, size);
    drawString(text, x / size - getStringWidth(text), y / size);
    GL11.glPopMatrix();
  }

  public void drawItem(ItemStack item, double xPosition, double yPosition, String value) {
    RenderHelper.enableGUIStandardItemLighting();
    GlStateManager.enableCull();
    if (item.hasEffect()) {
      GlStateManager.enableDepth();
      renderItemIntoGUI(item, xPosition, yPosition);
    } else {
      renderItemIntoGUI(item, xPosition, yPosition);
    }
    renderItemOverlayIntoGUI(item, xPosition, yPosition, value);
    GlStateManager.disableDepth();
    GlStateManager.disableLighting();
  }

  public void renderItemIntoGUI(ItemStack stack, double x, double y) {
    TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
    IBakedModel ibakedmodel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
    GlStateManager.pushMatrix();
    textureManager.bindTexture(TextureMap.locationBlocksTexture);
    textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
    GlStateManager.enableRescaleNormal();
    GlStateManager.enableAlpha();
    GlStateManager.alphaFunc(516, 0.1F);
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(770, 771);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    setupGuiTransform(x, y, ibakedmodel.isGui3d());
    ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
    Minecraft.getMinecraft().getRenderItem().renderItem(stack, ibakedmodel);
    GlStateManager.disableAlpha();
    GlStateManager.disableRescaleNormal();
    GlStateManager.disableLighting();
    GlStateManager.popMatrix();
    textureManager.bindTexture(TextureMap.locationBlocksTexture);
    textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
  }
  private void setupGuiTransform(double xPosition, double yPosition, boolean isGui3d) {
    GlStateManager.translate((float)xPosition, (float)yPosition, 100.0F + this.zLevel);
    GlStateManager.translate(8.0F, 8.0F, 0.0F);
    GlStateManager.scale(1.0F, 1.0F, -1.0F);
    GlStateManager.scale(0.5F, 0.5F, 0.5F);
    if (isGui3d) {
      GlStateManager.scale(40.0F, 40.0F, 40.0F);
      GlStateManager.rotate(210.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.enableLighting();
    } else {
      GlStateManager.scale(64.0F, 64.0F, 64.0F);
      GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.disableLighting();
    }
  }

  private void renderItemOverlayIntoGUI(ItemStack stack, double xPosition, double yPosition, String text) {
    if (stack != null) {
      if (stack.stackSize != 1 || text != null) {
        String s = (text == null) ? String.valueOf(stack.stackSize) : text;
        if (text == null && stack.stackSize < 1)
          s = EnumChatFormatting.RED + String.valueOf(stack.stackSize);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        this.drawString(s, xPosition + 19.0D - 2.0D - Minecraft.getMinecraft().fontRendererObj.getStringWidth(s), yPosition + 6.0D + 3.0D);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
      }
      if (stack.isItemDamaged()) {
        int j = (int)Math.round(13.0D - stack.getItemDamage() * 13.0D / stack.getMaxDamage());
        int i = (int)Math.round(255.0D - stack.getItemDamage() * 255.0D / stack.getMaxDamage());
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        drawItemTexture(xPosition + 2.0D, yPosition + 13.0D, 13.0D, 2.0D, 0, 0, 0, 255);
        drawItemTexture(xPosition + 2.0D, yPosition + 13.0D, 12.0D, 1.0D, (255 - i) / 4, 64, 0, 255);
        drawItemTexture(xPosition + 2.0D, yPosition + 13.0D, j, 1.0D, 255 - i, i, 0, 255);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
      }
    }
  }
  private void drawItemTexture(double x, double y, double z, double offset, int red, int green, int blue, int alpha) {
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
    worldrenderer.pos(x + 0.0D, y + 0.0D, 0.0D).color(red, green, blue, alpha).endVertex();
    worldrenderer.pos(x + 0.0D, y + offset, 0.0D).color(red, green, blue, alpha).endVertex();
    worldrenderer.pos(x + z, y + offset, 0.0D).color(red, green, blue, alpha).endVertex();
    worldrenderer.pos(x + z, y + 0.0D, 0.0D).color(red, green, blue, alpha).endVertex();
    Tessellator.getInstance().draw();
  }

  public int getStringWidth(String text) {
    return this.fontRenderer.getStringWidth(text);
  }

  public void drawRect(double left, double top, double right, double bottom, int color) {
    if (left < right) {
      double i = left;
      left = right;
      right = i;
    }
    if (top < bottom) {
      double j = top;
      top = bottom;
      bottom = j;
    }
    float f3 = (color >> 24 & 0xFF) / 255.0F;
    float f = (color >> 16 & 0xFF) / 255.0F;
    float f1 = (color >> 8 & 0xFF) / 255.0F;
    float f2 = (color & 0xFF) / 255.0F;
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    GlStateManager.enableBlend();
    GlStateManager.disableTexture2D();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    GlStateManager.color(f, f1, f2, f3);
    worldrenderer.begin(7, DefaultVertexFormats.POSITION);
    worldrenderer.pos(left, bottom, 0.0D).endVertex();
    worldrenderer.pos(right, bottom, 0.0D).endVertex();
    worldrenderer.pos(right, top, 0.0D).endVertex();
    worldrenderer.pos(left, top, 0.0D).endVertex();
    tessellator.draw();
    GlStateManager.enableTexture2D();
    GlStateManager.disableBlend();
  }

  public boolean drawRect(int mouseX, int mouseY, double left, double top, double right, double bottom, int color, int hoverColor) {
    boolean hover = (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom);
    drawRect(left, top, right, bottom, hover ? hoverColor : color);
    return hover;
  }

  public boolean drawRect(int mouseX, int mouseY, String displayString, double left, double top, double right, double bottom, int color, int hoverColor) {
    boolean hover = (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom);
    drawRect(left, top, right, bottom, hover ? hoverColor : color);
    drawCenteredString(displayString, left + (right - left) / 2.0D, top + (bottom - top) / 2.0D - 4.0D);
    return hover;
  }

  public void drawRectangle(int left, int top, int right, int bottom, int color) {
    drawRect(left, top, right, bottom, color);
  }

  public void drawGradientShadowTop(double y, double left, double right) {
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    int i1 = 4;
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
    GlStateManager.disableAlpha();
    GlStateManager.shadeModel(7425);
    GlStateManager.enableTexture2D();
    this.mc.getTextureManager().bindTexture(optionsBackground);
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    worldrenderer.pos(left, y + i1, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
    worldrenderer.pos(right, y + i1, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
    worldrenderer.pos(right, y, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
    worldrenderer.pos(left, y, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
    tessellator.draw();
    GlStateManager.shadeModel(7424);
    GlStateManager.enableAlpha();
    GlStateManager.disableBlend();
  }

  public void drawGradientShadowBottom(double y, double left, double right) {
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    int i1 = 4;
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
    GlStateManager.disableAlpha();
    GlStateManager.shadeModel(7425);
    GlStateManager.enableTexture2D();
    this.mc.getTextureManager().bindTexture(optionsBackground);
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    worldrenderer.pos(left, y, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
    worldrenderer.pos(right, y, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
    worldrenderer.pos(right, y - i1, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
    worldrenderer.pos(left, y - i1, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
    tessellator.draw();
    GlStateManager.shadeModel(7424);
    GlStateManager.enableAlpha();
    GlStateManager.disableBlend();
  }

  public void drawGradientShadowLeft(double x, double top, double bottom) {
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    int i1 = 4;
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
    GlStateManager.disableAlpha();
    GlStateManager.shadeModel(7425);
    GlStateManager.enableTexture2D();
    this.mc.getTextureManager().bindTexture(optionsBackground);
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    worldrenderer.pos(x + i1, bottom, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
    worldrenderer.pos(x + i1, top, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
    worldrenderer.pos(x, top, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
    worldrenderer.pos(x, bottom, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
    tessellator.draw();
    GlStateManager.shadeModel(7424);
    GlStateManager.enableAlpha();
    GlStateManager.disableBlend();
  }

  public void drawGradientShadowRight(double x, double top, double bottom) {
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    int i1 = 4;
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
    GlStateManager.disableAlpha();
    GlStateManager.shadeModel(7425);
    GlStateManager.enableTexture2D();
    this.mc.getTextureManager().bindTexture(optionsBackground);
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    worldrenderer.pos(x, bottom, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
    worldrenderer.pos(x, top, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
    worldrenderer.pos(x - i1, top, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
    worldrenderer.pos(x - i1, bottom, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
    tessellator.draw();
    GlStateManager.shadeModel(7424);
    GlStateManager.enableAlpha();
    GlStateManager.disableBlend();
  }

  public void drawIngameBackground() {
    drawGradientRect(0, 0, getWidth(), getHeight(), -1072689136, -804253680);
  }

  public void drawAutoDimmedBackground(double d) {
    if (Minecraft.getMinecraft().playerController != null) {
      drawIngameBackground();
    } else {
      drawDimmedBackground((int)d);
    }
  }

  public void drawAutoDimmedBackground(int left, int top, int right, int bottom) {
    if (Minecraft.getMinecraft().playerController != null) {
      drawGradientRect(left, top, right, bottom, -1072689136, -804253680);
    } else {
      drawDimmedOverlayBackground(left, top, right, bottom);
    }
  }

  public void drawBackground(int tint) {
    drawBackground(tint, 0.0D, 64);
  }

  public void drawDimmedBackground(int scroll) {
    drawBackground(0, -scroll, 32);
  }

  public void drawBackground(int tint, double scrolling, int brightness) {
    GlStateManager.disableLighting();
    GlStateManager.disableFog();
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    this.mc.getTextureManager().bindTexture(optionsBackground);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    worldrenderer.pos(0.0D, getHeight(), 0.0D).tex(0.0D, (getHeight() + scrolling) / 32.0D + tint).color(brightness, brightness, brightness, 255).endVertex();
    worldrenderer.pos(getWidth(), getHeight(), 0.0D)
        .tex((getWidth() / 32.0F), (getHeight() + scrolling) / 32.0D + tint)
        .color(brightness, brightness, brightness, 255)
        .endVertex();
    worldrenderer.pos(getWidth(), 0.0D, 0.0D).tex((getWidth() / 32.0F), tint + scrolling / 32.0D).color(brightness, brightness, brightness, 255).endVertex();
    worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, tint + scrolling / 32.0D).color(brightness, brightness, brightness, 255).endVertex();
    tessellator.draw();
  }

  public void drawOverlayBackground(int startY, int endY) {
    int endAlpha = 255;
    int startAlpha = 255;
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    this.mc.getTextureManager().bindTexture(optionsBackground);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    worldrenderer.pos(0.0D, endY, 0.0D).tex(0.0D, (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
    worldrenderer.pos((0 + getWidth()), endY, 0.0D).tex((getWidth() / 32.0F), (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
    worldrenderer.pos((0 + getWidth()), startY, 0.0D).tex((getWidth() / 32.0F), (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
    worldrenderer.pos(0.0D, startY, 0.0D).tex(0.0D, (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
    tessellator.draw();
  }

  public void drawDimmedOverlayBackground(int left, int top, int right, int bottom) {
    GlStateManager.disableLighting();
    GlStateManager.disableFog();
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    this.mc.getTextureManager().bindTexture(optionsBackground);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    float f = 32.0F;
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    worldrenderer.pos(left, bottom, 0.0D).tex((left / f), (bottom / f)).color(32, 32, 32, 255).endVertex();
    worldrenderer.pos(right, bottom, 0.0D).tex((right / f), (bottom / f)).color(32, 32, 32, 255).endVertex();
    worldrenderer.pos(right, top, 0.0D).tex((right / f), (top / f)).color(32, 32, 32, 255).endVertex();
    worldrenderer.pos(left, top, 0.0D).tex((left / f), (top / f)).color(32, 32, 32, 255).endVertex();
    tessellator.draw();
    GlStateManager.enableBlend();
    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
    GlStateManager.disableAlpha();
    GlStateManager.shadeModel(7425);
    GlStateManager.disableDepth();
  }

  public void drawOverlayBackground(int startX, int startY, int width, int endY) {
    drawOverlayBackground(startX, startY, width, endY, 64);
  }

  public void drawOverlayBackground(int startX, int startY, int width, int endY, int brightness) {
    int endAlpha = 255;
    int startAlpha = 255;
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    this.mc.getTextureManager().bindTexture(optionsBackground);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
    worldrenderer.pos(startX, endY, 0.0D).tex(0.0D, (endY / 32.0F)).color(brightness, brightness, brightness, endAlpha).endVertex();
    worldrenderer.pos((startX + width), endY, 0.0D).tex((width / 32.0F), (endY / 32.0F)).color(brightness, brightness, brightness, endAlpha).endVertex();
    worldrenderer.pos((startX + width), startY, 0.0D).tex((width / 32.0F), (startY / 32.0F)).color(brightness, brightness, brightness, startAlpha).endVertex();
    worldrenderer.pos(startX, startY, 0.0D).tex(0.0D, (startY / 32.0F)).color(brightness, brightness, brightness, startAlpha).endVertex();
    tessellator.draw();
  }

  public void drawTexturedModalRect(double x, double y, double textureX, double textureY, double width, double height) {
    drawTexturedModalRect((int)x, (int)y, (int)textureX, (int)textureY, (int)width, (int)height);
  }

  public void drawTexturedModalRect(double left, double top, double right, double bottom) {
    double textureX = 0.0D;
    double textureY = 0.0D;
    double x = left;
    double y = top;
    double width = right - left;
    double height = bottom - top;
    float f = 0.00390625F;
    float f1 = 0.00390625F;
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
    worldrenderer.pos(x + 0.0D, y + height, this.zLevel).tex(((float)(textureX + 0.0D) * f), ((float)(textureY + height) * f1)).endVertex();
    worldrenderer.pos(x + width, y + height, this.zLevel).tex(((float)(textureX + width) * f), ((float)(textureY + height) * f1)).endVertex();
    worldrenderer.pos(x + width, y + 0.0D, this.zLevel).tex(((float)(textureX + width) * f), ((float)(textureY + 0.0D) * f1)).endVertex();
    worldrenderer.pos(x + 0.0D, y + 0.0D, this.zLevel).tex(((float)(textureX + 0.0D) * f), ((float)(textureY + 0.0D) * f1)).endVertex();
    tessellator.draw();
  }

  public void drawTexture(double x, double y, double imageWidth, double imageHeight, double maxWidth, double maxHeight, float alpha) {
    GL11.glPushMatrix();
    double sizeWidth = maxWidth / imageWidth;
    double sizeHeight = maxHeight / imageHeight;
    GL11.glScaled(sizeWidth, sizeHeight, 0.0D);
    if (alpha <= 1.0F) {
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
    }
    drawTexturedModalRect(x / sizeWidth, y / sizeHeight, x / sizeWidth + imageWidth, y / sizeHeight + imageHeight);
    if (alpha <= 1.0F) {
      GlStateManager.disableAlpha();
      GlStateManager.disableBlend();
    }
    GL11.glPopMatrix();
  }

  public void drawRawTexture(double x, double y, double imageWidth, double imageHeight, double maxWidth, double maxHeight) {
    GL11.glPushMatrix();
    double sizeWidth = maxWidth / imageWidth;
    double sizeHeight = maxHeight / imageHeight;
    GL11.glScaled(sizeWidth, sizeHeight, 0.0D);
    drawTexturedModalRect(x / sizeWidth, y / sizeHeight, x / sizeWidth + imageWidth, y / sizeHeight + imageHeight);
    GL11.glPopMatrix();
  }

  public void drawTexture(double x, double y, double imageWidth, double imageHeight, double maxWidth, double maxHeight) {
    drawTexture(x, y, imageWidth, imageHeight, maxWidth, maxHeight, 1.0F);
  }

  public void drawTexture(double x, double y, double texturePosX, double texturePosY, double imageWidth, double imageHeight, double maxWidth, double maxHeight, float alpha) {
    GL11.glPushMatrix();
    double sizeWidth = maxWidth / imageWidth;
    double sizeHeight = maxHeight / imageHeight;
    GL11.glScaled(sizeWidth, sizeHeight, 0.0D);
    if (alpha <= 1.0F) {
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
    }
    drawUVTexture(x / sizeWidth, y / sizeHeight, texturePosX, texturePosY, x / sizeWidth + imageWidth - x / sizeWidth, y / sizeHeight + imageHeight - y / sizeHeight);
    if (alpha <= 1.0F) {
      GlStateManager.disableAlpha();
      GlStateManager.disableBlend();
    }
    GL11.glPopMatrix();
  }

  public void drawTexture(double x, double y, double texturePosX, double texturePosY, double imageWidth, double imageHeight, double maxWidth, double maxHeight) {
    drawTexture(x, y, texturePosX, texturePosY, imageWidth, imageHeight, maxWidth, maxHeight, 1.0F);
  }

  private void drawUVTexture(double x, double y, double textureX, double textureY, double width, double height) {
    float f = 0.00390625F;
    float f1 = 0.00390625F;
    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
    worldrenderer.pos(x + 0.0D, y + height, this.zLevel).tex(((float)(textureX + 0.0D) * f), ((float)(textureY + height) * f1)).endVertex();
    worldrenderer.pos(x + width, y + height, this.zLevel).tex(((float)(textureX + width) * f), ((float)(textureY + height) * f1)).endVertex();
    worldrenderer.pos(x + width, y + 0.0D, this.zLevel).tex(((float)(textureX + width) * f), ((float)(textureY + 0.0D) * f1)).endVertex();
    worldrenderer.pos(x + 0.0D, y + 0.0D, this.zLevel).tex(((float)(textureX + 0.0D) * f), ((float)(textureY + 0.0D) * f1)).endVertex();
    tessellator.draw();
  }

  public static void drawEntityOnScreen(int x, int y, int size, float mouseX, float mouseY, int rotationX, int rotationY, int rotationZ, EntityLivingBase entity) {
    GlStateManager.enableColorMaterial();
    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, 100.0F);
    GlStateManager.scale(-size - 30.0F, size + 30.0F, size);
    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
    float var6 = entity.renderYawOffset;
    float var7 = entity.rotationYaw;
    float var8 = entity.rotationPitch;
    float var9 = entity.prevRotationYawHead;
    float var10 = entity.rotationYawHead;
    GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
    RenderHelper.enableStandardItemLighting();
    GlStateManager.rotate(-135.0F + rotationX, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(rotationY, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(rotationZ, 0.0F, 0.0F, 1.0F);
    GlStateManager.rotate(-((float)Math.atan((mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
    entity.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
    entity.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
    entity.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
    entity.rotationYawHead = entity.rotationYaw;
    entity.prevRotationYawHead = entity.rotationYaw;
    GlStateManager.translate(0.0F, 0.0F, 0.0F);
    RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
    var11.setPlayerViewY(180.0F);
    var11.setRenderShadow(false);
    var11.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
    var11.setRenderShadow(true);
    entity.renderYawOffset = var6;
    entity.rotationYaw = var7;
    entity.rotationPitch = var8;
    entity.prevRotationYawHead = var9;
    entity.rotationYawHead = var10;
    GlStateManager.popMatrix();
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableRescaleNormal();
    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    GlStateManager.disableTexture2D();
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
  }

  public String trimStringToWidth(String text, int width) {
    if (text == null)
      return text;
    return this.fontRenderer.trimStringToWidth(text, width, false);
  }

  public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
    if (wrapWidth < 10)
      wrapWidth = 10;
    return this.fontRenderer.listFormattedStringToWidth(str, wrapWidth);
  }

  public List<String> listFormattedStringToWidth(String str, int wrapWidth, int maxLines) {
    List<String> list = listFormattedStringToWidth(str, wrapWidth);
    if (list.size() < maxLines)
      return list;
    List<String> output = new ArrayList<>();
    int count = 0;
    for (String line : list) {
      count++;
      output.add(line);
      if (count >= maxLines)
        break;
    }
    return output;
  }

  public void drawHoveringText(int x, int y, String... textLines) {
    if (textLines.length != 0) {
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      int i = 0;
      for (String s : textLines) {
        int j = this.fontRenderer.getStringWidth(s);
        if (j > i)
          i = j;
      }
      int l1 = x + 7;
      int i2 = y - 12;
      int k = 8;
      if (textLines.length > 1)
        k += 2 + (textLines.length - 1) * 10;
      if (i2 < 5)
        i2 = 5;
      if (l1 + i > getWidth())
        l1 -= 12 + i;
      if (i2 + k + 6 > getHeight())
        i2 = getHeight() - k - 6;
      this.zLevel = 300.0F;
      int l = -267386864;
      drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, l, l);
      drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, l, l);
      drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, l, l);
      drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, l, l);
      drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, l, l);
      int i1 = 1347420415;
      int j1 = (i1 & 0xFEFEFE) >> 1 | i1 & 0xFF000000;
      drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i1, j1);
      drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i1, j1);
      drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i1, i1);
      drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j1, j1);
      for (int k1 = 0; k1 < textLines.length; k1++) {
        String s1 = textLines[k1];
        this.fontRenderer.drawStringWithShadow(s1, l1, i2, -1);
        if (k1 == 0)
          i2 += 2;
        i2 += 10;
      }
      this.zLevel = 0.0F;
    }
  }

  public void drawHoveringTextBoxField(int x, int y, int width, int height) {
    this.zLevel = 300.0F;
    int color1 = -267386864;
    drawGradientRect(x - 3, y - 4, x + width + 3, y - 3, color1, color1);
    drawGradientRect(x - 3, y + height + 3, x + width + 3, y + height + 4, color1, color1);
    drawGradientRect(x - 3, y - 3, x + width + 3, y + height + 3, color1, color1);
    drawGradientRect(x - 4, y - 3, x - 3, y + height + 3, color1, color1);
    drawGradientRect(x + width + 3, y - 3, x + width + 4, y + height + 3, color1, color1);
    int color2 = 1347420415;
    int color3 = (color2 & 0xFEFEFE) >> 1 | color2 & 0xFF000000;
    drawGradientRect(x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, color2, color3);
    drawGradientRect(x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, color2, color3);
    drawGradientRect(x - 3, y - 3, x + width + 3, y - 3 + 1, color2, color2);
    drawGradientRect(x - 3, y + height + 2, x + width + 3, y + height + 3, color3, color3);
    this.zLevel = 0.0F;
    GlStateManager.disableDepth();
  }

  public void drawPlayerHead(ResourceLocation resourceLocation, int x, int y, int size) {
    if (resourceLocation == null)
      resourceLocation = DefaultPlayerSkin.getDefaultSkin(UUID.randomUUID());
    GlStateManager.enableAlpha();
    Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
    drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, size, size, 64.0F, 64.0F);
    drawScaledCustomSizeModalRect(x, y, 40.0F, 8.0F, 8, 8, size, size, 64.0F, 64.0F);
  }

  public void drawRectBorder(double left, double top, double right, double bottom, int color, double thickness) {
    drawRect(left + thickness, top, right - thickness, top + thickness, color);
    drawRect(right - thickness, top, right, bottom, color);
    drawRect(left + thickness, bottom - thickness, right - thickness, bottom, color);
    drawRect(left, top, left + thickness, bottom, color);
  }

  public void renderSkull(GameProfile gameProfile) {
    ModelSkeletonHead modelbase = this.humanoidHead;
    ResourceLocation f = DefaultPlayerSkin.getDefaultSkinLegacy();
    if (gameProfile != null) {
      Minecraft minecraft = Minecraft.getMinecraft();
      Map<?, ?> map = minecraft.getSkinManager().loadSkinFromCache(gameProfile);
      if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
        f = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
      } else {
        UUID uuid = EntityPlayer.getUUID(gameProfile);
        f = DefaultPlayerSkin.getDefaultSkin(uuid);
      }
    }
    Minecraft.getMinecraft().getTextureManager().bindTexture(f);
    GlStateManager.pushMatrix();
    GlStateManager.disableCull();
    GlStateManager.enableRescaleNormal();
    GlStateManager.enableAlpha();
    GlStateManager.scale(-1.0F, 1.0F, 1.0F);
    GlStateManager.translate(0.0F, 0.2F, 0.0F);
    modelbase.render(null, 0.0F, 0.0F, 0.0F, 180.0F, 0.0F, 0.0625F);
    GlStateManager.popMatrix();
  }
}
