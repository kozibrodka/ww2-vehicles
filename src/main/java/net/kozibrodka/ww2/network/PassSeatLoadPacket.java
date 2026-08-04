package net.kozibrodka.ww2.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.FabricLoader;
import net.kozibrodka.ww2.entity.EntityPassengerSeat;
import net.kozibrodka.ww2.entity.EntityTruck;
import net.kozibrodka.ww2.properties.PassengerSeatData;
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
    private int seatNr;

    public PassSeatLoadPacket() {
    }

    public PassSeatLoadPacket (int id) {
        this.entityId = id;
    }

    public PassSeatLoadPacket(int id, int momId, String pass, int entId, int numbero) {
        this.entityId = id;
        this.motherId = momId;
        this.playerPass = pass;
        this.passId = entId;
        this.seatNr = numbero;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.entityId = stream.readInt();
            this.motherId = stream.readInt();
            this.playerPass = stream.readUTF();
            this.passId = stream.readInt();
            this.seatNr = stream.readInt();
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
            stream.writeInt(this.seatNr);
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
        Entity entity = ((ClientWorld)player.world).getEntity(this.entityId);
        if(entity instanceof EntityPassengerSeat passSeat){

            passSeat.mother = ((ClientWorld)player.world).getEntity(this.motherId);
            if(passSeat.mother == null){return;}

            PassengerSeatData passData = ((EntityTruck)passSeat.mother).automobile.passengerSeats[this.seatNr];
            setPropsFromType(passSeat, passData);

            PlayerEntity jokeyPl = player.world.getPlayer(this.playerPass);
            if(jokeyPl != null){
                jokeyPl.setVehicle(passSeat);
            }
            Entity jokeyLiv = ((ClientWorld)player.world).getEntity(this.passId);
            if(jokeyLiv != null){
                jokeyLiv.setVehicle(passSeat);
            }
        }
    }


    public void setPropsFromType(EntityPassengerSeat seat, PassengerSeatData data){
        seat.relativeX = data.offSetX / 16D;
        seat.relativeY = data.offSetY / 16D;
        seat.relativeZ = data.offSetZ / 16D;
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        ServerPlayerEntity player = (ServerPlayerEntity) PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null){
            return;
        }

        Entity entity = ((ServerWorld)player.world).getEntity(this.entityId);

        if(entity instanceof EntityPassengerSeat passSeat){
            String sPass = "";
            int ePass = -1; /// jeżeli wyśle 0, czasami mob z ID zero wejdzie lokalnie na siedzenie
            if(passSeat.passenger instanceof PlayerEntity plPass){
                sPass = plPass.name;
            } else if(passSeat.passenger instanceof LivingEntity livPass){
                ePass = livPass.id;
            }

            PacketHelper.sendTo(player, new PassSeatLoadPacket(passSeat.id, passSeat.mother.id, sPass, ePass, passSeat.seatNumber));

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
