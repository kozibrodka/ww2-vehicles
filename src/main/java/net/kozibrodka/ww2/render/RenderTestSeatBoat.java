package net.kozibrodka.ww2.render;

import net.kozibrodka.ww2.entity.EntityPassengerSeat;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderTestSeatBoat extends EntityRenderer {

    protected EntityModel model;

    public RenderTestSeatBoat() {
        this.shadowRadius = 0.5F;
//        this.model = new BoatEntityModel();
    }

    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        this.renderMe((EntityPassengerSeat) entity,x,y,z,yaw,pitch);
    }

    Random random = new Random();
    String text = "";


    public void renderMe(EntityPassengerSeat passSeat, double d, double d1, double d2, float f, float f1) {

        if(passSeat.mother == null){
            return;
        }

        if(passSeat.seatNumber == 1){
            this.model = new MinecartEntityModel();
            text = "/item/cart.png";
        }else{
            this.model = new BoatEntityModel();
            text = "/item/boat.png";
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
//        GL11.glTranslatef((float)passSeat.mother.x, (float)passSeat.mother.y, (float)passSeat.mother.z);
        GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
        float f2 = passSeat.prevPitch + (passSeat.pitch - passSeat.prevPitch) * f1;
        GL11.glRotatef(-f2, 0.0F, 0.0F, 1.0F);

//        float f2 = passSeat.mother.prevPitch + (passSeat.mother.pitch - passSeat.mother.prevPitch) * f1;
//        GL11.glTranslatef((float)d, (float)d1, (float)d2);
//        GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
//        GL11.glRotatef(-f2, 0.0F, 0.0F, 1.0F);
//        GL11.glTranslatef(0.0F, 0.5F, 0.0F);

        float var10 = 1.0F;
        float var11 = 1.0F;
        if (var11 < 0.0F) {
            var11 = 0.0F;
        }

        if (var10 > 0.0F) {
            GL11.glRotatef(MathHelper.sin(var10) * var10 * var11 / 10.0F * 1.0F, 1.0F, 0.0F, 0.0F);
        }

        this.bindTexture("/terrain.png");
        float var12 = 0.75F;
        GL11.glScalef(var12, var12, var12);
        GL11.glScalef(1.0F / var12, 1.0F / var12, 1.0F / var12);
        this.bindTexture(text);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.model.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
