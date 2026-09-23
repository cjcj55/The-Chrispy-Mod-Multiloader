package com.cjcj55.chrispymod.common.client;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.menu.AlloyFurnaceMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

/**
 * The background panel comes from {@code textures/gui/alloy_furnace_gui.png}, at its top-left 176x166 corner
 * (the standard furnace-style GUI canvas size, on a 256x256 texture). The texture already draws its own
 * "combine" arrow (a bracket converging the two input slots into one line, per pixel measurement of the actual
 * PNG: the shaft+head run from x=88 to x=109, y=36 to y=42) and a wisp of smoke above the fuel slot (x=19-31,
 * y=34-46), so the fuel indicator reuses vanilla's flame sprite centered on that smoke art, while the progress
 * indicator is a plain fill sized to the texture's own arrow shape rather than vanilla's differently-shaped
 * sprite (which doesn't match this texture's custom arrow at all).
 */
public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceMenu> {
    private static final Identifier TEXTURE = ChrispyMod.id("textures/gui/alloy_furnace_gui.png");
    private static final Identifier LIT_PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/furnace/lit_progress");
    private static final int PROGRESS_ARROW_X = 88;
    private static final int PROGRESS_ARROW_Y = 36;
    private static final int PROGRESS_ARROW_WIDTH = 22;
    private static final int PROGRESS_ARROW_HEIGHT = 7;
    private static final int PROGRESS_ARROW_COLOR = 0xFFFFCC33;
    private static final int FUEL_X = 18;
    private static final int FUEL_Y = 33;
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
        int width = Mth.ceil(menu.getBurnProgress() * PROGRESS_ARROW_WIDTH);
        if (width <= 0) {
            return;
        }
        int x = leftPos + PROGRESS_ARROW_X;
        int y = topPos + PROGRESS_ARROW_Y;
        guiGraphics.fill(x, y, x + width, y + PROGRESS_ARROW_HEIGHT, PROGRESS_ARROW_COLOR);
    }

    private void drawFuelIndicator(GuiGraphicsExtractor guiGraphics) {
        if (!menu.isLit()) {
            return;
        }
        int height = Mth.ceil(menu.getLitProgress() * (FUEL_HEIGHT - 1)) + 1;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITE,
                FUEL_WIDTH, FUEL_HEIGHT, 0, FUEL_HEIGHT - height,
                leftPos + FUEL_X, topPos + FUEL_Y + FUEL_HEIGHT - height,
                FUEL_WIDTH, height);
    }
}
