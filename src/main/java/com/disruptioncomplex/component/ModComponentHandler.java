package com.disruptioncomplex.component;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.BambooSpearEntity;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponentHandler {
    
    public static final ComponentType<BambooSpearEntity.EntityReference> PARENT_ENTITY_REF = 
        Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MurkysManyFish.MOD_ID, "parent_entity_ref"),
            ComponentType.<BambooSpearEntity.EntityReference>builder()
                .codec(BambooSpearEntity.ENTITY_REFERENCE_CODEC)
                .build()
        );
    
    public static void registerComponents() {
        MurkysManyFish.LOGGER.info("Registering mod components for " + MurkysManyFish.MOD_ID);
    }
}