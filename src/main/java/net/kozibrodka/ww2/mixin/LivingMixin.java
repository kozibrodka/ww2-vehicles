package net.kozibrodka.ww2.mixin;

import net.kozibrodka.ww2.entity.EntityPassengerSeat;
import net.kozibrodka.ww2.entity.EntityTruck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingMixin extends Entity{


    /// Mixin so that LivingEntity wont push Cars while in PassangerSeat
    @Override
    public void onCollision(Entity otherEntity){
        if(this.vehicle instanceof EntityPassengerSeat && (otherEntity instanceof EntityTruck || otherEntity instanceof EntityPassengerSeat)){
            return; //TODO test mixina
        }
//        /// Living Entities wont push Trucks
//        if(otherEntity instanceof EntityTruck){
//            return;
//        }
        super.onCollision(otherEntity);
    }


    public LivingMixin(World world) {
        super(world);
    }

    @Override
    public void readNbt(NbtCompound nbt) {

    }

    @Override
    public void writeNbt(NbtCompound nbt) {

    }

    @Override
    public void initDataTracker() {

    }
}
