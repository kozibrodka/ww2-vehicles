package net.kozibrodka.ww2.properties;


import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.model.ModelCannon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;


public class Cannon_properties {

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
    public float topViewLimit = 90.0F;
    public float bottomViewLimit = 0.0F;
    public float cannonYawSpeed;
    public float cannonPitchSpeed;
    public float maxCannonDeviation;
    public float bodyTurnSpeed;
    public Item przedmiot;

    public int shellYOffset[];
    public int shellZOffset[];

    public int barrelLength;
    public int barrelPivotXOffset ;  /// Z modelu można przekopiować łatwo
    public int barrelPivotYOffset ;
    public int barrelPivotZOffset ;

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
