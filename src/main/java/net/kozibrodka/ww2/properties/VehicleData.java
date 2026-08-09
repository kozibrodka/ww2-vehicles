package net.kozibrodka.ww2.properties;

public class VehicleData {

    public VehicleData(String longname, int cargo, int bullet, int shell, int fueladd){
        longName = longname;
        numCargoSlots = cargo;
        numBulletSlots = bullet;
        numShellSlots = shell;
        vehicleFuelAdd = fueladd;
    }

    public VehicleData(String longname, int cargo, int bullet, int shell, boolean cannon){
        longName = longname;
        numCargoSlots = cargo;
        numBulletSlots = bullet;
        numShellSlots = shell;
        isCannon = cannon;
        vehicleFuelAdd = 100;
    }


    public String longName;
    public int numCargoSlots;
    public int numBulletSlots;
    public int numShellSlots;
    public int vehicleFuelAdd;
    public boolean isCannon;
}
