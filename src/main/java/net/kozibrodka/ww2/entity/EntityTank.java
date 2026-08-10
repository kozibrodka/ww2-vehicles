package net.kozibrodka.ww2.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk_api.ingame.mod_SdkFlasher;
import net.kozibrodka.sdk_api.particle.SdkParticleFactory;
import net.kozibrodka.sdk_api.utils.*;
import net.kozibrodka.ww2.entityBullet.ShellFactory;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.events.ww2Parts;
import net.kozibrodka.ww2.network.CarCrashPacket;
import net.kozibrodka.ww2.network.PassengerEnterPacket;
import net.kozibrodka.ww2.network.TruckLoadPacket;
import net.kozibrodka.ww2.properties.TankType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@HasTrackingParameters(trackingDistance = 160, updatePeriod = 2, sendVelocity = TriState.TRUE)
public class EntityTank extends EntityVehicle implements WW2Tank, EntitySpawnDataProvider {

    public EntityTank(World world)
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
        gunYaw = 0.0F;
        gunPitch = 0.0F;
        gunMachineGun = new ItemStack(mod_Vehicles.itemGunMachineGun);
        renderDistanceMultiplier = 2; //jakos to dostosoawac
        currentShell = ShellType.NULL;

    }

    public EntityTank(World world, double d, double d1, double d2)
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

    public EntityTank(World world, double d, double d1, double d2,
                      PlayerEntity entityplayer, int i, TankType vehicletype)
    {
        this(world);
        automobile = vehicletype;
        setDataFromTank(automobile);
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
                otherEntity.addVelocity(var2, 0.0F, var4);
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
        int fallDMG = (int)Math.ceil(fallDistance - 3.0F) * 6;
        if (fallDMG > 0) {
            damage(null, fallDMG);
        }
        if (passenger instanceof LivingEntity living1) {
            living1.onLanding(fallDistance);
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
        return 0.0F;
    }

    @Override
    public boolean interact(PlayerEntity entityplayer)
    {
        if(world.isRemote){
            if(!(passenger != null && passenger != entityplayer))
            {
                SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
            }
            return true;
        }
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == mod_Vehicles.vehicleBlowTorch.id)
        {
            if(health > 0 && health < automobile.MAX_HEALTH)
            {
                world.playSound(this, "ww2:blowtorch", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
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
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == mod_Vehicles.wrenchGoldDebug.id)
        {
            System.out.println("TYPE: " + automobile.name);
            System.out.println("ENGINE: " + engineType);
            System.out.println("HEALTH: " + health);
            System.out.println(ShellType.AP.ordinal() + "  lsita: " + Arrays.toString(ShellType.values()));
            for(ShellType kolor: ShellType.values()) {
                System.out.println(kolor.ordinal() + " " + kolor.name());
            }
            entityplayer.swingHand();
            return true;
        }
        if(passenger != null && passenger != entityplayer)
        {
            return true;
        }
        if(!world.isRemote && passenger == null)
        {
            SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
            if(currentShell.ordinal() == 0){
                currentShell = getFirstShell();
            }
            broadcastEventShellChange(currentShell.ordinal());
            entityplayer.setVehicle(this);
            if(SdkEnvTool.isEnvServ()) {
                PacketHelper.sendToAllTracking(this, new PassengerEnterPacket(this.id, entityplayer.name));
            }
        }
        return true;
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
                /// LEWO-PRAWO
                    double d4 = 0.0D;
                    if(vehicleFuel > 0 && clientLEFT)
                    {
                        d4 = -getTurnSpeed() * (double)(flag1 ? 1 : -1);
                    } else
                    if(vehicleFuel > 0 && clientRIGHT)
                    {
                        d4 = getTurnSpeed() * (double)(flag1 ? 1 : -1);
                    }
                    if(d4 != 0.0D)
                    {
                        yaw += d4;
                        if(getSpeed() != 0.0D){
                            projectMotion(d4);
                        }
                    }
                    lastTurnSpeed = d4 * (double)(flag1 ? 1 : -1);
                /// PRZÓD-TYŁ
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
                ///
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
        if(checkWaterCollisions())
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
                uphillTicks = 10; /// Daje 10 ticksów spowolnienia pod górke
                multiplySpeed(0.75D); /// Jednorazowe spowolnienie
            } else
            {
//                pitch = 0.0F;
                if(uphillTicks > 0){
                    if(pitch > 0.0F){
                        pitch -= 5.0F;
                    }
                    if(pitch < 0.0F){
                        pitch += 5.0F;
                    }
                }else{
                    pitch = 0.0F;
                }
            }
            velocityY -= 0.001D;
        } else
        {
            setRotationPitch(Math.max(Math.min((float)((-90D * velocityY) / getSpeed()) * (float)forwOrBack, 90F), -90F) / 2.0F);
            velocityY = y - prevY - automobile.FALL_SPEED;
        }
        lastOnGround = onGround;
        /// Handling Collision
        List list = world.getEntities(this, boundingBox.expand(0.20000000000000001D, 0.0D, 0.20000000000000001D));
        if(list != null && list.size() > 0)
        {
            for(int j = 0; j < list.size(); j++)
            {
                Entity entity = (Entity)list.get(j);
                if(entity != passenger && entity.isPushable()) //canbepushed
                {
                    handleCollision(entity);
                }
            }

        }
        /// Collision with living  - Ramming
        if(passenger != null && lastCollidedEntity instanceof LivingEntity){
            double colSpeedF = getSpeed();
            lastCollidedEntity.addVelocity(prevMotionX, prevMotionY + colSpeedF, prevMotionZ);
            if(lastCollidedEntity instanceof PlayerEntity player2 && !collPlayerDublet){
                sendPlayerCrash(player2, prevMotionX, prevMotionY + colSpeedF, prevMotionZ);
            }
            if(colSpeedF >= 0.1D) {
                lastCollidedEntity.damage(passenger, (int) (automobile.COLLISION_DAMAGE_ENTITY * (colSpeedF * 1.0D)));
                /// damage from passenger - animals aggro
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
                    if(lastCollidedEntity instanceof WW2Tank){
                        lastCollidedEntity.addVelocity(prevMotionX/64, prevMotionY/64 + 0.1D, prevMotionZ/64);
                    }else {
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
        tickTurret(); /// Turret, Cannon Yaw/Pitch
        if(shootDelay > 0)
        {
            shootDelay--;
        }
        if(shellDelay > 0)
        {
            shellDelay--;
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
        if((automobile.MAX_HEALTH-health) > (4.5 * automobile.MAX_HEALTH) / 5 && random.nextInt(30) == 0)  ///samoniszczenie
        {
            damage(this, 1);
        }
        if(velocityY > 0) { /// Anty-Podskakiwanie
            velocityY = -0.001;
        }
        wheelsAngle += (float)getSpeed() / 2F; /// Do Obracania kół - zepsute
        if(wheelsAngle > 3600)
            wheelsAngle = 0;
        fireMachineGun(); ///M-G FIRE
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
        if(random.nextInt(automobile.MAX_HEALTH) > health * 2) /// Particles
        {
            if(health < automobile.MAX_HEALTH/8)
                spawnParticles("flame", 2, false);
            if(health < automobile.MAX_HEALTH/4)
                spawnParticles("largesmoke", 2, false);
            if(health < automobile.MAX_HEALTH)
                spawnParticles("smoke", 4, false);
        }
        if(passenger != null) /// Dźwięk + Secondary Fire
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

    public void tickTurret(){
        if(passenger != null && automobile.hasTurret)
        {
            if(automobile.tankDestroyer)
            {
                gunYaw = -(180);
            }else{
                float passYaw = (-((passenger.yaw + 90F) - yaw)) % 360.0F;
                while (gunYaw > 0) gunYaw -= 360F;
                while (gunYaw < -360) gunYaw += 360F;
                while (passYaw > 0) passYaw -= 360F;
                float checkYaw = Math.abs(gunYaw-passYaw);
                if(checkYaw < automobile.turretYawSpeed || checkYaw > (360.0F - automobile.turretYawSpeed)){
                    gunYaw = passYaw;
                }else{
                    if(gunYaw > passYaw){
                        if(gunYaw-passYaw <= 180F){
                            gunYaw -= automobile.turretYawSpeed;
                        }else{
                            gunYaw += automobile.turretYawSpeed;
                        }
                    }
                    if(gunYaw < passYaw){
                        if(passYaw-gunYaw >= 180F){
                            gunYaw -= automobile.turretYawSpeed;
                        }else{
                            gunYaw += automobile.turretYawSpeed;
                        }
                    }
                }
            }
            float passPitch = passenger.pitch - pitch;
            if(Math.abs(passPitch - gunPitch) < automobile.turretPitchSpeed){
                gunPitch = passPitch;
            }else{
                if(passPitch > gunPitch){
                    gunPitch += automobile.turretPitchSpeed;
                }
                if(passPitch < gunPitch){
                    gunPitch -= automobile.turretPitchSpeed;
                }
            }
            while(gunPitch > automobile.gunPitchMax) gunPitch = automobile.gunPitchMax;
            while(gunPitch < automobile.gunPitchMin) gunPitch = automobile.gunPitchMin;
            System.out.println(gunYaw);
        }
    }

    public void destroyVehicle(){
        boolean flagW = checkWaterCollisions();
        SdkExplosion explosion = new SdkExplosion(world, null, x, y, z, 2.5F, true, false, "random.explode", flagW);
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
    }

    public void dropParts(){
        int r8 = 2;
        float f8 = 1.5F;
        if(automobile.item_cannon.itemId != Item.EGG.id && random.nextInt(r8) == 0)
            dropItem(automobile.item_cannon.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_body.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_turret.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_mg.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_track.itemId, 1, f8);
        if(random.nextInt(r8) == 0)
            dropItem(automobile.item_track.itemId, 1, f8);
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
        /// Tutaj zapogiegam odpalania onCollision dla Living, żeby siebie NAWZAJEm nie przesuwać
        if(!(entity instanceof LivingEntity) && !(entity instanceof EntityPassengerSeat pasSeat && pasSeat.mother == this)){  /// odpali onCollision jedynie dla obcego Col Boxa
            entity.onCollision(this);
        }
        /// Wykluczam do wyliczeń Kraksy - gdzie prędkość spadła tak mocno, że pojazd Dostaje DMG - i wtedy szuka czy lastCollidedEnt również należy uderzyć.
        if(entity.passenger != this && entity.vehicle != this && !(entity.vehicle instanceof EntityPassengerSeat) && !(entity instanceof EntityPassengerSeat passSeat && passSeat.mother == this)) //TODO te passSeaty nie są na ten moment potrzebne dla czołgu
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
            double d1 = ((y + random.nextDouble() * (double)height) - (double)height * 0.5D) + 1.50D;
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

    public float getTurnSpeedForRender()
    {
        return (float)(lastTurnSpeed * automobile.TURN_SPEED_RENDER_MULT);
    }    //?

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
        nbttagcompound.putFloat("GunYaw", gunYaw);
        nbttagcompound.putFloat("GunPitch", gunPitch);
        nbttagcompound.putInt("Engine", engineType);
        nbttagcompound.putInt("Fuel", vehicleFuel);
        nbttagcompound.putString("Type", automobile.name);
        nbttagcompound.putInt("Shell", currentShell.ordinal());
    }

    @Override
    public void readNbt(NbtCompound nbttagcompound)
    {
        automobile = mod_Vehicles.getTankType(nbttagcompound.getString("Type"));
        setDataFromTank(automobile);
        standingEyeHeight = automobile.standingOko;
        setBoundingBoxSpacing(automobile.autoWidth, automobile.autoHeight);
        setPosition(x, y, z);
        super.readNbt(nbttagcompound);
        health = nbttagcompound.getInt("Health");
        gunYaw = nbttagcompound.getFloat("GunYaw");
        gunPitch = nbttagcompound.getFloat("GunPitch");
        vehicleFuel = nbttagcompound.getInt("Fuel");
        engineType = nbttagcompound.getInt("Engine");
        currentShell = ShellType.values()[nbttagcompound.getInt("Shell")];
    }

    @Override
    public double getPassengerRidingHeight()
    {
        return automobile.playerYOffset;
    }

    @Override
    public void updatePassengerPosition(){

        double d = automobile.playerXOffset;;
        double d1 = getPassengerRidingHeight() + passenger.getStandingEyeHeight();
        double d2 = automobile.playerZOffset;
        double d3 = Math.cos(((double)(-yaw) / 180D) * 3.1415926535897931D);
        double d4 = Math.sin(((double)(-yaw) / 180D) * 3.1415926535897931D);
        double d5 = Math.cos(((double)pitch / 180D) * 3.1415926535897931D);
        double d6 = Math.sin(((double)pitch / 180D) * 3.1415926535897931D);
        double d7 = Math.cos(((double)yaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D * d5;
        double d8 = Math.sin(((double)yaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D * d5;
        double d9 = (d * d5 - d1 * d6) * d3 + d2 * d4;
        double d10 = d * d6 + d1 * d5;
        double d11 = (d1 * d6 - d * d5) * d4 + d2 * d3;
        passenger.setPosition(x + d9 + d7, y + d10, z + d11 + d8);
    }

//    public boolean shouldRenderAtDistance(double d) {
//        return true;
//    }
    /// SDK INTERFACE
    @Override
    public void reloadKey() {
        changeShell(currentShell);
    }

    @Override
    public void exitKey(PlayerEntity entityplayer) {
        passenger.setVehicle(null);
        broadcastEventExit(); ///zobacz czy alternate exit nie potrzeba
    }

    @Override
    public void bombKey() {
        if(automobile.antiAircraft){ //TODO AA-CODE - wszystko do wymiany...
            if(world.isRemote || shellDelay > 0 || !automobile.hasTurret)
            {
                return;
            }
            int k2 = 0;
            for(int j1 = slots_FirstShell; j1 < slots_Last; j1++)
            {
                if(cargoItems[j1] != null && cargoItems[j1].itemId == mod_Vehicles.aaShellTank.id)
                {
                    k2 = j1;
                }
            }

            if(k2 != 0)
            {
                double d2 = (double)automobile.shellXOffset / 16D;
                double d4 = 0.0D;
                double d6 = (double)automobile.shellZOffset / 16D;
                double d8 = Math.cos(((double)(-(yaw + (-gunYaw))) / 180D) * 3.1415926535897931D);
                double d10 = Math.sin(((double)(-(yaw + (-gunYaw))) / 180D) * 3.1415926535897931D);
                double d12 = Math.cos(((double)(-(pitch + gunPitch)) / 180D) * 3.1415926535897931D);
                double d14 = Math.sin(((double)(-(pitch + gunPitch)) / 180D) * 3.1415926535897931D);
                double d16 = (d2 * d12 - d4 * d14) * d8 + d6 * d10;
                double d18 = d2 * d14 + d4 * d12;
                double d20 = (d4 * d14 - d2 * d12) * d10 + d6 * d8;

                world.spawnEntity(new EntityAAShell(world, x + d16, y + d18 + (automobile.shellYOffset / 16D), z + d20, d16 / 3D, d18 / 3D, d20 / 3D, 7, 4, 2, automobile.gunFlakRange)); //wyjebałem stare properties DMG
                world.playSound(this, automobile.shootSound, 1.0F, 1.0F);

                mod_SdkFlasher.LightEntity(world, this, 15, 2);
                removeStack(k2, 1);
                shellDelay = automobile.vehicleShellDelay;
                automobile.shellZOffset = -automobile.shellZOffset;
            }
        }else{
            if(shellDelay <= 0 && currentShell == ShellType.NULL){
                broadcastEventEmptySound();
                shellDelay = automobile.vehicleShellDelay;
            }
            if(shellDelay > 0 || !automobile.hasTurret || currentShell == ShellType.NULL)
            {
                return;
            }
            int itemAmmo = 0;
            for(int j1 = slots_FirstShell; j1 < slots_Last; j1++)
            {
                if(cargoItems[j1] != null && cargoItems[j1].itemId == currentShell.ammoID)
                {
                    itemAmmo = j1;
                    break;
                }
            }

            if(itemAmmo != 0)
            {
                SdkEntityTankShell tShell = ShellFactory.getShellBasedOnTank(world,this, automobile, currentShell);
                world.spawnEntity(tShell);
                removeStack(itemAmmo, 1);
                shellDelay = automobile.vehicleShellDelay;
            }
        }
    }

    public void fireMachineGun(){
        if(clientFIRE){
            if(world.isRemote || shootDelay > 0 || !automobile.hasGuns)
            {
                return;
            }
            int j = 0;
            for(int i1 = slots_FirstBullet; i1 < slots_FirstShell; i1++)
            {
                if(cargoItems[i1] != null && cargoItems[i1].itemId == mod_Vehicles.tankBullet.id)
                {
                    j = i1;
                    break;
                }
            }

            if(j != 0)
            {
//                SdkEntityBulletMachineGun okurwa = new SdkEntityBulletMachineGun(world, this, ((SdkItemGun)automobile.gunMachineGun.getItem()), (float)(automobile.barrelX / 16D), (float)(automobile.barrelY / 16D), (float)(automobile.barrelZ / 16D), 90F, 0.0F);
//                world.spawnEntity(okurwa); ///jest Git tyle, że bez dźwięku...
                ///
                ((SdkItemGun)automobile.gunMachineGun.getItem()).onItemRightClickEntity(gunMachineGun, world, this, (float)(automobile.barrelX / 16D), (float)(automobile.barrelY / 16D), (float)(automobile.barrelZ / 16D), 90F, 0.0F, 0); //machine gun
                removeStack(j, 1);
                shootDelay = automobile.vehicleShootDelay;
            }else{
                broadcastEventEmptyMGSound();
                shootDelay = automobile.vehicleShootDelay;
            }
        }
    }

    @Override
    public void rocketKey() {
        if(Objects.equals(automobile.specialWeapon, "haul"))
        {
            /// wyjebałem holowanie z czołgów
        }
    }

    @Override
    public int getPercentHealth() {
        return (int) (((double)health/(double)automobile.MAX_HEALTH)*100D);
    }

    @Override
    public float getArmorFactor() { //TODO props reduce + factor both Car+tank
        return 3.0F;
    }

    @Override
    public float getDmgReduce() {
        return 0.0F;
    }

    @Override
    public float getDmgBroken() {
        return 0.1F;
    }

    @Override
    public String getAmmoName() {
        return currentShell.hudName;
    }

    @Override
    public String getBombName() {
        return "";
    }

    @Override
    public boolean canPassengerUseGun() {
        return false;
    }

    private double lastTurnSpeed;
    public boolean lastOnGround;
    public boolean doorOpen;
    public int health;
    public double prevMotionX;
    public double prevMotionY;
    public double prevMotionZ;
    public Entity lastCollidedEntity;
    public int flakGunOff;

    public int soundLoopTime;
    public float gunYaw;
    public float gunPitch;
    public float prevRotationRoll;
    public EntityPassengerSeat[] seats;
    public RotatedAxes axes;
    public int engineType;
    public float wheelsAngle;
//    public ItemStack[] cargoItems;
//    public int inventorySize;
//    public int vehicleFuel;
    private int shellDelay;
    private int shootDelay;
    public ItemStack gunMachineGun;
    public ItemStack gunSpecial;
    public TankType automobile;
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

    public void broadcastEventReload(){
        world.playSound(this, "ww2:tankreload", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        world.broadcastEntityEvent(this, (byte)9);
    }

    public void broadcastEventEmptySound(){
        world.playSound(this, "ww2:tnkfireempty", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        world.broadcastEntityEvent(this, (byte)14);
    }

    public void broadcastEventShellChange(int numer){
        world.broadcastEntityEvent(this, (byte)(numer + 10));
    }

    public void broadcastEventEmptyMGSound(){
        world.playSound(this, "ww2:gunempty", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        world.broadcastEntityEvent(this, (byte)15);
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
        } else if (status == 9){
            world.playSound(this, "ww2:tankreload", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (status >= 10 && status < 14) {
            currentShell = ShellType.values()[status - 10];
        }else if (status == 14) {
            world.playSound(this, "ww2:tnkfireempty", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }else if (status == 15) {
            world.playSound(this, "ww2:gunempty", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }else if (status == 16) {
            //  System.out.println("healing sound");
//            world.playSound(this, "sdk:wrench", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
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

    /// CLIENT TICK
    public void remoteTick(){
        if(clientInterpolationSteps == 0 || automobile == null){
            return;
        }
        onGround = getOnGround();
        double xt = x + (clientX - x) / clientInterpolationSteps;
        double yt = y + (clientY - y) / 2;
        double zt = z + (clientZ - z) / clientInterpolationSteps;

//        double xt = x + (clientX - x) / ((double) clientInterpolationSteps - 0.5D);
//        double yt = y + (clientY - y) / ((double) clientInterpolationSteps - 2.5D);
//        double zt = z + (clientZ - z) / ((double) clientInterpolationSteps - 0.5D);

        boolean flag1 = true;
        if(getClientSpeed() != 0.0D)
        {
            double d2 = (yaw * 3.1415926535897931D) / 180D;
            double d6 = Math.cos(d2);
            flag1 = -d6 > 0.0D && clientVelocityX > 0.0D || -d6 < 0.0D && clientVelocityX < 0.0D;
        }
        int forwOrBack = flag1 ? 1 : -1;
        /// /
        if(onGround && lastOnClientGround)
        {
            if(clientPrevY - clientY > 0.2D)
            {
                pitch = 45 * forwOrBack;
            } else
            if(clientPrevY - clientY < -0.2D)
            {
                pitch = -45 * forwOrBack;
                uphillTicks = 10;
            } else
            {
                if(uphillTicks > 0){
                    if(pitch > 0.0F){
                        pitch -= 5.0F;
                    }
                    if(pitch < 0.0F){
                        pitch += 5.0F;
                    }
                }else{
                    pitch = 0.0F;
                }
            }
        } else
        {
            setRotationPitch(Math.max(Math.min((float)((-90D * clientVelocityY) / getClientSpeed()) * (float)forwOrBack, 90F), -90F) / 2.0F);
        }
        ///
        lastOnClientGround = onGround;
        clientPrevY = clientY;

        float merkar2 = getClientYaw();
        float angleYaw = merkar2 % 360.0F;

        double yrd = angleYaw - yaw;
        while (yrd < 180F) yrd += 360F;
        while (yrd > 180.0F) yrd -= 360.0F;
        yaw += (float) (yrd / (clientInterpolationSteps - 2)); /// 0

        tickTurret();
        setPosition(xt, yt, zt);
        setRotation(yaw, pitch);
        clientInterpolationSteps--;

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

    /// Shells ENUMs
    public enum ShellType{
        NULL(0, ""),
        AP(mod_Vehicles.tankShell.id, "§9AP"),
        HE(mod_Vehicles.tankShellHE.id, "§6HE"),
        OBS(mod_Vehicles.aaShellTank.id, "§5OB");

        final int ammoID;
        final String hudName;

        ShellType(int id, String hud) {
            ammoID = id;
            hudName = hud;
        }
    }

    public ShellType currentShell;

    public void changeShell(ShellType type){
        ShellType swappedType = type;
        ShellType currentType = type;

        for(int i = 0; i < ShellType.values().length; i++){
            swappedType = getNextShell(swappedType);
            if(isAmmoPresent(swappedType)){
                currentType = swappedType;
            }

            if(currentType != type){
                broadcastEventReload();
                broadcastEventShellChange(currentType.ordinal());
                break;
            }
        }

        currentShell = currentType;
    }

    public ShellType getNextShell(ShellType current) {
        ShellType[] wartosci = ShellType.values();
        int next = (current.ordinal() + 1) % wartosci.length;
        if(next == 0){
            next = 1;
        }
        return wartosci[next];
    }

    public boolean isAmmoPresent(ShellType type){
        boolean flag = false;
        for(int j1 = slots_FirstShell; j1 < slots_Last; j1++)
        {
            if (cargoItems[j1] != null && cargoItems[j1].itemId == type.ammoID) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public ShellType getFirstShell(){
        ShellType flag = ShellType.NULL;
        boolean stop = false;
        for(int j1 = slots_FirstShell; j1 < slots_Last; j1++)
        {
            if(cargoItems[j1] != null){
                ShellType[] values = ShellType.values();
                for(int a = 0; a < values.length; a++){
                    if(cargoItems[j1].itemId == values[a].ammoID){
                        flag = values[a];
                        stop = true;
                        break;
                    }
                }
                if(stop){
                    break;
                }
            }
        }
        return flag;
    }

    @Override
    public void checkAmmoPresence(){
        checkIfRemoved(currentShell);
    }

    public void checkIfRemoved(ShellType type){
        if(!isAmmoPresent(type)){
            currentShell = getFirstShell();
            broadcastEventShellChange(currentShell.ordinal());
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(mod_Vehicles.MOD_ID, "Tank");
    }
}
