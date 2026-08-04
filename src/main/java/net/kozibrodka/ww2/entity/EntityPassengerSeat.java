package net.kozibrodka.ww2.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.kozibrodka.sdk_api.utils.SdkItemCustomUseDelay;
import net.kozibrodka.sdk_api.utils.SdkToolsRender;
import net.kozibrodka.sdk_api.utils.SdkVehicle;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.network.PassHeadRotPacket;
import net.kozibrodka.ww2.network.PassSeatLoadPacket;
import net.kozibrodka.ww2.network.PassengerEnterPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.Objects;

@HasTrackingParameters(trackingDistance = 240, updatePeriod = Integer.MAX_VALUE, sendVelocity = TriState.TRUE)
public class EntityPassengerSeat extends Entity implements SdkVehicle, EntitySpawnDataProvider
{

    //TODO prawdopodobnie klasa Interface PassSeats potrzebna?
    // TODO ENUM TYPE dla Typu MAtki - aby zostawić jendą klase PassSeat. (Enum.CAR/TANK/PLANE)

    public EntityPassengerSeat(World world)
    {
        super(world);
        blocksSameBlockSpawning = true;
//        setBoundingBoxSpacing(0.8F, 1.0F);
        setBoundingBoxSpacing(0.7F, 0.7F);
//        standingEyeHeight = height / 2.0F - 0.17F;
        standingEyeHeight = 0;
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
    }

    public EntityPassengerSeat(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1 + (double)standingEyeHeight, d2); ///??
        prevX = d;
        prevY = d1;
        prevZ = d2;
    }

    public EntityPassengerSeat(World world, int numnber, double d, double d1, double d2,
                               Entity entity1)
    {
        this(world);
        seatNumber = numnber;
        relativeX = d / 16D;
        relativeY = d1 / 16D;
        relativeZ = d2 / 16D;
        mother = entity1;
        setPosition(entity1.x, entity1.y, entity1.z);
    }

    @Override
    public void setPosition(double d, double d1, double d2)
    {
        x = d;
        y = d1;
        z = d2;
        float f = width / 2.0F;
        float f1 = height;
        boundingBox.set(d - (double)f, (d1 - (double)standingEyeHeight) + (double)cameraOffset, d2 - (double)f, d + (double)f, (d1 - (double)standingEyeHeight) + (double)cameraOffset + (double)f1, d2 + (double)f);
    }

    @Override
    public boolean damage(Entity entitySource, int i)
    {
        if(entitySource != null){
            mother.damage(entitySource, i);
        }
        return true;
    }


    @Override
    protected void initDataTracker()
    {
    }


    @Override
    public void addVelocity(double x, double y, double z) {
        if(mother != null){ /// Gdy następuje Zderzenie (o obrażeniami), addVelo przekierowane jest na Mother Vehicle
            mother.velocityX += x;
            mother.velocityY += y;
            mother.velocityZ += z;
        }
    }

    @Override
    public void onCollision(Entity otherEntity) { /// tutaj mogę wpisać pszesuwanie mnie oraz entity z którym mam kolizje
        /// ALPHA - czy tak to powinno działać?
//        if(mother != null && !(otherEntity instanceof LivingEntity)) {
//            System.out.println("przekazuje KOlizje");
//            mother.onCollision(otherEntity);
//        }
        if(mother != null){
            mother.onCollision(otherEntity);
        }
        /// Na razie wyłączam to, trzeba przemyśleć chyba...
    }

    @Override
    public Box getCollisionAgainstShape(Entity entity1)
    {
        return entity1.boundingBox;
    }

    @Override
    public Box getBoundingBox() /// Kolizja przy chodzeniu/poruszaniu sie
    {
        return boundingBox;
//        return null; ///brak kolizji z Pojazdem, ale cały czas bierze go pod uwagę - czyli może aktywować "hasCollided"
    }

    @Override
    public boolean isPushable() /// Czy będzie pchanie -> aktywacja on onCollision
    {
//        return false;
        return true; /// Zostawiam true - aby wybiórczo w onCollision() dać kolizje na pojazd matkę.
    }

    @Override
    public boolean isCollidable() /// Czy mogę kliknąć prawym myszy.
    {
        return !dead;
    }

    @Override
    public double getPassengerRidingHeight()
    {
        if(passenger instanceof PlayerEntity){
            return 0.025D;
        }else{
            return 0.3D;
        }
    }

    public void updateFromVehiclePosition(){

        /// GRANICA ///

        double d = relativeX;
        double d1 = relativeY;
        double d2 = relativeZ;
        double d3 = Math.cos(((double)(-mother.yaw) / 180D) * 3.1415926535897931D);
        double d4 = Math.sin(((double)(-mother.yaw) / 180D) * 3.1415926535897931D);

//        double d5 = Math.cos(((double)mother.pitch / 180D) * 3.1415926535897931D); /// GÓRA - DÓŁ
//        double d6 = Math.sin(((double)mother.pitch / 180D) * 3.1415926535897931D) * 0.5D; /// Przesunięcie PRZÓD-TYŁ
        /// Issue: Little "freeze" on drop

            double d5 = Math.cos(((double)pitch / 180D) * 3.1415926535897931D);
            double d6 = Math.sin(((double)pitch / 180D) * 3.1415926535897931D);
        /// Oryginal

//            double d5 = Math.cos(((double)0 / 180D) * 3.1415926535897931D);
//            double d6 = Math.sin(((double)0 / 180D) * 3.1415926535897931D);
        /// Simple no drop freeze

        double d7 = Math.cos(((double)mother.yaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D * d5;
        double d8 = Math.sin(((double)mother.yaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D * d5;
        double d9 = (d * d5 - d1 * d6) * d3 + d2 * d4;
        double d10 = d * d6 + d1 * d5;
        double d11 = (d1 * d6 - d * d5) * d4 + d2 * d3;
        setRotation(mother.yaw, mother.pitch);
        setPosition(mother.x + d9 + d7, mother.y + d10, mother.z + d11 + d8);
    }

    @Override
    public void markDead()
    {
        scheduleVelocityUpdate();
        if(passenger != null)
        {
            passenger.setVehicle(this);
        }
        super.markDead();
    }

    @Override
    public void animateHurt()
    {
    }

    @Override
    public void tick()
    {
        super.tick();
        if(world.isRemote){ //&& passenger != null
            if(!receivedP){
                receivedP = true;
                PacketHelper.send(new PassSeatLoadPacket(this.id));
            }
            if(passenger instanceof PlayerEntity playerEnt && Objects.equals(SdkToolsRender.minecraft.player.name, playerEnt.name)){/// Głowa Packet
                PacketHelper.send(new PassHeadRotPacket(passenger.yaw, passenger.pitch));
            }
        }else{
            if(mother == null || mother.dead){
                markDead();
            }
            if(passenger != null && (passenger.dead || !passenger.isAlive()))
            {
                if(passenger instanceof PlayerEntity player && mother instanceof EntityTruck truck){
                    truck.exitWithPupils(player);
                }
                passenger = null;
                broadcastEventExit();
            }
        }
        if(mother == null){
            return;
        }
//        if(!world.isRemote){
//            updateFromVehiclePosition();
//        }
        updateFromVehiclePosition();
    }

    @Override
    protected void writeNbt(NbtCompound nbttagcompound)
    {
    }

    @Override
    protected void readNbt(NbtCompound nbttagcompound)
    {
        if(mother == null){
            markDead();
        }
    }

    @Override
    public float getShadowRadius()
    {
        return 0.0F;
    }

    @Override
    public boolean interact(PlayerEntity entityplayer)
    {
        if(passenger != null && (passenger instanceof PlayerEntity) && passenger != entityplayer)
        {
            return true; ///zostawiam to jedynie dla ewentualnje logiki dontUseThatTick sdk
        }
        if(!world.isRemote && passenger == null)
        {
            SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
            entityplayer.setVehicle(this);
            if(mother instanceof EntityTruck truck){
                truck.occupySeatsWithPupils(entityplayer);
            }
            if(SdkEnvTool.isEnvServ()) {
                PacketHelper.sendToAllTracking(this, new PassengerEnterPacket(this.id, entityplayer.name));
            }
        }
        return true;
    }


    public Entity mother;
    public int seatNumber;
    public double relativeX;
    public double relativeY;
    public double relativeZ;
    public double playerYOffset;
    public boolean receivedP = false;

    @Override
    public void setControls(boolean b, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6) {

    }

    @Override
    public void reloadKey() {

    }

    @Override
    public void exitKey(PlayerEntity playerEntity) {
        alternateExit(playerEntity);
        if(mother instanceof EntityTruck truck){
            truck.exitWithPupils(playerEntity);
        }
        broadcastEventExit();
    }

    public void alternateExit(PlayerEntity playerEntity){
        playerEntity.vehiclePitchDelta = 0.0F;
        playerEntity.vehicleYawDelta = 0.0F;
        if (playerEntity.vehicle != null) {
            double extraY = 0.15D;
            playerEntity.setPositionAndAnglesKeepPrevAngles(playerEntity.x, playerEntity.vehicle.boundingBox.minY + (double)playerEntity.vehicle.height + extraY, playerEntity.z, playerEntity.yaw, playerEntity.pitch);
            playerEntity.vehicle.passenger = null;
        }
        playerEntity.vehicle = null;
    }

    @Override
    public void inventoryKey(PlayerEntity playerEntity) {

    }

    @Override
    public void bombKey() {

    }

    @Override
    public void rocketKey() {

    }

    @Override
    public int getPercentHealth() {
        return ((SdkVehicle)mother).getPercentHealth();
    }

    @Override
    public float getArmorFactor() {
        return ((SdkVehicle)mother).getArmorFactor();
    }

    @Override
    public float getDmgReduce() {
        return 0;
    }

    @Override
    public float getDmgBroken() {
        return 0;
    }

    @Override
    public String getAmmoName() {
        return "";
    }

    @Override
    public String getBombName() {
        return "";
    }

    @Override
    public boolean canPassengerUseGun() {
        return true;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(mod_Vehicles.MOD_ID, "PassSeatVehicle");
    }

    public void broadcastEventExit(){
        world.broadcastEntityEvent(this, (byte)6);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void processServerEntityStatus(byte status) {
        if (status == 6) {
            passenger = null;
        } else{
            super.processServerEntityStatus(status);
        }
    }

    //    @Override
//    public void updatePassengerPosition()  //updateRiderPosition
//    {
//        if(passenger == null)
//        {
//            return;
//        }
//        if(passenger == SdkTools.minecraft.player || (passenger instanceof WolfEntity))
//        {
//            double d = relativeX;
//            double d1 = getMountedYOffset() + passenger.getStandingEyeHeight() + relativeY;
//            double d2 = relativeZ;
//            double d3 = Math.cos(((double)(-yaw) / 180D) * 3.1415926535897931D);
//            double d4 = Math.sin(((double)(-yaw) / 180D) * 3.1415926535897931D);
//            double d5 = Math.cos(((double)pitch / 180D) * 3.1415926535897931D);
//            double d6 = Math.sin(((double)pitch / 180D) * 3.1415926535897931D);
//            double d7 = Math.cos(((double)yaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D * d5;
//            double d8 = Math.sin(((double)yaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D * d5;
//            double d9 = (d * d5 - d1 * d6) * d3 + d2 * d4;
//            double d10 = d * d6 + d1 * d5;
//            double d11 = (d1 * d6 - d * d5) * d4 + d2 * d3;
//            passenger.setPosition(x + d9 + d7, y + d10, z + d11 + d8);
//        } else
//        {
//        }
//    }
}
