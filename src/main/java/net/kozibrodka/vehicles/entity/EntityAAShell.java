package net.kozibrodka.vehicles.entity;

import net.kozibrodka.sdk_api.events.ingame.mod_SdkGuns;
import net.kozibrodka.sdk_api.events.utils.SdkTools;
import net.kozibrodka.sdk_api.events.utils.WW2Plane;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.Vec3f;

import java.util.List;

public class EntityAAShell extends EntityBase
{

    public EntityAAShell(Level world)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        flyTime = 0;
        setSize(0.5F, 0.5F);
    }

    public EntityAAShell(Level world, double d, double d1, double d2,
                         double d3, double d4, double d5, float dmg, float vel, float spr, int rng)
    {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        flyTime = 0;
        setSize(0.5F, 0.5F);
        setPosition(d, d1, d2);
        standingEyeHeight = 0.0F;
        setVelocity(d3, d4, d5);
        damageAA = (int)dmg;
        spreadVal = spr;
        muzzleVel = vel;
        aaRange = rng;

        setArrowHeading(velocityX, velocityY, velocityZ, muzzleVel, spreadVal);
        
    }

    public EntityAAShell(Level world, double d, double d1, double d2)
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
        flyTime++;
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
            float f2 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
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
            if(movingobjectposition.field_1989 != null)
            {
                if(movingobjectposition.field_1989.damage(this, damageAA/2))
                {
                    level.playSound(this, "vehicles:flak", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                    remove();
                }
            } else
            {
                xTile = movingobjectposition.x;
                yTile = movingobjectposition.y;
                zTile = movingobjectposition.z;
                inTile = level.getTileId(xTile, yTile, zTile);

                velocityX = (float)(movingobjectposition.field_1988.x - x);
                velocityY = (float)(movingobjectposition.field_1988.y - y);
                velocityZ = (float)(movingobjectposition.field_1988.z - z);
                float f1 = MathHelper.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
                x -= (velocityX / (double)f1) * 0.05000000074505806D;
                y -= (velocityY / (double)f1) * 0.05000000074505806D;
                z -= (velocityZ / (double)f1) * 0.05000000074505806D;
                level.playSound(this, "vehicles:bullethit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                if (inTile == BlockBase.GLASS.id && mod_SdkGuns.bulletsDestroyGlass) {
                    SdkTools.minecraft.particleManager.addTileBreakParticles(xTile, yTile, zTile, BlockBase.GLASS.id & 0xff, BlockBase.GLASS.id >> 8 & 0xff);
                    SdkTools.minecraft.soundHelper.playSound(BlockBase.GLASS.sounds.getBreakSound(), (float) xTile + 0.5F, (float) yTile + 0.5F, (float) zTile + 0.5F, (BlockBase.GLASS.sounds.getVolume() + 1.0F) / 2.0F, BlockBase.GLASS.sounds.getPitch() * 0.8F);
                    level.setTile(xTile, yTile, zTile, 0);
                    BlockBase.GLASS.activate(level, xTile, yTile, zTile, level.getTileMeta(xTile, yTile, zTile));
                }
                if (inTile == BlockBase.LEAVES.id && mod_SdkGuns.bulletsDestroyGlass) {
                    SdkTools.minecraft.particleManager.addTileBreakParticles(xTile, yTile, zTile, BlockBase.LEAVES.id & 0xff, BlockBase.LEAVES.id >> 8 & 0xff);
                    SdkTools.minecraft.soundHelper.playSound(BlockBase.LEAVES.sounds.getBreakSound(), (float) xTile + 0.5F, (float) yTile + 0.5F, (float) zTile + 0.5F, (BlockBase.LEAVES.sounds.getVolume() + 1.0F) / 2.0F, BlockBase.LEAVES.sounds.getPitch() * 0.8F);
                    level.setTile(xTile, yTile, zTile, 0);
                    BlockBase.LEAVES.activate(level, xTile, yTile, zTile, level.getTileMeta(xTile, yTile, zTile));
                }
                remove();
//                if(inTile == Block.glass.blockID || inTile == Block.glowStone.blockID || inTile == Block.leaves.blockID)
//                {
//                    Block block = Block.blocksList[inTile];
//                    ModLoader.getMinecraftInstance().sndManager.playSound(block.stepSound.stepSoundDir(), (float)xTile + 0.5F, (float)yTile + 0.5F, (float)zTile + 0.5F, (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
//                    level.setBlockWithNotify(xTile, yTile, zTile, 0);
//                } else
//                {
//                    velocityX = (float)(movingobjectposition.field_1988.x - x);
//                    velocityY = (float)(movingobjectposition.field_1988.y - y);
//                    velocityZ = (float)(movingobjectposition.field_1988.z - z);
//                    float f1 = MathHelper.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
//                    x -= (velocityX / (double)f1) * 0.05000000074505806D;
//                    y -= (velocityY / (double)f1) * 0.05000000074505806D;
//                    z -= (velocityZ / (double)f1) * 0.05000000074505806D;
//                    level.playSoundAtEntity(this, "ofensywa:bullethit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
//                    remove();
//                }
            }
        }
        List list1 = level.getEntities(WW2Plane.class, Box.createButWasteMemory(x - 4D, y - 4D, z - 4D, x + 4D, y + 4D, z + 4D));
        if(!list1.isEmpty() || flyTime >= aaRange) //20
        {
            for(int j = 0; j < 1000; j++)
            {
                WW2EntitySmokeFX entitysmokefx = new WW2EntitySmokeFX(level, x + rand.nextGaussian(), y + rand.nextGaussian(), z + rand.nextGaussian(), 0.01D, 0.01D, 0.01D);
                entitysmokefx.velocityX = rand.nextGaussian() / 20D;
                entitysmokefx.velocityY = rand.nextGaussian() / 20D;
                entitysmokefx.velocityZ = rand.nextGaussian() / 20D;
                entitysmokefx.renderDistanceMultiplier = 200D;
                SdkTools.minecraft.particleManager.addParticle(entitysmokefx);
//                ModLoader.getMinecraftInstance().effectRenderer.addEffect(entitysmokefx);
            }

            level.playSound(this, "vehicles:flak", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
            for(int k = 0; k < list1.size(); k++)
            {
                EntityBase entityplane = (EntityBase)list1.get(k); //TODO: czy to zadziała??
                entityplane.damage(this, damageAA*5);  //50
            }

            remove();
        }
//        List list2 = level.getEntities(EntityPlaneNew.class, Box.createButWasteMemory(x - 4D, y - 4D, z - 4D, x + 4D, y + 4D, z + 4D));
//        if(!list2.isEmpty() || flyTime >= aaRange) //20
//        {
//            for(int l = 0; l < 1000; l++)
//            {
//                WW2EntitySmokeFX entitysmokefx = new WW2EntitySmokeFX(level, x + rand.nextGaussian(), y + rand.nextGaussian(), z + rand.nextGaussian(), 0.01D, 0.01D, 0.01D);
//                entitysmokefx.velocityX = rand.nextGaussian() / 20D;
//                entitysmokefx.velocityY = rand.nextGaussian() / 20D;
//                entitysmokefx.velocityZ = rand.nextGaussian() / 20D;
//                entitysmokefx.renderDistanceMultiplier = 200D;
//                SdkTools.minecraft.particleManager.addParticle(entitysmokefx);
////                ModLoader.getMinecraftInstance().effectRenderer.addEffect(entitysmokefx1);
//            }
//
//            level.playSound(this, "Flak", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
//            for(int i1 = 0; i1 < list2.size(); i1++)
//            {
//                EntityPlaneNew entitypl = (EntityPlaneNew)list2.get(i1);
//                entitypl.damage(this, 40);
//            }
//
//            remove();
//        }
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        float f3 = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
        yaw = (float)((Math.atan2(velocityX, velocityZ) * 180D) / 3.1415927410125732D);
        for(pitch = (float)((Math.atan2(velocityY, f3) * 180D) / 3.1415927410125732D); pitch - prevPitch < -180F; prevPitch -= 360F) { }
        for(; pitch - prevPitch >= 180F; prevPitch += 360F) { }
        for(; yaw - prevYaw < -180F; prevYaw -= 360F) { }
        for(; yaw - prevYaw >= 180F; prevYaw += 360F) { }
        pitch = prevPitch + (pitch - prevPitch) * 0.2F;
        yaw = prevYaw + (yaw - prevYaw) * 0.2F;
        float f4 = 1.002557F;
        float f5 = 0.03F;
        if(method_1393())
        {
            for(int j1 = 0; j1 < 4; j1++)
            {
                float f6 = 0.25F;
                level.addParticle("bubble", x - velocityX * (double)f6, y - velocityY * (double)f6, z - velocityZ * (double)f6, velocityX, velocityY, velocityZ);
            }

            f4 = 0.8F;
        }
        velocityX *= f4;
        velocityY *= f4;
        velocityZ *= f4;
        setPosition(x, y, z);
    }

    public void writeCustomDataToTag(CompoundTag nbttagcompound)
    {
        nbttagcompound.put("xTile", (short)xTile);
        nbttagcompound.put("yTile", (short)yTile);
        nbttagcompound.put("zTile", (short)zTile);
        nbttagcompound.put("inTile", (byte)inTile);
        nbttagcompound.put("shake", (byte)arrowShake);
        nbttagcompound.put("inGround", (byte)(inGround ? 1 : 0));
    }

    public void readCustomDataFromTag(CompoundTag nbttagcompound)
    {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        arrowShake = nbttagcompound.getByte("shake") & 0xff;
        inGround = nbttagcompound.getByte("inGround") == 1;
    }

//    public void onCollideWithPlayer(EntityPlayer entityplayer)
//    {
//        if(worldObj.multiplayerWorld)
//        {
//            return;
//        }
//        if(inGround && owner == entityplayer && arrowShake <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1)))
//        {
//            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
//            entityplayer.onItemPickup(this, 1);
//            setEntityDead();
//        }
//    }

    public boolean shouldRenderAtDistance(double d) {
        return true;
    }


    public float getEyeHeight()
    {
        return 0.0F;
    }

    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    private boolean inGround;
    public int arrowShake;
    public Living owner;
    private int timeTillDeath;
    private int flyTime;
    protected int damageAA;
    private float muzzleVel;
    private float spreadVal;
    private int aaRange;
}
