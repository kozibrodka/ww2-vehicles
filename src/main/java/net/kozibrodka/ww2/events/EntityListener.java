package net.kozibrodka.ww2.events;

import net.kozibrodka.ww2.entity.*;
import net.kozibrodka.ww2.entityBullet.TankBulletMachineGun;
import net.kozibrodka.ww2.entityBullet.TankShellAP;
import net.kozibrodka.ww2.entityBullet.TankShellHE;
import net.kozibrodka.ww2.entityBullet.TankShellOBS;
import net.kozibrodka.ww2.test164.EntityVehicleTEST164;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class EntityListener {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public static void registerEntities(EntityRegisterEvent event) {
        event.register(Identifier.of(MOD_ID, "Tank"), EntityTank.class);
        event.register(Identifier.of(MOD_ID, "Truck"), EntityTruck.class);
        event.register(Identifier.of(MOD_ID, "Cannon"), EntityCannon.class);
        event.register(Identifier.of(MOD_ID, "PassSeatVehicle"), EntityPassengerSeat.class);

        event.register(Identifier.of(MOD_ID, "Tank164"), EntityVehicleTEST164.class);

        event.register(Identifier.of(MOD_ID, "TankBulletMachineGun"), TankBulletMachineGun.class);
        event.register(Identifier.of(MOD_ID, "TankShellAP"), TankShellAP.class);
        event.register(Identifier.of(MOD_ID, "TankShellHE"), TankShellHE.class);
        event.register(Identifier.of(MOD_ID, "TankShellOBS"), TankShellOBS.class);

        event.register(Identifier.of(MOD_ID, "Shell_OLD"), EntityShell_OLD.class);
        event.register(Identifier.of(MOD_ID, "Shell"), SdkEntityTankShell.class);
        event.register(Identifier.of(MOD_ID, "AAShellTank"), EntityAAShell.class);

    }

    @EventListener
    public static void registerMobHandlers(EntityHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("Tank"), EntityTank::new);
        Registry.register(event.registry, MOD_ID.id("Truck"), EntityTruck::new);
        Registry.register(event.registry, MOD_ID.id("Cannon"), EntityCannon::new);
        Registry.register(event.registry, MOD_ID.id("PassSeatVehicle"), EntityPassengerSeat::new);

        Registry.register(event.registry, MOD_ID.id("TankBulletMachineGun"), TankBulletMachineGun::new);
        Registry.register(event.registry, MOD_ID.id("TankShellAP"), TankShellAP::new);
        Registry.register(event.registry, MOD_ID.id("TankShellHE"), TankShellHE::new);
        Registry.register(event.registry, MOD_ID.id("TankShellOBS"), TankShellOBS::new);

        Registry.register(event.registry, MOD_ID.id("Shell_OLD"), EntityShell_OLD::new);
        Registry.register(event.registry, MOD_ID.id("Shell"), SdkEntityTankShell::new);
        Registry.register(event.registry, MOD_ID.id("AAShellTank"), EntityAAShell::new);
    }
}
