package net.kozibrodka.ww2.entity;

import net.kozibrodka.sdk_api.events.SdkGlass;
import net.kozibrodka.sdk_api.utils.*;
import net.kozibrodka.ww2.properties.CannonType;
import net.kozibrodka.ww2.properties.TankType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class SdkEntityAAShell extends SdkEntityBullet { //todo rename bez sdk

    public SdkEntityAAShell(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityAAShell(World world, double d, double d1, double d2)
    {
        this(world);
        setBoundingBoxSpacing(0.25F, 0.25F);
        this.setPosition(d, d1, d2);
        this.standingEyeHeight = 0.0F;
        this.serverSpawned = true;
        this.doFlash(false);
        this.bulletDrop = 0.005F;
        this.penetration = 2.5F;
    }


    public float exploPower;
    public boolean exploDeBlocks;
    public boolean exploFire;
    public float spread;
    public float muzzleVelocity;

    public SdkEntityAAShell(World world, EntityTank tankEntity, TankType tankType)
    {
        /// założenie Entity = Czołg, nie gracz
        this(world);
        this.owner = tankEntity;
        this.damage = tankType.cannonDamage;
        this.vehicleDamage = tankType.cannonVehicleDamage;
        this.penetration = tankType.cannonPenetration;
        this.bulletDrop = tankType.cannonBulletDrop;
        this.spread = tankType.cannonSpread;
        this.muzzleVelocity = tankType.cannonMuzzleVelocity;
        this.exploPower = tankType.cannonExploPower;
        this.exploDeBlocks = true;
        this.exploFire = false;
        this.standingEyeHeight = 0.0F;

        setBoundingBoxSpacing(0.25F, 0.25F);
        double d2 = (double)tankEntity.automobile.barrelLength / 16D;
        double d4 = (double)tankEntity.automobile.barrelLength / 16D; //todo
        double d6 = (double)tankEntity.automobile.barrelLength / 16D;
        double d8 = Math.cos(((double)(-(tankEntity.yaw + (-tankEntity.gunYaw))) / 180D) * 3.1415926535897931D); //gunYawShoot
        double d10 = Math.sin(((double)(-(tankEntity.yaw + (-tankEntity.gunYaw))) / 180D) * 3.1415926535897931D); //gunYawShoot
        double d12 = Math.cos(((double)(-(tankEntity.pitch + tankEntity.gunPitch)) / 180D) * 3.1415926535897931D);
        double d14 = Math.sin(((double)(-(tankEntity.pitch + tankEntity.gunPitch)) / 180D) * 3.1415926535897931D);
        double d16 = (d2 * d12 - d4 * d14) * d8 + d6 * d10;
        double d18 = d2 * d14 + d4 * d12;
        double d20 = (d4 * d14 - d2 * d12) * d10 + d6 * d8;
        this.setPositionAndAnglesKeepPrevAngles(tankEntity.x + d16, tankEntity.y + d18, tankEntity.z + d20, tankEntity.yaw - 90F + tankEntity.gunYaw, tankEntity.pitch + tankEntity.gunPitch);
        float f7 = this.spread;
        if (!tankEntity.onGround) {
            f7 *= 2.0F;
        }
        if (tankEntity.passenger instanceof PlayerEntity) {
            this.owner = tankEntity.passenger;
        }
        velocityX = -MathHelper.sin((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        velocityZ = MathHelper.cos((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        velocityY = -MathHelper.sin((pitch / 180F) * 3.141593F);
        this.setBulletHeading(this.velocityX, this.velocityY, this.velocityZ, this.muzzleVelocity, f7 / 2.0F);
        this.doFlash(true);
    }

    public SdkEntityAAShell(World world, EntityCannon cannonEntity, CannonType cannonType)
    {
        this(world);
        this.owner = cannonEntity;
        this.damage = cannonType.cannonDamage;
        this.vehicleDamage = cannonType.cannonVehicleDamage;
        this.penetration = cannonType.cannonPenetration;
        this.bulletDrop = cannonType.cannonBulletDrop;
        this.spread = cannonType.cannonSpread;
        this.muzzleVelocity = cannonType.cannonMuzzleVelocity;
        this.exploPower = cannonType.cannonExploPower;
        this.exploDeBlocks = true;
        this.exploFire = false;
        this.maxTimeAir = cannonType.cannonRange;
        this.standingEyeHeight = 0.0F;

        setBoundingBoxSpacing(0.25F, 0.25F);
        double d2 = (double)cannonEntity.cannonType.shellXOffset[cannonEntity.currentBarrel] / 16D;
        double d4 = (double)cannonEntity.cannonType.shellYOffset[cannonEntity.currentBarrel] / 16D;
        double d6 = (double)cannonEntity.cannonType.shellZOffset[cannonEntity.currentBarrel] / 16D;
        double d8 = Math.cos(((double)(-(cannonEntity.yaw + cannonEntity.gunYaw)) / 180D) * 3.1415926535897931D); //gunYawShoot
        double d10 = Math.sin(((double)(-(cannonEntity.yaw + cannonEntity.gunYaw)) / 180D) * 3.1415926535897931D); //gunYawShoot
        double d12 = Math.cos(((double)(-(cannonEntity.pitch + cannonEntity.gunPitch)) / 180D) * 3.1415926535897931D);
        double d14 = Math.sin(((double)(-(cannonEntity.pitch + cannonEntity.gunPitch)) / 180D) * 3.1415926535897931D);
        double d16 = (d2 * d12 - d4 * d14) * d8 + d6 * d10;
        double d18 = d2 * d14 + d4 * d12;
        double d20 = (d4 * d14 - d2 * d12) * d10 + d6 * d8;
        this.setPositionAndAnglesKeepPrevAngles(cannonEntity.x + d16, cannonEntity.y + d18, cannonEntity.z + d20, cannonEntity.yaw - 90F + cannonEntity.gunYaw, cannonEntity.pitch + cannonEntity.gunPitch);
        float f7 = this.spread;
        if (!cannonEntity.onGround) {
            f7 *= 2.0F;
        }
        if (cannonEntity.passenger instanceof PlayerEntity) {
            this.owner = cannonEntity.passenger;
        }
//        System.out.println("X: " + x +" Y: " + y + " Z: " + z);
        velocityX = -MathHelper.sin((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        velocityZ = MathHelper.cos((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        velocityY = -MathHelper.sin((pitch / 180F) * 3.141593F);
        this.setBulletHeading(this.velocityX, this.velocityY, this.velocityZ, this.muzzleVelocity, f7 / 2.0F);
        this.doFlash(true);
    }

    public void setVelocityClientShell(double d, double d1, double d2)
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


    public void addMoveParticle(int tick){
        if(tick % 1 == 0){
            double d = 0.625D;
            double d1 = Math.sqrt(velocityX * velocityX + velocityZ * velocityZ + velocityY * velocityY);
            world.addParticle("smoke", x - (velocityX / d1) * d, y - (velocityY / d1) * d, z - (velocityZ / d1) * d, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void tick() {
        baseTick();
        if (!serverSoundPlayed) {
            playServerSound(world);
            serverSoundPlayed = true;
        }
        if(y > 256 || timeInAir >= maxTimeAir || y < 0){ /// !world.isRemote && owner == null -> relog dead
            explode();
            return;
        }
        if(SdkEnvTool.isEnvClient())
        {
            addMoveParticle(timeInAir);
        }
        if (prevPitch == 0.0F && prevYaw == 0.0F) {
            float f = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
            prevYaw = yaw = (float) ((Math.atan2(velocityX, velocityZ) * 180D) / Math.PI);
            prevPitch = pitch = (float) ((Math.atan2(velocityY, f) * 180D) / Math.PI);
        }
        timeInAir++;
        Vec3d vecNc = Vec3d.createCached(x, y, z);
        Vec3d vecNc1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        HitResult nonColidableObjPos = world.raycast(vecNc, vecNc1, true);  /// {BEZ KOLIZJI - ON, LIQUID - ON}

        Vec3d vec3d = Vec3d.createCached(x, y, z);
        Vec3d vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
//        HitResult movingobjectposition = world.raycast(vec3d, vec3d1);  /// {BEZ KOLIZJI - ON, LIQUID - OFF}
//        HitResult movingobjectposition = world.raycast(vec3d, vec3d1, true);  /// {BEZ KOLIZJI - ON, LIQUID - ON}
        HitResult movingobjectposition = world.raycast(vec3d, vec3d1, false, true); /// {BEZ KOLIZJI - OFF, LIQUID - OFF}

        vec3d = Vec3d.createCached(x, y, z);
        vec3d1 = Vec3d.createCached(x + velocityX, y + velocityY, z + velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.createCached(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }
        Entity entity = null;
        List list = world.getEntities(this, boundingBox.stretch(velocityX, velocityY, velocityZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (int j = 0; j < list.size(); j++) {
            Entity entity1 = (Entity) list.get(j);
            if (!entity1.isCollidable() || (entity1 == owner || owner != null && entity1 == owner.vehicle || owner != null && entity1 == owner.passenger) && timeInAir < 5) { ///  || serverSpawned
                continue;
            }
            float f4 = 0.3F;
            Box axisalignedbb = entity1.boundingBox.expand(f4, f4, f4);
            HitResult movingobjectposition1 = axisalignedbb.raycast(vec3d, vec3d1);
            if (movingobjectposition1 == null) {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.pos);
            if (d1 < d || d == 0.0D) {
                entity = entity1;
                d = d1;
            }
        }
        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }
        float fV = 1.0F; /// Prędkość  og 1.002557F; - rocket
        float fD = bulletDrop; /// Opad
        if(touchedWater){
            fV = 0.98F;
            fD = 0.025F;
        }
        /// ADVANCED HitResoult - LIQUID (still) + non-Colliding Block
        if(nonColidableObjPos != null){
            xTile = nonColidableObjPos.blockX;
            yTile = nonColidableObjPos.blockY;
            zTile = nonColidableObjPos.blockZ;
            int k = world.getBlockId(xTile, yTile, zTile);
            int kMeta = world.getBlockMeta(xTile, yTile, zTile);
            inTile = k;
            if (SdkMap.BREAK_NONCOLL_LIST.contains(inTile) && SdkGlass.sdk_apiGlass.bulletsDestroyGlass && !touchedWater) {
                if(SdkEnvTool.isEnvClient()){
                    destroyBlockClient(inTile, kMeta, 4.0F); // 8.0F też spoko
                }
                if(!world.isRemote) {
                    world.setBlock(xTile, yTile, zTile, 0);
                }
            }
            if(k == 9 && !touchedWater){ /// WODA still
                fV = 0.25F;
                fD = 0.03F;
                timeInAir += 35;
                playImpactSound(world, Material.WATER);
                touchedWater = true;
            }
            if(k == 11){ /// LAVA still
                world.playSound(this, "random.fizz", 0.5F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
                playImpactSound(world, Material.WATER);
                explode();
            }
        }
        /// BASIC HitResoult - Entity + Solid Block
        if (movingobjectposition != null) {
            int k = world.getBlockId(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            int kMeta = world.getBlockMeta(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            if (movingobjectposition.entity != null) {
                int l = (damage- ((timeInAir-1)/10)); /// Opad DMG z czasem
                int lV = (vehicleDamage- ((timeInAir-1)/10));
                if (movingobjectposition.entity instanceof LivingEntity) {
                    SdkTools.attackEntityIgnoreDelay((LivingEntity) movingobjectposition.entity, owner, l);
                    playImpactSound(world, Material.FIRE);
//                    world.broadcastEntityEvent(this, (byte)6);
                } else {
                    if(movingobjectposition.entity instanceof SdkVehicle panzer)
                    {
                        if(penetration > panzer.getArmorFactor()){
                            movingobjectposition.entity.damage(this, l + lV); /// ADDICTIONAL DMG panzer
                            playImpactSound(world, Material.METAL);
//                            world.broadcastEntityEvent(this, (byte)7);
                        }else{
                            playImpactSound(world, Material.AIR);
//                            world.broadcastEntityEvent(this, (byte)8);
                        }
                    }else {
                        movingobjectposition.entity.damage(owner, l);
                        playImpactSound(world, Material.STONE);
//                        world.broadcastEntityEvent(this, (byte)9);
                    }
                }
                noImpSound = true;
            }
            playImpactSound(world, Block.BLOCKS[inTile].material);
            explode();
        }
        /// Anti-Aircraft Behaviour
        List list1 = world.collectEntitiesByClass(WW2Plane.class, Box.createCached(x - 4D, y - 4D, z - 4D, x + 4D, y + 4D, z + 4D));
        if(!list1.isEmpty()) //20
        {
            for (Object o : list1) {
                Entity entityplane = (Entity) o; //TODO: czy to zadziała??
                entityplane.damage(this, vehicleDamage);  //50
            }
            explode();
        }
        ///
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        float f1 = MathHelper.sqrt(velocityX * velocityX + velocityZ * velocityZ);
        yaw = (float) ((Math.atan2(velocityX, velocityZ) * 180D) / Math.PI);
        for (pitch = (float) ((Math.atan2(velocityY, f1) * 180D) / Math.PI); pitch - prevPitch < -180F; prevPitch -= 360F) {
        }
        for (; pitch - prevPitch >= 180F; prevPitch += 360F) {
        }
        for (; yaw - prevYaw < -180F; prevYaw -= 360F) {
        }
        for (; yaw - prevYaw >= 180F; prevYaw += 360F) {
        }
        pitch = prevPitch + (pitch - prevPitch) * 0.2F;
        yaw = prevYaw + (yaw - prevYaw) * 0.2F;
        if (checkWaterCollisions()) {
            for (int i1 = 0; i1 < 4; i1++) {
                float f6 = 0.25F;
                world.addParticle("bubble", x - velocityX * (double) f6, y - velocityY * (double) f6, z - velocityZ * (double) f6, velocityX, velocityY, velocityZ);
            }
            fV = 0.25F;
            fD = 0.03F;
            timeInAir += 50;
        }
        velocityX *= fV;
        velocityY *= fV;
        velocityZ *= fV;
        velocityY -= fD;
        setPosition(x, y, z);
    }


    public String getServerExploSound(){
        return "random.explode";
    }

    @Override
    public void playServerSound(World world) {
        world.playSound(this, "ww2:aafire", 4.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    private void explode()
    {
        for(int j = 0; j < 1000; j++)
        {
            WW2EntitySmokeFX entitysmokefx = new WW2EntitySmokeFX(world, x + random.nextGaussian(), y + random.nextGaussian(), z + random.nextGaussian(), 0.01D, 0.01D, 0.01D);
            entitysmokefx.velocityX = random.nextGaussian() / 20D;
            entitysmokefx.velocityY = random.nextGaussian() / 20D;
            entitysmokefx.velocityZ = random.nextGaussian() / 20D;
            entitysmokefx.renderDistanceMultiplier = 200D;
            SdkToolsRender.minecraft.particleManager.addParticle(entitysmokefx);
        }
        world.playSound(this, "ww2:flak", 4.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));

        markDead();
    }

}
