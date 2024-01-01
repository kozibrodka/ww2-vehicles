package net.kozibrodka.vehicles.events;

import net.kozibrodka.vehicles.entity.*;
import net.kozibrodka.vehicles.render.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class TextureListener {

    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

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
    }

    @EventListener
    private static void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(EntityShell.class, new RenderShell());
        event.renderers.put(EntityAAShell.class, new RenderAAShell());
        event.renderers.put(SdkEntityBulletMachineGun.class, new SdkRenderBulletMachineGun());
        event.renderers.put(EntityVehicle.class, new RenderVehicle());
        event.renderers.put(EntityTruck.class, new RenderTruck());
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
