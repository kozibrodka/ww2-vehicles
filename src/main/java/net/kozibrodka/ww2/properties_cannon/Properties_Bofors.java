package net.kozibrodka.ww2.properties_cannon;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.model_cannon.ModelBofors;
import net.kozibrodka.ww2.properties.Cannon_properties;

public class Properties_Bofors extends Cannon_properties {

    public Properties_Bofors(){
        name = "bofors"; //todo classic_
        longName = "Bofors 40mm AA Gun";
        model = new ModelBofors();
        texture = "AAAllies.png";
        przedmiot = mod_Vehicles.bofors;
        shootSound = "ww2:aafire";
        overheatSound = "planes:barreload";
        isAntiAircraft = true;
        numBarrels = 1;
        shellXOffset = new int[numBarrels];
        shellYOffset = new int[numBarrels];
        shellZOffset = new int[numBarrels];
        shellXOffset[0] = 44;
        shellYOffset[0] = 10;
        shellZOffset[0] = 4;

        MAX_HEALTH = 100;
        artWidth = 2.0F;
        artHeight = 2.0F;
        gunnerX = -4;
        gunnerY = 28;
        gunnerZ = -8;
        numCargoSlots = 5;
        numBulletSlots = 5;
        numShellSlots = 5;

        topViewLimit = 90.0F;
        bottomViewLimit = 0;
        maxCannonDeviation = 0F;
        cannonPitchSpeed = 2F;
        cannonYawSpeed = 2F;
        bodyTurnSpeed = 1F;

        shootDelay = 20;
        recoil = 5;
        cannonDamage = 25;
        cannonVehicleDamage = 25;
        cannonPenetration = 2.5F;
        cannonBulletDrop = 0.005F;
        cannonSpread = 1.0F;
        cannonMuzzleVelocity = 3.0F;
        cannonExploPower = 4.0F;
        cannonRange = 20;

    }
}
