package net.kozibrodka.ww2.entityBullet;

import net.kozibrodka.ww2.entity.EntityTank;
import net.kozibrodka.ww2.entity.SdkEntityTankShell;
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
}
