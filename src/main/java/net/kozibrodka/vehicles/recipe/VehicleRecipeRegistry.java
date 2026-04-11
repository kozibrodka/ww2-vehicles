package net.kozibrodka.vehicles.recipe;

import net.kozibrodka.sdk_api.events.init.ww2Parts;
import net.kozibrodka.vehicles.properties.TruckType;
import net.kozibrodka.vehicles.properties.VehicleType;
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

        for (int i = 0; i < VehicleType.types.size(); i++) { //VehicleType.types.size()
            VehicleType vehicleType = (VehicleType) VehicleType.types.get(i);

            this.addShapedRecipeVehicle(new ItemStack(vehicleType.przedmiot, 1,1), vehicleType.itemlist1, vehicleType.itemlist2, vehicleType.itemlist3, vehicleType.itemlist4,
                    'Q', vehicleType.item_mg,'W', vehicleType.item_turret,'E', vehicleType.item_cannon,'A', ww2Parts.smallEngine,'S', vehicleType.item_body,'D', vehicleType.dyeColor,'X', ww2Parts.trackPiece);

            this.addShapedRecipeVehicle(new ItemStack(vehicleType.przedmiot, 1,1), vehicleType.itemlist1, vehicleType.itemlist2, vehicleType.itemlist3, vehicleType.itemlist4,
                    'Q', vehicleType.item_mg,'W', vehicleType.item_turret,'E', vehicleType.item_cannon,'A', ww2Parts.mediumEngine,'S', vehicleType.item_body,'D', vehicleType.dyeColor,'X', ww2Parts.trackPiece);

            this.addShapedRecipeVehicle(new ItemStack(vehicleType.przedmiot, 1,1), vehicleType.itemlist1, vehicleType.itemlist2, vehicleType.itemlist3, vehicleType.itemlist4,
                    'Q', vehicleType.item_mg,'W', vehicleType.item_turret,'E', vehicleType.item_cannon,'A', ww2Parts.largeEngine,'S', vehicleType.item_body,'D', vehicleType.dyeColor,'X', ww2Parts.trackPiece);
        }

        for (int i = 0; i < TruckType.types.size(); i++) { //VehicleType.types.size()
            TruckType vehicleType = (TruckType) TruckType.types.get(i);

            this.addShapedRecipeVehicle(new ItemStack(vehicleType.przedmiot, 1,1), vehicleType.itemlist1, vehicleType.itemlist2, vehicleType.itemlist3, vehicleType.itemlist4,
                    'A', ww2Parts.smallEngine,'S', vehicleType.item_body,'D', vehicleType.dyeColor,'X', vehicleType.item_wheel);

            this.addShapedRecipeVehicle(new ItemStack(vehicleType.przedmiot, 1,1), vehicleType.itemlist1, vehicleType.itemlist2, vehicleType.itemlist3, vehicleType.itemlist4,
                    'A', ww2Parts.mediumEngine,'S', vehicleType.item_body,'D', vehicleType.dyeColor,'X', vehicleType.item_wheel);

            this.addShapedRecipeVehicle(new ItemStack(vehicleType.przedmiot, 1,1), vehicleType.itemlist1, vehicleType.itemlist2, vehicleType.itemlist3, vehicleType.itemlist4,
                    'A', ww2Parts.largeEngine,'S', vehicleType.item_body,'D', vehicleType.dyeColor,'X', vehicleType.item_wheel);
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

    public List getRecipeList()
    {
        return vehicle_recipes;
    }

    private static final VehicleRecipeRegistry INSTANCE = new VehicleRecipeRegistry();
    public static final VehicleRecipeRegistry getInstance() {
        return INSTANCE;
    }
    private List vehicle_recipes = new ArrayList();
    public List getRecipes() {
        return this.vehicle_recipes;
    }
}
