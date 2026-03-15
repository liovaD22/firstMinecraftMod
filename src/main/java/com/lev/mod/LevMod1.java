package com.lev.mod;

import com.lev.mod.entity.ModEntities;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevMod1 implements ModInitializer {

    public static final String MOD_ID = "lev-mod-1";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("LevMod1 is loading!");
        ModEntities.init();
    }
}
