package net.kozibrodka.vehicles.entity;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.sdk_api.events.ingame.mod_SdkFlasher;
import net.kozibrodka.sdk_api.events.init.ItemCasingListener;
import net.kozibrodka.sdk_api.events.init.KeyBindingListener;
import net.kozibrodka.sdk_api.events.init.ww2Parts;
import net.kozibrodka.sdk_api.events.utils.*;
import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.gui.GuiVehicle;
import net.kozibrodka.vehicles.properties.VehicleType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtFloat;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class EntityVehicle extends Entity implements Inventory, WW2Tank {

    public EntityVehicle(World world)
    {
        super(world);
        lastTurnSpeed = 0.0D;
        lastOnGround = true;
        prevMotionX = 0.0D;
        prevMotionY = 0.0D;
        prevMotionZ = 0.0D;
        lastCollidedEntity = null;
        blocksSameBlockSpawning = true;  //preventEntitySpawning
        deathTime = -13;
        soundLoopTime = 0;
//        standingEyeHeight = 0.625F;
        stepHeight = 1.0F; //stepHeight
        ignoreFrustumCull = true; //ignoreFrustumCheck
        gunYaw = 0.0F;
        gunPitch = 0.0F;
        gunYawShoot = 0.0F;
        gunMachineGun = new ItemStack(mod_Vehicles.itemGunMachineGun);
        renderDistanceMultiplier = 2; //jakos to dostosoawac

    }

    public EntityVehicle(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = d;
        prevY = d1;
        prevZ = d2;
        if(mod_Vehicles.type == null)
        {
            automobile = (VehicleType) VehicleType.types.get(0);
        } else
        {
            automobile = mod_Vehicles.type;
        }
        inventorySize = automobile.numCargoSlots + automobile.numBulletSlots + automobile.numShellSlots + 1;
        cargoItems = new ItemStack[inventorySize];
    }

    public EntityVehicle(World world, double d, double d1, double d2,
                         PlayerEntity entityplayer, int i, VehicleType vehicletype)
    {
        this(world);
        automobile = vehicletype;
        standingEyeHeight = automobile.standingOko;
        setBoundingBoxSpacing(automobile.autoWidth, automobile.autoHeight);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = d;
        prevY = d1;
        prevZ = d2;
        inventorySize = automobile.numCargoSlots + automobile.numBulletSlots + automobile.numShellSlots + 1;
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

    protected void initDataTracker()
    {
    }

    public Box getCollisionAgainstShape(Entity entity)
    {
        return entity.boundingBox;
    }

    public Box getBoundingBox()
    {
        return boundingBox;
    }

    public boolean damage(Entity entity, int i)
    {
        if(automobile.MAX_HEALTH != -1)
        {
            if(entity instanceof LivingEntity){
                if(entity instanceof Monster){
                    health -= (int)i/5;
                    System.out.println("TANK DAMAGED from: " + entity + " DMG: " + (int)i/5);
                    onHurt();
                }
            }else{
                health -= i;
                System.out.println("TANK DAMAGED from: " + entity + " DMG: " + i);
                onHurt();
            }

            if(health <= 0)
            {
                onDeath();
            }
        }
        return true;
    }

    public void onHurt()
    {
        world.playSound(this, "vehicles:mechhurt", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    public void onDeath()
    {
        if(deathTime == -13)
        {
            deathTime = automobile.DEATH_TIME_MAX;
        }
    }

    public boolean isCollidable() //canBeCollidedWith
    {
        return !dead;
    }

    public float getShadowRadius()
    {
        return 0.0F;
    }

    public boolean interact(PlayerEntity entityplayer)
    {
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == mod_Vehicles.vehicleBlowTorch.id)
        {
            if(health > 0 && health < automobile.MAX_HEALTH)
            {
                world.playSound(this, "vehicles:blowtorch", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
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
        if(entityplayer.getHand() != null && entityplayer.getHand().itemId == ItemCasingListener.itemWrenchGold.id)
        {
            System.out.println("TYPE: " + automobile.name);
            System.out.println("ENGINE: " + engineType);
            System.out.println("HEALTH: " + health);
            entityplayer.swingHand();
            return true;
        }
        if(passenger != null && (passenger instanceof PlayerEntity) && passenger != entityplayer)
        {
            return true;
        }
        if(!world.isRemote && passenger == null)
        {
            entityplayer.setVehicle(this);
            SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
        }
        return true;
    }

    public void tick()
    {
        super.tick();
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
                    if(vehicleFuel > 0 && minecraft.currentScreen == null && Keyboard.isKeyDown(minecraft.options.leftKey.code))
                    {
                        d4 = -getTurnSpeed() * (double)(flag1 ? 1 : -1);
                        wheelsYaw = (float)((double)wheelsYaw - 0.5D * getTurnSpeed());
                    } else
                    if(vehicleFuel > 0 && minecraft.currentScreen == null && Keyboard.isKeyDown(minecraft.options.rightKey.code))
                    {
                        d4 = getTurnSpeed() * (double)(flag1 ? 1 : -1);
                        wheelsYaw = (float)((double)wheelsYaw + 0.5D * getTurnSpeed());
                    }
                    if(d4 != 0.0D)
                    {
                        yaw += d4;
                        projectMotion(d4);
                    }
                    lastTurnSpeed = d4 * (double)(flag1 ? 1 : -1);
                }
                double d5 = 0.0D;
                if(passenger != null && minecraft.currentScreen == null)
                {
                    if(vehicleFuel > 0 && Keyboard.isKeyDown(minecraft.options.forwardKey.code))
                    {
                        d5 = -(flag1 ? getAccelForward() : automobile.ACCEL_BRAKE);
                        vehicleFuel--;
                        flag = true;
                    }else
                    if(vehicleFuel > 0 && Keyboard.isKeyDown(minecraft.options.backKey.code))
                    {
                        d5 = flag1 ? automobile.ACCEL_BRAKE : getAccelBackward();
                        flag = true;
                    }
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
        move(velocityX, velocityY, velocityZ);
        int i = flag1 ? 1 : -1;
        if(onGround && lastOnGround)
        {
            if(prevY - y > 0.01D)
            {
                pitch = 45 * i;
            } else
            if(prevY - y < -0.01D)
            {
                pitch = -45 * i;
            } else
            {
                pitch = 0.0F;
            }
            velocityY -= 0.001D;
        } else
        {
            setRotationPitch(Math.max(Math.min((float)((-90D * velocityY) / getSpeed()) * (float)i, 90F), -90F) / 2.0F);
            velocityY = y - prevY - automobile.FALL_SPEED;
        }
        lastOnGround = onGround;
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
        if(passenger != null && getPrevSpeed() - getSpeed() > automobile.COLLISION_SPEED_MIN)
        {
            if(lastCollidedEntity != null && !(lastCollidedEntity instanceof ItemEntity))
            {
                if(automobile.COLLISION_FLIGHT_ENTITY)
                {
                    if(lastCollidedEntity instanceof WW2Tank){
                        lastCollidedEntity.addVelocity(prevMotionX/64, prevMotionY/64 + 0.1D, prevMotionZ/64);
                    }else {
                        lastCollidedEntity.addVelocity(prevMotionX, prevMotionY + 1.0D, prevMotionZ);
                    }
                }
                if(automobile.COLLISION_DAMAGE)
                {
                    lastCollidedEntity.damage(this, automobile.COLLISION_DAMAGE_ENTITY);
                }
            }
            if(automobile.COLLISION_DAMAGE)
            {
                damage(lastCollidedEntity, automobile.COLLISION_DAMAGE_SELF);
            }
            if(automobile.COLLISION_FLIGHT_PLAYER)
            {
                passenger.addVelocity(prevMotionX, prevMotionY + 1.0D, prevMotionZ);
                passenger.setVehicle(null);
            }
        }
        lastCollidedEntity = null;
        prevMotionX = velocityX;
        prevMotionY = velocityY;
        prevMotionZ = velocityZ;
        if(passenger != null && passenger.dead)
        {
            passenger = null;
        }
        if(random.nextInt(automobile.MAX_HEALTH) > health * 2)
        {
            if(health < automobile.MAX_HEALTH/8)
                spawnParticles("flame", 2, false);
            if(health < automobile.MAX_HEALTH/4)
                spawnParticles("largesmoke", 2, false);
            if(health < automobile.MAX_HEALTH)
                spawnParticles("smoke", 4, false);
        }
        if(health > 0 && deathTime != -13)
        {
            deathTime = -13;
        }
        if(deathTime >= 0)
        {
            if(deathTime == 0)
            {
                Explosion explosion = new Explosion(world, null, x, (float)y, (float)z, 3F);
                explosion.explode();
                world.playSound(x, y, z, "random.explode", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
                spawnParticles("explode", 64, true);
                spawnParticles("smoke", 64, true);
                dropParts();
                markDead();
            } else
            if(random.nextInt(automobile.DEATH_TIME_MAX) > deathTime)
            {
//                spawnParticles("flame", 8, false);
                spawnParticles("lava", 8, false);
            }
            deathTime--;
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
        if(passenger != null && automobile.hasTurret)
        {
            if(automobile.tankDestroyer)
            {
                gunYaw = -(180);
                gunYawShoot = -gunYaw;
            }else{
                gunYaw = -((passenger.yaw + 90F) - yaw);
                gunYawShoot = -gunYaw;
            }
            gunPitch = passenger.pitch - pitch;
            if(gunPitch > automobile.gunPitchMax)
            {
                gunPitch = automobile.gunPitchMax;
            }
            if(gunPitch < automobile.gunPitchMin)
            {
                gunPitch = automobile.gunPitchMin;
            }
        }
        this.wheelsYaw *= 0.8F;
        if(this.wheelsYaw > 10.0F) {
            this.wheelsYaw = 10.0F;
        }

        if(this.wheelsYaw < -10.0F) {
            this.wheelsYaw = -10.0F;
        }
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
        if((automobile.MAX_HEALTH-health) > (4.5 * automobile.MAX_HEALTH) / 5 && random.nextInt(30) == 0)   //samoniszczenie
        {
            damage(this, 1);
        }
        if(towingEntity != null)
        {
            updateTowedPosition();
        }
            if(velocityY > 0) {
                velocityY = -0.001;
            }
            wheelsAngle += (float)getSpeed() / 2F;
            if(wheelsAngle > 3600)
                wheelsAngle = 0;
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
        if(entity instanceof WW2Tank || entity instanceof WW2Plane)
        {
            entity.onCollision(this);  //apply entity collision
        }
        if(entity.passenger != this && entity.vehicle != this)
        {
            lastCollidedEntity = entity;
        }
    }

    public void setRotationPitch(float f)
    {
        if((double)(f - pitch) > automobile.ROTATION_PITCH_DELTA_MAX)
        {
            pitch += automobile.ROTATION_PITCH_DELTA_MAX;
        } else
        if((double)(pitch - f) > automobile.ROTATION_PITCH_DELTA_MAX)
        {
            pitch -= automobile.ROTATION_PITCH_DELTA_MAX;
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
            double d1 = ((y + random.nextDouble() * (double)height) - (double)height * 0.5D) + 0.25D;
            double d2 = (z + random.nextDouble() * (double)width) - (double)width * 0.5D;
            double d3 = flag ? random.nextDouble() - 0.5D : 0.0D;
            double d4 = flag ? random.nextDouble() - 0.5D : 0.0D;
            double d5 = flag ? random.nextDouble() - 0.5D : 0.0D;
            if(Math.random() < 0.75D)
            {
                world.addParticle(s, d, d1, d2, d3, d4, d5);
            } else
            {
                world.addParticle(s, d, d1, d2, d3, d4, d5);
            }
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

    public boolean isPushable() //canBePushed
    {
        return true;
    }

    public double getPassengerRidingHeight()
    {
        return automobile.playerYOffset;
    }

    public float getEyeHeight()
    {
        return 0.7F;
    }

    public int size()
    {
        return inventorySize;
    }

    public ItemStack getStack(int i)
    {
        return cargoItems[i];
    }

    public ItemStack removeStack(int i, int j)
    {
        if(cargoItems[i] != null)
        {
            if(cargoItems[i].count <= j)
            {
                ItemStack itemstack = cargoItems[i];
                cargoItems[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = cargoItems[i].split(j);
            if(cargoItems[i].count == 0)
            {
                cargoItems[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }
    }

    public void setStack(int i, ItemStack itemstack)
    {
        cargoItems[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxCountPerStack())
        {
            itemstack.count = getMaxCountPerStack();
        }
        if(itemstack != null && itemstack.itemId == 263 && i == 0 && passenger != null && (passenger instanceof PlayerEntity))
        {
//            ((PlayerBase)passenger).increaseStat(mod_Planes.startPlane, 1); //TODO: achievement
        }
    }

    public String getName()
    {
        return automobile.name;
    }

    public int getMaxCountPerStack()
    {
        return 64;
    }

    public void markDirty()
    {
    }

    public boolean canPlayerUse(PlayerEntity entityplayer)
    {
        return entityplayer.getSquaredDistance(x, y, z) <= 64D;
    }

    public void read(NbtCompound nbttagcompound)
    {
        NbtList nbttaglist = nbttagcompound.getList("Pos");
        NbtList nbttaglist1 = nbttagcompound.getList("Motion");
        NbtList nbttaglist2 = nbttagcompound.getList("Rotation");
        setPosition(0.0D, 0.0D, 0.0D);
        velocityX = ((NbtDouble)nbttaglist1.get(0)).value;
        velocityY = ((NbtDouble)nbttaglist1.get(1)).value;
        velocityZ = ((NbtDouble)nbttaglist1.get(2)).value;
        if(Math.abs(velocityX) > 10D)
        {
            velocityX = 0.0D;
        }
        if(Math.abs(velocityY) > 10D)
        {
            velocityY = 0.0D;
        }
        if(Math.abs(velocityZ) > 10D)
        {
            velocityZ = 0.0D;
        }
        prevX = lastTickX = x = ((NbtDouble)nbttaglist.get(0)).value;
        prevY = lastTickY = y = ((NbtDouble)nbttaglist.get(1)).value;
        prevZ = lastTickZ = z = ((NbtDouble)nbttaglist.get(2)).value;
        prevYaw = yaw = ((NbtFloat)nbttaglist2.get(0)).value;
        prevPitch = pitch = ((NbtFloat)nbttaglist2.get(1)).value;
        fallDistance = nbttagcompound.getFloat("FallDistance");
        fireTicks = nbttagcompound.getShort("Fire");
        air = nbttagcompound.getShort("Air");
        onGround = nbttagcompound.getBoolean("OnGround");
        readNbt(nbttagcompound);
    }

    public void writeNbt(NbtCompound nbttagcompound)
    {
        NbtList nbttaglist = new NbtList();
        for(int i = 0; i < cargoItems.length; i++)
        {
            if(cargoItems[i] != null)
            {
                NbtCompound nbttagcompound1 = new NbtCompound();
                nbttagcompound1.putByte("Slot", (byte)i);
                cargoItems[i].writeNbt(nbttagcompound1);
                nbttaglist.add(nbttagcompound1);
            }
        }

        nbttagcompound.putInt("Health", health);
        nbttagcompound.putInt("DeathTime", deathTime);
        nbttagcompound.put("Items", nbttaglist);
        nbttagcompound.putFloat("GunYaw", gunYaw);
        nbttagcompound.putFloat("GunPitch", gunPitch);
        nbttagcompound.putInt("Engine", engineType);
        nbttagcompound.putInt("Fuel", vehicleFuel);
        nbttagcompound.putString("Type", automobile.name);
        nbttagcompound.putBoolean("HE", shootExplosive);
    }

    public void readNbt(NbtCompound nbttagcompound)
    {
        automobile = mod_Vehicles.getVehicleType(nbttagcompound.getString("Type"));
        standingEyeHeight = automobile.standingOko;
        setBoundingBoxSpacing(automobile.autoWidth, automobile.autoHeight);
        setPosition(x, y, z);
        inventorySize = automobile.numCargoSlots + automobile.numBulletSlots + automobile.numShellSlots + 1;
        NbtList nbttaglist = nbttagcompound.getList("Items");
        cargoItems = new ItemStack[size()];
        for(int i = 0; i < nbttaglist.size(); i++)
        {
            NbtCompound nbttagcompound1 = (NbtCompound)nbttaglist.get(i);
            int k = nbttagcompound1.getByte("Slot") & 0xff;
            if(k >= 0 && k < cargoItems.length)
            {
                cargoItems[k] = new ItemStack(nbttagcompound1);
            }
        }

        health = nbttagcompound.getInt("Health");
        deathTime = nbttagcompound.getInt("DeathTime");
        gunYaw = nbttagcompound.getFloat("GunYaw");
        gunPitch = nbttagcompound.getFloat("GunPitch");
        vehicleFuel = nbttagcompound.getInt("Fuel");
        engineType = nbttagcompound.getInt("Engine");
        shootExplosive = nbttagcompound.getBoolean("HE");
        if(engineType < 1)
        {
            engineType = 1;
        }
        if(engineType > 4)
        {
            engineType = 4;
        }
    }

    public void updateTowedPosition()
    {
        if(towingEntity == null)
        {
            return;
        }
        if(getSquaredDistance(towingEntity) > 36D)
        {
            towEntity(towingEntity);
            return;
        } else
        {
            double d = -1 * 2;  //-blockCheck (Integer) * 2
            double d1 = 0.0D;
            double d2 = 0.0D;
            double d3 = -Math.cos(((double)(-yaw) / 180D) * 3.1415926535897931D);
            double d4 = -Math.sin(((double)(-yaw) / 180D) * 3.1415926535897931D);
            double d5 = Math.cos(((double)pitch / 180D) * 3.1415926535897931D);
            double d6 = Math.sin(((double)pitch / 180D) * 3.1415926535897931D);
            double d7 = Math.cos(((double)(yaw) * 3.1415926535897931D) / 180D) * 0.40000000000000002D * d5;
            double d8 = Math.sin(((double)(yaw) * 3.1415926535897931D) / 180D) * 0.40000000000000002D * d5;
            double d9 = (d * d5 - d1 * d6) * d3 + d2 * d4;
            double d10 = d * d6 + d1 * d5;
            double d11 = (d1 * d6 - d * d5) * d4 + d2 * d3;
            double d12 = (x + d7 + d9) - towingEntity.x;
            double d13 = (y + d10) - towingEntity.y;
            double d14 = (z + d8 + d11) - towingEntity.z;
            towingEntity.move(d12, d13, d14);
            return;
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

//    public boolean shouldRenderAtDistance(double d) {
//        return true;
//    }

    public boolean isFuelled()
    {
        return vehicleFuel > 0;
    }

    public int getBurnTimeRemainingScaled(int i)
    {
        return (vehicleFuel * i) / automobile.vehicleFuelAdd;
    }

    private double lastTurnSpeed;
    public boolean lastOnGround;
    public boolean doorOpen;
    public int health;
    public double prevMotionX;
    public double prevMotionY;
    public double prevMotionZ;
    public Entity lastCollidedEntity;
    public Entity towingEntity;
    public int flakGunOff;
    Minecraft minecraft = Minecraft.class.cast(FabricLoader.getInstance().getGameInstance());

    public int deathTime;
    public int soundLoopTime;
    public float gunYaw;
    public float gunYawShoot;
    public float gunPitch;
    public float wheelsYaw;
    public float prevRotationRoll;
    public EntityPassengerSeat seats[];
    public RotatedAxes axes;
    public int engineType;
    public float wheelsAngle;
    public ItemStack cargoItems[];
    public int inventorySize;
    private int vehicleFuel;
    private int shellDelay;
    private int shootDelay;
    public ItemStack gunMachineGun;
    public ItemStack gunSpecial;
    public VehicleType automobile;
    public boolean shootExplosive;
    public boolean reloadKeyDown;
    public boolean rocketKeyDown;

    @Override
    public void firePrimaryKey(PlayerEntity entityplayer) {
        if(automobile.antiAircraft){
            if(world.isRemote || shellDelay > 0 || !automobile.hasTurret)
            {
                return;
            }
            int k2 = 0;
            for(int j1 = automobile.numCargoSlots + automobile.numBulletSlots + 1; j1 < automobile.numCargoSlots + automobile.numBulletSlots + automobile.numShellSlots + 1; j1++)
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
                double d8 = Math.cos(((double)(-(yaw + gunYawShoot)) / 180D) * 3.1415926535897931D);
                double d10 = Math.sin(((double)(-(yaw + gunYawShoot)) / 180D) * 3.1415926535897931D);
                double d12 = Math.cos(((double)(-(pitch + gunPitch)) / 180D) * 3.1415926535897931D);
                double d14 = Math.sin(((double)(-(pitch + gunPitch)) / 180D) * 3.1415926535897931D);
                double d16 = (d2 * d12 - d4 * d14) * d8 + d6 * d10;
                double d18 = d2 * d14 + d4 * d12;
                double d20 = (d4 * d14 - d2 * d12) * d10 + d6 * d8;

                world.spawnEntity(new EntityAAShell(world, x + d16, y + d18 + (automobile.shellYOffset / 16D), z + d20, d16 / 3D, d18 / 3D, d20 / 3D, automobile.gunDamage, automobile.gunVelocity, automobile.gunSpread, automobile.gunFlakRange));
                world.playSound(this, automobile.shootSound, 1.0F, 1.0F);

                mod_SdkFlasher.LightEntity(world, this, 15, 2);
                removeStack(k2, 1);
                shellDelay = automobile.vehicleShellDelay;
                automobile.shellZOffset = -automobile.shellZOffset;
            }
        }else{
            if(world.isRemote || shellDelay > 0 || !automobile.hasTurret)
            {
                return;
            }
            int k = 0;
            int shellID;
            if(shootExplosive){
                shellID = mod_Vehicles.tankShellHE.id;
            }else{
                shellID = mod_Vehicles.tankShell.id;
            }
            for(int j1 = automobile.numCargoSlots + automobile.numBulletSlots + 1; j1 < automobile.numCargoSlots + automobile.numBulletSlots + automobile.numShellSlots + 1; j1++)
            {
                if(cargoItems[j1] != null && cargoItems[j1].itemId == shellID)
                {
                    k = j1;
                }
            }

            if(k != 0)
            {
                double d2 = (double)automobile.shellXOffset / 16D;
                double d4 = 0.0D;
                double d6 = (double)automobile.shellZOffset / 16D;
                double d8 = Math.cos(((double)(-(yaw + gunYawShoot)) / 180D) * 3.1415926535897931D);
                double d10 = Math.sin(((double)(-(yaw + gunYawShoot)) / 180D) * 3.1415926535897931D);
                double d12 = Math.cos(((double)(-(pitch + gunPitch)) / 180D) * 3.1415926535897931D);
                double d14 = Math.sin(((double)(-(pitch + gunPitch)) / 180D) * 3.1415926535897931D);
                double d16 = (d2 * d12 - d4 * d14) * d8 + d6 * d10;
                double d18 = d2 * d14 + d4 * d12;
                double d20 = (d4 * d14 - d2 * d12) * d10 + d6 * d8;

                world.spawnEntity(new EntityShell(world, x + d16, y + d18 + (automobile.shellYOffset / 16D), z + d20, d16 / 3D, d18 / 3D, d20 / 3D, shootExplosive, automobile.gunDamage, automobile.gunVelocity, automobile.gunSpread));
//                ((SdkItemGun)gunMachineGun.getType()).onItemRightClickEntity(gunMachineGun, level, passenger, (float)(automobile.shellXOffset / 16D), (float)(automobile.shellYOffset / 16D), (float)(automobile.shellZOffset / 16D), 90F, 0.0F); //machine gun
                world.playSound(this, automobile.shootSound, 1.0F, 1.0F);

                mod_SdkFlasher.LightEntity(world, this, 15, 2);
                removeStack(k, 1);
                shellDelay = automobile.vehicleShellDelay;
            }
        }
    }

    @Override
    public void fireSecondaryKey(PlayerEntity entityplayer) {
        if(world.isRemote || shootDelay > 0 || !automobile.hasGuns)
        {
            return;
        }
        int j = 0;
        for(int i1 = automobile.numCargoSlots + 1; i1 < automobile.numCargoSlots + automobile.numBulletSlots + 1; i1++)
        {
            if(cargoItems[i1] != null && cargoItems[i1].itemId == mod_Vehicles.tankBullet.id)
            {
                j = i1;
            }
        }

        if(j != 0)
        {
            ((SdkItemGun)automobile.gunMachineGun.getItem()).onItemRightClickEntity(gunMachineGun, world, this, (float)(automobile.barrelX / 16D), (float)(automobile.barrelY / 16D), (float)(automobile.barrelZ / 16D), 90F, 0.0F); //machine gun
            removeStack(j, 1);
            shootDelay = automobile.vehicleShootDelay;
        }
        return;
    }

    @Override
    public void inventoryKey(Minecraft minecraft, PlayerEntity entityplayer) {
        if (minecraft.currentScreen instanceof GuiVehicle) {
            minecraft.setScreen(null);
            return;
        } else if (passenger.vehicle instanceof EntityVehicle) {
            minecraft.setScreen(new GuiVehicle(((PlayerEntity)passenger).inventory, (EntityVehicle) passenger.vehicle));
            return;
        }
    }

    @Override
    public void exitKey(PlayerEntity entityplayer) {
        passenger.setVehicle(this);
    }

    @Override
    public void towKey(PlayerEntity entityplayer) {
        if(automobile.specialWeapon == "haul")
        {
            if(towingEntity == null)
            {
                List list = world.getEntities(this, boundingBox.expand(0.10000000000000001D, 0.0D, 0.10000000000000001D));
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
    }

    @Override
    public void reloadKey(PlayerEntity entityplayer) {
        shootExplosive = !shootExplosive;
        world.playSound(this, "vehicles:tankreload", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public int getPercentHealth() {
        return 0;
    }
}
