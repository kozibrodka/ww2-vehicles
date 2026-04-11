package net.kozibrodka.vehicles.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SlotVehicles extends Slot
{

    public SlotVehicles(Inventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }

    public boolean canInsert(ItemStack itemstack)
    {
        return true;
    }
}
