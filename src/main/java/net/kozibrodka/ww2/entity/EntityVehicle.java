package net.kozibrodka.ww2.entity;

import net.kozibrodka.ww2.gui.InventoryTruck;
import net.kozibrodka.ww2.gui.InventoryVehicle;
import net.kozibrodka.ww2.properties.TankType;
import net.kozibrodka.ww2.properties.TruckType;
import net.kozibrodka.ww2.properties.VehicleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

//TODO ta klasa może implementować SDKVehicle... - wtedy Pass seat będzie miało łatwiej. - stopniowo zwiększać udział tej klasy.
public class EntityVehicle extends Entity implements Inventory {

    public EntityVehicle(World world) {
        super(world);
    }

    ///
    public VehicleData guiData;
    public int vehicleFuel;
    public ItemStack[] cargoItems;
    public int inventorySize;
    ///
    public int slots_Fuel = 0;
    public int slots_FirstCargo = 1;
    public int slots_FirstBullet;
    public int slots_FirstShell;
    public int slots_Last;
    ///


    public void setDataFromTruck(TruckType truckType){
        guiData = new VehicleData(truckType.longName, truckType.numCargoSlots, truckType.numBulletSlots, truckType.numShellSlots, truckType.vehicleFuelAdd);
        slots_FirstBullet = guiData.numCargoSlots + 1;
        slots_FirstShell = guiData.numCargoSlots + guiData.numBulletSlots + 1;
        slots_Last = guiData.numCargoSlots + guiData.numBulletSlots + guiData.numShellSlots + 1;
        inventorySize = slots_Last;
    }

    public void setDataFromTank(TankType tankType){
        guiData = new VehicleData(tankType.longName, tankType.numCargoSlots, tankType.numBulletSlots, tankType.numShellSlots, tankType.vehicleFuelAdd);
        slots_FirstBullet = guiData.numCargoSlots + 1;
        slots_FirstShell = guiData.numCargoSlots + guiData.numBulletSlots + 1;
        slots_Last = guiData.numCargoSlots + guiData.numBulletSlots + guiData.numShellSlots + 1;
        inventorySize = slots_Last;
    }

    /// INVENTORY START
    public int getBurnTimeRemainingScaled(int i)
    {
        return (vehicleFuel * i) / guiData.vehicleFuelAdd;
    }

    public boolean isFuelled()
    {
        return vehicleFuel > 0;
    }

    @Override
    public int size()
    {
        return inventorySize;
    }

    @Override
    public ItemStack getStack(int i)
    {
        return cargoItems[i];
    }

    @Override
    public ItemStack removeStack(int i, int j)
    {
        if(cargoItems[i] != null)
        {
            if(cargoItems[i].count <= j)
            {
                ItemStack itemstack = cargoItems[i];
                cargoItems[i] = null;
                checkAmmoPresence();
                return itemstack;
            }
            ItemStack itemstack1 = cargoItems[i].split(j);
            if(cargoItems[i].count == 0)
            {
                cargoItems[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }
    }

    @Override
    public void setStack(int i, ItemStack itemstack)
    {
        cargoItems[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxCountPerStack())
        {
            itemstack.count = getMaxCountPerStack();
        }
        if(itemstack != null && itemstack.itemId == 263 && i == 0 && passenger != null && (passenger instanceof PlayerEntity))
        {
//            ((PlayerBase)passenger).increaseStat(mod_Planes.startPlane, 1); //TODO: achievementsy - overrite setStack dla poszczególnych, daj super() i dodaj kod achievementa
        }
        if(itemstack != null){
            checkAmmoPresence();
        }
    }

    public void checkAmmoPresence(){
    }

    @Override
    public String getName()
    {
        return guiData.longName;
    }

    @Override
    public int getMaxCountPerStack()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public boolean canPlayerUse(PlayerEntity entityplayer)
    {
        return entityplayer.getSquaredDistance(x, y, z) <= 64D;
    }
    /// INVENTORY STOP

    ///
    @Override
    protected void initDataTracker() {}
    @Override
    protected void readNbt(NbtCompound nbt) {}
    @Override
    protected void writeNbt(NbtCompound nbt) {}
}
