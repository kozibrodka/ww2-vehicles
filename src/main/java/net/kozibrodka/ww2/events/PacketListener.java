package net.kozibrodka.ww2.events;

import net.kozibrodka.ww2.network.*;
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
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("vehicleLoad"), TruckLoadPacket.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("passSeatLoad"), PassSeatLoadPacket.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("passEnter"), PassengerEnterPacket.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("passLivingEnter"), PassengerLivingEnterPacket.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("passHeadRot"), PassHeadRotPacket.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("carCrash"), CarCrashPacket.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, MOD_ID.id("antiExitStuck"), ExitAntiStuckPacket.TYPE);

    }
}
