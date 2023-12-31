package net.kozibrodka.vehicles.events;


import net.glasslauncher.hmifabric.event.HMITabRegistryEvent;
import net.glasslauncher.mods.api.gcapi.api.GConfig;
import net.kozibrodka.vehicles.entity.EntityAAShell;
import net.kozibrodka.vehicles.entity.EntityShell;
import net.kozibrodka.vehicles.entity.EntityVehicle;
import net.kozibrodka.vehicles.entity.SdkEntityBulletMachineGun;
import net.kozibrodka.vehicles.item.ItemVehicle;
import net.kozibrodka.vehicles.item.SdkItemGunMachineGun;
import net.kozibrodka.vehicles.properties.*;
import net.kozibrodka.vehicles.properties.VehicleType;
import net.kozibrodka.vehicles.recipe.BlockVehicleWorkbench;
import net.kozibrodka.vehicles.recipe.VehicleRecipeRegistry;
import net.kozibrodka.vehicles.recipe.VehicleRecipeTab;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import net.modificationstation.stationapi.api.util.Null;

import java.util.HashMap;
import java.util.Map;

public class mod_Vehicles {

    @GConfig(value = "VehiclesCFG", visibleName = "WW2 Vehicles Config")
    public static final VehiclesCFG vehiclesGlass = new VehiclesCFG();

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    public static TemplateBlockBase vehicleWorkbench;
    public static TemplateItemBase vehicleFuel;
    public static TemplateItemBase tankShell;
    public static TemplateItemBase tankShellHE;
    public static TemplateItemBase tankBullet;
    public static TemplateItemBase aaShellTank;
    public static TemplateItemBase vehicleBlowTorch;
    public static TemplateItemBase itemGunMachineGun;


    public static TemplateItemBase vehicleClassic_Sherman;
    public static TemplateItemBase vehicleClassic_Panzer;

    public static TemplateItemBase vehicleOld_M41;
    public static TemplateItemBase vehicleOld_Panzer4G;
    public static TemplateItemBase vehicleOld_Panzer4H;
    public static TemplateItemBase vehicleOld_T34;
    public static TemplateItemBase vehicleOld_Hummel;
    public static TemplateItemBase vehicleOld_Flakpanzer4;

    public static TemplateItemBase vehicle_Tiger1;


    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        vehicleFuel = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "vehicleFuel")).setTranslationKey(MOD_ID, "vehicleFuel");
        tankShell = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "tankShell")).setTranslationKey(MOD_ID, "tankShell");
        tankShellHE = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "tankShellHE")).setTranslationKey(MOD_ID, "tankShellHE");
        tankBullet = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "tankBullet")).setTranslationKey(MOD_ID, "tankBullet");
        aaShellTank = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "aaShell")).setTranslationKey(MOD_ID, "aaShell");
        vehicleBlowTorch = (TemplateItemBase) new TemplateItemBase(Identifier.of(MOD_ID, "planeBlowTorch")).setTranslationKey(MOD_ID, "planeBlowTorch").setMaxStackSize(1).setDurability(15);;

        itemGunMachineGun = (TemplateItemBase) new SdkItemGunMachineGun(Identifier.of(MOD_ID, "itemGunMachineGun")).setTranslationKey(MOD_ID, "itemGunMachineGun");

       //TODO: ADD MACHINE GUN TYPE for vehicles, add cars, engine types work, DMG overall, props
        if(vehiclesGlass.registerVehicles_CLASSIC) {
            new VehicleType(new PropertiesClassic_Sherman());
            new VehicleType(new PropertiesClassic_Panzer());
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
        }

        for (int i = 0; i < VehicleType.types.size(); i++) {
            VehicleType vehicletype = (VehicleType) VehicleType.types.get(i);
            System.out.println((new StringBuilder()).append("mod_Vehicles added vehicle : ").append(vehicletype.name).toString());

            vehicleMapping.put(vehicletype.name, vehicletype);
            vehicletype.przedmiot = (TemplateItemBase) new ItemVehicle(Identifier.of(MOD_ID, vehicletype.name), vehicletype.name).setTranslationKey(MOD_ID, vehicletype.name).setMaxStackSize(1);
        }
    }

    @EventListener
    public void registerBlocks(BlockRegistryEvent samolotAddEvent) {
        vehicleWorkbench = (TemplateBlockBase) new BlockVehicleWorkbench(Identifier.of(MOD_ID, "vehicleWorkbench")).setTranslationKey(MOD_ID, "vehicleWorkbench").setHardness(5F).setBlastResistance(10F).setSounds(BlockBase.METAL_SOUNDS);
    }

    @EventListener
    private static void registerEntities(EntityRegister event) {
        event.register(EntityShell.class, String.valueOf(Identifier.of(MOD_ID, "EntityShell")));
        event.register(EntityAAShell.class, String.valueOf(Identifier.of(MOD_ID, "EntityAAShellTank")));
        event.register(SdkEntityBulletMachineGun.class, String.valueOf(Identifier.of(MOD_ID, "SdkEntityBulletMachineGun")));
        event.register(EntityVehicle.class, String.valueOf(Identifier.of(MOD_ID, "EntityVehicle")));
    }

    @EventListener
    private static void registerMobHandlers(EntityHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("EntityShell"), EntityShell::new);
        Registry.register(event.registry, MOD_ID.id("EntityAAShellTank"), EntityAAShell::new);
        Registry.register(event.registry, MOD_ID.id("SdkEntityBulletMachineGun"), SdkEntityBulletMachineGun::new);
        Registry.register(event.registry, MOD_ID.id("EntityVehicle"), EntityVehicle::new);
    }

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event){
        VehicleRecipeRegistry.getInstance().initVehicleRecipe();
    }

    @EventListener
    public void registerTabs(HMITabRegistryEvent event) {
        event.registry.register(Identifier.of(MOD_ID, "vehicles"), new VehicleRecipeTab(MOD_ID), new ItemInstance(vehicleWorkbench));
    }

    public static VehicleType getVehicleType(String s) {
        return (VehicleType) vehicleMapping.get(s);
    }
    public static VehicleType type = null;
    private static Map vehicleMapping = new HashMap();
}
