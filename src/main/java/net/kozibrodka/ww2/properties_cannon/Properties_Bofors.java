package net.kozibrodka.ww2.properties_cannon;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.model_cannon.ModelBofors;
import net.kozibrodka.ww2.properties.Cannon_properties;

public class Properties_Bofors extends Cannon_properties {

    public Properties_Bofors(){
        model = new ModelBofors();
        texture = "AAAllies.png";
        name = "bofors";
        longName = "Bofors 40mm AA Gun";
        przedmiot = mod_Vehicles.bofors;
        shootSound = "ww2:aafire";
        reloadSound = "planes:barreload";
        artWidth = 2.0F;
        artHeight = 2.0F;
        numCargoSlots = 5;
        numBulletSlots = 5;
        numShellSlots = 5;

        MAX_HEALTH = 100;
        recoil = 5;
        damage = 50.0F;
        accuracy = 6.0F;
        velocity = 3.0F;
        range = 20;

        shootDelay = 20;
        numBarrels = 1;
        isAntiAircraft = false;
        gunnerX = 8;
        gunnerY = 28;
        gunnerZ = 8;
        topViewLimit = 90.0F;
        bottomViewLimit = 0;
    }
}
