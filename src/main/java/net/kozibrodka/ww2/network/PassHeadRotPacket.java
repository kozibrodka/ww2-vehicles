package net.kozibrodka.ww2.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PassHeadRotPacket extends Packet implements ManagedPacket<PassHeadRotPacket> {

    public static final PacketType<PassHeadRotPacket> TYPE = PacketType.builder(true, true, PassHeadRotPacket::new).build();

    private float Yaw;
    private float Pitch;

    public PassHeadRotPacket() {
    }

    public PassHeadRotPacket(float yaw, float pitch) {
        this.Yaw = yaw;
        this.Pitch = pitch;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.Yaw = stream.readFloat();
            this.Pitch = stream.readFloat();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeFloat(this.Yaw);
            stream.writeFloat(this.Pitch);
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

    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ServerPlayerEntity player = (ServerPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }
            player.yaw = this.Yaw;
            player.pitch = this.Pitch;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<PassHeadRotPacket> getType() {
        return TYPE;
    }
}
