package net.kozibrodka.ww2.properties;

import net.kozibrodka.ww2.model.ModelCannon;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class CannonType {

    public CannonType(Cannon_properties klasa){
        artWidth = klasa.artWidth;
        artHeight = klasa.artHeight;
        recoil = klasa.recoil;
        accuracy = klasa.accuracy;
        velocity = klasa.velocity;
        range = klasa.range;
        damage = klasa.damage;
        shootDelay = klasa.shootDelay;
        numBarrels = klasa.numBarrels;
        numCargoSlots = klasa.numCargoSlots;
        numBulletSlots = klasa.numBulletSlots;
        numShellSlots = klasa.numShellSlots;
        isAntiAircraft = klasa.isAntiAircraft;
        MAX_HEALTH = klasa.MAX_HEALTH;
        gunnerX = klasa.gunnerX;
        gunnerY = klasa.gunnerY;
        gunnerZ = klasa.gunnerZ;
        shootSound = klasa.shootSound;
        reloadSound = klasa.reloadSound;
        model = klasa.model;
        texture = klasa.texture;
        name = klasa.name;
        longName = klasa.longName;
        topViewLimit = klasa.topViewLimit;
        bottomViewLimit = klasa.bottomViewLimit;
        przedmiot = klasa.przedmiot;
        types.add(this);
    }

    public float artWidth;
    public float artHeight;
    public int recoil = 5;
    public float accuracy;
    public float velocity;
    public int range;
    public float damage;
    public int shootDelay;
    public int numBarrels;
    public boolean isAntiAircraft;
    public int MAX_HEALTH;
    public int gunnerX;
    public int gunnerY;
    public int gunnerZ;
    public int numCargoSlots;
    public int numBulletSlots;
    public int numShellSlots;
    public String shootSound;
    public String reloadSound; //todo reload sound do wyjebanie.... ale będą inne dźwięki więc zrobie refactor rename
    public ModelCannon model;
    public String texture;
    public String name;
    public String longName;
    public float topViewLimit = 75.0F;
    public float bottomViewLimit = 0.0F;
    public Item przedmiot;
    public static List types = new ArrayList();
}
