package com.cjcj55.chrispymod.common.block.entity;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.AlloyFurnaceBlock;
import com.cjcj55.chrispymod.common.menu.AlloyFurnaceMenu;
import com.cjcj55.chrispymod.common.recipe.AlloyFurnaceRecipe;
import com.cjcj55.chrispymod.common.recipe.AlloyFurnaceRecipeInput;
import com.cjcj55.chrispymod.common.recipe.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;

import java.util.Optional;

public class AlloyFurnaceBlockEntity extends BaseContainerBlockEntity {
    public static final int SLOT_FUEL = 0;
    public static final int SLOT_INPUT_FIRST = 1;
    public static final int SLOT_INPUT_SECOND = 2;
    public static final int SLOT_OUTPUT = 3;
    private static final int SLOT_COUNT = 4;

    private static final int PROGRESS_PER_TICK = 1;
    private static final int PROGRESS_LOST_WHEN_IDLE = 2;

    private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private int litTime;
    private int litDuration;
    private int progress;
    private int maxProgress = 300;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> litTime;
                case 1 -> litDuration;
                case 2 -> progress;
                case 3 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> litTime = value;
                case 1 -> litDuration = value;
                case 2 -> progress = value;
                case 3 -> maxProgress = value;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOY_FURNACE, pos, state);
    }

    public boolean isLit() {
        return litTime > 0;
    }

    public ContainerData data() {
        return data;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container." + ChrispyMod.MOD_ID + ".alloy_furnace");
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return switch (slot) {
            case SLOT_FUEL -> stack.has(DataComponents.COOKING_FUEL);
            case SLOT_OUTPUT -> false;
            default -> true;
        };
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new AlloyFurnaceMenu(containerId, inventory, this, data);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        Containers.dropContents(level, pos, getItems());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, items);
        litTime = input.getIntOr("lit_time", 0);
        litDuration = input.getIntOr("lit_duration", 0);
        progress = input.getIntOr("progress", 0);
        maxProgress = input.getIntOr("max_progress", 300);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putInt("lit_time", litTime);
        output.putInt("lit_duration", litDuration);
        output.putInt("progress", progress);
        output.putInt("max_progress", maxProgress);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlloyFurnaceBlockEntity furnace) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        boolean wasLit = furnace.isLit();
        boolean changed = false;

        if (furnace.litTime > 0) {
            furnace.litTime--;
        }

        RecipeHolder<AlloyFurnaceRecipe> recipe = furnace.currentRecipe(serverLevel);
        boolean canCraft = recipe != null && furnace.canCraft(recipe.value());

        if (furnace.litTime <= 0 && canCraft) {
            ItemStack fuel = furnace.items.get(SLOT_FUEL);
            int duration = burnDuration(serverLevel, fuel);
            if (duration > 0) {
                furnace.litTime = duration;
                furnace.litDuration = duration;
                fuel.shrink(1);
                changed = true;
            }
        }

        if (furnace.litTime > 0 && canCraft) {
            furnace.progress = Math.min(furnace.maxProgress, furnace.progress + PROGRESS_PER_TICK);
            if (furnace.progress >= furnace.maxProgress) {
                furnace.craft(recipe.value());
                furnace.progress = 0;
            }
            changed = true;
        } else if (furnace.progress > 0) {
            furnace.progress = Math.max(0, furnace.progress - PROGRESS_LOST_WHEN_IDLE);
            changed = true;
        }

        boolean isLit = furnace.isLit();
        if (isLit != wasLit) {
            level.setBlock(pos, state.setValue(AlloyFurnaceBlock.LIT, isLit), 3);
            changed = true;
        }
        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private RecipeHolder<AlloyFurnaceRecipe> currentRecipe(ServerLevel level) {
        AlloyFurnaceRecipeInput input = new AlloyFurnaceRecipeInput(items.get(SLOT_INPUT_FIRST), items.get(SLOT_INPUT_SECOND));
        return level.recipeAccess().getRecipeFor(ModRecipeTypes.ALLOY_FURNACE, input, level).orElse(null);
    }

    private boolean canCraft(AlloyFurnaceRecipe recipe) {
        if (items.get(SLOT_INPUT_FIRST).isEmpty() || items.get(SLOT_INPUT_SECOND).isEmpty()) {
            return false;
        }
        ItemStack result = recipe.output().create();
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) {
            return true;
        }
        return ItemStack.isSameItemSameComponents(output, result) && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void craft(AlloyFurnaceRecipe recipe) {
        ItemStack result = recipe.output().create();
        ItemStack output = items.get(SLOT_OUTPUT);
        if (output.isEmpty()) {
            items.set(SLOT_OUTPUT, result);
        } else {
            output.grow(result.getCount());
        }
        items.get(SLOT_INPUT_FIRST).shrink(1);
        items.get(SLOT_INPUT_SECOND).shrink(1);
    }

    /** Reuses vanilla's own furnace-fuel component, so any vanilla or modded fuel (coal, blaze rods, Hellfire, ...) works. */
    private static int burnDuration(ServerLevel level, ItemStack fuel) {
        if (fuel.isEmpty() || !fuel.has(DataComponents.COOKING_FUEL)) {
            return 0;
        }
        LootParams params = new LootParams.Builder(level).create(LootContextParamSets.EMPTY);
        LootContext context = new LootContext.Builder(params).create(Optional.empty());
        return ResolvableInt.getFromItem(fuel, DataComponents.COOKING_FUEL, CookingFuel::burnTime, context, 0);
    }
}
