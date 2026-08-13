package net.kozibrodka.ww2.properties_cannon;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.model_cannon.ModelFlakvierling;
import net.kozibrodka.ww2.model_cannon.ModelPak40;
import net.kozibrodka.ww2.properties.Cannon_properties;

import java.util.Arrays;

public class Properties_Pak40 extends Cannon_properties {

    public Properties_Pak40(){
        name = "pak";
        longName = "Pak40mm";
        model = new ModelPak40();
//        model = new ModelPak40_new();
        texture = "Pak40.png";
//        texture = "skinpak40.png";
        przedmiot = mod_Vehicles.pak40;
        shootSound = "ww2:aafire";
        overheatSound = "planes:mp44reload";
        isAntiAircraft = false;
        numBarrels = 50;
        barrelLength = 44;
        barrelPivotXOffset = 0;
        barrelPivotYOffset = 16;
        barrelPivotZOffset = 0;
        shellYOffset = new int[numBarrels];
        shellZOffset = new int[numBarrels];
        Arrays.fill(shellYOffset, 0);
        Arrays.fill(shellZOffset, 0);

        MAX_HEALTH = 100;
        artWidth = 2.0F;
        artHeight = 1.0F;
        gunnerX = -8;
        gunnerY = 28;
        gunnerZ = -8;
        numCargoSlots = 0;
        numBulletSlots = 0;
        numShellSlots = 5;

        topViewLimit = 75;
        bottomViewLimit = 0;
        maxCannonDeviation = 15F;
        cannonPitchSpeed = 3F;
        cannonYawSpeed = 3F;
        bodyTurnSpeed = 0F;

        shootDelay = 10;
        recoil = 0;
        cannonDamage = 25;
        cannonVehicleDamage = 25;
        cannonPenetration = 3.5F;
        cannonBulletDrop = 0.005F;
        cannonSpread = 1.0F;
        cannonMuzzleVelocity = 4.0F;
        cannonExploPower = 4.0F;
        cannonRange = 200;
    }

}
