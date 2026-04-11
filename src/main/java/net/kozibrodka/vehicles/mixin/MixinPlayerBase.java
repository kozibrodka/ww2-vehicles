package net.kozibrodka.vehicles.mixin;

import net.kozibrodka.vehicles.entity.EntityVehicle;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerBase.class)
public abstract class MixinPlayerBase extends Living {

    public MixinPlayerBase(Level arg) {
        super(arg);
    }

    @Shadow public abstract boolean damage(EntityBase arg, int i);

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void InjectedDMG(EntityBase arg, int i, CallbackInfoReturnable<Boolean> cir){

    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerBase;increaseStat(Lnet/minecraft/stat/Stat;I)V"), cancellable = true)
    private void InjectedDMG2(EntityBase arg, int i, CallbackInfoReturnable<Boolean> cir){
        if(this.vehicle instanceof EntityVehicle){ //todo przenies do api
            System.out.println("negacja DMG w człogu");
            cir.setReturnValue(damage(null,0));
        }
    }
}
