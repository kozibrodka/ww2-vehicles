package net.kozibrodka.vehicles.properties;

import net.kozibrodka.sdk_api.events.init.ww2Parts;
import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.model.ModelTigerI;
import net.kozibrodka.vehicles.model.ModelTigerII;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;

public class Properties_Tiger1 extends Vehicle_properties{

    public Properties_Tiger1() {

        name = "TigerI";
        longName = "Panzerkampfwagen VI Tiger - Version E";
        texture = "SkinVehicleTigerI.png"; //SkinVehicleTigerI.png"
        model = new ModelTigerI(); //new ModelTigerI()
        SOUND_RIDING = "vehicles:engine";
        shootSound = "vehicles:tankshell";
        SOUND_LOOP_TIME_MAX = 20;
        przedmiot = mod_Vehicles.vehicle_Tiger1;
        gunMachineGun = new ItemInstance(mod_Vehicles.itemGunMachineGun);

        MAX_HEALTH = 1000;
        DEATH_TIME_MAX = 100;
        hasTurret = true;
        hasGuns = true;
        tankDestroyer = false;
        antiAircraft = false;
        vehicleFuelAdd = 1000;
        vehicleShootDelay = 5;
        vehicleShellDelay = 10;
        numCargoSlots = 5;
        numBulletSlots = 5;
        numShellSlots = 5;
        gunDamage = 10;
        gunVelocity = 4F;
        gunSpread = 0.7F;
        autoWidth = 3.0F;
        autoHeight = 3.2F;
        standingOko = 0.625F;

        playerXOffset = 0.0D;
        playerYOffset = 1.8D;
        playerZOffset = 0.0D;
        shellXOffset = 95;
        shellYOffset = 35;
        shellZOffset = 0;
        barrelX = -60;
        barrelY = 3;
        barrelZ = -7;
        gunYawMin = -180;
        gunYawMax = 180;
        gunPitchMin = -45;
        gunPitchMax = 8;

        MAX_SPEED = 0.18D;
        TURN_SPEED_STOPPED = 5D;
        TURN_SPEED_FULL = 2D;
        ACCEL_FORWARD_STOPPED = 0.008D;
        ACCEL_FORWARD_FULL = 0.002D;
        ACCEL_BACKWARD_STOPPED = 0.002D;
        ACCEL_BACKWARD_FULL = 0.0005D;
        ACCEL_BRAKE = 0.04D;
        FALL_SPEED = 0.06D;
        ROTATION_PITCH_DELTA_MAX = 10D;
        SPEED_MULT_WATER = 0.85D;
        SPEED_MULT_UNMOUNTED = 0.95D;
        SPEED_MULT_DECEL = 0.95D;
        STOP_SPEED = 0.01D;
        TURN_SPEED_RENDER_MULT = 2D;
        COLLISION_SPEED_MIN = 0.1D;
        COLLISION_DAMAGE_ENTITY = 10;
        COLLISION_DAMAGE_SELF = 10;
        COLLISION_DAMAGE = true;
        COLLISION_FLIGHT_PLAYER = false;
        COLLISION_FLIGHT_ENTITY = true;

        item_track =  new ItemInstance(ww2Parts.trackPiece.id, 1, 0);
        item_body =  new ItemInstance(ww2Parts.heavyTankBody.id, 1, 0);
        item_mg =  new ItemInstance(ww2Parts.machinegun.id, 1, 0);
        item_turret =  new ItemInstance(ww2Parts.heavyTankTurret.id, 1, 0);
        item_cannon =  new ItemInstance(ItemBase.egg.id, 1, 0);
        dyeColor = new ItemInstance(ItemBase.dyePowder.id, 1, 8);
        itemlist1 = "QW ";
        itemlist2 = "ASD";
        itemlist3 = " X ";
        itemlist4 = " X ";
    }
}
