package net.kozibrodka.ww2.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk_api.particle.SdkParticleFactory;
import net.kozibrodka.sdk_api.utils.*;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.network.*;
import net.kozibrodka.ww2.properties.PassengerSeatData;
import net.kozibrodka.ww2.properties.TruckType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@HasTrackingParameters(trackingDistance = 160, updatePeriod = 2, sendVelocity = TriState.TRUE)
public class EntityTruck extends EntityVehicle implements WW2Truck, EntitySpawnDataProvider {

    public EntityTruck(World world)
    {
        super(world);
        lastTurnSpeed = 0.0D;
        lastOnGround = true;
        prevMotionX = 0.0D;
        prevMotionY = 0.0D;
        prevMotionZ = 0.0D;
        lastCollidedEntity = null;
        soundLoopTime = 0;
        stepHeight = 1.0F; //stepHeight
        renderDistanceMultiplier = 2; //jakos to dostosoawac
    }

    public EntityTruck(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = d;
        prevY = d1;
        prevZ = d2;
    }

    public EntityTruck(World world, double d, double d1, double d2,
                       PlayerEntity entityplayer, int i, TruckType vehicletype)
    {
        this(world);
        automobile = vehicletype;
        setDataFromTruck(automobile);
        standingEyeHeight = automobile.standingOko;
        setBoundingBoxSpacing(automobile.autoWidth, automobile.autoHeight);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = d;
        prevY = d1;
        prevZ = d2;
        cargoItems = new ItemStack[inventorySize];
        health = automobile.MAX_HEALTH;
        engineType = i;
        if(engineType < 1)
        {
            engineType = 1;
        }
        if(engineType > 4)
        {
            engineType = 4;
        }
    }

    public void addPassengerSeats()
    {
        for(int i = 0; i < automobile.numPassengers; i++)
        {
            if(world != null)
            {
                PassengerSeatData passData = automobile.passengerSeats[i];
                seats[i] = new EntityPassengerSeat(world, passData.number, passData.offSetX, passData.offSetY, passData.offSetZ, this);
                world.spawnEntity(seats[i]);
            }
        }
    }

    public boolean shouldCollide(Entity otherEntity){
        if(otherEntity.passenger == this || otherEntity.vehicle == this || otherEntity instanceof EntityPassengerSeat || otherEntity.vehicle instanceof EntityPassengerSeat){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onCollision(Entity otherEntity) {
        if (shouldCollide(otherEntity)) {
            double var2 = otherEntity.x - this.x;
            double var4 = otherEntity.z - this.z;
            double var6 = MathHelper.absMax(var2, var4);
            if (var6 >= (double)0.01F) {
                var6 = MathHelper.sqrt(var6);
                var2 /= var6;
                var4 /= var6;
                double var8 = (double)1.0F / var6;
                if (var8 > (double)1.0F) {
                    var8 = 1.0F;
                }
                var2 *= var8;
                var4 *= var8;
                var2 *= 0.05F;
                var4 *= 0.05F;
                var2 *= 1.0F - this.pushSpeedReduction;
                var4 *= 1.0F - this.pushSpeedReduction;
                if(!(otherEntity instanceof LivingEntity)){ /// Lista co ma nie przesuwać auta. Gracz nie przesunie Pojazdu
                    this.addVelocity(-var2, 0.0F, -var4);
                }
                if(!(otherEntity instanceof WW2Tank)){ /// Lista czego auto ma nie przesuwać
                    otherEntity.addVelocity(var2, 0.0F, var4);
                }
            }
        }
    }

    @Override
    public Box getCollisionAgainstShape(Entity other) {
        /// System aby Auto nie podjeżdzało na niskie zwierzęta - tylko je przejeżdzało.
        if(other instanceof LivingEntity piggy && piggy.height <= 1.0F){
            Box ramBox = Box.create(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            ramBox.clone(piggy.boundingBox);
            double roznica = (1.0D - (double) piggy.height) + 0.1D;
            ramBox.maxY += roznica;
            return ramBox;
        }
        return other.boundingBox;
    }

    @Override
    public Box getBoundingBox()
    {
        return boundingBox;
    }

    @Override
    public boolean isPushable()
    {
        return true;
    }

    @Override
    public boolean damage(Entity entity, int i)
    {
        if(health > 0)
        {
            if(entity instanceof LivingEntity){ /// RACZEJ DO ZMIANY, przemyślenia
                if(entity instanceof Monster){
                    health -= i /5;
                    System.out.println("CAR DAMAGED from: " + entity + " DMG: " + i /5);
                    playHurtSound();
                }
            }else{
                health -= i;
                System.out.println("CAR DAMAGED from: " + entity + " DMG: " + i);
                playHurtSound();
            }
            if(health <= 0)
            {
                destroyVehicle();
                broadcastEventExplode();
            }
        }
        return true;
    }

    @Override
    protected void onLanding(float fallDistance) {
        int fallDMG = (int)Math.ceil(fallDistance - 3.0F) * 4;
        if (fallDMG > 0) {
            damage(null, fallDMG);
        }
        if (passenger instanceof LivingEntity living1) {
            living1.onLanding(fallDistance);
        }
        for (EntityPassengerSeat seat : seats) {
            if (seat.passenger instanceof LivingEntity passLiving) {
                passLiving.onLanding(fallDistance);
            }
        }
    }

    public void playHurtSound()
    {
        world.playSound(this, "ww2:mechhurt", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        world.broadcastEntityEvent(this, (byte)7);
    }

    @Override
    public boolean isCollidable()
    {
        return !dead;
    }

    @Override
    public float getShadowRadius()
    {
        return 0.0F; //TODO, czemu zero daje cień?
//        return this.width / 1.0F;
    }

    @Override
    public boolean interact(PlayerEntity entityplayer)
    {
        if(world.isRemote){ //todo interaction + healing / TANK również
            if(!(passenger != null && passenger != entityplayer))
            {
                SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
            }
            return true;
        }
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == mod_Vehicles.vehicleBlowTorch.id) //TODO przenieś do ItemBlowTorch / TANK również
        {
            if(health > 0 && health < automobile.MAX_HEALTH)
            {
                world.playSound(this, "ofensywa:wrench", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                health = Math.min(health + 10, automobile.MAX_HEALTH);
                entityplayer.swingHand();
                entityplayer.getHand().damage(1, entityplayer);
                if(entityplayer.getHand().getDamage() <= 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedSlot] = null;
                }
            }
            return true;
        }
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == mod_Vehicles.wrenchGoldDebug.id) /// DEBUG
        {
            System.out.println("TYPE: " + automobile.name);
            System.out.println("ENGINE: " + engineType);
            System.out.println("HEALTH: " + health);
//            damage(null, 200); ///debug
            entityplayer.swingHand();
            world.broadcastEntityEvent(this, (byte)110);
            return true;
        }
        if(passenger != null && passenger != entityplayer)
        {
            return true;
        }
        if(!world.isRemote && passenger == null)
        {
            SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
            entityplayer.setVehicle(this);
            occupySeatsWithPupils(entityplayer);
            if(SdkEnvTool.isEnvServ()) {
                PacketHelper.sendToAllTracking(this, new PassengerEnterPacket(this.id, entityplayer.name));
            }
        }
        return true;
    }

    public void occupySeatsWithPupils(PlayerEntity player){
        List list = world.collectEntitiesByClass(WolfEntity.class, Box.createCached(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(16D, 4D, 16D));
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();
            WolfEntity entitywolf = (WolfEntity) entity;
            if (entitywolf.isTamed() && !entitywolf.isInSittingPose() && player.name.equals(entitywolf.getOwnerName())) {
                int j = 0;
                while (j < automobile.numPassengers) {
                    if (seats[j].passenger == null) {
                        entitywolf.setVehicle(seats[j]);
                        entitywolf.setSitting(true);
                        if(SdkEnvTool.isEnvServ()) {
                            PacketHelper.sendToAllTracking(this, new PassengerLivingEnterPacket(this.id, entitywolf.id));
                        }
                    }
                    j++;
                }
            }
        }
    }

    public void exitWithPupils(PlayerEntity player){

        int j = 0;
        while(j < automobile.numPassengers)
        {
            if(seats[j].passenger instanceof WolfEntity wolf && Objects.equals(wolf.getOwnerName(), player.name))
            {
                /// Klasa Utils z integracją z mocreatures?
                wolf.setSitting(false);
                wolf.setVehicle(null);
                seats[j].broadcastEventExit();
            }
            j++;
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        if(world.isRemote){
            if(!receivedP){
                receivedP = true;
                PacketHelper.send(new TruckLoadPacket(this.id));
            }
            remoteTick();
            return;
        }
        prevX = x;
        prevY = y;
        prevZ = z;
        if(getSpeed() > 0.0D)
        {
            double d = getMotionYaw();
            double d1 = (double)yaw - d;
            projectMotion(d1);
        }
        boolean flag = false;
        boolean flag1 = true;
        if(getSpeed() != 0.0D)
        {
            double d2 = ((double)yaw * 3.1415926535897931D) / 180D;
            double d6 = Math.cos(d2);
            flag1 = -d6 > 0.0D && velocityX > 0.0D || -d6 < 0.0D && velocityX < 0.0D;
        }
        if(onGround)
        {
            if(passenger != null)
            {
                if(getSpeed() != 0.0D)
                {
                    double d4 = 0.0D;
                    if(vehicleFuel > 0 && clientLEFT) //todo vehicle fuel nie musi być ZERO dla akcji poza Gazem.
                    {
                        d4 = -getTurnSpeed() * (double)(flag1 ? 1 : -1);
                        wheelsYaw = (float)((double)wheelsYaw - 0.5D * getTurnSpeed());
                    } else
                    if(vehicleFuel > 0 && clientRIGHT)
                    {
                        d4 = getTurnSpeed() * (double)(flag1 ? 1 : -1);
                        wheelsYaw = (float)((double)wheelsYaw + 0.5D * getTurnSpeed());
                    }
                    if(d4 != 0.0D)
                    {
                        /// More Realistic 4 wheels physics
                        double rotSpeedM = getSpeed() * 2D;
                        if(rotSpeedM > 1D){
                            rotSpeedM = 1.0D;
                        }
                        double d4car = d4*rotSpeedM;
                        yaw += (float) d4car;
                        projectMotion(d4car);
                        /// Old
//                        yaw += d4;
//                        projectMotion(d4);
                        ///
                    }
                    lastTurnSpeed = d4 * (double)(flag1 ? 1 : -1);
                }
                double d5 = 0.0D;
                if(vehicleFuel > 0 && clientFORWARD)
                {
                    d5 = -(flag1 ? getAccelForward() : automobile.ACCEL_BRAKE);
                    vehicleFuel--;
                    flag = true;
                }else
                if(vehicleFuel > 0 && clientBACK)
                {
                    d5 = flag1 ? automobile.ACCEL_BRAKE : getAccelBackward();
                    flag = true;
                }
                if(d5 != 0.0D)
                {
                    double d7 = ((double)yaw * 3.1415926535897931D) / 180D;
                    double d8 = Math.cos(d7);
                    double d9 = Math.sin(d7);
                    velocityX += d5 * d8;
                    velocityZ += d5 * d9;
                }
            }
            if(!flag)
            {
                multiplySpeed(automobile.SPEED_MULT_DECEL);
            }
            if(passenger == null)
            {
                multiplySpeed(automobile.SPEED_MULT_UNMOUNTED);
            }
            double d3 = getSpeed();
            if(d3 > (automobile.MAX_SPEED + engineType*0.03D))
            {
                multiplySpeed((automobile.MAX_SPEED + engineType*0.03D) / d3);
            }
        }
        if(checkWaterCollisions()) //handle water mv
        {
            multiplySpeed(automobile.SPEED_MULT_WATER);
        }
        if(!flag && getSpeed() < automobile.STOP_SPEED)
        {
            multiplySpeed(0.0D);
        }
        if(uphillTicks > 0){ /// Spowolnienie pod Górke
            uphillTicks--;
            multiplySpeed(automobile.UPHILL_SLOWDOWN);
        }
        move(velocityX, velocityY, velocityZ);
        int forwOrBack = flag1 ? 1 : -1;
        if(onGround && lastOnGround)
        {
            if(prevY - y > 0.01D)
            {
                pitch = 45 * forwOrBack;
            } else
            if(prevY - y < -0.01D)
            {
                pitch = -45 * forwOrBack;
                uphillTicks = 4; /// Daje 4 ticksów spowolnienia pod górke
            } else
            {
                pitch = 0.0F;
                /// System z czołgu, nie jestem pewien czy pasuje
//                if(uphillTicks > 0){
//                    if(pitch > 0.0F){
//                        pitch -= 10.0F;
//                    }
//                    if(pitch < 0.0F){
//                        pitch += 10.0F;
//                    }
//                }else{
//                    pitch = 0.0F;
//                }
            }
            velocityY -= 0.001D;
        } else
        {
            setRotationPitch(Math.max(Math.min((float)((-90D * velocityY) / getSpeed()) * (float)forwOrBack, 90F), -90F) / 2.0F);
            velocityY = y - prevY - automobile.FALL_SPEED;
        }
        //TODO DEBUG
        if(clientDOWN){
            this.pitch = 45F;
        }if (clientUP){
        this.pitch = -45F;
        }
        //TODO DEBUG
        lastOnGround = onGround;
        /// Handling Collision
        List list = world.getEntities(this, boundingBox.expand(0.2D, 0.0D, 0.2D));
        if(list != null && !list.isEmpty())
        {
            for(int j = 0; j < list.size(); j++)
            {
                Entity entity = (Entity)list.get(j);
                if(entity != passenger && entity.isPushable())
                {
                    handleCollision(entity);
                }
            }

        }
        /// Collision with living - Ramming
        if(passenger != null && lastCollidedEntity instanceof LivingEntity){
            /// todo, przy określonej wielkości entity jakieś konsekwencje dla samochodu (dmg, spowolnienie)? DMG przy Crash - maksymalnie tyle samo co w zwykłej na ten moment - lekko dziwny system.
            //TODO:  Podbicie Gracza jest pobugowane, czasami wywala w kosmos, czasami za lekko - z tym zawsze były problemy (żyrafa for example)
            double colSpeedF = getSpeed();
            lastCollidedEntity.addVelocity(prevMotionX, prevMotionY + colSpeedF, prevMotionZ);
            if(lastCollidedEntity instanceof PlayerEntity player2 && !collPlayerDublet){
                sendPlayerCrash(player2, prevMotionX, prevMotionY + colSpeedF, prevMotionZ);
            }
            if(colSpeedF >= 0.2D) {
                lastCollidedEntity.damage(passenger, (int) (automobile.COLLISION_DAMAGE_ENTITY * (colSpeedF * 2.0D)));
            }
        }
        collPlayerDublet = false;
        /// Spowolnienie na tyle duże, że kwalfikowane jako kolizja - Crash
        if(passenger != null && getPrevSpeed() - getSpeed() > automobile.COLLISION_SPEED_MIN)
        {
            if(lastCollidedEntity != null)
            {
                if(automobile.COLLISION_FLIGHT_ENTITY)
                {
                    if(!(lastCollidedEntity instanceof WW2Tank)) { /// Lista czego nie przesuwać przy Car-Crash
                        lastCollidedEntity.addVelocity(prevMotionX, prevMotionY + 1.0D, prevMotionZ);
                        if(lastCollidedEntity instanceof PlayerEntity player1){
                            sendPlayerCrash(player1, prevMotionX, prevMotionY + getPrevSpeed(), prevMotionZ);
                            collPlayerDublet = true;
                        }
                    }
                }
                if(automobile.COLLISION_DAMAGE) /// DMG from Crash i Ramming się wyklucza, albo jedno albo drugie się odpala.
                {
                    lastCollidedEntity.damage(this, (int) (automobile.COLLISION_DAMAGE_ENTITY * (getPrevSpeed() * 2.0D))); /// Kolizja z zatrzymaniem
                }
            }
            if(automobile.COLLISION_DAMAGE) /// Jako, że źrodłem obrażeń jest lastCollidedEntity, zderzenie z LivingEntity nie zada obrażeń.
            {
                damage(lastCollidedEntity, (int) (automobile.COLLISION_DAMAGE_SELF * (getPrevSpeed() * 2.0D)));
            }
            if(automobile.COLLISION_FLIGHT_PLAYER && lastCollidedEntity == null) /// Wypadnięcie jedynie na "drzewie", nigdy na Entity.
            {
                PlayerEntity playerPass = (PlayerEntity)passenger;
                passenger.addVelocity(prevMotionX, prevMotionY + 1.0D, prevMotionZ);
                passenger.setVehicle(null);
                broadcastEventExit();
                sendPlayerCrash(playerPass, prevMotionX, prevMotionY + 1.0D, prevMotionZ);
            }
        }
        lastCollidedEntity = null;
        ///
        prevMotionX = velocityX;
        prevMotionY = velocityY;
        prevMotionZ = velocityZ;
        if(passenger != null && (passenger.dead || !passenger.isAlive()))
        {
            passenger = null;
            broadcastEventExit();
        }
        this.wheelsYaw *= 0.8F;
        if(this.wheelsYaw > 10.0F) {
            this.wheelsYaw = 10.0F;
        }

        if(this.wheelsYaw < -10.0F) {
            this.wheelsYaw = -10.0F;
        }
        if(vehicleFuel <= 0 && passenger != null && !world.isRemote)
        {
            if(cargoItems[0] != null && cargoItems[0].itemId == mod_Vehicles.vehicleFuel.id)
            {
                vehicleFuel = automobile.vehicleFuelAdd;
                removeStack(0, 1);
            }
        }
        if(vehicleFuel > 0 && passenger != null)
        {
            vehicleFuel--;
        }
        if((automobile.MAX_HEALTH-health) > (4.5 * automobile.MAX_HEALTH) / 5 && random.nextInt(30) == 0)   ///samoniszczenie //todo? tank też
        {
            damage(this, 1);
        }
        if(towingEntity != null)
        {
            updateTowedPosition();
        }
        if(velocityY > 0) { /// Anty-Podskakiwanie
            velocityY = -0.001;
        }
        wheelsAngle += (float)getSpeed() / 2F; /// Do Obracania kół - zepsut
        if(wheelsAngle > 3600){
            wheelsAngle = 0;
        }
        if(!spawnedSeats){ /// Pass-Seat Spawn
            spawnedSeats = true;
            this.seats = new EntityPassengerSeat[automobile.numPassengers];
            addPassengerSeats();
        }
        if(!world.isRemote){ /// Server Data-Tracker send
            setOnGround(this.onGround);
            setClientYaw(yaw);
            setClientFuel(vehicleFuel);
            this.dataTracker.set(29, health);
        }
        if(SdkEnvTool.isEnvClient()){
            tickEffects(); /// Particles + Driving Sound
        }
    }

    public void tickEffects(){
        if(random.nextInt(automobile.MAX_HEALTH) > health * 2)
        {
            if(health < automobile.MAX_HEALTH/8)
                spawnParticles("flame", 2, false);
            if(health < automobile.MAX_HEALTH/4)
                spawnParticles("largesmoke", 2, false);
            if(health < automobile.MAX_HEALTH)
                spawnParticles("smoke", 4, false);
        }
        if(passenger != null)
        {
            if(soundLoopTime <= 0 && vehicleFuel > 0)
            {
                world.playSound(this, automobile.SOUND_RIDING, 1.0F, 1.0F);
                soundLoopTime = automobile.SOUND_LOOP_TIME_MAX;
            }
            soundLoopTime--;
        } else
        {
            soundLoopTime = 0;
        }
    }

    public void destroyVehicle(){
        boolean flagW = checkWaterCollisions();
        SdkExplosion explosion = new SdkExplosion(world, null, x, y, z, 1.5F, true, false, "random.explode", flagW);
        explosion.setVolume(6.0F);
        explosion.fireChance = 0.2F;
        explosion.explodeA();
        explosion.explodeB(true);
        if(SdkEnvTool.isEnvClient()) {
            if(flagW){
                spawnParticles("splash", 32, true);
                spawnParticles("bubble", 32, true);
            }else{
                spawnParticles("explode", 32, true);
                spawnParticles("smoke", 32, true);
                spawnParticles("lava", 32, false);
            }
        }
        if(!world.isRemote){
            dropParts();
            markDead();
        }
        //todo damage dla pasażerów + kierowcy dodatkowy / TANK też
    }

    public void dropParts(){
        int r8 = 2;
        float f8 = 1.5F;
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_body.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_wheel.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_wheel.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_wheel.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_wheel.itemId, 1, f8);
        dropItem(automobile.dyeColor.itemId, random.nextInt(2) + 1, f8);

        switch(engineType)
        {
            case 1: // '\001'
                if(random.nextInt(r8) == 0)
                    dropItem(ww2Parts.smallEngine.id, 1, f8);
                break;

            case 2: // '\002'
                if(random.nextInt(r8) == 0)
                    dropItem(ww2Parts.mediumEngine.id, 1, f8);
                break;

            case 3: // '\003'
                if(random.nextInt(r8) == 0)
                    dropItem(ww2Parts.largeEngine.id, 1, f8);
                break;

        }
    }

    public double getMotionYaw()
    {
        double d;
        if(velocityX >= 0.0D && velocityZ >= 0.0D)
        {
            d = Math.atan(Math.abs(velocityZ / velocityX)) * 57.295779513082323D + 180D;
        } else
        if(velocityX >= 0.0D && velocityZ <= 0.0D)
        {
            d = Math.atan(Math.abs(velocityX / velocityZ)) * 57.295779513082323D + 90D;
        } else
        if(velocityX <= 0.0D && velocityZ >= 0.0D)
        {
            d = Math.atan(Math.abs(velocityX / velocityZ)) * 57.295779513082323D + 270D;
        } else
        {
            d = Math.atan(Math.abs(velocityZ / velocityX)) * 57.295779513082323D;
        }
        return d;
    }

    public void projectMotion(double d)
    {
        double d1 = (d * 3.1415926535897931D) / 180D;
        double d2 = Math.cos(d1) * velocityX - Math.sin(d1) * velocityZ;
        double d3 = Math.sin(d1) * velocityX + Math.cos(d1) * velocityZ;
        double d4 = getSpeed();
        double d5 = d4 * Math.cos(d1);
        d2 *= d5 / d4;
        d3 *= d5 / d4;
        velocityX = d2;
        velocityZ = d3;
    }

    public double getSpeed()
    {
        return Math.sqrt(velocityX * velocityX + velocityZ * velocityZ);
    }

    public void multiplySpeed(double d)
    {
        velocityX *= d;
        velocityZ *= d;
    }

    public double getTurnSpeed()
    {
        return scaleOnSpeed((automobile.TURN_SPEED_STOPPED + engineType*0.5D), (automobile.TURN_SPEED_FULL + engineType*0.25D));
    }

    public double getAccelForward()
    {
        return scaleOnSpeed((automobile.ACCEL_FORWARD_STOPPED+ engineType*0.005D), (automobile.ACCEL_FORWARD_FULL+ engineType*0.001D));
    }

    public double getAccelBackward()
    {
        return scaleOnSpeed((automobile.ACCEL_BACKWARD_STOPPED+ engineType*0.0025D), (automobile.ACCEL_BACKWARD_FULL+ engineType*0.001D));
    }

    public double scaleOnSpeed(double d, double d1)
    {
        return d - (d - d1) * (getSpeed() / (automobile.MAX_SPEED + engineType*0.03D));
    }

    public void handleCollision(Entity entity)
    {
        /// POKOMBINOWAĆ TRZEBA - Słoń powinien przesunąć
        /// Tutaj zapogiegam odpalania onCollision dla Living, żeby siebie NAWZAJEm nie przesuwać
        if(!(entity instanceof LivingEntity) && !(entity instanceof EntityPassengerSeat pasSeat && pasSeat.mother == this)){  /// odpali onCollision jedynie dla obcego Col Boxa
           entity.onCollision(this);
        }
        /// Wykluczam do wyliczeń Kraksy - gdzie prędkość spadła tak mocno, że pojazd Dostaje DMG - i wtedy szuka czy lastCollidedEnt również należy uderzyć.
        if(entity.passenger != this && entity.vehicle != this && !(entity.vehicle instanceof EntityPassengerSeat) && !(entity instanceof EntityPassengerSeat passSeat && passSeat.mother == this))
        {
            lastCollidedEntity = entity;
        }
    }

    public void setRotationPitch(float f)
    {
        if((double)(f - pitch) > automobile.ROTATION_PITCH_DELTA_MAX)
        {
            pitch += (float) automobile.ROTATION_PITCH_DELTA_MAX;
        } else
        if((double)(pitch - f) > automobile.ROTATION_PITCH_DELTA_MAX)
        {
            pitch -= (float) automobile.ROTATION_PITCH_DELTA_MAX;
        } else
        {
            pitch = f;
        }
    }

    public void spawnParticles(String s, int i, boolean flag)
    {
        for(int j = 0; j < i; j++)
        {
            double d = (x + random.nextDouble() * (double)width * 1.5D) - (double)width * 0.75D;
            double d1 = ((y + random.nextDouble() * (double)height) - (double)height * 0.5D) + 0.75D;
            double d2 = (z + random.nextDouble() * (double)width) - (double)width * 0.5D;
            double d3 = flag ? random.nextDouble() - 0.5D : 0.0D;
            double d4 = flag ? random.nextDouble() - 0.5D : 0.0D;
            double d5 = flag ? random.nextDouble() - 0.5D : 0.0D;
            SdkParticleFactory.addVanillaParticle(world, s, d, d1, d2, d3, d4, d5);
        }

    }

    public double getPrevSpeed()
    {
        return Math.sqrt(prevMotionX * prevMotionX + prevMotionZ * prevMotionZ);
    }

    public float getTurnSpeedForRender() /// Tego ATV używa w Render, ale nie rozumiem do końca dlaczego. Raczej remove to + i w Tank.
    {
        return (float)(lastTurnSpeed * automobile.TURN_SPEED_RENDER_MULT);
    }

    @Override
    public float getEyeHeight()
    {
        return 0.7F;
    }

    @Override
    public void writeNbt(NbtCompound nbttagcompound)
    {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putInt("Health", health);
        nbttagcompound.putInt("Engine", engineType);
        nbttagcompound.putInt("Fuel", vehicleFuel);
        nbttagcompound.putString("Type", automobile.name);

    }

    @Override
    public void readNbt(NbtCompound nbttagcompound)
    {
        automobile = mod_Vehicles.getTruckType(nbttagcompound.getString("Type"));
        setDataFromTruck(automobile);
        standingEyeHeight = automobile.standingOko;
        setBoundingBoxSpacing(automobile.autoWidth, automobile.autoHeight);
        setPosition(x, y, z);
        super.readNbt(nbttagcompound);
        health = nbttagcompound.getInt("Health");
        vehicleFuel = nbttagcompound.getInt("Fuel");
        engineType = nbttagcompound.getInt("Engine");
    }

    @Override
    public double getPassengerRidingHeight()
    {
        return automobile.playerYOffset;
    }

    @Override
    public void updatePassengerPosition(){
            if(passenger == null){
                return;
            }
        passenger.velocityX = 0.0D;
        passenger.velocityY = 0.0D;
        passenger.velocityZ = 0.0D;

            double d = automobile.playerXOffset;;
            double d1 = getPassengerRidingHeight() + passenger.getStandingEyeHeight();
//            double d1 = automobile.playerYOffset + 1;
            double d2 = automobile.playerZOffset;
            double d3 = Math.cos(((double)(-yaw) / 180D) * 3.1415926535897931D);
            double d4 = Math.sin(((double)(-yaw) / 180D) * 3.1415926535897931D);
            /// Issue: Little "freeze" on drop
//            double d5 = Math.cos(((double)pitch / 180D) * 3.1415926535897931D); /// GÓRA - DÓŁ
//            double d6 = Math.sin(((double)pitch / 180D) * 3.1415926535897931D) * 0.5D; /// Przesunięcie PRZÓD-TYŁ
            double d6 = Math.sin(((double)pitch / 180D) * 3.1415926535897931D * 0.25D); /// Przesunięcie PRZÓD-TYŁ 25%
            /// Oryginal
            double d5 = Math.cos(((double)pitch / 180D) * 3.1415926535897931D);
//            double d6 = Math.sin(((double)pitch / 180D) * 3.1415926535897931D);
            /// Simple no drop freeze
//            double d5 = Math.cos(((double)0 / 180D) * 3.1415926535897931D);
//            double d6 = Math.sin(((double)0 / 180D) * 3.1415926535897931D);

            double d7 = Math.cos(((double)yaw * 3.1415926535897931D) / 180D) * 0.4D * d5;
            double d8 = Math.sin(((double)yaw * 3.1415926535897931D) / 180D) * 0.4D * d5;
            double d9 = (d * d5 - d1 * d6) * d3 + d2 * d4;
            double d10 = d * d6 + d1 * d5;
            double d11 = (d1 * d6 - d * d5) * d4 + d2 * d3;
            passenger.setPosition(x + d9 + d7, y + d10, z + d11 + d8);

        passenger.velocityX = 0.0D;
        passenger.velocityY = 0.0D;
        passenger.velocityZ = 0.0D;
    }

    public void updateTowedPosition() /// Zupełnie nie wiadomo co jescze...
    {
        if(towingEntity == null)
        {
            return;
        }
        if(getSquaredDistance(towingEntity) > 36D)
        {
            towEntity(towingEntity);
        } else
        {
            double d = -1 * 2;  //-blockCheck (Integer) * 2
            double d1 = 0.0D;
            double d2 = 0.0D;
            double d3 = -Math.cos(((double)(-yaw) / 180D) * 3.1415926535897931D);
            double d4 = -Math.sin(((double)(-yaw) / 180D) * 3.1415926535897931D);
            double d5 = Math.cos(((double)pitch / 180D) * 3.1415926535897931D);
            double d6 = Math.sin(((double)pitch / 180D) * 3.1415926535897931D);
            double d7 = Math.cos(((double)(yaw) * 3.1415926535897931D) / 180D) * 0.4D * d5;
            double d8 = Math.sin(((double)(yaw) * 3.1415926535897931D) / 180D) * 0.4D * d5;
            double d9 = (d * d5 - d1 * d6) * d3 + d2 * d4;
            double d10 = d * d6 + d1 * d5;
            double d11 = (d1 * d6 - d * d5) * d4 + d2 * d3;
            double d12 = (x + d7 + d9) - towingEntity.x;
            double d13 = (y + d10) - towingEntity.y;
            double d14 = (z + d8 + d11) - towingEntity.z;
            towingEntity.move(d12, d13, d14);
        }
    }

    public boolean towEntity(Entity entity)
    {
        if(towingEntity != null && towingEntity == entity)
        {
            towingEntity = null;
            return true;
        }
        if(towingEntity == null)
        {
            towingEntity = entity;
            return true;
        }else{
            towingEntity = null;
            return true;
        }
    }

    /// Do przetestowania na dłuższych dystansach...
//    public boolean shouldRenderAtDistance(double d) { ///
//        return true;
//    }

    @Override
    public void exitKey(PlayerEntity playerEntity) {
        alternateExit(playerEntity);
        broadcastEventExit();
        exitWithPupils(playerEntity);
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
    public void rocketKey() {
        if(towingEntity == null)
        {
            List list = world.getEntities(this, boundingBox.expand(0.1D, 0.0D, 0.1D));
            if(list != null && list.size() > 0)
            {
                for(int j2 = 0; j2 < list.size(); j2++)
                {
                    Entity entity = (Entity)list.get(j2);

                    if(entity instanceof WW2Cannon && towingEntity == null)
                    {
                        towEntity(entity);
                    }else
                    {
                        entity.onCollision(this);
                    }
                }
            }
        }else{
            towEntity(towingEntity);
        }
    }

    @Override
    public int getPercentHealth() {
        return (int) (((double)health/(double)automobile.MAX_HEALTH)*100D);
    }

    @Override
    public float getArmorFactor() {
        return 1.0F;
    }

    @Override
    public float getDmgReduce() {
        return 0.5F;
    }

    @Override
    public float getDmgBroken() {
        return 1.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(mod_Vehicles.MOD_ID, "Truck");
    }

    private double lastTurnSpeed;
    public boolean lastOnGround;
    public int health;
    public double prevMotionX;
    public double prevMotionY;
    public double prevMotionZ;
    public Entity lastCollidedEntity;
    public Entity towingEntity;

    public int soundLoopTime;
    public float wheelsYaw;
    public float prevRotationRoll;
    public RotatedAxes axes;
    public int engineType;
    public float wheelsAngle; //TODO dla obracających się kół, ale zepsute na razie - Tank też
    public TruckType automobile;

    public boolean spawnedSeats;
    public EntityPassengerSeat[] seats;
    public int uphillTicks;
    public boolean receivedP = false;
    public boolean collPlayerDublet;

    public void broadcastEventExit(){
        world.broadcastEntityEvent(this, (byte)6);
    }

    public void broadcastEventExplode(){
        world.broadcastEntityEvent(this, (byte)8);
    }

    public void sendPlayerCrash(PlayerEntity player, double x, double y, double z){
        if(SdkEnvTool.isEnvServ()) {
            PacketHelper.sendTo(player, new CarCrashPacket(x,y,z));
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void processServerEntityStatus(byte status) {
        if (status == 6) {
            passenger = null;
        } else if (status == 7) {
            world.playSound(this, "ww2:mechhurt", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (status == 8) {
            destroyVehicle();
        }  else if (status == 9){
//            world.playSound(this, "sdk:wrench", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (status == 110){
            System.out.println("TESTOWY EVENT 110");
//            this.passenger = null;
        }else{
            super.processServerEntityStatus(status);
        }
    }

    @Override
    protected void initDataTracker()
    {
        dataTracker.startTracking(16, (byte) 0); //onGround
        dataTracker.startTracking(17, 0); //Yaw
        dataTracker.startTracking(18, 0); //Fuel

        dataTracker.startTracking(29, 0); //HEALTH
    }

    public void remoteTick(){
        if(clientInterpolationSteps == 0 || automobile == null){
            return;
        }
        onGround = getOnGround();
//        double xt = x + (clientX - x) / clientInterpolationSteps;
//        double yt = y + (clientY - y) / 2;
//        double zt = z + (clientZ - z) / clientInterpolationSteps;

//        double xt = x + (clientX - x) / ((double) clientInterpolationSteps - 0.5D);
//        double yt = y + (clientY - y) / ((double) clientInterpolationSteps - 1.5D);
//        double zt = z + (clientZ - z) / ((double) clientInterpolationSteps - 0.5D);

        double xt = x + (clientX - x) / ((double) clientInterpolationSteps - 0.5D);
        double yt = y + (clientY - y) / ((double) clientInterpolationSteps - 2.5D);
        double zt = z + (clientZ - z) / ((double) clientInterpolationSteps - 0.5D);

        boolean flag1 = true;
        if(getClientSpeed() != 0.0D)
        {
            double d2 = (yaw * 3.1415926535897931D) / 180D;
            double d6 = Math.cos(d2);
            flag1 = -d6 > 0.0D && clientVelocityX > 0.0D || -d6 < 0.0D && clientVelocityX < 0.0D;
        }
        int forwOrBack = flag1 ? 1 : -1;
        if(onGround && lastOnClientGround)
        {
            if(clientPrevY - clientY > 0.2D)
            {
                pitch = 45 * forwOrBack;
            } else
            if(clientPrevY - clientY < -0.2D)
            {
                pitch = -45 * forwOrBack;
            } else
            {
                pitch = 0.0F;
            }
        } else
        {
            setRotationPitch(Math.max(Math.min((float)((-90D * clientVelocityY) / getClientSpeed()) * (float)forwOrBack, 90F), -90F) / 2.0F);
        }
        lastOnClientGround = onGround;
        clientPrevY = clientY;

        float merkar2 = getClientYaw();
        float angleYaw = merkar2 % 360.0F;

        float prevRYaw = yaw;
        double yrd = angleYaw - yaw;
        while (yrd < 180F) yrd += 360F;
        while (yrd > 180.0F) yrd -= 360.0F;
        yaw += (float) (yrd / (clientInterpolationSteps - 2)); /// 0

        double pyrd1 = yaw - prevRYaw; //
        setPosition(xt, yt, zt);
        setRotation(yaw, pitch);
        clientInterpolationSteps--;

        if(pyrd1 == 0.0D){
        }
        if(pyrd1 < 0.0D){ /// lewo-prawo??
            wheelsYaw = (float)((double)wheelsYaw - 0.5D * getClientTurnSpeedRender() * forwOrBack);
        }
        if(pyrd1 > 0.0D){ // yrd
            wheelsYaw = (float)((double)wheelsYaw + 0.5D * getClientTurnSpeedRender() * forwOrBack);
        }
        this.wheelsYaw *= 0.8F;
        if(this.wheelsYaw > 10.0F) {
            this.wheelsYaw = 10.0F;
        }

        if(this.wheelsYaw < -10.0F) {
            this.wheelsYaw = -10.0F;
        }
        vehicleFuel = getClientFuel();
        health = dataTracker.getInt(29);
        tickEffects();
    }

    public double getClientSpeed()
    {
        return Math.sqrt(clientVelocityX * clientVelocityX + clientVelocityZ * clientVelocityZ);
    }

    public double getClientTurnSpeedRender()
    {
        return scaleOnClientSpeed((automobile.TURN_SPEED_STOPPED + engineType*0.5D), (automobile.TURN_SPEED_FULL + engineType*0.25D));
    }

    public double scaleOnClientSpeed(double d, double d1)
    {
        return d - (d - d1) * (getClientSpeed() / (automobile.MAX_SPEED + engineType*0.03D));
    }

    //GROUND
    public boolean getOnGround()
    {
        return (dataTracker.getByte(16) & 1) != 0;
    }
    public void setOnGround(boolean flag)
    {
        if(flag)
        {
            dataTracker.set(16, (byte) 1);
        } else
        {
            dataTracker.set(16, (byte) 0);
        }
    }

    //YAW
    public void setClientYaw(float age)
    {
        dataTracker.set(17, Float.floatToRawIntBits(age));
    }
    public float getClientYaw()
    {
        return Float.intBitsToFloat(dataTracker.getInt(17));
    }

    //Fuel
    public void setClientFuel(int fuel)
    {
        dataTracker.set(18, fuel);
    }

    public int getClientFuel()
    {
        return dataTracker.getInt(18);
    }
}
