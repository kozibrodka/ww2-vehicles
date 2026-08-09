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
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);

        bindTexture("/assets/ww2/stationapi/textures/mob/cannon/" + cannon.cannonType.texture);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        ModelCannon modelCannon = cannon.cannonType.model;
        modelCannon.renderBase(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, cannon);
        GL11.glRotatef(180.0F - cannon.gunYaw, 0.0F, 1.0F, 0.0F);
        modelCannon.renderGun(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, cannon);
        GL11.glPopMatrix();
    }

    @Override
    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderAA((EntityCannon)entity, d, d1, d2, f, f1);
    }
}
