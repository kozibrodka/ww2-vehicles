package net.kozibrodka.ww2.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.ww2.entity.EntityTruck;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.ClientWorld;
import net.minecraft.world.ServerWorld;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TruckLoadPacket extends Packet implements ManagedPacket<TruckLoadPacket> {

    public static final PacketType<TruckLoadPacket> TYPE = PacketType.builder(true, true, TruckLoadPacket::new).build();

    private int entityId;
    private String entityPass = "";
    private String typeName = "";
    private float entityYaw;
    private float entityPitch;
    private boolean entityGround;
    private int entityHealth;

    public TruckLoadPacket() {
    }

    public TruckLoadPacket(int id) {
        this.entityId = id;
    }

    public TruckLoadPacket(int id, String name) {
        this.entityId = id;
        this.typeName = name;
    }

    public TruckLoadPacket(int id, String name, float ya, float pi, boolean gr, int ht, String pass) {
        this.entityId = id;
        this.typeName = name;
        this.entityYaw = ya;
        this.entityPitch = pi;
        this.entityGround = gr;
        this.entityHealth = ht;
        this.entityPass = pass;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.entityId = stream.readInt();
            this.typeName = stream.readUTF();
            this.entityYaw = stream.readFloat();
            this.entityPitch = stream.readFloat();
            this.entityGround = stream.readBoolean();
            this.entityHealth = stream.readInt();
            this.entityPass = stream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.entityId);
            stream.writeUTF(this.typeName);
            stream.writeFloat(this.entityYaw);
            stream.writeFloat(this.entityPitch);
            stream.writeBoolean(this.entityGround);
            stream.writeInt(this.entityHealth);
            stream.writeUTF(this.entityPass);
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
        Entity vehicleEntity = ((ClientWorld)player.world).getEntity(this.entityId);
        if(vehicleEntity instanceof EntityTruck truck){

            truck.automobile = mod_Vehicles.getTruckType(this.typeName);
            truck.setOnGround(this.entityGround);
            truck.getDataTracker().set(29, this.entityHealth);
            truck.setClientYaw(this.entityYaw);
            truck.setBoundingBoxSpacing(truck.automobile.autoWidth, truck.automobile.autoHeight);
            truck.setPosition(truck.x, truck.y, truck.z);

//            truck.setPosition(truck.x, d1 + (double)standingEyeHeight, d2);

            int inventorySize = truck.automobile.numCargoSlots + truck.automobile.numBulletSlots + truck.automobile.numShellSlots + 1;
            truck.cargoItems = new ItemStack[inventorySize];

            PlayerEntity jokey1 = player.world.getPlayer(this.entityPass);
                if(jokey1 != null){
                    jokey1.setVehicle(truck);
                }
        }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ServerPlayerEntity player = (ServerPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }

        Entity vehicleEntity = ((ServerWorld)player.world).getEntity(this.entityId);

        if(vehicleEntity instanceof EntityTruck truck){
            String sPass = "";
            if(vehicleEntity.passenger instanceof PlayerEntity plPass){
                sPass = plPass.name;
            }

            PacketHelper.sendTo(player, new TruckLoadPacket(truck.id, truck.automobile.name, truck.yaw, truck.pitch, truck.onGround, truck.health, sPass));
//            PacketHelper.sendTo(player, new TruckLoadPacket(vehicleEntity.id, truck.automobile.name));
//            PacketHelper.sendTo(player, new CarLoadPacket(vehicleEntity.id, vehicleEntity.yaw, vehicleEntity.pitch, vehicleEntity.onGround, landVeh.health, landVeh.gunA.itemId, landVeh.gunB.itemId, sPass));

        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<TruckLoadPacket> getType() {
        return TYPE;
    }
}
