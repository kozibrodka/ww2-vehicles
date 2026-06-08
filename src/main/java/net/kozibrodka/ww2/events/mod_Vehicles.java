package net.kozibrodka.ww2.events;


import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
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
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
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

    @ConfigRoot(value = "VehiclesCFG", visibleName = "WW2 Vehicles Config")
    public static final VehiclesCFG vehiclesGlass = new VehiclesCFG();

    @Entrypoint.Namespace
    public static  Namespace MOD_ID = Null.get();

    public static TemplateBlock vehicleWorkbench;
    public static TemplateItem vehicleFuel;
    public static TemplateItem tankShell;
    public static TemplateItem tankShellHE;
    public static TemplateItem tankBullet;
    public static TemplateItem aaShellTank;
    public static TemplateItem vehicleBlowTorch;
    public static TemplateItem itemGunMachineGun;


    public static TemplateItem vehicleClassic_Sherman;
    public static TemplateItem vehicleClassic_Panzer;
    public static TemplateItem vehicleClassic_WillysJeep;
    public static TemplateItem vehicleClassic_Kubelwagen;

    public static TemplateItem vehicleOld_M41;
    public static TemplateItem vehicleOld_Panzer4G;
    public static TemplateItem vehicleOld_Panzer4H;
    public static TemplateItem vehicleOld_T34;
    public static TemplateItem vehicleOld_Hummel;
    public static TemplateItem vehicleOld_Flakpanzer4;

    public static TemplateItem vehicle_VWType82;
    public static TemplateItem vehicle_WillyJeep;
    public static TemplateItem vehicle_Tiger1;
    public static TemplateItem vehicle_Tiger2;



    public static Item vehicle_test164;


    @EventListener
    public void registerItems(ItemRegistryEvent event) { //TODO move all items to ww2Parts Listener? - chyba tak
//        ww2Parts.registerWW2Parts();
//        registerWW2Parts();
        vehicleFuel = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "vehicleFuel")).setTranslationKey(MOD_ID, "vehicleFuel");
        tankShell = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "tankShell")).setTranslationKey(MOD_ID, "tankShell");
        tankShellHE = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "tankShellHE")).setTranslationKey(MOD_ID, "tankShellHE");
        tankBullet = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "tankBullet")).setTranslationKey(MOD_ID, "tankBullet");
        aaShellTank = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "aaShellTank")).setTranslationKey(MOD_ID, "aaShellTank");
        vehicleBlowTorch = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "vehicleBlowTorch")).setTranslationKey(MOD_ID, "vehicleBlowTorch").setMaxCount(1).setMaxDamage(64);;

        itemGunMachineGun = (TemplateItem) new SdkItemGunMachineGun(Identifier.of(MOD_ID, "itemGunMachineGun")).setTranslationKey(MOD_ID, "itemGunMachineGun");

       //TODO: ADD MACHINE GUN TYPE for vehicles, engine types work, DMG overall & props & collision, Truck playerXOffset, MINA!, ZAPORA ANTY-CZOLGOWA! (ala płotek), blowtorch effect, Gaśnica!
        if(vehiclesGlass.registerVehicles_CLASSIC) {
            new VehicleType(new PropertiesClassic_Sherman());
            new VehicleType(new PropertiesClassic_Panzer());
            new TruckType(new PropertiesClassic_Jeep());
            new TruckType(new PropertiesClassic_Kubelwagen());
        }
        if(vehiclesGlass.registerVehicles_NORMAL) {
            new VehicleType(new PropertiesOld_M41());
            new VehicleType(new PropertiesOld_T34());
            new VehicleType(new PropertiesOld_Panzer4G());
            new VehicleType(new PropertiesOld_Panzer4H());
            new VehicleType(new PropertiesOld_Hummel());
            new VehicleType(new PropertiesOld_Flakpanzer4());
        }

        if(vehiclesGlass.registerVehicles_NEW) {
            new VehicleType(new Properties_Tiger1());
            new VehicleType(new Properties_Tiger2());
            new TruckType(new Properties_WillyJeep());
            new TruckType(new Properties_VWType82());
        }

        for (int i = 0; i < VehicleType.types.size(); i++) {
            VehicleType vehicletype = (VehicleType) VehicleType.types.get(i);
            System.out.println((new StringBuilder()).append("mod_Vehicles added vehicle : ").append(vehicletype.name).toString());

            vehicleMapping.put(vehicletype.name, vehicletype);
            vehicletype.przedmiot = (TemplateItem) new ItemVehicle(Identifier.of(MOD_ID, vehicletype.name), vehicletype.name).setTranslationKey(MOD_ID, vehicletype.name).setMaxCount(1);
        }

        for (int i = 0; i < TruckType.types.size(); i++) {
            TruckType truckType = (TruckType) TruckType.types.get(i);
            System.out.println((new StringBuilder()).append("mod_Vehicles added truck : ").append(truckType.name).toString());

            truckMapping.put(truckType.name, truckType);
            truckType.przedmiot = (TemplateItem) new ItemTruck(Identifier.of(MOD_ID, truckType.name), truckType.name).setTranslationKey(MOD_ID, truckType.name).setMaxCount(1);
        }


        vehicle_test164 = new ItemVehicle164(Identifier.of(MOD_ID, "vehicle_test164")).setTranslationKey(MOD_ID, "vehicle_test164");

    }

    @EventListener
    public void registerBlocks(BlockRegistryEvent samolotAddEvent) {
        vehicleWorkbench = (TemplateBlock) new BlockVehicleWorkbench(Identifier.of(MOD_ID, "vehicleWorkbench")).setTranslationKey(MOD_ID, "vehicleWorkbench").setHardness(5F).setResistance(10F).setSoundGroup(Block.METAL_SOUND_GROUP);
    }


    @EventListener
    public static void registerEntities(EntityRegister event) {
        event.register(EntityShell.class, String.valueOf(Identifier.of(MOD_ID, "EntityShell")));
        event.register(EntityAAShell.class, String.valueOf(Identifier.of(MOD_ID, "EntityAAShellTank")));
        event.register(SdkEntityBulletMachineGun.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletMachineGun")));
        event.register(EntityVehicle.class, String.valueOf(Identifier.of(MOD_ID, "EntityVehicle")));
        event.register(EntityTruck.class, String.valueOf(Identifier.of(MOD_ID, "EntityTruck")));
    }

    @EventListener
    public static void registerMobHandlers(EntityHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("EntityShell"), EntityShell::new);
        Registry.register(event.registry, MOD_ID.id("EntityAAShellTank"), EntityAAShell::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletMachineGun"), SdkEntityBulletMachineGun::new);
        Registry.register(event.registry, MOD_ID.id("EntityVehicle"), EntityVehicle::new);
        Registry.register(event.registry, MOD_ID.id("EntityTruck"), EntityTruck::new);
    }

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event){
        VehicleRecipeRegistry.getInstance().initVehicleRecipe();
    }

//    @EventListener
//    public void registerTabs(HMITabRegistryEvent event) {
//        event.registry.register(Identifier.of(MOD_ID, "vehicles"), new VehicleRecipeTab(MOD_ID), new ItemStack(vehicleWorkbench));
//    }


    public static VehicleType getVehicleType(String s) {
        return (VehicleType) vehicleMapping.get(s);
    }
    public static TruckType getTruckType(String s) {
        return (TruckType) truckMapping.get(s);
    }
    public static VehicleType type = null;
    public static TruckType type_truck = null;
    private static Map vehicleMapping = new HashMap();
    private static Map truckMapping = new HashMap();


    public static void registerWW2Parts() {
        if(FabricLoader.getInstance().isModLoaded("planes")) {
            biplaneWing = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "biplaneWing")).setTranslationKey(MOD_ID, "biplaneWing").setMaxCount(8);
            woodenTail = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "woodenTail")).setTranslationKey(MOD_ID, "woodenTail").setMaxCount(4);
            woodenPropeller = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "woodenPropeller")).setTranslationKey(MOD_ID, "woodenPropeller").setMaxCount(4);
            triplaneWing = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "triplaneWing")).setTranslationKey(MOD_ID, "triplaneWing").setMaxCount(8);
            woodenCockpit = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "woodenCockpit")).setTranslationKey(MOD_ID, "woodenCockpit").setMaxCount(4);
            wheel = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "wheel")).setTranslationKey(MOD_ID, "wheel").setMaxCount(12);
            metalWingMG = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalWingMG")).setTranslationKey(MOD_ID, "metalWingMG").setMaxCount(8);
            metalTail = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalTail")).setTranslationKey(MOD_ID, "metalTail").setMaxCount(4);
            bombBay = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "bombBay")).setTranslationKey(MOD_ID, "bombBay").setMaxCount(4);
            metalNose = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalNose")).setTranslationKey(MOD_ID, "metalNose").setMaxCount(4);
            metalWing = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalWing")).setTranslationKey(MOD_ID, "metalWing").setMaxCount(8);
            metalWingSection = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalWingSection")).setTranslationKey(MOD_ID, "metalWingSection").setMaxCount(8);
            metalCockpit = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalCockpit")).setTranslationKey(MOD_ID, "metalCockpit").setMaxCount(4);
            advancedMetalCockpit = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "advancedMetalCockpit")).setTranslationKey(MOD_ID, "advancedMetalCockpit").setMaxCount(2);
            passengerBay = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "passengerBay")).setTranslationKey(MOD_ID, "passengerBay").setMaxCount(4);
            metalPropeller = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalPropeller")).setTranslationKey(MOD_ID, "metalPropeller").setMaxCount(4);
            metalWingpPropv4 = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalWingpPropv4")).setTranslationKey(MOD_ID, "metalWingpPropv4").setMaxCount(8);
            metalWingpPropv6 = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalWingpPropv6")).setTranslationKey(MOD_ID, "metalWingpPropv6").setMaxCount(8);
            metalWingpPropv8 = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalWingpPropv8")).setTranslationKey(MOD_ID, "metalWingpPropv8").setMaxCount(8);
            metalWingpPropRot = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "metalWingpPropRot")).setTranslationKey(MOD_ID, "metalWingpPropRot").setMaxCount(8);
            vehicleSeat = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "vehicleSeat")).setTranslationKey(MOD_ID, "vehicleSeat").setMaxCount(8);
        }
        if(true || FabricLoader.getInstance().isModLoaded("planes")) {
            smallEngine = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "smallEngine")).setTranslationKey(MOD_ID, "smallEngine").setMaxCount(4);
            mediumEngine = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "mediumEngine")).setTranslationKey(MOD_ID, "mediumEngine").setMaxCount(4);
            largeEngine = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "largeEngine")).setTranslationKey(MOD_ID, "largeEngine").setMaxCount(4);
            rotaryEngine = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "rotaryEngine")).setTranslationKey(MOD_ID, "rotaryEngine").setMaxCount(4);
            piston = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "piston")).setTranslationKey(MOD_ID, "piston").setMaxCount(16);
            machinegun = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "machinegun")).setTranslationKey(MOD_ID, "machinegun").setMaxCount(8);
            symbolGerman = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "symbolGerman")).setTranslationKey(MOD_ID, "symbolGerman");
            symbolAmerican = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "symbolAmerican")).setTranslationKey(MOD_ID, "symbolAmerican");
            symbolBritish = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "symbolBritish")).setTranslationKey(MOD_ID, "symbolBritish");
            symbolRussian = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "symbolRussian")).setTranslationKey(MOD_ID, "symbolRussian");
        }
        if(true) {
            redstoneCoil = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "redstoneCoil")).setTranslationKey(MOD_ID, "redstoneCoil").setMaxCount(16);
            denseredRedstoneCoil = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "denseredRedstoneCoil")).setTranslationKey(MOD_ID, "denseredRedstoneCoil").setMaxCount(16);
            largeWheel = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "largeWheel")).setTranslationKey(MOD_ID, "largeWheel").setMaxCount(4);
            smallCarChassis = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "smallCarChassis")).setTranslationKey(MOD_ID, "smallCarChassis").setMaxCount(4);
            lightTankBody = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "lightTankBody")).setTranslationKey(MOD_ID, "lightTankBody").setMaxCount(4);
            mediumTankBody = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "mediumTankBody")).setTranslationKey(MOD_ID, "mediumTankBody").setMaxCount(4);
            heavyTankBody = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "heavyTankBody")).setTranslationKey(MOD_ID, "heavyTankBody").setMaxCount(4);
            advancedTankBody = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "advancedTankBody")).setTranslationKey(MOD_ID, "advancedTankBody").setMaxCount(4);
            tankBarrel = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "tankBarrel")).setTranslationKey(MOD_ID, "tankBarrel").setMaxCount(4);
            gunShield = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "gunShield")).setTranslationKey(MOD_ID, "gunShield").setMaxCount(4);
            lightTankTurret = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "lightTankTurret")).setTranslationKey(MOD_ID, "lightTankTurret").setMaxCount(4);
            mediumTankTurret = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "mediumTankTurret")).setTranslationKey(MOD_ID, "mediumTankTurret").setMaxCount(4);
            heavyTankTurret = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "heavyTankTurret")).setTranslationKey(MOD_ID, "heavyTankTurret").setMaxCount(4);
            trackPiece = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "trackPiece")).setTranslationKey(MOD_ID, "trackPiece").setMaxCount(32);
            towBar = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "towBar")).setTranslationKey(MOD_ID, "towBar").setMaxCount(4);
            caterpillarTrack = (TemplateItem) new TemplateItem(Identifier.of(MOD_ID, "caterpillarTrack")).setTranslationKey(MOD_ID, "caterpillarTrack").setMaxCount(8);
        }
    }

    public static TemplateItem biplaneWing;
    public static TemplateItem woodenTail;
    public static TemplateItem woodenPropeller;
    public static TemplateItem triplaneWing;
    public static TemplateItem woodenCockpit;
    public static TemplateItem wheel;
    public static TemplateItem metalWingMG;
    public static TemplateItem metalTail;
    public static TemplateItem bombBay;
    public static TemplateItem machinegun;
    public static TemplateItem metalWing;
    public static TemplateItem metalWingSection;
    public static TemplateItem metalNose;
    public static TemplateItem metalCockpit;
    public static TemplateItem advancedMetalCockpit;
    public static TemplateItem smallEngine;
    public static TemplateItem mediumEngine;
    public static TemplateItem largeEngine;
    public static TemplateItem rotaryEngine;
    public static TemplateItem piston;
    public static TemplateItem passengerBay;
    public static TemplateItem metalPropeller;
    public static TemplateItem metalWingpPropv4;
    public static TemplateItem metalWingpPropv6;
    public static TemplateItem metalWingpPropv8;
    public static TemplateItem metalWingpPropRot;
    public static TemplateItem vehicleSeat;
    public static TemplateItem redstoneCoil;
    public static TemplateItem denseredRedstoneCoil;
    public static TemplateItem largeWheel;
    public static TemplateItem smallCarChassis;
    public static TemplateItem lightTankBody;
    public static TemplateItem mediumTankBody;
    public static TemplateItem heavyTankBody;
    public static TemplateItem advancedTankBody;
    public static TemplateItem tankBarrel;
    public static TemplateItem gunShield;
    public static TemplateItem lightTankTurret;
    public static TemplateItem mediumTankTurret;
    public static TemplateItem heavyTankTurret;
    public static TemplateItem trackPiece;
    public static TemplateItem towBar;
    public static TemplateItem caterpillarTrack;
    public static TemplateItem symbolGerman;
    public static TemplateItem symbolAmerican;
    public static TemplateItem symbolBritish;
    public static TemplateItem symbolRussian;
}
