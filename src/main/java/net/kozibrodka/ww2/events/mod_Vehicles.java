package net.kozibrodka.ww2.events;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.kozibrodka.ww2.glasscfg.VehiclesCFG;
import net.kozibrodka.ww2.item.ItemCannon;
import net.kozibrodka.ww2.item.ItemTruck;
import net.kozibrodka.ww2.item.ItemVehicle;
import net.kozibrodka.ww2.item.SdkItemGunMachineGun;
import net.kozibrodka.ww2.properties.*;
import net.kozibrodka.ww2.properties_cannon.Properties_Bofors;
import net.kozibrodka.ww2.properties_cannon.Properties_Flakvierling;
import net.kozibrodka.ww2.properties_cannon.Properties_Pak40;
import net.kozibrodka.ww2.properties_car.PropertiesClassic_Jeep;
import net.kozibrodka.ww2.properties_car.PropertiesClassic_Kubelwagen;
import net.kozibrodka.ww2.properties_tank.PropertiesClassic_Panzer;
import net.kozibrodka.ww2.properties_tank.PropertiesClassic_Sherman;
import net.kozibrodka.ww2.properties_unused.*;
import net.kozibrodka.ww2.recipe.BlockVehicleWorkbench;
import net.kozibrodka.ww2.recipe.VehicleRecipeRegistry;
import net.kozibrodka.ww2.test164.ItemVehicle164;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import java.util.HashMap;
import java.util.Map;

public class mod_Vehicles {
    //todo rename
    @ConfigRoot(value = "VehiclesCFG", visibleName = "WW2 Config")
    public static final VehiclesCFG ww2Glass = new VehiclesCFG();

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
    public static Item aaShell;

    public static Item vehicleClassic_Sherman; //todo rename all
    public static Item vehicleClassic_Panzer;
    public static Item vehicleClassic_WillysJeep;
    public static Item vehicleClassic_Kubelwagen;
    public static Item bofors;
    public static Item flakvierling;
    public static Item pak40;

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
        aaShell = new TemplateItem(Identifier.of(MOD_ID, "aaShell")).setTranslationKey(MOD_ID, "aaShell");

        //TODO: ADD MACHINE GUN TYPE for vehicles, engine types work, DMG overall & props & collision, Truck playerXOffset, MINA!, ZAPORA ANTY-CZOLGOWA! (ala płotek), blowtorch effect, Gaśnica!
        if(true) {
            new TankType(new PropertiesClassic_Sherman());
            new TankType(new PropertiesClassic_Panzer());
            new TruckType(new PropertiesClassic_Jeep());
            new TruckType(new PropertiesClassic_Kubelwagen());
            new CannonType(new Properties_Pak40());
            new CannonType(new Properties_Bofors());
            new CannonType(new Properties_Flakvierling());
        }
        if(true) {
            new TankType(new PropertiesOld_M41());
            new TankType(new PropertiesOld_T34());
            new TankType(new PropertiesOld_Panzer4G());
            new TankType(new PropertiesOld_Panzer4H());
            new TankType(new PropertiesOld_Hummel());
            new TankType(new PropertiesOld_Flakpanzer4());
        }

        if(true) {
            new TankType(new Properties_Tiger1());
            new TankType(new Properties_Tiger2()); /// TIGER-2 PSUJE RECIPE - bo dodaje pustą
//            new TruckType(new Properties_WillyJeep());
//            new TruckType(new Properties_VWType82());
        }

        /// UPDATE! - dodajemy Itemy, ale ukrywamy w AMI + nie dodajemy recipes.

        for (int i = 0; i < TankType.types.size(); i++) {
            TankType tankType = (TankType) TankType.types.get(i);
            System.out.println("mod_WW2 added tank: " + tankType.name);

            tankMapping.put(tankType.name, tankType);
            tankType.przedmiot = new ItemVehicle(Identifier.of(MOD_ID, tankType.name), tankType.name).setTranslationKey(MOD_ID, tankType.name).setMaxCount(1);
        }

        for (int i = 0; i < TruckType.types.size(); i++) {
            TruckType truckType = (TruckType) TruckType.types.get(i);
            System.out.println("mod_WW2 added truck: " + truckType.name);

            truckMapping.put(truckType.name, truckType);
            truckType.przedmiot = new ItemTruck(Identifier.of(MOD_ID, truckType.name), truckType.name).setTranslationKey(MOD_ID, truckType.name).setMaxCount(1);
        }

        for (int i = 0; i < CannonType.types.size(); i++) {
            CannonType cannonType = (CannonType) CannonType.types.get(i);
            System.out.println("mod_Planes added cannon: " + cannonType.name); //TODO!! name nie może zawierać CYFR... bo nei załaduje itema.

            cannonMapping.put(cannonType.name, cannonType);
            cannonType.przedmiot = new ItemCannon(Identifier.of(MOD_ID, cannonType.name), cannonType.name).setTranslationKey(MOD_ID, cannonType.name).setMaxCount(1);
            System.out.println(cannonType.przedmiot);
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
    public void registerRecipes(RecipeRegisterEvent event){
        VehicleRecipeRegistry.getInstance().initVehicleRecipe();
    }

    public static TankType getTankType(String s) {
        return (TankType) tankMapping.get(s);
    }
    public static TruckType getTruckType(String s) {
        return (TruckType) truckMapping.get(s);
    }
    public static CannonType getCannonType(String s) {return (CannonType) cannonMapping.get(s);}

    private static final Map<String, TankType> tankMapping = new HashMap<>();
    private static final Map<String, TruckType> truckMapping = new HashMap<>();
    private static final Map<String, CannonType> cannonMapping = new HashMap<>();

}
