package net.kozibrodka.ww2.properties_cannon;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.model_cannon.ModelBofors;
import net.kozibrodka.ww2.model_cannon.ModelFlakvierling;
import net.kozibrodka.ww2.properties.Cannon_properties;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class Properties_Flakvierling extends Cannon_properties {

    public Properties_Flakvierling(){
        name = "flakvierling";
        longName = "Flakvierling-38 AA Gun";
        model = new ModelFlakvierling();
        texture = "AAAxis.png";
        przedmiot = mod_Vehicles.flakvierling;
        shootSound = "ww2:aafire";
        overheatSound = "planes:barreload";
        isAntiAircraft = true;
        numBarrels = 4;

        barrelLength = 30; ///debug 30
        barrelPivotXOffset = 0;  /// 1
        barrelPivotYOffset = 14; /// 15
        barrelPivotZOffset = 0;
        shellYOffset = new int[numBarrels];
        shellZOffset = new int[numBarrels];

//        Arrays.fill(shellYOffset, -6);
//        Arrays.fill(shellZOffset, 0);

        shellYOffset[0] = -2;
        shellZOffset[0] = 4;

        shellYOffset[1] = -10;
        shellZOffset[1] = 4;

        shellYOffset[2] = -2;
        shellZOffset[2] = -4;

        shellYOffset[3] = -10;
        shellZOffset[3] = -4;

        MAX_HEALTH = 100;
        artWidth = 2.0F;
        artHeight = 1.0F;
        gunnerX = -2;
        gunnerY = 1;
        gunnerZ = -8;
        numCargoSlots = 0;
        numBulletSlots = 0;
        numShellSlots = 5;

        topViewLimit = 90F;
        bottomViewLimit = 0F;
        maxCannonDeviation = 0F;
        cannonPitchSpeed = 3F;
        cannonYawSpeed = 3F;
        bodyTurnSpeed = 3F;

        shootDelay = 10;
        recoil = 5;
        cannonDamage = 25;
        cannonVehicleDamage = 25;
        cannonPenetration = 2.5F;
        cannonBulletDrop = 0.005F;
        cannonSpread = 0.0F;
        cannonMuzzleVelocity = 3.0F;
        cannonExploPower = 4.0F;
        cannonRange = 20;

        recipelist[0] = "QWQ";
        recipelist[1] = "ASD";
        recipelist[2] = "QCQ";
        recipelist[3] = "   ";
        recipeItem[0] = new ItemStack(ww2Parts.aaBarrel); /// "Q" | body-gun
        recipeItem[1] = new ItemStack(Item.IRON_INGOT); /// "W" | turret
        recipeItem[3] = new ItemStack(Item.IRON_INGOT); /// "A" | engine
        recipeItem[4] = new ItemStack(Block.IRON_BLOCK); /// "S" | body
        recipeItem[5] = new ItemStack(Item.DYE,1,8); /// "D" | symbol
        recipeItem[7] = new ItemStack(Item.LEATHER); /// "C" | track
    }

}
