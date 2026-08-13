package net.kozibrodka.ww2.entityBullet;


import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class TankBulletMachineGun extends SdkEntityBullet implements EntitySpawnDataProvider {

    public TankBulletMachineGun(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.35F, 0.35F);
    }

    public TankBulletMachineGun(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
        setBoundingBoxSpacing(0.35F, 0.35F);
    }

    public TankBulletMachineGun(World world, Entity entity, SdkItemGun sdkitemgun, float f, float f1, float f2, float f3,
                                float f4)
    {
        super(world, entity, sdkitemgun, f, f1, f2, f3, f4);
        setBoundingBoxSpacing(0.35F, 0.35F);
    }

    @Override
    public void playServerSound(World world)
    {
        world.playSound(this, ((SdkItemGun) mod_Vehicles.itemGunMachineGun).firingSound, ((SdkItemGun)mod_Vehicles.itemGunMachineGun).soundRangeFactor, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(mod_Vehicles.MOD_ID, "TankBulletMachineGun");
    }

    @Override
    public void tick(){
        if(timeInAir == 0) {
            velocityX = 0;
            velocityY = 0;
            velocityZ = 0;
            return;
        }
        /// DEBUG for setting ShellPosParameters for Tanks. ^
        super.tick();
    }

}
