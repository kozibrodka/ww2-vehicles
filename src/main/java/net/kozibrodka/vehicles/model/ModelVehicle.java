package net.kozibrodka.vehicles.model;

import net.kozibrodka.tmt.TURBO_MODEL_125.*;
import net.kozibrodka.vehicles.entity.EntityVehicle;

public class ModelVehicle extends ModelBase {
//    public ModelRendererTurbo[] bodyModel;
//    public ModelRendererTurbo[] turretModel;
//    public ModelRendererTurbo[] barrelModel;
//    public ModelRendererTurbo[] gunModel;
//    public ModelRendererTurbo[] ammoModel;
//    public ModelRendererTurbo[] leftFrontWheelModel;
//    public ModelRendererTurbo[] rightFrontWheelModel;
//    public ModelRendererTurbo[] leftBackWheelModel;
//    public ModelRendererTurbo[] rightBackWheelModel;
//
//    public ModelRendererTurbo[] leftTrackWheelModels;
//    public ModelRendererTurbo[] leftTrackModel;
//    public ModelRendererTurbo[] rightTrackWheelModels;
//    public ModelRendererTurbo[] rightTrackModel;

    public ModelRendererTurbo[] bodyModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] turretModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] barrelModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] gunModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] ammoModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] leftFrontWheelModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] rightFrontWheelModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] leftBackWheelModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] rightBackWheelModel = new ModelRendererTurbo[0];

    public ModelRendererTurbo[] leftTrackWheelModels = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] leftTrackModel = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] rightTrackWheelModels = new ModelRendererTurbo[0];
    public ModelRendererTurbo[] rightTrackModel = new ModelRendererTurbo[0];

//    public CollisionBox[] collisionBoxes;

    public void render(float f, float f1, float f2, float f3, float f4, float f5, EntityVehicle vehicle) {
        int i;
        for(i = 0; i < this.bodyModel.length; ++i) {
            this.bodyModel[i].render(f5);
        }

        for(i = 0; i < this.leftBackWheelModel.length; ++i) {
            this.leftBackWheelModel[i].render(f5);
        }

        for(i = 0; i < this.rightBackWheelModel.length; ++i) {
            this.rightBackWheelModel[i].render(f5);
        }

        for(i = 0; i < this.leftFrontWheelModel.length; ++i) {
            this.leftFrontWheelModel[i].rotateAngleY = -vehicle.wheelsYaw * (float)Math.PI / 180.0F * 10.0F;
            this.leftFrontWheelModel[i].render(f5);
        }

        for(i = 0; i < this.rightFrontWheelModel.length; ++i) {
            this.rightFrontWheelModel[i].rotateAngleY = -vehicle.wheelsYaw * (float)Math.PI / 180.0F * 10.0F;
            this.rightFrontWheelModel[i].render(f5);
        }

        // usunac nulle
        if(leftTrackModel != null) {
            for (i = 0; i < this.leftTrackModel.length; ++i) {
                this.leftTrackModel[i].render(f5);
            }
        }
        if(rightTrackModel != null) {
            for (i = 0; i < this.rightTrackModel.length; ++i) {
                this.rightTrackModel[i].render(f5);
            }
        }
        if(leftTrackWheelModels != null) {
            for (i = 0; i < this.leftTrackWheelModels.length; ++i) {
                this.leftTrackWheelModels[i].rotateAngleZ = -vehicle.wheelsAngle;
                this.leftTrackWheelModels[i].render(f5);
            }
        }
        if(rightTrackWheelModels != null) {
            for (i = 0; i < this.rightTrackWheelModels.length; ++i) {
                this.rightTrackWheelModels[i].rotateAngleZ = -vehicle.wheelsAngle;
                this.rightTrackWheelModels[i].render(f5);
            }
        }
        //

    }

    public void renderGun(float f, float f1, float f2, float f3, float f4, float f5, EntityVehicle vehicle, float gunYaw, float gunPitch) {
        int i;
        for(i = 0; i < this.gunModel.length; ++i) {
            this.gunModel[i].rotateAngleX = gunPitch * (float)Math.PI / 180.0F;
            this.gunModel[i].render(f5);
        }

        if(true) { //if(vehicle.data.ammo[1] != null)
            for(i = 0; i < this.ammoModel.length; ++i) {
                this.ammoModel[i].rotateAngleX = gunPitch * (float)Math.PI / 180.0F;
                this.ammoModel[i].render(f5);
            }
        }

    }

    public void renderTurret(float f, float f1, float f2, float f3, float f4, float f5, EntityVehicle vehicle, float gunYaw, float gunPitch) {
        int i;
        for(i = 0; i < this.turretModel.length; ++i) {
            this.turretModel[i].render(f5);
        }

        for(i = 0; i < this.barrelModel.length; ++i) {
            this.barrelModel[i].rotateAngleZ = -gunPitch * (float)Math.PI / 180.0F;
            this.barrelModel[i].render(f5);
        }

    }

    public void flipAll() {
        int i;
        for(i = 0; i < this.bodyModel.length; ++i) {
            this.bodyModel[i].doMirror(false, true, true);
            this.bodyModel[i].setRotationPoint(this.bodyModel[i].rotationPointX, -this.bodyModel[i].rotationPointY, -this.bodyModel[i].rotationPointZ);
        }

        for(i = 0; i < this.turretModel.length; ++i) {
            this.turretModel[i].doMirror(false, true, true);
            this.turretModel[i].setRotationPoint(this.turretModel[i].rotationPointX, -this.turretModel[i].rotationPointY, -this.turretModel[i].rotationPointZ);
        }

        for(i = 0; i < this.barrelModel.length; ++i) {
            this.barrelModel[i].doMirror(false, true, true);
            this.barrelModel[i].setRotationPoint(this.barrelModel[i].rotationPointX, -this.barrelModel[i].rotationPointY, -this.barrelModel[i].rotationPointZ);
        }

        for(i = 0; i < this.leftFrontWheelModel.length; ++i) {
            this.leftFrontWheelModel[i].doMirror(false, true, true);
            this.leftFrontWheelModel[i].setRotationPoint(this.leftFrontWheelModel[i].rotationPointX, -this.leftFrontWheelModel[i].rotationPointY, -this.leftFrontWheelModel[i].rotationPointZ);
        }

        for(i = 0; i < this.rightFrontWheelModel.length; ++i) {
            this.rightFrontWheelModel[i].doMirror(false, true, true);
            this.rightFrontWheelModel[i].setRotationPoint(this.rightFrontWheelModel[i].rotationPointX, -this.rightFrontWheelModel[i].rotationPointY, -this.rightFrontWheelModel[i].rotationPointZ);
        }

        for(i = 0; i < this.leftBackWheelModel.length; ++i) {
            this.leftBackWheelModel[i].doMirror(false, true, true);
            this.leftBackWheelModel[i].setRotationPoint(this.leftBackWheelModel[i].rotationPointX, -this.leftBackWheelModel[i].rotationPointY, -this.leftBackWheelModel[i].rotationPointZ);
        }

        for(i = 0; i < this.rightBackWheelModel.length; ++i) {
            this.rightBackWheelModel[i].doMirror(false, true, true);
            this.rightBackWheelModel[i].setRotationPoint(this.rightBackWheelModel[i].rotationPointX, -this.rightBackWheelModel[i].rotationPointY, -this.rightBackWheelModel[i].rotationPointZ);
        }

        //
        for(i = 0; i < this.leftTrackWheelModels.length; ++i) {
            this.leftTrackWheelModels[i].doMirror(false, true, true);
            this.leftTrackWheelModels[i].setRotationPoint(this.leftTrackWheelModels[i].rotationPointX, -this.leftTrackWheelModels[i].rotationPointY, -this.leftTrackWheelModels[i].rotationPointZ);
        }

        for(i = 0; i < this.rightTrackWheelModels.length; ++i) {
            this.rightTrackWheelModels[i].doMirror(false, true, true);
            this.rightTrackWheelModels[i].setRotationPoint(this.rightTrackWheelModels[i].rotationPointX, -this.rightTrackWheelModels[i].rotationPointY, -this.rightTrackWheelModels[i].rotationPointZ);
        }

        for(i = 0; i < this.leftTrackModel.length; ++i) {
            this.leftTrackModel[i].doMirror(false, true, true);
            this.leftTrackModel[i].setRotationPoint(this.leftTrackModel[i].rotationPointX, -this.leftTrackModel[i].rotationPointY, -this.leftTrackModel[i].rotationPointZ);
        }

        for(i = 0; i < this.rightTrackModel.length; ++i) {
            this.rightTrackModel[i].doMirror(false, true, true);
            this.rightTrackModel[i].setRotationPoint(this.rightTrackModel[i].rotationPointX, -this.rightTrackModel[i].rotationPointY, -this.rightTrackModel[i].rotationPointZ);
        }
        //


    }

    public void translateAll(int y) {
        ModelRendererTurbo[] arr$ = this.bodyModel;
        int len$ = arr$.length;

        int i$;
        ModelRendererTurbo mod;
        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.turretModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.barrelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.gunModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.ammoModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.leftFrontWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.rightFrontWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.leftBackWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

        arr$ = this.rightBackWheelModel;
        len$ = arr$.length;

        for(i$ = 0; i$ < len$; ++i$) {
            mod = arr$[i$];
            mod.rotationPointY += (float)y;
        }

    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    }
}
