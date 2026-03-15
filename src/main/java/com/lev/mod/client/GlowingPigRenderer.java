package com.lev.mod.client;

import com.lev.mod.entity.GlowingPigEntity;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class GlowingPigRenderer extends MobRenderer<GlowingPigEntity, PigModel<GlowingPigEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/pig/pig.png");

    public GlowingPigRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(ModelLayers.PIG)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(GlowingPigEntity entity) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(GlowingPigEntity entity, BlockPos pos) {
        return 10;
    }
}
