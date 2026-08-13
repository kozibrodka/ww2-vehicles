package net.kozibrodka.ww2.recipe;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.properties.CannonType;
import net.kozibrodka.ww2.properties.TruckType;
import net.kozibrodka.ww2.properties.TankType;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VehicleRecipeRegistry {

    String chuj = "#####";

    public void initVehicleRecipe()
    {
//        for (int i = 0; i < VehicleType.types.size(); i++) {
//            VehicleType vehicletype = (VehicleType) VehicleType.types.get(i);
//            if(vehicletype.name == "old_M41") {
//                this.addShapedRecipeVehicle(new ItemInstance(vehicletype.przedmiot, 1, 1), "X", "#", 'X', ItemBase.sugar, '#', ItemBase.coal);
//                this.addShapedRecipeVehicle(new ItemInstance(vehicletype.przedmiot, 1, 2), "X", "#", 'X', ItemBase.sugar, '#', ItemBase.ironIngot);
//                this.addShapedRecipeVehicle(new ItemInstance(vehicletype.przedmiot, 1, 3), "X", "#", 'X', ItemBase.sugar, '#', ItemBase.goldIngot);
//                this.addShapedRecipeVehicle(new ItemInstance(vehicletype.przedmiot, 1, 4), "X", "#", 'X', ItemBase.sugar, '#', ItemBase.diamond);
//            }
//        }
//
//            this.addShapedRecipeVehicle(new ItemInstance(ItemBase.ironAxe, 1), "W", 'W', ItemBase.arrow);
        initTankRecipe();
        initTruckRecipe();
        initArtilleryRecipe();
    }

    public void initTankRecipe() {
        if (mod_Vehicles.ww2Glass.register_TANK) {
            for (int i = 0; i < TankType.types.size(); i++) {
                TankType tankType = (TankType) TankType.types.get(i);
                if(tankType.przedmiot == null){return;} /// null-check dla możliwości dodania typu "widmo"
                this.addShapedRecipeVehicle(new ItemStack(tankType.przedmiot, 1, 1), tankType.recipelist[0], tankType.recipelist[1], tankType.recipelist[2], tankType.recipelist[3],
                        'Q', tankType.recipeItem[0], 'W', tankType.recipeItem[1], 'E', tankType.recipeItem[2], 'A', ww2Parts.smallEngine, 'S', tankType.recipeItem[4], 'D', tankType.recipeItem[5], 'X', tankType.recipeItem[6], 'C', tankType.recipeItem[7]);
                this.addShapedRecipeVehicle(new ItemStack(tankType.przedmiot, 1, 1), tankType.recipelist[0], tankType.recipelist[1], tankType.recipelist[2], tankType.recipelist[3],
                        'Q', tankType.recipeItem[0], 'W', tankType.recipeItem[1], 'E', tankType.recipeItem[2], 'A', ww2Parts.mediumEngine, 'S', tankType.recipeItem[4], 'D', tankType.recipeItem[5], 'X', tankType.recipeItem[6], 'C', tankType.recipeItem[7]);
                this.addShapedRecipeVehicle(new ItemStack(tankType.przedmiot, 1, 1), tankType.recipelist[0], tankType.recipelist[1], tankType.recipelist[2], tankType.recipelist[3],
                        'Q', tankType.recipeItem[0], 'W', tankType.recipeItem[1], 'E', tankType.recipeItem[2], 'A', ww2Parts.largeEngine, 'S', tankType.recipeItem[4], 'D', tankType.recipeItem[5], 'X', tankType.recipeItem[6], 'C', tankType.recipeItem[7]);

//                this.addShapedRecipeVehicle(new ItemStack(tankType.przedmiot, 1, 1), tankType.itemlist1, tankType.itemlist2, tankType.itemlist3, tankType.itemlist4,
//                        'Q', tankType.item_mg, 'W', tankType.item_turret, 'E', tankType.item_cannon, 'A', ww2Parts.smallEngine, 'S', tankType.item_body, 'D', tankType.dyeColor, 'X', tankType.item_track);
//                this.addShapedRecipeVehicle(new ItemStack(tankType.przedmiot, 1, 2), tankType.itemlist1, tankType.itemlist2, tankType.itemlist3, tankType.itemlist4,
//                        'Q', tankType.item_mg, 'W', tankType.item_turret, 'E', tankType.item_cannon, 'A', ww2Parts.mediumEngine, 'S', tankType.item_body, 'D', tankType.dyeColor, 'X', tankType.item_track);
//                this.addShapedRecipeVehicle(new ItemStack(tankType.przedmiot, 1, 3), tankType.itemlist1, tankType.itemlist2, tankType.itemlist3, tankType.itemlist4,
//                        'Q', tankType.item_mg, 'W', tankType.item_turret, 'E', tankType.item_cannon, 'A', ww2Parts.largeEngine, 'S', tankType.item_body, 'D', tankType.dyeColor, 'X', tankType.item_track);
            }
        }
    }

    public void initTruckRecipe(){
        if (mod_Vehicles.ww2Glass.register_TRUCK) {
            for (int i = 0; i < TruckType.types.size(); i++) {
                TruckType truckType = (TruckType) TruckType.types.get(i);
                if(truckType.przedmiot == null){return;}
                this.addShapedRecipeVehicle(new ItemStack(truckType.przedmiot, 1, 1), truckType.recipelist[0], truckType.recipelist[1], truckType.recipelist[2], truckType.recipelist[3],
                        'Q', truckType.recipeItem[0], 'W', truckType.recipeItem[1], 'E', truckType.recipeItem[2], 'A', ww2Parts.smallEngine, 'S', truckType.recipeItem[4], 'D', truckType.recipeItem[5], 'X', truckType.recipeItem[6], 'C', truckType.recipeItem[7]);
                this.addShapedRecipeVehicle(new ItemStack(truckType.przedmiot, 1, 1), truckType.recipelist[0], truckType.recipelist[1], truckType.recipelist[2], truckType.recipelist[3],
                        'Q', truckType.recipeItem[0], 'W', truckType.recipeItem[1], 'E', truckType.recipeItem[2], 'A', ww2Parts.mediumEngine, 'S', truckType.recipeItem[4], 'D', truckType.recipeItem[5], 'X', truckType.recipeItem[6], 'C', truckType.recipeItem[7]);
                this.addShapedRecipeVehicle(new ItemStack(truckType.przedmiot, 1, 1), truckType.recipelist[0], truckType.recipelist[1], truckType.recipelist[2], truckType.recipelist[3],
                        'Q', truckType.recipeItem[0], 'W', truckType.recipeItem[1], 'E', truckType.recipeItem[2], 'A', ww2Parts.largeEngine, 'S', truckType.recipeItem[4], 'D', truckType.recipeItem[5], 'X', truckType.recipeItem[6], 'C', truckType.recipeItem[7]);
//                this.addShapedRecipeVehicle(new ItemStack(truckType.przedmiot, 1, 1), truckType.itemlist1, truckType.itemlist2, truckType.itemlist3, truckType.itemlist4,
//                        'A', ww2Parts.smallEngine, 'S', truckType.item_body, 'D', truckType.dyeColor, 'X', truckType.item_wheel);
//                this.addShapedRecipeVehicle(new ItemStack(truckType.przedmiot, 1, 2), truckType.itemlist1, truckType.itemlist2, truckType.itemlist3, truckType.itemlist4,
//                        'A', ww2Parts.mediumEngine, 'S', truckType.item_body, 'D', truckType.dyeColor, 'X', truckType.item_wheel);
//                this.addShapedRecipeVehicle(new ItemStack(truckType.przedmiot, 1, 3), truckType.itemlist1, truckType.itemlist2, truckType.itemlist3, truckType.itemlist4,
//                        'A', ww2Parts.largeEngine, 'S', truckType.item_body, 'D', truckType.dyeColor, 'X', truckType.item_wheel);
            }
        }
    }

    public void initArtilleryRecipe(){
        if (mod_Vehicles.ww2Glass.register_CANNON) {
            for (int i = 0; i < CannonType.types.size(); i++) {
                CannonType canType = (CannonType) CannonType.types.get(i);
                if(canType.przedmiot == null){return;}
                this.addShapedRecipeVehicle(new ItemStack(canType.przedmiot, 1, 1), canType.recipelist[0], canType.recipelist[1], canType.recipelist[2], canType.recipelist[3],
                        'Q', canType.recipeItem[0], 'W', canType.recipeItem[1], 'E', canType.recipeItem[2], 'A', canType.recipeItem[3], 'S', canType.recipeItem[4], 'D', canType.recipeItem[5], 'X', canType.recipeItem[6], 'C', canType.recipeItem[7]);
            }
        }
    }


    void addShapedRecipeVehicle(ItemStack arg, Object... objects) {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        if (objects[var4] instanceof String[]) {
            String[] var11 = (String[])((String[])objects[var4++]);

            for(int var8 = 0; var8 < var11.length; ++var8) {
                String var9 = var11[var8];
                ++var6;
                var5 = var9.length();
                var3 = var3 + var9;
            }
        } else {
            while(objects[var4] instanceof String) {
                String var7 = (String)objects[var4++];
                ++var6;
                var5 = var7.length();
                var3 = var3 + var7;
            }
        }

        HashMap var12;
        for(var12 = new HashMap(); var4 < objects.length; var4 += 2) {
            Character var13 = (Character)objects[var4];
            ItemStack var15 = null;
            if (objects[var4 + 1] instanceof Item) {
                var15 = new ItemStack((Item)objects[var4 + 1]);
            } else if (objects[var4 + 1] instanceof Block) {
                var15 = new ItemStack((Block)objects[var4 + 1], 1, -1);
            } else if (objects[var4 + 1] instanceof ItemStack) {
                var15 = (ItemStack)objects[var4 + 1];
            }

            var12.put(var13, var15);
        }

        ItemStack[] var14 = new ItemStack[var5 * var6];

        for(int var16 = 0; var16 < var5 * var6; ++var16) {
            char var10 = var3.charAt(var16);
            if (var12.containsKey(var10)) {
                var14[var16] = ((ItemStack)var12.get(var10)).copy();
            } else {
                var14[var16] = null;
            }
        }

        this.vehicle_recipes.add(new VehicleShapedRecipe(var5, var6, var14, arg));
    }

    public ItemStack getCraftingOutput(CraftingInventory arg) {
        for(int var2 = 0; var2 < this.vehicle_recipes.size(); ++var2) {
            VehicleShapedRecipe var3 = (VehicleShapedRecipe)this.vehicle_recipes.get(var2);
            if (var3.canCraft(arg)) {
                return var3.craft(arg);
            }
        }

        return null;
    }

//    public List getRecipeList()
//    {
//        return vehicle_recipes;
//    }

    private static final VehicleRecipeRegistry INSTANCE = new VehicleRecipeRegistry();
    public static final VehicleRecipeRegistry getInstance() {
        return INSTANCE;
    }
    private List vehicle_recipes = new ArrayList();
    public List getRecipes() {
        return this.vehicle_recipes;
    }

    /// for AMI class only
    public ArrayList getShapedRecipes() {
        ArrayList shapedRecipes = new ArrayList();
        for (Object recipe : vehicle_recipes) {
            if (recipe instanceof VehicleShapedRecipe) {
                shapedRecipes.add(recipe);
            }
        }
        return shapedRecipes;
    }
}
