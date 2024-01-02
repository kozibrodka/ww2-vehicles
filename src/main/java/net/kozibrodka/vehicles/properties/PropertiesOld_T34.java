package net.kozibrodka.vehicles.properties;

import net.kozibrodka.sdk_api.events.init.ww2Parts;
import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.model.ModelT34_Old;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;

public class PropertiesOld_T34 extends Vehicle_properties{

    public PropertiesOld_T34() {
        name = "old_T34";
        longName = "T-34-85";
        texture = "SkinT34.png";
        model = new ModelT34_Old();
        SOUND_RIDING = "vehicles:engine";
        shootSound = "vehicles:tankshell";
        SOUND_LOOP_TIME_MAX = 20;
        przedmiot = mod_Vehicles.vehicleOld_T34;
        gunMachineGun = new ItemInstance(mod_Vehicles.itemGunMachineGun);
        specialWeapon = "haul";

        MAX_HEALTH = 500;
        DEATH_TIME_MAX = 10;
        hasTurret = true;
        hasGuns = true;
        vehicleFuelAdd = 1000;
        vehicleShootDelay = 3;
        vehicleShellDelay = 70;
        numCargoSlots = 5;
        numBulletSlots = 5;
        numShellSlots = 5;
        gunDamage = 8;
        gunVelocity = 3F;
        gunSpread = 2F;
        autoWidth = 2.5F;
        autoHeight = 2.5F;
        standingOko = 0.625F;

        playerYOffset = 1.05D;
        shellXOffset = 70;
        shellYOffset = 20;
        shellZOffset = 0;
        barrelX = -48;
        barrelY = 2;
        barrelZ = -6;
        gunYawMin = -180;
        gunYawMax = 180;
        gunPitchMin = -45;
        gunPitchMax = 5;

        MAX_SPEED = 0.9D; //0.22 //TODO:
        TURN_SPEED_STOPPED = 6D;
        TURN_SPEED_FULL = 2.5D;
        ACCEL_FORWARD_STOPPED = 0.006D;
        ACCEL_FORWARD_FULL = 0.001D;
        ACCEL_BACKWARD_STOPPED = 0.005D;
        ACCEL_BACKWARD_FULL = 0.001D;
        ACCEL_BRAKE = 0.04D;
        FALL_SPEED = 0.06D;
        ROTATION_PITCH_DELTA_MAX = 10D;
        SPEED_MULT_WATER = 0.85D;
        SPEED_MULT_UNMOUNTED = 0.11D;
        SPEED_MULT_DECEL = 0.95D;
        STOP_SPEED = 0.01D;
        TURN_SPEED_RENDER_MULT = 2D;
        COLLISION_SPEED_MIN = 0.1D;
        COLLISION_DAMAGE_ENTITY = 30;
        COLLISION_DAMAGE_SELF = 10;
        COLLISION_DAMAGE = true;
        COLLISION_FLIGHT_PLAYER = false;
        COLLISION_FLIGHT_ENTITY = true;

        item_track =  new ItemInstance(ww2Parts.trackPiece.id, 1, 0);
        item_body =  new ItemInstance(ww2Parts.mediumTankBody.id, 1, 0);
        item_mg =  new ItemInstance(ww2Parts.machinegun.id, 1, 0);
        item_turret =  new ItemInstance(ww2Parts.mediumTankTurret.id, 1, 0);
        item_cannon =  new ItemInstance(ItemBase.egg.id, 1, 0);
        dyeColor = new ItemInstance(ItemBase.dyePowder.id, 1, 10);
        itemlist1 = "QW ";
        itemlist2 = "ASD";
        itemlist3 = " X ";
        itemlist4 = " X ";
    }
}
