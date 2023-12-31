package net.kozibrodka.vehicles.recipe;

import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public class SlotVehicles extends Slot
{

    public SlotVehicles(InventoryBase iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }

    public boolean canInsert(ItemInstance itemstack)
    {
        return true;
    }
}
