package com.disruptioncomplex.entity;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.block.ModBlockHandler;
import com.disruptioncomplex.entity.block.CrabTrapBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityHandler {

    public static final BlockEntityType<CrabTrapBlockEntity> CRAB_TRAP_BLOCK_ENTITY =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(MurkysManyFish.MOD_ID, "crab_trap"),
                    FabricBlockEntityTypeBuilder.create(CrabTrapBlockEntity::new, ModBlockHandler.CRAB_TRAP)
                            .build(null)
            );

    public static void registerModBlockEntities() {
        MurkysManyFish.LOGGER.info("Registering Mod Block Entities for " + MurkysManyFish.MOD_ID);



    }

}
