package net.kozibrodka.vehicles.entity;


import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.item.SdkItemGunMachineGun;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SdkEntityBulletMachineGun extends SdkEntityBullet {

    public SdkEntityBulletMachineGun(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.35F, 0.35F);
    }

    public SdkEntityBulletMachineGun(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setBoundingBoxSpacing(0.35F, 0.35F);
    }

    public SdkEntityBulletMachineGun(World world, Entity entity, SdkItemGunMachineGun sdkitemgun, float f, float f1, float f2, float f3,
                                     float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.35F, 0.35F);
    }

    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGunMachineGun) mod_Vehicles.itemGunMachineGun).firingSound, ((SdkItemGunMachineGun)mod_Vehicles.itemGunMachineGun).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    public void playImpactSound(World world){
        world.playSound(this, "vehicles:bullethit", 1.0F, 1.2F / (random.nextFloat() * 0.1F + 0.9F)); //oryginalna glosnosc 1.0
    }

}
