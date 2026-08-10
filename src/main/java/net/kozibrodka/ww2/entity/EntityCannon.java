package net.kozibrodka.ww2.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.sdk_api.utils.SdkEntityBullet;
import net.kozibrodka.sdk_api.utils.SdkEnvTool;
import net.kozibrodka.sdk_api.utils.SdkItemCustomUseDelay;
import net.kozibrodka.ww2.entityBullet.ShellFactory;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.network.PassengerEnterPacket;
import net.kozibrodka.ww2.properties.CannonType;
import net.kozibrodka.sdk_api.utils.WW2Cannon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

@HasTrackingParameters(trackingDistance = 160, updatePeriod = 2, sendVelocity = TriState.TRUE)
public class EntityCannon extends EntityVehicle implements WW2Cannon, EntitySpawnDataProvider {
    private int field_9394_d;
    private double field_9393_e;
    private double field_9392_f;
    private double field_9391_g;
    private double field_9390_h;
    private double field_9389_i;
    private double field_9388_j;
    private double field_9387_k;
    private double field_9386_l;

    public int health;
    protected int shootDelay;
    public float gunYaw;
    public float gunPitch;
    public float[] barrelRecoil;
    public CannonType cannonType;
    protected int barrelHeat;
    protected int overHeat;
//    public Entity towedByEntity;
    public int shellDelay;
    public int currentBarrel;

    public EntityCannon(World world) {
        super(world);
        standingEyeHeight = 0.0F;
        gunYaw = -180.0F;
        gunPitch = 0.0F;
        shootDelay = 0;
        currentBarrel = 0;
        currentShell = ArtShellType.NULL;
    }

    public EntityCannon(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        velocityX = 0.0D;
        velocityY = 0.0D;
        velocityZ = 0.0D;
        prevX = d;
        prevY = d1;
        prevZ = d2;
    }

    public EntityCannon(World world, CannonType type1, double d, double d1, double d2) {
        this(world);
        cannonType = type1;
        setDataFromCannon(cannonType);
        setBoundingBoxSpacing(cannonType.artWidth, cannonType.artHeight);
        setPosition(d, d1 + (double)standingEyeHeight, d2);
        health = cannonType.MAX_HEALTH;
        cargoItems = new ItemStack[inventorySize];
        barrelRecoil = new float[cannonType.numBarrels];
    }

    @Override
    public Box getCollisionAgainstShape(Entity entity) {
        return entity.boundingBox;
    }

    @Override
    public Box getBoundingBox() {
        return boundingBox;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return !dead;
    }

    @Override
    public double getPassengerRidingHeight() {
        return 0.0D;
    }

    @Override
    public float getShadowRadius() {
        return 0.0F;
    }

    @Override
    public boolean damage(Entity entity, int i) {

        if(dead)
        {
            return true;
        }
        if(entity == passenger && (entity instanceof PlayerEntity)){
            fireCannon();
        } else {
            if(entity instanceof LivingEntity){
                if(entity instanceof Monster){
                    scheduleVelocityUpdate(); //setBeenAttacked
                    health -= (int)i/5;
                    world.playSound(this, "planes:mechhurt", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    System.out.println("AA DAMAGED from: " + entity + " DMG: " + (int)i/5);
                }
                health -= i; ///addon
            }else{
                scheduleVelocityUpdate(); //setBeenAttacked
                health -= i;
                world.playSound(this, "planes:mechhurt", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                System.out.println("AA DAMAGED from: " + entity + " DMG: " + i);
            }

            if(!world.isRemote && health <= 0) {
                markDead();
            }
        }

        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if(passenger != null) {
            /// PRZED ZMIANAMI
//            gunYaw = (((passenger.yaw + 90F) - yaw)) % 360.0F;
//            while (gunYaw > 0) gunYaw -= 360F;
//            while (gunYaw < -360) gunYaw += 360F;
            /// PO zmianach
//            gunYaw = (-((passenger.yaw + 90F) - yaw)) % 360.0F;
//            while (gunYaw > 0) gunYaw -= 360F;
//            while (gunYaw < -360) gunYaw += 360F;
            /// Yaw
            float passYaw = (-((passenger.yaw + 90F) - yaw)) % 360.0F;
            while (gunYaw > 0) gunYaw -= 360F;
            while (gunYaw < -360) gunYaw += 360F;
            while (passYaw > 0) passYaw -= 360F;
            float checkYaw = Math.abs(gunYaw-passYaw);
            if(checkYaw < cannonType.cannonYawSpeed || checkYaw > (360.0F - cannonType.cannonYawSpeed)){
                gunYaw = passYaw;
            }else{
                if(gunYaw > passYaw){
                    if(gunYaw-passYaw <= 180F){
                        gunYaw -= cannonType.cannonYawSpeed;
                    }else{
                        gunYaw += cannonType.cannonYawSpeed;
                    }
                }
                if(gunYaw < passYaw){
                    if(passYaw-gunYaw >= 180F){
                        gunYaw -= cannonType.cannonYawSpeed;
                    }else{
                        gunYaw += cannonType.cannonYawSpeed;
                    }
                }
            }
            /// +++
            if(cannonType.maxCannonDeviation > 0) {
                if (gunYaw > -180) {
                    if (gunYaw > (-180 + cannonType.maxCannonDeviation)) {
                        gunYaw = -180 + cannonType.maxCannonDeviation;
                    }
                }
                if (gunYaw < -180) {
                    if (gunYaw < (-180 - cannonType.maxCannonDeviation)) {
                        gunYaw = -180 - cannonType.maxCannonDeviation;
                    }
                }

            }
            /// Pitch
            float passPitch = passenger.pitch - pitch;
            if(Math.abs(passPitch - gunPitch) < cannonType.cannonPitchSpeed){
                gunPitch = passPitch;
            }else{
                if(passPitch > gunPitch){
                    gunPitch += cannonType.cannonPitchSpeed;
                }
                if(passPitch < gunPitch){
                    gunPitch -= cannonType.cannonPitchSpeed;
                }
            }
            if(gunPitch > cannonType.bottomViewLimit) {gunPitch = cannonType.bottomViewLimit;}
            if(gunPitch < -cannonType.topViewLimit) {gunPitch = -cannonType.topViewLimit;}
            /// Body Yaw
            if(clientRIGHT){
                this.yaw += cannonType.bodyTurnSpeed;
            }
            if(clientLEFT){
                this.yaw -= cannonType.bodyTurnSpeed;
            }

            setRotation(yaw, pitch);
//            System.out.println(gunYaw);


            if(clientFIRE){
                fireCannon();
            }
        }



        int i;
        for(i = 0; i < cannonType.numBarrels; ++i) {
            barrelRecoil[i] *= 0.9F;
        }
        if(shootDelay > 0) {
            --shootDelay;
        }
        if(barrelHeat > 0) {
            --barrelHeat;
        }
        if(overHeat > 0){
            --overHeat;
        }
        if(barrelHeat > 100){
            overHeat = 80;
        }
        if(!onGround) {
            velocityY -= 0.0245D;
        }
        velocityX *= 0.5D;
        velocityZ *= 0.5D;
        move(velocityX, velocityY, velocityZ);



            if(passenger != null && passenger.dead) {
                passenger = null;
            }
            if(shellDelay > 0) {
                --shellDelay;
            }

//        if(field_9394_d > 0) { //TODO interpelation stuff weird names...
//            double var9 = x + (field_9393_e - x) / (double)field_9394_d;
//            double d5 = y + (field_9392_f - y) / (double)field_9394_d;
//            double d9 = z + (field_9391_g - z) / (double)field_9394_d;
//
//            double d12;
//            for(d12 = field_9390_h - (double)yaw; d12 < -180.0D; d12 += 360.0D) {
//            }
//
//            while(d12 >= 180.0D) {
//                d12 -= 360.0D;
//            }
//
//            yaw = (float)((double)yaw + d12 / (double)field_9394_d);
//            pitch = (float)((double)pitch + (field_9389_i - (double)pitch) / (double)field_9394_d);
//            --field_9394_d;
//            setPosition(var9, d5, d9);
//            setRotation(yaw, pitch);
//        }
    }

    protected void setGunRotation(float yaw, float pitch) {
        gunYaw = yaw % 360.0F;
        gunPitch = pitch % 360.0F;
    }

    @Override
    public void markDead() {
        super.markDead();
        dropItem(cannonType.przedmiot.id, 1);
    }

    public void updatePassengerPosition_old() { /// stara funkcja, gdzie tylko wieżyczka się obraca a Yaw zawsze w miejscu stoi.
        if(passenger != null) {
            double x = (double)cannonType.gunnerX / 16.0D;
            double y = (double)cannonType.gunnerY / 16.0D;
            double z = (double)cannonType.gunnerZ / 16.0D;
            /// OLD
//            double cosYaw = Math.cos((double)(-gunYaw) / 180.0D * Math.PI);
//            double sinYaw = Math.sin((double)(-gunYaw) / 180.0D * Math.PI);
            /// NEW
            double cosYaw = Math.cos((double)(gunYaw) / 180.0D * Math.PI);
            double sinYaw = Math.sin((double)(gunYaw) / 180.0D * Math.PI);
            double cosPitch = Math.cos((double)gunPitch / 180.0D * Math.PI);
            double sinPitch = Math.sin((double)gunPitch / 180.0D * Math.PI);
            double x2 = x * cosYaw + z * sinYaw;
            double z2 = -x * sinYaw + z * cosYaw;
            passenger.setPosition(this.x + x2, this.y + y, this.z + z2);
        }
    }

    @Override
    public void updatePassengerPosition(){
        double d2 = (double)cannonType.gunnerX / 16D;
        double d4 = 0.0D;
        double d6 = (double)cannonType.gunnerZ / 16D;
        double d8 = Math.cos(((double)(-(yaw + (-gunYaw))) / 180D) * 3.1415926535897931D);
        double d10 = Math.sin(((double)(-(yaw + (-gunYaw))) / 180D) * 3.1415926535897931D);
        double d12 = Math.cos(((double)(-(pitch + gunPitch)) / 180D) * 3.1415926535897931D);
        double d14 = Math.sin(((double)(-(pitch + gunPitch)) / 180D) * 3.1415926535897931D);
        double d16 = (d2 * d12 - d4 * d14) * d8 + d6 * d10;
        double d18 = d2 * d14 + d4 * d12;
        double d20 = (d4 * d14 - d2 * d12) * d10 + d6 * d8;
        passenger.setPosition(this.x + d16, this.y + d18 + (double)cannonType.gunnerY / 16D, this.z + d20);
    }

    @Override
    protected void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putString("Type", cannonType.name);
        nbttagcompound.putInt("Health", health);
        nbttagcompound.putFloat("GunYaw", gunYaw);
        nbttagcompound.putFloat("GunPitch", gunPitch);
    }

    @Override
    protected void readNbt(NbtCompound nbttagcompound) {
        cannonType = mod_Vehicles.getCannonType(nbttagcompound.getString("Type"));
        setDataFromCannon(cannonType);
        setBoundingBoxSpacing(cannonType.artWidth, cannonType.artHeight);
        setPosition(x, y, z);
        super.readNbt(nbttagcompound);
        health = nbttagcompound.getInt("Health");
        gunYaw = nbttagcompound.getFloat("GunYaw");
        gunPitch = nbttagcompound.getFloat("GunPitch");
        barrelRecoil = new float[cannonType.numBarrels];
    }

    public void fireCannon(){
        if(overHeat > 0){
            world.playSound(x, y, z, "random.fizz", 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
            return;
        }
        if(shellDelay <= 0 && currentShell == ArtShellType.NULL){
            broadcastEventEmptySound();
            shellDelay = cannonType.shootDelay;
        }
        if(shellDelay > 0  || currentShell == ArtShellType.NULL)
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

        if(itemAmmo != 0) {
//            double d = 2.75D;
//            double d1 = 0.625D;
//            double d2 = 0.25D;
//            double d3 = Math.cos(((180D - (double) gunYaw) / 180D) * 3.1415926535897931D);
//            double d4 = Math.sin(((180D - (double) gunYaw) / 180D) * 3.1415926535897931D);
//            double d5 = Math.cos(((double) (-gunPitch) / 180D) * 3.1415926535897931D);
//            double d6 = Math.sin(((double) (-gunPitch) / 180D) * 3.1415926535897931D);
//            double d7 = (d * d5 - d1 * d6) * d3 + d2 * d4;
//            double d8 = d * d6 + d1 * d5;
//            double d9 = (d1 * d6 - d * d5) * d4 + d2 * d3;
//            d = 6.25D;
//            double d10 = (d * d5 - d1 * d6) * d3 + d2 * d4;
//            double d11 = d * d6 + d1 * d5;
//            double d12 = (d1 * d6 - d * d5) * d4 + d2 * d3;
//
//            world.playSound(x, y, z, cannonType.shootSound, 4F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
//            if (!world.isRemote) {
////                           System.out.println( j + "  " + ammo[j] + "  " + ammo[j].getDamage() +  "  " + ammo[j].getDurability());
//                world.spawnEntity(new EntityAAShell(world, d7 + x, d8 + y, d9 + z, d10 - d7, d11 - d8, d12 - d9, cannonType.cannonDamage, cannonType.cannonMuzzleVelocity, cannonType.cannonSpread, cannonType.cannonRange));
////                           level.addParticle("smoke", x + d7, y + d8, z + d9, d10 - d7, d11 - d8, d12 - d9);
//            }
            SdkEntityBullet tShell = ShellFactory.getShellBasedOnCannon(world,this, cannonType, currentShell);
            world.spawnEntity(tShell);

            removeStack(itemAmmo, 1);
            shellDelay = cannonType.shootDelay;
            barrelHeat += (shellDelay + 10);
            barrelRecoil[currentBarrel] = (float) cannonType.recoil;
            currentBarrel = (currentBarrel + 1) % cannonType.numBarrels;
        }

    }

    @Override
    public boolean interact(PlayerEntity entityplayer) {
        if(world.isRemote){
            if(!(passenger != null && passenger != entityplayer))
            {
                SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
            }
            return true;
        }

        if(passenger != null && passenger != entityplayer)
        {
            return true;
        }
        if(passenger == null)
        {
            SdkItemCustomUseDelay.doNotUseThisTick = world.getTime();
            if(currentShell.ordinal() == 0){
                currentShell = getFirstShell();
            }
            broadcastEventShellChange(currentShell.ordinal());
            entityplayer.setVehicle(this);
            if(SdkEnvTool.isEnvServ()) {
                PacketHelper.sendToAllTracking(this, new PassengerEnterPacket(id, entityplayer.name));
            }
        }
        return true;
    }

    @Override
    public void bombKey() {
        fireCannon();
    }

    @Override
    public void exitKey(PlayerEntity entityplayer) {
        passenger.setVehicle(null);
    }

    @Override
    public int getPercentHealth() {
        return (int) (((double)health/(double)cannonType.MAX_HEALTH)*100D);
    }

    @Override
    public void reloadKey() {
        changeShell(currentShell);
    }

    @Override
    public String getAmmoName() {
        return currentShell.hudName;
    }

    public void broadcastEventReload(){
        world.playSound(this, "ww2:tankreload", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
//        world.broadcastEntityEvent(this, (byte)9);
    }

    public void broadcastEventEmptySound(){
        world.playSound(this, "ww2:tnkfireempty", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
//        world.broadcastEntityEvent(this, (byte)14);
    }

    public void broadcastEventShellChange(int numer){
//        world.broadcastEntityEvent(this, (byte)(numer + 10));
    }

//    public void broadcastEventEmptyMGSound(){
//        world.playSound(this, "ww2:gunempty", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
//        world.broadcastEntityEvent(this, (byte)15);
//    }

    @Override
    @Environment(EnvType.CLIENT)
    public void processServerEntityStatus(byte status) {
        if (status == 6) {
//            passenger = null;
        } else if (status == 7) {
//            world.playSound(this, "ww2:mechhurt", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (status == 8) {
//            destroyVehicle();
        } else if (status == 9){
//            world.playSound(this, "ww2:tankreload", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        } else if (status >= 10 && status < 14) {
//            currentShell = EntityTank.ShellType.values()[status - 10];
        }else if (status == 14) {
//            world.playSound(this, "ww2:tnkfireempty", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }else if (status == 15) {
//            world.playSound(this, "ww2:gunempty", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }else if (status == 16) {
            //  System.out.println("healing sound");
//            world.playSound(this, "sdk:wrench", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }else{
            super.processServerEntityStatus(status);
        }
    }

    @Override
    protected void initDataTracker() {
//        dataTracker.startTracking(29, 0); //HEALTH
    }

    /// Shells ENUMs
    public enum ArtShellType {
        NULL(0, "", false),
        AP(mod_Vehicles.tankShell.id, "§9AP", false),
        HE(mod_Vehicles.tankShellHE.id, "§6HE", false),
        AA(mod_Vehicles.aaShell.id, "§bAA", true);

        final int ammoID;
        final String hudName;
        final boolean isAA;

        ArtShellType(int id, String hud, boolean aa) {
            ammoID = id;
            hudName = hud;
            isAA = aa;
        }
    }

    public ArtShellType currentShell;

    public void changeShell(ArtShellType type){
        ArtShellType swappedType = type;
        ArtShellType currentType = type;

        for(int i = 0; i < ArtShellType.values().length; i++){
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

    public ArtShellType getNextShell(ArtShellType current) {
        ArtShellType[] wartosci = ArtShellType.values();
        int next = (current.ordinal() + 1) % wartosci.length;
        if(next == 0){
            next = 1;
        }
        return wartosci[next];
    }

    public boolean isAmmoPresent(ArtShellType type){
        boolean flag = false;
        for(int j1 = slots_FirstShell; j1 < slots_Last; j1++)
        {
            if (cargoItems[j1] != null && cargoItems[j1].itemId == type.ammoID && doesAmmoFitCannon(type)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public ArtShellType getFirstShell(){
        ArtShellType flag = ArtShellType.NULL;
        boolean stop = false;
        for(int j1 = slots_FirstShell; j1 < slots_Last; j1++)
        {
            if(cargoItems[j1] != null){
                ArtShellType[] values = ArtShellType.values();
                for(int a = 0; a < values.length; a++){
                    if(cargoItems[j1].itemId == values[a].ammoID && doesAmmoFitCannon(values[a])){
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

    public void checkIfRemoved(ArtShellType type){
        if(!isAmmoPresent(type)){
            currentShell = getFirstShell();
            broadcastEventShellChange(currentShell.ordinal());
        }
    }

    public boolean doesAmmoFitCannon(ArtShellType type){
        return  type.isAA && cannonType.isAntiAircraft || !type.isAA && !cannonType.isAntiAircraft;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Identifier.of(mod_Vehicles.MOD_ID, "Cannon");
    }
}
