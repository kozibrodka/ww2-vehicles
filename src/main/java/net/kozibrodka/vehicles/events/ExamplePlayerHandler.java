package net.kozibrodka.vehicles.events;

import net.modificationstation.stationapi.api.entity.player.PlayerHandler;

public class ExamplePlayerHandler implements PlayerHandler {

    @Override
    public boolean respawn() {
        System.out.println("Oh noes");
        return false;
    }

    @Override
    public boolean damageEntityBase(int i) { //todo tylko dla testu usunac potem
        System.out.println("DMG PLAYERAPI");
        return false;
    }

}
