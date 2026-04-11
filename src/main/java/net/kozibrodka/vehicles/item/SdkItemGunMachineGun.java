package net.kozibrodka.vehicles.item;

import net.kozibrodka.sdk_api.events.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.events.utils.SdkEntityBulletCasing;
import net.kozibrodka.sdk_api.events.utils.SdkItemGun;
import net.kozibrodka.vehicles.entity.SdkEntityBulletMachineGun;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class SdkItemGunMachineGun extends SdkItemGun {

    public SdkItemGunMachineGun(Identifier i) {
        super(i);
        firingSound = "vehicles:bullet";
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

    public SdkEntityBullet getBulletEntity(World world, Entity entity, float f, float f1, float f2, float f3, float f4) {
        return new SdkEntityBulletMachineGun(world, entity, this, f, f1, f2, f3, f4);
    }

    public SdkEntityBulletCasing getBulletCasingEntity(World world, Entity entity, float f) {
        return null;
    }
}
