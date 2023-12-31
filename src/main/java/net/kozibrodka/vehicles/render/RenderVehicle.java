package net.kozibrodka.vehicles.render;

import net.kozibrodka.vehicles.entity.EntityPassengerSeat;
import net.kozibrodka.vehicles.entity.EntityVehicle;
import net.kozibrodka.vehicles.model.ModelVehicle;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityBase;
import org.lwjgl.opengl.GL11;

public class RenderVehicle extends EntityRenderer {

    public RenderVehicle() {
        this.field_2678 = 0.5F;
    }

    public void func_157_a(EntityVehicle vehicle, double d, double d1, double d2, float f, float f1) {
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
        ModelVehicle modVehicle = vehicle.automobile.model;
        if(modVehicle != null) {
            modVehicle.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, vehicle);
        }

        float gunYaw = 90.0F;
        float gunPitch = 0.0F;
        GL11.glPushMatrix();
        if(modVehicle != null && modVehicle.gunModel.length > 0) { // && vehicle.data.guns[1] != null
            EntityPassengerSeat[] arr$ = vehicle.seats;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
//                EntityPassengerSeat seat = arr$[i$];
//                if(seat.gunnerID == 1 && seat.passenger != null) {
//                    gunYaw = -seat.passenger.yaw - f + 90.0F;
//                    gunPitch = seat.passenger.pitch - vehicle.axes.getPitch();
//                }
            }

            while(gunYaw > 90.0F) {
                gunYaw -= 360.0F;
            }

            while(gunYaw <= -270.0F) {
                gunYaw += 360.0F;
            }

            if(gunYaw > vehicle.automobile.gunYawMax - 90.0F) {
                gunYaw = vehicle.automobile.gunYawMax - 90.0F;
            }

            if(gunYaw < vehicle.automobile.gunYawMin - 90.0F) {
                gunYaw = vehicle.automobile.gunYawMin - 90.0F;
            }

            if(gunPitch < vehicle.automobile.gunPitchMax) {
                gunPitch = vehicle.automobile.gunPitchMax;
            }

            if(gunPitch > vehicle.automobile.gunPitchMin) {
                gunPitch = vehicle.automobile.gunPitchMin;
            }

            GL11.glTranslatef(modVehicle.gunModel[0].rotationPointX / 16.0F, modVehicle.gunModel[0].rotationPointY / 16.0F, modVehicle.gunModel[0].rotationPointZ / 16.0F);
            GL11.glRotatef(180.0F + gunYaw, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-modVehicle.gunModel[0].rotationPointX / 16.0F, -modVehicle.gunModel[0].rotationPointY / 16.0F, -modVehicle.gunModel[0].rotationPointZ / 16.0F);

        }

        GL11.glPopMatrix();
        if(modVehicle != null && modVehicle.barrelModel.length > 0 && vehicle.automobile.hasTurret) {
            gunYaw = vehicle.gunYaw;
            gunPitch = vehicle.gunPitch;
            GL11.glTranslatef(modVehicle.turretModel[0].rotationPointX / 16.0F, modVehicle.turretModel[0].rotationPointY / 16.0F, modVehicle.turretModel[0].rotationPointZ / 16.0F);
            GL11.glRotatef(180.0F + gunYaw, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-modVehicle.turretModel[0].rotationPointX / 16.0F, -modVehicle.turretModel[0].rotationPointY / 16.0F, -modVehicle.turretModel[0].rotationPointZ / 16.0F);
            modVehicle.renderTurret(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, vehicle, gunYaw, gunPitch);
        }

        GL11.glPopMatrix();
    }

    public void render(EntityBase entity, double d, double d1, double d2, float f, float f1) {
        this.func_157_a((EntityVehicle)entity, d, d1, d2, f, f1);
    }
}
