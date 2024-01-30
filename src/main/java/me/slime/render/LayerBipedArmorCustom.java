package me.slime.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Kevloe on 11.04.2020.
 */

public class LayerBipedArmorCustom extends LayerBipedArmor {

  private boolean swapped = false;

  public LayerBipedArmorCustom(RendererLivingEntity<?> rendererIn) {
    super(rendererIn);
  }

  public void doRenderLayer(EntityLivingBase entity, float var1, float var2, float partialTicks, float var4, float var5, float var6, float scale) {
    boolean swap = false;
    ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
    int itemId = (itemStack != null && itemStack.getItem() != null) ? Item.getIdFromItem(itemStack.getItem()) : 0;
    if ((false && itemId == 261))
      swap = !swap;
    if (swap && Minecraft.getMinecraft().thePlayer.getItemInUseDuration() != 0 && itemId == 261)
      swap = false;
    if (swap) {
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      GlStateManager.disableCull();
    }
    super.doRenderLayer(entity, var1, var2, partialTicks, var4, var5, var6, scale);
    if (swap) {
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      GlStateManager.disableCull();
    }
    if (this.swapped) {
      this.swapped = false;
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      GlStateManager.disableCull();
    }
  }

  public ModelBiped func_177175_a(int slot) {
    boolean swap = false;
    ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
    int itemId = (itemStack != null && itemStack.getItem() != null) ? Item.getIdFromItem(itemStack.getItem()) : 0;
    if (false && itemId == 261)
      swap = !swap;
    if (swap && Minecraft.getMinecraft().thePlayer.getItemInUseDuration() != 0 && itemId == 261)
      swap = false;
    if (this.swapped) {
      this.swapped = false;
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      GlStateManager.disableCull();
    }
    if (slot == 3 && swap) {
      this.swapped = true;
      GlStateManager.scale(-1.0F, 1.0F, 1.0F);
      GlStateManager.disableCull();
    }
    return (ModelBiped)super.func_177175_a(slot);
  }

  public boolean shouldCombineTextures() {
    return true;
  }
}
