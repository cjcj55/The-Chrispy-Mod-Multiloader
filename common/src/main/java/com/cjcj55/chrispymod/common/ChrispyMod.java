package com.cjcj55.chrispymod.common;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChrispyMod {
    public static final String MOD_ID = "chrispymod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    /**
     * Non-registry initialization shared by both loaders. Registry content is registered separately through
     * {@code ModBlocks.register()} then {@code ModItems.register()}, because registries are only open at
     * different moments on each loader.
     */
    public static void initialize() {
        LOGGER.info("Initialized {}", MOD_ID);
    }
}
