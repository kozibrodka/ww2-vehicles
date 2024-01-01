package net.kozibrodka.vehicles.properties;

import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.model.ModelTigerII;
import net.kozibrodka.vehicles.model.ModelVWType82;

public class Properties_Tiger2 extends Vehicle_properties{

    public Properties_Tiger2() {

        name = "TigerII";
        longName = "Panzerkampfwagen VI Tiger II - Version B";
        texture = "SkinVehicleTigerII.png";
        model = new ModelTigerII();
        SOUND_RIDING = "vehicles:engine";
        shootSound = "vehicles:tankshell";
        SOUND_LOOP_TIME_MAX = 20;
        przedmiot = mod_Vehicles.vehicle_Tiger2;

        MAX_HEALTH = 200;
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
        gunDamage = 3F;
        gunVelocity = 3F;
        gunSpread = 1F;
        autoWidth = 2.5F;
        autoHeight = 3.2F;
        standingOko = 0.625F;

        playerXOffset = 0.0D;
        playerYOffset = 1.8D;
        playerZOffset = 0.0D;
        shellXOffset = 70;
        shellYOffset = 35;
        shellZOffset = 0;
        barrelX = -48;
        barrelY = 2;
        barrelZ = -6;
        gunYawMin = -180;
        gunYawMax = 180;
        gunPitchMin = -45;
        gunPitchMax = 8;

        MAX_SPEED = 0.15D;
        TURN_SPEED_STOPPED = 3.5D;
        TURN_SPEED_FULL = 2D;
        ACCEL_FORWARD_STOPPED = 0.005D;
        ACCEL_FORWARD_FULL = 0.001D;
        ACCEL_BACKWARD_STOPPED = 0.002D;
        ACCEL_BACKWARD_FULL = 0.0005D;
        ACCEL_BRAKE = 0.04D;
        FALL_SPEED = 0.06D;
        ROTATION_PITCH_DELTA_MAX = 10D;
        SPEED_MULT_WATER = 0.85D;
        SPEED_MULT_UNMOUNTED = 0.13D; //0.95
        SPEED_MULT_DECEL = 0.95D;
        STOP_SPEED = 0.01D;
        TURN_SPEED_RENDER_MULT = 2D;
        COLLISION_SPEED_MIN = 0.5D;
        COLLISION_DAMAGE_ENTITY = 10;
        COLLISION_DAMAGE_SELF = 10;
        COLLISION_DAMAGE = true;
        COLLISION_FLIGHT_PLAYER = false;
        COLLISION_FLIGHT_ENTITY = false;
    }
}
