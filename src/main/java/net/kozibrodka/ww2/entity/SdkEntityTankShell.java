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

public class SdkEntityTankShell extends SdkEntityBullet { //todo rename bez sdk

    public SdkEntityTankShell(World world)
    {
        super(world);
        setBoundingBoxSpacing(0.25F, 0.25F);
    }

    public SdkEntityTankShell(World world, double d, double d1, double d2)
    {
        this(world);
        setBoundingBoxSpacing(0.25F, 0.25F);
        this.setPosition(d, d1, d2);
        this.standingEyeHeight = 0.0F;
        this.serverSpawned = true;
        this.doFlash(false);
        this.bulletDrop = 0.005F;
    }

    public float exploPower;
    public boolean exploDeBlocks;
    public boolean exploFire;
    public float spread;
    public float muzzleVelocity;

    public SdkEntityTankShell(World world, EntityTank tankEntity, TankType tankType)
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
        /// optymalization - claudeAI
        double pivotX = tankEntity.automobile.barrelPivotXOffset / 16.0;
        double pivotY = tankEntity.automobile.barrelPivotYOffset / 16.0;
        double pivotZ = tankEntity.automobile.barrelPivotZOffset / 16.0;
        double barrelLength = tankEntity.automobile.barrelLength / 16.0;

        double radYaw = Math.toRadians(-tankEntity.yaw);
        double radGun = Math.toRadians(tankEntity.gunYaw - 180.0F);
        double radPitch = Math.toRadians(tankEntity.pitch);
        double radGunPitch = Math.toRadians(tankEntity.gunPitch);

        double cosG = Math.cos(radGun),  sinG = Math.sin(radGun);
        double cosP = Math.cos(radPitch), sinP = Math.sin(radPitch);
        double cosY = Math.cos(radYaw),  sinY = Math.sin(radYaw);
        double cosGP = Math.cos(radGunPitch), sinGP = Math.sin(radGunPitch);

        // wektor lufy w lokalnym układzie wieży (po uniesieniu gunPitch)
        double localBarrelX = barrelLength * cosGP;
        double localBarrelY = barrelLength * sinGP;

        double[] dir = rotateTank(localBarrelX, localBarrelY, 0.0,
                cosG, sinG, cosP, sinP, cosY, sinY);
        double dirX = dir[0], dirY = dir[1], dirZ = dir[2];

        double[] piv = rotateTank(pivotX, pivotY, pivotZ,
                cosG, sinG, cosP, sinP, cosY, sinY);

        double worldX = dirX + piv[0];
        double worldY = dirY + piv[1];
        double worldZ = dirZ + piv[2];

        double horizontalDistance = Math.sqrt(dirX * dirX + dirZ * dirZ);
        float bulletYaw   = (float)(Math.atan2(dirZ, dirX) * 180.0 / Math.PI) - 90.0F;
        float bulletPitch = (float)-(Math.atan2(dirY, horizontalDistance) * 180.0 / Math.PI);

        this.setPositionAndAnglesKeepPrevAngles(
                tankEntity.x + worldX,
                tankEntity.y + worldY,
                tankEntity.z + worldZ,
                bulletYaw,
                bulletPitch
        );
        ///
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

    public SdkEntityTankShell(World world, EntityCannon cannonEntity, CannonType cannonType)
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
        /// claude AI optymized code
        double pivotX = cannonEntity.cannonType.barrelPivotXOffset / 16.0;
        double pivotY = cannonEntity.cannonType.barrelPivotYOffset / 16.0;
        double pivotZ = cannonEntity.cannonType.barrelPivotZOffset / 16.0;
        double barrelLength = cannonEntity.cannonType.barrelLength / 16.0;

        double shellOffsetY = cannonEntity.cannonType.shellYOffset[cannonEntity.currentBarrel] / 16.0;
        double shellOffsetZ = -cannonEntity.cannonType.shellZOffset[cannonEntity.currentBarrel] / 16.0;

        double radYaw = Math.toRadians(-cannonEntity.yaw);
        double radGun = Math.toRadians(cannonEntity.gunYaw - 180.0F);
        double radPitch = Math.toRadians(cannonEntity.pitch);
        double radGunPitch = Math.toRadians(cannonEntity.gunPitch);

        double cosG = Math.cos(radGun),  sinG = Math.sin(radGun);
        double cosP = Math.cos(radPitch), sinP = Math.sin(radPitch);
        double cosY = Math.cos(radYaw),  sinY = Math.sin(radYaw);
        double cosGP = Math.cos(radGunPitch), sinGP = Math.sin(radGunPitch);

// --- 1) CZYSTY kierunek lufy (bez offsetu) - używany TYLKO do liczenia kąta lotu ---
        double barrelDirX = barrelLength * cosGP;
        double barrelDirY = barrelLength * sinGP;

        double[] dirOnly = rotateTank(barrelDirX, barrelDirY, 0.0,
                cosG, sinG, cosP, sinP, cosY, sinY);

// --- 2) Offset punktu wylotu (mocowanie lufy), osobno od kierunku ---
// shellOffsetY obraca się razem z gunPitch (ta sama "kołyska" co lufa)
        double offsetX = -shellOffsetY * sinGP;
        double offsetY =  shellOffsetY * cosGP;

        double[] offsetRotated = rotateTank(offsetX, offsetY, shellOffsetZ,
                cosG, sinG, cosP, sinP, cosY, sinY);

        double[] piv = rotateTank(pivotX, pivotY, pivotZ,
                cosG, sinG, cosP, sinP, cosY, sinY);

// --- 3) Pozycja = pivot + offset mocowania + kierunek lufy ---
        double worldX = dirOnly[0] + offsetRotated[0] + piv[0];
        double worldY = dirOnly[1] + offsetRotated[1] + piv[1];
        double worldZ = dirOnly[2] + offsetRotated[2] + piv[2];

// --- 4) Kąt lotu liczony WYŁĄCZNIE z czystego kierunku lufy (dirOnly) ---
        double horizontalDistance = Math.sqrt(dirOnly[0] * dirOnly[0] + dirOnly[2] * dirOnly[2]);
        float bulletYaw   = (float)(Math.atan2(dirOnly[2], dirOnly[0]) * 180.0 / Math.PI) - 90.0F;
        float bulletPitch = (float)-(Math.atan2(dirOnly[1], horizontalDistance) * 180.0 / Math.PI);

        this.setPositionAndAnglesKeepPrevAngles(
                cannonEntity.x + worldX,
                cannonEntity.y + worldY,
                cannonEntity.z + worldZ,
                bulletYaw,
                bulletPitch
        );
        ///
        float f7 = this.spread;
        if (!cannonEntity.onGround) {
            f7 *= 2.0F;
        }
        if (cannonEntity.passenger instanceof PlayerEntity) {
            this.owner = cannonEntity.passenger;
        }
        velocityX = -MathHelper.sin((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        velocityZ = MathHelper.cos((yaw / 180F) * 3.141593F) * MathHelper.cos((pitch / 180F) * 3.141593F);
        velocityY = -MathHelper.sin((pitch / 180F) * 3.141593F);
        this.setBulletHeading(this.velocityX, this.velocityY, this.velocityZ, this.muzzleVelocity, f7 / 2.0F);
        this.doFlash(true);
    }

    protected static double[] rotateTank(double x, double y, double z,
                                       double cosG, double sinG, double cosP, double sinP, double cosY, double sinY) {
        // Krok A: obrót wieży (gunYaw), oś Y
        double x1 = x * cosG - z * sinG;
        double z1 = x * sinG + z * cosG;
        // Krok B: pochylenie czołgu (pitch), oś Z
        double x2 = x1 * cosP - y * sinP;
        double y2 = x1 * sinP + y * cosP;
        // Krok C: globalny yaw świata, oś Y
        double x3 = x2 * cosY + z1 * sinY;
        double z3 = -x2 * sinY + z1 * cosY;

        return new double[]{x3, y2, z3};
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
        if(timeInAir == 0) {
            velocityX = 0;
            velocityY = 0;
            velocityZ = 0;
            return;
        }
        /// DEBUG for setting ShellPosParameters for Tanks. ^
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
        world.playSound(this, "ww2:tankshell", 4.0F, 1.0F / (random.nextFloat() * 0.1F + 0.95F));
    }

    private void explode()
    {
        boolean flagW = false;
        if(checkWaterCollisions()){
            flagW = true;
            for (int i = 0; i < 32; i++) {
                world.addParticle("splash", x + world.random.nextDouble() - 0.5D, y + (world.random.nextDouble()*3.0D), z + world.random.nextDouble() - 0.5D, 1, 1, 1);
                world.addParticle("bubble", x, y, z, (world.random.nextDouble() - 0.5D) * 3.0D, (world.random.nextDouble() - 0.5D) * 10.0D, (world.random.nextDouble() - 0.5D) * 3.0D);
            }
        }else {
            for (int i = 0; i < 32; i++) {
                world.addParticle("explode", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
                world.addParticle("smoke", x, y, z, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D, world.random.nextDouble() - 0.5D);
            }
        }
        SdkExplosion explosion = new SdkExplosion(world, null, x, y, z, exploPower, exploFire, exploDeBlocks, getServerExploSound(), flagW);
        explosion.setVolume(4.0F);
        explosion.explodeA();
        explosion.explodeB(true);
        markDead();
    }

}
