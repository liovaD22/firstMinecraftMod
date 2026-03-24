package com.lev.mod.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class RedSunMixin {

    private static final ResourceLocation SUN_LOCATION = ResourceLocation.withDefaultNamespace("textures/environment/sun.png");

    @Inject(
        method = "renderSky",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V",
            ordinal = 0
        )
    )
    private void tintSunRed(Matrix4f projectionMatrix, Matrix4f modelViewMatrix, float partialTick,
                            net.minecraft.client.Camera camera, boolean isFoggy, Runnable fogCallback,
                            CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    @Inject(
        method = "renderSky",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V",
            ordinal = 1
        )
    )
    private void drawSecondSunAndReset(Matrix4f projectionMatrix, Matrix4f modelViewMatrix, float partialTick,
                                       net.minecraft.client.Camera camera, boolean isFoggy, Runnable fogCallback,
                                       CallbackInfo ci) {
        // Draw a second sun at a different position in the sky
        float skyAngle = Minecraft.getInstance().level.getTimeOfDay(partialTick);

        // Build a matrix for the second sun: offset by 120 degrees along the sky arc
        Matrix4f matrix = new Matrix4f(modelViewMatrix);
        matrix.rotate((float) Math.toRadians(-90.0f), 0.0f, 1.0f, 0.0f);
        matrix.rotate((float) Math.toRadians(skyAngle * 360.0f + 120.0f), 1.0f, 0.0f, 0.0f);

        float k = 30.0f;
        RenderSystem.setShaderColor(1.0f, 0.3f, 0.0f, 1.0f); // Orange-red tint for second sun
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SUN_LOCATION);

        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.addVertex(matrix, -k, 100.0f, -k).setUv(0.0f, 0.0f);
        bufferBuilder.addVertex(matrix, k, 100.0f, -k).setUv(1.0f, 0.0f);
        bufferBuilder.addVertex(matrix, k, 100.0f, k).setUv(1.0f, 1.0f);
        bufferBuilder.addVertex(matrix, -k, 100.0f, k).setUv(0.0f, 1.0f);
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());

        // Reset color for moon
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
