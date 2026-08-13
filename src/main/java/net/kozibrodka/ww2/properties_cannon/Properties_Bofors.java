package net.kozibrodka.ww2.properties_cannon;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.model_cannon.ModelBofors;
import net.kozibrodka.ww2.properties.Cannon_properties;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class Properties_Bofors extends Cannon_properties {

    public Properties_Bofors(){
        name = "bofors"; //todo classic_
        longName = "Bofors 40mm AA Gun";
        model = new ModelBofors();
        texture = "AAAllies.png";
        przedmiot = mod_Vehicles.bofors;
        shootSound = "ww2:aafire";
        overheatSound = "planes:barreload";
        isAntiAircraft = true;
        numBarrels = 1;
        barrelLength = 47; ///44
        barrelPivotXOffset = -6;
        barrelPivotYOffset = 10;
        barrelPivotZOffset = 4;
        shellYOffset = new int[numBarrels];
        shellZOffset = new int[numBarrels];
        shellYOffset[0] = -2;
        shellZOffset[0] = 0;

        MAX_HEALTH = 100;
        artWidth = 2.0F;
        artHeight = 2.0F;
        gunnerX = 6;
        gunnerY = -1;
        gunnerZ = -8;
        numCargoSlots = 5;
        numBulletSlots = 5;
        numShellSlots = 5;

        topViewLimit = 90.0F;
        bottomViewLimit = 0;
        maxCannonDeviation = 0F;
        cannonPitchSpeed = 2F;
        cannonYawSpeed = 2F;
        bodyTurnSpeed = 1F;

        shootDelay = 20;
        recoil = 5;
        cannonDamage = 25;
        cannonVehicleDamage = 25;
        cannonPenetration = 2.5F;
        cannonBulletDrop = 0.005F;
        cannonSpread = 1.0F;
        cannonMuzzleVelocity = 3.0F;
        cannonExploPower = 4.0F;
        cannonRange = 20;

        recipelist[0] = "QW ";
        recipelist[1] = "ASD";
        recipelist[2] = "X X";
        recipelist[3] = "X X";
        recipeItem[0] = new ItemStack(ww2Parts.aaBarrel); /// "Q" | body-gun //TODO itemsy z Planes...
        recipeItem[1] = new ItemStack(Item.IRON_INGOT); /// "W" | turret
        recipeItem[3] = new ItemStack(Item.IRON_INGOT); /// "A" | engine
        recipeItem[4] = new ItemStack(Block.IRON_BLOCK); /// "S" | body
        recipeItem[5] = new ItemStack(Item.DYE, 1, 2); /// "D" | symbol
        recipeItem[6] = new ItemStack(ww2Parts.wheel); /// "X" | wheel //todo LARGE WHEEL

    }
}
