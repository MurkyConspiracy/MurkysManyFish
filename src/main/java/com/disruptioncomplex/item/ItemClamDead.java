package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.block.ClamBlock;
import com.disruptioncomplex.block.ModBlockHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ItemClamDead extends Item{

    public static final String ITEM_ID = "clam_dead";

    public ItemClamDead() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"clam_dead")))
                        .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.1F).build())
        );

        MurkysManyFish.LOGGER.info("Building Dead Clam");
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos().offset(context.getSide());
        BlockState blockState;

        if (context.getPlayer() != null && context.getPlayer().isSneaking()) {
            blockState = ModBlockHandler.CLAM_DEAD.getDefaultState()
                    .with(ClamBlock.FACING, Direction.random(context.getWorld().random))
                    .with(ClamBlock.WATERLOGGED, context.getWorld().getFluidState(blockPos).isOf(Fluids.WATER));
        } else {
            // Get the player's placement direction, default to NORTH if invalid
            Direction direction = context.getPlayer().getFacing();
            Direction horizontalDirection = direction.getAxis().isHorizontal() ? direction : Direction.NORTH;

            // Set the block's default state with facing and waterlogged properties
            blockState = ModBlockHandler.CLAM_DEAD.getDefaultState()
                    .with(ClamBlock.FACING, horizontalDirection.getOpposite())
                    .with(ClamBlock.WATERLOGGED, context.getWorld().getFluidState(blockPos).isOf(Fluids.WATER));
        }


        if (world.getBlockState(blockPos).isAir() || world.getBlockState(blockPos).isOf(Blocks.WATER)) {
            world.setBlockState(blockPos, blockState);
            context.getStack().decrement(1);
            world.scheduleBlockTick(blockPos, ModBlockHandler.CLAM_DEAD, 60);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }



}
