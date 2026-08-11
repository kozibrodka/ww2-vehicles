package net.kozibrodka.ww2.properties_unused;

import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.model_unused.ModelHummel_Old;
import net.kozibrodka.ww2.properties.Tank_properties;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PropertiesOld_Hummel extends Tank_properties {

    public PropertiesOld_Hummel() {
        name = "old_Hummel";
        longName = "Hummel";
        texture = "SkinPanzer4.png";
        model = new ModelHummel_Old();
        SOUND_RIDING = "ww2:engine";
        shootSound = "ww2:tankshell";
        SOUND_LOOP_TIME_MAX = 20;
        przedmiot = mod_Vehicles.vehicleOld_Hummel;
        gunMachineGun = new ItemStack(mod_Vehicles.itemGunMachineGun);

        MAX_HEALTH = 300;
        hasTurret = true;
        hasGuns = true;
        tankDestroyer = true;
        vehicleFuelAdd = 1000;
        vehicleShootDelay = 6;
        vehicleShellDelay = 125;
        numCargoSlots = 5;
        numBulletSlots = 5;
        numShellSlots = 5;
        autoWidth = 2.5F;
        autoHeight = 2.5F;
        standingOko = 0.625F;

        playerXOffset = 0;
        playerYOffset = 19;
        playerZOffset = 0;;
        barrelLength = 90;
        barrelX = -48;
        barrelY = 2;
        barrelZ = -8;
        gunYawMin = -180;
        gunYawMax = 180;
        topViewLimit = -45;
        bottomViewLimit = 2;

        MAX_SPEED = 0.10D;
        TURN_SPEED_STOPPED = 2D;
        TURN_SPEED_FULL = 1D;
        ACCEL_FORWARD_STOPPED = 0.005D;
        ACCEL_FORWARD_FULL = 0.001D;
        ACCEL_BACKWARD_STOPPED = 0.002D;
        ACCEL_BACKWARD_FULL = 0.0005D;
        ACCEL_BRAKE = 0.04D;
        FALL_SPEED = 0.06D;
        ROTATION_PITCH_DELTA_MAX = 10D;
        SPEED_MULT_WATER = 0.85D;
        SPEED_MULT_UNMOUNTED = 0.1D;
        SPEED_MULT_DECEL = 0.95D;
        STOP_SPEED = 0.01D;
        TURN_SPEED_RENDER_MULT = 2D;
        COLLISION_SPEED_MIN = 0.1D;
        COLLISION_DAMAGE_ENTITY = 30;
        COLLISION_DAMAGE_SELF = 10;
        COLLISION_DAMAGE = true;
        COLLISION_FLIGHT_ENTITY = true;

        item_track =  new ItemStack(ww2Parts.trackPiece.id, 1, 0);
        item_body =  new ItemStack(ww2Parts.heavyTankBody.id, 1, 0);
        item_mg =  new ItemStack(ww2Parts.machinegun.id, 1, 0);
        item_turret =  new ItemStack(ww2Parts.gunShield.id, 1, 0);
        item_cannon =  new ItemStack(ww2Parts.tankBarrel.id, 1, 0);
        dyeColor = new ItemStack(Item.DYE.id, 1, 8);
        itemlist1 = "QWE";
        itemlist2 = "ASD";
        itemlist3 = " X ";
        itemlist4 = " X ";
    }
}
