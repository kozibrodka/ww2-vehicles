package net.kozibrodka.ww2.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ExitAntiStuckPacket extends Packet implements ManagedPacket<ExitAntiStuckPacket> {

    public static final PacketType<ExitAntiStuckPacket> TYPE = PacketType.builder(true, true, ExitAntiStuckPacket::new).build();

    private int action;

    public ExitAntiStuckPacket() {
    }

    public ExitAntiStuckPacket(int code) {
        this.action = code;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.action = stream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.action);
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
        if(action == 1) {
//            double e = 0.1D;
            double e = 0.2D;
            player.setPosition(player.x, player.y + e, player.z);
//            player.setPosition(player.x, player.y + 10D, player.z);
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
    public @NotNull PacketType<ExitAntiStuckPacket> getType() {
        return TYPE;
    }
}
