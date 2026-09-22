package com.cjcj55.chrispymod.common.client;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.menu.AlloyFurnaceMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

/**
 * The background panel comes from {@code textures/gui/alloy_furnace_gui.png}, at its top-left 176x166 corner
 * (the standard furnace-style GUI canvas size, on a 256x256 texture). The progress arrow and fuel flame are drawn
 * as plain bars rather than sprites from that texture, since its exact overlay-sprite layout isn't known here.
 */
public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceMenu> {
    private static final Identifier TEXTURE = ChrispyMod.id("textures/gui/alloy_furnace_gui.png");
    private static final int PROGRESS_ARROW_X = 92;
    private static final int PROGRESS_ARROW_Y = 34;
    private static final int PROGRESS_ARROW_WIDTH = 26;
    private static final int PROGRESS_ARROW_HEIGHT = 16;
    private static final int FUEL_X = 20;
    private static final int FUEL_Y = 36;
    private static final int FUEL_WIDTH = 14;
    private static final int FUEL_HEIGHT = 14;

    public AlloyFurnaceScreen(AlloyFurnaceMenu menu, Inventory inventory, net.minecraft.network.chat.Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0f, 0f, imageWidth, imageHeight, 256, 256);
        drawProgressArrow(guiGraphics);
        drawFuelIndicator(guiGraphics);
        super.extractContents(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void drawProgressArrow(GuiGraphicsExtractor guiGraphics) {
        int filled = menu.getScaledProgress();
        if (filled <= 0) {
            return;
        }
        int x = leftPos + PROGRESS_ARROW_X;
        int y = topPos + PROGRESS_ARROW_Y;
        guiGraphics.fill(x, y, x + Math.min(filled, PROGRESS_ARROW_WIDTH), y + PROGRESS_ARROW_HEIGHT, 0xFFB0B0B0);
    }

    private void drawFuelIndicator(GuiGraphicsExtractor guiGraphics) {
        int filled = menu.getScaledFuel();
        if (filled <= 0) {
            return;
        }
        int x = leftPos + FUEL_X;
        int bottom = topPos + FUEL_Y + FUEL_HEIGHT;
        int height = Math.min(filled, FUEL_HEIGHT);
        guiGraphics.fill(x, bottom - height, x + FUEL_WIDTH, bottom, 0xFFFF8000);
    }
}
