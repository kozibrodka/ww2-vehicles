package net.kozibrodka.vehicles.gui;

import net.kozibrodka.vehicles.entity.EntityVehicle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class InventoryVehicle extends ScreenHandler
{

    public InventoryVehicle(Inventory iinventory, EntityVehicle entityvehicle)
    {
        vehicle = entityvehicle;
        addSlot(new Slot(entityvehicle, 0, 8, 53));
        int i = 1;
        for(int j = 0; j < vehicle.automobile.numCargoSlots; j++)
        {
            addSlot(new Slot(entityvehicle, i, 80 + j * 18, 18));
            i++;
        }

        for(int k = 0; k < vehicle.automobile.numBulletSlots; k++)
        {
            addSlot(new Slot(entityvehicle, i, 80 + k * 18, 36));
            i++;
        }

        for(int l = 0; l < vehicle.automobile.numShellSlots; l++)
        {
            addSlot(new Slot(entityvehicle, i, 80 + l * 18, 54));
            i++;
        }

        for(int i1 = 0; i1 < 3; i1++)
        {
            for(int k1 = 0; k1 < 9; k1++)
            {
                addSlot(new Slot(iinventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
            }

        }

        for(int j1 = 0; j1 < 9; j1++)
        {
            addSlot(new Slot(iinventory, j1, 8 + j1 * 18, 142));
        }

    }

    public boolean canUse(PlayerEntity entityplayer)
    {
        return vehicle.canPlayerUse(entityplayer);
    }

    private EntityVehicle vehicle;
}
