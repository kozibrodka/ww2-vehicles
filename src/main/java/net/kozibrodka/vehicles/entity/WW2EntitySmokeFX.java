package net.kozibrodka.vehicles.entity;

import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.ParticleBase;
import net.minecraft.level.Level;

public class WW2EntitySmokeFX extends ParticleBase
{

    public WW2EntitySmokeFX(Level world, double d, double d1, double d2,
                            double d3, double d4, double d5)
    {
        this(world, d, d1, d2, d3, d4, d5, 1.0F);
    }

    public WW2EntitySmokeFX(Level world, double d, double d1, double d2,
                            double d3, double d4, double d5, float f)
    {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        velocityX *= 0.10000000149011612D;
        velocityY *= 0.10000000149011612D;
        velocityZ *= 0.10000000149011612D;
        velocityX += d3;
        velocityY += d4;
        velocityZ += d5;
        field_2642 = field_2643 = field_2644 = (float)(Math.random() * 0.30000001192092896D);
        field_2640 *= 0.75F;
        field_2640 *= f;
        field_671_a = field_2640;
        field_2639 = (int)(8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
        field_2639 *= f;
        field_1642 = false;
    }

    public void method_2002(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
        float f6 = (((float)field_2638 + f) / (float)field_2639) * 32F;
        if(f6 < 0.0F)
        {
            f6 = 0.0F;
        }
        if(f6 > 1.0F)
        {
            f6 = 1.0F;
        }
        field_2640 = field_671_a * f6;
        super.method_2002(tessellator, f, f1, f2, f3, f4, f5);
    }

    public void tick()
    {
        prevX = x;
        prevY = y;
        prevZ = z;
        if(field_2638++ >= field_2639)
        {
            remove();
        }
        field_2635 = 7 - (field_2638 * 8) / field_2639;
        velocityY += 0.0040000000000000001D;
        move(velocityX, velocityY, velocityZ);
        if(y == prevY)
        {
            velocityX *= 1.1000000000000001D;
            velocityZ *= 1.1000000000000001D;
        }
        velocityX *= 0.95999997854232788D;
        velocityY *= 0.95999997854232788D;
        velocityZ *= 0.95999997854232788D;
        if(onGround)
        {
            velocityX *= 0.69999998807907104D;
            velocityZ *= 0.69999998807907104D;
        }
    }

    float field_671_a;
}
