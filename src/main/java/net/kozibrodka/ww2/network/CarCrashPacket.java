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

public class CarCrashPacket extends Packet implements ManagedPacket<CarCrashPacket> {

    public static final PacketType<CarCrashPacket> TYPE = PacketType.builder(true, true, CarCrashPacket::new).build();


    private double VelX;
    private double VelY;
    private double VelZ;

    public CarCrashPacket() {
    }


    public CarCrashPacket(double x, double y, double z) {
        this.VelX = x;
        this.VelY = y;
        this.VelZ = z;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.VelX = stream.readDouble();
            this.VelY = stream.readDouble();
            this.VelZ = stream.readDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeDouble(this.VelX);
            stream.writeDouble(this.VelY);
            stream.writeDouble(this.VelZ);
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
            player.velocityX += this.VelX;
            player.velocityY += this.VelY;
            player.velocityZ += this.VelZ;
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<CarCrashPacket> getType() {
        return TYPE;
    }
}
