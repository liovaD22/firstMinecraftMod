package com.lev.mod.entity;

import com.lev.mod.LevMod1;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Pig;

public class ModEntities {

    public static final EntityType<GlowingPigEntity> GLOWING_PIG = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(LevMod1.MOD_ID, "glowing_pig"),
            EntityType.Builder.of(GlowingPigEntity::new, MobCategory.CREATURE)
                    .sized(0.9F, 0.9F)
                    .build("glowing_pig")
    );

    public static void init() {
        FabricDefaultAttributeRegistry.register(GLOWING_PIG, Pig.createAttributes());
    }
}
