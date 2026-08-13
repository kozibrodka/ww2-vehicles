package net.kozibrodka.ww2.gui;

import net.kozibrodka.ww2.events.mod_Vehicles;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SlotBullets extends Slot {

    public SlotBullets(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack itemstack) //todo, for planes albo dodać tu bomby albo dać innego slota.
    {
        int id = itemstack.getItem().id;
        return id == mod_Vehicles.tankBullet.id;
    }
}
