package net.kozibrodka.vehicles.render;

import net.kozibrodka.vehicles.entity.SdkEntityBulletMachineGun;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityBase;
import org.lwjgl.opengl.GL11;

public class SdkRenderBulletMachineGun extends EntityRenderer {

    public SdkRenderBulletMachineGun()
    {
    }

    public void renderArrow(SdkEntityBulletMachineGun sdkentitybullet, double d, double d1, double d2,
                            float f, float f1)
    {
        bindTexture("/assets/vehicles/stationapi/textures/mob/bullet.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef((sdkentitybullet.prevYaw + (sdkentitybullet.yaw - sdkentitybullet.prevYaw) * f1) - 90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(sdkentitybullet.prevPitch + (sdkentitybullet.pitch - sdkentitybullet.prevPitch) * f1, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.INSTANCE;
        int i = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (float)(0 + i * 10) / 32F;
        float f5 = (float)(5 + i * 10) / 32F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (float)(5 + i * 10) / 32F;
        float f9 = (float)(10 + i * 10) / 32F;
        float f10 = 0.05625F;
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        tessellator.start();
        tessellator.vertex(-7D, -2D, -2D, f6, f8);
        tessellator.vertex(-7D, -2D, 2D, f7, f8);
        tessellator.vertex(-7D, 2D, 2D, f7, f9);
        tessellator.vertex(-7D, 2D, -2D, f6, f9);
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        tessellator.start();
        tessellator.vertex(-7D, 2D, -2D, f6, f8);
        tessellator.vertex(-7D, 2D, 2D, f7, f8);
        tessellator.vertex(-7D, -2D, 2D, f7, f9);
        tessellator.vertex(-7D, -2D, -2D, f6, f9);
        tessellator.draw();
        for(int j = 0; j < 4; j++)
        {
            GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.start();
            tessellator.vertex(-8D, -2D, 0.0D, f2, f4);
            tessellator.vertex(8D, -2D, 0.0D, f3, f4);
            tessellator.vertex(8D, 2D, 0.0D, f3, f5);
            tessellator.vertex(-8D, 2D, 0.0D, f2, f5);
            tessellator.draw();
        }

        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }

    public void render(EntityBase entity, double d, double d1, double d2,
                       float f, float f1)
    {
        renderArrow((SdkEntityBulletMachineGun)entity, d, d1, d2, f, f1);
    }
}
