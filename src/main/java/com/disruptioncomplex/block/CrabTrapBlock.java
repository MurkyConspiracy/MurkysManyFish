package com.disruptioncomplex.block;

import com.disruptioncomplex.entity.block.CrabTrapBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
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

import java.util.Objects;

public class CrabTrapBlock extends Block implements Waterloggable,BlockEntityProvider {

    @SuppressWarnings("unused")
    public static final String BLOCK_ID = "crab_trap_block";
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    @SuppressWarnings("unused")
    public static final Settings BLOCK_SETTINGS = AbstractBlock.Settings.create()
            .instrument(NoteBlockInstrument.HAT)
            .strength(0.3F)
            .sounds(BlockSoundGroup.IRON)
            .nonOpaque()
            .allowsSpawning(Blocks::never)
            .solidBlock(Blocks::never)
            .suffocates(Blocks::never)
            .blockVision(Blocks::never);


    public CrabTrapBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrabTrapBlockEntity(pos, state);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
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
        if(player.getStackInHand(player.getActiveHand()).isOf(Items.WATER_BUCKET)) {
            return ActionResult.FAIL;
        }
        if(!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof CrabTrapBlockEntity) {
                player.openHandledScreen((CrabTrapBlockEntity)blockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }
}
