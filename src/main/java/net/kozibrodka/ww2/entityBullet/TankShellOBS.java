package net.kozibrodka.ww2.entityBullet;

import net.kozibrodka.ww2.entity.EntityTank;
import net.kozibrodka.ww2.entity.SdkEntityTankShell;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.properties.TankType;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public class TankShellOBS extends SdkEntityTankShell implements EntitySpawnDataProvider {

    public TankShellOBS(World world) {
        super(world);
    }

    public TankShellOBS(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        /// CLIENT-DATA
        this.exploPower = 16.0F;
    }

    public TankShellOBS(World world, EntityTank tankEntity, TankType tankType) {
        super(world, tankEntity, tankType);
    }

    @Override
    public void playServerSound(World world) {
        world.playSound(this, "random.pop", 4.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    @Override
    public String getServerExploSound(){
        return "sdk_api:explowater";
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(mod_Vehicles.MOD_ID, "TankShellOBS");
    }
}
