package com.disruptioncomplex.entity.block;

import com.disruptioncomplex.entity.ModBlockEntityHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ClamBlockEntity extends BlockEntity {
    public ClamBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityHandler.CLAM_BLOCK_ENTITY, pos, state);
    }
}
