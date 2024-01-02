package net.kozibrodka.vehicles.properties;


import net.kozibrodka.sdk_api.events.init.ww2Parts;
import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.model.ModelJeep_Classic;
import net.kozibrodka.vehicles.model.ModelKubel_Classic;
import net.kozibrodka.vehicles.model.ModelPanzer_Classic;
import net.kozibrodka.vehicles.model.ModelVWType82;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;

public class PropertiesClassic_Kubelwagen extends Truck_properties{

    public PropertiesClassic_Kubelwagen() {
        name = "classic_Kubel";
        longName = "Kubelwagen";
        texture = "kubelwagen.png";
        model = new ModelKubel_Classic();
        SOUND_RIDING = "vehicles:engine";
        shootSound = "vehicles:tankshell";
        SOUND_LOOP_TIME_MAX = 20;
        przedmiot = mod_Vehicles.vehicleClassic_Kubelwagen;

        MAX_HEALTH = 250;
        DEATH_TIME_MAX = 100;
        vehicleFuelAdd = 1000;
        numCargoSlots = 5;
        autoWidth = 1.5F;
        autoHeight = 1F;
        standingOko = 0F;
        playerYOffset = 0.5D;

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

        item_body =  new ItemInstance(ww2Parts.smallCarChassis.id, 1, 0);
        item_wheel =  new ItemInstance(ww2Parts.largeWheel.id, 1, 0); //TODO
        dyeColor = new ItemInstance(ItemBase.dyePowder.id, 1, 8);
        itemlist1 = "   ";
        itemlist2 = "ASD";
        itemlist3 = "X X";
        itemlist4 = "X X";

    }
}
