package net.kozibrodka.vehicles.gui;

import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.recipe.SlotVehicles;
import net.kozibrodka.vehicles.recipe.VehicleRecipeRegistry;
import net.minecraft.container.ContainerBase;
import net.minecraft.container.slot.CraftingResult;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Chest;
import net.minecraft.inventory.Crafting;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;


public class CraftingInventoryVehicle extends ContainerBase {

    public CraftingInventoryVehicle(PlayerInventory inventoryplayer, Level worldy, int i, int j, int k) {
        this.inventory = inventoryplayer;
        this.craftMatrix = new Crafting(this, 3, 4);
        this.craftResult = new Chest();
        this.world = worldy;
        this.xTile = i;
        this.yTile = j;
        this.zTile = k;
        this.addSlot(new CraftingResult(inventoryplayer.player, craftMatrix, craftResult, 0, 127, 54));

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

        this.onContentsChanged(this.craftMatrix);
    }

//    public ItemStack transferStackInSlot(int i) {
//        return null;
//    }

    public void onContentsChanged(InventoryBase iinventory)
    {
//        craftResult.setInventoryItem(0, RecipeRegistry.getInstance().getCraftingOutput(craftMatrix));
        craftResult.setInventoryItem(0, VehicleRecipeRegistry.getInstance().getCraftingOutput(craftMatrix));
    }


    public void onClosed(PlayerBase entityplayer)
    {
        super.onClosed(entityplayer);
        for(int i = 0; i < 12; i++)
        {
            ItemInstance itemstack = craftMatrix.getInventoryItem(i);
            if(itemstack != null)
            {
                entityplayer.dropItem(itemstack);
            }
        }

    }

    public boolean canUse(PlayerBase entityplayer)
    {
        if(world.getTileId(xTile, yTile, zTile) != mod_Vehicles.vehicleWorkbench.id)
        {
            return false;
        } else
        {
            return entityplayer.squaredDistanceTo((double)xTile + 0.5D, (double)yTile + 0.5D, (double)zTile + 0.5D) <= 64D;
        }
    }

    public PlayerInventory inventory;
    public Crafting craftMatrix;
    public InventoryBase craftResult;
    public Level world;
    private int xTile;
    private int yTile;
    private int zTile;
}
