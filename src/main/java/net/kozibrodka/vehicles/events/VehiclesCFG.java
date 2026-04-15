package net.kozibrodka.vehicles.events;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class VehiclesCFG {

    @ConfigEntry(name = "Enable Bullets")
    public Boolean shellsEnabled = true;
    @ConfigEntry(name = "Vehicles Explode")
    public Boolean vehiclesExplode = true;

    @ConfigEntry(name = "Add Legacy Vehicles")
    public Boolean registerVehicles_CLASSIC = true;
    @ConfigEntry(name = "Add OLD Vehicles")
    public Boolean registerVehicles_NORMAL = false;
    @ConfigEntry(name = "Add New Vehicles")
    public Boolean registerVehicles_NEW = false;



}
