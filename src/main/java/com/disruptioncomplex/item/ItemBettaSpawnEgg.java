package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemBettaSpawnEgg extends SpawnEggItem {


    public static final String ITEM_ID = "betta_spawn_egg";

    public ItemBettaSpawnEgg(EntityType<? extends MobEntity> type) {
        super(type, new Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM,Identifier.of(MurkysManyFish.MOD_ID,"betta_spawn_egg"))));
    }
}