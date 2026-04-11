package net.kozibrodka.vehicles.gui;

import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.recipe.SlotVehicles;
import net.kozibrodka.vehicles.recipe.VehicleRecipeRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;


public class CraftingInventoryVehicle extends ScreenHandler {

    public CraftingInventoryVehicle(PlayerInventory inventoryplayer, World worldy, int i, int j, int k) {
        this.inventory = inventoryplayer;
        this.craftMatrix = new CraftingInventory(this, 3, 4);
        this.craftResult = new CraftingResultInventory();
        this.world = worldy;
        this.xTile = i;
        this.yTile = j;
        this.zTile = k;
        this.addSlot(new CraftingResultSlot(inventoryplayer.player, craftMatrix, craftResult, 0, 127, 54));

        int col;
        int col1;
        for(col = 0; col < 4; ++col) {
            for(col1 = 0; col1 < 3; ++col1) {
                this.addSlot(new SlotVehicles(craftMatrix, col1 + col * 3, 32 + col1 * 18, 18 + col * 18));
            }
        }

        for(col = 0; col < 3; ++col) {
            for(col1 = 0; col1 < 9; ++col1) {
                this.addSlot(new Slot(inventoryplayer, col1 + col * 9 + 9, 8 + col1 * 18, 102 + col * 18));
            }
        }

        for(col = 0; col < 9; ++col) {
            this.addSlot(new Slot(inventoryplayer, col, 8 + col * 18, 160));
        }

        this.onSlotUpdate(this.craftMatrix);
    }

//    public ItemStack transferStackInSlot(int i) {
//        return null;
//    }

    public void onSlotUpdate(Inventory iinventory)
    {
//        craftResult.setInventoryItem(0, RecipeRegistry.getInstance().getCraftingOutput(craftMatrix));
        craftResult.setStack(0, VehicleRecipeRegistry.getInstance().getCraftingOutput(craftMatrix));
    }


    public void onClosed(PlayerEntity entityplayer)
    {
        super.onClosed(entityplayer);
        for(int i = 0; i < 12; i++)
        {
            ItemStack itemstack = craftMatrix.getStack(i);
            if(itemstack != null)
            {
                entityplayer.dropItem(itemstack);
            }
        }

    }

    public boolean canUse(PlayerEntity entityplayer)
    {
        if(world.getBlockId(xTile, yTile, zTile) != mod_Vehicles.vehicleWorkbench.id)
        {
            return false;
        } else
        {
            return entityplayer.getSquaredDistance((double)xTile + 0.5D, (double)yTile + 0.5D, (double)zTile + 0.5D) <= 64D;
        }
    }

    public PlayerInventory inventory;
    public CraftingInventory craftMatrix;
    public Inventory craftResult;
    public World world;
    private int xTile;
    private int yTile;
    private int zTile;
}
