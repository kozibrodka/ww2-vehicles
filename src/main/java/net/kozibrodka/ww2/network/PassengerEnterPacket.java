package net.kozibrodka.ww2.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.ClientWorld;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class PassengerEnterPacket extends Packet implements ManagedPacket<PassengerEnterPacket> {

    public static final PacketType<PassengerEnterPacket> TYPE = PacketType.builder(true, true, PassengerEnterPacket::new).build();

    private int entityId;
    private String entityJokey;

    public PassengerEnterPacket() {
    }

    public PassengerEnterPacket(int id, String roper) {
        this.entityId = id;
        this.entityJokey = roper;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.entityId = stream.readInt();
            this.entityJokey = stream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.entityId);
            stream.writeUTF(this.entityJokey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void apply(NetworkHandler arg) {
        switch (FabricLoader.INSTANCE.getEnvironmentType()) {
            case CLIENT -> handleClient(arg);
            case SERVER -> handleServer(arg);
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleClient(NetworkHandler networkHandler) {
        ClientPlayerEntity player = (ClientPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }
                Entity panzer1 = ((ClientWorld)player.world).getEntity(this.entityId);
                PlayerEntity jokey1 = player.world.getPlayer(this.entityJokey);

                if(panzer1 != null && jokey1 != null && !Objects.equals(jokey1.name, player.name)){
                    jokey1.setVehicle(panzer1);
                }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public @NotNull PacketType<PassengerEnterPacket> getType() {
        return TYPE;
    }
}
