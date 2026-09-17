package com.cjcj55.chrispymod.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChrispyMod {
    public static final String MOD_ID = "chrispymod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void initialize() {
        LOGGER.info("Hello World!");
    }
}