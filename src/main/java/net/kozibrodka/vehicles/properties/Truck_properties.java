package net.kozibrodka.vehicles.properties;

import net.kozibrodka.vehicles.model.ModelTruck;
import net.kozibrodka.vehicles.model.ModelVehicle;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class Truck_properties {

    public String name;
    public String longName;
    public String texture;
    public ModelTruck model;
    public String SOUND_RIDING;
    public String shootSound;
    public int SOUND_LOOP_TIME_MAX;
    public TemplateItem przedmiot;

    public int MAX_HEALTH;
    public int DEATH_TIME_MAX; //czas do eksplozji
    public int vehicleFuelAdd;
    public int numCargoSlots;
    public int numBulletSlots;
    public int numShellSlots;
    public float autoWidth;
    public float autoHeight;
    public float standingOko;
    public double playerXOffset;
    public double playerYOffset;
    public double playerZOffset;

    public ItemInstance dyeColor;
    public ItemInstance item_body;
    public ItemInstance item_wheel;
    public String itemlist1;
    public String itemlist2;
    public String itemlist3;
    public String itemlist4;

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
}
