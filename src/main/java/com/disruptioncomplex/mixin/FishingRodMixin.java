package com.disruptioncomplex.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@SuppressWarnings("all")
@Mixin(FishingRodItem.class)
public class FishingRodMixin {


    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void overrideUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> callback) {


        ItemStack itemStack = user.getStackInHand(hand);
        if (user.fishHook != null) {
            if (!world.isClient) {
                int i = user.fishHook.use(itemStack);

                itemStack.damage(i, user, LivingEntity.getSlotForHand(hand));
            }

            world.playSound( null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            if (world instanceof ServerWorld serverWorld) {
                int j = (int) (EnchantmentHelper.getFishingTimeReduction(serverWorld, itemStack, user) * 20.0F);
                int k = EnchantmentHelper.getFishingLuckBonus(serverWorld, itemStack, user);
                ProjectileEntity.spawn(new FishingBobberEntity(user, world, k, j), serverWorld, itemStack);
            }

            user.incrementStat(Stats.USED.getOrCreateStat((Item) (Object) this));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
        }


        callback.setReturnValue(ActionResult.SUCCESS);
        callback.cancel();

    }


}
