package com.disruptioncomplex.world.gen.feature;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.block.ClamBlock;
import com.disruptioncomplex.block.ModBlockHandler;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ClamFeature extends Feature<CountConfig> {
    public ClamFeature(Codec<CountConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<CountConfig> context) {
        int i = 0;
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        int j = random.nextInt(MurkysManyFish.CONFIG.ClamMaxGroupSize() - MurkysManyFish.CONFIG.ClamMinGroupSize() + 1) + MurkysManyFish.CONFIG.ClamMinGroupSize();

        for(int k = 0; k < j; ++k) {
            int l = random.nextInt(8) - random.nextInt(8);
            int m = random.nextInt(8) - random.nextInt(8);
            int n = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + l, blockPos.getZ() + m);
            BlockPos blockPos2 = new BlockPos(blockPos.getX() + l, n, blockPos.getZ() + m);
            BlockState blockState = ModBlockHandler.CLAM.getDefaultState().with(ClamBlock.FACING, Direction.Type.HORIZONTAL.random(random));
            if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) && blockState.canPlaceAt(structureWorldAccess, blockPos2)) {
                structureWorldAccess.setBlockState(blockPos2, blockState, 2);
                ++i;
            }
        }

        return i > 0;
    }
}
