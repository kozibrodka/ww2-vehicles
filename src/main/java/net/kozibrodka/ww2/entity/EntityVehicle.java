package net.kozibrodka.ww2.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk_api.utils.SdkVehicle;
import net.kozibrodka.ww2.gui.InventoryVehicle;
import net.kozibrodka.ww2.properties.CannonType;
import net.kozibrodka.ww2.properties.TankType;
import net.kozibrodka.ww2.properties.TruckType;
import net.kozibrodka.ww2.properties.VehicleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;


//TODO ta klasa może implementować SDKVehicle... - wtedy Pass seat będzie miało łatwiej. - stopniowo zwiększać udział tej klasy.
public class EntityVehicle extends Entity implements Inventory, SdkVehicle {

    public EntityVehicle(World world) {
        super(world);
        ignoreFrustumCull = true;
        blocksSameBlockSpawning = true;
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
    /// INIT DATA
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

    public void setDataFromCannon(CannonType cannonType){
        guiData = new VehicleData(cannonType.longName, cannonType.numCargoSlots, cannonType.numBulletSlots, cannonType.numShellSlots, true);
        slots_FirstBullet = guiData.numCargoSlots + 1;
        slots_FirstShell = guiData.numCargoSlots + guiData.numBulletSlots + 1;
        slots_Last = guiData.numCargoSlots + guiData.numBulletSlots + guiData.numShellSlots + 1;
        inventorySize = slots_Last;
    }
    /// INVENTORY
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
        if(itemstack != null && !world.isRemote){
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
    /// CLIENT INTERPOLATION
    @Environment(EnvType.CLIENT)
    protected int clientInterpolationSteps;
    @Environment(EnvType.CLIENT)
    protected double clientX;
    @Environment(EnvType.CLIENT)
    protected double clientY;
    @Environment(EnvType.CLIENT)
    protected double clientZ;
    @Environment(EnvType.CLIENT)
    protected double clientYaw;
    @Environment(EnvType.CLIENT)
    protected double clientPitch;
    @Environment(EnvType.CLIENT)
    protected double clientPrevY;
    @Environment(EnvType.CLIENT)
    protected double clientVelocityX;
    @Environment(EnvType.CLIENT)
    protected double clientVelocityY;
    @Environment(EnvType.CLIENT)
    protected double clientVelocityZ;
    protected boolean lastOnClientGround;

    @Override
    @Environment(EnvType.CLIENT)
    public void setPositionAndAnglesAvoidEntities(double x, double y, double z, float pitch, float yaw, int interpolationSteps) {
        clientX = x;
        clientY = y;
        clientZ = z;
        clientYaw = pitch;
        clientPitch = yaw;
        clientInterpolationSteps = interpolationSteps + 1;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setVelocityClient(double x, double y, double z) {
        clientVelocityX = x;
        clientVelocityY = y;
        clientVelocityZ = z;
    }
    /// SDK INTERFACE
    boolean clientFORWARD = false;
    boolean clientBACK = false;
    boolean clientLEFT= false;
    boolean clientRIGHT= false;
    boolean clientUP= false;
    boolean clientDOWN= false;
    boolean clientFIRE= false;

    @Override
    public void setControls(boolean forward, boolean back, boolean left, boolean right, boolean up, boolean down, boolean fire) {
        clientFORWARD = forward;
        clientBACK = back;
        clientLEFT= left;
        clientRIGHT= right;
        clientUP= up;
        clientDOWN= down;
        clientFIRE= fire;
    }

    @Override
    public void reloadKey() {}

    @Override
    public void exitKey(PlayerEntity playerEntity) {}

    @Override
    public void inventoryKey(PlayerEntity playerEntity) {
        GuiHelper.openGUI(
                playerEntity,
                Identifier.of(Namespace.of("ww2"), "openVehicle"),
                this,
                new InventoryVehicle(playerEntity.inventory, this)
        );
    }

    @Override
    public void bombKey() {}

    @Override
    public void rocketKey() {}

    @Override
    public int getPercentHealth() {return 0;}

    @Override
    public float getArmorFactor() {return 0;}

    @Override
    public float getDmgReduce() {return 0;}

    @Override
    public float getDmgBroken() {return 0;}

    @Override
    public String getAmmoName() {return "";}

    @Override
    public String getBombName() {return "";}

    @Override
    public boolean canPassengerUseGun() {return false;}
    ///
    @Override
    protected void initDataTracker() {}
    @Override
    protected void readNbt(NbtCompound nbt) {
        NbtList nbttaglist = nbt.getList("Items");
        cargoItems = new ItemStack[size()];
        for(int i = 0; i < nbttaglist.size(); i++)
        {
            NbtCompound nbttagcompound1 = (NbtCompound)nbttaglist.get(i);
            int k = nbttagcompound1.getByte("Slot") & 0xff;
            if(k >= 0 && k < cargoItems.length)
            {
                cargoItems[k] = new ItemStack(nbttagcompound1);
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        NbtList nbttaglist = new NbtList();
        for(int i = 0; i < cargoItems.length; i++)
        {
            if(cargoItems[i] != null)
            {
                NbtCompound nbttagcompound1 = new NbtCompound();
                nbttagcompound1.putByte("Slot", (byte)i);
                cargoItems[i].writeNbt(nbttagcompound1);
                nbttaglist.add(nbttagcompound1);
            }
        }
        nbt.put("Items", nbttaglist);
    }

}
