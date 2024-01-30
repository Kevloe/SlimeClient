package me.slime.render;

import java.lang.reflect.Field;
import java.util.Map;
import me.slime.util.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

/**
 * Created by Kevloe on 10.04.2020.
 */

public class RenderPlayerHook {

  public RenderPlayerHook(){
    try {
      RenderPlayerImplementation renderPlayerImplementation = new RenderPlayerImplementation();
      Field skinMapField = ReflectionHelper
          .findField(RenderManager.class, renderPlayerImplementation.getSkinMapNames());
      Map<String, RenderPlayer> skinMap = (Map<String, RenderPlayer>)skinMapField.get(Minecraft.getMinecraft().getRenderManager());
      skinMap.put("default", renderPlayerImplementation.getRenderPlayer(Minecraft.getMinecraft().getRenderManager(), false));
      skinMap.put("slim", renderPlayerImplementation.getRenderPlayer(Minecraft.getMinecraft().getRenderManager(), true));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static abstract class RenderPlayerCustom extends RenderPlayer{
    public RenderPlayerCustom(RenderManager renderManager, boolean slim){
      super(renderManager, slim);
      this.layerRenderers.clear();
      for (LayerRenderer layer : new RenderPlayerImplementation().getLayerRenderers(this))
        addLayer(layer);
    }
    public abstract boolean canRenderTheName(AbstractClientPlayer param1AbstractClientPlayer);

    public abstract void renderLabel(AbstractClientPlayer param1AbstractClientPlayer, double param1Double1, double param1Double2, double param1Double3, String param1String, float param1Float, double param1Double4);
  }
}
