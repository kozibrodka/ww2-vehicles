package net.kozibrodka.vehicles.properties;

import net.kozibrodka.vehicles.model.ModelTruck;
import net.kozibrodka.vehicles.model.ModelVehicle;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

import java.util.ArrayList;
import java.util.List;

public class TruckType {

    public TruckType(Truck_properties klasa)
    {
          name = klasa.name;
          longName = klasa.longName;
          texture = klasa.texture;
          model = klasa.model;
          SOUND_RIDING = klasa.SOUND_RIDING;
          shootSound = klasa.shootSound;
          SOUND_LOOP_TIME_MAX = klasa.SOUND_LOOP_TIME_MAX;
          przedmiot = klasa.przedmiot;

          MAX_HEALTH = klasa.MAX_HEALTH;
          DEATH_TIME_MAX = klasa.DEATH_TIME_MAX;
          vehicleFuelAdd = klasa.vehicleFuelAdd;
          numCargoSlots = klasa.numCargoSlots;
          numBulletSlots = klasa.numBulletSlots;
          numShellSlots = klasa.numShellSlots;

          playerXOffset = klasa.playerXOffset;
          playerYOffset = klasa.playerYOffset;
          playerZOffset = klasa.playerZOffset;
          autoWidth = klasa.autoWidth;
          autoHeight = klasa.autoHeight;
          standingOko = klasa.standingOko;

          dyeColor = klasa.dyeColor;
          item_body = klasa.item_body;
          item_wheel = klasa.item_wheel;
          itemlist1 = klasa.itemlist1;
          itemlist2 = klasa.itemlist2;
          itemlist3 = klasa.itemlist3;
          itemlist4 = klasa.itemlist4;

          ACCEL_FORWARD_STOPPED = klasa.ACCEL_FORWARD_STOPPED;
          ACCEL_FORWARD_FULL = klasa.ACCEL_FORWARD_FULL;
          ACCEL_BACKWARD_STOPPED = klasa.ACCEL_BACKWARD_STOPPED;
          ACCEL_BACKWARD_FULL = klasa.ACCEL_BACKWARD_FULL;
          ACCEL_BRAKE = klasa.ACCEL_BRAKE;
          TURN_SPEED_STOPPED = klasa.TURN_SPEED_STOPPED;
          TURN_SPEED_FULL = klasa.TURN_SPEED_FULL;
          MAX_SPEED = klasa.MAX_SPEED;
          FALL_SPEED = klasa.FALL_SPEED;
          ROTATION_PITCH_DELTA_MAX = klasa.ROTATION_PITCH_DELTA_MAX;
          SPEED_MULT_WATER = klasa.SPEED_MULT_WATER;
          SPEED_MULT_UNMOUNTED = klasa.SPEED_MULT_UNMOUNTED;
          SPEED_MULT_DECEL = klasa.SPEED_MULT_DECEL;
          STOP_SPEED = klasa.STOP_SPEED;
          TURN_SPEED_RENDER_MULT = klasa.TURN_SPEED_RENDER_MULT;
          COLLISION_SPEED_MIN = klasa.COLLISION_SPEED_MIN;
          COLLISION_DAMAGE_ENTITY = klasa.COLLISION_DAMAGE_ENTITY;
          COLLISION_DAMAGE_SELF = klasa.COLLISION_DAMAGE_SELF;
          COLLISION_DAMAGE = klasa.COLLISION_DAMAGE;
          COLLISION_FLIGHT_PLAYER = klasa.COLLISION_FLIGHT_PLAYER;
          COLLISION_FLIGHT_ENTITY = klasa.COLLISION_FLIGHT_ENTITY;

          types.add(this);
    }

    public String name;
    public String longName;
    public String texture;
    public ModelTruck model;
    public String SOUND_RIDING;
    public TemplateItem przedmiot;
    public String shootSound;
    public int SOUND_LOOP_TIME_MAX;
    public static List types = new ArrayList();

    public int MAX_HEALTH;
    public int DEATH_TIME_MAX;
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

    public double ACCEL_FORWARD_STOPPED;
    public double ACCEL_FORWARD_FULL;
    public double ACCEL_BACKWARD_STOPPED;
    public double ACCEL_BACKWARD_FULL;
    public double ACCEL_BRAKE;
    public double TURN_SPEED_STOPPED;
    public double TURN_SPEED_FULL;
    public double MAX_SPEED;
    public double FALL_SPEED;
    public double ROTATION_PITCH_DELTA_MAX;
    public double SPEED_MULT_WATER;
    public double SPEED_MULT_UNMOUNTED;
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
