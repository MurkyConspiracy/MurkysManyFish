package com.disruptioncomplex.entity;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.MackerelEntity;
import com.disruptioncomplex.entity.custom.TunaEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntityHandler {
    public static final EntityType<MackerelEntity> MACKEREL = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID,"mackerel"),
            EntityType.Builder.create(MackerelEntity::new, SpawnGroup.AMBIENT)
                    .dimensions(1f,1f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID,"mackerel"))));

    public static final EntityType<TunaEntity> TUNA = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID,"tuna"),
            EntityType.Builder.create(TunaEntity::new, SpawnGroup.AMBIENT)
                    .dimensions(1f,1f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID,"tuna"))));

    public static void registerModEntities() {
        MurkysManyFish.LOGGER.info("Registering Mod Entities for " + MurkysManyFish.MOD_ID);


        FabricDefaultAttributeRegistry.register(ModEntityHandler.MACKEREL, MackerelEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityHandler.TUNA, TunaEntity.createAttributes());

    }

}
