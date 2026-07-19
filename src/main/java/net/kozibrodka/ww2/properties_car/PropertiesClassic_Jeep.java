package net.kozibrodka.ww2.properties_car;

import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.model_car.ModelJeep_Classic;
import net.kozibrodka.ww2.properties.PassengerSeatData;
import net.kozibrodka.ww2.properties.Truck_properties;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PropertiesClassic_Jeep extends Truck_properties {

    public PropertiesClassic_Jeep() {
        name = "classic_Jeep";
        longName = "Willy's Jeep";
        texture = "Jeep.png";
        model = new ModelJeep_Classic();
        SOUND_RIDING = "ww2:engine";
        shootSound = "ww2:tankshell";
        SOUND_LOOP_TIME_MAX = 20;
        przedmiot = mod_Vehicles.vehicleClassic_WillysJeep;

        MAX_HEALTH = 200;
        DEATH_TIME_MAX = 100;
        vehicleFuelAdd = 1000;
        numCargoSlots = 5;
        autoWidth = 1.8F;
        autoHeight = 1.05F;
        standingOko = 0F;
        playerXOffset = -0.3D;
        playerYOffset = 0.4D;
        playerZOffset = 0.5D;

        numPassengers = 3;
        passengerSeats = new PassengerSeatData[numPassengers];
        passengerSeats[0] = new PassengerSeatData(-8D, 6D, -8D);
        passengerSeats[1] = new PassengerSeatData(8D, 6D, 8D);
//        passengerSeats[2] = new PassengerSeatData(8D, 6D, -8D);
        passengerSeats[2] = new PassengerSeatData(16D, 6D, -8D); ///DEBUG - collision test

        MAX_SPEED = 0.5D;
//        TURN_SPEED_STOPPED = 8D;
        TURN_SPEED_STOPPED = 12D;
//        TURN_SPEED_STOPPED = 2D;
//        TURN_SPEED_FULL = 2D;
        TURN_SPEED_FULL = 4D;
        ACCEL_FORWARD_STOPPED = 0.01D;
        ACCEL_FORWARD_FULL = 0.003D;
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
        COLLISION_SPEED_MIN = 0.2D;
        COLLISION_DAMAGE_ENTITY = 10;
        COLLISION_DAMAGE_SELF = 10;
        COLLISION_DAMAGE = true;
        COLLISION_FLIGHT_PLAYER = true;
        COLLISION_FLIGHT_ENTITY = true;
        UPHILL_SLOWDOWN = 0.9D;

        item_body =  new ItemStack(ww2Parts.smallCarChassis.id, 1, 0);
        item_wheel =  new ItemStack(ww2Parts.largeWheel.id, 1, 0); //TODO
        dyeColor = new ItemStack(Item.DYE.id, 1, 2);
        itemlist1 = "   ";
        itemlist2 = "ASD";
        itemlist3 = "X X";
        itemlist4 = "X X";

    }
}
