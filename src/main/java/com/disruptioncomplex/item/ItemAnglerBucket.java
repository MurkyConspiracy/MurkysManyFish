package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.ModEntityHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ItemAnglerBucket extends EntityBucketItem {

    public static final String ITEM_ID = "angler_bucket";

    public ItemAnglerBucket() {
        super(ModEntityHandler.ANGLER,
                Fluids.WATER,
                SoundEvents.ITEM_BUCKET_EMPTY_FISH,
                new Settings()
                        .registryKey(
                                RegistryKey.of(
                                        RegistryKeys.ITEM,
                                        Identifier.of(
                                                MurkysManyFish.MOD_ID,
                                                "angler_bucket")))
                        .maxCount(1)
                        .component(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT)


        );

        MurkysManyFish.LOGGER.info("Building Angler Bucket");
    }
}