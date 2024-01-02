package net.kozibrodka.vehicles.properties;

import net.kozibrodka.sdk_api.events.init.ww2Parts;
import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.model.ModelPanzer_Classic;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;

public class PropertiesClassic_Panzer extends Vehicle_properties{

    public PropertiesClassic_Panzer() {
        name = "classic_Panzer";
        longName = "Panzer";
        texture = "Panzer.png";
        model = new ModelPanzer_Classic();
        SOUND_RIDING = "vehicles:engine";
        shootSound = "vehicles:tankshell";
        SOUND_LOOP_TIME_MAX = 20;
        przedmiot = mod_Vehicles.vehicleClassic_Panzer;
        gunMachineGun = new ItemInstance(mod_Vehicles.itemGunMachineGun);

        MAX_HEALTH = 500;
        DEATH_TIME_MAX = 100;
        hasTurret = true;
        hasGuns = true;
        vehicleFuelAdd = 1000;
        vehicleShootDelay = 2;
        vehicleShellDelay = 110;
        numCargoSlots = 5;
        numBulletSlots = 5;
        numShellSlots = 5;
        gunDamage = 12;
        gunVelocity = 3F;
        gunSpread = 1F;
        autoWidth = 2.5F;
        autoHeight = 3.0F;
        standingOko = 0F;

        playerYOffset = 1.8D;
        shellXOffset = 75;
        shellYOffset = 40;
        shellZOffset = 0;
        barrelX = -28;
        barrelY = 5;
        barrelZ = -10;
        gunYawMin = -180;
        gunYawMax = 180;
        gunPitchMin = -45;
        gunPitchMax = 0;

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
        TURN_SPEED_RENDER_MULT = 1D;
        COLLISION_SPEED_MIN = 0.2D;
        COLLISION_DAMAGE_ENTITY = 30;
        COLLISION_DAMAGE_SELF = 10;
        COLLISION_DAMAGE = true;
        COLLISION_FLIGHT_PLAYER = false;
        COLLISION_FLIGHT_ENTITY = true;

        item_track =  new ItemInstance(ww2Parts.trackPiece.id, 1, 0);
        item_body =  new ItemInstance(ww2Parts.lightTankBody.id, 1, 0);
        item_mg =  new ItemInstance(ww2Parts.machinegun.id, 1, 0);
        item_turret =  new ItemInstance(ww2Parts.lightTankTurret.id, 1, 0);
        item_cannon =  new ItemInstance(ItemBase.egg.id, 1, 0);
        dyeColor = new ItemInstance(ItemBase.dyePowder.id, 1, 8);
        itemlist1 = "QW ";
        itemlist2 = "ASD";
        itemlist3 = " X ";
        itemlist4 = " X ";

    }
}
