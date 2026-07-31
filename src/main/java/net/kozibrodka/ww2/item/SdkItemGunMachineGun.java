package net.kozibrodka.ww2.item;

import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkEntityCasing;
import net.kozibrodka.sdk_api.utils.SdkItemGun;
import net.kozibrodka.ww2.entity.SdkEntityBulletMachineGun;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class SdkItemGunMachineGun extends SdkItemGun {

    public SdkItemGunMachineGun(Identifier i) {
        super(i);
        firingSound = "ww2:bullet";
        requiredBullet = Item.MAP;
        numBullets = 1;
        damage = 10;
        muzzleVelocity = 4F;
        spread = 2.0F;
        useDelay = 1;
        recoil = 1.0F;
        soundRangeFactor = 4F;
        penetration = 2;
    }

    @Override
    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4) {
        return new SdkEntityBulletMachineGun(world, entity, this, f, f1, f2, f3, f4);
    }

    @Override
    public SdkEntityCasing getBulletCasingEntity(World world, Entity entity, float f) {
        return null;
    }
}
