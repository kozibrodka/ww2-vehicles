package net.kozibrodka.ww2.entityBullet;

import net.kozibrodka.ww2.entity.EntityTank;
import net.kozibrodka.ww2.entity.SdkEntityTankShell;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.properties.TankType;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class TankShellAP extends SdkEntityTankShell implements EntitySpawnDataProvider {

    public TankShellAP(World world) {
        super(world);
    }

    public TankShellAP(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        /// CLIENT-DATA
        this.exploPower = 1.0F;
        this.penetration = 3.5F;
    }

    public TankShellAP(World world, EntityTank tankEntity, TankType tankType) {
        super(world, tankEntity, tankType);
        this.exploPower = 1.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(mod_Vehicles.MOD_ID, "TankShellAP");
    }

}
