package com.disruptioncomplex.entity;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.*;
import com.disruptioncomplex.entity.data.FishingMagnetEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntityHandler {

    //Non-Fish Entities


    public static final EntityType<BambooSpearEntity> BAMBOO_SPEAR = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "bamboo_spear"),
            EntityType.Builder.<BambooSpearEntity>create(BambooSpearEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.5f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID, "bamboo_spear")
                    ))
    );


    public static final EntityType<FishingMagnetEntity> FISHING_MAGNET = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "fishing_magnet"),
            EntityType.Builder.<FishingMagnetEntity>create(FishingMagnetEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID, "fishing_magnet")
                    ))
    );

    // Small Fish
    public static final EntityType<AnchovyEntity> ANCHOVY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "anchovy"),
            EntityType.Builder.create(AnchovyEntity::new, SpawnGroup.AMBIENT)
                    .dimensions(0.8f, 0.8f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID, "anchovy"))));

    public static final EntityType<BettaEntity> BETTA = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "betta"),
            EntityType.Builder.create(BettaEntity::new, SpawnGroup.AMBIENT)
                    .dimensions(1f, 1f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID, "betta")
                    ))
    );

    // Medium Fish
    public static final EntityType<AngelfishEntity> ANGELFISH = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "angelfish"),
            EntityType.Builder.create(AngelfishEntity::new, SpawnGroup.AMBIENT)
                    .dimensions(1f, 1.3f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID, "angelfish"))));

    public static final EntityType<AnglerEntity> ANGLER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "angler"),
            EntityType.Builder.create(AnglerEntity::new, SpawnGroup.AMBIENT)
                    .dimensions(1f, 1.2f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID, "angler"))));

    public static final EntityType<MackerelEntity> MACKEREL = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "mackerel"),
            EntityType.Builder.create(MackerelEntity::new, SpawnGroup.AMBIENT)
                    .dimensions(1f, 1f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID, "mackerel"))));

    // Large Fish
    public static final EntityType<TunaEntity> TUNA = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "tuna"),
            EntityType.Builder.create(TunaEntity::new, SpawnGroup.AMBIENT)
                    .dimensions(1f, 1f).build(RegistryKey.of(
                            RegistryKeys.ENTITY_TYPE,
                            Identifier.of(MurkysManyFish.MOD_ID, "tuna"))));





    public static void registerModEntities() {
        MurkysManyFish.LOGGER.info("Registering Mod Entities for " + MurkysManyFish.MOD_ID);

        FabricDefaultAttributeRegistry.register(ModEntityHandler.ANCHOVY, AnchovyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityHandler.BETTA, BettaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityHandler.ANGELFISH, AngelfishEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityHandler.ANGLER, AnglerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityHandler.MACKEREL, MackerelEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityHandler.TUNA, TunaEntity.createAttributes());

    }

}
