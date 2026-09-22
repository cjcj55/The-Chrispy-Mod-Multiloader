package com.cjcj55.chrispymod.common.menu;

import com.cjcj55.chrispymod.common.block.entity.AlloyFurnaceBlockEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Slot count matches {@link AlloyFurnaceBlockEntity}. On the client it's opened with an empty placeholder
 * container (like vanilla's {@code FurnaceMenu}) that the server then fills in through the normal slot sync,
 * so opening the menu needs no loader-specific extra data.
 */
public class AlloyFurnaceMenu extends AbstractContainerMenu {
    private static final int HOTBAR_SLOTS = 9;
    private static final int PLAYER_INVENTORY_SLOTS = 27;
    private static final int VANILLA_SLOTS = HOTBAR_SLOTS + PLAYER_INVENTORY_SLOTS;
    private static final int FURNACE_SLOTS = 4;
    private static final int PLAYER_INVENTORY_START = FURNACE_SLOTS;

    private final Container container;
    private final ContainerData data;

    public AlloyFurnaceMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(FURNACE_SLOTS), new SimpleContainerData(4));
    }

    public AlloyFurnaceMenu(int containerId, Inventory inventory, Container container, ContainerData data) {
        super(ModMenuTypes.ALLOY_FURNACE, containerId);
        this.container = container;
        this.data = data;

        addSlot(new FuelSlot(container, AlloyFurnaceBlockEntity.SLOT_FUEL, 18, 50));
        addSlot(new Slot(container, AlloyFurnaceBlockEntity.SLOT_INPUT_FIRST, 66, 16));
        addSlot(new Slot(container, AlloyFurnaceBlockEntity.SLOT_INPUT_SECOND, 66, 50));
        addSlot(new OutputSlot(container, AlloyFurnaceBlockEntity.SLOT_OUTPUT, 114, 33));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 86 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, 8 + column * 18, 144));
        }

        addDataSlots(data);
    }

    public boolean isLit() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = data.get(2);
        int maxProgress = data.get(3);
        int arrowWidth = 26;
        return maxProgress != 0 && progress != 0 ? progress * arrowWidth / maxProgress : 0;
    }

    public int getScaledFuel() {
        int litTime = data.get(0);
        int litDuration = data.get(1);
        int fuelHeight = 14;
        return litDuration != 0 ? litTime * fuelHeight / litDuration : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copy = sourceStack.copy();

        if (index < FURNACE_SLOTS) {
            if (!moveItemStackTo(sourceStack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_START + VANILLA_SLOTS, true)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(sourceStack, 0, FURNACE_SLOTS, false)) {
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    private static final class FuelSlot extends Slot {
        FuelSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.has(DataComponents.COOKING_FUEL);
        }
    }

    private static final class OutputSlot extends Slot {
        OutputSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
}
