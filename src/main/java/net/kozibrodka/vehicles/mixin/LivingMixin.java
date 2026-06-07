package net.kozibrodka.vehicles.mixin;

import net.kozibrodka.vehicles.entity.EntityPassengerSeat;
import net.kozibrodka.vehicles.entity.EntityTruck;
import net.minecraft.block.material.PassableMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingMixin extends Entity{



    @Override
    public void onCollision(Entity otherEntity){
        if(this.vehicle instanceof EntityPassengerSeat && otherEntity instanceof EntityTruck){
            return; //TODO test mixina
        }
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
