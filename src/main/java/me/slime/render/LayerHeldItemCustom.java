package me.slime.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Kevloe on 11.04.2020.
 */

public class LayerHeldItemCustom extends LayerHeldItem {

  private RendererLivingEntity<?> livingEntityRenderer;

  public LayerHeldItemCustom(RendererLivingEntity<?> livingEntityRendererIn) {
    super(livingEntityRendererIn);
    this.livingEntityRenderer = livingEntityRendererIn;
  }

  public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
    ItemStack itemstack = entitylivingbaseIn.getHeldItem();
    if (itemstack != null) {
      GlStateManager.pushMatrix();
      if ((this.livingEntityRenderer.getMainModel()).isChild) {
        float f = 0.5F;
        GlStateManager.translate(0.0F, 0.625F, 0.0F);
        GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
        GlStateManager.scale(f, f, f);
      }
      Item item = itemstack.getItem();
      boolean tool = (item instanceof net.minecraft.item.ItemSword || item instanceof net.minecraft.item.ItemHoe || item instanceof net.minecraft.item.ItemAxe || item instanceof net.minecraft.item.ItemPickaxe || item instanceof net.minecraft.item.ItemSpade);
      if (tool && true) {
        if (Minecraft.getMinecraft().thePlayer.isUsingItem() && Minecraft.getMinecraft().thePlayer.getItemInUse().getItem().getItemUseAction(Minecraft.getMinecraft().thePlayer.getItemInUse()) == EnumAction.BLOCK) {
          postRenderArm(entitylivingbaseIn, 0.07F);
          GlStateManager.rotate(70.0F, 0.0F, 0.0F, -1.0F);
          GlStateManager.rotate(35.0F, 0.0F, -1.0F, 0.0F);
          GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.scale(1.15F, 1.15F, 1.15F);
          if (entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(-0.38D, 0.06D, 0.36D);
          } else {
            GlStateManager.translate(-0.32D, 0.15D, 0.26D);
          }
        } else {
          postRenderArm(entitylivingbaseIn, 0.0625F);
          GlStateManager.scale(1.1F, 1.1F, 1.1F);
          GlStateManager.translate(-0.1F, 0.445F, 0.169F);
          GlStateManager.rotate(18.5F, -1.0F, 0.0F, 0.0F);
        }
      } else if (true && true && !(item instanceof net.minecraft.item.ItemBlock)) {
        postRenderArm(entitylivingbaseIn, 0.0625F);
        GlStateManager.scale(-1.0F, 1.0F, 1.0F);
        GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
        if (item instanceof net.minecraft.item.ItemBow) {
          GlStateManager.translate(-0.1F, 0.0F, -0.05F);
          GlStateManager.rotate(10.0F, -1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(15.0F, 0.0F, 1.0F, 0.0F);
          GlStateManager.rotate(15.0F, 0.0F, 1.0F, 0.0F);
        } else if (item instanceof net.minecraft.item.ItemFishingRod || item instanceof net.minecraft.item.ItemCarrotOnAStick) {
          GlStateManager.translate(0.08F, -0.07F, 0.07F);
          GlStateManager.scale(1.1D, 1.1D, 1.1D);
          GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
          GlStateManager.rotate(5.0F, 0.0F, 0.0F, -1.0F);
        } else {
          GlStateManager.translate(0.06F, 0.01F, -0.0F);
          GlStateManager.rotate(10.0F, -1.0F, 1.0F, -2.0F);
        }
      } else {
        postRenderArm(entitylivingbaseIn, 0.0625F);
        GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
      }
      if (entitylivingbaseIn instanceof EntityPlayer
          && ((EntityPlayer)entitylivingbaseIn).fishEntity != null)
        itemstack = new ItemStack((Item) Items.fishing_rod, 0);
      Minecraft minecraft = Minecraft.getMinecraft();
      if (item instanceof net.minecraft.item.ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2) {
        GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
        GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f1 = 0.375F;
        GlStateManager.scale(-f1, -f1, f1);
      }
      if (entitylivingbaseIn.isSneaking())
        GlStateManager.translate(0.0F, 0.203125F, 0.0F);
      minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON);
      GlStateManager.popMatrix();
    }
  }

  private void postRenderArm(EntityLivingBase entityLivingBase, float scale) {
    ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(scale);
  }
}
