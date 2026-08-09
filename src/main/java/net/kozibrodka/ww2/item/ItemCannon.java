package net.kozibrodka.ww2.item;

import net.kozibrodka.ww2.entity.EntityCannon;
import net.kozibrodka.ww2.events.mod_Vehicles;
import net.kozibrodka.ww2.properties.CannonType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemCannon extends TemplateItem
{

    public ItemCannon(Identifier i, String s)
    {
        super(i);
        maxCount = 1;
        aaType = s;
    }

    @Override
    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer)
    {
        float f = 1.0F;
        float f1 = entityplayer.prevPitch + (entityplayer.pitch - entityplayer.prevPitch) * f;
        float f2 = entityplayer.prevYaw + (entityplayer.yaw - entityplayer.prevYaw) * f;
        double d = entityplayer.prevX + (entityplayer.x - entityplayer.prevX) * (double)f;
        double d1 = (entityplayer.prevY + (entityplayer.y - entityplayer.prevY) * (double)f + 1.6200000000000001D) - (double)entityplayer.standingEyeHeight;
        double d2 = entityplayer.prevZ + (entityplayer.z - entityplayer.prevZ) * (double)f;
        Vec3d vec3d = Vec3d.createCached(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3d vec3d1 = vec3d.add((double)f7 * d3, (double)f8 * d3, (double)f9 * d3);
        HitResult movingobjectposition = world.raycast(vec3d, vec3d1, true);
        if(movingobjectposition == null)
        {
            return itemstack;
        }
        if(movingobjectposition.type == HitResultType.BLOCK)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            CannonType cannonType = mod_Vehicles.getCannonType(aaType);
            if(!world.isRemote)
            {
                int l = itemstack.getDamage();
                world.spawnEntity(new EntityCannon(world, cannonType, (double)i + 0.5D, (double)j + 1.5D, (double)k + 0.5D));
            }
            itemstack.count--;
        }
        return itemstack;
    }

    private String aaType;
}
