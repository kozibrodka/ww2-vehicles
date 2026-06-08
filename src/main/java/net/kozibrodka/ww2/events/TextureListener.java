package net.kozibrodka.ww2.events;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.ww2.entity.*;
import net.kozibrodka.ww2.render.*;
import net.kozibrodka.ww2.test164.EntityVehicleTEST164;
import net.kozibrodka.ww2.test164.RenderVehicle164;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class TextureListener {

    @Entrypoint.Namespace
    public static  Namespace MOD_ID = Null.get();

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        mod_Vehicles.vehicleFuel.setTexture(Identifier.of(MOD_ID, "item/FuelCan"));
        mod_Vehicles.tankShell.setTexture(Identifier.of(MOD_ID, "item/TankShell"));
        mod_Vehicles.tankShellHE.setTexture(Identifier.of(MOD_ID, "item/TankShellHE"));
        mod_Vehicles.tankBullet.setTexture(Identifier.of(MOD_ID, "item/TankBullet"));
        mod_Vehicles.aaShellTank.setTexture(Identifier.of(MOD_ID, "item/AAShellTank"));
        mod_Vehicles.vehicleBlowTorch.setTexture(Identifier.of(MOD_ID, "item/blowtorch"));
        mod_Vehicles.itemGunMachineGun.setTexture(Identifier.of(MOD_ID, "item/PlaneGun"));

        veh_work_top = registerBlockTexture("block/VehicleWorkbenchTop");
        veh_work_bottom = registerBlockTexture("block/WorkSide");
        veh_work_side = registerBlockTexture("block/WorkBot");

        if(FabricLoader.getInstance().isModLoaded("planes")) {
            ww2Parts.biplaneWing.setTexture(Identifier.of(MOD_ID, "item/part/BiplaneWing"));
            ww2Parts.woodenTail.setTexture(Identifier.of(MOD_ID, "item/part/WoodenTail"));
            ww2Parts.woodenPropeller.setTexture(Identifier.of(MOD_ID, "item/part/WoodenPropeller"));
            ww2Parts.triplaneWing.setTexture(Identifier.of(MOD_ID, "item/part/TriplaneWing"));
            ww2Parts.woodenCockpit.setTexture(Identifier.of(MOD_ID, "item/part/WoodenCockpit"));
            ww2Parts.wheel.setTexture(Identifier.of(MOD_ID, "item/part/Wheel"));
            ww2Parts.metalWingMG.setTexture(Identifier.of(MOD_ID, "item/part/MetalWingMG"));
            ww2Parts.metalTail.setTexture(Identifier.of(MOD_ID, "item/part/MetalTail"));
            ww2Parts.bombBay.setTexture(Identifier.of(MOD_ID, "item/part/BombBay"));
            ww2Parts.metalNose.setTexture(Identifier.of(MOD_ID, "item/part/MetalNose"));
            ww2Parts.metalWing.setTexture(Identifier.of(MOD_ID, "item/part/MetalWing"));
            ww2Parts.metalWingSection.setTexture(Identifier.of(MOD_ID, "item/part/metalWingSection"));
            ww2Parts.metalCockpit.setTexture(Identifier.of(MOD_ID, "item/part/MetalCockpit"));
            ww2Parts.advancedMetalCockpit.setTexture(Identifier.of(MOD_ID, "item/part/AdvancedMetalCockpit"));
            ww2Parts.passengerBay.setTexture(Identifier.of(MOD_ID, "item/part/PassengerBay"));
            ww2Parts.metalPropeller.setTexture(Identifier.of(MOD_ID, "item/part/MetalPropeller"));
            ww2Parts.metalWingpPropv4.setTexture(Identifier.of(MOD_ID, "item/part/MetalWingProp"));
            ww2Parts.metalWingpPropv6.setTexture(Identifier.of(MOD_ID, "item/part/MetalWingProp"));
            ww2Parts.metalWingpPropv8.setTexture(Identifier.of(MOD_ID, "item/part/MetalWingProp"));
            ww2Parts.metalWingpPropRot.setTexture(Identifier.of(MOD_ID, "item/part/MetalWingProp"));
            ww2Parts.vehicleSeat.setTexture(Identifier.of(MOD_ID, "item/part/vehicleseat"));
        }

        if(FabricLoader.getInstance().isModLoaded("ww2") || FabricLoader.getInstance().isModLoaded("planes")) {
            ww2Parts.smallEngine.setTexture(Identifier.of(MOD_ID, "item/part/V4Engine"));
            ww2Parts.mediumEngine.setTexture(Identifier.of(MOD_ID, "item/part/V6Engine"));
            ww2Parts.largeEngine.setTexture(Identifier.of(MOD_ID, "item/part/V8Engine"));
            ww2Parts.rotaryEngine.setTexture(Identifier.of(MOD_ID, "item/part/RotaryEngine"));
            ww2Parts.piston.setTexture(Identifier.of(MOD_ID, "item/part/EnginePiston"));
            ww2Parts.machinegun.setTexture(Identifier.of(MOD_ID, "item/part/Machinegun"));
            ww2Parts.symbolGerman.setTexture(Identifier.of(MOD_ID, "item/part/SymbolGerman"));
            ww2Parts.symbolAmerican.setTexture(Identifier.of(MOD_ID, "item/part/SymbolAmerican"));
            ww2Parts.symbolBritish.setTexture(Identifier.of(MOD_ID, "item/part/SymbolBritish"));
            ww2Parts.symbolRussian.setTexture(Identifier.of(MOD_ID, "item/part/SymbolRussian"));
        }
        if(FabricLoader.getInstance().isModLoaded("ww2")) {
            ww2Parts.redstoneCoil.setTexture(Identifier.of(MOD_ID, "item/part/RedstoneCoil"));
            ww2Parts.denseredRedstoneCoil.setTexture(Identifier.of(MOD_ID, "item/part/DenseredRedstoneCoil"));
            ww2Parts.largeWheel.setTexture(Identifier.of(MOD_ID, "item/part/LargeWheel"));
            ww2Parts.smallCarChassis.setTexture(Identifier.of(MOD_ID, "item/part/SmallCarChassis"));
            ww2Parts.lightTankBody.setTexture(Identifier.of(MOD_ID, "item/part/LightTankBody"));
            ww2Parts.mediumTankBody.setTexture(Identifier.of(MOD_ID, "item/part/MediumTankBody"));
            ww2Parts.heavyTankBody.setTexture(Identifier.of(MOD_ID, "item/part/HeavyTankBody"));
            ww2Parts.advancedTankBody.setTexture(Identifier.of(MOD_ID, "item/part/AdvancedTankBody"));
            ww2Parts.tankBarrel.setTexture(Identifier.of(MOD_ID, "item/part/TankBarrel"));
            ww2Parts.gunShield.setTexture(Identifier.of(MOD_ID, "item/part/GunShield"));
            ww2Parts.lightTankTurret.setTexture(Identifier.of(MOD_ID, "item/part/LightTankTurret"));
            ww2Parts.mediumTankTurret.setTexture(Identifier.of(MOD_ID, "item/part/MediumTankTurret"));
            ww2Parts.heavyTankTurret.setTexture(Identifier.of(MOD_ID, "item/part/HeavyTankTurret"));
            ww2Parts.trackPiece.setTexture(Identifier.of(MOD_ID, "item/part/TrackPiece"));
            ww2Parts.towBar.setTexture(Identifier.of(MOD_ID, "item/part/Towbar"));
            ww2Parts.caterpillarTrack.setTexture(Identifier.of(MOD_ID, "item/part/CaterpillarTrack"));
        }
    }

    @EventListener
    public static void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(EntityShell.class, new RenderShell());
        event.renderers.put(EntityAAShell.class, new RenderAAShell());
        event.renderers.put(SdkEntityBulletMachineGun.class, new SdkRenderBulletMachineGun());
        event.renderers.put(EntityVehicle.class, new RenderVehicle());
        event.renderers.put(EntityTruck.class, new RenderTruck());
        event.renderers.put(EntityVehicleTEST164.class, new RenderVehicle164());
    }

    private int registerBlockTexture(String s) {
        if(s == null) {
            return 0;
        }
        return Atlases.getStationTerrain().addTexture(Identifier.of(MOD_ID, s)).index;
    }

    private int registerItemTexture(String s) {
        if(s == null) {
            return 0;
        }
        return Atlases.getStationGuiItems().addTexture(Identifier.of(MOD_ID, s)).index;
    }

    public static int veh_work_top;
    public static int veh_work_bottom;
    public static int veh_work_side;
}
