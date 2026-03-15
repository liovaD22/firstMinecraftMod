package com.lev.mod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;

public class GlowingPigEntity extends Pig {

    private BlockPos lastLightPos = null;

    public GlowingPigEntity(EntityType<? extends Pig> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isCurrentlyGlowing() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            BlockPos currentPos = blockPosition();
            if (lastLightPos == null || !lastLightPos.equals(currentPos)) {
                // Remove old light block
                if (lastLightPos != null && level().getBlockState(lastLightPos).is(Blocks.LIGHT)) {
                    level().setBlock(lastLightPos, Blocks.AIR.defaultBlockState(), 3);
                }
                // Place new light block if the space is air
                if (level().getBlockState(currentPos).isAir()) {
                    level().setBlock(currentPos,
                            Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 10), 3);
                    lastLightPos = currentPos;
                } else {
                    lastLightPos = null;
                }
            }
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!level().isClientSide() && lastLightPos != null
                && level().getBlockState(lastLightPos).is(Blocks.LIGHT)) {
            level().setBlock(lastLightPos, Blocks.AIR.defaultBlockState(), 3);
        }
        super.remove(reason);
    }
}
