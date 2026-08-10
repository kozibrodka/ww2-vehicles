package net.kozibrodka.ww2.entityBullet;

import net.kozibrodka.ww2.entity.EntityCannon;
import net.kozibrodka.ww2.entity.EntityTank;
import net.kozibrodka.ww2.entity.SdkEntityTankShell;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.properties.CannonType;
import net.kozibrodka.ww2.properties.TankType;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class TankShellHE extends SdkEntityTankShell implements EntitySpawnDataProvider {

    public TankShellHE(World world) {
        super(world);
    }

    public TankShellHE(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        /// CLIENT-DATA
        this.exploPower = 4.0F;
        this.penetration = 0.0F;
    }

    public TankShellHE(World world, EntityTank tankEntity, TankType tankType) {
        super(world, tankEntity, tankType);
        this.penetration = 0.0F;
    }

    public TankShellHE(World world, EntityCannon cannonEntity, CannonType cannonType) {
        super(world, cannonEntity, cannonType);
        this.penetration = 0.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(mod_Vehicles.MOD_ID, "TankShellHE");
    }

//    @Override
//    public void playServerSound(World world) {
//    }
//
//    @Override
//    public String getServerExploSound(){
//        return "";
//    }

}
