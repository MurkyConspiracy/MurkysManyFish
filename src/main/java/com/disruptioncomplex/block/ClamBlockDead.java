package com.disruptioncomplex.block;

import com.disruptioncomplex.item.ModItemHandler;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ClamBlockDead extends Block implements Waterloggable {


    @SuppressWarnings("unused")
    public static final String BLOCK_ID = "clam_block_dead";
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final Property<Direction> FACING = HorizontalFacingBlock.FACING;
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

    public ClamBlockDead(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(WATERLOGGED, false)
                .with(FACING, Direction.NORTH)

        );
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {


        // Sneaking behavior: Always face north if the player is sneaking
        if (ctx.getPlayer() != null && ctx.getPlayer().isSneaking()) {
            return this.getDefaultState()
                    .with(FACING, Direction.random(ctx.getWorld().random))
                    .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
        }

        // Get the player's placement direction, default to NORTH if invalid
        Direction direction = ctx.getPlayerLookDirection();
        Direction horizontalDirection = direction.getAxis().isHorizontal() ? direction : Direction.NORTH;

        // Set the block's default state with facing and waterlogged properties
        return this.getDefaultState()
                .with(FACING, horizontalDirection.getOpposite())
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
        return VoxelShapes.cuboid(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.1625D, 0.9375D);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.1625D, 0.9375D);
    }

    @Override
    protected VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.cuboid(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.1625D, 0.9375D);
    }

    @Override
    public Item asItem() {
        return ModItemHandler.CLAM_DEAD;
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        return List.of(new ItemStack(ModItemHandler.CLAM_DEAD));
    }

}

