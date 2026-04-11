package net.kozibrodka.vehicles.entity;

import net.kozibrodka.sdk_api.events.utils.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import java.util.List;

public class EntityShell extends Entity
{

    public EntityShell(World world)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        arrowShake = 0;
        flyTime = 0;
        setBoundingBoxSpacing(0.5F, 0.5F);
        damageShell = 10;
    }

    public EntityShell(World world, double d, double d1, double d2,
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
        setBoundingBoxSpacing(0.5F, 0.5F);
        setPosition(d, d1, d2);
        standingEyeHeight = 0.0F;
        setVelocityClient(d3, d4, d5);
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

    public EntityShell(World world, double d, double d1, double d2)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        inGround = false;
        arrowShake = 0;
        flyTime = 0;
        setBoundingBoxSpacing(0.5F, 0.5F);
        setPosition(d, d1, d2);
        standingEyeHeight = 0.0F;
        damageShell = 10;
    }

    public EntityShell(World world, LivingEntity entityliving)
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
        setBoundingBoxSpacing(0.5F, 0.5F);
        setPositionAndAnglesKeepPrevAngles(entityliving.x, entityliving.y + (double)entityliving.getShadowRadius(), entityliving.z, entityliving.yaw, entityliving.pitch);
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
        d += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
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

    public void setVelocityClient(double d, double d1, double d2)
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

    public boolean shouldRender(double d) {
        return true;
    }

    public void tick()
    {
        super.tick();
        if(flyTime > 1000)
        {
            markDead();
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
        Vec3d vec3d = Vec3d.createCached(x, y, z);
        Vec3d vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        HitResult movingobjectposition = world.raycast(vec3d, vec3d1);
        vec3d = Vec3d.createCached(x, y, z);
        vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3d.createCached(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }
        Entity entity = null;
        List list = world.getEntities(this, boundingBox.stretch(velocityX, velocityY, velocityZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity)list.get(i);
            if(!entity1.isCollidable() || entity1 == owner && flyTime < 5)
            {
                continue;
            }
            float f3 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f3, f3, f3);
            HitResult movingobjectposition1 = axisalignedbb.raycast(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.pos);
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
            int k = world.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            if(movingobjectposition.entity != null || k != Block.GRASS.id)
            {
                if(movingobjectposition.entity != null)
                {
                    int l = (int) damageShell;
                    if(!explodeBoolean)
                    {
                        l *= 3;
                    }
                    if((owner instanceof Monster) && (movingobjectposition.entity instanceof PlayerEntity))
                    {
                        if(world.difficulty == 0)
                        {
                            l = 0;
                        }
                        if(world.difficulty == 1)
                        {
                            l = l / 3 + 1;
                        }
                        if(world.difficulty == 3)
                        {
                            l = (l * 3) / 2;
                        }
                    }
                    if(movingobjectposition.entity instanceof LivingEntity) //TODO: ?
                    {
                        SdkTools.attackEntityIgnoreDelay((LivingEntity)movingobjectposition.entity, this, l);
                    } else
                    {
                        if(movingobjectposition.entity instanceof WW2Plane || movingobjectposition.entity instanceof WW2Tank || movingobjectposition.entity instanceof WW2Truck || movingobjectposition.entity instanceof WW2Cannon)
                        {
                            movingobjectposition.entity.damage(this, l);
                        }else {
                            movingobjectposition.entity.damage(owner, l);
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
        if(checkWaterCollisions())
        {
            for(int j = 0; j < 4; j++)
            {
                float f5 = 0.25F;
                world.addParticle("bubble", x - velocityX * (double)f5, y - velocityY * (double)f5, z - velocityZ * (double)f5, velocityX, velocityY, velocityZ);
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

    public void writeNbt(NbtCompound nbttagcompound)
    {
        nbttagcompound.putShort("xTile", (short)xTile);
        nbttagcompound.putShort("yTile", (short)yTile);
        nbttagcompound.putShort("zTile", (short)zTile);
    }

    public void readNbt(NbtCompound nbttagcompound)
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

        Explosion explosion = new Explosion(world, null, x, (float)y, (float)z, damageShell/5);
        explosion.explode();
        if(explodeBoolean)
        {
            explosion.playExplosionSound(true);
        } else
        {
            world.playSound(x, y, z, "random.explode", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
        }
        for(int i = 0; i < 32; i++)
        {
            world.addParticle("explode", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
            world.addParticle("smoke", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
        }

        markDead();
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

    public float getShadowRadius()
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
    public LivingEntity owner;
    private int timeTillDeath;
    private int flyTime;
    protected float damageShell;
    private float muzzleVel;
    private float spreadVal;
}
