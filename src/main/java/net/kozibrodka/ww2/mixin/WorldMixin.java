package net.kozibrodka.ww2.mixin;

import net.kozibrodka.ww2.entity.EntityPassengerSeat;
import net.kozibrodka.ww2.entity.EntityTruck;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.WorldStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(World.class)
public class WorldMixin{

    @Shadow
    private ArrayList tempCollisionBoxes;

    /// This Mixin procudes posibility of ingoring certain Entities so that they won't block your movement - they will be vieved as AIR.

    @Inject(method = "getEntityCollisions", at = @At("HEAD"), cancellable = true)
    void getEntityCollisionsV2(Entity entity, Box box, CallbackInfoReturnable<List> cir) {
        this.tempCollisionBoxes.clear();
        int var3 = MathHelper.floor(box.minX);
        int var4 = MathHelper.floor(box.maxX + (double)1.0F);
        int var5 = MathHelper.floor(box.minY);
        int var6 = MathHelper.floor(box.maxY + (double)1.0F);
        int var7 = MathHelper.floor(box.minZ);
        int var8 = MathHelper.floor(box.maxZ + (double)1.0F);

        for(int var9 = var3; var9 < var4; ++var9) {
            for(int var10 = var7; var10 < var8; ++var10) {
                if (entity.world.isPosLoaded(var9, 64, var10)) {
                    for(int var11 = var5 - 1; var11 < var6; ++var11) {
                        Block var12 = Block.BLOCKS[entity.world.getBlockId(var9, var11, var10)];
                        if (var12 != null) {
                            var12.addIntersectingBoundingBox(entity.world, var9, var11, var10, box, this.tempCollisionBoxes);
                        }
                    }
                }
            }
        }

        double var14 = (double)0.25F;
        List var15 = entity.world.getEntities(entity, box.expand(var14, var14, var14));

        for(int var16 = 0; var16 < var15.size(); ++var16) {
            Box var13 = ((Entity)var15.get(var16)).getBoundingBox();
            if (var13 != null && var13.intersects(box) && shouldConsider(entity, (Entity)var15.get(var16))) { /// addon shouldConsider
                this.tempCollisionBoxes.add(var13);
            }

            var13 = entity.getCollisionAgainstShape((Entity)var15.get(var16));
            if (var13 != null && var13.intersects(box) && shouldConsider(entity, (Entity)var15.get(var16))) { /// addon shouldConsider
                this.tempCollisionBoxes.add(var13);
            }
        }

        cir.setReturnValue(this.tempCollisionBoxes);
//        return this.tempCollisionBoxes;
    }

    @Unique
    public boolean shouldConsider(Entity movingEntity, Entity objectEntity){
        /// Alpha version of this code, rewrite later
        if(movingEntity instanceof EntityTruck){
            if(objectEntity instanceof EntityPassengerSeat passSeat && passSeat.mother == movingEntity){
                return false;
            }
            if(objectEntity instanceof ItemEntity){
                return false;
            }
            if(objectEntity instanceof LivingEntity livin && livin.vehicle instanceof EntityPassengerSeat passSeat2 && passSeat2.mother == movingEntity){
                return false;
            }
        }
        return true;
    }


    /// Spróbować zaingerować w hasCollided, powykluczać odpowiednie Entityi....
}
