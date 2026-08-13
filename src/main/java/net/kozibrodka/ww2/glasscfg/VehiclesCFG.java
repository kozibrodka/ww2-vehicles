package net.kozibrodka.ww2.glasscfg;

import net.glasslauncher.mods.gcapi3.api.ConfigCategory;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class VehiclesCFG {

    @ConfigEntry(name = "Render-Debug Passanger Seat", requiresRestart = true)
    public Boolean debugPassSeats = false; ///DEV

    @ConfigEntry(name = "Enable Tanks", multiplayerSynced = true, requiresRestart = true)
    public Boolean register_TANK = true;

    @ConfigEntry(name = "Enable Trucks", multiplayerSynced = true, requiresRestart = true)
    public Boolean register_TRUCK = true;

    @ConfigEntry(name = "Enable Artillery", multiplayerSynced = true, requiresRestart = true)
    public Boolean register_CANNON = true;

    @ConfigEntry(name = "Enable Planes", multiplayerSynced = true, requiresRestart = true)
    public Boolean register_PLANE = true;

    @ConfigEntry(name = "Enable Guns", multiplayerSynced = true, requiresRestart = true)
    public Boolean register_GUNS = true;

    @ConfigCategory(name="§aGameplay", multiplayerSynced = true)
    public GameplayCFG gameplay = new GameplayCFG();

    @ConfigCategory(name="§aContent", multiplayerSynced = true)
    public ContentCFG content = new ContentCFG();


//    @ConfigEntry(name = "Enable Bullets", multiplayerSynced = true, requiresRestart = true)
//    public Boolean testMP = true;
//
//    @ConfigEntry(name = "Enable Bullets")
//    public Boolean shellsEnabled = true;
//    @ConfigEntry(name = "Vehicles Explode")
//    public Boolean vehiclesExplode = true;
//
//    @ConfigEntry(name = "Add Legacy Vehicles")
//    public Boolean registerVehicles_CLASSIC = true;
//    @ConfigEntry(name = "Add OLD Vehicles")
//    public Boolean registerVehicles_NORMAL = false;
//    @ConfigEntry(name = "Add New Vehicles")
//    public Boolean registerVehicles_NEW = false;



}
