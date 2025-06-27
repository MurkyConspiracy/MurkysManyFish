package com.disruptioncomplex.block;

import com.disruptioncomplex.entity.block.ClamBlockEntity;
import com.disruptioncomplex.item.ModItemHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ClamBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {


    @SuppressWarnings("unused")
    public static final String BLOCK_ID = "clam_block";
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final Property<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final MapCodec<ClamBlock> CODEC = createCodec(ClamBlock::new);
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

    public ClamBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(WATERLOGGED, true)
                .with(FACING, Direction.NORTH)

        );
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ClamBlockEntity(pos, state);
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

        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)
                ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
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
        return ModItemHandler.CLAM;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(WATERLOGGED)) {
            world.setBlockState(pos, ModBlockHandler.CLAM_DEAD.getDefaultState().with(ClamBlockDead.WATERLOGGED, false).with(ClamBlockDead.FACING, state.get(FACING)), Block.NOTIFY_LISTENERS);
        }
        super.scheduledTick(state, world, pos, random);
    }

    protected void checkLivingConditions(BlockState state, BlockView world, ScheduledTickView tickView, Random random, BlockPos pos) {
        if (!isInWater(state, world, pos)) {
            tickView.scheduleBlockTick(pos, this, 60 + random.nextInt(40));
        }
    }

    protected static boolean isInWater(BlockState state, BlockView world, BlockPos pos) {
        if (state.get(WATERLOGGED)) {
            return true;
        } else {
            for (Direction direction : Direction.values()) {
                if (world.getFluidState(pos.offset(direction)).isIn(FluidTags.WATER)) {
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        this.checkLivingConditions(state, world, world, world.random, pos);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
        this.checkLivingConditions(state, world, world, world.random, pos);

        //this.scheduledTick(state, (ServerWorld) world, pos, world.random);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        return List.of(new ItemStack(ModItemHandler.CLAM));
    }


}

