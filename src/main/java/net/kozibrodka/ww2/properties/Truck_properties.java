package net.kozibrodka.ww2.properties;

import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.model.ModelTruck;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Truck_properties {

    public String name;
    public String longName;
    public String texture;
    public ModelTruck model;
    public String SOUND_RIDING;
    public String shootSound;
    public int SOUND_LOOP_TIME_MAX;
    public Item przedmiot;

    public int MAX_HEALTH;
    public int vehicleFuelAdd;
    public int numCargoSlots;
    public int numBulletSlots;
    public int numShellSlots;
    public float autoWidth;
    public float autoHeight;
    public float standingOko;
    public int playerXOffset;
    public int playerYOffset;
    public int playerZOffset;

    public int numPassengers;
    public PassengerSeatData passengerSeats[];

    public double ACCEL_FORWARD_STOPPED;//ruszenie do przodu
    public double ACCEL_FORWARD_FULL; //jazda do przodu
    public double ACCEL_BACKWARD_STOPPED; //ruszenie do tylu
    public double ACCEL_BACKWARD_FULL; //jazda do tylu
    public double ACCEL_BRAKE; //hamulec
    public double TURN_SPEED_STOPPED; //obrót w miejscu
    public double TURN_SPEED_FULL; //obrót w jezdzie
    public double MAX_SPEED; //MAX
    public double FALL_SPEED;
    public double ROTATION_PITCH_DELTA_MAX;
    public double SPEED_MULT_WATER; //woda
    public double SPEED_MULT_UNMOUNTED; //pchnięcie
    public double SPEED_MULT_DECEL;
    public double STOP_SPEED;
    public double TURN_SPEED_RENDER_MULT;
    public double COLLISION_SPEED_MIN;
    public int COLLISION_DAMAGE_ENTITY;
    public int COLLISION_DAMAGE_SELF;
    public boolean COLLISION_DAMAGE;
    public boolean COLLISION_FLIGHT_PLAYER;
    public boolean COLLISION_FLIGHT_ENTITY;
    public double UPHILL_SLOWDOWN; /// spowolnienie pod górke

    public ItemStack[] recipeItem = new ItemStack[8];
    public String[] recipelist = new String[4];

//    recipelist[0] = "QWE";
//    recipelist[1] = "ASD";
//    recipelist[2] = "XCX";
//    recipelist[3] = "XCX";
//    recipeItem[0] = new ItemStack(ww2Parts.largeEngine); /// "Q" | body-gun
//    recipeItem[1] = new ItemStack(ww2Parts.largeEngine); /// "W" | turret
//    recipeItem[2] = new ItemStack(ww2Parts.largeEngine); /// "E" | turret-gun
//    recipeItem[3] = new ItemStack(ww2Parts.largeEngine); /// "A" | engine
//    recipeItem[4] = new ItemStack(ww2Parts.largeEngine); /// "S" | body
//    recipeItem[5] = new ItemStack(ww2Parts.largeEngine); /// "D" | symbol
//    recipeItem[6] = new ItemStack(ww2Parts.largeEngine); /// "X" | wheel
//    recipeItem[7] = new ItemStack(ww2Parts.largeEngine); /// "C" | track
}
