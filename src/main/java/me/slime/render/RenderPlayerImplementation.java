package me.slime.render;

import me.slime.user.group.Group;
import me.slime.util.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import org.lwjgl.opengl.GL11;

/**
 * Created by Kevloe on 10.04.2020.
 */

public class RenderPlayerImplementation {

  public String[] getSkinMapNames() {
    return new String[] { "skinMap", "l", "field_178636_l" };
  }

  public LayerRenderer[] getLayerRenderers(RenderPlayer renderPlayer) {
    return new LayerRenderer[] {(LayerRenderer)new LayerBipedArmorCustom((RendererLivingEntity) renderPlayer),(LayerRenderer)new LayerHeldItemCustom((RendererLivingEntity)renderPlayer), (LayerRenderer)new LayerArrowCustom((RendererLivingEntity)renderPlayer),(LayerRenderer)new LayerDeadmau5Head(renderPlayer), (LayerRenderer)new LayerCustomHead((renderPlayer.getMainModel()).bipedHead) };
  }

  public void renderName(RenderPlayerHook.RenderPlayerCustom renderPlayer, AbstractClientPlayer entity, double x, double y, double z) {
    boolean canRender = (Minecraft.isGuiEnabled() && !entity.isInvisibleToPlayer((EntityPlayer) (Minecraft.getMinecraft()).thePlayer) && entity.riddenByEntity == null);
    if ((entity == (renderPlayer.getRenderManager()).livingPlayer && canRender)) {
      double distance = entity.getDistanceSqToEntity((renderPlayer.getRenderManager()).livingPlayer);
      float f = entity.isSneaking() ? 32.0F : 64.0F;
      if (distance < (f * f)) {
        float maxNameTagHeight = 1.0F;
        String username = entity.getDisplayName().getFormattedText();
        GlStateManager.alphaFunc(516, 0.1F);
        float fixedPlayerViewX = (renderPlayer.getRenderManager()).playerViewX * (((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2) ? -1 : 2);
        y += maxNameTagHeight;
        FontRenderer fontrenderer = renderPlayer.getFontRendererFromRenderManager();
        if (entity.isSneaking()) {
          GlStateManager.pushMatrix();
          GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (entity.height / 2.0F), (float)z);
          GL11.glNormal3f(0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(-(renderPlayer.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(fixedPlayerViewX, 1.0F, 0.0F, 0.0F);
          GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
          GlStateManager.translate(0.0F, 9.374999F, 0.0F);
          GlStateManager.disableLighting();
          GlStateManager.depthMask(false);
          GlStateManager.enableBlend();
          GlStateManager.disableTexture2D();
          GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
          int i = fontrenderer.getStringWidth(username) / 2;
          Tessellator tessellator = Tessellator.getInstance();
          WorldRenderer worldrenderer = tessellator.getWorldRenderer();
          worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
          worldrenderer.pos((-i - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
          worldrenderer.pos((-i - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
          worldrenderer.pos((i + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
          worldrenderer.pos((i + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
          tessellator.draw();
          GlStateManager.enableTexture2D();
          GlStateManager.depthMask(true);
          fontrenderer.drawString(username, -fontrenderer.getStringWidth(username) / 2, 0, 553648127);
          GlStateManager.enableLighting();
          GlStateManager.disableBlend();
          GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          GlStateManager.popMatrix();
        } else {
          if (distance < 100.0D) {
            Scoreboard scoreboard = entity.getWorldScoreboard();
            ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
            if (scoreobjective != null) {
              Score score = scoreboard.getValueFromObjective(entity.getName(), scoreobjective);
              renderLivingLabelCustom(renderPlayer, (Entity)entity, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z, 64);
              y += ((new DrawUtils().getFontRenderer()).FONT_HEIGHT * 1.15F * 0.02666667F);
            }
          }
          renderLivingLabelCustom(renderPlayer, (Entity)entity, username, x, y - (entity.height / 2.0F), z, 64);
          GlStateManager.pushMatrix();
          double size = 0.5D;
          GlStateManager.scale(size, size, size);
          GlStateManager.translate(0.0D, 2.0D, 0.0D);
          renderLivingLabelCustom(renderPlayer, (Entity)entity, new Group(0, "administrator","Administrator", "","4","ADMIN").getDisplayTag(), x / size, (y - (entity.height / 2.0F) + 0.3D) / size, z / size, 10);
          GlStateManager.popMatrix();
        }
      }
    }
  }

  protected void renderLivingLabelCustom(RenderPlayer renderPlayer, Entity entityIn, String str, double x, double y, double z, int maxDistance) {
    double d0 = entityIn.getDistanceSqToEntity((renderPlayer.getRenderManager()).livingPlayer);
    if (d0 <= (maxDistance * maxDistance)) {
      float fixedPlayerViewX = (renderPlayer.getRenderManager()).playerViewX * (((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2) ? -1 : 2);
      FontRenderer fontrenderer = renderPlayer.getFontRendererFromRenderManager();
      float f1 = 0.016666668F * 1.6F;
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x + 0.0F, (float)y + entityIn.height + 0.5F, (float)z);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-(renderPlayer.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(fixedPlayerViewX, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(-f1, -f1, f1);
      GlStateManager.disableLighting();
      GlStateManager.depthMask(false);
      GlStateManager.disableDepth();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      int i = 0;
      if (str.equals("deadmau5"))
        i = -10;
      int j = fontrenderer.getStringWidth(str) / 2;
      GlStateManager.disableTexture2D();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      worldrenderer.pos((-j - 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
      worldrenderer.pos((-j - 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
      worldrenderer.pos((j + 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
      worldrenderer.pos((j + 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
      GlStateManager.enableDepth();
      GlStateManager.depthMask(true);
      fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
      GlStateManager.enableLighting();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
    }
  }
  public RenderPlayerHook.RenderPlayerCustom getRenderPlayer(RenderManager renderManager, boolean slim) {
    return new RenderPlayerHook.RenderPlayerCustom(renderManager, slim) {
      public boolean canRenderTheName(AbstractClientPlayer entity) {
        return canRenderName(entity);
      }

      public void renderLabel(AbstractClientPlayer entityIn, double x, double y, double z, String string, float height, double distance) {
        renderOffsetLivingLabel(entityIn, x, y, z, string, height, distance);
      }

      public void renderName(AbstractClientPlayer entity, double x, double y, double z) {
        new RenderPlayerImplementation().renderName(this, entity, x, y, z);
      }
    };
  }
}
