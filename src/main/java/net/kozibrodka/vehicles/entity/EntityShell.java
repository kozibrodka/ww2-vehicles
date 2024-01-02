package net.kozibrodka.vehicles.entity;

import net.kozibrodka.sdk_api.events.utils.*;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.monster.MonsterEntityType;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.sortme.Explosion;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.Vec3f;

import java.util.List;

public class EntityShell extends EntityBase
{

    public EntityShell(Level world)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        arrowShake = 0;
        flyTime = 0;
        setSize(0.5F, 0.5F);
        damageShell = 10;
    }

    public EntityShell(Level world, double d, double d1, double d2,
                       double d3, double d4, double d5, boolean he, float dmg, float vel, float spr)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        arrowShake = 0;
        flyTime = 0;
        setSize(0.5F, 0.5F);
        setPosition(d, d1, d2);
        standingEyeHeight = 0.0F;
        setVelocity(d3, d4, d5);
        explodeBoolean = he;
        damageShell = dmg;
        spreadVal = spr;
        if(explodeBoolean)
        {
            muzzleVel = vel*0.6F;
        }else
        {
            muzzleVel = vel;
        }
        setArrowHeading(velocityX, velocityY, velocityZ, muzzleVel, spreadVal);
    }

    public EntityShell(Level world, double d, double d1, double d2)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        arrowShake = 0;
        flyTime = 0;
        setSize(0.5F, 0.5F);
        setPosition(d, d1, d2);
        standingEyeHeight = 0.0F;
        damageShell = 10;
    }

    public EntityShell(Level world, Living entityliving)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        arrowShake = 0;
        flyTime = 0;
        owner = entityliving;
        setSize(0.5F, 0.5F);
        setPositionAndAngles(entityliving.x, entityliving.y + (double)entityliving.getEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        x -= MathHelper.cos((yaw / 180F) * 3.141593F) * 0.16F;
        y -= 0.10000000149011612D;
        z -= MathHelper.sin((yaw / 180F) * 3.141593F) * 0.16F;
        setPosition(x, y, z);
        standingEyeHeight = 0.0F;
        velocityX = -MathHelper.sin((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        velocityZ = MathHelper.cos((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        velocityY = -MathHelper.sin((pitch / 180F) * 3.141593F);
        setArrowHeading(velocityX, velocityY, velocityZ, 1.5F, 1.0F);
        damageShell = 10;
    }

    protected void initDataTracker()
    {
    }

    public void setArrowHeading(double d, double d1, double d2, float f,
                                float f1)
    {
        float f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        velocityX = d;
        velocityY = d1;
        velocityZ = d2;
        float f3 = MathHelper.sqrt(d * d + d2 * d2);
        prevYaw = yaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevPitch = pitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        timeTillDeath = 0;
    }

    public void setVelocity(double d, double d1, double d2)
    {
        velocityX = d;
        velocityY = d1;
        velocityZ = d2;
        if(prevPitch == 0.0F && prevYaw == 0.0F)
        {
            float f = MathHelper.sqrt(d * d + d2 * d2);
            prevYaw = yaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
            prevPitch = pitch = (float)((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
        }
    }

    public boolean shouldRenderAtDistance(double d) {
        return true;
    }

    public void tick()
    {
        super.tick();
        if(flyTime > 1000)
        {
            remove();
        }
        if(prevPitch == 0.0F && prevYaw == 0.0F)
        {
            float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
            prevYaw = yaw = (float)((Math.atan2(velocityX, velocityZ) * 180D) / 3.1415927410125732D);
            prevPitch = pitch = (float)((Math.atan2(velocityY, f) * 180D) / 3.1415927410125732D);
        }
        if(arrowShake > 0)
        {
            arrowShake--;
        }
        if(inGround)
        {
            explode();
//            level.createExplosion(null, x, y, z, 4F);
//            remove();
        } else
        {
            flyTime++;
        }
        Vec3f vec3d = Vec3f.from(x, y, z);
        Vec3f vec3d1 = Vec3f.from(x + velocityX, y + velocityY, z + velocityZ);
        HitResult movingobjectposition = level.method_160(vec3d, vec3d1);
        vec3d = Vec3f.from(x, y, z);
        vec3d1 = Vec3f.from(x + velocityX, y + velocityY, z + velocityZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3f.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
        }
        EntityBase entity = null;
        List list = level.getEntities(this, boundingBox.method_86(velocityX, velocityY, velocityZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int i = 0; i < list.size(); i++)
        {
            EntityBase entity1 = (EntityBase)list.get(i);
            if(!entity1.method_1356() || entity1 == owner && flyTime < 5)
            {
                continue;
            }
            float f3 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f3, f3, f3);
            HitResult movingobjectposition1 = axisalignedbb.method_89(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3d.method_1294(movingobjectposition1.field_1988);
            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new HitResult(entity);
        }
        if(movingobjectposition != null)
        {
            int k = level.getTileId(movingobjectposition.x, movingobjectposition.y, movingobjectposition.z);
            if(movingobjectposition.field_1989 != null || k != BlockBase.TALLGRASS.id)
            {
                if(movingobjectposition.field_1989 != null)
                {
                    int l = (int) damageShell;
                    if(!explodeBoolean)
                    {
                        l *= 3;
                    }
                    if((owner instanceof MonsterEntityType) && (movingobjectposition.field_1989 instanceof PlayerBase))
                    {
                        if(level.difficulty == 0)
                        {
                            l = 0;
                        }
                        if(level.difficulty == 1)
                        {
                            l = l / 3 + 1;
                        }
                        if(level.difficulty == 3)
                        {
                            l = (l * 3) / 2;
                        }
                    }
                    if(movingobjectposition.field_1989 instanceof Living) //TODO: ?
                    {
                        SdkTools.attackEntityIgnoreDelay((Living)movingobjectposition.field_1989, this, l);
                    } else
                    {
                        if(movingobjectposition.field_1989 instanceof WW2Plane || movingobjectposition.field_1989 instanceof WW2Tank || movingobjectposition.field_1989 instanceof WW2Truck || movingobjectposition.field_1989 instanceof WW2Cannon)
                        {
                            movingobjectposition.field_1989.damage(this, l);
                        }else {
                            movingobjectposition.field_1989.damage(owner, l);
                        }
                    }
                }
                explode();
//                level.createExplosion(null, x, y, z, 4F);
            }
        }
        x += velocityX;   //ADDON
        y += velocityY;
        z += velocityZ;
        float f1 = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
        yaw = (float)((Math.atan2(velocityX, velocityZ) * 180D) / 3.1415927410125732D);
        for(pitch = (float)((Math.atan2(velocityY, f1) * 180D) / 3.1415927410125732D); pitch - prevPitch < -180F; prevPitch -= 360F) { }
        for(; pitch - prevPitch >= 180F; prevPitch += 360F) { }
        for(; yaw - prevYaw < -180F; prevYaw -= 360F) { }
        for(; yaw - prevYaw >= 180F; prevYaw += 360F) { }
        pitch = prevPitch + (pitch - prevPitch) * 0.2F;
        yaw = prevYaw + (yaw - prevYaw) * 0.2F;
        float f2 = 1.002557F;
        float f4 = 0.005F; //opad oryg 0.03
        if(method_1393())
        {
            for(int j = 0; j < 4; j++)
            {
                float f5 = 0.25F;
                level.addParticle("bubble", x - velocityX * (double)f5, y - velocityY * (double)f5, z - velocityZ * (double)f5, velocityX, velocityY, velocityZ);
            }

            f2 = 0.95F;
            f4 = 0.03F;
        }
        velocityX *= f2;
        velocityY *= f2;
        velocityZ *= f2;
        velocityY -= f4;
        setPosition(x, y, z);
    }

    public void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
        nbttagcompound.put("xTile", (short)xTile);
        nbttagcompound.put("yTile", (short)yTile);
        nbttagcompound.put("zTile", (short)zTile);
    }

    public void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
    }

    private void explode()
    {
//        if(explodeBoolean)
//        {
//            Explosion explosionHE = new Explosion(level, null, x, (float)y, (float)z, damage);
//            explosionHE.kaboomPhase1();
//            explosionHE.kaboomPhase2(true);
//
//        }else{
//            Explosion explosionPZ = new Explosion(level, null, x, (float)y, (float)z, damage*1.5F);
//            explosionPZ.kaboomPhase1();
//            level.playSound(x, y, z, "random.explode", 4F, (1.0F + (level.rand.nextFloat() - level.rand.nextFloat()) * 0.2F) * 0.7F);
//            for(int i = 0; i < 32; i++)
//            {
//                level.addParticle("explode", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
//                level.addParticle("smoke", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
//            }
//        }

        Explosion explosion = new Explosion(level, null, x, (float)y, (float)z, damageShell/5);
        explosion.kaboomPhase1();
        if(explodeBoolean)
        {
            explosion.kaboomPhase2(true);
        } else
        {
            level.playSound(x, y, z, "random.explode", 4F, (1.0F + (level.rand.nextFloat() - level.rand.nextFloat()) * 0.2F) * 0.7F);
        }
        for(int i = 0; i < 32; i++)
        {
            level.addParticle("explode", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
            level.addParticle("smoke", x, y, z, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D, level.rand.nextDouble() - 0.5D);
        }

        remove();
    }

//    public void onPlayerCollision(PlayerBase entityplayer)
//    {
//        if(level.isServerSide)
//        {
//            return;
//        }
//        if(inGround && owner == entityplayer && arrowShake <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1)))
//        {
//            level.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
//            entityplayer.onItemPickup(this, 1);
//            setEntityDead();
//        }
//    }

    public float getEyeHeight()
    {
        return 0.0F;
    }

    private boolean explodeBoolean;
    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public int arrowShake;
    public Living owner;
    private int timeTillDeath;
    private int flyTime;
    protected float damageShell;
    private float muzzleVel;
    private float spreadVal;
}
