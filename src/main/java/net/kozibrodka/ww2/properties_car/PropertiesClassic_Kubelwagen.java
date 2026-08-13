package net.kozibrodka.ww2.properties_car;

import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.model_car.ModelKubel_Classic;
import net.kozibrodka.ww2.properties.PassengerSeatData;
import net.kozibrodka.ww2.properties.Truck_properties;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PropertiesClassic_Kubelwagen extends Truck_properties {

    public PropertiesClassic_Kubelwagen() {
        name = "classic_Kubel";
        longName = "Kubelwagen";
        texture = "kubelwagen.png";
        if(SdkEnvTool.isEnvClient()) {
            model = new ModelKubel_Classic();
        }
        SOUND_RIDING = "ww2:engine";
        shootSound = "ww2:tankshell";
        SOUND_LOOP_TIME_MAX = 20;
        przedmiot = mod_Vehicles.vehicleClassic_Kubelwagen;

        MAX_HEALTH = 250;
        vehicleFuelAdd = 1000;
        numShellSlots = 5;
        autoWidth = 1.8F;
        autoHeight = 1.05F;
        standingOko = 0F;
        playerXOffset = 2;
        playerYOffset = -2;
        playerZOffset = -8;

        numPassengers = 3;
        passengerSeats = new PassengerSeatData[numPassengers];
        passengerSeats[0] = new PassengerSeatData(0,-5D, 6D, -8D);
        passengerSeats[1] = new PassengerSeatData(1,10D, 6D, 8D);
        passengerSeats[2] = new PassengerSeatData(2,10D, 6D, -8D);

        MAX_SPEED = 0.5D;
        TURN_SPEED_STOPPED = 8D;
        TURN_SPEED_FULL = 2D;
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
        COLLISION_FLIGHT_PLAYER = false;
        COLLISION_FLIGHT_ENTITY = true;

        recipelist[0] = "   ";
        recipelist[1] = "ASD";
        recipelist[2] = "X X";
        recipelist[3] = "X X";
        recipeItem[4] = new ItemStack(ww2Parts.smallCarChassis); /// "S" | body
        recipeItem[5] = new ItemStack(Item.DYE, 1, 8); /// "D" | symbol
        recipeItem[6] = new ItemStack(ww2Parts.largeWheel); /// "X" | wheel

    }
}
