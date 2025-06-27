package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.component.ModComponentHandler;
import com.disruptioncomplex.entity.custom.BambooSpearEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemBambooSpearLine extends Item {
    public static final String ITEM_ID = "bamboo_spear_line";

    public ItemBambooSpearLine() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MurkysManyFish.MOD_ID, ITEM_ID)))
                        .maxCount(1)
                        .maxDamage(3)
        );
    }

    // Method to create ItemStack with parent entity reference
    public static ItemStack createWithParent(BambooSpearEntity parentEntity) {
        ItemStack stack = new ItemStack(ModItemHandler.BAMBOO_SPEAR_LINE);
        BambooSpearEntity.EntityReference ref = new BambooSpearEntity.EntityReference(
            parentEntity.getUuid(),
            parentEntity.getX(),
            parentEntity.getY(),
            parentEntity.getZ()
        );
        stack.set(ModComponentHandler.PARENT_ENTITY_REF, ref);
        return stack;
    }
    
    // Method to find the actual entity from the reference
    public BambooSpearEntity getParentEntity(ItemStack stack, World world) {
        BambooSpearEntity.EntityReference ref = stack.get(ModComponentHandler.PARENT_ENTITY_REF);
        if (ref != null && world instanceof ServerWorld serverWorld) {
            Entity entity = serverWorld.getEntity(ref.uuid());
            if (entity instanceof BambooSpearEntity bambooSpear) {
                return bambooSpear;
            }
        }
        return null;
    }
    
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            BambooSpearEntity parentEntity = getParentEntity(stack, world);
            
            if (parentEntity != null) {
                Vec3d playerPos = user.getPos();
                Vec3d entityPos = parentEntity.getPos();
                Vec3d direction = playerPos.subtract(entityPos).normalize();
                double speed = 0.5;
                parentEntity.setVelocity(direction.multiply(speed));
                stack.damage(1, user);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}