package com.disruptioncomplex.mixin;

import com.disruptioncomplex.MurkysManyFish;
import com.disruptioncomplex.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;
import net.minecraft.item.Item;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
@Mixin(ItemStack.class)
public class SlipperyMixin {
    
    private static RegistryEntry<Enchantment> cachedSlipperyEnchantment = null;

@Inject(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V",
        at = @At("HEAD"))
private void onItemDamageVoid(int amount, ServerWorld world, ServerPlayerEntity player, Consumer<Item> onBreak, CallbackInfo ci) {
    // Only run on the server-side (we're already on server since we have ServerWorld)
    @SuppressWarnings("all")
    ItemStack stack = (ItemStack) (Object) this;
    
    // Cache the enchantment entry on first use
    if (cachedSlipperyEnchantment == null) {
        cachedSlipperyEnchantment = world.getRegistryManager()
            .getOrThrow(net.minecraft.registry.RegistryKeys.ENCHANTMENT)
            .getEntry(ModEnchantments.SLIPPERY.getValue()) // Use getValue() to get the Identifier
            .orElse(null);
    }
    
    if (cachedSlipperyEnchantment != null) {
        int enchantmentLevel = EnchantmentHelper.getLevel(cachedSlipperyEnchantment, stack);
        
        if (enchantmentLevel > 0 && player != null) {

            float dropChance = (MurkysManyFish.CONFIG.slipperyDropChancePerLevel() / 100f) * enchantmentLevel;
            if (world.isRaining())
                dropChance *= 2f;
            
            if (world.getRandom().nextFloat() < dropChance) {
                // Calculate position in front of player
                Vec3d playerPos = player.getPos();
                Vec3d lookDirection = player.getRotationVector();
                Vec3d dropPos = playerPos.add(lookDirection.multiply(1.5)); // 1.5 blocks in front
                
                // Create the item entity manually for more control
                ItemEntity itemEntity = new ItemEntity(world, dropPos.x, dropPos.y, dropPos.z, stack.copy());
                
                // Add momentum away from player (adjust multiplier for desired speed)
                Vec3d velocity = lookDirection.multiply(0.3).add(0, 0.2, 0); // Forward + slight upward
                itemEntity.setVelocity(velocity);
                
                // Prevent immediate pickup (300 ticks = 15 seconds, adjust as needed)
                itemEntity.setPickupDelay(40); // 2 seconds
                
                // Set the thrower to prevent the same player from picking it up immediately
                itemEntity.setThrower(player);
                
                world.spawnEntity(itemEntity);
                stack.setCount(0);
            }
        }
    }
}
}