package net.kozibrodka.vehicles.render;

import net.kozibrodka.vehicles.entity.EntityPassengerSeat;
import net.kozibrodka.vehicles.entity.EntityTruck;
import net.kozibrodka.vehicles.entity.EntityVehicle;
import net.kozibrodka.vehicles.model.ModelTruck;
import net.kozibrodka.vehicles.model.ModelVehicle;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityBase;
import org.lwjgl.opengl.GL11;

public class RenderTruck extends EntityRenderer {

    public RenderTruck() {
        this.field_2678 = 0.5F;
    }

    public void func_157_a(EntityTruck vehicle, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        float f2 = vehicle.prevPitch + (vehicle.pitch - vehicle.prevPitch) * f1;
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f2, 0.0F, 0.0F, 1.0F);
//        GL11.glTranslatef((float)d, (float)d1, (float)d2);
//        GL11.glRotatef(f + 90.0F, 0.0F, 1.0F, 0.0F);
//        GL11.glRotatef(vehicle.prevPitch + (vehicle.axes.getPitch() - vehicle.prevPitch) * f1, 0.0F, 0.0F, 1.0F);
//        GL11.glRotatef(-vehicle.prevRotationRoll - (vehicle.axes.getRoll() - vehicle.prevRotationRoll) * f1, 1.0F, 0.0F, 0.0F);
        this.bindTexture("/assets/vehicles/stationapi/textures/mob/" + vehicle.automobile.texture);
        ModelTruck modVehicle = vehicle.automobile.model;
        if(modVehicle != null) {
            modVehicle.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, vehicle);
        }
//        GL11.glPushMatrix();
        GL11.glPopMatrix();
    }

    public void render(EntityBase entity, double d, double d1, double d2, float f, float f1) {
        this.func_157_a((EntityTruck)entity, d, d1, d2, f, f1);
    }
}
