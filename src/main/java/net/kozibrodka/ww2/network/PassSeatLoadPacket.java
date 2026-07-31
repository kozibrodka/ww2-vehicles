package net.kozibrodka.ww2.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.ww2.entity.EntityPassengerSeat;
import net.kozibrodka.ww2.entity.EntityTruck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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

public class PassSeatLoadPacket extends Packet implements ManagedPacket<PassSeatLoadPacket> {

    public static final PacketType<PassSeatLoadPacket> TYPE = PacketType.builder(true, true, PassSeatLoadPacket::new).build();

    private int entityId;
    private String playerPass = "";
    private int motherId;
    private int passId;
    private double offSetX;
    private double offSetY;
    private double offSetZ;

    public PassSeatLoadPacket() {
    }

    public PassSeatLoadPacket (int id) {
        this.entityId = id;
    }

    public PassSeatLoadPacket(int id, int momId, String pass, int entId, double x, double y, double z) {
        this.entityId = id;
        this.motherId = momId;
        this.playerPass = pass;
        this.passId = entId;
        this.offSetX = x;
        this.offSetY = y;
        this.offSetZ = z;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.entityId = stream.readInt();
            this.motherId = stream.readInt();
            this.playerPass = stream.readUTF();
            this.passId = stream.readInt();
            this.offSetX = stream.readDouble();
            this.offSetY = stream.readDouble();
            this.offSetZ = stream.readDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.entityId);
            stream.writeInt(this.motherId);
            stream.writeUTF(this.playerPass);
            stream.writeInt(this.passId);
            stream.writeDouble(this.offSetX);
            stream.writeDouble(this.offSetY);
            stream.writeDouble(this.offSetZ);
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
        Entity passSeatEntity = ((ClientWorld)player.world).getEntity(this.entityId);
        if(passSeatEntity instanceof EntityPassengerSeat passSeat){

            passSeat.mother = ((ClientWorld)player.world).getEntity(this.motherId);
            passSeat.relativeX = this.offSetX;
            passSeat.relativeY = this.offSetY;
            passSeat.relativeZ = this.offSetZ; //todo: do zmiany system, bo jeszcze będą jakieś do Broni pass Seat milion opcji...

            PlayerEntity jokey1 = player.world.getPlayer(this.playerPass);
                if(jokey1 != null){
                    jokey1.setVehicle(passSeat);
                }
            Entity passangerEnt = ((ClientWorld)player.world).getEntity(this.passId);
            if(passangerEnt != null){
                passangerEnt.setVehicle(passSeat);
            }
        }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ServerPlayerEntity player = (ServerPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }

        Entity passSeatEntity = ((ServerWorld)player.world).getEntity(this.entityId);

        if(passSeatEntity instanceof EntityPassengerSeat passSeat){
            String sPass = "";
            int ePass = 0;
            if(passSeat.passenger instanceof PlayerEntity plPass){
                sPass = plPass.name;
            } else if(passSeat.passenger instanceof LivingEntity livPass){
                ePass = livPass.id;
            }

//            ((EntityTruck)passSeat.mother).automobile.passengerSeats[0].offSetX; //TODO - dodać jedną dane kolejność passSeats i jedynie wysyłać nr. - bo jak bronie dojdą czy coś to za dużo będzie.

            PacketHelper.sendTo(player, new PassSeatLoadPacket(passSeat.id, passSeat.mother.id, sPass, ePass, passSeat.relativeX, passSeat.relativeY, passSeat.relativeZ));
//            PacketHelper.sendTo(player, new TruckLoadPacket(vehicleEntity.id, truck.automobile.name));
//            PacketHelper.sendTo(player, new CarLoadPacket(vehicleEntity.id, vehicleEntity.yaw, vehicleEntity.pitch, vehicleEntity.onGround, landVeh.health, landVeh.gunA.itemId, landVeh.gunB.itemId, sPass));

        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<PassSeatLoadPacket> getType() {
        return TYPE;
    }
}
