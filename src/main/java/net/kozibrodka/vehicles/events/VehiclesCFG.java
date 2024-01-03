package net.kozibrodka.vehicles.events;

import net.glasslauncher.mods.api.gcapi.api.ConfigName;

public class VehiclesCFG {

    @ConfigName("Enable Bullets")
    public Boolean shellsEnabled = true;
    @ConfigName("Vehicles Explode")
    public Boolean vehiclesExplode = true;

    @ConfigName("Add Legacy Vehicles")
    public Boolean registerVehicles_CLASSIC = true;
    @ConfigName("Add OLD Vehicles")
    public Boolean registerVehicles_NORMAL = false;
    @ConfigName("Add New Vehicles")
    public Boolean registerVehicles_NEW = false;



}
