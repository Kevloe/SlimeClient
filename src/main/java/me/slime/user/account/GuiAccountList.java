package me.slime.user.account;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

import java.io.IOException;

public class GuiAccountList extends GuiScreen {

    private GuiAccountList.List list;

    public GuiAccountList(GuiMainMenu guiMainMenu) {
        super();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

    public void List() {

    }
    public class List extends GuiSlot {

        public List(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
            super(mcIn, width, height, topIn, bottomIn, slotHeightIn);
        }

        @Override
        protected int getSize() {
            return 0;
        }

        @Override
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {

        }

        @Override
        protected boolean isSelected(int slotIndex) {
            return false;
        }

        @Override
        protected void drawBackground() {

        }

        @Override
        protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {

        }
    }

}
