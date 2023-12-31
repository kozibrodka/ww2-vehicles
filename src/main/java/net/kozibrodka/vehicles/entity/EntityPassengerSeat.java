package net.kozibrodka.vehicles.entity;

import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;

public class EntityPassengerSeat extends EntityBase
{

    public EntityPassengerSeat(Level world)
    {
        super(world);
        field_1593 = true;
        setSize(0.8F, 1.0F);
        standingEyeHeight = height / 2.0F - 0.17F;
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
//        getProperties();
    }

    public void setPosition(double d, double d1, double d2)
    {
        x = d;
        y = d1;
        z = d2;
        float f = width / 2.0F;
        float f1 = height;
        boundingBox.method_99(d - (double)f, (d1 - (double)standingEyeHeight) + (double)field_1640, d2 - (double)f, d + (double)f, (d1 - (double)standingEyeHeight) + (double)field_1640 + (double)f1, d2 + (double)f);
    }

    protected void initDataTracker()
    {
    }

    public Box getBoundingBox(EntityBase entity1)
    {
        return entity1.boundingBox;
    }

    public Box method_1381()
    {
        return boundingBox;
    }

    public boolean method_1380()
    {
        return true;
    }

    public EntityPassengerSeat(Level world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        prevX = d;
        prevY = d1;
        prevZ = d2;
    }

    public EntityPassengerSeat(Level world, double d, double d1, double d2,
                               EntityBase entity1)
    {
        this(world);
        relativeX = d / 16D;
        relativeY = d1 / 16D;
        relativeZ = d2 / 16D;
        entity = entity1;
        setPosition(entity1.x, entity1.y, entity1.z);
    }

    public double getMountedYOffset()
    {
        return playerYOffset;
    }

    public boolean damage(EntityBase entity1, int i)
    {
        return true;
    }

    public void remove()
    {
        method_1336();
        if(passenger != null)
        {
            passenger.startRiding(this);
        }
        super.remove();
    }

    public void method_1312()
    {
    }

    public boolean method_1356()
    {
        return !removed;
    }

    public void method_1311(double d, double d1, double d2, float f,
                                        float f1, int i)
    {
        field_9393_e = d;
        field_9392_f = d1;
        field_9391_g = d2;
        field_9390_h = f;
        field_9389_i = f1;
        field_9394_d = i + 4;
        velocityX = field_9388_j;
        velocityY = field_9387_k;
        velocityZ = field_9386_l;
    }

    public void setVelocity(double d, double d1, double d2)
    {
        field_9388_j = velocityX = d;
        field_9387_k = velocityY = d1;
        field_9386_l = velocityZ = d2;
    }

    public double getSpeed()
    {
        return Math.sqrt(velocityX * velocityX + velocityZ * velocityZ);
    }

    public void pressKey(int i)
    {
        if(i == 8)
        {
            passenger.startRiding(this);
        }
    }

    public void tick()
    {
        super.tick();
//        Minecraft minecraft = ModLoader.getMinecraftInstance();     //pass seat
//        if(ModLoader.isGUIOpen(null) && minecraft.thePlayer.ridingEntity != null && minecraft.thePlayer.ridingEntity == this)
//        {
//            if(Keyboard.isKeyDown(KEY_INV))
//            {
//                pressKey(7);
//            }
//            if(Keyboard.isKeyDown(KEY_GETOUT))
//            {
//                pressKey(8);
//            }
//        }
        if(level.isServerSide)
        {
            if(field_9394_d > 0)
            {
                double d = x + (field_9393_e - x) / (double)field_9394_d;
                double d1 = y + (field_9392_f - y) / (double)field_9394_d;
                double d2 = z + (field_9391_g - z) / (double)field_9394_d;
                double d3;
                for(d3 = field_9390_h - (double)yaw; d3 < -180D; d3 += 360D) { }
                for(; d3 >= 180D; d3 -= 360D) { }
                yaw += d3 / (double)field_9394_d;
                pitch += (field_9389_i - (double)pitch) / (double)field_9394_d;
                field_9394_d--;
                setPosition(d, d1, d2);
                setRotation(yaw, pitch);
            }
            return;
        }
        if(entity == null || entity.removed)
        {
            remove();
        }
    }

    public void updatePlanePosition(double d, double d1, double d2, float f,
                                    float f1)
    {
        velocityX = d - prevX;
        velocityY = d1 - prevY;
        velocityZ = d2 - prevZ;
        prevX = x;
        prevY = y;
        prevZ = z;
        prevYaw = yaw;
        prevPitch = pitch;
        setPosition(d, d1, d2);
        setRotation(f, f1);
        method_1382();
    }

    public void method_1382()  //updateRiderPosition
    {
        if(passenger == null)
        {
            return;
        }
        if(passenger == SdkTools.minecraft.player || (passenger instanceof Wolf))
        {
            double d = relativeX;
            double d1 = getMountedYOffset() + passenger.getHeightOffset() + relativeY;
            double d2 = relativeZ;
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
            return;
        } else
        {
            return;
        }
    }

    protected void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
    }

    protected void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
    }

    public float getEyeHeight()
    {
        return 0.0F;
    }

    public boolean interact(PlayerBase entityplayer)
    {
        if(passenger != null && (passenger instanceof PlayerBase) && passenger != entityplayer)
        {
            return true;
        }
        if(!level.isServerSide && passenger != entityplayer)
        {
            entityplayer.startRiding(this);
        }
        return true;
    }

    public boolean canPlayerUse(PlayerBase entityplayer) //? czemu sie nie swieci
    {
        if(removed)
        {
            return false;
        } else
        {
            return entityplayer.method_1352(this) <= 64D;
        }
    }

    public int boatCurrentDamage;
    public int boatTimeSinceHit;
    public int boatRockDirection;
    private int field_9394_d;
    private double field_9393_e;
    private double field_9392_f;
    private double field_9391_g;
    private double field_9390_h;
    private double field_9389_i;
    private double field_9388_j;
    private double field_9387_k;
    private double field_9386_l;
    private static int KEY_GETOUT;
    private static int KEY_INV;
    public EntityBase entity;
    private int seatNumber;
    private double relativeX;
    private double relativeY;
    private double relativeZ;
    public double playerYOffset;
}
