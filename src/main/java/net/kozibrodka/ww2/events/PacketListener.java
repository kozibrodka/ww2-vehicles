package net.kozibrodka.ww2.events;

import net.kozibrodka.ww2.network.PassSeatLoadPacket;
import net.kozibrodka.ww2.network.TruckLoadPacket;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class PacketListener {

    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public void registerPacket(PacketRegisterEvent event) {
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("truckLoad"), TruckLoadPacket.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("passSeatLoad"), PassSeatLoadPacket.TYPE);

    }
}
