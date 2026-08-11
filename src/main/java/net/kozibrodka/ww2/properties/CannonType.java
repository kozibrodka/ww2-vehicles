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

        cannonDamage = klasa.cannonDamage;
        cannonVehicleDamage = klasa.cannonVehicleDamage;
        cannonPenetration = klasa.cannonPenetration;
        cannonBulletDrop = klasa.cannonBulletDrop;
        cannonSpread = klasa.cannonSpread;
        cannonMuzzleVelocity = klasa.cannonMuzzleVelocity;
        cannonExploPower = klasa.cannonExploPower;
        cannonRange = klasa.cannonRange;
        shellXOffset = klasa.shellXOffset;
        shellYOffset = klasa.shellYOffset;
        shellZOffset = klasa.shellZOffset;

        shootDelay = klasa.shootDelay;
        numBarrels = klasa.numBarrels;
        numCargoSlots = klasa.numCargoSlots;
        numBulletSlots = klasa.numBulletSlots;
        numShellSlots = klasa.numShellSlots;
        isAntiAircraft = klasa.isAntiAircraft;
        MAX_HEALTH = klasa.MAX_HEALTH;
        gunnerX = -klasa.gunnerX;  /// Przód(x), Prawo(y) = wartość na plus
        gunnerY = klasa.gunnerY;
        gunnerZ = -klasa.gunnerZ;
        shootSound = klasa.shootSound;
        overheatSound = klasa.overheatSound;
        model = klasa.model;
        texture = klasa.texture;
        name = klasa.name;
        longName = klasa.longName;
        topViewLimit = klasa.topViewLimit;
        bottomViewLimit = klasa.bottomViewLimit;
        cannonYawSpeed = klasa.cannonYawSpeed;
        cannonPitchSpeed = klasa.cannonPitchSpeed;
        maxCannonDeviation = klasa.maxCannonDeviation;
        bodyTurnSpeed = klasa.bodyTurnSpeed;
        przedmiot = klasa.przedmiot;
        types.add(this);
    }

    public float artWidth;
    public float artHeight;
    public int recoil = 5;

    public int cannonDamage;
    public int cannonVehicleDamage;
    public float cannonPenetration;
    public float cannonBulletDrop;
    public float cannonSpread;
    public float cannonMuzzleVelocity;
    public float cannonExploPower;
    public int cannonRange;

    public int shellXOffset[];
    public int shellYOffset[];
    public int shellZOffset[];

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
    public String overheatSound;
    public ModelCannon model;
    public String texture;
    public String name;
    public String longName;
    public float topViewLimit;
    public float bottomViewLimit;
    public float cannonYawSpeed;
    public float cannonPitchSpeed;
    public float maxCannonDeviation;
    public float bodyTurnSpeed;
    public Item przedmiot;
    public static List types = new ArrayList();
}
