package net.kozibrodka.ww2.entityBullet;

import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.ww2.entity.EntityCannon;
import net.kozibrodka.ww2.entity.EntityTank;
import net.kozibrodka.ww2.entity.SdkEntityAAShell;
import net.kozibrodka.ww2.entity.SdkEntityTankShell;
import net.kozibrodka.ww2.properties.CannonType;
import net.kozibrodka.ww2.properties.TankType;
import net.minecraft.world.World;

public class ShellFactory {

//    public static void fireShellBasedOnType(World world, EntityTank tankEntity, TankType tankType, EntityTank.ShellType shellEnum){
//        switch (shellEnum){
//            case AP -> world.spawnEntity(new TankShellAP(world, tankEntity, tankType))
//        }
//    }

    public static SdkEntityTankShell getShellBasedOnTank(World world, EntityTank tankEntity, TankType tankType, EntityTank.ShellType shellEnum){
        switch (shellEnum){
            case AP -> {
                return new TankShellAP(world, tankEntity, tankType);
            }
            case HE -> {
                return new TankShellHE(world, tankEntity, tankType);
            }
            case OBS -> {
                return new TankShellOBS(world, tankEntity, tankType);
            }
        }
        return null;
    }

    public static SdkEntityBullet getShellBasedOnCannon(World world, EntityCannon entityCannon, CannonType cannonType, EntityCannon.ArtShellType shellEnum){
        switch (shellEnum){
            case AP -> {
                return new TankShellAP(world, entityCannon, cannonType);
            }
            case HE -> {
                return new TankShellHE(world, entityCannon, cannonType);
            }
            case AA -> {
                return new SdkEntityAAShell(world, entityCannon, cannonType);
            }
//            case OBS -> {
//                return new TankShellOBS(world, entityCannon, cannonType);
//            }
        }
        return null;
    }
}
