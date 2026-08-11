package net.kozibrodka.ww2.properties_cannon;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.model_cannon.ModelBofors;
import net.kozibrodka.ww2.model_cannon.ModelFlakvierling;
import net.kozibrodka.ww2.properties.Cannon_properties;

import java.util.Arrays;

public class Properties_Flakvierling extends Cannon_properties {

    public Properties_Flakvierling(){
        name = "flakvierling";
        longName = "Flakvierling-38 AA Gun";
        model = new ModelFlakvierling();
        texture = "AAAxis.png";
        przedmiot = mod_Vehicles.flakvierling;
        shootSound = "ww2:aafire";
        overheatSound = "planes:barreload";
        isAntiAircraft = true;
        numBarrels = 4;
        shellXOffset = new int[numBarrels];
        shellYOffset = new int[numBarrels];
        shellZOffset = new int[numBarrels];
//        Arrays.fill(shellXOffset, 44);
//        Arrays.fill(shellYOffset, 10);
//        Arrays.fill(shellZOffset, 4);

        shellXOffset[0] = 44;
        shellYOffset[0] = 10;
        shellZOffset[0] = 6;

        shellXOffset[1] = 44;
        shellYOffset[1] = 5;
        shellZOffset[1] = 6;

        shellXOffset[2] = 44;
        shellYOffset[2] = 10;
        shellZOffset[2] = -2;

        shellXOffset[3] = 44;
        shellYOffset[3] = 5;
        shellZOffset[3] = -2;

        MAX_HEALTH = 100;
        artWidth = 2.0F;
        artHeight = 1.0F;
        gunnerX = -8;
        gunnerY = 28;
        gunnerZ = -8;
        numCargoSlots = 0;
        numBulletSlots = 0;
        numShellSlots = 5;

        topViewLimit = 90F;
        bottomViewLimit = 0F;
        maxCannonDeviation = 0F;
        cannonPitchSpeed = 3F;
        cannonYawSpeed = 3F;
        bodyTurnSpeed = 3F;

        shootDelay = 10;
        recoil = 5;
        cannonDamage = 25;
        cannonVehicleDamage = 25;
        cannonPenetration = 2.5F;
        cannonBulletDrop = 0.005F;
        cannonSpread = 0.0F;
        cannonMuzzleVelocity = 3.0F;
        cannonExploPower = 4.0F;
        cannonRange = 20;
    }

}
