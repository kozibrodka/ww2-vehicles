package net.kozibrodka.ww2.events;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.kozibrodka.ww2.glasscfg.VehiclesCFG;
import net.kozibrodka.ww2.entity.*;
import net.kozibrodka.ww2.item.ItemTruck;
import net.kozibrodka.ww2.item.ItemVehicle;
import net.kozibrodka.ww2.item.SdkItemGunMachineGun;
import net.kozibrodka.ww2.properties.*;
import net.kozibrodka.ww2.properties_car.PropertiesClassic_Jeep;
import net.kozibrodka.ww2.properties_car.PropertiesClassic_Kubelwagen;
import net.kozibrodka.ww2.properties_tank.PropertiesClassic_Panzer;
import net.kozibrodka.ww2.properties_tank.PropertiesClassic_Sherman;
import net.kozibrodka.ww2.recipe.BlockVehicleWorkbench;
import net.kozibrodka.ww2.recipe.VehicleRecipeRegistry;
import net.kozibrodka.ww2.test164.ItemVehicle164;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.entity.EntityRegisterEvent;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import java.util.HashMap;
import java.util.Map;

public class mod_Vehicles {
    //todo rename
    @ConfigRoot(value = "VehiclesCFG", visibleName = "WW2 Vehicles Config")
    public static final VehiclesCFG vehiclesGlass = new VehiclesCFG();

    @Entrypoint.Namespace
    public static  Namespace MOD_ID = Null.get();

    public static TemplateBlock vehicleWorkbench;
    public static Item vehicleFuel;
    public static Item tankShell;
    public static Item tankShellHE;
    public static Item tankBullet;
    public static Item aaShellTank;
    public static Item vehicleBlowTorch;
    public static Item itemGunMachineGun;


    public static Item vehicleClassic_Sherman;
    public static Item vehicleClassic_Panzer;
    public static Item vehicleClassic_WillysJeep;
    public static Item vehicleClassic_Kubelwagen;

    public static Item vehicleOld_M41;
    public static Item vehicleOld_Panzer4G;
    public static Item vehicleOld_Panzer4H;
    public static Item vehicleOld_T34;
    public static Item vehicleOld_Hummel;
    public static Item vehicleOld_Flakpanzer4;

    public static Item vehicle_VWType82;
    public static Item vehicle_WillyJeep;
    public static Item vehicle_Tiger1;
    public static Item vehicle_Tiger2;

    public static Item vehicle_test164;
    public static Item wrenchGoldDebug;

    @EventListener
    public void registerItems(ItemRegistryEvent event) { //TODO move all items to ww2Parts Listener? - chyba tak

        vehicleFuel =  new TemplateItem(Identifier.of(MOD_ID, "vehicleFuel")).setTranslationKey(MOD_ID, "vehicleFuel");
        tankShell = new TemplateItem(Identifier.of(MOD_ID, "tankShell")).setTranslationKey(MOD_ID, "tankShell").setMaxCount(16);
        tankShellHE = new TemplateItem(Identifier.of(MOD_ID, "tankShellHE")).setTranslationKey(MOD_ID, "tankShellHE").setMaxCount(16);
        tankBullet = new TemplateItem(Identifier.of(MOD_ID, "tankBullet")).setTranslationKey(MOD_ID, "tankBullet");
        aaShellTank = new TemplateItem(Identifier.of(MOD_ID, "aaShellTank")).setTranslationKey(MOD_ID, "aaShellTank");
        vehicleBlowTorch = new TemplateItem(Identifier.of(MOD_ID, "vehicleBlowTorch")).setTranslationKey(MOD_ID, "vehicleBlowTorch").setMaxCount(1).setMaxDamage(64);;

        itemGunMachineGun = new SdkItemGunMachineGun(Identifier.of(MOD_ID, "itemGunMachineGun")).setTranslationKey(MOD_ID, "itemGunMachineGun");

       //TODO: ADD MACHINE GUN TYPE for vehicles, engine types work, DMG overall & props & collision, Truck playerXOffset, MINA!, ZAPORA ANTY-CZOLGOWA! (ala płotek), blowtorch effect, Gaśnica!
        if(vehiclesGlass.registerVehicles_CLASSIC) {
            new TankType(new PropertiesClassic_Sherman());
            new TankType(new PropertiesClassic_Panzer());
            new TruckType(new PropertiesClassic_Jeep());
            new TruckType(new PropertiesClassic_Kubelwagen());
        }
        if(vehiclesGlass.registerVehicles_NORMAL) {
            new TankType(new PropertiesOld_M41());
            new TankType(new PropertiesOld_T34());
            new TankType(new PropertiesOld_Panzer4G());
            new TankType(new PropertiesOld_Panzer4H());
            new TankType(new PropertiesOld_Hummel());
            new TankType(new PropertiesOld_Flakpanzer4());
        }

        if(vehiclesGlass.registerVehicles_NEW) {
            new TankType(new Properties_Tiger1());
            new TankType(new Properties_Tiger2());
            new TruckType(new Properties_WillyJeep());
            new TruckType(new Properties_VWType82());
        }

        //TODO "pozwolenie" na dodanie czołgów dodaje VehicleType ale nie dodaje itema aby przy wyłączniu nie było resetu chunków przez NBT null error.
        /// UPDATE! - dodajemy Itemy, ale ukrywamy w AMI + nie dodajemy recipes.

        for (int i = 0; i < TankType.types.size(); i++) {
            TankType vehicletype = (TankType) TankType.types.get(i);
            System.out.println("mod_WW2 added tank: " + vehicletype.name);

            vehicleMapping.put(vehicletype.name, vehicletype);
            vehicletype.przedmiot = new ItemVehicle(Identifier.of(MOD_ID, vehicletype.name), vehicletype.name).setTranslationKey(MOD_ID, vehicletype.name).setMaxCount(1);
        }

        for (int i = 0; i < TruckType.types.size(); i++) {
            TruckType truckType = (TruckType) TruckType.types.get(i);
            System.out.println("mod_WW2 added truck: " + truckType.name);

            truckMapping.put(truckType.name, truckType);
            truckType.przedmiot = new ItemTruck(Identifier.of(MOD_ID, truckType.name), truckType.name).setTranslationKey(MOD_ID, truckType.name).setMaxCount(1);
        }

        vehicle_test164 = new ItemVehicle164(Identifier.of(MOD_ID, "vehicle_test164")).setTranslationKey(MOD_ID, "vehicle_test164");
//        if(vehiclesGlass.testMP) {
            wrenchGoldDebug = new TemplateItem(Identifier.of(MOD_ID, "wrenchGoldDebug")).setTranslationKey(MOD_ID, "wrenchGoldDebug");
//        }

    }

    @EventListener
    public void registerBlocks(BlockRegistryEvent samolotAddEvent) {
        vehicleWorkbench = (TemplateBlock) new BlockVehicleWorkbench(Identifier.of(MOD_ID, "vehicleWorkbench")).setTranslationKey(MOD_ID, "vehicleWorkbench").setHardness(5F).setResistance(10F).setSoundGroup(Block.METAL_SOUND_GROUP);
    }


    @EventListener
    public static void registerEntities(EntityRegisterEvent event) {
        event.register(Identifier.of(MOD_ID, "Shell_OLD"), EntityShell_OLD.class);
        event.register(Identifier.of(MOD_ID, "Shell"), SdkEntityTankShell.class);
        event.register(Identifier.of(MOD_ID, "AAShellTank"), EntityAAShell.class);
        event.register(Identifier.of(MOD_ID, "SdkBulletMachineGun"), SdkEntityBulletMachineGun.class);
        event.register(Identifier.of(MOD_ID, "Tank"), EntityTank.class);
        event.register(Identifier.of(MOD_ID, "Truck"), EntityTruck.class);
        event.register(Identifier.of(MOD_ID, "PassSeatVehicle"), EntityPassengerSeat.class);
    }

    @EventListener
    public static void registerMobHandlers(EntityHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("Shell_OLD"), EntityShell_OLD::new);
        Registry.register(event.registry, MOD_ID.id("Shell"), SdkEntityTankShell::new);
        Registry.register(event.registry, MOD_ID.id("AAShellTank"), EntityAAShell::new);
        Registry.register(event.registry, MOD_ID.id("SdkBulletMachineGun"), SdkEntityBulletMachineGun::new);
        Registry.register(event.registry, MOD_ID.id("Tank"), EntityTank::new);
        Registry.register(event.registry, MOD_ID.id("Truck"), EntityTruck::new);
        Registry.register(event.registry, MOD_ID.id("PassSeatVehicle"), EntityPassengerSeat::new);
    }

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event){
        VehicleRecipeRegistry.getInstance().initVehicleRecipe();
    }

    public static TankType getTankType(String s) {
        return (TankType) vehicleMapping.get(s);
    }
    public static TruckType getTruckType(String s) {
        return (TruckType) truckMapping.get(s);
    }

    ///todo Co to kurwa jest???
//    public static VehicleType type = null;
//    public static TruckType type_truck = null;

    private static Map<String, TankType> vehicleMapping = new HashMap<>();
    private static Map<String, TruckType> truckMapping = new HashMap<>();

}
