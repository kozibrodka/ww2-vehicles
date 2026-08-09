package net.kozibrodka.ww2.properties_cannon;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.model_cannon.ModelFlakvierling;
import net.kozibrodka.ww2.properties.Cannon_properties;

public class Properties_Flakvierling extends Cannon_properties {

    public Properties_Flakvierling(){
        model = new ModelFlakvierling();
        texture = "AAAxis.png";
        name = "flakvierling";
        longName = "Flakvierling-38 AA Gun";
        przedmiot = mod_Vehicles.flakvierling;
        shootSound = "ww2:aafire";
        reloadSound = "planes:mp44reload";
        artWidth = 2.0F;
        artHeight = 1.0F;
        numCargoSlots = 0;
        numBulletSlots = 0;
        numShellSlots = 5;

        MAX_HEALTH = 100;
        recoil = 5;
        damage = 50.0F;
        accuracy = 6.0F;
        velocity = 3.0F;
        range = 20;

        shootDelay = 10;
        numBarrels = 4;
        isAntiAircraft = true;
        gunnerX = 8;
        gunnerY = 28;
        gunnerZ = 8;
        topViewLimit = 75;
        bottomViewLimit = 0;
    }

}
