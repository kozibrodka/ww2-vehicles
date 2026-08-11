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
        ///
        System.out.println("NOWY TANK SHELL");
        ///

//// 1. OffSety z pikseli modelu na bloki
//        double pivotX = (double)tankEntity.automobile.barrelPivotXOffset / 16.0;
//        double pivotY = (double)tankEntity.automobile.barrelPivotYOffset / 16.0;
//        double pivotZ = (double)tankEntity.automobile.barrelPivotZOffset / 16.0;
//        double barrelLength = (double)tankEntity.automobile.barrelLength / 16.0;
//
//// 2. Kąty w radianach
//        double radYaw = Math.toRadians(-tankEntity.yaw);
//        double radGun = Math.toRadians(tankEntity.gunYaw - 180.0F);
//        double radPitch = Math.toRadians(tankEntity.pitch);
//        double radGunPitch = Math.toRadians(tankEntity.gunPitch);
//
//// =========================================================================
//// A. POZYCJA GLOBALNA JARZMA (Obliczenia dla długości lufy = 0)
//// =========================================================================
//        double cosG = Math.cos(radGun), sinG = Math.sin(radGun);
//        double pTX = pivotX * cosG - pivotZ * sinG;
//        double pTZ = pivotX * sinG + pivotZ * cosG;
//        double pTY = pivotY;
//
//        double cosP = Math.cos(radPitch), sinP = Math.sin(radPitch);
//        double pLocalX = pTX * cosP - pTY * sinP;
//        double pLocalY = pTX * sinP + pTY * cosP;
//        double pLocalZ = pTZ;
//
//        double cosY = Math.cos(radYaw), sinY = Math.sin(radYaw);
//        double pivotWorldX = pLocalX * cosY + pLocalZ * sinY;
//        double pivotWorldY = pLocalY;
//        double pivotWorldZ = -pLocalX * sinY + pLocalZ * cosY;
//
//// =========================================================================
//// B. POZYCJA GLOBALNA WYLOTU (Na bazie pozycji jarzma dodajemy wysunięcie lufy)
//// =========================================================================
//        double cosGP = Math.cos(radGunPitch), sinGP = Math.sin(radGunPitch);
//        double localBarrelX = barrelLength * cosGP;
//        double localBarrelY = barrelLength * sinGP;
//
//// Obrót wieży i kadłuba dla samego wysunięcia lufy
//        double bTX = localBarrelX * cosG;
//        double bTZ = localBarrelX * sinG;
//        double bTY = localBarrelY;
//
//        double bLocalX = bTX * cosP - bTY * sinP;
//        double bLocalY = bTX * sinP + bTY * cosP;
//        double bLocalZ = bTZ;
//
//// Końcowa pozycja wylotu pocisku w świecie
//        double worldX = pivotWorldX + (bLocalX * cosY + bLocalZ * sinY);
//        double worldY = pivotWorldY + bLocalY;
//        double worldZ = pivotWorldZ + (-bLocalX * sinY + bLocalZ * cosY);
//
//// =========================================================================
//// C. WYLICZENIE KĄTÓW NA BAZIE WEKTORA (Wylot - Jarzmo)
//// =========================================================================
//// Wektor kierunku lufy to po prostu przesunięcie wylotu względem jarzma
//        double dirX = worldX - pivotWorldX;
//        double dirY = worldY - pivotWorldY;
//        double dirZ = worldZ - pivotWorldZ;
//
//        double horizontalDistance = Math.sqrt(dirX * dirX + dirZ * dirZ);
//
//// Konwersja wektora na kąty dla silnika Beta 1.7.3
//        float bulletYaw = (float)(Math.atan2(dirZ, dirX) * 180.0D / Math.PI) - 90.0F;
//        float bulletPitch = (float)-(Math.atan2(dirY, horizontalDistance) * 180.0D / Math.PI);
//
//// 3. Ustawienie pozycji i kątów lotu kuli
//        this.setPositionAndAnglesKeepPrevAngles(
//                tankEntity.x + worldX,
//                tankEntity.y + worldY,
//                tankEntity.z + worldZ,
//                bulletYaw,
//                bulletPitch
//        );
        ///
//        setBoundingBoxSpacing(0.25F, 0.25F);
//        double d2 = (double)tankEntity.automobile.shellXOffset / 16D;
//        double d4 = (double)tankEntity.automobile.shellYOffset / 16D;
//        double d6 = (double)tankEntity.automobile.shellZOffset / 16D;
//        double d8 = Math.cos(((double)(-(tankEntity.yaw + tankEntity.gunYaw)) / 180D) * 3.1415926535897931D); //gunYawShoot
//        double d10 = Math.sin(((double)(-(tankEntity.yaw + tankEntity.gunYaw)) / 180D) * 3.1415926535897931D); //gunYawShoot
//        double d12 = Math.cos(((double)(-(tankEntity.pitch + tankEntity.gunPitch)) / 180D) * 3.1415926535897931D);
//        double d14 = Math.sin(((double)(-(tankEntity.pitch + tankEntity.gunPitch)) / 180D) * 3.1415926535897931D);
//        double d16 = (d2 * d12 - d4 * d14) * d8 + d6 * d10;
//        double d18 = d2 * d14 + d4 * d12;
//        double d20 = (d4 * d14 - d2 * d12) * d10 + d6 * d8;
//        this.setPositionAndAnglesKeepPrevAngles(tankEntity.x + d16, tankEntity.y + d18, tankEntity.z + d20, tankEntity.yaw - 90F + tankEntity.gunYaw, tankEntity.pitch + tankEntity.gunPitch);
        ///
// 1. OffSety z pikseli modelu na bloki
        double pivotX = (double)tankEntity.automobile.barrelPivotXOffset / 16.0;
        double pivotY = (double)tankEntity.automobile.barrelPivotYOffset / 16.0;
        double pivotZ = (double)tankEntity.automobile.barrelPivotZOffset / 16.0;
        double barrelLength = (double)tankEntity.automobile.barrelLength / 16.0;

// 2. Kąty w radianach
        double radYaw = Math.toRadians(-tankEntity.yaw);
        double radGun = Math.toRadians(tankEntity.gunYaw - 180.0F);
        double radPitch = Math.toRadians(tankEntity.pitch);
        double radGunPitch = Math.toRadians(tankEntity.gunPitch);

// 3. KROK 1: Obrót samej długości lufy w pionie (uniesienie)
        double cosGP = Math.cos(radGunPitch), sinGP = Math.sin(radGunPitch);
        double localBarrelX = barrelLength * cosGP;
        double localBarrelY = barrelLength * sinGP;

// 4. KROK 2: Obrót wieży (gunYaw) dla samego wektora lufy
        double cosG = Math.cos(radGun), sinG = Math.sin(radGun);
        double bTX = localBarrelX * cosG;
        double bTZ = localBarrelX * sinG;
        double bTY = localBarrelY;

// 5. KROK 3: Pochylenie całego czołgu (pitch) dla samego wektora lufy
        double cosP = Math.cos(radPitch), sinP = Math.sin(radPitch);
        double bLocalX = bTX * cosP - bTY * sinP;
        double bLocalY = bTX * sinP + bTY * cosP;
        double bLocalZ = bTZ;

// 6. KROK 4: Globalna transformacja pozioma świata gry (Yaw) -> WYLICZENIE CZYSTEGO WEKTORA KIERUNKU
        double cosY = Math.cos(radYaw), sinY = Math.sin(radYaw);
        double dirX = bLocalX * cosY + bLocalZ * sinY;
        double dirY = bLocalY;
        double dirZ = -bLocalX * sinY + bLocalZ * cosY;

// =========================================================================
// OBLICZANIE KOŃCOWEJ POZYCJI WYLOTU (Dodanie przesunięć jarzma na samym końcu)
// =========================================================================
// Transformacja jarzma (pivotu) czołgu przepuszczona przez te same funkcje trygonometryczne
        double pTX = pivotX * cosG - pivotZ * sinG;
        double pTZ = pivotX * sinG + pivotZ * cosG;
        double pLocalX = pTX * cosP - pivotY * sinP;
        double pLocalY = pTX * sinP + pivotY * cosP;

        double worldX = dirX + (pLocalX * cosY + pTZ * sinY);
        double worldY = dirY + pLocalY;
        double worldZ = dirZ + (-pLocalX * sinY + pTZ * cosY);

// =========================================================================
// WEKTOROWA KONWERSJA NA KĄTY SŁUŻBOWE MINECRAFTA (Zawsze idealna)
// =========================================================================
        double horizontalDistance = Math.sqrt(dirX * dirX + dirZ * dirZ);
        float bulletYaw = (float)(Math.atan2(dirZ, dirX) * 180.0D / Math.PI) - 90.0F;
        float bulletPitch = (float)-(Math.atan2(dirY, horizontalDistance) * 180.0D / Math.PI);

// 7. Ustawienie pozycji i kątów lotu kuli
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
