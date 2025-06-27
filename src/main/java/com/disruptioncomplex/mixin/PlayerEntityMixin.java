package com.disruptioncomplex.mixin;

import com.disruptioncomplex.entity.data.FishingMagnetEntity;
import com.disruptioncomplex.util.MagnetHookAccessor;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@SuppressWarnings("unused")
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements MagnetHookAccessor {
    
    @Unique
    private FishingMagnetEntity magnetHook;
    
    @Inject(method = "<init>", at = @At("TAIL"))
    private void initMagnetHook(CallbackInfo ci) {
        this.magnetHook = null;
    }
    
    @Override
    public FishingMagnetEntity getMagnetHook() {
        return this.magnetHook;
    }
    
    @Override
    public void setMagnetHook(FishingMagnetEntity magnetHook) {
        this.magnetHook = magnetHook;
    }
}