package com.cjcj55.chrispymod.fabric;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.menu.AlloyFurnaceMenu;
import com.cjcj55.chrispymod.common.menu.ModMenuTypes;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;

/** Fabric's {@code ExtendedMenuType} is how a mod builds a {@code MenuType} with vanilla's private constructor. */
public final class FabricMenuTypes {
    private FabricMenuTypes() {
    }

    public static void register() {
        ExtendedMenuType<AlloyFurnaceMenu, Unit> type = new ExtendedMenuType<>(
                (id, inventory, unit) -> new AlloyFurnaceMenu(id, inventory), Unit.STREAM_CODEC);
        Registry.register(BuiltInRegistries.MENU, ChrispyMod.id("alloy_furnace"), type);
        ModMenuTypes.ALLOY_FURNACE = type;

        // Fabric's ExtendedMenuType requires the provider passed to Player.openMenu to implement
        // ExtendedMenuProvider, unlike NeoForge's IMenuTypeExtension. Common's providers (block entities) can't
        // implement that Fabric-only interface directly, so wrap them here instead.
        ModMenuTypes.OPEN = (player, provider) -> player.openMenu(new UnitMenuProvider(provider));
    }

    private record UnitMenuProvider(MenuProvider delegate) implements ExtendedMenuProvider<Unit> {
        @Override
        public Unit getScreenOpeningData(ServerPlayer player) {
            return Unit.INSTANCE;
        }

        @Override
        public Component getDisplayName() {
            return delegate.getDisplayName();
        }

        @Override
        public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
            return delegate.createMenu(containerId, inventory, player);
        }
    }
}
