package net.kozibrodka.ww2.render;

import net.kozibrodka.ww2.entity.EntityCannon;
import net.kozibrodka.ww2.model.ModelCannon;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderCannon extends EntityRenderer {
    public RenderCannon() {
        this.shadowRadius = 0.5F;
    }

    public void renderAA(EntityCannon cannon, double d, double d1, double d2, float f, float f1) {
        if(cannon.cannonType == null){return;}
        GL11.glPushMatrix();
        float f2 = cannon.prevPitch + (cannon.pitch - cannon.prevPitch) * f1;
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f2, 0.0F, 0.0F, 1.0F);

        bindTexture("/assets/ww2/stationapi/textures/mob/cannon/" + cannon.cannonType.texture);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
//        GL11.glScalef(0.5F, 0.5F, 0.5F); ///mogę powiększyć/pomniejszyć...
        ModelCannon modelCannon = cannon.cannonType.model;
        modelCannon.renderBase(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, cannon);

        /// NEW TRY
//        GL11.glPushMatrix();
//        GL11.glPopMatrix();
//        float gunYaw = 90.0F;
//        float gunPitch = 0.0F;
//        if(modelCannon.barrelModel.length > 0){
//            gunYaw = cannon.gunYaw;
//            gunPitch = cannon.gunPitch;
//            GL11.glTranslatef(modelCannon.barrelModel[0][0].rotationPointX / 16.0F, modelCannon.barrelModel[0][0].rotationPointY / 16.0F, modelCannon.barrelModel[0][0].rotationPointZ / 16.0F);
//            GL11.glRotatef(180.0F + gunYaw, 0.0F, 1.0F, 0.0F);
//            GL11.glTranslatef(-modelCannon.barrelModel[0][0].rotationPointX / 16.0F, -modelCannon.barrelModel[0][0].rotationPointY / 16.0F, -modelCannon.barrelModel[0][0].rotationPointZ / 16.0F);
//            modelCannon.renderGun(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, cannon);
//        }
        /// NEW stop
        /// New v2
//        GL11.glRotatef(180.0F + cannon.gunYaw, 0.0F, 1.0F, 0.0F);
//        modelCannon.renderGun(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, cannon);
        /// New v2 stop
        GL11.glRotatef(180.0F - cannon.gunYaw, 0.0F, 1.0F, 0.0F);
        modelCannon.renderGun(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, cannon);
        GL11.glPopMatrix();
    }

    @Override
    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderAA((EntityCannon)entity, d, d1, d2, f, f1);
    }
}
