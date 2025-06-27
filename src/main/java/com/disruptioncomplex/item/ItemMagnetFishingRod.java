package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.data.FishingMagnetEntity;
import com.disruptioncomplex.util.MagnetHookAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ItemMagnetFishingRod extends Item {


    public static final String ITEM_ID = "magnet_fishing_rod";

    public ItemMagnetFishingRod() {
        super(
                new Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MurkysManyFish.MOD_ID,"magnet_fishing_rod")))
                        .maxCount(1)
                        .maxDamage(MurkysManyFish.CONFIG.magnetRodDurability())
        );

        MurkysManyFish.LOGGER.info("Building Magnet Fishing Rod");
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (((MagnetHookAccessor) user).getMagnetHook() != null) {
            if (!world.isClient) {
                int i = ((MagnetHookAccessor) user).getMagnetHook().use(itemStack);
                itemStack.damage(i, user, LivingEntity.getSlotForHand(hand));
            }

            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE,
                    SoundCategory.NEUTRAL,
                    1.0F,
                    0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
            );
            user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_FISHING_BOBBER_THROW,
                    SoundCategory.NEUTRAL,
                    0.5F,
                    0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
            );
            if (world instanceof ServerWorld serverWorld) {
                int j = (int)(EnchantmentHelper.getFishingTimeReduction(serverWorld, itemStack, user) * 20.0F);
                int k = EnchantmentHelper.getFishingLuckBonus(serverWorld, itemStack, user);
                ProjectileEntity.spawn(new FishingMagnetEntity(user, world, k, j), serverWorld, itemStack);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return ActionResult.SUCCESS;
    }
}
