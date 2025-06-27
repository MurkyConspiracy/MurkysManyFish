package com.disruptioncomplex.item;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.entity.custom.BambooSpearEntity;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class ItemBambooSpear extends Item implements ProjectileItem {

    public static final String ITEM_ID = "bamboo_spear";
    private static final Item.Settings ITEM_SETTINGS =
            new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MurkysManyFish.MOD_ID, "bamboo_spear")))
            .sword(ToolMaterial.STONE, 3, 1.1F)
            .maxCount(1)
            .maxDamage(45);

    public ItemBambooSpear() {
        super(ITEM_SETTINGS);

        MurkysManyFish.LOGGER.info("Building Bamboo Spear");
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 2400;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.willBreakNextUse()) {
            return ActionResult.FAIL;
        } else {
            user.setCurrentHand(hand);
            return ActionResult.CONSUME;
        }
    }


    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        BambooSpearEntity bambooSpearEntity = new BambooSpearEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1));
        bambooSpearEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return bambooSpearEntity;
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
            if (i < 10) {
                return false;
            } else if (stack.willBreakNextUse()) {
                return false;
            } else {
                RegistryEntry<SoundEvent> registryEntry = EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND)
                        .orElse(SoundEvents.ITEM_TRIDENT_THROW);
                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                if (world instanceof ServerWorld serverWorld) {
                    stack.damage(1, playerEntity);
                    ItemStack itemStack = stack.splitUnlessCreative(1, playerEntity);
                    BambooSpearEntity bambooSpearEntity = ProjectileEntity.spawnWithVelocity(BambooSpearEntity::new, serverWorld, stack, playerEntity, 0.0F, 2.5F, 1.0F);
                    ItemStack bambooSpearLine = ItemBambooSpearLine.createWithParent(bambooSpearEntity);
                    playerEntity.getInventory().insertStack(bambooSpearLine);
                    if (playerEntity.isInCreativeMode()) {
                        bambooSpearEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    }

                    world.playSoundFromEntity(null, bambooSpearEntity, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                    return true;
                }
                return false;
            }
        }
        return false;
    }


}

