package com.disruptioncomplex.block;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.ModBlockEntityHandler;
import com.disruptioncomplex.entity.block.CrabTrapBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CrabTrapBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {

    @SuppressWarnings("unused")
    public static final String BLOCK_ID = "crab_trap_block";
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    @SuppressWarnings("unused")
    public static final Settings BLOCK_SETTINGS = Settings.create()
            .instrument(NoteBlockInstrument.HAT)
            .strength(0.3F)
            .sounds(BlockSoundGroup.IRON)
            .nonOpaque()
            .allowsSpawning(Blocks::never)
            .solidBlock(Blocks::never)
            .suffocates(Blocks::never)
            .blockVision(Blocks::never);

    public static final MapCodec<CrabTrapBlock> CODEC = createCodec(CrabTrapBlock::new);
    public static final BooleanProperty IN_VALID_WATER = BooleanProperty.of("in_valid_water");


    public CrabTrapBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(IN_VALID_WATER, false)
        );
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? null : validateTicker(type, ModBlockEntityHandler.CRAB_TRAP_BLOCK_ENTITY, CrabTrapBlockEntity::serverTick);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrabTrapBlockEntity(pos, state);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        builder.add(WATERLOGGED);
        builder.add(IN_VALID_WATER);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }


    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state,
            WorldView world,
            ScheduledTickView tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            Random random
    ) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        if (Objects.requireNonNull(type) == NavigationType.WATER) {
            return state.getFluidState().isIn(FluidTags.WATER);
        }
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Create a shape that is 1/16th (1 pixel) inward from all sides
        return Block.createCuboidShape(1, 1, 1, 15, 16, 15);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.getStackInHand(player.getActiveHand()).isOf(Items.WATER_BUCKET)) {
            return ActionResult.FAIL;
        }
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrabTrapBlockEntity) {
                player.openHandledScreen((CrabTrapBlockEntity) blockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (world.getBlockEntity(pos)!= null && world.getBlockEntity(pos) instanceof CrabTrapBlockEntity blockEntity) {
            blockEntity.lastUpdateTick = world.getTime();
        }
        if (!world.isClient) {
            WaterVolume waterInfo = getConnectedWaterVolume(world, pos);
            boolean isValid = waterInfo.totalVolume >= MurkysManyFish.CONFIG.CrabTrapBlockWaterVolumeMin() && checkAdjacentConnections(world, pos);

            // Update the block state with the water volume check result
            world.setBlockState(pos, state.with(IN_VALID_WATER, isValid), Block.NOTIFY_ALL);
        }

    }

    private boolean checkAdjacentConnections(World world, BlockPos pos) {
        int validConnections = 0;

        // Check the four horizontal directions
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction dir : directions) {
            BlockPos adjacentPos = pos.offset(dir);
            BlockState adjacentState = world.getBlockState(adjacentPos);

            // Check if the adjacent block is either water or another CrabTrap
            if (adjacentState.getFluidState().isIn(FluidTags.WATER)) {
                validConnections++;
            }
        }

        // Return true if at least 3 sides are connected
        return validConnections >= 3;
    }



    private static class WaterVolume {
        int totalVolume;
        int maxDepth;
    }

    private static WaterVolume getConnectedWaterVolume(World world, BlockPos startPos) {
        //MurkysManyFish.LOGGER.info("Getting Connected Water Volume");
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> toExplore = new LinkedList<>();

        WaterVolume result = new WaterVolume();
        result.totalVolume = 0;
        result.maxDepth = 0;

        // Track the highest water block to calculate depth
        int highestY = startPos.getY();
        int lowestY = startPos.getY();

        // Start with the initial position
        toExplore.offer(startPos);
        visited.add(startPos);

        while (!toExplore.isEmpty() && result.totalVolume < MurkysManyFish.CONFIG.CrabTrapBlockWaterVolumeMin()) {
            BlockPos currentPos = toExplore.poll();
            FluidState fluidState = world.getBlockState(currentPos).getFluidState();

            if (fluidState.isIn(FluidTags.WATER) && fluidState.isStill()) {
                result.totalVolume++;

                // Update depth tracking
                assert currentPos != null;
                highestY = Math.max(highestY, currentPos.getY());
                lowestY = Math.min(lowestY, currentPos.getY());
                result.maxDepth = highestY - lowestY + 1;

                // If we've reached our limit, stop searching
                if (result.totalVolume >= 30) {
                    break;
                }

                // Check all adjacent positions
                BlockPos[] adjacentPositions = {
                        currentPos.north(), currentPos.south(),
                        currentPos.east(), currentPos.west(),
                        currentPos.up(), currentPos.down()
                };

                for (BlockPos adjacent : adjacentPositions) {
                    if (!visited.contains(adjacent)) {
                        visited.add(adjacent);
                        toExplore.offer(adjacent);
                    }
                }
            }
        }
        //MurkysManyFish.LOGGER.info("Connected Water Volume: {}", result.totalVolume);
        return result;
    }

}
