package com.cjcj55.chrispymod.common.menu;

import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

import java.util.function.BiConsumer;

/**
 * Vanilla's {@code MenuType} constructor is private, so a plain {@code Registry.register} call like the rest of
 * this mod's registries can't build one. Each loader constructs the type with its own helper
 * (NeoForge's {@code IMenuTypeExtension}, Fabric's {@code ExtendedMenuType}) and assigns it here during its own
 * registration step, before {@link AlloyFurnaceMenu} instances are created.
 */
public final class ModMenuTypes {
    private ModMenuTypes() {
    }

    public static MenuType<AlloyFurnaceMenu> ALLOY_FURNACE;

    /**
     * How to actually open a menu, so common code never has to call {@link Player#openMenu} directly. Defaults to
     * vanilla's own behaviour, which NeoForge's {@code IMenuTypeExtension} menus are happy with. Fabric's
     * {@code ExtendedMenuType} instead requires the opened {@link MenuProvider} to implement its
     * {@code ExtendedMenuProvider}, a Fabric-only interface common can't implement directly, so
     * {@code FabricMenuTypes} overrides this to wrap the provider first.
     */
    public static BiConsumer<Player, MenuProvider> OPEN = Player::openMenu;
}
