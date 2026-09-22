package com.cjcj55.chrispymod.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/** The lava-saturated sponge. It has no special behavior of its own; smelting it turns it back into {@link LavaSpongeBlock}. */
public class WetLavaSpongeBlock extends Block {
    public WetLavaSpongeBlock(Properties properties) {
        super(properties);
    }

    /** Drips lava from whichever face is exposed to air, mirroring vanilla's wet sponge dripping water. */
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction direction = Direction.getRandom(random);
        if (direction == Direction.UP) {
            return;
        }
        BlockPos neighborPos = pos.relative(direction);
        BlockState neighborState = level.getBlockState(neighborPos);
        if (state.canOcclude() && neighborState.isFaceSturdy(level, neighborPos, direction.getOpposite())) {
            return;
        }

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        if (direction == Direction.DOWN) {
            y -= 0.05;
            x += random.nextDouble();
            z += random.nextDouble();
        } else {
            y += random.nextDouble() * 0.8;
            if (direction.getAxis() == Direction.Axis.X) {
                z += random.nextDouble();
                x += direction == Direction.EAST ? 1.0 : -0.05;
            } else {
                x += random.nextDouble();
                z += direction == Direction.SOUTH ? 1.0 : -0.05;
            }
        }
        level.addParticle(ParticleTypes.DRIPPING_LAVA, x, y, z, 0.0, 0.0, 0.0);
    }
}
