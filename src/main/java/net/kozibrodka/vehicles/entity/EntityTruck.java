package net.kozibrodka.vehicles.entity;

import net.fabricmc.loader.api.FabricLoader;
import net.kozibrodka.sdk_api.events.ingame.mod_SdkFlasher;
import net.kozibrodka.sdk_api.events.init.ItemCasingListener;
import net.kozibrodka.sdk_api.events.init.ww2Parts;
import net.kozibrodka.sdk_api.events.utils.*;
import net.kozibrodka.vehicles.events.mod_Vehicles;
import net.kozibrodka.vehicles.gui.GuiTruck;
import net.kozibrodka.vehicles.gui.GuiVehicle;
import net.kozibrodka.vehicles.properties.TruckType;
import net.kozibrodka.vehicles.properties.VehicleType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.sortme.Explosion;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.DoubleTag;
import net.minecraft.util.io.FloatTag;
import net.minecraft.util.io.ListTag;
import net.minecraft.util.maths.Box;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class EntityTruck extends EntityBase implements InventoryBase, WW2Truck {

    public EntityTruck(Level world)
    {
        super(world);
        lastTurnSpeed = 0.0D;
        lastOnGround = true;
        prevMotionX = 0.0D;
        prevMotionY = 0.0D;
        prevMotionZ = 0.0D;
        lastCollidedEntity = null;
        field_1593 = true;  //preventEntitySpawning
        deathTime = -13;
        soundLoopTime = 0;
//        standingEyeHeight = 0.625F;
        field_1641 = 1.0F; //stepHeight
        field_1622 = true; //ignoreFrustumCheck
        renderDistanceMultiplier = 2; //jakos to dostosoawac

    }

    public EntityTruck(Level world, double d, double d1, double d2)
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
            automobile = (TruckType) TruckType.types.get(0);
        } else
        {
            automobile = mod_Vehicles.type_truck;
        }
        inventorySize = automobile.numCargoSlots + automobile.numBulletSlots + automobile.numShellSlots + 1;
        cargoItems = new ItemInstance[inventorySize];
    }

    public EntityTruck(Level world, double d, double d1, double d2,
                       PlayerBase entityplayer, int i, TruckType vehicletype)
    {
        this(world);
        automobile = vehicletype;
        standingEyeHeight = automobile.standingOko;
        setSize(automobile.autoWidth, automobile.autoHeight);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = d;
        prevY = d1;
        prevZ = d2;
        inventorySize = automobile.numCargoSlots + automobile.numBulletSlots + automobile.numShellSlots + 1;
        cargoItems = new ItemInstance[inventorySize];
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

    public Box getBoundingBox(EntityBase entity)
    {
        return entity.boundingBox;
    }

    public Box method_1381()
    {
        return boundingBox;
    }

    public boolean damage(EntityBase entity, int i)
    {
        if(automobile.MAX_HEALTH != -1)
        {
            onHurt();
            System.out.println("Czołg dostał od: " + entity + " dmg: " + i);
            health -= i;
            if(health <= 0)
            {
                onDeath();
            }
        }
        return true;
    }

    public void onHurt()
    {
        level.playSound(this, "vehicles:mechhurt", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
    }

    public void onDeath()
    {
        if(deathTime == -13)
        {
            deathTime = automobile.DEATH_TIME_MAX;
        }
    }

    public boolean method_1356() //canBeCollidedWith
    {
        return !removed;
    }

    public float getEyeHeight()
    {
        return 0.0F;
    }

    public boolean interact(PlayerBase entityplayer)
    {
        if(entityplayer.getHeldItem() != null && entityplayer.getHeldItem().itemId == mod_Vehicles.vehicleBlowTorch.id)
        {
            if(health > 0 && health < automobile.MAX_HEALTH)
            {
                level.playSound(this, "ofensywa:wrench", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                health = Math.min(health + 4, automobile.MAX_HEALTH);
                entityplayer.swingHand();
                entityplayer.getHeldItem().applyDamage(1, entityplayer);
                if(entityplayer.getHeldItem().getDamage() <= 0)
                {
                    entityplayer.inventory.main[entityplayer.inventory.selectedHotbarSlot] = null;
                }
            }
            return true;
        }
        if(entityplayer.getHeldItem() != null && entityplayer.getHeldItem().itemId == ItemCasingListener.itemWrenchGold.id)
        {
            System.out.println("TYPE: " + automobile.name);
            System.out.println("ENGINE: " + engineType);
            System.out.println("HEALTH: " + health);
            return true;
        }
        if(passenger != null && (passenger instanceof PlayerBase) && passenger != entityplayer)
        {
            return true;
        }
        if(!level.isServerSide && passenger == null)
        {
            entityplayer.startRiding(this);
            SdkItemCustomUseDelay.doNotUseThisTick = level.getLevelTime();
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
                    if(vehicleFuel > 0 && minecraft.currentScreen == null && Keyboard.isKeyDown(minecraft.options.leftKey.key))
                    {
                        d4 = -getTurnSpeed() * (double)(flag1 ? 1 : -1);
                        wheelsYaw = (float)((double)wheelsYaw - 0.5D * getTurnSpeed());
                    } else
                    if(vehicleFuel > 0 && minecraft.currentScreen == null && Keyboard.isKeyDown(minecraft.options.rightKey.key))
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
                    if(vehicleFuel > 0 && Keyboard.isKeyDown(minecraft.options.forwardKey.key))
                    {
                        d5 = -(flag1 ? getAccelForward() : automobile.ACCEL_BRAKE);
                        vehicleFuel--;
                        flag = true;
                    }else
                    if(vehicleFuel > 0 && Keyboard.isKeyDown(minecraft.options.backKey.key))
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
        if(method_1393()) //handle water mv
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
        List list = level.getEntities(this, boundingBox.expand(0.20000000000000001D, 0.0D, 0.20000000000000001D));
        if(list != null && list.size() > 0)
        {
            for(int j = 0; j < list.size(); j++)
            {
                EntityBase entity = (EntityBase)list.get(j);
                if(entity != passenger && entity.method_1380()) //canbepushed
                {
                    handleCollision(entity);
                }
            }

        }
        if(passenger != null && getPrevSpeed() - getSpeed() > automobile.COLLISION_SPEED_MIN)
        {
            if(lastCollidedEntity != null)
            {
                if(automobile.COLLISION_FLIGHT_ENTITY)
                {
                    System.out.println("CZOLG SKOCZYL");
                    lastCollidedEntity.accelerate(prevMotionX, prevMotionY + 1.0D, prevMotionZ);
                }
                if(automobile.COLLISION_DAMAGE)
                {
                    lastCollidedEntity.damage(passenger, automobile.COLLISION_DAMAGE_ENTITY);
                }
            }
            if(automobile.COLLISION_DAMAGE)
            {
                damage(lastCollidedEntity, automobile.COLLISION_DAMAGE_SELF);
            }
            if(automobile.COLLISION_FLIGHT_PLAYER)
            {
                passenger.accelerate(prevMotionX, prevMotionY + 1.0D, prevMotionZ);
                passenger.startRiding(null);
            }
        }
        lastCollidedEntity = null;
        prevMotionX = velocityX;
        prevMotionY = velocityY;
        prevMotionZ = velocityZ;
        if(passenger != null && passenger.removed)
        {
            passenger = null;
        }
        if(rand.nextInt(automobile.MAX_HEALTH) > health * 2)
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
                Explosion explosion = new Explosion(level, null, x, (float)y, (float)z, 3F);
                explosion.kaboomPhase1();
                level.playSound(x, y, z, "random.explode", 4F, (1.0F + (level.rand.nextFloat() - level.rand.nextFloat()) * 0.2F) * 0.7F);
                spawnParticles("explode", 64, true);
                spawnParticles("smoke", 64, true);
                dropParts();
                remove();
            } else
            if(rand.nextInt(automobile.DEATH_TIME_MAX) > deathTime)
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
                level.playSound(this, automobile.SOUND_RIDING, 1.0F, 1.0F);
                soundLoopTime = automobile.SOUND_LOOP_TIME_MAX;
            }
            soundLoopTime--;
        } else
        {
            soundLoopTime = 0;
        }
        this.wheelsYaw *= 0.8F;
        if(this.wheelsYaw > 10.0F) {
            this.wheelsYaw = 10.0F;
        }

        if(this.wheelsYaw < -10.0F) {
            this.wheelsYaw = -10.0F;
        }
        if(vehicleFuel <= 0 && passenger != null && !level.isServerSide)
        {
            if(cargoItems[0] != null && cargoItems[0].itemId == mod_Vehicles.vehicleFuel.id)
            {
                vehicleFuel = automobile.vehicleFuelAdd;
                takeInventoryItem(0, 1);
            }
        }
        if(vehicleFuel > 0 && passenger != null)
        {
            vehicleFuel--;
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
        if(rand.nextInt(r8) == 0)
            dropItem(automobile.item_body.itemId, 1, f8);
        if(rand.nextInt(r8) == 0)
            dropItem(automobile.item_wheel.itemId, 1, f8);
        if(rand.nextInt(r8) == 0)
            dropItem(automobile.item_wheel.itemId, 1, f8);
        if(rand.nextInt(r8) == 0)
            dropItem(automobile.item_wheel.itemId, 1, f8);
        if(rand.nextInt(r8) == 0)
            dropItem(automobile.item_wheel.itemId, 1, f8);
        dropItem(automobile.dyeColor.itemId, rand.nextInt(2) + 1, f8);

        switch(engineType)
        {
            case 1: // '\001'
                if(rand.nextInt(r8) == 0)
                    dropItem(ww2Parts.smallEngine.id, 1, f8);
                break;

            case 2: // '\002'
                if(rand.nextInt(r8) == 0)
                    dropItem(ww2Parts.mediumEngine.id, 1, f8);
                break;

            case 3: // '\003'
                if(rand.nextInt(r8) == 0)
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

    public void handleCollision(EntityBase entity)
    {
        entity.method_1353(this);  //apply entity collision
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
            double d = (x + rand.nextDouble() * (double)width * 1.5D) - (double)width * 0.75D;
            double d1 = ((y + rand.nextDouble() * (double)height) - (double)height * 0.5D) + 0.25D;
            double d2 = (z + rand.nextDouble() * (double)width) - (double)width * 0.5D;
            double d3 = flag ? rand.nextDouble() - 0.5D : 0.0D;
            double d4 = flag ? rand.nextDouble() - 0.5D : 0.0D;
            double d5 = flag ? rand.nextDouble() - 0.5D : 0.0D;
            if(Math.random() < 0.75D)
            {
                level.addParticle(s, d, d1, d2, d3, d4, d5);
            } else
            {
                level.addParticle(s, d, d1, d2, d3, d4, d5);
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

    public boolean method_1380() //canBePushe
    {
        return true;
    }

    public double getMountedHeightOffset()
    {
        return automobile.playerYOffset;
    }

    public float getStandingEyeHeight()
    {
        return 0.7F;
    }

    public int getInventorySize()
    {
        return inventorySize;
    }

    public ItemInstance getInventoryItem(int i)
    {
        return cargoItems[i];
    }

    public ItemInstance takeInventoryItem(int i, int j)
    {
        if(cargoItems[i] != null)
        {
            if(cargoItems[i].count <= j)
            {
                ItemInstance itemstack = cargoItems[i];
                cargoItems[i] = null;
                return itemstack;
            }
            ItemInstance itemstack1 = cargoItems[i].split(j);
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

    public void setInventoryItem(int i, ItemInstance itemstack)
    {
        cargoItems[i] = itemstack;
        if(itemstack != null && itemstack.count > getMaxItemCount())
        {
            itemstack.count = getMaxItemCount();
        }
        if(itemstack != null && itemstack.itemId == 263 && i == 0 && passenger != null && (passenger instanceof PlayerBase))
        {
//            ((PlayerBase)passenger).increaseStat(mod_Planes.startPlane, 1); //TODO: achievement
        }
    }

    public String getContainerName()
    {
        return automobile.name;
    }

    public int getMaxItemCount()
    {
        return 64;
    }

    public void markDirty()
    {
    }

    public boolean canPlayerUse(PlayerBase entityplayer)
    {
        return entityplayer.squaredDistanceTo(x, y, z) <= 64D;
    }

    public void fromTag(CompoundTag nbttagcompound)
    {
        ListTag nbttaglist = nbttagcompound.getListTag("Pos");
        ListTag nbttaglist1 = nbttagcompound.getListTag("Motion");
        ListTag nbttaglist2 = nbttagcompound.getListTag("Rotation");
        setPosition(0.0D, 0.0D, 0.0D);
        velocityX = ((DoubleTag)nbttaglist1.get(0)).data;
        velocityY = ((DoubleTag)nbttaglist1.get(1)).data;
        velocityZ = ((DoubleTag)nbttaglist1.get(2)).data;
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
        prevX = prevRenderX = x = ((DoubleTag)nbttaglist.get(0)).data;
        prevY = prevRenderY = y = ((DoubleTag)nbttaglist.get(1)).data;
        prevZ = prevRenderZ = z = ((DoubleTag)nbttaglist.get(2)).data;
        prevYaw = yaw = ((FloatTag)nbttaglist2.get(0)).data;
        prevPitch = pitch = ((FloatTag)nbttaglist2.get(1)).data;
        fallDistance = nbttagcompound.getFloat("FallDistance");
        fire = nbttagcompound.getShort("Fire");
        air = nbttagcompound.getShort("Air");
        onGround = nbttagcompound.getBoolean("OnGround");
        readCustomDataFromTag(nbttagcompound);
    }

    public void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
        ListTag nbttaglist = new ListTag();
        for(int i = 0; i < cargoItems.length; i++)
        {
            if(cargoItems[i] != null)
            {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.put("Slot", (byte)i);
                cargoItems[i].toTag(nbttagcompound1);
                nbttaglist.add(nbttagcompound1);
            }
        }

        nbttagcompound.put("Health", health);
        nbttagcompound.put("DeathTime", deathTime);
        nbttagcompound.put("Items", nbttaglist);
        nbttagcompound.put("Engine", engineType);
        nbttagcompound.put("Fuel", vehicleFuel);
        nbttagcompound.put("Type", automobile.name);

    }

    public void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
        automobile = mod_Vehicles.getTruckType(nbttagcompound.getString("Type"));
        standingEyeHeight = automobile.standingOko;
        setSize(automobile.autoWidth, automobile.autoHeight);
        setPosition(x, y, z);
        inventorySize = automobile.numCargoSlots + automobile.numBulletSlots + automobile.numShellSlots + 1;
        ListTag nbttaglist = nbttagcompound.getListTag("Items");
        cargoItems = new ItemInstance[getInventorySize()];
        for(int i = 0; i < nbttaglist.size(); i++)
        {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.get(i);
            int k = nbttagcompound1.getByte("Slot") & 0xff;
            if(k >= 0 && k < cargoItems.length)
            {
                cargoItems[k] = new ItemInstance(nbttagcompound1);
            }
        }

        health = nbttagcompound.getInt("Health");
        deathTime = nbttagcompound.getInt("DeathTime");
        vehicleFuel = nbttagcompound.getInt("Fuel");
        engineType = nbttagcompound.getInt("Engine");
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
        if(method_1352(towingEntity) > 36D)
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

    public boolean towEntity(EntityBase entity)
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
    public int health;
    public double prevMotionX;
    public double prevMotionY;
    public double prevMotionZ;
    public EntityBase lastCollidedEntity;
    public EntityBase towingEntity;
    Minecraft minecraft = Minecraft.class.cast(FabricLoader.getInstance().getGameInstance());

    public int deathTime;
    public int soundLoopTime;
    public float wheelsYaw;
    public float prevRotationRoll;
    public EntityPassengerSeat seats[];
    public RotatedAxes axes;
    public int engineType;
    public float wheelsAngle;
    public ItemInstance cargoItems[];
    public int inventorySize;
    private int vehicleFuel;
    public TruckType automobile;



    @Override
    public void inventoryKey(Minecraft minecraft, PlayerBase entityplayer) {
        if (minecraft.currentScreen instanceof GuiTruck) {
            minecraft.openScreen(null);
            return;
        } else if (passenger.vehicle instanceof EntityTruck) {
            minecraft.openScreen(new GuiTruck(((PlayerBase)passenger).inventory, (EntityTruck) passenger.vehicle));
            return;
        }
    }

    @Override
    public void exitKey(PlayerBase entityplayer) {
        passenger.startRiding(this);
    }

    @Override
    public void towKey(PlayerBase entityplayer) {
            if(towingEntity == null)
            {
                List list = level.getEntities(this, boundingBox.expand(0.10000000000000001D, 0.0D, 0.10000000000000001D));
                if(list != null && list.size() > 0)
                {
                    for(int j2 = 0; j2 < list.size(); j2++)
                    {
                        EntityBase entity = (EntityBase)list.get(j2);

                        if(entity instanceof WW2Cannon && towingEntity == null)
                        {
                            towEntity(entity);
                        }else
                        {
                            entity.method_1353(this);
                        }
                    }
                }
            }else{
                towEntity(towingEntity);
            }

    }

}
