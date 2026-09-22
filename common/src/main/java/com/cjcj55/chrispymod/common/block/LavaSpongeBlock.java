package com.cjcj55.chrispymod.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;

import java.util.ArrayDeque;
import java.util.Queue;

/** The dry sponge. Placing it next to lava, or lava flowing next to it, soaks up nearby lava and turns it into {@link WetLavaSpongeBlock}. */
public class LavaSpongeBlock extends Block {
    public LavaSpongeBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(state.getBlock())) {
            tryAbsorbLava(level, pos);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, Orientation orientation, boolean movedByPiston) {
        tryAbsorbLava(level, pos);
        super.neighborChanged(state, level, pos, neighborBlock, orientation, movedByPiston);
    }

    private void tryAbsorbLava(Level level, BlockPos pos) {
        if (removeNearbyLava(level, pos)) {
            level.setBlock(pos, ModBlocks.WET_LAVA_SPONGE.defaultBlockState(), 2);
            level.levelEvent(2001, pos, Block.getId(Blocks.LAVA.defaultBlockState()));
        }
    }

    /** Breadth-first search outward from the sponge, same shape as vanilla's water sponge. */
    private boolean removeNearbyLava(Level level, BlockPos origin) {
        Queue<BlockPos> queue = new ArrayDeque<>();
        Queue<Integer> depths = new ArrayDeque<>();
        queue.add(origin);
        depths.add(0);
        int removed = 0;

        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            int depth = depths.poll();

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);
                FluidState fluidState = level.getFluidState(neighborPos);
                if (fluidState.is(FluidTags.LAVA) && neighborState.getBlock() instanceof LiquidBlock) {
                    level.setBlock(neighborPos, Blocks.AIR.defaultBlockState(), 3);
                    removed++;
                    if (depth < SpongeBlock.MAX_DEPTH) {
                        queue.add(neighborPos);
                        depths.add(depth + 1);
                    }
                }
            }

            if (removed > SpongeBlock.MAX_COUNT) {
                break;
            }
        }

        return removed > 0;
    }
}
